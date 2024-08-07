/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables.records


import fi.hsl.jore.jore4.jooq.vehicle_service.tables.Block

import java.util.UUID

import org.jooq.Record1
import org.jooq.impl.UpdatableRecordImpl
import org.jooq.types.YearToSecond


/**
 * The work of a vehicle from the time it leaves a PARKING POINT after parking
 * until its next return to park at a PARKING POINT. Any subsequent departure
 * from a PARKING POINT after parking marks the start of a new BLOCK. The period
 * of a BLOCK has to be covered by DUTies. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=3:5:958 
 */
@Suppress("UNCHECKED_CAST")
open class BlockRecord private constructor() : UpdatableRecordImpl<BlockRecord>(Block.BLOCK) {

    open var blockId: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var vehicleServiceId: UUID
        set(value): Unit = set(1, value)
        get(): UUID = get(1) as UUID

    open var preparingTime: YearToSecond?
        set(value): Unit = set(2, value)
        get(): YearToSecond? = get(2) as YearToSecond?

    open var finishingTime: YearToSecond?
        set(value): Unit = set(3, value)
        get(): YearToSecond? = get(3) as YearToSecond?

    open var vehicleTypeId: UUID?
        set(value): Unit = set(4, value)
        get(): UUID? = get(4) as UUID?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    /**
     * Create a detached, initialised BlockRecord
     */
    constructor(blockId: UUID? = null, vehicleServiceId: UUID, preparingTime: YearToSecond? = null, finishingTime: YearToSecond? = null, vehicleTypeId: UUID? = null): this() {
        this.blockId = blockId
        this.vehicleServiceId = vehicleServiceId
        this.preparingTime = preparingTime
        this.finishingTime = finishingTime
        this.vehicleTypeId = vehicleTypeId
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised BlockRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.Block?): this() {
        if (value != null) {
            this.blockId = value.blockId
            this.vehicleServiceId = value.vehicleServiceId
            this.preparingTime = value.preparingTime
            this.finishingTime = value.finishingTime
            this.vehicleTypeId = value.vehicleTypeId
            resetChangedOnNotNull()
        }
    }
}
