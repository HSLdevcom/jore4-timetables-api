/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables.daos


import fi.hsl.jore.jore4.jooq.AbstractSpringDAOImpl
import fi.hsl.jore.jore4.jooq.service_calendar.tables.SubstituteOperatingDayByLineType
import fi.hsl.jore.jore4.jooq.service_calendar.tables.records.SubstituteOperatingDayByLineTypeRecord

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

import kotlin.collections.List

import org.jooq.Configuration
import org.jooq.types.YearToSecond
import org.springframework.stereotype.Repository


/**
 * Models substitute public transit as (1) a reference day or (2) indicating
 * that public transit does not occur on certain date. Substitute operating days
 * are always bound to a type of line.
 */
@Suppress("UNCHECKED_CAST")
@Repository
open class SubstituteOperatingDayByLineTypeDao(configuration: Configuration?) : AbstractSpringDAOImpl<SubstituteOperatingDayByLineTypeRecord, fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType, UUID>(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE, fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType::class.java, configuration) {

    /**
     * Create a new SubstituteOperatingDayByLineTypeDao without any
     * configuration
     */
    constructor(): this(null)

    override fun getId(o: fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType): UUID? = o.substituteOperatingDayByLineTypeId

    /**
     * Fetch records that have <code>substitute_operating_day_by_line_type_id
     * BETWEEN lowerInclusive AND upperInclusive</code>
     */
    fun fetchRangeOfSubstituteOperatingDayByLineTypeId(lowerInclusive: UUID?, upperInclusive: UUID?): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetchRange(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>substitute_operating_day_by_line_type_id IN
     * (values)</code>
     */
    fun fetchBySubstituteOperatingDayByLineTypeId(vararg values: UUID): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetch(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID, *values)

    /**
     * Fetch a unique record that has
     * <code>substitute_operating_day_by_line_type_id = value</code>
     */
    fun fetchOneBySubstituteOperatingDayByLineTypeId(value: UUID): fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType? = fetchOne(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID, value)

    /**
     * Fetch records that have <code>type_of_line BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfTypeOfLine(lowerInclusive: String, upperInclusive: String): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetchRange(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.TYPE_OF_LINE, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>type_of_line IN (values)</code>
     */
    fun fetchByTypeOfLine(vararg values: String): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetch(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.TYPE_OF_LINE, *values)

    /**
     * Fetch records that have <code>superseded_date BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfSupersededDate(lowerInclusive: LocalDate, upperInclusive: LocalDate): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetchRange(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUPERSEDED_DATE, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>superseded_date IN (values)</code>
     */
    fun fetchBySupersededDate(vararg values: LocalDate): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetch(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUPERSEDED_DATE, *values)

    /**
     * Fetch records that have <code>substitute_day_of_week BETWEEN
     * lowerInclusive AND upperInclusive</code>
     */
    fun fetchRangeOfSubstituteDayOfWeek(lowerInclusive: Int?, upperInclusive: Int?): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetchRange(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUBSTITUTE_DAY_OF_WEEK, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>substitute_day_of_week IN (values)</code>
     */
    fun fetchBySubstituteDayOfWeek(vararg values: Int): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetch(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUBSTITUTE_DAY_OF_WEEK, *values.toTypedArray())

    /**
     * Fetch records that have <code>begin_time BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfBeginTime(lowerInclusive: YearToSecond?, upperInclusive: YearToSecond?): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetchRange(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.BEGIN_TIME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>begin_time IN (values)</code>
     */
    fun fetchByBeginTime(vararg values: YearToSecond): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetch(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.BEGIN_TIME, *values)

    /**
     * Fetch records that have <code>end_time BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfEndTime(lowerInclusive: YearToSecond?, upperInclusive: YearToSecond?): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetchRange(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.END_TIME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>end_time IN (values)</code>
     */
    fun fetchByEndTime(vararg values: YearToSecond): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetch(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.END_TIME, *values)

    /**
     * Fetch records that have <code>timezone BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfTimezone(lowerInclusive: String?, upperInclusive: String?): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetchRange(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.TIMEZONE, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>timezone IN (values)</code>
     */
    fun fetchByTimezone(vararg values: String): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetch(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.TIMEZONE, *values)

    /**
     * Fetch records that have <code>substitute_operating_period_id BETWEEN
     * lowerInclusive AND upperInclusive</code>
     */
    fun fetchRangeOfSubstituteOperatingPeriodId(lowerInclusive: UUID, upperInclusive: UUID): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetchRange(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUBSTITUTE_OPERATING_PERIOD_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>substitute_operating_period_id IN
     * (values)</code>
     */
    fun fetchBySubstituteOperatingPeriodId(vararg values: UUID): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetch(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.SUBSTITUTE_OPERATING_PERIOD_ID, *values)

    /**
     * Fetch records that have <code>created_at BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfCreatedAt(lowerInclusive: OffsetDateTime?, upperInclusive: OffsetDateTime?): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetchRange(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.CREATED_AT, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>created_at IN (values)</code>
     */
    fun fetchByCreatedAt(vararg values: OffsetDateTime): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingDayByLineType> = fetch(SubstituteOperatingDayByLineType.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE.CREATED_AT, *values)
}