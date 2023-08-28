/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_schedule.routines.references


import fi.hsl.jore.jore4.jooq.vehicle_schedule.routines.ValidateQueuedSchedulesUniqueness
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.GetOverlappingSchedules

import java.util.UUID

import org.jooq.Configuration
import org.jooq.Field
import org.jooq.Record
import org.jooq.Result



/**
 * Call <code>vehicle_schedule.validate_queued_schedules_uniqueness</code>
 */
fun validateQueuedSchedulesUniqueness(
      configuration: Configuration
): Unit {
    val p = ValidateQueuedSchedulesUniqueness()

    p.execute(configuration)
}

/**
 * Call <code>vehicle_schedule.get_overlapping_schedules</code>.
 */
fun getOverlappingSchedules(
      configuration: Configuration
    , filterVehicleScheduleFrameIds: Array<UUID?>?
    , filterJourneyPatternRefIds: Array<UUID?>?
): Result<Record> = configuration.dsl().selectFrom(fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.GetOverlappingSchedules.GET_OVERLAPPING_SCHEDULES.call(
      filterVehicleScheduleFrameIds
    , filterJourneyPatternRefIds
)).fetch()

/**
 * Get <code>vehicle_schedule.get_overlapping_schedules</code> as a table.
 */
fun getOverlappingSchedules(
      filterVehicleScheduleFrameIds: Array<UUID?>?
    , filterJourneyPatternRefIds: Array<UUID?>?
): GetOverlappingSchedules = fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.GetOverlappingSchedules.GET_OVERLAPPING_SCHEDULES.call(
    filterVehicleScheduleFrameIds,
    filterJourneyPatternRefIds
)

/**
 * Get <code>vehicle_schedule.get_overlapping_schedules</code> as a table.
 */
fun getOverlappingSchedules(
      filterVehicleScheduleFrameIds: Field<Array<UUID?>?>
    , filterJourneyPatternRefIds: Field<Array<UUID?>?>
): GetOverlappingSchedules = fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.GetOverlappingSchedules.GET_OVERLAPPING_SCHEDULES.call(
    filterVehicleScheduleFrameIds,
    filterJourneyPatternRefIds
)
