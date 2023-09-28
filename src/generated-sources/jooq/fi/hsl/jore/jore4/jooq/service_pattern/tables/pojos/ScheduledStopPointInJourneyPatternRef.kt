/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_pattern.tables.pojos


import java.io.Serializable
import java.util.UUID


/**
 * Reference the a SCHEDULED STOP POINT within a JOURNEY PATTERN. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=2:3:4:729 
 */
@Suppress("UNCHECKED_CAST")
data class ScheduledStopPointInJourneyPatternRef(
    var scheduledStopPointInJourneyPatternRefId: UUID? = null,
    var journeyPatternRefId: UUID,
    var scheduledStopPointLabel: String,
    var scheduledStopPointSequence: Int
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: ScheduledStopPointInJourneyPatternRef = other as ScheduledStopPointInJourneyPatternRef
        if (this.scheduledStopPointInJourneyPatternRefId == null) {
            if (o.scheduledStopPointInJourneyPatternRefId != null)
                return false
        }
        else if (this.scheduledStopPointInJourneyPatternRefId != o.scheduledStopPointInJourneyPatternRefId)
            return false
        if (this.journeyPatternRefId != o.journeyPatternRefId)
            return false
        if (this.scheduledStopPointLabel != o.scheduledStopPointLabel)
            return false
        if (this.scheduledStopPointSequence != o.scheduledStopPointSequence)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.scheduledStopPointInJourneyPatternRefId == null) 0 else this.scheduledStopPointInJourneyPatternRefId.hashCode())
        result = prime * result + this.journeyPatternRefId.hashCode()
        result = prime * result + this.scheduledStopPointLabel.hashCode()
        result = prime * result + this.scheduledStopPointSequence.hashCode()
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("ScheduledStopPointInJourneyPatternRef (")

        sb.append(scheduledStopPointInJourneyPatternRefId)
        sb.append(", ").append(journeyPatternRefId)
        sb.append(", ").append(scheduledStopPointLabel)
        sb.append(", ").append(scheduledStopPointSequence)

        sb.append(")")
        return sb.toString()
    }
}