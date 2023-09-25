package fi.hsl.jore4.timetables.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    val logger = KotlinLogging.logger {}

    inner class HasuraFilter(private val authenticationProvider: AuthenticationProvider) : OncePerRequestFilter() {
        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            val sessionCookie = request.cookies?.find { it.name.lowercase() == "session" }
            val roleHeader = request.getHeader("X-Hasura-Role")

            if ("OPTIONS" == request.method) {
                response.status = HttpServletResponse.SC_OK
            } else {
                if (sessionCookie == null || sessionCookie.value.isBlank()) {
                    logger.info("No session cookie in request http request")
                } else if (roleHeader == null) {
                    logger.info("No role header in http request")
                } else {
                    logger.info("cookie value ${sessionCookie.value}")

                    val preauth = PreAuthenticatedAuthenticationToken(sessionCookie.value, roleHeader)
                    SecurityContextHolder.getContext().authentication = authenticationProvider.authenticate(preauth)
                }
            }
            filterChain.doFilter(request, response)
        }
    }

    @Bean
    @Throws(Exception::class)
    fun configure(httpSecurity: HttpSecurity, authentication: RemoteAuthenticationProvider): SecurityFilterChain {
        return httpSecurity
            .addFilterBefore(HasuraFilter(authentication), UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) }
            .csrf { it.disable() }
            .cors {}
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        HttpMethod.GET,
                        "/actuator/health",
                        "/error"
                    )
                    .permitAll()
                    .requestMatchers(
                        HttpMethod.GET,
                        "/timetables/to-replace"
                    )
                    .hasAuthority("admin")
                    .requestMatchers(
                        HttpMethod.POST,
                        "/timetables/*"
                    )
                    .hasAuthority("admin")
                    .anyRequest().denyAll()
            }
            .build()
    }
}
