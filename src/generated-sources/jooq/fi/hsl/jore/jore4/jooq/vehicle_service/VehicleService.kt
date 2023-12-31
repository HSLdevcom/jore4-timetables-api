/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service


import fi.hsl.jore.jore4.jooq.DefaultCatalog
import fi.hsl.jore.jore4.jooq.return_value.tables.records.TimetableVersionRecord
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.Block
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetableVersionsByJourneyPatternIds
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetablesAndSubstituteOperatingDays
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetVehicleServiceTimingData
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.JourneyPatternsInVehicleService
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.records.GetVehicleServiceTimingDataRecord

import java.time.LocalDate
import java.util.UUID

import kotlin.collections.List

import org.jooq.Catalog
import org.jooq.Configuration
import org.jooq.Field
import org.jooq.Result
import org.jooq.Table
import org.jooq.impl.SchemaImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class VehicleService : SchemaImpl("vehicle_service", DefaultCatalog.DEFAULT_CATALOG) {
    public companion object {

        /**
         * The reference instance of <code>vehicle_service</code>
         */
        val VEHICLE_SERVICE: VehicleService = VehicleService()
    }

    /**
     * The work of a vehicle from the time it leaves a PARKING POINT after
     * parking until its next return to park at a PARKING POINT. Any subsequent
     * departure from a PARKING POINT after parking marks the start of a new
     * BLOCK. The period of a BLOCK has to be covered by DUTies. Transmodel:
     * https://www.transmodel-cen.eu/model/index.htm?goto=3:5:958 
     */
    val BLOCK: Block get() = Block.BLOCK

    /**
     * The table
     * <code>vehicle_service.get_timetable_versions_by_journey_pattern_ids</code>.
     */
    val GET_TIMETABLE_VERSIONS_BY_JOURNEY_PATTERN_IDS: GetTimetableVersionsByJourneyPatternIds get() = GetTimetableVersionsByJourneyPatternIds.GET_TIMETABLE_VERSIONS_BY_JOURNEY_PATTERN_IDS

    /**
     * Call
     * <code>vehicle_service.get_timetable_versions_by_journey_pattern_ids</code>.
     */
    fun GET_TIMETABLE_VERSIONS_BY_JOURNEY_PATTERN_IDS(
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
     * <code>vehicle_service.get_timetable_versions_by_journey_pattern_ids</code>
     * as a table.
     */
    fun GET_TIMETABLE_VERSIONS_BY_JOURNEY_PATTERN_IDS(
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
     * <code>vehicle_service.get_timetable_versions_by_journey_pattern_ids</code>
     * as a table.
     */
    fun GET_TIMETABLE_VERSIONS_BY_JOURNEY_PATTERN_IDS(
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
     * The table
     * <code>vehicle_service.get_timetables_and_substitute_operating_days</code>.
     */
    val GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS: GetTimetablesAndSubstituteOperatingDays get() = GetTimetablesAndSubstituteOperatingDays.GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS

    /**
     * Call
     * <code>vehicle_service.get_timetables_and_substitute_operating_days</code>.
     */
    fun GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS(
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
     * Get
     * <code>vehicle_service.get_timetables_and_substitute_operating_days</code>
     * as a table.
     */
    fun GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS(
          journeyPatternIds: Array<UUID?>?
        , startDate: LocalDate?
        , endDate: LocalDate?
    ): GetTimetablesAndSubstituteOperatingDays = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetablesAndSubstituteOperatingDays.GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS.call(
        journeyPatternIds,
        startDate,
        endDate
    )

    /**
     * Get
     * <code>vehicle_service.get_timetables_and_substitute_operating_days</code>
     * as a table.
     */
    fun GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS(
          journeyPatternIds: Field<Array<UUID?>?>
        , startDate: Field<LocalDate?>
        , endDate: Field<LocalDate?>
    ): GetTimetablesAndSubstituteOperatingDays = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetTimetablesAndSubstituteOperatingDays.GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS.call(
        journeyPatternIds,
        startDate,
        endDate
    )

    /**
     * The table <code>vehicle_service.get_vehicle_service_timing_data</code>.
     */
    val GET_VEHICLE_SERVICE_TIMING_DATA: GetVehicleServiceTimingData get() = GetVehicleServiceTimingData.GET_VEHICLE_SERVICE_TIMING_DATA

    /**
     * Call <code>vehicle_service.get_vehicle_service_timing_data</code>.
     */
    fun GET_VEHICLE_SERVICE_TIMING_DATA(
          configuration: Configuration
        , vehicleServiceIds: Array<UUID?>?
    ): Result<GetVehicleServiceTimingDataRecord> = configuration.dsl().selectFrom(fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetVehicleServiceTimingData.GET_VEHICLE_SERVICE_TIMING_DATA.call(
          vehicleServiceIds
    )).fetch()

    /**
     * Get <code>vehicle_service.get_vehicle_service_timing_data</code> as a
     * table.
     */
    fun GET_VEHICLE_SERVICE_TIMING_DATA(
          vehicleServiceIds: Array<UUID?>?
    ): GetVehicleServiceTimingData = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetVehicleServiceTimingData.GET_VEHICLE_SERVICE_TIMING_DATA.call(
        vehicleServiceIds
    )

    /**
     * Get <code>vehicle_service.get_vehicle_service_timing_data</code> as a
     * table.
     */
    fun GET_VEHICLE_SERVICE_TIMING_DATA(
          vehicleServiceIds: Field<Array<UUID?>?>
    ): GetVehicleServiceTimingData = fi.hsl.jore.jore4.jooq.vehicle_service.tables.GetVehicleServiceTimingData.GET_VEHICLE_SERVICE_TIMING_DATA.call(
        vehicleServiceIds
    )

    /**
     * A denormalized table containing relationships between vehicle_services
     * and journey_patterns (via journey_pattern_ref.journey_pattern_id).
     *  Without this table this relationship could be found via vehicle_service
     * -&gt; block -&gt; vehicle_journey -&gt; journey_pattern_ref.
     *  Kept up to date with triggers, should not be updated manually.
     */
    val JOURNEY_PATTERNS_IN_VEHICLE_SERVICE: JourneyPatternsInVehicleService get() = JourneyPatternsInVehicleService.JOURNEY_PATTERNS_IN_VEHICLE_SERVICE

    /**
     * A work plan for a single vehicle for a whole day, planned for a specific
     * DAY TYPE. A VEHICLE SERVICE includes one or several BLOCKs. If there is
     * no service on a given day, it does not include any BLOCKs. Transmodel:
     * https://www.transmodel-cen.eu/model/index.htm?goto=3:5:965 
     */
    val VEHICLE_SERVICE_ : fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService get() = fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService.VEHICLE_SERVICE_

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        Block.BLOCK,
        GetTimetableVersionsByJourneyPatternIds.GET_TIMETABLE_VERSIONS_BY_JOURNEY_PATTERN_IDS,
        GetTimetablesAndSubstituteOperatingDays.GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS,
        GetVehicleServiceTimingData.GET_VEHICLE_SERVICE_TIMING_DATA,
        JourneyPatternsInVehicleService.JOURNEY_PATTERNS_IN_VEHICLE_SERVICE,
        fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService.VEHICLE_SERVICE_
    )
}
