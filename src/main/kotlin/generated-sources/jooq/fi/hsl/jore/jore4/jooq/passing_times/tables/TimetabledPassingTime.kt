/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.passing_times.tables


import fi.hsl.jore.jore4.jooq.passing_times.PassingTimes

import java.util.UUID

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl
import org.jooq.types.YearToSecond


/**
 * Long-term planned time data concerning public transport vehicles passing a
 * particular POINT IN JOURNEY PATTERN on a specified VEHICLE JOURNEY for a
 * certain DAY TYPE. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=3:4:946 
 */
@Suppress("UNCHECKED_CAST")
open class TimetabledPassingTime(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, Record>?,
    aliased: Table<Record>?,
    parameters: Array<Field<*>?>?
): TableImpl<Record>(
    alias,
    PassingTimes.PASSING_TIMES,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("Long-term planned time data concerning public transport vehicles passing a particular POINT IN JOURNEY PATTERN on a specified VEHICLE JOURNEY for a certain DAY TYPE. Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=3:4:946 "),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of
         * <code>passing_times.timetabled_passing_time</code>
         */
        val TIMETABLED_PASSING_TIME: TimetabledPassingTime = TimetabledPassingTime()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column
     * <code>passing_times.timetabled_passing_time.timetabled_passing_time_id</code>.
     */
    val TIMETABLED_PASSING_TIME_ID: TableField<Record, UUID?> = createField(DSL.name("timetabled_passing_time_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field("gen_random_uuid()", SQLDataType.UUID)), this, "")

    /**
     * The column
     * <code>passing_times.timetabled_passing_time.vehicle_journey_id</code>.
     * The VEHICLE JOURNEY to which this TIMETABLED PASSING TIME belongs
     */
    val VEHICLE_JOURNEY_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_journey_id"), SQLDataType.UUID.nullable(false), this, "The VEHICLE JOURNEY to which this TIMETABLED PASSING TIME belongs")

    /**
     * The column
     * <code>passing_times.timetabled_passing_time.scheduled_stop_point_in_journey_pattern_ref_id</code>.
     * The SCHEDULED STOP POINT of the JOURNEY PATTERN where the vehicle passes
     */
    val SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_ID: TableField<Record, UUID?> = createField(DSL.name("scheduled_stop_point_in_journey_pattern_ref_id"), SQLDataType.UUID.nullable(false), this, "The SCHEDULED STOP POINT of the JOURNEY PATTERN where the vehicle passes")

    /**
     * The column
     * <code>passing_times.timetabled_passing_time.arrival_time</code>. The time
     * when the vehicle arrives to the SCHEDULED STOP POINT. Measured as
     * interval counted from the midnight of the OPERATING DAY. When NULL, only
     * the departure time is defined for the passing time. E.g. in case this is
     * the first SCHEDULED STOP POINT of the journey.
     */
    val ARRIVAL_TIME: TableField<Record, YearToSecond?> = createField(DSL.name("arrival_time"), SQLDataType.INTERVAL, this, "The time when the vehicle arrives to the SCHEDULED STOP POINT. Measured as interval counted from the midnight of the OPERATING DAY. When NULL, only the departure time is defined for the passing time. E.g. in case this is the first SCHEDULED STOP POINT of the journey.")

    /**
     * The column
     * <code>passing_times.timetabled_passing_time.departure_time</code>. The
     * time when the vehicle departs from the SCHEDULED STOP POINT. Measured as
     * interval counted from the midnight of the OPERATING DAY. When NULL, only
     * the arrival time is defined for the passing time. E.g. in case this is
     * the last SCHEDULED STOP POINT of the journey.
     */
    val DEPARTURE_TIME: TableField<Record, YearToSecond?> = createField(DSL.name("departure_time"), SQLDataType.INTERVAL, this, "The time when the vehicle departs from the SCHEDULED STOP POINT. Measured as interval counted from the midnight of the OPERATING DAY. When NULL, only the arrival time is defined for the passing time. E.g. in case this is the last SCHEDULED STOP POINT of the journey.")

    /**
     * The column
     * <code>passing_times.timetabled_passing_time.passing_time</code>. The time
     * when the vehicle can be considered as passing a SCHEDULED STOP POINT.
     * Computed field to ease development; it can never be NULL.
     */
    val PASSING_TIME: TableField<Record, YearToSecond?> = createField(DSL.name("passing_time"), SQLDataType.INTERVAL.nullable(false), this, "The time when the vehicle can be considered as passing a SCHEDULED STOP POINT. Computed field to ease development; it can never be NULL.")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>passing_times.timetabled_passing_time</code>
     * table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>passing_times.timetabled_passing_time</code>
     * table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>passing_times.timetabled_passing_time</code> table
     * reference
     */
    constructor(): this(DSL.name("timetabled_passing_time"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, TIMETABLED_PASSING_TIME, null)
    override fun getSchema(): Schema? = if (aliased()) null else PassingTimes.PASSING_TIMES
    override fun `as`(alias: String): TimetabledPassingTime = TimetabledPassingTime(DSL.name(alias), this)
    override fun `as`(alias: Name): TimetabledPassingTime = TimetabledPassingTime(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): TimetabledPassingTime = TimetabledPassingTime(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): TimetabledPassingTime = TimetabledPassingTime(name, null)
}
