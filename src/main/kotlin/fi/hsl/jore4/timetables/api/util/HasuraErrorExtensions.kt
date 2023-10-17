package fi.hsl.jore4.timetables.api.util

import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import org.springframework.http.HttpStatus
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

    constructor(httpStatus: HttpStatus, targetPriority: TimetablesPriority) : this(
        httpStatus.value(),
        targetPriority
    )
}

data class StagingVehicleScheduleFrameNotFoundExtensions(
    override val code: Int,
    val stagingVehicleScheduleFrameId: UUID
) : HasuraErrorExtensions {

    constructor(httpStatus: HttpStatus, stagingVehicleScheduleFrameId: UUID) : this(
        httpStatus.value(),
        stagingVehicleScheduleFrameId
    )
}

data class TargetVehicleScheduleFrameNotFoundExtensions(
    override val code: Int,
    val stagingVehicleScheduleFrameId: UUID
) : HasuraErrorExtensions {

    constructor(httpStatus: HttpStatus, stagingVehicleScheduleFrameId: UUID) : this(
        httpStatus.value(),
        stagingVehicleScheduleFrameId
    )
}

data class MultipleTargetFramesFoundExtensions(
    override val code: Int,
    val stagingVehicleScheduleFrameId: UUID,
    val targetVehicleScheduleFrameIds: List<UUID>
) : HasuraErrorExtensions {

    constructor(
        httpStatus: HttpStatus,
        stagingVehicleScheduleFrameId: UUID,
        targetVehicleScheduleFrameIds: List<UUID>
    ) : this(
        httpStatus.value(),
        stagingVehicleScheduleFrameId,
        targetVehicleScheduleFrameIds
    )
}
