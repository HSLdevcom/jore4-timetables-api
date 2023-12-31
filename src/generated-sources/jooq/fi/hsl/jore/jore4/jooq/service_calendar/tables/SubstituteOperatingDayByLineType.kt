/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables


import fi.hsl.jore.jore4.jooq.route.tables.TypeOfLine
import fi.hsl.jore.jore4.jooq.service_calendar.ServiceCalendar
import fi.hsl.jore.jore4.jooq.service_calendar.keys.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_PKEY
import fi.hsl.jore.jore4.jooq.service_calendar.keys.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE__SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_SUBSTITUTE_PERIOD_FKEY
import fi.hsl.jore.jore4.jooq.service_calendar.keys.SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE__SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_TYPE_OF_LINE_FKEY
import fi.hsl.jore.jore4.jooq.service_calendar.tables.records.SubstituteOperatingDayByLineTypeRecord

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID
import java.util.function.Function

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row9
import org.jooq.Schema
import org.jooq.SelectField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
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
    path: ForeignKey<out Record, SubstituteOperatingDayByLineTypeRecord>?,
    aliased: Table<SubstituteOperatingDayByLineTypeRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<SubstituteOperatingDayByLineTypeRecord>(
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
    override fun getRecordType(): Class<SubstituteOperatingDayByLineTypeRecord> = SubstituteOperatingDayByLineTypeRecord::class.java

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.substitute_operating_day_by_line_type_id</code>.
     */
    val SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID: TableField<SubstituteOperatingDayByLineTypeRecord, UUID?> = createField(DSL.name("substitute_operating_day_by_line_type_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.type_of_line</code>.
     * The type of line this substitute operating day is bound to.
     */
    val TYPE_OF_LINE: TableField<SubstituteOperatingDayByLineTypeRecord, String?> = createField(DSL.name("type_of_line"), SQLDataType.CLOB.nullable(false), this, "The type of line this substitute operating day is bound to.")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.superseded_date</code>.
     * The date of operating day being superseded.
     */
    val SUPERSEDED_DATE: TableField<SubstituteOperatingDayByLineTypeRecord, LocalDate?> = createField(DSL.name("superseded_date"), SQLDataType.LOCALDATE.nullable(false), this, "The date of operating day being superseded.")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.substitute_day_of_week</code>.
     * The ISO day of week (1=Monday, ... , 7=Sunday) of the day type used as
     * the basis for operating day substitution. A NULL value indicates that
     * there is no public transit at all, i.e. no vehicle journeys are operated
     * within the given time period.
     */
    val SUBSTITUTE_DAY_OF_WEEK: TableField<SubstituteOperatingDayByLineTypeRecord, Int?> = createField(DSL.name("substitute_day_of_week"), SQLDataType.INTEGER, this, "The ISO day of week (1=Monday, ... , 7=Sunday) of the day type used as the basis for operating day substitution. A NULL value indicates that there is no public transit at all, i.e. no vehicle journeys are operated within the given time period.")

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
    val BEGIN_TIME: TableField<SubstituteOperatingDayByLineTypeRecord, YearToSecond?> = createField(DSL.name("begin_time"), SQLDataType.INTERVAL, this, "The time from which the substituting public transit comes into effect. If NULL, the substitution is in effect from the start of the operating day. When substitute_day_of_week is not NULL (reference day case), vehicle journeys prior to this time are not operated. When substitute_day_of_week is NULL (no traffic case), the vehicle journeys before this time are operated as usual.")

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
    val END_TIME: TableField<SubstituteOperatingDayByLineTypeRecord, YearToSecond?> = createField(DSL.name("end_time"), SQLDataType.INTERVAL, this, "The time (exclusive) until which the substituting public transit is valid. If NULL, the substitution is in effect until the end of the operating day. When substitute_day_of_week is not NULL (reference day case), vehicle journeys starting from this time are not operated. When substitute_day_of_week is NULL (no traffic case), the vehicle journeys starting from this time are operated as usual.")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.timezone</code>.
     */
    val TIMEZONE: TableField<SubstituteOperatingDayByLineTypeRecord, String?> = createField(DSL.name("timezone"), SQLDataType.CLOB.nullable(false).defaultValue(DSL.field(DSL.raw("internal_utils.const_default_timezone()"), SQLDataType.CLOB)), this, "")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.substitute_operating_period_id</code>.
     * The id of the substitute operating period
     */
    val SUBSTITUTE_OPERATING_PERIOD_ID: TableField<SubstituteOperatingDayByLineTypeRecord, UUID?> = createField(DSL.name("substitute_operating_period_id"), SQLDataType.UUID.nullable(false), this, "The id of the substitute operating period")

    /**
     * The column
     * <code>service_calendar.substitute_operating_day_by_line_type.created_at</code>.
     */
    val CREATED_AT: TableField<SubstituteOperatingDayByLineTypeRecord, OffsetDateTime?> = createField(DSL.name("created_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false).defaultValue(DSL.field(DSL.raw("now()"), SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "")

    private constructor(alias: Name, aliased: Table<SubstituteOperatingDayByLineTypeRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<SubstituteOperatingDayByLineTypeRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

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

    constructor(child: Table<out Record>, key: ForeignKey<out Record, SubstituteOperatingDayByLineTypeRecord>): this(Internal.createPathAlias(child, key), child, key, SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE, null)
    override fun getSchema(): Schema? = if (aliased()) null else ServiceCalendar.SERVICE_CALENDAR
    override fun getPrimaryKey(): UniqueKey<SubstituteOperatingDayByLineTypeRecord> = SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_PKEY
    override fun getReferences(): List<ForeignKey<SubstituteOperatingDayByLineTypeRecord, *>> = listOf(SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE__SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_TYPE_OF_LINE_FKEY, SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE__SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_SUBSTITUTE_PERIOD_FKEY)

    private lateinit var _typeOfLine: TypeOfLine
    private lateinit var _substituteOperatingPeriod: SubstituteOperatingPeriod

    /**
     * Get the implicit join path to the <code>route.type_of_line</code> table.
     */
    fun typeOfLine(): TypeOfLine {
        if (!this::_typeOfLine.isInitialized)
            _typeOfLine = TypeOfLine(this, SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE__SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_TYPE_OF_LINE_FKEY)

        return _typeOfLine;
    }

    val typeOfLine: TypeOfLine
        get(): TypeOfLine = typeOfLine()

    /**
     * Get the implicit join path to the
     * <code>service_calendar.substitute_operating_period</code> table.
     */
    fun substituteOperatingPeriod(): SubstituteOperatingPeriod {
        if (!this::_substituteOperatingPeriod.isInitialized)
            _substituteOperatingPeriod = SubstituteOperatingPeriod(this, SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE__SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_SUBSTITUTE_PERIOD_FKEY)

        return _substituteOperatingPeriod;
    }

    val substituteOperatingPeriod: SubstituteOperatingPeriod
        get(): SubstituteOperatingPeriod = substituteOperatingPeriod()
    override fun `as`(alias: String): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(DSL.name(alias), this)
    override fun `as`(alias: Name): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(alias, this)
    override fun `as`(alias: Table<*>): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): SubstituteOperatingDayByLineType = SubstituteOperatingDayByLineType(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row9<UUID?, String?, LocalDate?, Int?, YearToSecond?, YearToSecond?, String?, UUID?, OffsetDateTime?> = super.fieldsRow() as Row9<UUID?, String?, LocalDate?, Int?, YearToSecond?, YearToSecond?, String?, UUID?, OffsetDateTime?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, String?, LocalDate?, Int?, YearToSecond?, YearToSecond?, String?, UUID?, OffsetDateTime?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, String?, LocalDate?, Int?, YearToSecond?, YearToSecond?, String?, UUID?, OffsetDateTime?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
