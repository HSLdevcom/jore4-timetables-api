/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables.records


import fi.hsl.jore.jore4.jooq.service_calendar.tables.DayTypeActiveOnDayOfWeek

import java.util.UUID

import org.jooq.Field
import org.jooq.Record2
import org.jooq.Row2
import org.jooq.impl.UpdatableRecordImpl


/**
 * Tells on which days of week a particular DAY TYPE is active
 */
@Suppress("UNCHECKED_CAST")
open class DayTypeActiveOnDayOfWeekRecord private constructor() : UpdatableRecordImpl<DayTypeActiveOnDayOfWeekRecord>(DayTypeActiveOnDayOfWeek.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK), Record2<UUID?, Int?> {

    open var dayTypeId: UUID
        set(value): Unit = set(0, value)
        get(): UUID = get(0) as UUID

    open var dayOfWeek: Int
        set(value): Unit = set(1, value)
        get(): Int = get(1) as Int

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record2<UUID?, Int?> = super.key() as Record2<UUID?, Int?>

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row2<UUID?, Int?> = super.fieldsRow() as Row2<UUID?, Int?>
    override fun valuesRow(): Row2<UUID?, Int?> = super.valuesRow() as Row2<UUID?, Int?>
    override fun field1(): Field<UUID?> = DayTypeActiveOnDayOfWeek.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK.DAY_TYPE_ID
    override fun field2(): Field<Int?> = DayTypeActiveOnDayOfWeek.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK.DAY_OF_WEEK
    override fun component1(): UUID = dayTypeId
    override fun component2(): Int = dayOfWeek
    override fun value1(): UUID = dayTypeId
    override fun value2(): Int = dayOfWeek

    override fun value1(value: UUID?): DayTypeActiveOnDayOfWeekRecord {
        set(0, value)
        return this
    }

    override fun value2(value: Int?): DayTypeActiveOnDayOfWeekRecord {
        set(1, value)
        return this
    }

    override fun values(value1: UUID?, value2: Int?): DayTypeActiveOnDayOfWeekRecord {
        this.value1(value1)
        this.value2(value2)
        return this
    }

    /**
     * Create a detached, initialised DayTypeActiveOnDayOfWeekRecord
     */
    constructor(dayTypeId: UUID, dayOfWeek: Int): this() {
        this.dayTypeId = dayTypeId
        this.dayOfWeek = dayOfWeek
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised DayTypeActiveOnDayOfWeekRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos.DayTypeActiveOnDayOfWeek?): this() {
        if (value != null) {
            this.dayTypeId = value.dayTypeId
            this.dayOfWeek = value.dayOfWeek
            resetChangedOnNotNull()
        }
    }
}