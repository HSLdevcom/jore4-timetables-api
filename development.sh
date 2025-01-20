#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "$0")" # Setting the working directory as the script directory

# By default, the tip of the main branch of the jore4-docker-compose-bundle
# repository is used as the commit reference, which determines the version of
# the Docker Compose bundle to download. For debugging purposes, this default
# can be overridden by some other commit reference (e.g., commit SHA or its
# initial substring), which you can pass via the `BUNDLE_REF` environment
# variable.
DOCKER_COMPOSE_BUNDLE_REF=${BUNDLE_REF:-main}

DOCKER_COMPOSE_CMD="docker compose -f ./docker/docker-compose.yml -f ./docker/docker-compose.custom.yml"

print_usage() {
  echo "
  Usage: $(basename "$0") <command>

  Available commands:

  start
    Start the dependencies and the dockerized application.

    You can control which version of the Docker Compose bundle is downloaded by
    passing a commit reference to the jore4-docker-compose-bundle repository via
    the BUNDLE_REF environment variable. By default, the latest version is
    downloaded.

  start:deps
    Start the dependencies only.

    You can control which version of the Docker Compose bundle is downloaded by
    passing a commit reference to the jore4-docker-compose-bundle repository via
    the BUNDLE_REF environment variable. By default, the latest version is
    downloaded.

  generate:jooq
    Generate jOOQ classes.

  build:data-inserter
    Build the timetables data inserter (git submodule).

  stop
    Stop the dependencies and the dockerised application.

  remove
    Remove the dependencies and the dockerised application.
  "
}

# Download Docker Compose bundle from the "jore4-docker-compose-bundle"
# repository. GitHub CLI is required to be installed.
#
# A commit reference is read from global `DOCKER_COMPOSE_BUNDLE_REF` variable,
# which should be set based on the script execution arguments.
download_docker_compose_bundle() {
  local commit_ref="$DOCKER_COMPOSE_BUNDLE_REF"

  local repo_name="jore4-docker-compose-bundle"
  local repo_owner="HSLdevcom"

  # Check GitHub CLI availability.
  if ! command -v gh &> /dev/null; then
    echo "Please install the GitHub CLI (gh) on your machine."
    exit 1
  fi

  # Make sure the user is authenticated to GitHub.
  gh auth status || gh auth login

  echo "Using the commit reference '${commit_ref}' to fetch a Docker Compose bundle..."

  # First, try to find a commit on GitHub that matches the given reference.
  # This function exits with an error code if no matching commit is found.
  local commit_sha
  commit_sha=$(
    gh api \
      -H "Accept: application/vnd.github+json" \
      -H "X-GitHub-Api-Version: 2022-11-28" \
      "repos/${repo_owner}/${repo_name}/commits/${commit_ref}" \
      --jq '.sha'
  )

  echo "Commit with the following SHA digest was found: ${commit_sha}"

  local zip_file="/tmp/${repo_name}.zip"
  local unzip_target_dir_prefix="/tmp/${repo_owner}-${repo_name}"

  # Remove old temporary directories if any remain.
  rm -fr "$unzip_target_dir_prefix"-*

  echo "Downloading the JORE4 Docker Compose bundle..."

  # Download Docker Compose bundle from the jore4-docker-compose-bundle
  # repository as a ZIP file.
  gh api "repos/${repo_owner}/${repo_name}/zipball/${commit_sha}" > "$zip_file"

  # Extract ZIP file contents to a temporary directory.
  unzip -q "$zip_file" -d /tmp

  # Clean untracked files from `docker` directory even if they are git-ignored.
  git clean -fx ./docker

  echo "Copying JORE4 Docker Compose bundle files to ./docker directory..."

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
  $DOCKER_COMPOSE_CMD up -d jore4-hasura jore4-testdb
  $DOCKER_COMPOSE_CMD up --build -d jore4-timetables-api
}

start_deps() {
  # Runs the following services:
  # jore4-hasura - Hasura. We have to start Hasura because it ensures that db migrations are run to the Jore 4 database.
  # jore4-testdb - Jore 4 database. This is the database used by the API.
  $DOCKER_COMPOSE_CMD -f ./docker/docker-compose.test.yml up --build -d jore4-hasura jore4-testdb jore4-hasura-test jore4-testdb-test
}

wait_for_test_databases_to_be_ready() {
  while ! pg_isready -h localhost -p 6432; do
    echo "waiting for Jore 4 db to spin up"
    sleep 2;
  done
  while ! curl --fail http://localhost:3201/healthz --output /dev/null --silent; do
    echo "waiting for hasura db migrations to execute"
    sleep 2;
  done
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

COMMAND=${1:-}

if [[ -z $COMMAND ]]; then
  print_usage
  exit 1
fi

case $COMMAND in
  start)
    download_docker_compose_bundle
    start_all
    prepare_timetables_data_inserter
    ;;

  start:deps)
    download_docker_compose_bundle
    start_deps
    prepare_timetables_data_inserter
    ;;

  build:data-inserter)
    prepare_timetables_data_inserter
    ;;

  generate:jooq)
    wait_for_test_databases_to_be_ready
    generate_jooq
    ;;

  stop)
    $DOCKER_COMPOSE_CMD down
    ;;

  remove)
    $DOCKER_COMPOSE_CMD rm -f
    ;;

  *)
    echo ""
    echo "Unknown command: '${COMMAND}'"
    print_usage
    exit 1
    ;;
esac
