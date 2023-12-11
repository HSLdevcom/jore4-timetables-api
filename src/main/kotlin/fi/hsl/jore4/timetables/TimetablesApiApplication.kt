package fi.hsl.jore4.timetables

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import fi.hsl.jore4.timetables.config.AuthenticationProperties
import fi.hsl.jore4.timetables.config.DatabaseProperties
import fi.hsl.jore4.timetables.config.JOOQProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

fun main(args: Array<String>) {
    runApplication<TimetablesApiApplication>(*args)
}

/**
 * Spring boot application definition.
 */
@SpringBootApplication
@EnableConfigurationProperties(AuthenticationProperties::class, DatabaseProperties::class, JOOQProperties::class)
class TimetablesApiApplication {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .registerModule(JavaTimeModule())
            .registerModule(
                KotlinModule.Builder()
                    .withReflectionCacheSize(512)
                    .configure(KotlinFeature.NullToEmptyCollection, false)
                    .configure(KotlinFeature.NullToEmptyMap, false)
                    .configure(KotlinFeature.NullIsSameAsDefault, false)
                    .configure(KotlinFeature.SingletonSupport, false)
                    .configure(KotlinFeature.StrictNullChecks, true)
                    .build()
            )
    }
}
