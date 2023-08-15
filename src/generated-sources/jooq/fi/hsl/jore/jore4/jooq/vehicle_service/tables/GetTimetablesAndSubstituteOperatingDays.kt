/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables


import fi.hsl.jore.jore4.jooq.vehicle_service.VehicleService

import java.time.LocalDate
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
open class GetTimetablesAndSubstituteOperatingDays(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, Record>?,
    aliased: Table<Record>?,
    parameters: Array<Field<*>?>?
): TableImpl<Record>(
    alias,
    VehicleService.VEHICLE_SERVICE,
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
         * <code>vehicle_service.get_timetables_and_substitute_operating_days</code>
         */
        val GET_TIMETABLES_AND_SUBSTITUTE_OPERATING_DAYS: GetTimetablesAndSubstituteOperatingDays = GetTimetablesAndSubstituteOperatingDays()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column
     * <code>vehicle_service.get_timetables_and_substitute_operating_days.vehicle_schedule_frame_id</code>.
     */
    val VEHICLE_SCHEDULE_FRAME_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_schedule_frame_id"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>vehicle_service.get_timetables_and_substitute_operating_days.substitute_operating_day_by_line_type_id</code>.
     */
    val SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID: TableField<Record, UUID?> = createField(DSL.name("substitute_operating_day_by_line_type_id"), SQLDataType.UUID, this, "")

    /**
     * The column
     * <code>vehicle_service.get_timetables_and_substitute_operating_days.validity_start</code>.
     */
    val VALIDITY_START: TableField<Record, LocalDate?> = createField(DSL.name("validity_start"), SQLDataType.LOCALDATE.nullable(false), this, "")

    /**
     * The column
     * <code>vehicle_service.get_timetables_and_substitute_operating_days.validity_end</code>.
     */
    val VALIDITY_END: TableField<Record, LocalDate?> = createField(DSL.name("validity_end"), SQLDataType.LOCALDATE.nullable(false), this, "")

    /**
     * The column
     * <code>vehicle_service.get_timetables_and_substitute_operating_days.priority</code>.
     */
    val PRIORITY: TableField<Record, Int?> = createField(DSL.name("priority"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column
     * <code>vehicle_service.get_timetables_and_substitute_operating_days.in_effect</code>.
     */
    val IN_EFFECT: TableField<Record, Boolean?> = createField(DSL.name("in_effect"), SQLDataType.BOOLEAN.nullable(false), this, "")

    /**
     * The column
     * <code>vehicle_service.get_timetables_and_substitute_operating_days.day_type_id</code>.
     */
    val DAY_TYPE_ID: TableField<Record, UUID?> = createField(DSL.name("day_type_id"), SQLDataType.UUID.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, arrayOf(
        DSL.value(null, SQLDataType.UUID.array()),
        DSL.value(null, SQLDataType.LOCALDATE),
        DSL.value(null, SQLDataType.LOCALDATE)
    ))
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased
     * <code>vehicle_service.get_timetables_and_substitute_operating_days</code>
     * table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased
     * <code>vehicle_service.get_timetables_and_substitute_operating_days</code>
     * table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a
     * <code>vehicle_service.get_timetables_and_substitute_operating_days</code>
     * table reference
     */
    constructor(): this(DSL.name("get_timetables_and_substitute_operating_days"), null)
    override fun getSchema(): Schema? = if (aliased()) null else VehicleService.VEHICLE_SERVICE
    override fun `as`(alias: String): GetTimetablesAndSubstituteOperatingDays = GetTimetablesAndSubstituteOperatingDays(DSL.name(alias), this, parameters)
    override fun `as`(alias: Name): GetTimetablesAndSubstituteOperatingDays = GetTimetablesAndSubstituteOperatingDays(alias, this, parameters)
    override fun `as`(alias: Table<*>): GetTimetablesAndSubstituteOperatingDays = GetTimetablesAndSubstituteOperatingDays(alias.getQualifiedName(), this, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: String): GetTimetablesAndSubstituteOperatingDays = GetTimetablesAndSubstituteOperatingDays(DSL.name(name), null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Name): GetTimetablesAndSubstituteOperatingDays = GetTimetablesAndSubstituteOperatingDays(name, null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): GetTimetablesAndSubstituteOperatingDays = GetTimetablesAndSubstituteOperatingDays(name.getQualifiedName(), null, parameters)

    /**
     * Call this table-valued function
     */
    fun call(
          journeyPatternIds: Array<UUID?>?
        , startDate: LocalDate?
        , endDate: LocalDate?
    ): GetTimetablesAndSubstituteOperatingDays = GetTimetablesAndSubstituteOperatingDays(DSL.name("get_timetables_and_substitute_operating_days"), null, arrayOf(
        DSL.value(journeyPatternIds, SQLDataType.UUID.array()),
        DSL.value(startDate, SQLDataType.LOCALDATE),
        DSL.value(endDate, SQLDataType.LOCALDATE)
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }

    /**
     * Call this table-valued function
     */
    fun call(
          journeyPatternIds: Field<Array<UUID?>?>
        , startDate: Field<LocalDate?>
        , endDate: Field<LocalDate?>
    ): GetTimetablesAndSubstituteOperatingDays = GetTimetablesAndSubstituteOperatingDays(DSL.name("get_timetables_and_substitute_operating_days"), null, arrayOf(
        journeyPatternIds,
        startDate,
        endDate
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }
}