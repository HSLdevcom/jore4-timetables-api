package fi.hsl.jore4.timetables.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jore4.db")
data class DatabaseConfiguration(
    val driver: String,
    val url: String,
    val username: String,
    val password: String
)
