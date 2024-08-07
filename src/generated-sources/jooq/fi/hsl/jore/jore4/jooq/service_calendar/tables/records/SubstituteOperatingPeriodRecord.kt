/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables.records


import fi.hsl.jore.jore4.jooq.service_calendar.tables.SubstituteOperatingPeriod

import java.util.UUID

import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl


/**
 * Models substitute operating period that consists of substitute operating days
 * by line types.
 */
@Suppress("UNCHECKED_CAST")
open class SubstituteOperatingPeriodRecord private constructor() : UpdatableRecordImpl<SubstituteOperatingPeriodRecord>(SubstituteOperatingPeriod.SUBSTITUTE_OPERATING_PERIOD) {

    open var substituteOperatingPeriodId: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var periodName: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    @Suppress("INAPPLICABLE_JVM_NAME")
    @set:JvmName("setIsPreset")
    open var isPreset: Boolean?
        set(value): Unit = set(2, value)
        get(): Boolean? = get(2) as Boolean?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    /**
     * Create a detached, initialised SubstituteOperatingPeriodRecord
     */
    constructor(substituteOperatingPeriodId: UUID? = null, periodName: String, isPreset: Boolean? = null): this() {
        this.substituteOperatingPeriodId = substituteOperatingPeriodId
        this.periodName = periodName
        this.isPreset = isPreset
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised SubstituteOperatingPeriodRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.SubstituteOperatingPeriod?): this() {
        if (value != null) {
            this.substituteOperatingPeriodId = value.substituteOperatingPeriodId
            this.periodName = value.periodName
            this.isPreset = value.isPreset
            resetChangedOnNotNull()
        }
    }
}
