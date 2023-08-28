/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_journey


import fi.hsl.jore.jore4.jooq.DefaultCatalog
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.GetVehicleSchedulesOnDate
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.JourneyType

import java.time.LocalDate
import java.util.UUID

import kotlin.collections.List

import org.jooq.Catalog
import org.jooq.Configuration
import org.jooq.Field
import org.jooq.Record
import org.jooq.Result
import org.jooq.Table
import org.jooq.impl.SchemaImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class VehicleJourney : SchemaImpl("vehicle_journey", DefaultCatalog.DEFAULT_CATALOG) {
    public companion object {

        /**
         * The reference instance of <code>vehicle_journey</code>
         */
        val VEHICLE_JOURNEY: VehicleJourney = VehicleJourney()
    }

    /**
     * The table <code>vehicle_journey.get_vehicle_schedules_on_date</code>.
     */
    val GET_VEHICLE_SCHEDULES_ON_DATE: GetVehicleSchedulesOnDate get() = GetVehicleSchedulesOnDate.GET_VEHICLE_SCHEDULES_ON_DATE

    /**
     * Call <code>vehicle_journey.get_vehicle_schedules_on_date</code>.
     */
    fun GET_VEHICLE_SCHEDULES_ON_DATE(
          configuration: Configuration
        , journeyPatternUuid: UUID?
        , observationDate: LocalDate?
    ): Result<Record> = configuration.dsl().selectFrom(fi.hsl.jore.jore4.jooq.vehicle_journey.tables.GetVehicleSchedulesOnDate.GET_VEHICLE_SCHEDULES_ON_DATE.call(
          journeyPatternUuid
        , observationDate
    )).fetch()

    /**
     * Get <code>vehicle_journey.get_vehicle_schedules_on_date</code> as a
     * table.
     */
    fun GET_VEHICLE_SCHEDULES_ON_DATE(
          journeyPatternUuid: UUID?
        , observationDate: LocalDate?
    ): GetVehicleSchedulesOnDate = fi.hsl.jore.jore4.jooq.vehicle_journey.tables.GetVehicleSchedulesOnDate.GET_VEHICLE_SCHEDULES_ON_DATE.call(
        journeyPatternUuid,
        observationDate
    )

    /**
     * Get <code>vehicle_journey.get_vehicle_schedules_on_date</code> as a
     * table.
     */
    fun GET_VEHICLE_SCHEDULES_ON_DATE(
          journeyPatternUuid: Field<UUID?>
        , observationDate: Field<LocalDate?>
    ): GetVehicleSchedulesOnDate = fi.hsl.jore.jore4.jooq.vehicle_journey.tables.GetVehicleSchedulesOnDate.GET_VEHICLE_SCHEDULES_ON_DATE.call(
        journeyPatternUuid,
        observationDate
    )

    /**
     * Enum table for defining allowed journey types.
     */
    val JOURNEY_TYPE: JourneyType get() = JourneyType.JOURNEY_TYPE

    /**
     * The planned movement of a public transport vehicle on a DAY TYPE from the
     * start point to the end point of a JOURNEY PATTERN on a specified ROUTE.
     * Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=3:1:1:831 
     */
    val VEHICLE_JOURNEY_ : fi.hsl.jore.jore4.jooq.vehicle_journey.tables.VehicleJourney get() = fi.hsl.jore.jore4.jooq.vehicle_journey.tables.VehicleJourney.VEHICLE_JOURNEY_

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        GetVehicleSchedulesOnDate.GET_VEHICLE_SCHEDULES_ON_DATE,
        JourneyType.JOURNEY_TYPE,
        fi.hsl.jore.jore4.jooq.vehicle_journey.tables.VehicleJourney.VEHICLE_JOURNEY_
    )
}
