/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.passing_times.tables.daos


import fi.hsl.jore.jore4.jooq.AbstractSpringDAOImpl
import fi.hsl.jore.jore4.jooq.passing_times.tables.TimetabledPassingTime
import fi.hsl.jore.jore4.jooq.passing_times.tables.records.TimetabledPassingTimeRecord

import java.util.UUID

import kotlin.collections.List

import org.jooq.Configuration
import org.jooq.types.YearToSecond
import org.springframework.stereotype.Repository


/**
 * Long-term planned time data concerning public transport vehicles passing a
 * particular POINT IN JOURNEY PATTERN on a specified VEHICLE JOURNEY for a
 * certain DAY TYPE. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=3:4:946 
 */
@Suppress("UNCHECKED_CAST")
@Repository
open class TimetabledPassingTimeDao(configuration: Configuration?) : AbstractSpringDAOImpl<TimetabledPassingTimeRecord, fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime, UUID>(TimetabledPassingTime.TIMETABLED_PASSING_TIME, fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime::class.java, configuration) {

    /**
     * Create a new TimetabledPassingTimeDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime): UUID? = o.timetabledPassingTimeId

    /**
     * Fetch records that have <code>timetabled_passing_time_id BETWEEN
     * lowerInclusive AND upperInclusive</code>
     */
    fun fetchRangeOfTimetabledPassingTimeId(lowerInclusive: UUID?, upperInclusive: UUID?): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetchRange(TimetabledPassingTime.TIMETABLED_PASSING_TIME.TIMETABLED_PASSING_TIME_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>timetabled_passing_time_id IN
     * (values)</code>
     */
    fun fetchByTimetabledPassingTimeId(vararg values: UUID): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetch(TimetabledPassingTime.TIMETABLED_PASSING_TIME.TIMETABLED_PASSING_TIME_ID, *values)

    /**
     * Fetch a unique record that has <code>timetabled_passing_time_id =
     * value</code>
     */
    fun fetchOneByTimetabledPassingTimeId(value: UUID): fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime? = fetchOne(TimetabledPassingTime.TIMETABLED_PASSING_TIME.TIMETABLED_PASSING_TIME_ID, value)

    /**
     * Fetch records that have <code>vehicle_journey_id BETWEEN lowerInclusive
     * AND upperInclusive</code>
     */
    fun fetchRangeOfVehicleJourneyId(lowerInclusive: UUID, upperInclusive: UUID): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetchRange(TimetabledPassingTime.TIMETABLED_PASSING_TIME.VEHICLE_JOURNEY_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>vehicle_journey_id IN (values)</code>
     */
    fun fetchByVehicleJourneyId(vararg values: UUID): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetch(TimetabledPassingTime.TIMETABLED_PASSING_TIME.VEHICLE_JOURNEY_ID, *values)

    /**
     * Fetch records that have
     * <code>scheduled_stop_point_in_journey_pattern_ref_id BETWEEN
     * lowerInclusive AND upperInclusive</code>
     */
    fun fetchRangeOfScheduledStopPointInJourneyPatternRefId(lowerInclusive: UUID, upperInclusive: UUID): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetchRange(TimetabledPassingTime.TIMETABLED_PASSING_TIME.SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have
     * <code>scheduled_stop_point_in_journey_pattern_ref_id IN (values)</code>
     */
    fun fetchByScheduledStopPointInJourneyPatternRefId(vararg values: UUID): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetch(TimetabledPassingTime.TIMETABLED_PASSING_TIME.SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_ID, *values)

    /**
     * Fetch records that have <code>arrival_time BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfArrivalTime(lowerInclusive: YearToSecond?, upperInclusive: YearToSecond?): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetchRange(TimetabledPassingTime.TIMETABLED_PASSING_TIME.ARRIVAL_TIME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>arrival_time IN (values)</code>
     */
    fun fetchByArrivalTime(vararg values: YearToSecond): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetch(TimetabledPassingTime.TIMETABLED_PASSING_TIME.ARRIVAL_TIME, *values)

    /**
     * Fetch records that have <code>departure_time BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfDepartureTime(lowerInclusive: YearToSecond?, upperInclusive: YearToSecond?): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetchRange(TimetabledPassingTime.TIMETABLED_PASSING_TIME.DEPARTURE_TIME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>departure_time IN (values)</code>
     */
    fun fetchByDepartureTime(vararg values: YearToSecond): List<fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime> = fetch(TimetabledPassingTime.TIMETABLED_PASSING_TIME.DEPARTURE_TIME, *values)
}