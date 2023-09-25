/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos


import java.io.Serializable
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

import org.jooq.types.YearToSecond


/**
 * Models substitute public transit as (1) a reference day or (2) indicating
 * that public transit does not occur on certain date. Substitute operating days
 * are always bound to a type of line.
 */
@Suppress("UNCHECKED_CAST")
data class SubstituteOperatingDayByLineType(
    var substituteOperatingDayByLineTypeId: UUID? = null,
    var typeOfLine: String,
    var supersededDate: LocalDate,
    var substituteDayOfWeek: Int? = null,
    var beginTime: YearToSecond? = null,
    var endTime: YearToSecond? = null,
    var timezone: String? = null,
    var substituteOperatingPeriodId: UUID,
    var createdAt: OffsetDateTime? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: SubstituteOperatingDayByLineType = other as SubstituteOperatingDayByLineType
        if (this.substituteOperatingDayByLineTypeId == null) {
            if (o.substituteOperatingDayByLineTypeId != null)
                return false
        }
        else if (this.substituteOperatingDayByLineTypeId != o.substituteOperatingDayByLineTypeId)
            return false
        if (this.typeOfLine != o.typeOfLine)
            return false
        if (this.supersededDate != o.supersededDate)
            return false
        if (this.substituteDayOfWeek == null) {
            if (o.substituteDayOfWeek != null)
                return false
        }
        else if (this.substituteDayOfWeek != o.substituteDayOfWeek)
            return false
        if (this.beginTime == null) {
            if (o.beginTime != null)
                return false
        }
        else if (this.beginTime != o.beginTime)
            return false
        if (this.endTime == null) {
            if (o.endTime != null)
                return false
        }
        else if (this.endTime != o.endTime)
            return false
        if (this.timezone == null) {
            if (o.timezone != null)
                return false
        }
        else if (this.timezone != o.timezone)
            return false
        if (this.substituteOperatingPeriodId != o.substituteOperatingPeriodId)
            return false
        if (this.createdAt == null) {
            if (o.createdAt != null)
                return false
        }
        else if (this.createdAt != o.createdAt)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.substituteOperatingDayByLineTypeId == null) 0 else this.substituteOperatingDayByLineTypeId.hashCode())
        result = prime * result + this.typeOfLine.hashCode()
        result = prime * result + this.supersededDate.hashCode()
        result = prime * result + (if (this.substituteDayOfWeek == null) 0 else this.substituteDayOfWeek.hashCode())
        result = prime * result + (if (this.beginTime == null) 0 else this.beginTime.hashCode())
        result = prime * result + (if (this.endTime == null) 0 else this.endTime.hashCode())
        result = prime * result + (if (this.timezone == null) 0 else this.timezone.hashCode())
        result = prime * result + this.substituteOperatingPeriodId.hashCode()
        result = prime * result + (if (this.createdAt == null) 0 else this.createdAt.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("SubstituteOperatingDayByLineType (")

        sb.append(substituteOperatingDayByLineTypeId)
        sb.append(", ").append(typeOfLine)
        sb.append(", ").append(supersededDate)
        sb.append(", ").append(substituteDayOfWeek)
        sb.append(", ").append(beginTime)
        sb.append(", ").append(endTime)
        sb.append(", ").append(timezone)
        sb.append(", ").append(substituteOperatingPeriodId)
        sb.append(", ").append(createdAt)

        sb.append(")")
        return sb.toString()
    }
}