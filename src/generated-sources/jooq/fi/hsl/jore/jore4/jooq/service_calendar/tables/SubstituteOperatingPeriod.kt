/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.service_calendar.tables


import fi.hsl.jore.jore4.jooq.service_calendar.ServiceCalendar
import fi.hsl.jore.jore4.jooq.service_calendar.keys.SUBSTITUTE_OPERATING_PERIOD_PERIOD_NAME_KEY
import fi.hsl.jore.jore4.jooq.service_calendar.keys.SUBSTITUTE_OPERATING_PERIOD_PKEY
import fi.hsl.jore.jore4.jooq.service_calendar.tables.records.SubstituteOperatingPeriodRecord

import java.util.UUID
import java.util.function.Function

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row3
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


/**
 * Models substitute operating period that consists of substitute operating days
 * by line types.
 */
@Suppress("UNCHECKED_CAST")
open class SubstituteOperatingPeriod(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, SubstituteOperatingPeriodRecord>?,
    aliased: Table<SubstituteOperatingPeriodRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<SubstituteOperatingPeriodRecord>(
    alias,
    ServiceCalendar.SERVICE_CALENDAR,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("Models substitute operating period that consists of substitute operating days by line types."),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of
         * <code>service_calendar.substitute_operating_period</code>
         */
        val SUBSTITUTE_OPERATING_PERIOD: SubstituteOperatingPeriod = SubstituteOperatingPeriod()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<SubstituteOperatingPeriodRecord> = SubstituteOperatingPeriodRecord::class.java

    /**
     * The column
     * <code>service_calendar.substitute_operating_period.substitute_operating_period_id</code>.
     */
    val SUBSTITUTE_OPERATING_PERIOD_ID: TableField<SubstituteOperatingPeriodRecord, UUID?> = createField(DSL.name("substitute_operating_period_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "")

    /**
     * The column
     * <code>service_calendar.substitute_operating_period.period_name</code>.
     * Substitute operating period's name
     */
    val PERIOD_NAME: TableField<SubstituteOperatingPeriodRecord, String?> = createField(DSL.name("period_name"), SQLDataType.CLOB.nullable(false), this, "Substitute operating period's name")

    /**
     * The column
     * <code>service_calendar.substitute_operating_period.is_preset</code>. Flag
     * indicating whether operating period is preset or not. Preset operating
     * periods have restrictions on the UI
     */
    val IS_PRESET: TableField<SubstituteOperatingPeriodRecord, Boolean?> = createField(DSL.name("is_preset"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "Flag indicating whether operating period is preset or not. Preset operating periods have restrictions on the UI")

    private constructor(alias: Name, aliased: Table<SubstituteOperatingPeriodRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<SubstituteOperatingPeriodRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased
     * <code>service_calendar.substitute_operating_period</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased
     * <code>service_calendar.substitute_operating_period</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>service_calendar.substitute_operating_period</code> table
     * reference
     */
    constructor(): this(DSL.name("substitute_operating_period"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, SubstituteOperatingPeriodRecord>): this(Internal.createPathAlias(child, key), child, key, SUBSTITUTE_OPERATING_PERIOD, null)
    override fun getSchema(): Schema? = if (aliased()) null else ServiceCalendar.SERVICE_CALENDAR
    override fun getPrimaryKey(): UniqueKey<SubstituteOperatingPeriodRecord> = SUBSTITUTE_OPERATING_PERIOD_PKEY
    override fun getUniqueKeys(): List<UniqueKey<SubstituteOperatingPeriodRecord>> = listOf(SUBSTITUTE_OPERATING_PERIOD_PERIOD_NAME_KEY)
    override fun `as`(alias: String): SubstituteOperatingPeriod = SubstituteOperatingPeriod(DSL.name(alias), this)
    override fun `as`(alias: Name): SubstituteOperatingPeriod = SubstituteOperatingPeriod(alias, this)
    override fun `as`(alias: Table<*>): SubstituteOperatingPeriod = SubstituteOperatingPeriod(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): SubstituteOperatingPeriod = SubstituteOperatingPeriod(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): SubstituteOperatingPeriod = SubstituteOperatingPeriod(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): SubstituteOperatingPeriod = SubstituteOperatingPeriod(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row3<UUID?, String?, Boolean?> = super.fieldsRow() as Row3<UUID?, String?, Boolean?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, String?, Boolean?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, String?, Boolean?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
