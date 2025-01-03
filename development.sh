#!/usr/bin/env bash

set -eu

cd "$(dirname "$0")" # Setting the working directory as the script directory

COMMAND=${1:-}
ARG1=${2:-}

DOCKER_COMPOSE_CMD="docker compose -f ./docker/docker-compose.yml -f ./docker/docker-compose.custom.yml"

instruct_and_exit() {
  echo "Usage: ${0} <command> [<argument>]"
  echo ""
  echo "Available commands:"
  echo ""
  echo "start [<commit_sha>]       Start the dependencies and the dockerized application. A commit SHA can"
  echo "                           be given as an argument to fetch a specific version of Docker Compose"
  echo "                           bundle. Without argument, the latest commit in the main branch of the"
  echo "                           jore4-docker-compose-bundle repository is used."
  echo ""
  echo "start:deps [<commit_sha>]  Start the dependencies only. A commit SHA can be given as an argument to"
  echo "                           fetch a specific version of Docker Compose bundle. Without argument, the"
  echo "                           latest commit in the main branch of the jore4-docker-compose-bundle"
  echo "                           repository is used."
  echo ""
  echo "generate:jooq              Generate jOOQ classes"
  echo ""
  echo "build:data-inserter        Installs required dependencies and builds the timetables data inserter"
  echo ""
  echo "stop                       Stop the dependencies and the dockerized application"
  echo ""
  exit 1
}

# Download Docker Compose bundle from the "jore4-docker-compose-bundle"
# repository. GitHub CLI is required to be installed.
#
# A commit SHA can be given as an argument.
download_docker_bundle() {
  local commit_sha="${1:-}"

  local repo_name="jore4-docker-compose-bundle"
  local repo_owner="HSLdevcom"
  local gh_common_path="/repos/${repo_owner}/${repo_name}"

  if [[ -n "$commit_sha" ]]; then
    # Verify that a commit with SHA actually exists in the repository.
    echo "Verifying that a commit with SHA '${commit_sha}' exists in the ${repo_owner}/${repo_name} repository..."

    # First, query GitHub API using the commit SHA argument.
    local http_response=$(
      gh api \
        -H "Accept: application/vnd.github+json" \
        -H "X-GitHub-Api-Version: 2022-11-28" \
        "${gh_common_path}/commits/${commit_sha}" \
        --silent \
        --include
    )
    local http_status=$(echo "$http_response" | awk '/^HTTP/{print $2}')

    # Then, check if the status is not 200.
    if [[ $http_status != "200" ]]; then
      echo "Error: Querying GitHub API with the commit ID '${commit_sha}' failed." >&2
      exit 1
    else
      echo "SHA digest OK."
    fi
  else # when no argument given
    # Resolve the SHA digest of the last commit in the main branch.
    commit_sha=$(
      gh api \
        -H "Accept: application/vnd.github+json" \
        -H "X-GitHub-Api-Version: 2022-11-28" \
        "${gh_common_path}/branches/main" \
        --jq '.commit.sha'
    )

    echo "Resolved the SHA digest of the last commit in the main branch: ${commit_sha}"
  fi

  local zip_file="/tmp/${repo_name}.zip"
  local unzip_target_dir_prefix="/tmp/${repo_owner}-${repo_name}"

  # Remove old temporary directories if any remain.
  rm -fr "$unzip_target_dir_prefix"-*

  echo "Downloading the JORE4 Docker Compose bundle..."

  # Download the latest Docker Compose bundle from the
  # jore4-docker-compose-bundle repository as a ZIP file and extract its
  # contents to a temporary directory.
  gh api "${gh_common_path}/zipball/${commit_sha}" > "$zip_file" \
    && unzip -q "$zip_file" -d /tmp

  # Clean untracked files from `docker` directory even if they are git-ignored.
  git clean -fx ./docker

  # Copy files from the `docker-compose` directory of the ZIP file to your
  # local `docker` directory.
  mv "$unzip_target_dir_prefix"-*/docker-compose/* ./docker

  # Remove the temporary files and directories created above.
  rm -fr "$zip_file" "$unzip_target_dir_prefix"-*

  echo "Generating a release version file for the downloaded bundle..."

  # Create a release version file containing the SHA digest of the referenced
  # commit.
  echo "$commit_sha" > ./docker/RELEASE_VERSION.txt
}

start_all() {
  local commit_sha="${1:-}"

  download_docker_bundle ${commit_sha}
  $DOCKER_COMPOSE_CMD up -d jore4-hasura jore4-testdb
  $DOCKER_COMPOSE_CMD up --build -d jore4-timetables-api
  prepare_timetables_data_inserter
}

start_deps() {
  local commit_sha="${1:-}"

  download_docker_bundle ${commit_sha}
  # Runs the following services:
  # jore4-hasura - Hasura. We have to start Hasura because it ensures that db migrations are run to the Jore 4 database.
  # jore4-testdb - Jore 4 database. This is the database used by the API.
  $DOCKER_COMPOSE_CMD -f ./docker/docker-compose.test.yml up --build -d jore4-hasura jore4-testdb jore4-hasura-test jore4-testdb-test
  prepare_timetables_data_inserter
}

generate_jooq() {
  mvn clean generate-sources -Pci
}

prepare_timetables_data_inserter() {
  ensure_hasura_submodule_initialized

  cd jore4-hasura/test/hasura
  yarn install
  yarn timetables-data-inserter:build
  cd -
}

ensure_hasura_submodule_initialized() {
  if [ ! -d jore4-hasura/test ]; then
    echo "jore4-hasura submodule not found! Initializing..."

    git submodule init
    git submodule update
    echo "jore4-hasura submodule: setting sparse checkout..."
    cd jore4-hasura
    git sparse-checkout init --cone
    git sparse-checkout set test/hasura
    cd -

    echo "jore4-hasura submodule initialized."
  fi

  echo "jore4-hasura submodule: updating..."
  git submodule update --remote
  echo "jore4-hasura submodule up to date."
}

### Control flow

if [[ -z ${COMMAND} ]]; then
  instruct_and_exit
fi

if [[ ${COMMAND} == "start" ]]; then
  start_all ${ARG1}
  exit 0
fi

if [[ ${COMMAND} == "start:deps" ]]; then
  start_deps ${ARG1}
  exit 0
fi

if [[ ${COMMAND} == "build:data-inserter" ]]; then
  prepare_timetables_data_inserter
  exit 0
fi

if [[ ${COMMAND} == "generate:jooq" ]]; then
  while ! pg_isready -h localhost -p 6432
  do
    echo "waiting for Jore 4 db to spin up"
    sleep 2;
  done
  while ! curl --fail http://localhost:3201/healthz --output /dev/null --silent
  do
    echo "waiting for hasura db migrations to execute"
    sleep 2;
  done
  generate_jooq
  exit 0
fi

if [[ ${COMMAND} == "stop" ]]; then
  $DOCKER_COMPOSE_CMD down
  exit 0
fi

if [[ ${COMMAND} == "remove" ]]; then
  $DOCKER_COMPOSE_CMD rm -f
  exit 0
fi

### Unknown argument was passed.

echo "Unknown command '${COMMAND}' !"
echo ""
instruct_and_exit
