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

    companion object {
        val logger = KotlinLogging.logger {}

        private const val ROLE_HEADER = "X-Hasura-Role"
        private const val ID_HEADER = "X-Hasura-Id"
        private val objectMapper = ObjectMapper()
        private val httpClient = HttpClient.newHttpClient()

        private fun creteAuthenticationToken(authResponse: HttpResponse<String>): Authentication {
            val authResponseMap = objectMapper.readValue<MutableMap<Any, Any>>(authResponse.body())

            logger.debug(authResponse.toString())
            logger.debug(authResponseMap.toString())

            return UsernamePasswordAuthenticationToken.authenticated(
                authResponseMap[ID_HEADER],
                "", // Credentials not used
                listOf(SimpleGrantedAuthority(authResponseMap[ROLE_HEADER].toString()))
            )
        }

        fun sendRequest(authRequest: HttpRequest): HttpResponse<String> {
            return httpClient.send(authRequest, HttpResponse.BodyHandlers.ofString()).also {
                logger.debug("Authorization response $it")
            }
        }
    }

    private fun authenticateWithHasuraWebhook(authentication: Authentication?): HttpResponse<String> {
        val authRequest = HttpRequest.newBuilder().run {
            uri(URI("http://jore4-auth:8080/public/v1/hasura/webhook"))
            headers("cookie", "SESSION=${authentication?.principal}", ROLE_HEADER, authentication?.credentials.toString(), "Accept", "application/json")
            header("cookie", "SESSION=${authentication?.principal}")
            header(ROLE_HEADER, authentication?.credentials.toString())
            header("Accept", "application/json")
            GET()
            build()
        }

        logger.debug("Sending authorization request to $authRequest")
        logger.debug("Authorization headers ${authRequest.headers()}")

        return sendRequest(authRequest)
    }

    override fun authenticate(authentication: Authentication?): Authentication {
        val authResponse = authenticateWithHasuraWebhook(authentication)
        if (authResponse.body().isBlank()) {
            return UsernamePasswordAuthenticationToken.unauthenticated("", "")
        }
        return creteAuthenticationToken(authResponse)
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.equals(PreAuthenticatedAuthenticationToken::class.java) ?: false
    }
}
