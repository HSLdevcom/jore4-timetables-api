#!/usr/bin/env bash

set -eu

cd "$(dirname "$0")" # Setting the working directory as the script directory

COMMAND=${1:-}

DOCKER_COMPOSE_CMD="docker compose -f ./docker/docker-compose.yml -f ./docker/docker-compose.custom.yml"

instruct_and_exit() {
  echo "Usage: ${0} <command>"
  echo ""
  echo "Available commands:"
  echo "start               Start the dependencies and the dockerized application"
  echo "start:deps          Start the dependencies only"
  echo "generate:jooq       Generate jOOQ classes"
  echo "build:data-inserter Installs required dependencies and builds the timetables data inserter"
  echo "stop                Stop the dependencies and the dockerized application"
  exit 1
}

download_docker_bundle() {
  # based on https://github.com/HSLdevcom/jore4-tools#download-docker-bundlesh

  echo "Downloading latest version of E2E docker-compose package..."
  curl https://raw.githubusercontent.com/HSLdevcom/jore4-tools/main/docker/download-docker-bundle.sh | bash
}

start_all() {
  download_docker_bundle
  $DOCKER_COMPOSE_CMD up -d jore4-hasura jore4-testdb
  $DOCKER_COMPOSE_CMD up --build -d jore4-timetables-api
  prepare_timetables_data_inserter
}

start_deps() {
  download_docker_bundle
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
  start_all
  exit 0
fi

if [[ ${COMMAND} == "start:deps" ]]; then
  start_deps
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
