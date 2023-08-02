# jore4-timetables-api

REST API for Jore4 timetables

## Development

### Necessary tools

- Maven
- JDK17+

Uses maven to build the project, use `mvn install` to build the server. You can also run the generated .jar file locally to test the server on port 8080.

`development.sh` provides several commands to run the server in a docker container:

- `start` runs the server in port 3008
- `stop` stops the container
- `remove` removes the docker container
- `build` builds the server locally using maven
- `test` runs all tests

## API structure

### GET

`hello` Hello world example interface returns http 200

## Technical Documentation

jore4-timetables-api is a Spring Boot application written in Kotlin, which implements a REST API for converting Hastus CSV into Jore4 data and the reverse, Jore4 data into CSV files for Hastus.

### Package Structure

- `fi.hsl.jore.timetables.api` package contains the API endpoint definitions
- `fi.hsl.jore.timetables.config` package contains the server configuration

Tests are in the `fi.hsl.jore.timetables.test` package.

## Developer Guide

### Coding Conventions

Code should be written using [standard Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html).

Also:

- ktlint is run automatically during build and will fail the build if any warnings are found
- Additionally, minimize the use of mutable variables, using `val` whenever possible.

## Docker reference

The application uses spring boot which allows overwriting configuration properties as described
[here](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config.typesafe-configuration-properties.relaxed-binding.environment-variables).
The docker container is also able to
[read secrets](https://github.com/HSLdevcom/jore4-tools#read-secretssh) and expose
them as environment variables.

The following configuration properties are to be defined for each environment:

| Config property   | Environment variable   | Secret name | Example                             | Description                                           |
| ----------------- | ---------------------- | ----------- | ----------------------------------- | ----------------------------------------------------- |
| -                 | SECRET_STORE_BASE_PATH | -           | /mnt/secrets-store                  | Directory containing the docker secrets               |
| jore4.db.driver   | HASURA_URL             | hasura-url  | http://jore4-hasura:8080/v1/graphql | Driver for database connection. Postgresql by default |
| jore4.db.url      | HASURA_URL             | hasura-url  | http://jore4-hasura:8080/v1/graphql | JDBC connection URL for database                      |
| jore4.db.username | HASURA_URL             | hasura-url  | http://jore4-hasura:8080/v1/graphql | Database username                                     |
| jore4.db.password | HASURA_URL             | hasura-url  | http://jore4-hasura:8080/v1/graphql | Database user password                                |
| jooq.sql.dialect  | HASURA_URL             | hasura-url  | http://jore4-hasura:8080/v1/graphql | Dialect for jOOQ, postgresql by default               |

More properties can be found from `/profiles/prod/config.properties`
