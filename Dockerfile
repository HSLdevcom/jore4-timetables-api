FROM maven:3-eclipse-temurin-17 AS builder

# set up workdir
WORKDIR /build

# download dependencies
COPY ./pom.xml /build
RUN mvn de.qaware.maven:go-offline-maven-plugin:resolve-dependencies

# copy sources
COPY ./src /build/src

# package using "prod" profile
COPY ./profiles/prod /build/profiles/prod
RUN mvn -Pprod clean package spring-boot:repackage

FROM eclipse-temurin:17.0.7_7-jre

# expose server port
EXPOSE 8080

# download script for reading Docker secrets
RUN curl -o /tmp/lread-secrets.sh "https://raw.githubusercontent.com/HSLdevcom/jore4-tools/main/docker/read-secrets.sh"

# copy over helper scripts
COPY ./script/build-jdbc-urls.sh /tmp/

# copy compiled jar from builder stage
COPY --from=builder /build/target/*.jar /usr/src/jore4-hastus/jore4-timetables.jar

# read Docker secrets into environment variables and run application
CMD /bin/bash -c "source /tmp/read-secrets.sh && source /tmp/build-jdbc-urls.sh && java -jar /usr/src/jore4-hastus/jore4-timetables.jar"

HEALTHCHECK --interval=1m --timeout=5s \
  CMD curl --fail http://localhost:8080/actuator/health
