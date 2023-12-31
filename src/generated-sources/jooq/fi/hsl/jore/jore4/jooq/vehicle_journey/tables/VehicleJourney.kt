/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_journey.tables


import fi.hsl.jore.jore4.jooq.journey_pattern.tables.JourneyPatternRef
import fi.hsl.jore.jore4.jooq.vehicle_journey.keys.VEHICLE_JOURNEY_PKEY
import fi.hsl.jore.jore4.jooq.vehicle_journey.keys.VEHICLE_JOURNEY__VEHICLE_JOURNEY_BLOCK_ID_FKEY
import fi.hsl.jore.jore4.jooq.vehicle_journey.keys.VEHICLE_JOURNEY__VEHICLE_JOURNEY_JOURNEY_PATTERN_REF_ID_FKEY
import fi.hsl.jore.jore4.jooq.vehicle_journey.keys.VEHICLE_JOURNEY__VEHICLE_JOURNEY_JOURNEY_TYPE_FKEY
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.records.VehicleJourneyRecord
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.Block

import java.util.UUID
import java.util.function.Function

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.JSONB
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row11
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
 * The planned movement of a public transport vehicle on a DAY TYPE from the
 * start point to the end point of a JOURNEY PATTERN on a specified ROUTE.
 * Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=3:1:1:831 
 */
