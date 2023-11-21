package fi.hsl.jore4.timetables.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Configuration
class RemoteAuthenticationProvider : AuthenticationProvider {

    val logger = KotlinLogging.logger {}

    companion object {
        val ROLE_HEADER = "X-Hasura-Role"
        val ID_HEADER = "X-Hasura-Id"
    }

    override fun authenticate(authentication: Authentication?): Authentication {
        val authRequest = HttpRequest.newBuilder().run {
            uri(URI("http://jore4-auth:8080/public/v1/hasura/webhook"))
            headers("cookie", "SESSION=${authentication?.principal}", ROLE_HEADER, authentication?.credentials.toString(), "Accept", "application/json")
            GET()
            build()
        }

        logger.debug("Sending authorization request to $authRequest")
        logger.debug("Authorization headers ${authRequest.headers()}")

        val client = HttpClient.newHttpClient()

        val authResponse = client.send(authRequest, HttpResponse.BodyHandlers.ofString())

        logger.debug("Authorization response $authResponse")

        val objectMapper = ObjectMapper()
        val authResponseMap = objectMapper.readValue<MutableMap<Any, Any>>(authResponse.body())

        logger.debug("Roles: ${authResponseMap[ROLE_HEADER]}")

        return UsernamePasswordAuthenticationToken.authenticated(
            authResponseMap[ID_HEADER],
            "", // Credentials not used
            listOf(SimpleGrantedAuthority(authResponseMap[ROLE_HEADER].toString()))
        )
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.equals(PreAuthenticatedAuthenticationToken::class.java) ?: false
    }
}
