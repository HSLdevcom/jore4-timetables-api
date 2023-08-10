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
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column
     * <code>vehicle_service.journey_patterns_in_vehicle_service.vehicle_service_id</code>.
     */
    val VEHICLE_SERVICE_ID: TableField<Record, UUID?> = createField(DSL.name("vehicle_service_id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column
     * <code>vehicle_service.journey_patterns_in_vehicle_service.journey_pattern_id</code>.
     * The journey_pattern_id from journey_pattern.journey_pattern_ref.
     *  No foreign key reference is set because the target column is not unique.
     */
    val JOURNEY_PATTERN_ID: TableField<Record, UUID?> = createField(DSL.name("journey_pattern_id"), SQLDataType.UUID.nullable(false), this, "The journey_pattern_id from journey_pattern.journey_pattern_ref.\n No foreign key reference is set because the target column is not unique.")

    /**
     * The column
     * <code>vehicle_service.journey_patterns_in_vehicle_service.reference_count</code>.
     * The amount of unique references between the journey_pattern and
     * vehicle_service.
     *   When this reaches 0 the row will be deleted.
     */
    val REFERENCE_COUNT: TableField<Record, Int?> = createField(DSL.name("reference_count"), SQLDataType.INTEGER.nullable(false), this, "The amount of unique references between the journey_pattern and vehicle_service.\n  When this reaches 0 the row will be deleted.")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

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

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, JOURNEY_PATTERNS_IN_VEHICLE_SERVICE, null)
    override fun getSchema(): Schema? = if (aliased()) null else VehicleService.VEHICLE_SERVICE
    override fun `as`(alias: String): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(DSL.name(alias), this)
    override fun `as`(alias: Name): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): JourneyPatternsInVehicleService = JourneyPatternsInVehicleService(name, null)
}
