/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables


import fi.hsl.jore.jore4.jooq.service_calendar.ServiceCalendar

import java.time.LocalDate
import java.time.OffsetDateTime
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
 * Models substitute public transit as (1) a reference day or (2) indicating
 * that public transit does not occur on certain date. Substitute operating days
 * are always bound to a type of line.
 */
@Suppress("UNCHECKED_CAST")
open class SubstituteOperatingDayByLineType(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, Record>?,
    aliased: Table<Record>?,
    parameters: Array<Field<*>?>?
): TableImpl<Record>(
    alias,
    ServiceCalendar.SERVICE_CALENDAR,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("Models substitute public transit as (1) a reference day or (2) indicating that public transit does not occur on certain date. Substitute operating days are always bound to a type of line."),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of
         * <code>service_calendar.substitute_operating_day_by_line_type</code>
         */
        val SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE: SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.substitute_operating_day_by_line_type_id</code>.
     */
    val SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID: TableField<Record, UUID?> = createField(DSL.name("substitute_operating_day_by_line_type_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field("gen_random_uuid()", SQLDataType.UUID)), this, "")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.type_of_line</code>.
     * The type of line this substitute operating day is bound to.
     */
    val TYPE_OF_LINE: TableField<Record, String?> = createField(DSL.name("type_of_line"), SQLDataType.CLOB.nullable(false), this, "The type of line this substitute operating day is bound to.")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.superseded_date</code>.
     * The date of operating day being superseded.
     */
    val SUPERSEDED_DATE: TableField<Record, LocalDate?> = createField(DSL.name("superseded_date"), SQLDataType.LOCALDATE.nullable(false), this, "The date of operating day being superseded.")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.substitute_day_of_week</code>.
     * The ISO day of week (1=Monday, ... , 7=Sunday) of the day type used as
     * the basis for operating day substitution. A NULL value indicates that
     * there is no public transit at all, i.e. no vehicle journeys are operated
     * within the given time period.
     */
    val SUBSTITUTE_DAY_OF_WEEK: TableField<Record, Int?> = createField(DSL.name("substitute_day_of_week"), SQLDataType.INTEGER, this, "The ISO day of week (1=Monday, ... , 7=Sunday) of the day type used as the basis for operating day substitution. A NULL value indicates that there is no public transit at all, i.e. no vehicle journeys are operated within the given time period.")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.begin_time</code>.
     * The time from which the substituting public transit comes into effect. If
     * NULL, the substitution is in effect from the start of the operating day.
     * When substitute_day_of_week is not NULL (reference day case), vehicle
     * journeys prior to this time are not operated. When substitute_day_of_week
     * is NULL (no traffic case), the vehicle journeys before this time are
     * operated as usual.
     */
    val BEGIN_TIME: TableField<Record, YearToSecond?> = createField(DSL.name("begin_time"), SQLDataType.INTERVAL, this, "The time from which the substituting public transit comes into effect. If NULL, the substitution is in effect from the start of the operating day. When substitute_day_of_week is not NULL (reference day case), vehicle journeys prior to this time are not operated. When substitute_day_of_week is NULL (no traffic case), the vehicle journeys before this time are operated as usual.")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.end_time</code>.
     * The time (exclusive) until which the substituting public transit is
     * valid. If NULL, the substitution is in effect until the end of the
     * operating day. When substitute_day_of_week is not NULL (reference day
     * case), vehicle journeys starting from this time are not operated. When
     * substitute_day_of_week is NULL (no traffic case), the vehicle journeys
     * starting from this time are operated as usual.
     */
    val END_TIME: TableField<Record, YearToSecond?> = createField(DSL.name("end_time"), SQLDataType.INTERVAL, this, "The time (exclusive) until which the substituting public transit is valid. If NULL, the substitution is in effect until the end of the operating day. When substitute_day_of_week is not NULL (reference day case), vehicle journeys starting from this time are not operated. When substitute_day_of_week is NULL (no traffic case), the vehicle journeys starting from this time are operated as usual.")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.timezone</code>.
     */
    val TIMEZONE: TableField<Record, String?> = createField(DSL.name("timezone"), SQLDataType.CLOB.nullable(false).defaultValue(DSL.field("internal_utils.const_default_timezone()", SQLDataType.CLOB)), this, "")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.begin_datetime</code>.
     * Calculated timestamp for the instant from which the substituting public
     * transit comes into effect.
     */
    val BEGIN_DATETIME: TableField<Record, OffsetDateTime?> = createField(DSL.name("begin_datetime"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "Calculated timestamp for the instant from which the substituting public transit comes into effect.")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.end_datetime</code>.
     * Calculated timestamp for the instant (exclusive) until which the
     * substituting public transit is in effect.
     */
    val END_DATETIME: TableField<Record, OffsetDateTime?> = createField(DSL.name("end_datetime"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "Calculated timestamp for the instant (exclusive) until which the substituting public transit is in effect.")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased
     * <code>service_calendar.substitute_operating_day_by_line_type</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased
     * <code>service_calendar.substitute_operating_day_by_line_type</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a
     * <code>service_calendar.substitute_operating_day_by_line_type</code> table
     * reference
     */
    constructor(): this(DSL.name("substitute_operating_day_by_line_type"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE, null)
    override fun getSchema(): Schema? = if (aliased()) null else ServiceCalendar.SERVICE_CALENDAR
    override fun `as`(alias: String): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(DSL.name(alias), this)
    override fun `as`(alias: Name): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(name, null)
}
