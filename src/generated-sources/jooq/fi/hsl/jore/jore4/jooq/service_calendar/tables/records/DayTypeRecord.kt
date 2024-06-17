/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables.records


import fi.hsl.jore.jore4.jooq.service_calendar.tables.DayType

import java.util.UUID

import org.jooq.JSONB
import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl


/**
 * A type of day characterised by one or more properties which affect public
 * transport operation. For example: weekday in school holidays. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=1:6:3:299 
 */
@Suppress("UNCHECKED_CAST")
open class DayTypeRecord private constructor() : UpdatableRecordImpl<DayTypeRecord>(DayType.DAY_TYPE) {

    open var dayTypeId: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var label: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    open var nameI18n: JSONB
        set(value): Unit = set(2, value)
        get(): JSONB = get(2) as JSONB

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    /**
     * Create a detached, initialised DayTypeRecord
     */
    constructor(dayTypeId: UUID? = null, label: String, nameI18n: JSONB): this() {
        this.dayTypeId = dayTypeId
        this.label = label
        this.nameI18n = nameI18n
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised DayTypeRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.DayType?): this() {
        if (value != null) {
            this.dayTypeId = value.dayTypeId
            this.label = value.label
            this.nameI18n = value.nameI18n
            resetChangedOnNotNull()
        }
    }
}
