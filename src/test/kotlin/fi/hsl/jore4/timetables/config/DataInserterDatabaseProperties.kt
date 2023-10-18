package fi.hsl.jore4.timetables.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "dataInserter.db")
data class DataInserterDatabaseProperties(
    val username: String,
    val password: String
)
