/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq


import fi.hsl.jore.jore4.jooq.information_schema.InformationSchema
import fi.hsl.jore.jore4.jooq.internal_utils.InternalUtils
import fi.hsl.jore.jore4.jooq.journey_pattern.JourneyPattern
import fi.hsl.jore.jore4.jooq.passing_times.PassingTimes
import fi.hsl.jore.jore4.jooq.pg_catalog.PgCatalog
import fi.hsl.jore.jore4.jooq.return_value.ReturnValue
import fi.hsl.jore.jore4.jooq.route.Route
import fi.hsl.jore.jore4.jooq.service_calendar.ServiceCalendar
import fi.hsl.jore.jore4.jooq.service_pattern.ServicePattern
import fi.hsl.jore.jore4.jooq.vehicle_journey.VehicleJourney
import fi.hsl.jore.jore4.jooq.vehicle_schedule.VehicleSchedule
import fi.hsl.jore.jore4.jooq.vehicle_service.VehicleService
import fi.hsl.jore.jore4.jooq.vehicle_type.VehicleType

import kotlin.collections.List

import org.jooq.Schema
import org.jooq.impl.CatalogImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class DefaultCatalog : CatalogImpl("") {
    companion object {

        /**
         * The reference instance of <code>DEFAULT_CATALOG</code>
         */
        public val DEFAULT_CATALOG: DefaultCatalog = DefaultCatalog()
    }

    /**
     * The schema <code>information_schema</code>.
     */
    val INFORMATION_SCHEMA: InformationSchema get(): InformationSchema = InformationSchema.INFORMATION_SCHEMA

    /**
     * The schema <code>internal_utils</code>.
     */
    val INTERNAL_UTILS: InternalUtils get(): InternalUtils = InternalUtils.INTERNAL_UTILS

    /**
     * The schema <code>journey_pattern</code>.
     */
    val JOURNEY_PATTERN: JourneyPattern get(): JourneyPattern = JourneyPattern.JOURNEY_PATTERN

    /**
     * The schema <code>passing_times</code>.
     */
    val PASSING_TIMES: PassingTimes get(): PassingTimes = PassingTimes.PASSING_TIMES

    /**
     * The schema <code>pg_catalog</code>.
     */
    val PG_CATALOG: PgCatalog get(): PgCatalog = PgCatalog.PG_CATALOG

    /**
     * The schema <code>public</code>.
     */
    val PUBLIC: fi.hsl.jore.jore4.jooq.`public`.Public get(): fi.hsl.jore.jore4.jooq.`public`.Public = fi.hsl.jore.jore4.jooq.`public`.Public.PUBLIC

    /**
     * The schema <code>return_value</code>.
     */
    val RETURN_VALUE: ReturnValue get(): ReturnValue = ReturnValue.RETURN_VALUE

    /**
     * The schema <code>route</code>.
     */
    val ROUTE: Route get(): Route = Route.ROUTE

    /**
     * The schema <code>service_calendar</code>.
     */
    val SERVICE_CALENDAR: ServiceCalendar get(): ServiceCalendar = ServiceCalendar.SERVICE_CALENDAR

    /**
     * The schema <code>service_pattern</code>.
     */
    val SERVICE_PATTERN: ServicePattern get(): ServicePattern = ServicePattern.SERVICE_PATTERN

    /**
     * The schema <code>vehicle_journey</code>.
     */
    val VEHICLE_JOURNEY: VehicleJourney get(): VehicleJourney = VehicleJourney.VEHICLE_JOURNEY

    /**
     * The schema <code>vehicle_schedule</code>.
     */
    val VEHICLE_SCHEDULE: VehicleSchedule get(): VehicleSchedule = VehicleSchedule.VEHICLE_SCHEDULE

    /**
     * The schema <code>vehicle_service</code>.
     */
    val VEHICLE_SERVICE: VehicleService get(): VehicleService = VehicleService.VEHICLE_SERVICE

    /**
     * The schema <code>vehicle_type</code>.
     */
    val VEHICLE_TYPE: VehicleType get(): VehicleType = VehicleType.VEHICLE_TYPE

    override fun getSchemas(): List<Schema> = listOf(
        InformationSchema.INFORMATION_SCHEMA,
        InternalUtils.INTERNAL_UTILS,
        JourneyPattern.JOURNEY_PATTERN,
        PassingTimes.PASSING_TIMES,
        PgCatalog.PG_CATALOG,
        fi.hsl.jore.jore4.jooq.`public`.Public.PUBLIC,
        ReturnValue.RETURN_VALUE,
        Route.ROUTE,
        ServiceCalendar.SERVICE_CALENDAR,
        ServicePattern.SERVICE_PATTERN,
        VehicleJourney.VEHICLE_JOURNEY,
        VehicleSchedule.VEHICLE_SCHEDULE,
        VehicleService.VEHICLE_SERVICE,
        VehicleType.VEHICLE_TYPE
    )
}
