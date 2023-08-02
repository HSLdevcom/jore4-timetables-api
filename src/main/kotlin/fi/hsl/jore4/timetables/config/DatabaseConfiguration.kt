package fi.hsl.jore4.timetables.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "jore4.db")
@ConstructorBinding
data class DatabaseConfiguration(
    val driver: String,
    val url: String,
    val username: String,
    val password: String
)
