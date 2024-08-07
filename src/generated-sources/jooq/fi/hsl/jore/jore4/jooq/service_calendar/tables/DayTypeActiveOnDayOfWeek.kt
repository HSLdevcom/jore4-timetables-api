/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables


import fi.hsl.jore.jore4.jooq.service_calendar.ServiceCalendar
import fi.hsl.jore.jore4.jooq.service_calendar.keys.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK_PKEY
import fi.hsl.jore.jore4.jooq.service_calendar.keys.DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK__DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK_DAY_TYPE_ID_FKEY
import fi.hsl.jore.jore4.jooq.service_calendar.tables.DayType.DayTypePath
import fi.hsl.jore.jore4.jooq.service_calendar.tables.records.DayTypeActiveOnDayOfWeekRecord

import java.util.UUID

import kotlin.collections.Collection
import kotlin.collections.List

import org.jooq.Condition
import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.InverseForeignKey
import org.jooq.Name
import org.jooq.Path
import org.jooq.PlainSQL
import org.jooq.QueryPart
import org.jooq.Record
import org.jooq.SQL
import org.jooq.Schema
import org.jooq.Select
import org.jooq.Stringly
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * Tells on which days of week a particular DAY TYPE is active
 */
@Suppress("UNCHECKED_CAST")
open class DayTypeActiveOnDayOfWeek(
    alias: Name,
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, DayTypeActiveOnDayOfWeekRecord>?,
    parentPath: InverseForeignKey<out Record, DayTypeActiveOnDayOfWeekRecord>?,
    aliased: Table<DayTypeActiveOnDayOfWeekRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<DayTypeActiveOnDayOfWeekRecord>(
    alias,
    ServiceCalendar.SERVICE_CALENDAR,
    path,
    childPath,
    parentPath,
    aliased,
    parameters,
    DSL.comment("Tells on which days of week a particular DAY TYPE is active"),
    TableOptions.table(),
    where,
) {
    companion object {

        /**
         * The reference instance of
         * <code>service_calendar.day_type_active_on_day_of_week</code>
         */
        val DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK: DayTypeActiveOnDayOfWeek = DayTypeActiveOnDayOfWeek()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<DayTypeActiveOnDayOfWeekRecord> = DayTypeActiveOnDayOfWeekRecord::class.java

    /**
     * The column
     * <code>service_calendar.day_type_active_on_day_of_week.day_type_id</code>.
     * The DAY TYPE for which we define the activeness
     */
    val DAY_TYPE_ID: TableField<DayTypeActiveOnDayOfWeekRecord, UUID?> = createField(DSL.name("day_type_id"), SQLDataType.UUID.nullable(false), this, "The DAY TYPE for which we define the activeness")

    /**
     * The column
     * <code>service_calendar.day_type_active_on_day_of_week.day_of_week</code>.
     * ISO week day definition (1 = Monday, 7 = Sunday)
     */
    val DAY_OF_WEEK: TableField<DayTypeActiveOnDayOfWeekRecord, Int?> = createField(DSL.name("day_of_week"), SQLDataType.INTEGER.nullable(false), this, "ISO week day definition (1 = Monday, 7 = Sunday)")

    private constructor(alias: Name, aliased: Table<DayTypeActiveOnDayOfWeekRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<DayTypeActiveOnDayOfWeekRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<DayTypeActiveOnDayOfWeekRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

    /**
     * Create an aliased
     * <code>service_calendar.day_type_active_on_day_of_week</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased
     * <code>service_calendar.day_type_active_on_day_of_week</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>service_calendar.day_type_active_on_day_of_week</code>
     * table reference
     */
    constructor(): this(DSL.name("day_type_active_on_day_of_week"), null)

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DayTypeActiveOnDayOfWeekRecord>?, parentPath: InverseForeignKey<out Record, DayTypeActiveOnDayOfWeekRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class DayTypeActiveOnDayOfWeekPath : DayTypeActiveOnDayOfWeek, Path<DayTypeActiveOnDayOfWeekRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, DayTypeActiveOnDayOfWeekRecord>?, parentPath: InverseForeignKey<out Record, DayTypeActiveOnDayOfWeekRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<DayTypeActiveOnDayOfWeekRecord>): super(alias, aliased)
        override fun `as`(alias: String): DayTypeActiveOnDayOfWeekPath = DayTypeActiveOnDayOfWeekPath(DSL.name(alias), this)
        override fun `as`(alias: Name): DayTypeActiveOnDayOfWeekPath = DayTypeActiveOnDayOfWeekPath(alias, this)
        override fun `as`(alias: Table<*>): DayTypeActiveOnDayOfWeekPath = DayTypeActiveOnDayOfWeekPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else ServiceCalendar.SERVICE_CALENDAR
    override fun getPrimaryKey(): UniqueKey<DayTypeActiveOnDayOfWeekRecord> = DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK_PKEY
    override fun getReferences(): List<ForeignKey<DayTypeActiveOnDayOfWeekRecord, *>> = listOf(DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK__DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK_DAY_TYPE_ID_FKEY)

    private lateinit var _dayType: DayTypePath

    /**
     * Get the implicit join path to the <code>service_calendar.day_type</code>
     * table.
     */
    fun dayType(): DayTypePath {
        if (!this::_dayType.isInitialized)
            _dayType = DayTypePath(this, DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK__DAY_TYPE_ACTIVE_ON_DAY_OF_WEEK_DAY_TYPE_ID_FKEY, null)

        return _dayType;
    }

    val dayType: DayTypePath
        get(): DayTypePath = dayType()
    override fun `as`(alias: String): DayTypeActiveOnDayOfWeek = DayTypeActiveOnDayOfWeek(DSL.name(alias), this)
    override fun `as`(alias: Name): DayTypeActiveOnDayOfWeek = DayTypeActiveOnDayOfWeek(alias, this)
    override fun `as`(alias: Table<*>): DayTypeActiveOnDayOfWeek = DayTypeActiveOnDayOfWeek(alias.qualifiedName, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): DayTypeActiveOnDayOfWeek = DayTypeActiveOnDayOfWeek(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): DayTypeActiveOnDayOfWeek = DayTypeActiveOnDayOfWeek(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): DayTypeActiveOnDayOfWeek = DayTypeActiveOnDayOfWeek(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): DayTypeActiveOnDayOfWeek = DayTypeActiveOnDayOfWeek(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): DayTypeActiveOnDayOfWeek = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): DayTypeActiveOnDayOfWeek = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): DayTypeActiveOnDayOfWeek = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): DayTypeActiveOnDayOfWeek = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): DayTypeActiveOnDayOfWeek = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): DayTypeActiveOnDayOfWeek = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): DayTypeActiveOnDayOfWeek = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): DayTypeActiveOnDayOfWeek = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): DayTypeActiveOnDayOfWeek = where(DSL.notExists(select))
}