@Suppress("UNCHECKED_CAST")
open class VehicleJourney(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, VehicleJourneyRecord>?,
    aliased: Table<VehicleJourneyRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<VehicleJourneyRecord>(
    alias,
    fi.hsl.jore.jore4.jooq.vehicle_journey.VehicleJourney.VEHICLE_JOURNEY,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("The planned movement of a public transport vehicle on a DAY TYPE from the start point to the end point of a JOURNEY PATTERN on a specified ROUTE. Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=3:1:1:831 "),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of
         * <code>vehicle_journey.vehicle_journey</code>
         */
        val VEHICLE_JOURNEY_: VehicleJourney = VehicleJourney()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<VehicleJourneyRecord> = VehicleJourneyRecord::class.java

    /**
     * The column
     * <code>vehicle_journey.vehicle_journey.vehicle_journey_id</code>.
     */
    val VEHICLE_JOURNEY_ID: TableField<VehicleJourneyRecord, UUID?> = createField(DSL.name("vehicle_journey_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "")

    /**
     * The column
     * <code>vehicle_journey.vehicle_journey.journey_pattern_ref_id</code>. The
     * JOURNEY PATTERN on which the VEHICLE JOURNEY travels
     */
    val JOURNEY_PATTERN_REF_ID: TableField<VehicleJourneyRecord, UUID?> = createField(DSL.name("journey_pattern_ref_id"), SQLDataType.UUID.nullable(false), this, "The JOURNEY PATTERN on which the VEHICLE JOURNEY travels")

    /**
     * The column <code>vehicle_journey.vehicle_journey.block_id</code>. The
     * BLOCK to which this VEHICLE JOURNEY belongs
     */
    val BLOCK_ID: TableField<VehicleJourneyRecord, UUID?> = createField(DSL.name("block_id"), SQLDataType.UUID.nullable(false), this, "The BLOCK to which this VEHICLE JOURNEY belongs")

    /**
     * The column
     * <code>vehicle_journey.vehicle_journey.journey_name_i18n</code>. Name that
     * user can give to the vehicle journey.
     */
    val JOURNEY_NAME_I18N: TableField<VehicleJourneyRecord, JSONB?> = createField(DSL.name("journey_name_i18n"), SQLDataType.JSONB, this, "Name that user can give to the vehicle journey.")

    /**
     * The column <code>vehicle_journey.vehicle_journey.turnaround_time</code>.
     * Turnaround time is the time taken by a vehicle to proceed from the end of
     * a ROUTE to the start of another.
     */
    val TURNAROUND_TIME: TableField<VehicleJourneyRecord, YearToSecond?> = createField(DSL.name("turnaround_time"), SQLDataType.INTERVAL, this, "Turnaround time is the time taken by a vehicle to proceed from the end of a ROUTE to the start of another.")

    /**
     * The column <code>vehicle_journey.vehicle_journey.layover_time</code>.
     * LAYOVER TIMEs describe a certain time allowance that may be given at the
     * end of each VEHICLE JOURNEY, before starting the next one, to compensate
     * delays or for other purposes (e.g. rest time for the driver). This
     * “layover time” can be regarded as a buffer time, which may or may not be
     * actually consumed in real time operation.
     */
    val LAYOVER_TIME: TableField<VehicleJourneyRecord, YearToSecond?> = createField(DSL.name("layover_time"), SQLDataType.INTERVAL, this, "LAYOVER TIMEs describe a certain time allowance that may be given at the end of each VEHICLE JOURNEY, before starting the next one, to compensate delays or for other purposes (e.g. rest time for the driver). This “layover time” can be regarded as a buffer time, which may or may not be actually consumed in real time operation.")

    /**
     * The column <code>vehicle_journey.vehicle_journey.journey_type</code>.
     * STANDARD | DRY_RUN | SERVICE_JOURNEY
     */
    val JOURNEY_TYPE: TableField<VehicleJourneyRecord, String?> = createField(DSL.name("journey_type"), SQLDataType.CLOB.nullable(false).defaultValue(DSL.field(DSL.raw("'STANDARD'::text"), SQLDataType.CLOB)), this, "STANDARD | DRY_RUN | SERVICE_JOURNEY")

    /**
     * The column <code>vehicle_journey.vehicle_journey.displayed_name</code>.
     * Displayed name of the journey.
     */
    val DISPLAYED_NAME: TableField<VehicleJourneyRecord, String?> = createField(DSL.name("displayed_name"), SQLDataType.CLOB, this, "Displayed name of the journey.")

    /**
     * The column
     * <code>vehicle_journey.vehicle_journey.is_vehicle_type_mandatory</code>.
     * It is required to use the same vehicle type as required in vehicle
     * service.
     */
    val IS_VEHICLE_TYPE_MANDATORY: TableField<VehicleJourneyRecord, Boolean?> = createField(DSL.name("is_vehicle_type_mandatory"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "It is required to use the same vehicle type as required in vehicle service.")

    /**
     * The column
     * <code>vehicle_journey.vehicle_journey.is_backup_journey</code>. Is the
     * journey a backup journey.
     */
    val IS_BACKUP_JOURNEY: TableField<VehicleJourneyRecord, Boolean?> = createField(DSL.name("is_backup_journey"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "Is the journey a backup journey.")

    /**
     * The column <code>vehicle_journey.vehicle_journey.is_extra_journey</code>.
     * Is the journey an extra journey.
     */
    val IS_EXTRA_JOURNEY: TableField<VehicleJourneyRecord, Boolean?> = createField(DSL.name("is_extra_journey"), SQLDataType.BOOLEAN.nullable(false).defaultValue(DSL.field(DSL.raw("false"), SQLDataType.BOOLEAN)), this, "Is the journey an extra journey.")

    private constructor(alias: Name, aliased: Table<VehicleJourneyRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<VehicleJourneyRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>vehicle_journey.vehicle_journey</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>vehicle_journey.vehicle_journey</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vehicle_journey.vehicle_journey</code> table reference
     */
    constructor(): this(DSL.name("vehicle_journey"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, VehicleJourneyRecord>): this(Internal.createPathAlias(child, key), child, key, VEHICLE_JOURNEY_, null)
    override fun getSchema(): Schema? = if (aliased()) null else fi.hsl.jore.jore4.jooq.vehicle_journey.VehicleJourney.VEHICLE_JOURNEY
    override fun getPrimaryKey(): UniqueKey<VehicleJourneyRecord> = VEHICLE_JOURNEY_PKEY
    override fun getReferences(): List<ForeignKey<VehicleJourneyRecord, *>> = listOf(VEHICLE_JOURNEY__VEHICLE_JOURNEY_JOURNEY_PATTERN_REF_ID_FKEY, VEHICLE_JOURNEY__VEHICLE_JOURNEY_BLOCK_ID_FKEY, VEHICLE_JOURNEY__VEHICLE_JOURNEY_JOURNEY_TYPE_FKEY)

    private lateinit var _journeyPatternRef: JourneyPatternRef
    private lateinit var _block: Block
    private lateinit var _journeyType: JourneyType

    /**
     * Get the implicit join path to the
     * <code>journey_pattern.journey_pattern_ref</code> table.
     */
    fun journeyPatternRef(): JourneyPatternRef {
        if (!this::_journeyPatternRef.isInitialized)
            _journeyPatternRef = JourneyPatternRef(this, VEHICLE_JOURNEY__VEHICLE_JOURNEY_JOURNEY_PATTERN_REF_ID_FKEY)

        return _journeyPatternRef;
    }

    val journeyPatternRef: JourneyPatternRef
        get(): JourneyPatternRef = journeyPatternRef()

    /**
     * Get the implicit join path to the <code>vehicle_service.block</code>
     * table.
     */
    fun block(): Block {
        if (!this::_block.isInitialized)
            _block = Block(this, VEHICLE_JOURNEY__VEHICLE_JOURNEY_BLOCK_ID_FKEY)

        return _block;
    }

    val block: Block
        get(): Block = block()

    /**
     * Get the implicit join path to the
     * <code>vehicle_journey.journey_type</code> table.
     */
    fun journeyType(): JourneyType {
        if (!this::_journeyType.isInitialized)
            _journeyType = JourneyType(this, VEHICLE_JOURNEY__VEHICLE_JOURNEY_JOURNEY_TYPE_FKEY)

        return _journeyType;
    }

    val journeyType: JourneyType
        get(): JourneyType = journeyType()
    override fun `as`(alias: String): VehicleJourney = VehicleJourney(DSL.name(alias), this)
    override fun `as`(alias: Name): VehicleJourney = VehicleJourney(alias, this)
    override fun `as`(alias: Table<*>): VehicleJourney = VehicleJourney(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): VehicleJourney = VehicleJourney(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): VehicleJourney = VehicleJourney(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): VehicleJourney = VehicleJourney(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row11 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row11<UUID?, UUID?, UUID?, JSONB?, YearToSecond?, YearToSecond?, String?, String?, Boolean?, Boolean?, Boolean?> = super.fieldsRow() as Row11<UUID?, UUID?, UUID?, JSONB?, YearToSecond?, YearToSecond?, String?, String?, Boolean?, Boolean?, Boolean?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, UUID?, UUID?, JSONB?, YearToSecond?, YearToSecond?, String?, String?, Boolean?, Boolean?, Boolean?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, UUID?, UUID?, JSONB?, YearToSecond?, YearToSecond?, String?, String?, Boolean?, Boolean?, Boolean?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
