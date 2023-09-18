# jore4-timetables-api

REST API for Jore4 timetables

## Development

### Necessary tools

- Maven
- JDK17+

Uses maven to build the project, use `mvn install` to build the server. You can also run the generated .jar file locally to test the server on port 8080.

`development.sh` provides several commands to run the server in a docker container:

- `start` runs the server, by default in port 3009
- `start:deps` starts dependencies for the REST API
- `stop` stops the container
- `remove` removes the docker container
- `generate:jooq` generate jOOQ classes
- `test` runs all tests

### jOOQ! database classes

jOOQ! classes are not automatically generated and should be refreshed using the `development.sh generate:jooq` command. This is necessary when the structure of a part of the database used by this API changes.

## API structure

### GET

`hello` Hello world example interface returns http 200

## Technical Documentation

jore4-timetables-api is a Spring Boot application written in Kotlin, which implements a REST API for accessing the timetables database and creating more complicated updates in one transaction than is possible with the graphQL interface.

### Database locking

The SERIALIZABLE transaction isolation level is chosen as the way to deal with concurrency problems, i.e. multiple parallel write transactions that conflict each other. Currently, this solution is considered sufficient because the number of concurrent users is not that high and the number of daily API requests is expected to be quite small.

If in the future it is found that this does not scale well enough, the SERIALIZABLE isolation level can be replaced by a `SELECT ... FOR UPDATE` construct in SQL statements.

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

| Config property   | Environment variable   | Secret name | Example                                       | Description                                                                                                                     |
| ----------------- | ---------------------- | ----------- | --------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| -                 | SECRET_STORE_BASE_PATH | -           | /mnt/secrets-store                            | Directory containing the docker secrets                                                                                         |
| -                 | JORE4_DB_PORT          | -           | 5432                                          | Port of database                                                                                                                |
| -                 | JORE4_DB_HOSTNAME      | -           | jore4-testdb                                  | Hostname for database                                                                                                           |
| -                 | JORE4_DB_DATABASE      | -           | timetablesdb                                  | Database name                                                                                                                   |
| jore4.db.driver   | JORE4_DB_DRIVER        | hasura-url  | org.postgresql.Driver                         | Driver for database connection. Postgresql by default                                                                           |
| jore4.db.url      | JORE4_DB_URL           | hasura-url  | jdbc:postgresql://localhost:5342/timetablesdb | JDBC connection URL for database. Constructed from JORE4_DB_PORT, JORE4_DB_HOSTNAME and JORE4_DB_DATABASE if it is not defined. |
| jore4.db.username | JORE4_DB_USERNAME      | hasura-url  | username                                      | Database username                                                                                                               |
| jore4.db.password | JORE4_DB_PASSWORD      | hasura-url  | password                                      | Database user password                                                                                                          |
| jooq.sql.dialect  | JOOQ_SQL_DIALECT       | hasura-url  | POSTGRES                                      | Dialect for jOOQ, postgresql by default                                                                                         |

More properties can be found from `/profiles/prod/config.properties`
