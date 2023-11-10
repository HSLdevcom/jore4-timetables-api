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

data class PlainStatusExtensions(override val code: Int) : HasuraErrorExtensions {

    constructor(httpStatus: HttpStatus) : this(httpStatus.value())
}

data class InvalidTargetPriorityExtensions(
    override val code: Int,
    val targetPriority: TimetablesPriority
) : HasuraErrorExtensions {

    companion object {
        fun from(ex: InvalidTargetPriorityException) = InvalidTargetPriorityExtensions(
            HttpStatus.BAD_REQUEST.value(),
            ex.targetPriority
        )
    }
}

data class StagingVehicleScheduleFrameNotFoundExtensions(
    override val code: Int,
    val stagingVehicleScheduleFrameId: UUID
) : HasuraErrorExtensions {

    companion object {
        fun from(ex: StagingVehicleScheduleFrameNotFoundException) = StagingVehicleScheduleFrameNotFoundExtensions(
            HttpStatus.NOT_FOUND.value(),
            ex.stagingVehicleScheduleFrameId
        )
    }
}

data class TargetVehicleScheduleFrameNotFoundExtensions(
    override val code: Int,
    val stagingVehicleScheduleFrameId: UUID
) : HasuraErrorExtensions {

    companion object {
        fun from(ex: TargetFrameNotFoundException) = TargetVehicleScheduleFrameNotFoundExtensions(
            HttpStatus.NOT_FOUND.value(),
            ex.stagingVehicleScheduleFrameId
        )
    }
}

data class MultipleTargetFramesFoundExtensions(
    override val code: Int,
    val stagingVehicleScheduleFrameId: UUID,
    val targetVehicleScheduleFrameIds: List<UUID>
) : HasuraErrorExtensions {

    companion object {
        fun from(ex: MultipleTargetFramesFoundException) = MultipleTargetFramesFoundExtensions(
            HttpStatus.CONFLICT.value(),
            ex.stagingVehicleScheduleFrameId,
            ex.targetVehicleScheduleFrameIds
        )
    }
}

data class TargetPriorityParsingExtensions(
    override val code: Int,
    val targetPriority: Int
) : HasuraErrorExtensions {

    companion object {
        fun from(ex: TargetPriorityParsingException) = TargetPriorityParsingExtensions(
            HttpStatus.BAD_REQUEST.value(),
            ex.targetPriority
        )
    }
}

data class TransactionSystemExtensions(
    override val code: Int,
    val sqlErrorMessage: String
) : HasuraErrorExtensions {

    companion object {
        fun from(ex: TransactionSystemException) = TransactionSystemExtensions(
            HttpStatus.CONFLICT.value(),
            ex.cause?.message ?: "unknown error on transaction commit or rollback"
        )
    }
}
