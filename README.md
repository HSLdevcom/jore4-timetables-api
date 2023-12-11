# jore4-timetables-api

REST API for Jore4 timetables

## Development

### Necessary tools

- Maven
- JDK17+
- Node.js 18.x
- Yarn 1.x

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

### Submodules

Tests use `timetables-data-inserter` from ´jore4-hasura´ repository.
This is included as a Git submodule.
The submodule is automatically initialized when dependencies are set up with `development.sh`.

When the submodule is updated, to get the newest version of inserter you need to:
- Update git submodules with `git submodule update`
- Install dependencies and build the timetables data inserter with `development.sh`,
  by running either of the `start` tasks or `build:data-inserter`

## API structure

- `GET /hello` Hello world example interface returns http 200
- `POST /timetables/replace`: Import staging timetables to target priority
  by replacing matching currently active vehicle schedule frames.
  Example request body:
  ```JSON
  {
    // Ids of staging vehicle schedule frames to import by replacing.
    "stagingVehicleScheduleFrameIds": [
      "77fa8187-9a8e-4ce6-9fe2-5855f438b0e2",
      "b82d135a-bd47-466b-a3a4-710c9ea4b430"
    ],
    // The priority to which the staging timetables will be promoted.
    // Timetables priority, e.g. STANDARD = 10
    "targetPriority": 10
  }
  ```
  Example response body:
  ```JSON
  {
    // The IDs of vehicle schedule frames of target priority that got replaced.
    // There can be 0-n of these per staging frame.
    "replacedVehicleScheduleFrameIds": [
      "d3d0aea6-db3f-4421-b4eb-39cffe8835a8",
      "e0ea0778-833d-4ce1-8f2d-27109cab4a4e"
    ]
  }
  ```
- `POST /timetables/combine`: Import staging timetables to target priority
  by combining new vehicle journeys to matching vehicle schedule frames.
  Example request body:
  ```JSON
  {
    // Ids of staging vehicle schedule frames to import by combining.
    "stagingVehicleScheduleFrameIds": [
      "77fa8187-9a8e-4ce6-9fe2-5855f438b0e2",
      "b82d135a-bd47-466b-a3a4-710c9ea4b430"
    ],
    // The priority to which the staging timetables will be promoted.
    // Timetables priority, e.g. STANDARD = 10
    "targetPriority": 10
  }
  ```
  Example response body:
  ```JSON
  {
    // The IDs of vehicle schedule frames of target priority where staging vehicle schedule frames got combined to.
    // One for each staging frame, in same order.
    "combinedIntoVehicleScheduleFrameIds": [
      "d3d0aea6-db3f-4421-b4eb-39cffe8835a8",
      "e0ea0778-833d-4ce1-8f2d-27109cab4a4e"
    ]
  }
  ```
- `GET /timetables/to-replace`: Fetch the vehicle schedule frame IDs slated for replacement,
  considering the replacement action with the staging vehicle schedule frame ID and target priority.
  - Request params:
    - `stagingVehicleScheduleFrameId` The ID of the staging vehicle schedule frame. Example: `"50f939b0-aac3-453a-b2f5-24c0cdf8ad21"`
    - `targetPriority` The priority to which the staging timetables will be promoted. Example: `10`

  Example response body:
  ```JSON
  {
    // The IDs of vehicle schedule frames slated for replacement
    "toReplaceVehicleScheduleFrameIds": [
      "d3d0aea6-db3f-4421-b4eb-39cffe8835a8",
      "e0ea0778-833d-4ce1-8f2d-27109cab4a4e"
    ]
  }
  ```

## Authentication

Authentication is done via the existing Hasura webhook in the Jore4 Auth -service. The client holds a Spring session
cookie which is used to verify their identity. Each request accessing a protected endpoint must also have a role header
which includes which role they are requesting.

Example request:

```
GET http://jore4-auth:8080/public/v1/hasura/webhook

headers {
  cookie: "SESSION=1234567890abcdefghijklmnopqrstuvwxyz"
  x-hasura-role: "admin"
}
```

Will return either `HTTP 401 Unauthorized` or a response with `HTTP 200` and
```
headers {
  x-hasura-role: "granted role here"
  x-hasura-id: "ID of user"
}
```
Which can be used in the security context to grant access to protected endpoints and log user actions using the ID.

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
- In tests, write test function names with backticks instead of `@DisplayName` annotation, eg:
  ````
  @Test
  fun `some name with spaces`() {
  ````
  - Do *not* use this convention anywhere else besides test function names.

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
