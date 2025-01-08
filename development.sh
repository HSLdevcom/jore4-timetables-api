#!/usr/bin/env bash

set -eu

cd "$(dirname "$0")" # Setting the working directory as the script directory

COMMAND=${1:-}

DOCKER_COMPOSE_CMD="docker compose -f ./docker/docker-compose.yml -f ./docker/docker-compose.custom.yml"

instruct_and_exit() {
  echo "Usage: ${0} <command> [<argument>]"
  echo ""
  echo "Available commands:"
  echo ""
  echo "start [<commit_ref>]       Start the dependencies and the dockerized application. A commit"
  echo "                           reference can be given as an argument to fetch a specific version of"
  echo "                           Docker Compose bundle. Without argument, the latest commit in the main"
  echo "                           branch of the jore4-docker-compose-bundle repository is used."
  echo ""
  echo "start:deps [<commit_ref>]  Start the dependencies only. A commit reference can be given as an"
  echo "                           argument to fetch a specific version of Docker Compose bundle. Without"
  echo "                           argument, the latest commit in the main branch of the"
  echo "                           jore4-docker-compose-bundle repository is used."
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
# A commit reference can be given as an argument. It can contain, for example,
# only a substring of an actual SHA digest.
download_docker_bundle() {
  local commit_ref="${1:-main}"

  local repo_name="jore4-docker-compose-bundle"
  local repo_owner="HSLdevcom"
  local gh_common_path="/repos/${repo_owner}/${repo_name}"

  # Check GitHub CLI availability.
  if ! command -v gh &> /dev/null; then
    echo "Please install the GitHub CLI (gh) on your machine."
    exit 1
  fi

  # Make sure the user is authenticated to GitHub.
  gh auth status || gh auth login

  echo "Using the commit reference '${commit_ref}' to fetch a Docker Compose bundle..."

  # First, try to find a commit on GitHub that matches the given reference.
  local commit_sha=$(
    gh api \
      -H "Accept: application/vnd.github+json" \
      -H "X-GitHub-Api-Version: 2022-11-28" \
      "${gh_common_path}/commits/${commit_ref}" \
      --jq '.sha'
  )

  # Then, check if a match wasn't found, meaning the previous GitHub CLI command
  # failed.
  if [[ $? -ne 0 ]]; then
    echo "Error: Querying GitHub API with the commit ref '${commit_ref}' failed." >&2
    exit 1
  fi

  echo "Commit with the following SHA digest was found: ${commit_sha}"

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
  download_docker_bundle "$@"
  $DOCKER_COMPOSE_CMD up -d jore4-hasura jore4-testdb
  $DOCKER_COMPOSE_CMD up --build -d jore4-timetables-api
  prepare_timetables_data_inserter
}

start_deps() {
  download_docker_bundle "$@"
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

# Shift other arguments after the command so that we can refer to them later
# with "$@".
shift

if [[ ${COMMAND} == "start" ]]; then
  start_all "$@"
  exit 0
fi

if [[ ${COMMAND} == "start:deps" ]]; then
  start_deps "$@"
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
