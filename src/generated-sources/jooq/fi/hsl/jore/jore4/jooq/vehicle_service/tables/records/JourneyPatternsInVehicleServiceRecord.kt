/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables.records


import fi.hsl.jore.jore4.jooq.vehicle_service.tables.JourneyPatternsInVehicleService

import java.util.UUID

import org.jooq.Field
import org.jooq.Record2
import org.jooq.Record3
import org.jooq.Row3
import org.jooq.impl.UpdatableRecordImpl


/**
 * A denormalized table containing relationships between vehicle_services and
 * journey_patterns (via journey_pattern_ref.journey_pattern_id).
 *  Without this table this relationship could be found via vehicle_service
 * -&gt; block -&gt; vehicle_journey -&gt; journey_pattern_ref.
 *  Kept up to date with triggers, should not be updated manually.
 */
@Suppress("UNCHECKED_CAST")
open class JourneyPatternsInVehicleServiceRecord() : UpdatableRecordImpl<JourneyPatternsInVehicleServiceRecord>(JourneyPatternsInVehicleService.JOURNEY_PATTERNS_IN_VEHICLE_SERVICE), Record3<UUID?, UUID?, Int?> {

    open var vehicleServiceId: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var journeyPatternId: UUID?
        set(value): Unit = set(1, value)
        get(): UUID? = get(1) as UUID?

    open var referenceCount: Int?
        set(value): Unit = set(2, value)
        get(): Int? = get(2) as Int?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record2<UUID?, UUID?> = super.key() as Record2<UUID?, UUID?>

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row3<UUID?, UUID?, Int?> = super.fieldsRow() as Row3<UUID?, UUID?, Int?>
    override fun valuesRow(): Row3<UUID?, UUID?, Int?> = super.valuesRow() as Row3<UUID?, UUID?, Int?>
    override fun field1(): Field<UUID?> = JourneyPatternsInVehicleService.JOURNEY_PATTERNS_IN_VEHICLE_SERVICE.VEHICLE_SERVICE_ID
    override fun field2(): Field<UUID?> = JourneyPatternsInVehicleService.JOURNEY_PATTERNS_IN_VEHICLE_SERVICE.JOURNEY_PATTERN_ID
    override fun field3(): Field<Int?> = JourneyPatternsInVehicleService.JOURNEY_PATTERNS_IN_VEHICLE_SERVICE.REFERENCE_COUNT
    override fun component1(): UUID? = vehicleServiceId
    override fun component2(): UUID? = journeyPatternId
    override fun component3(): Int? = referenceCount
    override fun value1(): UUID? = vehicleServiceId
    override fun value2(): UUID? = journeyPatternId
    override fun value3(): Int? = referenceCount

    override fun value1(value: UUID?): JourneyPatternsInVehicleServiceRecord {
        set(0, value)
        return this
    }

    override fun value2(value: UUID?): JourneyPatternsInVehicleServiceRecord {
        set(1, value)
        return this
    }

    override fun value3(value: Int?): JourneyPatternsInVehicleServiceRecord {
        set(2, value)
        return this
    }

    override fun values(value1: UUID?, value2: UUID?, value3: Int?): JourneyPatternsInVehicleServiceRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        return this
    }

    /**
     * Create a detached, initialised JourneyPatternsInVehicleServiceRecord
     */
    constructor(vehicleServiceId: UUID? = null, journeyPatternId: UUID? = null, referenceCount: Int? = null): this() {
        this.vehicleServiceId = vehicleServiceId
        this.journeyPatternId = journeyPatternId
        this.referenceCount = referenceCount
        resetChangedOnNotNull()
    }
}
