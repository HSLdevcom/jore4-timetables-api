package fi.hsl.jore4.timetables.config

import io.mockk.every
import io.mockk.mockkObject
import org.junit.jupiter.api.Test
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpHeaders
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.Optional
import javax.net.ssl.SSLSession
import kotlin.jvm.optionals.getOrDefault
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RemoteAuthenticationProviderTest {

    companion object {
        private const val requestUrl = "http://testing:123"
        private const val ROLE_HEADER = "X-Hasura-Role"
        private const val ID_HEADER = "X-Hasura-Id"
    }

    private val authenticationProperties = AuthenticationProperties(requestUrl)
    private val remoteAuthenticationProvider = RemoteAuthenticationProvider(authenticationProperties)

    class CustomHttpResponse(
        private val status: Int,
        private val uri: URI,
        private val id: String,
        private val role: String
    ) : HttpResponse<String> {

        override fun statusCode(): Int {
            return status
        }

        override fun request(): HttpRequest {
            TODO("Not implemented")
        }

        override fun previousResponse(): Optional<HttpResponse<String>> {
            return Optional.empty()
        }

        override fun headers(): HttpHeaders {
            return HttpHeaders.of(mutableMapOf("Content-Type" to listOf("application/json"))) { _, _ -> true }
        }

        override fun body(): String {
            if (status == 401) {
                return ""
            }
            return """
                {
                   "$ID_HEADER": "$id",
                   "$ROLE_HEADER": "$role"
                }
            """.trimIndent()
        }

        override fun sslSession(): Optional<SSLSession> {
            return Optional.empty()
        }

        override fun uri(): URI {
            return uri
        }

        override fun version(): HttpClient.Version {
            return HttpClient.Version.HTTP_2
        }
    }

    private fun setupResponseForToken(
        sessionToken: String,
        userName: String,
        role: String
    ) {
        mockkObject(RemoteAuthenticationProvider)
        every {
            RemoteAuthenticationProvider.sendRequest(
                match { request ->
                    request.headers()
                        .firstValue("cookie")
                        .map { it.substringAfter("SESSION=") }
                        .map { it == sessionToken }
                        .getOrDefault(false)
                }
            )
        } returns CustomHttpResponse(
            200,
            URI(requestUrl),
            userName,
            role
        )

        every {
            RemoteAuthenticationProvider.sendRequest(
                match { request ->
                    request.headers()
                        .firstValue("cookie")
                        .map { it.substringAfter("SESSION=") }
                        .map { it != sessionToken }
                        .getOrDefault(true)
                }
            )
        } returns CustomHttpResponse(
            401,
            URI(requestUrl),
            "",
            ""
        )
    }

    @Test
    fun `should authenticate the user`() {
        val requestedRole = "admin"
        val userName = "user123"
        val sessionToken = "sessionToken123"

        setupResponseForToken(sessionToken, userName, requestedRole)

        val preAuth = PreAuthenticatedAuthenticationToken(sessionToken, requestedRole)

        val value = remoteAuthenticationProvider.authenticate(preAuth)

        assertTrue(value.isAuthenticated)
        assertEquals(userName, value.principal)
        assertEquals(1, value.authorities.size)
        assertEquals(requestedRole, value.authorities.first().authority)
    }

    @Test
    fun `should fail to authenticate`() {
        val requestedRole = "admin"
        val userName = "user123"
        val sessionToken = "sessionToken123"

        setupResponseForToken(sessionToken, userName, requestedRole)

        val preAuth = PreAuthenticatedAuthenticationToken("wrong token", requestedRole)

        val value = remoteAuthenticationProvider.authenticate(preAuth)

        assertFalse(value.isAuthenticated)
        assertEquals("", value.principal)
        assertEquals(0, value.authorities.size)
    }
}
