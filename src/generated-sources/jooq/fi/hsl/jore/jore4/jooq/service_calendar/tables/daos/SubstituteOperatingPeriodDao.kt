/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables.daos


import fi.hsl.jore.jore4.jooq.AbstractSpringDAOImpl
import fi.hsl.jore.jore4.jooq.service_calendar.tables.SubstituteOperatingPeriod
import fi.hsl.jore.jore4.jooq.service_calendar.tables.records.SubstituteOperatingPeriodRecord

import java.util.UUID

import kotlin.collections.List

import org.jooq.Configuration
import org.springframework.stereotype.Repository


/**
 * Models substitute operating period that consists of substitute operating days
 * by line types.
 */
@Suppress("UNCHECKED_CAST")
@Repository
open class SubstituteOperatingPeriodDao(configuration: Configuration?) : AbstractSpringDAOImpl<SubstituteOperatingPeriodRecord, fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod, UUID>(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD, fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod::class.java, configuration) {

    /**
     * Create a new SubstituteOperatingPeriodDao without any configuration
     */
    constructor(): this(null)

    override fun getId(o: fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod): UUID? = o.substituteOperatingPeriodId

    /**
     * Fetch records that have <code>substitute_operating_period_id BETWEEN
     * lowerInclusive AND upperInclusive</code>
     */
    fun fetchRangeOfSubstituteOperatingPeriodId(lowerInclusive: UUID?, upperInclusive: UUID?): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod> = fetchRange(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.SUBSTITUTE_OPERATING_PERIOD_ID, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>substitute_operating_period_id IN
     * (values)</code>
     */
    fun fetchBySubstituteOperatingPeriodId(vararg values: UUID): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod> = fetch(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.SUBSTITUTE_OPERATING_PERIOD_ID, *values)

    /**
     * Fetch a unique record that has <code>substitute_operating_period_id =
     * value</code>
     */
    fun fetchOneBySubstituteOperatingPeriodId(value: UUID): fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod? = fetchOne(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.SUBSTITUTE_OPERATING_PERIOD_ID, value)

    /**
     * Fetch records that have <code>period_name BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfPeriodName(lowerInclusive: String, upperInclusive: String): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod> = fetchRange(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.PERIOD_NAME, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>period_name IN (values)</code>
     */
    fun fetchByPeriodName(vararg values: String): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod> = fetch(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.PERIOD_NAME, *values)

    /**
     * Fetch a unique record that has <code>period_name = value</code>
     */
    fun fetchOneByPeriodName(value: String): fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod? = fetchOne(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.PERIOD_NAME, value)

    /**
     * Fetch records that have <code>is_preset BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    fun fetchRangeOfIsPreset(lowerInclusive: Boolean?, upperInclusive: Boolean?): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod> = fetchRange(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.IS_PRESET, lowerInclusive, upperInclusive)

    /**
     * Fetch records that have <code>is_preset IN (values)</code>
     */
    fun fetchByIsPreset(vararg values: Boolean): List<fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod> = fetch(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD.IS_PRESET, *values.toTypedArray())
}
