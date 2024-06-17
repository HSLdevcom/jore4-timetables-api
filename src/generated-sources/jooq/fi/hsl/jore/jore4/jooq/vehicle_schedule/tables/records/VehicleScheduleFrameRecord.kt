/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.records


import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

import org.jooq.JSONB
import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl


/**
 * A coherent set of BLOCKS, COMPOUND BLOCKs, COURSEs of JOURNEY and VEHICLE
 * SCHEDULEs to which the same set of VALIDITY CONDITIONs have been assigned.
 * Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=3:7:2:993 
 */
@Suppress("UNCHECKED_CAST")
open class VehicleScheduleFrameRecord private constructor() : UpdatableRecordImpl<VehicleScheduleFrameRecord>(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME) {

    open var vehicleScheduleFrameId: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var nameI18n: JSONB?
        set(value): Unit = set(1, value)
        get(): JSONB? = get(1) as JSONB?

    open var validityStart: LocalDate
        set(value): Unit = set(2, value)
        get(): LocalDate = get(2) as LocalDate

    open var validityEnd: LocalDate
        set(value): Unit = set(3, value)
        get(): LocalDate = get(3) as LocalDate

    open var priority: Int
        set(value): Unit = set(4, value)
        get(): Int = get(4) as Int

    open var label: String
        set(value): Unit = set(5, value)
        get(): String = get(5) as String

    open var bookingLabel: String?
        set(value): Unit = set(6, value)
        get(): String? = get(6) as String?

    open var bookingDescriptionI18n: JSONB?
        set(value): Unit = set(7, value)
        get(): JSONB? = get(7) as JSONB?

    open var createdAt: OffsetDateTime?
        set(value): Unit = set(8, value)
        get(): OffsetDateTime? = get(8) as OffsetDateTime?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    /**
     * Create a detached, initialised VehicleScheduleFrameRecord
     */
    constructor(vehicleScheduleFrameId: UUID? = null, nameI18n: JSONB? = null, validityStart: LocalDate, validityEnd: LocalDate, priority: Int, label: String, bookingLabel: String? = null, bookingDescriptionI18n: JSONB? = null, createdAt: OffsetDateTime? = null): this() {
        this.vehicleScheduleFrameId = vehicleScheduleFrameId
        this.nameI18n = nameI18n
        this.validityStart = validityStart
        this.validityEnd = validityEnd
        this.priority = priority
        this.label = label
        this.bookingLabel = bookingLabel
        this.bookingDescriptionI18n = bookingDescriptionI18n
        this.createdAt = createdAt
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised VehicleScheduleFrameRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame?): this() {
        if (value != null) {
            this.vehicleScheduleFrameId = value.vehicleScheduleFrameId
            this.nameI18n = value.nameI18n
            this.validityStart = value.validityStart
            this.validityEnd = value.validityEnd
            this.priority = value.priority
            this.label = value.label
            this.bookingLabel = value.bookingLabel
            this.bookingDescriptionI18n = value.bookingDescriptionI18n
            this.createdAt = value.createdAt
            resetChangedOnNotNull()
        }
    }
}
