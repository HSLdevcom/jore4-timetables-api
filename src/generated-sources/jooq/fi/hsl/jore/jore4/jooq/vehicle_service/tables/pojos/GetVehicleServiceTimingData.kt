/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables.pojos


import java.io.Serializable
import java.util.UUID

import org.jooq.types.YearToSecond


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
data class GetVehicleServiceTimingData(
    var vehicleServiceId: UUID? = null,
    var serviceStart: YearToSecond? = null,
    var serviceEnd: YearToSecond? = null,
    var blockId: UUID? = null,
    var blockStart: YearToSecond? = null,
    var blockEnd: YearToSecond? = null,
    var preparingTime: YearToSecond? = null,
    var finishingTime: YearToSecond? = null,
    var vehicleJourneyId: UUID? = null,
    var journeyStart: YearToSecond? = null,
    var journeyEnd: YearToSecond? = null,
    var journeyFirstStopDeparture: YearToSecond? = null,
    var journeyLastStopArrival: YearToSecond? = null,
    var turnaroundTime: YearToSecond? = null,
    var layoverTime: YearToSecond? = null,
    var timetabledPassingTimeId: UUID? = null,
    var stopDepartureTime: YearToSecond? = null,
    var stopArrivalTime: YearToSecond? = null
): Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        if (other == null)
            return false
        if (this::class != other::class)
            return false
        val o: GetVehicleServiceTimingData = other as GetVehicleServiceTimingData
        if (this.vehicleServiceId == null) {
            if (o.vehicleServiceId != null)
                return false
        }
        else if (this.vehicleServiceId != o.vehicleServiceId)
            return false
        if (this.serviceStart == null) {
            if (o.serviceStart != null)
                return false
        }
        else if (this.serviceStart != o.serviceStart)
            return false
        if (this.serviceEnd == null) {
            if (o.serviceEnd != null)
                return false
        }
        else if (this.serviceEnd != o.serviceEnd)
            return false
        if (this.blockId == null) {
            if (o.blockId != null)
                return false
        }
        else if (this.blockId != o.blockId)
            return false
        if (this.blockStart == null) {
            if (o.blockStart != null)
                return false
        }
        else if (this.blockStart != o.blockStart)
            return false
        if (this.blockEnd == null) {
            if (o.blockEnd != null)
                return false
        }
        else if (this.blockEnd != o.blockEnd)
            return false
        if (this.preparingTime == null) {
            if (o.preparingTime != null)
                return false
        }
        else if (this.preparingTime != o.preparingTime)
            return false
        if (this.finishingTime == null) {
            if (o.finishingTime != null)
                return false
        }
        else if (this.finishingTime != o.finishingTime)
            return false
        if (this.vehicleJourneyId == null) {
            if (o.vehicleJourneyId != null)
                return false
        }
        else if (this.vehicleJourneyId != o.vehicleJourneyId)
            return false
        if (this.journeyStart == null) {
            if (o.journeyStart != null)
                return false
        }
        else if (this.journeyStart != o.journeyStart)
            return false
        if (this.journeyEnd == null) {
            if (o.journeyEnd != null)
                return false
        }
        else if (this.journeyEnd != o.journeyEnd)
            return false
        if (this.journeyFirstStopDeparture == null) {
            if (o.journeyFirstStopDeparture != null)
                return false
        }
        else if (this.journeyFirstStopDeparture != o.journeyFirstStopDeparture)
            return false
        if (this.journeyLastStopArrival == null) {
            if (o.journeyLastStopArrival != null)
                return false
        }
        else if (this.journeyLastStopArrival != o.journeyLastStopArrival)
            return false
        if (this.turnaroundTime == null) {
            if (o.turnaroundTime != null)
                return false
        }
        else if (this.turnaroundTime != o.turnaroundTime)
            return false
        if (this.layoverTime == null) {
            if (o.layoverTime != null)
                return false
        }
        else if (this.layoverTime != o.layoverTime)
            return false
        if (this.timetabledPassingTimeId == null) {
            if (o.timetabledPassingTimeId != null)
                return false
        }
        else if (this.timetabledPassingTimeId != o.timetabledPassingTimeId)
            return false
        if (this.stopDepartureTime == null) {
            if (o.stopDepartureTime != null)
                return false
        }
        else if (this.stopDepartureTime != o.stopDepartureTime)
            return false
        if (this.stopArrivalTime == null) {
            if (o.stopArrivalTime != null)
                return false
        }
        else if (this.stopArrivalTime != o.stopArrivalTime)
            return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + (if (this.vehicleServiceId == null) 0 else this.vehicleServiceId.hashCode())
        result = prime * result + (if (this.serviceStart == null) 0 else this.serviceStart.hashCode())
        result = prime * result + (if (this.serviceEnd == null) 0 else this.serviceEnd.hashCode())
        result = prime * result + (if (this.blockId == null) 0 else this.blockId.hashCode())
        result = prime * result + (if (this.blockStart == null) 0 else this.blockStart.hashCode())
        result = prime * result + (if (this.blockEnd == null) 0 else this.blockEnd.hashCode())
        result = prime * result + (if (this.preparingTime == null) 0 else this.preparingTime.hashCode())
        result = prime * result + (if (this.finishingTime == null) 0 else this.finishingTime.hashCode())
        result = prime * result + (if (this.vehicleJourneyId == null) 0 else this.vehicleJourneyId.hashCode())
        result = prime * result + (if (this.journeyStart == null) 0 else this.journeyStart.hashCode())
        result = prime * result + (if (this.journeyEnd == null) 0 else this.journeyEnd.hashCode())
        result = prime * result + (if (this.journeyFirstStopDeparture == null) 0 else this.journeyFirstStopDeparture.hashCode())
        result = prime * result + (if (this.journeyLastStopArrival == null) 0 else this.journeyLastStopArrival.hashCode())
        result = prime * result + (if (this.turnaroundTime == null) 0 else this.turnaroundTime.hashCode())
        result = prime * result + (if (this.layoverTime == null) 0 else this.layoverTime.hashCode())
        result = prime * result + (if (this.timetabledPassingTimeId == null) 0 else this.timetabledPassingTimeId.hashCode())
        result = prime * result + (if (this.stopDepartureTime == null) 0 else this.stopDepartureTime.hashCode())
        result = prime * result + (if (this.stopArrivalTime == null) 0 else this.stopArrivalTime.hashCode())
        return result
    }

    override fun toString(): String {
        val sb = StringBuilder("GetVehicleServiceTimingData (")

        sb.append(vehicleServiceId)
        sb.append(", ").append(serviceStart)
        sb.append(", ").append(serviceEnd)
        sb.append(", ").append(blockId)
        sb.append(", ").append(blockStart)
        sb.append(", ").append(blockEnd)
        sb.append(", ").append(preparingTime)
        sb.append(", ").append(finishingTime)
        sb.append(", ").append(vehicleJourneyId)
        sb.append(", ").append(journeyStart)
        sb.append(", ").append(journeyEnd)
        sb.append(", ").append(journeyFirstStopDeparture)
        sb.append(", ").append(journeyLastStopArrival)
        sb.append(", ").append(turnaroundTime)
        sb.append(", ").append(layoverTime)
        sb.append(", ").append(timetabledPassingTimeId)
        sb.append(", ").append(stopDepartureTime)
        sb.append(", ").append(stopArrivalTime)

        sb.append(")")
        return sb.toString()
    }
}