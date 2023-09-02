/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.passing_times.tables.pojos


import java.io.Serializable
import java.util.UUID

import org.jooq.types.YearToSecond


/**
 * Long-term planned time data concerning public transport vehicles passing a
 * particular POINT IN JOURNEY PATTERN on a specified VEHICLE JOURNEY for a
 * certain DAY TYPE. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=3:4:946 
 */
@Suppress("UNCHECKED_CAST")
data class TimetabledPassingTime(
    var timetabledPassingTimeId: UUID? = null,
    var vehicleJourneyId: UUID,
    var scheduledStopPointInJourneyPatternRefId: UUID,
    var arrivalTime: YearToSecond? = null,
    var departureTime: YearToSecond? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: TimetabledPassingTime = other as TimetabledPassingTime
        if (this.timetabledPassingTimeId == null) {
            if (o.timetabledPassingTimeId != null)
                return false
        }
        else if (this.timetabledPassingTimeId != o.timetabledPassingTimeId)
            return false
        if (this.vehicleJourneyId != o.vehicleJourneyId)
            return false
        if (this.scheduledStopPointInJourneyPatternRefId != o.scheduledStopPointInJourneyPatternRefId)
            return false
        if (this.arrivalTime == null) {
            if (o.arrivalTime != null)
                return false
        }
        else if (this.arrivalTime != o.arrivalTime)
            return false
        if (this.departureTime == null) {
            if (o.departureTime != null)
                return false
        }
        else if (this.departureTime != o.departureTime)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.timetabledPassingTimeId == null) 0 else this.timetabledPassingTimeId.hashCode())
        result = prime * result + this.vehicleJourneyId.hashCode()
        result = prime * result + this.scheduledStopPointInJourneyPatternRefId.hashCode()
        result = prime * result + (if (this.arrivalTime == null) 0 else this.arrivalTime.hashCode())
        result = prime * result + (if (this.departureTime == null) 0 else this.departureTime.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("TimetabledPassingTime (")

        sb.append(timetabledPassingTimeId)
        sb.append(", ").append(vehicleJourneyId)
        sb.append(", ").append(scheduledStopPointInJourneyPatternRefId)
        sb.append(", ").append(arrivalTime)
        sb.append(", ").append(departureTime)

        sb.append(")")
        return sb.toString()
    }
}
