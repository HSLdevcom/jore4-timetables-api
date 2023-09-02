/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_type.tables.pojos


import java.io.Serializable
import java.util.UUID

import org.jooq.JSONB


/**
 * The VEHICLE entity is used to describe the physical public transport vehicles
 * available for short-term planning of operations and daily assignment (in
 * contrast to logical vehicles considered for resource planning of operations
 * and daily assignment (in contrast to logical vehicles cplanning). Each
 * VEHICLE shall be classified as of a particular VEHICLE TYPE.
 */
@Suppress("UNCHECKED_CAST")
data class VehicleType(
    var vehicleTypeId: UUID? = null,
    var label: String,
    var descriptionI18n: JSONB? = null,
    var hslId: Short
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: VehicleType = other as VehicleType
        if (this.vehicleTypeId == null) {
            if (o.vehicleTypeId != null)
                return false
        }
        else if (this.vehicleTypeId != o.vehicleTypeId)
            return false
        if (this.label != o.label)
            return false
        if (this.descriptionI18n == null) {
            if (o.descriptionI18n != null)
                return false
        }
        else if (this.descriptionI18n != o.descriptionI18n)
            return false
        if (this.hslId != o.hslId)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.vehicleTypeId == null) 0 else this.vehicleTypeId.hashCode())
        result = prime * result + this.label.hashCode()
        result = prime * result + (if (this.descriptionI18n == null) 0 else this.descriptionI18n.hashCode())
        result = prime * result + this.hslId.hashCode()
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("VehicleType (")

        sb.append(vehicleTypeId)
        sb.append(", ").append(label)
        sb.append(", ").append(descriptionI18n)
        sb.append(", ").append(hslId)

        sb.append(")")
        return sb.toString()
    }
}
