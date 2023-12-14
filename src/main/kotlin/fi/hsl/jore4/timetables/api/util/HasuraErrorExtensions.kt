package fi.hsl.jore4.timetables.api.util

import fi.hsl.jore4.timetables.api.TimetablesController.TargetPriorityParsingException
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.InvalidTargetPriorityException
import fi.hsl.jore4.timetables.service.MultipleTargetFramesFoundException
import fi.hsl.jore4.timetables.service.StagingVehicleScheduleFrameNotFoundException
import fi.hsl.jore4.timetables.service.TargetFrameNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.transaction.TransactionSystemException
import java.util.UUID

sealed interface HasuraErrorExtensions {
    // code must be 4xx
    val code: Int
}

sealed interface JoreErrorExtensions : HasuraErrorExtensions {
    // Not required by Hasura,  but we always want to provide one
    // so that the UI can present nice detailed error messages.
    val type: TimetablesApiErrorType
}

data class PlainStatusExtensions(
    override val code: Int,
    override val type: TimetablesApiErrorType
) : JoreErrorExtensions {

    constructor(httpStatus: HttpStatus) : this(
        httpStatus.value(),
        TimetablesApiErrorType.UnknownError
    )
}

data class InvalidTargetPriorityExtensions(
    override val code: Int,
    override val type: TimetablesApiErrorType,
    val targetPriority: TimetablesPriority
) : JoreErrorExtensions {

    companion object {
        fun from(ex: InvalidTargetPriorityException) = InvalidTargetPriorityExtensions(
            HttpStatus.BAD_REQUEST.value(),
            TimetablesApiErrorType.InvalidTargetPriorityError,
            ex.targetPriority
        )
    }
}

data class StagingVehicleScheduleFrameNotFoundExtensions(
    override val code: Int,
    override val type: TimetablesApiErrorType,
    val stagingVehicleScheduleFrameId: UUID
) : JoreErrorExtensions {

    companion object {
        fun from(ex: StagingVehicleScheduleFrameNotFoundException) = StagingVehicleScheduleFrameNotFoundExtensions(
            HttpStatus.NOT_FOUND.value(),
            TimetablesApiErrorType.StagingVehicleScheduleFrameNotFoundError,
            ex.stagingVehicleScheduleFrameId
        )
    }
}

data class TargetVehicleScheduleFrameNotFoundExtensions(
    override val code: Int,
    override val type: TimetablesApiErrorType,
    val stagingVehicleScheduleFrameId: UUID
) : JoreErrorExtensions {

    companion object {
        fun from(ex: TargetFrameNotFoundException) = TargetVehicleScheduleFrameNotFoundExtensions(
            HttpStatus.NOT_FOUND.value(),
            TimetablesApiErrorType.TargetVehicleScheduleFrameNotFoundError,
            ex.stagingVehicleScheduleFrameId
        )
    }
}

data class MultipleTargetFramesFoundExtensions(
    override val code: Int,
    override val type: TimetablesApiErrorType,
    val stagingVehicleScheduleFrameId: UUID,
    val targetVehicleScheduleFrameIds: List<UUID>
) : JoreErrorExtensions {

    companion object {
        fun from(ex: MultipleTargetFramesFoundException) = MultipleTargetFramesFoundExtensions(
            HttpStatus.CONFLICT.value(),
            TimetablesApiErrorType.MultipleTargetFramesFoundError,
            ex.stagingVehicleScheduleFrameId,
            ex.targetVehicleScheduleFrameIds
        )
    }
}

data class TargetPriorityParsingExtensions(
    override val code: Int,
    override val type: TimetablesApiErrorType,
    val targetPriority: Int
) : JoreErrorExtensions {

    companion object {
        fun from(ex: TargetPriorityParsingException) = TargetPriorityParsingExtensions(
            HttpStatus.BAD_REQUEST.value(),
            TimetablesApiErrorType.TargetPriorityParsingError,
            ex.targetPriority
        )
    }
}

data class TransactionSystemExtensions(
    override val code: Int,
    override val type: TimetablesApiErrorType,
    val sqlErrorMessage: String
) : JoreErrorExtensions {

    companion object {
        fun from(ex: TransactionSystemException) = TransactionSystemExtensions(
            HttpStatus.CONFLICT.value(),
            resolveErrorType(ex),
            ex.cause?.message ?: "unknown error on transaction commit or rollback"
        )

        private fun resolveErrorType(ex: TransactionSystemException): TimetablesApiErrorType {
            val errorMessage = ex.cause?.message ?: "" // Should always be defined though.

            // Attempt to detect from the error message (because there's no other data really) which error case triggered the failure.
            // Mainly using either the triggering validation function name or some known part of the error message for this.
            return errorTypesWithMatchingSubstrings.find { it.second in errorMessage }?.first
                ?: TimetablesApiErrorType.TransactionSystemError
        }

        private val errorTypesWithMatchingSubstrings = listOf(
            TimetablesApiErrorType.PassingTimeStopPointMatchingOrderError to "passing times and their matching stop points must be in same order",
            TimetablesApiErrorType.PassingTimeFirstArrivalTimeError to "first passing time must not have arrival_time set",
            TimetablesApiErrorType.PassingTimeLastDepartureTimeError to "last passing time must not have departure_time set",
            TimetablesApiErrorType.PassingTimeNullError to "all passing time that are not first or last in the sequence must have both departure and arrival time defined",
            TimetablesApiErrorType.PassingTimesMixedJourneyPatternRefsError to "inconsistent journey_pattern_ref within vehicle journey, all timetabled_passing_times must reference same journey_pattern_ref as the vehicle_journey",
            TimetablesApiErrorType.ConflictingSchedulesError to "validate_queued_schedules_uniqueness",
            TimetablesApiErrorType.SequentialIntegrityError to "validate_service_sequential_integrity"
        )
    }
}
