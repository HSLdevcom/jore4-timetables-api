package fi.hsl.jore4.timetables.api.util

// See https://hasura.io/docs/latest/actions/action-handlers/#returning-an-error-response
class HasuraErrorResponse(
    nullableMessage: String?,
    val extensions: HasuraErrorExtensions
) {
    val message = nullableMessage ?: "An error occurred."
}
