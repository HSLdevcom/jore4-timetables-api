package fi.hsl.jore4.timetables.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jooq.sql")
data class JOOQProperties(
    val dialect: String
)
