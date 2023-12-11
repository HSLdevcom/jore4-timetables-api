package fi.hsl.jore4.timetables.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "authentication")
data class AuthenticationProperties(
    val url: String
)
