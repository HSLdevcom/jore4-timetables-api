package fi.hsl.jore4.timetables.api.util

// See https://hasura.io/docs/latest/actions/action-handlers/#returning-an-error-response
open class HasuraErrorResponse(
    nullableMessage: String?,
    open val extensions: HasuraErrorExtensions
) {
    val message = nullableMessage ?: "An error occurred."
}

class JoreErrorResponse(
    nullableMessage: String?,
    override val extensions: JoreErrorExtensions
) : HasuraErrorResponse(nullableMessage, extensions)
