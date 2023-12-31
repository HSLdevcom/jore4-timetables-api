/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.journey_pattern.tables


import fi.hsl.jore.jore4.jooq.journey_pattern.JourneyPattern
import fi.hsl.jore.jore4.jooq.journey_pattern.keys.JOURNEY_PATTERN_REF_PKEY
import fi.hsl.jore.jore4.jooq.journey_pattern.keys.JOURNEY_PATTERN_REF__JOURNEY_PATTERN_REF_ROUTE_DIRECTION_FKEY
import fi.hsl.jore.jore4.jooq.journey_pattern.keys.JOURNEY_PATTERN_REF__JOURNEY_PATTERN_REF_TYPE_OF_LINE_FKEY
import fi.hsl.jore.jore4.jooq.journey_pattern.tables.records.JourneyPatternRefRecord
import fi.hsl.jore.jore4.jooq.route.tables.Direction
import fi.hsl.jore.jore4.jooq.route.tables.TypeOfLine

import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID
import java.util.function.Function

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row9
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
 * Reference to a given snapshot of a JOURNEY PATTERN for a given operating day.
 * Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=2:3:4:729 
 */
@Suppress("UNCHECKED_CAST")
open class JourneyPatternRef(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, JourneyPatternRefRecord>?,
    aliased: Table<JourneyPatternRefRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<JourneyPatternRefRecord>(
    alias,
    JourneyPattern.JOURNEY_PATTERN,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("Reference to a given snapshot of a JOURNEY PATTERN for a given operating day. Transmodel: https://www.transmodel-cen.eu/model/index.htm?goto=2:3:4:729 "),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of
         * <code>journey_pattern.journey_pattern_ref</code>
         */
        val JOURNEY_PATTERN_REF: JourneyPatternRef = JourneyPatternRef()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<JourneyPatternRefRecord> = JourneyPatternRefRecord::class.java

    /**
     * The column
     * <code>journey_pattern.journey_pattern_ref.journey_pattern_ref_id</code>.
     */
    val JOURNEY_PATTERN_REF_ID: TableField<JourneyPatternRefRecord, UUID?> = createField(DSL.name("journey_pattern_ref_id"), SQLDataType.UUID.nullable(false).defaultValue(DSL.field(DSL.raw("gen_random_uuid()"), SQLDataType.UUID)), this, "")

    /**
     * The column
     * <code>journey_pattern.journey_pattern_ref.journey_pattern_id</code>. The
     * ID of the referenced JOURNEY PATTERN
     */
    val JOURNEY_PATTERN_ID: TableField<JourneyPatternRefRecord, UUID?> = createField(DSL.name("journey_pattern_id"), SQLDataType.UUID.nullable(false), this, "The ID of the referenced JOURNEY PATTERN")

    /**
     * The column
     * <code>journey_pattern.journey_pattern_ref.observation_timestamp</code>.
     * The user-given point of time used to pick one journey pattern (with route
     * and scheduled stop points) among possibly many variants. The selected,
     * unambiguous journey pattern variant is used as a basis for schedule
     * planning.
     */
    val OBSERVATION_TIMESTAMP: TableField<JourneyPatternRefRecord, OffsetDateTime?> = createField(DSL.name("observation_timestamp"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "The user-given point of time used to pick one journey pattern (with route and scheduled stop points) among possibly many variants. The selected, unambiguous journey pattern variant is used as a basis for schedule planning.")

    /**
     * The column
     * <code>journey_pattern.journey_pattern_ref.snapshot_timestamp</code>. The
     * timestamp when the snapshot was taken
     */
    val SNAPSHOT_TIMESTAMP: TableField<JourneyPatternRefRecord, OffsetDateTime?> = createField(DSL.name("snapshot_timestamp"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "The timestamp when the snapshot was taken")

    /**
     * The column <code>journey_pattern.journey_pattern_ref.type_of_line</code>.
     * The type of line (GTFS route type):
     * https://developers.google.com/transit/gtfs/reference/extended-route-types
     */
    val TYPE_OF_LINE: TableField<JourneyPatternRefRecord, String?> = createField(DSL.name("type_of_line"), SQLDataType.CLOB.nullable(false), this, "The type of line (GTFS route type): https://developers.google.com/transit/gtfs/reference/extended-route-types")

    /**
     * The column <code>journey_pattern.journey_pattern_ref.route_label</code>.
     * The label of the route associated with the referenced journey pattern
     */
    val ROUTE_LABEL: TableField<JourneyPatternRefRecord, String?> = createField(DSL.name("route_label"), SQLDataType.CLOB.nullable(false), this, "The label of the route associated with the referenced journey pattern")

    /**
     * The column
     * <code>journey_pattern.journey_pattern_ref.route_direction</code>. The
     * direction of the route associated with the referenced journey pattern
     */
    val ROUTE_DIRECTION: TableField<JourneyPatternRefRecord, String?> = createField(DSL.name("route_direction"), SQLDataType.CLOB.nullable(false), this, "The direction of the route associated with the referenced journey pattern")

    /**
     * The column
     * <code>journey_pattern.journey_pattern_ref.route_validity_start</code>.
     * The start date of the validity period of the route associated with the
     * referenced journey pattern. If NULL, then the start of the validity
     * period is unbounded (-infinity).
     */
    val ROUTE_VALIDITY_START: TableField<JourneyPatternRefRecord, LocalDate?> = createField(DSL.name("route_validity_start"), SQLDataType.LOCALDATE, this, "The start date of the validity period of the route associated with the referenced journey pattern. If NULL, then the start of the validity period is unbounded (-infinity).")

    /**
     * The column
     * <code>journey_pattern.journey_pattern_ref.route_validity_end</code>. The
     * end date of the validity period of the route associated with the
     * referenced journey pattern. If NULL, then the end of the validity period
     * is unbounded (infinity).
     */
    val ROUTE_VALIDITY_END: TableField<JourneyPatternRefRecord, LocalDate?> = createField(DSL.name("route_validity_end"), SQLDataType.LOCALDATE, this, "The end date of the validity period of the route associated with the referenced journey pattern. If NULL, then the end of the validity period is unbounded (infinity).")

    private constructor(alias: Name, aliased: Table<JourneyPatternRefRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<JourneyPatternRefRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>journey_pattern.journey_pattern_ref</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>journey_pattern.journey_pattern_ref</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>journey_pattern.journey_pattern_ref</code> table reference
     */
    constructor(): this(DSL.name("journey_pattern_ref"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, JourneyPatternRefRecord>): this(Internal.createPathAlias(child, key), child, key, JOURNEY_PATTERN_REF, null)
    override fun getSchema(): Schema? = if (aliased()) null else JourneyPattern.JOURNEY_PATTERN
    override fun getPrimaryKey(): UniqueKey<JourneyPatternRefRecord> = JOURNEY_PATTERN_REF_PKEY
    override fun getReferences(): List<ForeignKey<JourneyPatternRefRecord, *>> = listOf(JOURNEY_PATTERN_REF__JOURNEY_PATTERN_REF_TYPE_OF_LINE_FKEY, JOURNEY_PATTERN_REF__JOURNEY_PATTERN_REF_ROUTE_DIRECTION_FKEY)

    private lateinit var _typeOfLine: TypeOfLine
    private lateinit var _direction: Direction

    /**
     * Get the implicit join path to the <code>route.type_of_line</code> table.
     */
    fun typeOfLine(): TypeOfLine {
        if (!this::_typeOfLine.isInitialized)
            _typeOfLine = TypeOfLine(this, JOURNEY_PATTERN_REF__JOURNEY_PATTERN_REF_TYPE_OF_LINE_FKEY)

        return _typeOfLine;
    }

    val typeOfLine: TypeOfLine
        get(): TypeOfLine = typeOfLine()

    /**
     * Get the implicit join path to the <code>route.direction</code> table.
     */
    fun direction(): Direction {
        if (!this::_direction.isInitialized)
            _direction = Direction(this, JOURNEY_PATTERN_REF__JOURNEY_PATTERN_REF_ROUTE_DIRECTION_FKEY)

        return _direction;
    }

    val direction: Direction
        get(): Direction = direction()
    override fun `as`(alias: String): JourneyPatternRef = JourneyPatternRef(DSL.name(alias), this)
    override fun `as`(alias: Name): JourneyPatternRef = JourneyPatternRef(alias, this)
    override fun `as`(alias: Table<*>): JourneyPatternRef = JourneyPatternRef(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): JourneyPatternRef = JourneyPatternRef(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): JourneyPatternRef = JourneyPatternRef(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): JourneyPatternRef = JourneyPatternRef(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row9<UUID?, UUID?, OffsetDateTime?, OffsetDateTime?, String?, String?, String?, LocalDate?, LocalDate?> = super.fieldsRow() as Row9<UUID?, UUID?, OffsetDateTime?, OffsetDateTime?, String?, String?, String?, LocalDate?, LocalDate?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (UUID?, UUID?, OffsetDateTime?, OffsetDateTime?, String?, String?, String?, LocalDate?, LocalDate?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (UUID?, UUID?, OffsetDateTime?, OffsetDateTime?, String?, String?, String?, LocalDate?, LocalDate?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
