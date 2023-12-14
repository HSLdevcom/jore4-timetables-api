package fi.hsl.jore4.timetables.api.util

import org.junit.jupiter.api.Test
import org.springframework.transaction.TransactionSystemException
import kotlin.test.assertEquals

class TransactionSystemExtensionsTest {

    private fun createTransactionSystemExceptionWithCause(message: String): TransactionSystemException {
        return TransactionSystemException("test exception", Exception(message))
    }

    @Test
    fun `resolves PassingTimeStopPointMatchingOrderError`() {
        val exception = TransactionSystemExtensions.from(
            createTransactionSystemExceptionWithCause(
                """
                ERROR: passing times and their matching stop points must be in same order
                Where: PL/pgSQL function passing_times.validate_passing_time_sequences()
                """.trimIndent()
            )
        )
        assertEquals(exception.type, TimetablesApiErrorType.PassingTimeStopPointMatchingOrderError)
    }

    @Test
    fun `resolves PassingTimeFirstArrivalTimeError`() {
        val exception = TransactionSystemExtensions.from(
            createTransactionSystemExceptionWithCause(
                """
                ERROR: first passing time must not have arrival_time set
                Where: PL/pgSQL function passing_times.validate_passing_time_sequences()
                """.trimIndent()
            )
        )
        assertEquals(exception.type, TimetablesApiErrorType.PassingTimeFirstArrivalTimeError)
    }

    @Test
    fun `resolves PassingTimeLastDepartureTimeError`() {
        val exception = TransactionSystemExtensions.from(
            createTransactionSystemExceptionWithCause(
                """
                ERROR: last passing time must not have departure_time set
                Where: PL/pgSQL function passing_times.validate_passing_time_sequences()
                """.trimIndent()
            )
        )
        assertEquals(exception.type, TimetablesApiErrorType.PassingTimeLastDepartureTimeError)
    }

    @Test
    fun `resolves PassingTimeNullError`() {
        val exception = TransactionSystemExtensions.from(
            createTransactionSystemExceptionWithCause(
                """
                ERROR: all passing time that are not first or last in the sequence must have both departure and arrival time defined
                Where: PL/pgSQL function passing_times.validate_passing_time_sequences()
                """.trimIndent()
            )
        )
        assertEquals(exception.type, TimetablesApiErrorType.PassingTimeNullError)
    }

    @Test
    fun `resolves PassingTimesMixedJourneyPatternRefsError`() {
        val exception = TransactionSystemExtensions.from(
            createTransactionSystemExceptionWithCause(
                """
                ERROR: inconsistent journey_pattern_ref within vehicle journey, all timetabled_passing_times must reference same journey_pattern_ref as the vehicle_journey
                Where: PL/pgSQL function passing_times.validate_passing_time_sequences()
                """.trimIndent()
            )
        )
        assertEquals(exception.type, TimetablesApiErrorType.PassingTimesMixedJourneyPatternRefsError)
    }

    @Test
    fun `resolves ConflictingSchedulesError`() {
        val exception = TransactionSystemExtensions.from(
            createTransactionSystemExceptionWithCause(
                """
                ERROR: conflicting schedules detected: vehicle schedule frame
                Where: PL/pgSQL function vehicle_schedule.validate_queued_schedules_uniqueness()
                """.trimIndent()
            )
        )
        assertEquals(exception.type, TimetablesApiErrorType.ConflictingSchedulesError)
    }

    @Test
    fun `resolves SequentialIntegrityError`() {
        val exception = TransactionSystemExtensions.from(
            createTransactionSystemExceptionWithCause(
                """
                ERROR: Sequential integrity issues detected:
                Where: PL/pgSQL function vehicle_service.validate_service_sequential_integrity()
                """.trimIndent()
            )
        )
        assertEquals(exception.type, TimetablesApiErrorType.SequentialIntegrityError)
    }

    @Test
    fun `resolves an unknown error as TransactionSystemError`() {
        val exception = TransactionSystemExtensions.from(
            createTransactionSystemExceptionWithCause(
                """
                ERROR: something else:
                Where: PL/pgSQL function somewhere_else()
                """.trimIndent()
            )
        )
        assertEquals(exception.type, TimetablesApiErrorType.TransactionSystemError)
    }
}
