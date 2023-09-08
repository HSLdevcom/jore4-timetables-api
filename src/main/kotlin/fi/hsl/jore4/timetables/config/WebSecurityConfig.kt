package fi.hsl.jore4.timetables.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun configure(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) }
            .csrf { it.disable() }
            .cors {}
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        HttpMethod.GET,
                        "/actuator/health",
                        "/error",
                        "/hello",
                        "/hello/test"
                    )
                    .permitAll()
                it
                    .requestMatchers(
                        HttpMethod.POST,
                        "/timetables/*",
                    )
                    .permitAll()

                    .anyRequest().denyAll()
            }
            .build()
    }
}
