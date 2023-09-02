/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_schedule.tables


import fi.hsl.jore.jore4.jooq.vehicle_schedule.VehicleSchedule
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.records.GetOverlappingSchedulesRecord

import java.util.UUID
import java.util.function.Function

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row8
import org.jooq.Schema
import org.jooq.SelectField
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
open class GetOverlappingSchedules(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, GetOverlappingSchedulesRecord>?,
    aliased: Table<GetOverlappingSchedulesRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<GetOverlappingSchedulesRecord>(
    alias,
    VehicleSchedule.VEHICLE_SCHEDULE,
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
         * <code>vehicle_schedule.get_overlapping_schedules</code>
         */
        val GET_OVERLAPPING_SCHEDULES: GetOverlappingSchedules = GetOverlappingSchedules()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<GetOverlappingSchedulesRecord> = GetOverlappingSchedulesRecord::class.java

    /**
     * The column
     * <code>vehicle_schedule.get_overlapping_schedules.current_vehicle_schedule_frame_id</code>.
     */
    val CURRENT_VEHICLE_SCHEDULE_FRAME_ID: TableField<GetOverlappingSchedulesRecord, UUID?> = createField(DSL.name("current_vehicle_schedule_frame_id"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>vehicle_schedule.get_overlapping_schedules.other_vehicle_schedule_frame_id</code>.
     */
    val OTHER_VEHICLE_SCHEDULE_FRAME_ID: TableField<GetOverlappingSchedulesRecord, UUID?> = createField(DSL.name("other_vehicle_schedule_frame_id"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>vehicle_schedule.get_overlapping_schedules.journey_pattern_id</code>.
     */
    val JOURNEY_PATTERN_ID: TableField<GetOverlappingSchedulesRecord, UUID?> = createField(DSL.name("journey_pattern_id"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>vehicle_schedule.get_overlapping_schedules.active_on_day_of_week</code>.
     */
    val ACTIVE_ON_DAY_OF_WEEK: TableField<GetOverlappingSchedulesRecord, Int?> = createField(DSL.name("active_on_day_of_week"), SQLDataType.INTEGER, this, "")

    /**
     * The column
     * <code>vehicle_schedule.get_overlapping_schedules.priority</code>.
     */
    val PRIORITY: TableField<GetOverlappingSchedulesRecord, Int?> = createField(DSL.name("priority"), SQLDataType.INTEGER, this, "")
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val CURRENT_VALIDITY_RANGE: TableField<GetOverlappingSchedulesRecord, Any?> = createField(DSL.name("current_validity_range"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"daterange\""), this, "")
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val OTHER_VALIDITY_RANGE: TableField<GetOverlappingSchedulesRecord, Any?> = createField(DSL.name("other_validity_range"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"daterange\""), this, "")
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val VALIDITY_INTERSECTION: TableField<GetOverlappingSchedulesRecord, Any?> = createField(DSL.name("validity_intersection"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"daterange\""), this, "")

    private constructor(alias: Name, aliased: Table<GetOverlappingSchedulesRecord>?): this(alias, null, null, aliased, arrayOf(
        DSL.value(null, SQLDataType.UUID.array()),
        DSL.value(null, SQLDataType.UUID.array()),
        DSL.value(null, SQLDataType.BOOLEAN.defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)))
    ))
    private constructor(alias: Name, aliased: Table<GetOverlappingSchedulesRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>vehicle_schedule.get_overlapping_schedules</code>
     * table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>vehicle_schedule.get_overlapping_schedules</code>
     * table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vehicle_schedule.get_overlapping_schedules</code> table
     * reference
     */
    constructor(): this(DSL.name("get_overlapping_schedules"), null)
    override fun getSchema(): Schema? = if (aliased()) null else VehicleSchedule.VEHICLE_SCHEDULE
    override fun `as`(alias: String): GetOverlappingSchedules = GetOverlappingSchedules(DSL.name(alias), this, parameters)
    override fun `as`(alias: Name): GetOverlappingSchedules = GetOverlappingSchedules(alias, this, parameters)
    override fun `as`(alias: Table<*>): GetOverlappingSchedules = GetOverlappingSchedules(alias.getQualifiedName(), this, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: String): GetOverlappingSchedules = GetOverlappingSchedules(DSL.name(name), null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Name): GetOverlappingSchedules = GetOverlappingSchedules(name, null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): GetOverlappingSchedules = GetOverlappingSchedules(name.getQualifiedName(), null, parameters)

    // -------------------------------------------------------------------------
    // Row8 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row8<UUID?, UUID?, UUID?, Int?, Int?, Any?, Any?, Any?> = super.fieldsRow() as Row8<UUID?, UUID?, UUID?, Int?, Int?, Any?, Any?, Any?>

    /**
     * Call this table-valued function
     */
    fun call(
          filterVehicleScheduleFrameIds: Array<UUID?>?
        , filterJourneyPatternRefIds: Array<UUID?>?
        , ignorePriority: Boolean?
    ): GetOverlappingSchedules = GetOverlappingSchedules(DSL.name("get_overlapping_schedules"), null, arrayOf(
        DSL.value(filterVehicleScheduleFrameIds, SQLDataType.UUID.array()),
        DSL.value(filterJourneyPatternRefIds, SQLDataType.UUID.array()),
        DSL.value(ignorePriority, SQLDataType.BOOLEAN.defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)))
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }

    /**
     * Call this table-valued function
     */
    fun call(
          filterVehicleScheduleFrameIds: Field<Array<UUID?>?>
        , filterJourneyPatternRefIds: Field<Array<UUID?>?>
        , ignorePriority: Field<Boolean?>
    ): GetOverlappingSchedules = GetOverlappingSchedules(DSL.name("get_overlapping_schedules"), null, arrayOf(
        filterVehicleScheduleFrameIds,
        filterJourneyPatternRefIds,
        ignorePriority
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, UUID?, UUID?, Int?, Int?, Any?, Any?, Any?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, UUID?, UUID?, Int?, Int?, Any?, Any?, Any?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
