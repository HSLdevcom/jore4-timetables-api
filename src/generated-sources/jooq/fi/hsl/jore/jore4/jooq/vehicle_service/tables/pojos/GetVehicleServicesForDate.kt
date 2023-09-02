/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos


import java.io.Serializable
import java.util.UUID

import org.jooq.JSONB


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class GetVehicleServicesForDate(
    var vehicleServiceId: UUID? = null,
    var dayTypeId: UUID,
    var vehicleScheduleFrameId: UUID,
    var nameI18n: JSONB? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: GetVehicleServicesForDate = other as GetVehicleServicesForDate
        if (this.vehicleServiceId == null) {
            if (o.vehicleServiceId != null)
                return false
        }
        else if (this.vehicleServiceId != o.vehicleServiceId)
            return false
        if (this.dayTypeId != o.dayTypeId)
            return false
        if (this.vehicleScheduleFrameId != o.vehicleScheduleFrameId)
            return false
        if (this.nameI18n == null) {
            if (o.nameI18n != null)
                return false
        }
        else if (this.nameI18n != o.nameI18n)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.vehicleServiceId == null) 0 else this.vehicleServiceId.hashCode())
        result = prime * result + this.dayTypeId.hashCode()
        result = prime * result + this.vehicleScheduleFrameId.hashCode()
        result = prime * result + (if (this.nameI18n == null) 0 else this.nameI18n.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("GetVehicleServicesForDate (")

        sb.append(vehicleServiceId)
        sb.append(", ").append(dayTypeId)
        sb.append(", ").append(vehicleScheduleFrameId)
        sb.append(", ").append(nameI18n)

        sb.append(")")
        return sb.toString()
    }
}
