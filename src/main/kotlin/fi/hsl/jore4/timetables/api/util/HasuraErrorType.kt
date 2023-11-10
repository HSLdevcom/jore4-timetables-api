package fi.hsl.jore4.timetables.api.util

enum class HasuraErrorType {
    UnknownError,
    InvalidTargetPriorityError,
    MultipleTargetFramesFoundError,
    StagingVehicleScheduleFrameNotFoundError,
    TargetPriorityParsingError,
    TargetVehicleScheduleFrameNotFoundError,
    TransactionSystemError
}
