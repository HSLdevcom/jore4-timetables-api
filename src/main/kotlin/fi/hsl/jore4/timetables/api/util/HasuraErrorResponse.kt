package fi.hsl.jore4.timetables.api.util

// See https://hasura.io/docs/latest/actions/action-handlers/#returning-an-error-response
open class HasuraErrorResponse(
    nullableMessage: String?,
    val extensions: HasuraErrorExtensions
) {
    val message = nullableMessage ?: "An error occurred."
}

open class HasuraErrorExtensions(
    open val code: Int // Must be a 4xx code
)
