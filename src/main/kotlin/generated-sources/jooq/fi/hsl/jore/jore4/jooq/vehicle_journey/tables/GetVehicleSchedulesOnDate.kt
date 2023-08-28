/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_journey.tables


import fi.hsl.jore.jore4.jooq.vehicle_journey.VehicleJourney

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
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class GetVehicleSchedulesOnDate(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, Record>?,
    aliased: Table<Record>?,
    parameters: Array<Field<*>?>?
): TableImpl<Record>(
    alias,
    VehicleJourney.VEHICLE_JOURNEY,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.function()
) {
    companion object {

        /**
         * The reference instance of <code>vehicle_journey.get_vehicle_schedules_on_date</code>
         */
        val GET_VEHICLE_SCHEDULES_ON_DATE = GetVehicleSchedulesOnDate()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>vehicle_journey.get_vehicle_schedules_on_date.vehicle_journey_id</code>.
     */
    val VEHICLE_JOURNEY_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_journey_id"), SQLDataType.UUID, this, "")

    /**
     * The column <code>vehicle_journey.get_vehicle_schedules_on_date.validity_start</code>.
     */
    val VALIDITY_START: TableField<Record, LocalDate?> = createField(DSL.name("validity_start"), SQLDataType.LOCALDATE.nullable(false), this, "")

    /**
     * The column <code>vehicle_journey.get_vehicle_schedules_on_date.validity_end</code>.
     */
    val VALIDITY_END: TableField<Record, LocalDate?> = createField(DSL.name("validity_end"), SQLDataType.LOCALDATE.nullable(false), this, "")

    /**
     * The column <code>vehicle_journey.get_vehicle_schedules_on_date.priority</code>.
     */
    val PRIORITY: TableField<Record, Int?> = createField(DSL.name("priority"), SQLDataType.INTEGER.nullable(false), this, "")

    /**
     * The column <code>vehicle_journey.get_vehicle_schedules_on_date.day_type_id</code>.
     */
    val DAY_TYPE_ID: TableField<Record, UUID?> = createField(DSL.name("day_type_id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>vehicle_journey.get_vehicle_schedules_on_date.vehicle_schedule_frame_id</code>.
     */
    val VEHICLE_SCHEDULE_FRAME_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_schedule_frame_id"), SQLDataType.UUID, this, "")

    /**
     * The column <code>vehicle_journey.get_vehicle_schedules_on_date.substitute_operating_day_by_line_type_id</code>.
     */
    val SUBSTITUTE_OPERATING_DAY_BY_LINE_TYPE_ID: TableField<Record, UUID?> = createField(DSL.name("substitute_operating_day_by_line_type_id"), SQLDataType.UUID, this, "")

    /**
     * The column <code>vehicle_journey.get_vehicle_schedules_on_date.created_at</code>.
     */
    val CREATED_AT: TableField<Record, OffsetDateTime?> = createField(DSL.name("created_at"), SQLDataType.TIMESTAMPWITHTIMEZONE(6), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, arrayOf())
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>vehicle_journey.get_vehicle_schedules_on_date</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>vehicle_journey.get_vehicle_schedules_on_date</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vehicle_journey.get_vehicle_schedules_on_date</code> table reference
     */
    constructor(): this(DSL.name("get_vehicle_schedules_on_date"), null)
    override fun getSchema(): Schema = VehicleJourney.VEHICLE_JOURNEY
    override fun `as`(alias: String): GetVehicleSchedulesOnDate = GetVehicleSchedulesOnDate(DSL.name(alias), this, parameters)
    override fun `as`(alias: Name): GetVehicleSchedulesOnDate = GetVehicleSchedulesOnDate(alias, this, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: String): GetVehicleSchedulesOnDate = GetVehicleSchedulesOnDate(DSL.name(name), null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Name): GetVehicleSchedulesOnDate = GetVehicleSchedulesOnDate(name, null, parameters)

    /**
     * Call this table-valued function
     */
    fun call(
          journeyPatternUuid: UUID?
        , observationDate: LocalDate?
    ): GetVehicleSchedulesOnDate = GetVehicleSchedulesOnDate(DSL.name("get_vehicle_schedules_on_date"), null, arrayOf(
          DSL.value(journeyPatternUuid, SQLDataType.UUID)
        , DSL.value(observationDate, SQLDataType.LOCALDATE)
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }

    /**
     * Call this table-valued function
     */
    fun call(
          journeyPatternUuid: Field<UUID?>
        , observationDate: Field<LocalDate?>
    ): GetVehicleSchedulesOnDate = GetVehicleSchedulesOnDate(DSL.name("get_vehicle_schedules_on_date"), null, arrayOf(
          journeyPatternUuid
        , observationDate
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }
}
