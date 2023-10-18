package fi.hsl.jore4.timetables.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jore4.test.db")
data class TestDatabaseProperties(
    val username: String,
    val password: String
)
