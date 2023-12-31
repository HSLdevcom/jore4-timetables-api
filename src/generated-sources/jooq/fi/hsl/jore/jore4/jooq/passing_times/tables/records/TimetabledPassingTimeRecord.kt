/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.passing_times.tables.records


import fi.hsl.jore.jore4.jooq.passing_times.tables.TimetabledPassingTime

import java.util.UUID

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record5
import org.jooq.Row5
import org.jooq.impl.UpdatableRecordImpl
import org.jooq.types.YearToSecond


/**
 * Long-term planned time data concerning public transport vehicles passing a
 * particular POINT IN JOURNEY PATTERN on a specified VEHICLE JOURNEY for a
 * certain DAY TYPE. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=3:4:946 
 */
@Suppress("UNCHECKED_CAST")
open class TimetabledPassingTimeRecord private constructor() : UpdatableRecordImpl<TimetabledPassingTimeRecord>(TimetabledPassingTime.TIMETABLED_PASSING_TIME), Record5<UUID?, UUID?, UUID?, YearToSecond?, YearToSecond?> {

    open var timetabledPassingTimeId: UUID?
        set(value): Unit = set(0, value)
        get(): UUID? = get(0) as UUID?

    open var vehicleJourneyId: UUID
        set(value): Unit = set(1, value)
        get(): UUID = get(1) as UUID

    open var scheduledStopPointInJourneyPatternRefId: UUID
        set(value): Unit = set(2, value)
        get(): UUID = get(2) as UUID

    open var arrivalTime: YearToSecond?
        set(value): Unit = set(3, value)
        get(): YearToSecond? = get(3) as YearToSecond?

    open var departureTime: YearToSecond?
        set(value): Unit = set(4, value)
        get(): YearToSecond? = get(4) as YearToSecond?

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    override fun key(): Record1<UUID?> = super.key() as Record1<UUID?>

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    override fun fieldsRow(): Row5<UUID?, UUID?, UUID?, YearToSecond?, YearToSecond?> = super.fieldsRow() as Row5<UUID?, UUID?, UUID?, YearToSecond?, YearToSecond?>
    override fun valuesRow(): Row5<UUID?, UUID?, UUID?, YearToSecond?, YearToSecond?> = super.valuesRow() as Row5<UUID?, UUID?, UUID?, YearToSecond?, YearToSecond?>
    override fun field1(): Field<UUID?> = TimetabledPassingTime.TIMETABLED_PASSING_TIME.TIMETABLED_PASSING_TIME_ID
    override fun field2(): Field<UUID?> = TimetabledPassingTime.TIMETABLED_PASSING_TIME.VEHICLE_JOURNEY_ID
    override fun field3(): Field<UUID?> = TimetabledPassingTime.TIMETABLED_PASSING_TIME.SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_ID
    override fun field4(): Field<YearToSecond?> = TimetabledPassingTime.TIMETABLED_PASSING_TIME.ARRIVAL_TIME
    override fun field5(): Field<YearToSecond?> = TimetabledPassingTime.TIMETABLED_PASSING_TIME.DEPARTURE_TIME
    override fun component1(): UUID? = timetabledPassingTimeId
    override fun component2(): UUID = vehicleJourneyId
    override fun component3(): UUID = scheduledStopPointInJourneyPatternRefId
    override fun component4(): YearToSecond? = arrivalTime
    override fun component5(): YearToSecond? = departureTime
    override fun value1(): UUID? = timetabledPassingTimeId
    override fun value2(): UUID = vehicleJourneyId
    override fun value3(): UUID = scheduledStopPointInJourneyPatternRefId
    override fun value4(): YearToSecond? = arrivalTime
    override fun value5(): YearToSecond? = departureTime

    override fun value1(value: UUID?): TimetabledPassingTimeRecord {
        set(0, value)
        return this
    }

    override fun value2(value: UUID?): TimetabledPassingTimeRecord {
        set(1, value)
        return this
    }

    override fun value3(value: UUID?): TimetabledPassingTimeRecord {
        set(2, value)
        return this
    }

    override fun value4(value: YearToSecond?): TimetabledPassingTimeRecord {
        set(3, value)
        return this
    }

    override fun value5(value: YearToSecond?): TimetabledPassingTimeRecord {
        set(4, value)
        return this
    }

    override fun values(value1: UUID?, value2: UUID?, value3: UUID?, value4: YearToSecond?, value5: YearToSecond?): TimetabledPassingTimeRecord {
        this.value1(value1)
        this.value2(value2)
        this.value3(value3)
        this.value4(value4)
        this.value5(value5)
        return this
    }

    /**
     * Create a detached, initialised TimetabledPassingTimeRecord
     */
    constructor(timetabledPassingTimeId: UUID? = null, vehicleJourneyId: UUID, scheduledStopPointInJourneyPatternRefId: UUID, arrivalTime: YearToSecond? = null, departureTime: YearToSecond? = null): this() {
        this.timetabledPassingTimeId = timetabledPassingTimeId
        this.vehicleJourneyId = vehicleJourneyId
        this.scheduledStopPointInJourneyPatternRefId = scheduledStopPointInJourneyPatternRefId
        this.arrivalTime = arrivalTime
        this.departureTime = departureTime
        resetChangedOnNotNull()
    }

    /**
     * Create a detached, initialised TimetabledPassingTimeRecord
     */
    constructor(value: fi.hsl.jore.jore4.jooq.passing_times.tables.pojos.TimetabledPassingTime?): this() {
        if (value != null) {
            this.timetabledPassingTimeId = value.timetabledPassingTimeId
            this.vehicleJourneyId = value.vehicleJourneyId
            this.scheduledStopPointInJourneyPatternRefId = value.scheduledStopPointInJourneyPatternRefId
            this.arrivalTime = value.arrivalTime
            this.departureTime = value.departureTime
            resetChangedOnNotNull()
        }
    }
}
