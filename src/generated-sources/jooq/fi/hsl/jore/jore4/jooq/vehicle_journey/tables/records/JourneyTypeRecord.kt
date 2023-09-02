/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_journey.tables.records


import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.JourneyType

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Row1
import org.jooq.impl.UpdatableRecordImpl


/**
 * Enum table for defining allowed journey types.
 */
@Suppress("UNCHECKED_CAST")
open class JourneyTypeRecord private constructor() : UpdatableRecordImpl<JourneyTypeRecord>(JourneyType.JOURNEY_TYPE), Record1<String?> {

    open var type: String
        set(value): Unit = set(0, value)
        get(): String = get(0) as String

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<String?> = super.key() as Record1<String?>

    // -------------------------------------------------------------------------
    // Record1 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row1<String?> = super.fieldsRow() as Row1<String?>
    override fun valuesRow(): Row1<String?> = super.valuesRow() as Row1<String?>
    override fun field1(): Field<String?> = JourneyType.JOURNEY_TYPE.TYPE
    override fun component1(): String = type
    override fun value1(): String = type

    override fun value1(value: String?): JourneyTypeRecord {
        set(0, value)
        return this
    }

    override fun values(value1: String?): JourneyTypeRecord {
        this.value1(value1)
        return this
    }

    /**
     * Create a detached, initialised JourneyTypeRecord
     */
    constructor(type: String): this() {
        this.type = type
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised JourneyTypeRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.vehicle_journey.tables.pojos.JourneyType?): this() {
        if (value != null) {
            this.type = value.type
            resetChangedOnNotNull()
        }
    }
}
