/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables.records


import fi.hsl.jore.jore4.jooq.vehicle_service.tables.JourneyPatternsInVehicleService

import java.util.UUID

import org.jooq.Record2
import org.jooq.impl.UpdatableRecordImpl


/**
 * A denormalized table containing relationships between vehicle_services and
 * journey_patterns (via journey_pattern_ref.journey_pattern_id).
 *  Without this table this relationship could be found via vehicle_service
 * -&gt; block -&gt; vehicle_journey -&gt; journey_pattern_ref.
 *  Kept up to date with triggers, should not be updated manually.
 */
@Suppress("UNCHECKED_CAST")
open class JourneyPatternsInVehicleServiceRecord private constructor() : UpdatableRecordImpl<JourneyPatternsInVehicleServiceRecord>(JourneyPatternsInVehicleService.JOURNEY_PATTERNS_IN_VEHICLE_SERVICE) {

    open var vehicleServiceId: UUID
        set(value): Unit = set(0, value)
        get(): UUID = get(0) as UUID

    open var journeyPatternId: UUID
        set(value): Unit = set(1, value)
        get(): UUID = get(1) as UUID

    open var referenceCount: Int
        set(value): Unit = set(2, value)
        get(): Int = get(2) as Int

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record2<UUID?, UUID?> = super.key() as Record2<UUID?, UUID?>

    /**
     * Create a detached, initialised JourneyPatternsInVehicleServiceRecord
     */
    constructor(vehicleServiceId: UUID, journeyPatternId: UUID, referenceCount: Int): this() {
        this.vehicleServiceId = vehicleServiceId
        this.journeyPatternId = journeyPatternId
        this.referenceCount = referenceCount
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised JourneyPatternsInVehicleServiceRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos.JourneyPatternsInVehicleService?): this() {
        if (value != null) {
            this.vehicleServiceId = value.vehicleServiceId
            this.journeyPatternId = value.journeyPatternId
            this.referenceCount = value.referenceCount
            resetChangedOnNotNull()
        }
    }
}
