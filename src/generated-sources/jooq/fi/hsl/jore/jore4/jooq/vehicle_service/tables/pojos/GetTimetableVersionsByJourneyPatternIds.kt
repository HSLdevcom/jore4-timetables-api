/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos


import java.io.Serializable
import java.time.LocalDate
import java.util.UUID


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class GetTimetableVersionsByJourneyPatternIds(
    var vehicleScheduleFrameId: UUID? = null,
    var substituteOperatingDayByLineTypeId: UUID? = null,
    var validityStart: LocalDate,
    var validityEnd: LocalDate,
    var priority: Int,
    var inEffect: Boolean,
    var dayTypeId: UUID
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: GetTimetableVersionsByJourneyPatternIds = other as GetTimetableVersionsByJourneyPatternIds
        if (this.vehicleScheduleFrameId == null) {
            if (o.vehicleScheduleFrameId != null)
                return false
        }
        else if (this.vehicleScheduleFrameId != o.vehicleScheduleFrameId)
            return false
        if (this.substituteOperatingDayByLineTypeId == null) {
            if (o.substituteOperatingDayByLineTypeId != null)
                return false
        }
        else if (this.substituteOperatingDayByLineTypeId != o.substituteOperatingDayByLineTypeId)
            return false
        if (this.validityStart != o.validityStart)
            return false
        if (this.validityEnd != o.validityEnd)
            return false
        if (this.priority != o.priority)
            return false
        if (this.inEffect != o.inEffect)
            return false
        if (this.dayTypeId != o.dayTypeId)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.vehicleScheduleFrameId == null) 0 else this.vehicleScheduleFrameId.hashCode())
        result = prime * result + (if (this.substituteOperatingDayByLineTypeId == null) 0 else this.substituteOperatingDayByLineTypeId.hashCode())
        result = prime * result + this.validityStart.hashCode()
        result = prime * result + this.validityEnd.hashCode()
        result = prime * result + this.priority.hashCode()
        result = prime * result + this.inEffect.hashCode()
        result = prime * result + this.dayTypeId.hashCode()
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("GetTimetableVersionsByJourneyPatternIds (")

        sb.append(vehicleScheduleFrameId)
        sb.append(", ").append(substituteOperatingDayByLineTypeId)
        sb.append(", ").append(validityStart)
        sb.append(", ").append(validityEnd)
        sb.append(", ").append(priority)
        sb.append(", ").append(inEffect)
        sb.append(", ").append(dayTypeId)

        sb.append(")")
        return sb.toString()
    }
}
