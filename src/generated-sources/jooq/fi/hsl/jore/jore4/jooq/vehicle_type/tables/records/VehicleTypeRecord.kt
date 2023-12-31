/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_type.tables.records


import fi.hsl.jore.jore4.jooq.vehicle_type.tables.VehicleType

import java.util.UUID

import org.jooq.Field
import org.jooq.JSONB
import org.jooq.Record1
import org.jooq.Record4
import org.jooq.Row4
import org.jooq.impl.UpdatableRecordImpl


/**
 * The VEHICLE entity is used to describe the physical public transport vehicles
 * available for short-term planning of operations and daily assignment (in
 * contrast to logical vehicles considered for resource planning of operations
 * and daily assignment (in contrast to logical vehicles cplanning). Each
 * VEHICLE shall be classified as of a particular VEHICLE TYPE.
 */
@Suppress("UNCHECKED_CAST")
open class VehicleTypeRecord private constructor() : UpdatableRecordImpl<VehicleTypeRecord>(VehicleType.VEHICLE_TYPE_), Record4<UUID?, String?, JSONB?, Short?> {

    open var vehicleTypeId: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var label: String
        set(value): Unit = set(1, value)
        get(): String = get(1) as String

    open var descriptionI18n: JSONB?
        set(value): Unit = set(2, value)
        get(): JSONB? = get(2) as JSONB?

    open var hslId: Short
        set(value): Unit = set(3, value)
        get(): Short = get(3) as Short

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row4<UUID?, String?, JSONB?, Short?> = super.fieldsRow() as Row4<UUID?, String?, JSONB?, Short?>
    override fun valuesRow(): Row4<UUID?, String?, JSONB?, Short?> = super.valuesRow() as Row4<UUID?, String?, JSONB?, Short?>
    override fun field1(): Field<UUID?> = VehicleType.VEHICLE_TYPE_.VEHICLE_TYPE_ID
    override fun field2(): Field<String?> = VehicleType.VEHICLE_TYPE_.LABEL
    override fun field3(): Field<JSONB?> = VehicleType.VEHICLE_TYPE_.DESCRIPTION_I18N
    override fun field4(): Field<Short?> = VehicleType.VEHICLE_TYPE_.HSL_ID
    override fun component1(): UUID? = vehicleTypeId
    override fun component2(): String = label
    override fun component3(): JSONB? = descriptionI18n
    override fun component4(): Short = hslId
    override fun value1(): UUID? = vehicleTypeId
    override fun value2(): String = label
    override fun value3(): JSONB? = descriptionI18n
    override fun value4(): Short = hslId

    override fun value1(value: UUID?): VehicleTypeRecord {
        set(0, value)
        return this
    }

    override fun value2(value: String?): VehicleTypeRecord {
        set(1, value)
        return this
    }

    override fun value3(value: JSONB?): VehicleTypeRecord {
        set(2, value)
        return this
    }

    override fun value4(value: Short?): VehicleTypeRecord {
        set(3, value)
        return this
    }

    override fun values(value1: UUID?, value2: String?, value3: JSONB?, value4: Short?): VehicleTypeRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        return this
    }

    /**
     * Create a detached, initialised VehicleTypeRecord
     */
    constructor(vehicleTypeId: UUID? = null, label: String, descriptionI18n: JSONB? = null, hslId: Short): this() {
        this.vehicleTypeId = vehicleTypeId
        this.label = label
        this.descriptionI18n = descriptionI18n
        this.hslId = hslId
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised VehicleTypeRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.vehicle_type.tables.pojos.VehicleType?): this() {
        if (value != null) {
            this.vehicleTypeId = value.vehicleTypeId
            this.label = value.label
            this.descriptionI18n = value.descriptionI18n
            this.hslId = value.hslId
            resetChangedOnNotNull()
        }
    }
}
