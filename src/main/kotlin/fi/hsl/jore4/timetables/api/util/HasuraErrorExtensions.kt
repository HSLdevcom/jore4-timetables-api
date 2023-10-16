package fi.hsl.jore4.timetables.api.util

import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import org.springframework.http.HttpStatus
import java.util.UUID

sealed class HasuraErrorExtensions(httpStatus: HttpStatus) {
    // code must be 4xx
    val code: Int = httpStatus.value()
}

class PlainStatusExtensions(httpStatus: HttpStatus) : HasuraErrorExtensions(httpStatus)

class InvalidTargetPriorityExtensions(
    httpStatus: HttpStatus,
    val targetPriority: TimetablesPriority
) : HasuraErrorExtensions(httpStatus)

class StagingVehicleScheduleFrameNotFoundExtensions(
    httpStatus: HttpStatus,
    val stagingVehicleScheduleFrameId: UUID
) : HasuraErrorExtensions(httpStatus)

class TargetVehicleScheduleFrameNotFoundExtensions(
    httpStatus: HttpStatus,
    val stagingVehicleScheduleFrameId: UUID
) : HasuraErrorExtensions(httpStatus)

class MultipleTargetFramesFoundExtensions(
    httpStatus: HttpStatus,
    val stagingVehicleScheduleFrameId: UUID,
    val targetVehicleScheduleFrameIds: List<UUID>
) : HasuraErrorExtensions(httpStatus)

class TargetPriorityParsingExtensions(
    httpStatus: HttpStatus,
    val targetPriority: Int
) : HasuraErrorExtensions(httpStatus)
