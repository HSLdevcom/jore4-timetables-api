/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables.pojos


import java.io.Serializable
import java.util.UUID


/**
 * Tells on which days of week a particular DAY TYPE is active
 */
@Suppress("UNCHECKED_CAST")
data class DayTypeActiveOnDayOfWeek(
    var dayTypeId: UUID,
    var dayOfWeek: Int
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: DayTypeActiveOnDayOfWeek = other as DayTypeActiveOnDayOfWeek
        if (this.dayTypeId != o.dayTypeId)
            return false
        if (this.dayOfWeek != o.dayOfWeek)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + this.dayTypeId.hashCode()
        result = prime * result + this.dayOfWeek.hashCode()
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("DayTypeActiveOnDayOfWeek (")

        sb.append(dayTypeId)
        sb.append(", ").append(dayOfWeek)

        sb.append(")")
        return sb.toString()
    }
}
