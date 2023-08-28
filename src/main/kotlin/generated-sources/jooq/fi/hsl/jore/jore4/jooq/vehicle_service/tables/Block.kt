/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables


import fi.hsl.jore.jore4.jooq.vehicle_service.VehicleService

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
 * The work of a vehicle from the time it leaves a PARKING POINT after parking
 * until its next return to park at a PARKING POINT. Any subsequent departure
 * from a PARKING POINT after parking marks the start of a new BLOCK. The period
 * of a BLOCK has to be covered by DUTies. Transmodel:
 * https://www.transmodel-cen.eu/model/index.htm?goto=3:5:958 
 */
@Suppress("UNCHECKED_CAST")
open class Block(
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
    DSL.comment("The work of a vehicle from the time it leaves a PARKING POINT after parking until its next return to park at a PARKING POINT. Any subsequent departure from a PARKING POINT after parking marks the start of a new BLOCK. The period of a BLOCK has to be covered by DUTies. Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=3:5:958 "),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>vehicle_service.block</code>
         */
        val BLOCK: Block = Block()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>vehicle_service.block.block_id</code>.
     */
    val BLOCK_ID: TableField<Record, UUID?> = createField(DSL.name("block_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "")

    /**
     * The column <code>vehicle_service.block.vehicle_service_id</code>. The
     * VEHICLE SERVICE to which this BLOCK belongs.
     */
    val VEHICLE_SERVICE_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_service_id"), SQLDataType.UUID.nullable(false), this, "The VEHICLE SERVICE to which this BLOCK belongs.")

    /**
     * The column <code>vehicle_service.block.preparing_time</code>. Preparation
     * time before start of vehicle service block.
     */
    val PREPARING_TIME: TableField<Record, YearToSecond?> = createField(DSL.name("preparing_time"), SQLDataType.INTERVAL, this, "Preparation time before start of vehicle service block.")

    /**
     * The column <code>vehicle_service.block.finishing_time</code>. Finishing
     * time after end of vehicle service block.
     */
    val FINISHING_TIME: TableField<Record, YearToSecond?> = createField(DSL.name("finishing_time"), SQLDataType.INTERVAL, this, "Finishing time after end of vehicle service block.")

    /**
     * The column <code>vehicle_service.block.vehicle_type_id</code>. Reference
     * to vehicle_type.vehicle_type.
     */
    val VEHICLE_TYPE_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_type_id"), SQLDataType.UUID, this, "Reference to vehicle_type.vehicle_type.")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>vehicle_service.block</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>vehicle_service.block</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vehicle_service.block</code> table reference
     */
    constructor(): this(DSL.name("block"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, BLOCK, null)
    override fun getSchema(): Schema? = if (aliased()) null else VehicleService.VEHICLE_SERVICE
    override fun `as`(alias: String): Block = Block(DSL.name(alias), this)
    override fun `as`(alias: Name): Block = Block(alias, this)
    override fun `as`(alias: Table<*>): Block = Block(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Block = Block(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Block = Block(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Block = Block(name.getQualifiedName(), null)
}
