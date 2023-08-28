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
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class GetPassingTimeOrderValidityData(
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
    DSL.comment(""),
    TableOptions.function()
) {
    companion object {

        /**
         * The reference instance of
         * <code>passing_times.get_passing_time_order_validity_data</code>
         */
        val GET_PASSING_TIME_ORDER_VALIDITY_DATA: GetPassingTimeOrderValidityData = GetPassingTimeOrderValidityData()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column
     * <code>passing_times.get_passing_time_order_validity_data.vehicle_journey_id</code>.
     */
    val VEHICLE_JOURNEY_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_journey_id"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>passing_times.get_passing_time_order_validity_data.first_passing_time_id</code>.
     */
    val FIRST_PASSING_TIME_ID: TableField<Record, UUID?> = createField(DSL.name("first_passing_time_id"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>passing_times.get_passing_time_order_validity_data.last_passing_time_id</code>.
     */
    val LAST_PASSING_TIME_ID: TableField<Record, UUID?> = createField(DSL.name("last_passing_time_id"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>passing_times.get_passing_time_order_validity_data.stop_order_is_valid</code>.
     */
    val STOP_ORDER_IS_VALID: TableField<Record, Boolean?> = createField(DSL.name("stop_order_is_valid"), SQLDataType.BOOLEAN, this, "")

    /**
     * The column
     * <code>passing_times.get_passing_time_order_validity_data.coherent_journey_pattern_refs</code>.
     */
    val COHERENT_JOURNEY_PATTERN_REFS: TableField<Record, Boolean?> = createField(DSL.name("coherent_journey_pattern_refs"), SQLDataType.BOOLEAN, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, arrayOf(
        DSL.value(null, SQLDataType.UUID.array()),
        DSL.value(null, SQLDataType.UUID.array())
    ))
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased
     * <code>passing_times.get_passing_time_order_validity_data</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased
     * <code>passing_times.get_passing_time_order_validity_data</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>passing_times.get_passing_time_order_validity_data</code>
     * table reference
     */
    constructor(): this(DSL.name("get_passing_time_order_validity_data"), null)
    override fun getSchema(): Schema? = if (aliased()) null else PassingTimes.PASSING_TIMES
    override fun `as`(alias: String): GetPassingTimeOrderValidityData = GetPassingTimeOrderValidityData(DSL.name(alias), this, parameters)
    override fun `as`(alias: Name): GetPassingTimeOrderValidityData = GetPassingTimeOrderValidityData(alias, this, parameters)
    override fun `as`(alias: Table<*>): GetPassingTimeOrderValidityData = GetPassingTimeOrderValidityData(alias.getQualifiedName(), this, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: String): GetPassingTimeOrderValidityData = GetPassingTimeOrderValidityData(DSL.name(name), null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Name): GetPassingTimeOrderValidityData = GetPassingTimeOrderValidityData(name, null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): GetPassingTimeOrderValidityData = GetPassingTimeOrderValidityData(name.getQualifiedName(), null, parameters)

    /**
     * Call this table-valued function
     */
    fun call(
          filterVehicleJourneyIds: Array<UUID?>?
        , filterJourneyPatternRefIds: Array<UUID?>?
    ): GetPassingTimeOrderValidityData = GetPassingTimeOrderValidityData(DSL.name("get_passing_time_order_validity_data"), null, arrayOf(
        DSL.value(filterVehicleJourneyIds, SQLDataType.UUID.array()),
        DSL.value(filterJourneyPatternRefIds, SQLDataType.UUID.array())
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }

    /**
     * Call this table-valued function
     */
    fun call(
          filterVehicleJourneyIds: Field<Array<UUID?>?>
        , filterJourneyPatternRefIds: Field<Array<UUID?>?>
    ): GetPassingTimeOrderValidityData = GetPassingTimeOrderValidityData(DSL.name("get_passing_time_order_validity_data"), null, arrayOf(
        filterVehicleJourneyIds,
        filterJourneyPatternRefIds
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }
}
