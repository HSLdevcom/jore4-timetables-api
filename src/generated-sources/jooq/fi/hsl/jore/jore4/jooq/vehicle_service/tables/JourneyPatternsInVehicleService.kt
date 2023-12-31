/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_service.tables


import fi.hsl.jore.jore4.jooq.vehicle_service.VehicleService
import fi.hsl.jore.jore4.jooq.vehicle_service.keys.JOURNEY_PATTERNS_IN_VEHICLE_SERVICE_PKEY
import fi.hsl.jore.jore4.jooq.vehicle_service.keys.JOURNEY_PATTERNS_IN_VEHICLE_SERVICE__JOURNEY_PATTERNS_IN_VEHICLE_SERVICE_VEHICLE_SERVICE_ID_FKEY
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.records.JourneyPatternsInVehicleServiceRecord

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
 * A denormalized table containing relationships between vehicle_services and
 * journey_patterns (via journey_pattern_ref.journey_pattern_id).
 *  Without this table this relationship could be found via vehicle_service
 * -&gt; block -&gt; vehicle_journey -&gt; journey_pattern_ref.
 *  Kept up to date with triggers, should not be updated manually.
 */
@Suppress("UNCHECKED_CAST")
open class JourneyPatternsInVehicleService(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, JourneyPatternsInVehicleServiceRecord>?,
    aliased: Table<JourneyPatternsInVehicleServiceRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<JourneyPatternsInVehicleServiceRecord>(
    alias,
    VehicleService.VEHICLE_SERVICE,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("A denormalized table containing relationships between vehicle_services and journey_patterns (via journey_pattern_ref.journey_pattern_id).\n Without this table this relationship could be found via vehicle_service -> block -> vehicle_journey -> journey_pattern_ref.\n Kept up to date with triggers, should not be updated manually."),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of
         * <code>vehicle_service.journey_patterns_in_vehicle_service</code>
         */
        val JOURNEY_PATTERNS_IN_VEHICLE_SERVICE: JourneyPatternsInVehicleService = JourneyPatternsInVehicleService()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<JourneyPatternsInVehicleServiceRecord> = JourneyPatternsInVehicleServiceRecord::class.java

    /**
     * The column
     * <code>vehicle_service.journey_patterns_in_vehicle_service.vehicle_service_id</code>.
     */
    val VEHICLE_SERVICE_ID: TableField<JourneyPatternsInVehicleServiceRecord, UUID?> = createField(DSL.name("vehicle_service_id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column
     * <code>vehicle_service.journey_patterns_in_vehicle_service.journey_pattern_id</code>.
     * The journey_pattern_id from journey_pattern.journey_pattern_ref.
     *  No foreign key reference is set because the target column is not unique.
     */
    val JOURNEY_PATTERN_ID: TableField<JourneyPatternsInVehicleServiceRecord, UUID?> = createField(DSL.name("journey_pattern_id"), SQLDataType.UUID.nullable(false), this, "The journey_pattern_id from journey_pattern.journey_pattern_ref.\n No foreign key reference is set because the target column is not unique.")

    /**
     * The column
     * <code>vehicle_service.journey_patterns_in_vehicle_service.reference_count</code>.
     * The amount of unique references between the journey_pattern and
     * vehicle_service.
     *   When this reaches 0 the row will be deleted.
     */
    val REFERENCE_COUNT: TableField<JourneyPatternsInVehicleServiceRecord, Int?> = createField(DSL.name("reference_count"), SQLDataType.INTEGER.nullable(false), this, "The amount of unique references between the journey_pattern and vehicle_service.\n  When this reaches 0 the row will be deleted.")

    private constructor(alias: Name, aliased: Table<JourneyPatternsInVehicleServiceRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<JourneyPatternsInVehicleServiceRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased
     * <code>vehicle_service.journey_patterns_in_vehicle_service</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased
     * <code>vehicle_service.journey_patterns_in_vehicle_service</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>vehicle_service.journey_patterns_in_vehicle_service</code>
     * table reference
     */
    constructor(): this(DSL.name("journey_patterns_in_vehicle_service"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, JourneyPatternsInVehicleServiceRecord>): this(Internal.createPathAlias(child, key), child, key, JOURNEY_PATTERNS_IN_VEHICLE_SERVICE, null)
    override fun getSchema(): Schema? = if (aliased()) null else VehicleService.VEHICLE_SERVICE
    override fun getPrimaryKey(): UniqueKey<JourneyPatternsInVehicleServiceRecord> = JOURNEY_PATTERNS_IN_VEHICLE_SERVICE_PKEY
    override fun getReferences(): List<ForeignKey<JourneyPatternsInVehicleServiceRecord, *>> = listOf(JOURNEY_PATTERNS_IN_VEHICLE_SERVICE__JOURNEY_PATTERNS_IN_VEHICLE_SERVICE_VEHICLE_SERVICE_ID_FKEY)

    private lateinit var _vehicleService: fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService

    /**
     * Get the implicit join path to the
     * <code>vehicle_service.vehicle_service</code> table.
     */
    fun vehicleService(): fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService {
        if (!this::_vehicleService.isInitialized)
            _vehicleService = fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService(this, JOURNEY_PATTERNS_IN_VEHICLE_SERVICE__JOURNEY_PATTERNS_IN_VEHICLE_SERVICE_VEHICLE_SERVICE_ID_FKEY)

        return _vehicleService;
    }

    val vehicleService: fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService
        get(): fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService = vehicleService()
    override fun `as`(alias: String): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(DSL.name(alias), this)
    override fun `as`(alias: Name): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(alias, this)
    override fun `as`(alias: Table<*>): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row3<UUID?, UUID?, Int?> = super.fieldsRow() as Row3<UUID?, UUID?, Int?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, UUID?, Int?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, UUID?, Int?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
