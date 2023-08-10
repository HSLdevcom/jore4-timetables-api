/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables


import fi.hsl.jore.jore4.jooq.vehicle_service.VehicleService

import java.time.LocalDate
import java.util.UUID

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.JSONB
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
open class GetVehicleServicesForDate(
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
         * <code>vehicle_service.get_vehicle_services_for_date</code>
         */
        val GET_VEHICLE_SERVICES_FOR_DATE: GetVehicleServicesForDate = GetVehicleServicesForDate()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column
     * <code>vehicle_service.get_vehicle_services_for_date.vehicle_service_id</code>.
     */
    val VEHICLE_SERVICE_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_service_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field("gen_random_uuid()", SQLDataType.UUID)), this, "")

    /**
     * The column
     * <code>vehicle_service.get_vehicle_services_for_date.day_type_id</code>.
     */
    val DAY_TYPE_ID: TableField<Record, UUID?> = createField(DSL.name("day_type_id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column
     * <code>vehicle_service.get_vehicle_services_for_date.vehicle_schedule_frame_id</code>.
     */
    val VEHICLE_SCHEDULE_FRAME_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_schedule_frame_id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column
     * <code>vehicle_service.get_vehicle_services_for_date.name_i18n</code>.
     */
    val NAME_I18N: TableField<Record, JSONB?> = createField(DSL.name("name_i18n"), SQLDataType.JSONB, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, arrayOf(
        DSL.value(null, SQLDataType.LOCALDATE)
    ))
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased
     * <code>vehicle_service.get_vehicle_services_for_date</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased
     * <code>vehicle_service.get_vehicle_services_for_date</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vehicle_service.get_vehicle_services_for_date</code> table
     * reference
     */
    constructor(): this(DSL.name("get_vehicle_services_for_date"), null)
    override fun getSchema(): Schema? = if (aliased()) null else VehicleService.VEHICLE_SERVICE
    override fun `as`(alias: String): GetVehicleServicesForDate = GetVehicleServicesForDate(DSL.name(alias), this, parameters)
    override fun `as`(alias: Name): GetVehicleServicesForDate = GetVehicleServicesForDate(alias, this, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: String): GetVehicleServicesForDate = GetVehicleServicesForDate(DSL.name(name), null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Name): GetVehicleServicesForDate = GetVehicleServicesForDate(name, null, parameters)

    /**
     * Call this table-valued function
     */
    fun call(
          observationDate: LocalDate?
    ): GetVehicleServicesForDate = GetVehicleServicesForDate(DSL.name("get_vehicle_services_for_date"), null, arrayOf(
        DSL.value(observationDate, SQLDataType.LOCALDATE)
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }

    /**
     * Call this table-valued function
     */
    fun call(
          observationDate: Field<LocalDate?>
    ): GetVehicleServicesForDate = GetVehicleServicesForDate(DSL.name("get_vehicle_services_for_date"), null, arrayOf(
        observationDate
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }
}
