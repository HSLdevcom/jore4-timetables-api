/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.passing_times.tables.pojos


import java.io.Serializable
import java.util.UUID


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class GetPassingTimeOrderValidityData(
    var vehicleJourneyId: UUID? = null,
    var firstPassingTimeId: UUID? = null,
    var lastPassingTimeId: UUID? = null,
    var stopOrderIsValid: Boolean? = null,
    var coherentJourneyPatternRefs: Boolean? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: GetPassingTimeOrderValidityData = other as GetPassingTimeOrderValidityData
        if (this.vehicleJourneyId == null) {
            if (o.vehicleJourneyId != null)
                return false
        }
        else if (this.vehicleJourneyId != o.vehicleJourneyId)
            return false
        if (this.firstPassingTimeId == null) {
            if (o.firstPassingTimeId != null)
                return false
        }
        else if (this.firstPassingTimeId != o.firstPassingTimeId)
            return false
        if (this.lastPassingTimeId == null) {
            if (o.lastPassingTimeId != null)
                return false
        }
        else if (this.lastPassingTimeId != o.lastPassingTimeId)
            return false
        if (this.stopOrderIsValid == null) {
            if (o.stopOrderIsValid != null)
                return false
        }
        else if (this.stopOrderIsValid != o.stopOrderIsValid)
            return false
        if (this.coherentJourneyPatternRefs == null) {
            if (o.coherentJourneyPatternRefs != null)
                return false
        }
        else if (this.coherentJourneyPatternRefs != o.coherentJourneyPatternRefs)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.vehicleJourneyId == null) 0 else this.vehicleJourneyId.hashCode())
        result = prime * result + (if (this.firstPassingTimeId == null) 0 else this.firstPassingTimeId.hashCode())
        result = prime * result + (if (this.lastPassingTimeId == null) 0 else this.lastPassingTimeId.hashCode())
        result = prime * result + (if (this.stopOrderIsValid == null) 0 else this.stopOrderIsValid.hashCode())
        result = prime * result + (if (this.coherentJourneyPatternRefs == null) 0 else this.coherentJourneyPatternRefs.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("GetPassingTimeOrderValidityData (")

        sb.append(vehicleJourneyId)
        sb.append(", ").append(firstPassingTimeId)
        sb.append(", ").append(lastPassingTimeId)
        sb.append(", ").append(stopOrderIsValid)
        sb.append(", ").append(coherentJourneyPatternRefs)

        sb.append(")")
        return sb.toString()
    }
}
