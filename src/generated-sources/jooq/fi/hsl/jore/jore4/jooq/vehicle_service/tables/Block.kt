/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables


import fi.hsl.jore.jore4.jooq.vehicle_journey.keys.VEHICLE_JOURNEY__VEHICLE_JOURNEY_BLOCK_ID_FKEY
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.VehicleJourney.VehicleJourneyPath
import fi.hsl.jore.jore4.jooq.vehicle_service.VehicleService
import fi.hsl.jore.jore4.jooq.vehicle_service.keys.BLOCK_PKEY
import fi.hsl.jore.jore4.jooq.vehicle_service.keys.BLOCK__BLOCK_VEHICLE_SERVICE_ID_FKEY
import fi.hsl.jore.jore4.jooq.vehicle_service.keys.BLOCK__VEHICLE_TYPE_FKEY
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService.VehicleServicePath
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.records.BlockRecord
import fi.hsl.jore.jore4.jooq.vehicle_type.tables.VehicleType.VehicleTypePath

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
    path: Table<out Record>?,
    childPath: ForeignKey<out Record, BlockRecord>?,
    parentPath: InverseForeignKey<out Record, BlockRecord>?,
    aliased: Table<BlockRecord>?,
    parameters: Array<Field<*>?>?,
    where: Condition?
): TableImpl<BlockRecord>(
    alias,
    VehicleService.VEHICLE_SERVICE,
    path,
    childPath,
    parentPath,
    aliased,
    parameters,
    DSL.comment("The work of a vehicle from the time it leaves a PARKING POINT after parking until its next return to park at a PARKING POINT. Any subsequent departure from a PARKING POINT after parking marks the start of a new BLOCK. The period of a BLOCK has to be covered by DUTies. Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=3:5:958 "),
    TableOptions.table(),
    where,
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
    override fun getRecordType(): Class<BlockRecord> = BlockRecord::class.java

    /**
     * The column <code>vehicle_service.block.block_id</code>.
     */
    val BLOCK_ID: TableField<BlockRecord, UUID?> = createField(DSL.name("block_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "")

    /**
     * The column <code>vehicle_service.block.vehicle_service_id</code>. The
     * VEHICLE SERVICE to which this BLOCK belongs.
     */
    val VEHICLE_SERVICE_ID: TableField<BlockRecord, UUID?> = createField(DSL.name("vehicle_service_id"), SQLDataType.UUID.nullable(false), this, "The VEHICLE SERVICE to which this BLOCK belongs.")

    /**
     * The column <code>vehicle_service.block.preparing_time</code>. Preparation
     * time before start of vehicle service block.
     */
    val PREPARING_TIME: TableField<BlockRecord, YearToSecond?> = createField(DSL.name("preparing_time"), SQLDataType.INTERVAL, this, "Preparation time before start of vehicle service block.")

    /**
     * The column <code>vehicle_service.block.finishing_time</code>. Finishing
     * time after end of vehicle service block.
     */
    val FINISHING_TIME: TableField<BlockRecord, YearToSecond?> = createField(DSL.name("finishing_time"), SQLDataType.INTERVAL, this, "Finishing time after end of vehicle service block.")

    /**
     * The column <code>vehicle_service.block.vehicle_type_id</code>. Reference
     * to vehicle_type.vehicle_type.
     */
    val VEHICLE_TYPE_ID: TableField<BlockRecord, UUID?> = createField(DSL.name("vehicle_type_id"), SQLDataType.UUID, this, "Reference to vehicle_type.vehicle_type.")

    private constructor(alias: Name, aliased: Table<BlockRecord>?): this(alias, null, null, null, aliased, null, null)
    private constructor(alias: Name, aliased: Table<BlockRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, null, aliased, parameters, null)
    private constructor(alias: Name, aliased: Table<BlockRecord>?, where: Condition?): this(alias, null, null, null, aliased, null, where)

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

    constructor(path: Table<out Record>, childPath: ForeignKey<out Record, BlockRecord>?, parentPath: InverseForeignKey<out Record, BlockRecord>?): this(Internal.createPathAlias(path, childPath, parentPath), path, childPath, parentPath, BLOCK, null, null)

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    open class BlockPath : Block, Path<BlockRecord> {
        constructor(path: Table<out Record>, childPath: ForeignKey<out Record, BlockRecord>?, parentPath: InverseForeignKey<out Record, BlockRecord>?): super(path, childPath, parentPath)
        private constructor(alias: Name, aliased: Table<BlockRecord>): super(alias, aliased)
        override fun `as`(alias: String): BlockPath = BlockPath(DSL.name(alias), this)
        override fun `as`(alias: Name): BlockPath = BlockPath(alias, this)
        override fun `as`(alias: Table<*>): BlockPath = BlockPath(alias.qualifiedName, this)
    }
    override fun getSchema(): Schema? = if (aliased()) null else VehicleService.VEHICLE_SERVICE
    override fun getPrimaryKey(): UniqueKey<BlockRecord> = BLOCK_PKEY
    override fun getReferences(): List<ForeignKey<BlockRecord, *>> = listOf(BLOCK__BLOCK_VEHICLE_SERVICE_ID_FKEY, BLOCK__VEHICLE_TYPE_FKEY)

    private lateinit var _vehicleService: VehicleServicePath

    /**
     * Get the implicit join path to the
     * <code>vehicle_service.vehicle_service</code> table.
     */
    fun vehicleService(): VehicleServicePath {
        if (!this::_vehicleService.isInitialized)
            _vehicleService = VehicleServicePath(this, BLOCK__BLOCK_VEHICLE_SERVICE_ID_FKEY, null)

        return _vehicleService;
    }

    val vehicleService: VehicleServicePath
        get(): VehicleServicePath = vehicleService()

    private lateinit var _vehicleType: VehicleTypePath

    /**
     * Get the implicit join path to the <code>vehicle_type.vehicle_type</code>
     * table.
     */
    fun vehicleType(): VehicleTypePath {
        if (!this::_vehicleType.isInitialized)
            _vehicleType = VehicleTypePath(this, BLOCK__VEHICLE_TYPE_FKEY, null)

        return _vehicleType;
    }

    val vehicleType: VehicleTypePath
        get(): VehicleTypePath = vehicleType()

    private lateinit var _vehicleJourney: VehicleJourneyPath

    /**
     * Get the implicit to-many join path to the
     * <code>vehicle_journey.vehicle_journey</code> table
     */
    fun vehicleJourney(): VehicleJourneyPath {
        if (!this::_vehicleJourney.isInitialized)
            _vehicleJourney = VehicleJourneyPath(this, null, VEHICLE_JOURNEY__VEHICLE_JOURNEY_BLOCK_ID_FKEY.inverseKey)

        return _vehicleJourney;
    }

    val vehicleJourney: VehicleJourneyPath
        get(): VehicleJourneyPath = vehicleJourney()
    override fun `as`(alias: String): Block = Block(DSL.name(alias), this)
    override fun `as`(alias: Name): Block = Block(alias, this)
    override fun `as`(alias: Table<*>): Block = Block(alias.qualifiedName, this)

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
    override fun rename(name: Table<*>): Block = Block(name.qualifiedName, null)

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Condition?): Block = Block(qualifiedName, if (aliased()) this else null, condition)

    /**
     * Create an inline derived table from this table
     */
    override fun where(conditions: Collection<Condition>): Block = where(DSL.and(conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(vararg conditions: Condition?): Block = where(DSL.and(*conditions))

    /**
     * Create an inline derived table from this table
     */
    override fun where(condition: Field<Boolean?>?): Block = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(condition: SQL): Block = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String): Block = where(DSL.condition(condition))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg binds: Any?): Block = where(DSL.condition(condition, *binds))

    /**
     * Create an inline derived table from this table
     */
    @PlainSQL override fun where(@Stringly.SQL condition: String, vararg parts: QueryPart): Block = where(DSL.condition(condition, *parts))

    /**
     * Create an inline derived table from this table
     */
    override fun whereExists(select: Select<*>): Block = where(DSL.exists(select))

    /**
     * Create an inline derived table from this table
     */
    override fun whereNotExists(select: Select<*>): Block = where(DSL.notExists(select))
}
