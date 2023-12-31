/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables.records


import fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService

import java.util.UUID

import org.jooq.Field
import org.jooq.JSONB
import org.jooq.Record1
import org.jooq.Record4
import org.jooq.Row4
import org.jooq.impl.UpdatableRecordImpl


/**
 * A work plan for a single vehicle for a whole day, planned for a specific DAY
 * TYPE. A VEHICLE SERVICE includes one or several BLOCKs. If there is no
 * service on a given day, it does not include any BLOCKs. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=3:5:965 
 */
@Suppress("UNCHECKED_CAST")
open class VehicleServiceRecord private constructor() : UpdatableRecordImpl<VehicleServiceRecord>(VehicleService.VEHICLE_SERVICE_), Record4<UUID?, UUID?, UUID?, JSONB?> {

    open var vehicleServiceId: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var dayTypeId: UUID
        set(value): Unit = set(1, value)
        get(): UUID = get(1) as UUID

    open var vehicleScheduleFrameId: UUID
        set(value): Unit = set(2, value)
        get(): UUID = get(2) as UUID

    open var nameI18n: JSONB?
        set(value): Unit = set(3, value)
        get(): JSONB? = get(3) as JSONB?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row4<UUID?, UUID?, UUID?, JSONB?> = super.fieldsRow() as Row4<UUID?, UUID?, UUID?, JSONB?>
    override fun valuesRow(): Row4<UUID?, UUID?, UUID?, JSONB?> = super.valuesRow() as Row4<UUID?, UUID?, UUID?, JSONB?>
    override fun field1(): Field<UUID?> = VehicleService.VEHICLE_SERVICE_.VEHICLE_SERVICE_ID
    override fun field2(): Field<UUID?> = VehicleService.VEHICLE_SERVICE_.DAY_TYPE_ID
    override fun field3(): Field<UUID?> = VehicleService.VEHICLE_SERVICE_.VEHICLE_SCHEDULE_FRAME_ID
    override fun field4(): Field<JSONB?> = VehicleService.VEHICLE_SERVICE_.NAME_I18N
    override fun component1(): UUID? = vehicleServiceId
    override fun component2(): UUID = dayTypeId
    override fun component3(): UUID = vehicleScheduleFrameId
    override fun component4(): JSONB? = nameI18n
    override fun value1(): UUID? = vehicleServiceId
    override fun value2(): UUID = dayTypeId
    override fun value3(): UUID = vehicleScheduleFrameId
    override fun value4(): JSONB? = nameI18n

    override fun value1(value: UUID?): VehicleServiceRecord {
        set(0, value)
        return this
    }

    override fun value2(value: UUID?): VehicleServiceRecord {
        set(1, value)
        return this
    }

    override fun value3(value: UUID?): VehicleServiceRecord {
        set(2, value)
        return this
    }

    override fun value4(value: JSONB?): VehicleServiceRecord {
        set(3, value)
        return this
    }

    override fun values(value1: UUID?, value2: UUID?, value3: UUID?, value4: JSONB?): VehicleServiceRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        return this
    }

    /**
     * Create a detached, initialised VehicleServiceRecord
     */
    constructor(vehicleServiceId: UUID? = null, dayTypeId: UUID, vehicleScheduleFrameId: UUID, nameI18n: JSONB? = null): this() {
        this.vehicleServiceId = vehicleServiceId
        this.dayTypeId = dayTypeId
        this.vehicleScheduleFrameId = vehicleScheduleFrameId
        this.nameI18n = nameI18n
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised VehicleServiceRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.VehicleService?): this() {
        if (value != null) {
            this.vehicleServiceId = value.vehicleServiceId
            this.dayTypeId = value.dayTypeId
            this.vehicleScheduleFrameId = value.vehicleScheduleFrameId
            this.nameI18n = value.nameI18n
            resetChangedOnNotNull()
        }
    }
}
