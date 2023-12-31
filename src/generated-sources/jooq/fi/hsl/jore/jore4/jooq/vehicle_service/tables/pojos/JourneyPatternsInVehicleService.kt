/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos


import java.io.Serializable
import java.util.UUID


/**
 * A denormalized table containing relationships between vehicle_services and
 * journey_patterns (via journey_pattern_ref.journey_pattern_id).
 *  Without this table this relationship could be found via vehicle_service
 * -&gt; block -&gt; vehicle_journey -&gt; journey_pattern_ref.
 *  Kept up to date with triggers, should not be updated manually.
 */
@Suppress("UNCHECKED_CAST")
data class JourneyPatternsInVehicleService(
    var vehicleServiceId: UUID,
    var journeyPatternId: UUID,
    var referenceCount: Int
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: JourneyPatternsInVehicleService = other as JourneyPatternsInVehicleService
        if (this.vehicleServiceId != o.vehicleServiceId)
            return false
        if (this.journeyPatternId != o.journeyPatternId)
            return false
        if (this.referenceCount != o.referenceCount)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + this.vehicleServiceId.hashCode()
        result = prime * result + this.journeyPatternId.hashCode()
        result = prime * result + this.referenceCount.hashCode()
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("JourneyPatternsInVehicleService (")

        sb.append(vehicleServiceId)
        sb.append(", ").append(journeyPatternId)
        sb.append(", ").append(referenceCount)

        sb.append(")")
        return sb.toString()
    }
}
