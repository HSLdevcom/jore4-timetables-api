/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.routines.references


import fi.hsl.jore.jore4.jooq.return_value.tables.records.TimetableVersionRecord
import fi.hsl.jore.jore4.jooq.vehicle_service.routines.RefreshJourneyPatternsInVehicleService
import fi.hsl.jore.jore4.jooq.vehicle_service.routines.ValidateServiceSequentialIntegrity
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetableVersionsByJourneyPatternIds
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetablesAndSubstituteOperatingDays
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetVehicleServiceTimingData
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.records.GetVehicleServiceTimingDataRecord

import java.time.LocalDate
import java.util.UUID

import org.jooq.Configuration
import org.jooq.Field
import org.jooq.Result



/**
 * Call <code>vehicle_service.refresh_journey_patterns_in_vehicle_service</code>
 */
fun refreshJourneyPatternsInVehicleService(
      configuration: Configuration
): Unit {
    val p = RefreshJourneyPatternsInVehicleService()

    p.execute(configuration)
}

/**
 * Call <code>vehicle_service.validate_service_sequential_integrity</code>
 */
fun validateServiceSequentialIntegrity(
      configuration: Configuration
): Unit {
    val p = ValidateServiceSequentialIntegrity()

    p.execute(configuration)
}

/**
 * Call
 * <code>vehicle_service.get_timetable_versions_by_journey_pattern_ids</code>.
 */
fun getTimetableVersionsByJourneyPatternIds(
      configuration: Configuration
    , journeyPatternIds: Array<UUID?>?
    , startDate: LocalDate?
    , endDate: LocalDate?
    , observationDate: LocalDate?
): Result<TimetableVersionRecord> = configuration.dsl().selectFrom(fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetableVersionsByJourneyPatternIds.GET_TIMETABLE_VERSIONS_BY_JOURNEY_PATTERN_IDS.call(
      journeyPatternIds
    , startDate
    , endDate
    , observationDate
)).fetch()

/**
 * Get
 * <code>vehicle_service.get_timetable_versions_by_journey_pattern_ids</code> as
 * a table.
 */
fun getTimetableVersionsByJourneyPatternIds(
      journeyPatternIds: Array<UUID?>?
    , startDate: LocalDate?
    , endDate: LocalDate?
    , observationDate: LocalDate?
): GetTimetableVersionsByJourneyPatternIds = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetableVersionsByJourneyPatternIds.GET_TIMETABLE_VERSIONS_BY_JOURNEY_PATTERN_IDS.call(
    journeyPatternIds,
    startDate,
    endDate,
    observationDate
)

/**
 * Get
 * <code>vehicle_service.get_timetable_versions_by_journey_pattern_ids</code> as
 * a table.
 */
fun getTimetableVersionsByJourneyPatternIds(
      journeyPatternIds: Field<Array<UUID?>?>
    , startDate: Field<LocalDate?>
    , endDate: Field<LocalDate?>
    , observationDate: Field<LocalDate?>
): GetTimetableVersionsByJourneyPatternIds = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetableVersionsByJourneyPatternIds.GET_TIMETABLE_VERSIONS_BY_JOURNEY_PATTERN_IDS.call(
    journeyPatternIds,
    startDate,
    endDate,
    observationDate
)

/**
 * Call
 * <code>vehicle_service.get_timetables_and_substitute_operating_days</code>.
 */
fun getTimetablesAndSubstituteOperatingDays(
      configuration: Configuration
    , journeyPatternIds: Array<UUID?>?
    , startDate: LocalDate?
    , endDate: LocalDate?
): Result<TimetableVersionRecord> = configuration.dsl().selectFrom(fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetablesAndSubstituteOperatingDays.GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS.call(
      journeyPatternIds
    , startDate
    , endDate
)).fetch()

/**
 * Get <code>vehicle_service.get_timetables_and_substitute_operating_days</code>
 * as a table.
 */
fun getTimetablesAndSubstituteOperatingDays(
      journeyPatternIds: Array<UUID?>?
    , startDate: LocalDate?
    , endDate: LocalDate?
): GetTimetablesAndSubstituteOperatingDays = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetablesAndSubstituteOperatingDays.GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS.call(
    journeyPatternIds,
    startDate,
    endDate
)

/**
 * Get <code>vehicle_service.get_timetables_and_substitute_operating_days</code>
 * as a table.
 */
fun getTimetablesAndSubstituteOperatingDays(
      journeyPatternIds: Field<Array<UUID?>?>
    , startDate: Field<LocalDate?>
    , endDate: Field<LocalDate?>
): GetTimetablesAndSubstituteOperatingDays = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetablesAndSubstituteOperatingDays.GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS.call(
    journeyPatternIds,
    startDate,
    endDate
)

/**
 * Call <code>vehicle_service.get_vehicle_service_timing_data</code>.
 */
fun getVehicleServiceTimingData(
      configuration: Configuration
    , vehicleServiceIds: Array<UUID?>?
): Result<GetVehicleServiceTimingDataRecord> = configuration.dsl().selectFrom(fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetVehicleServiceTimingData.GET_VEHICLE_SERVICE_TIMING_DATA.call(
      vehicleServiceIds
)).fetch()

/**
 * Get <code>vehicle_service.get_vehicle_service_timing_data</code> as a table.
 */
fun getVehicleServiceTimingData(
      vehicleServiceIds: Array<UUID?>?
): GetVehicleServiceTimingData = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetVehicleServiceTimingData.GET_VEHICLE_SERVICE_TIMING_DATA.call(
    vehicleServiceIds
)

/**
 * Get <code>vehicle_service.get_vehicle_service_timing_data</code> as a table.
 */
fun getVehicleServiceTimingData(
      vehicleServiceIds: Field<Array<UUID?>?>
): GetVehicleServiceTimingData = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetVehicleServiceTimingData.GET_VEHICLE_SERVICE_TIMING_DATA.call(
    vehicleServiceIds
)
