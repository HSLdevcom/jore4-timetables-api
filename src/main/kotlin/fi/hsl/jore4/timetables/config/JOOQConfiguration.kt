package fi.hsl.jore4.timetables.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "jooq.sql")
@ConstructorBinding
data class JOOQConfiguration(
    val dialect: String
)
