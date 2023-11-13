package fi.hsl.jore4.timetables.api.util

enum class HasuraErrorType {
    UnknownError,
    ConflictingSchedulesError,
    InvalidTargetPriorityError,
    MultipleTargetFramesFoundError,
    PassingTimeFirstArrivalTimeError,
    PassingTimeLastDepartureTimeError,
    PassingTimeNullError,
    PassingTimeStopPointMatchingOrderError,
    PassingTimesMixedJourneyPatternRefsError,
    SequentialIntegrityError,
    StagingVehicleScheduleFrameNotFoundError,
    TargetPriorityParsingError,
    TargetVehicleScheduleFrameNotFoundError,
    TransactionSystemError
}
