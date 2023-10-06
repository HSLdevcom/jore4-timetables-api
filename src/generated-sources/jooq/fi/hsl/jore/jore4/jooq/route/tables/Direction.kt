/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.route.tables


import fi.hsl.jore.jore4.jooq.route.Route
import fi.hsl.jore.jore4.jooq.route.keys.DIRECTION_PKEY
import fi.hsl.jore.jore4.jooq.route.keys.DIRECTION__DIRECTION_THE_OPPOSITE_OF_DIRECTION_FKEY
import fi.hsl.jore.jore4.jooq.route.tables.records.DirectionRecord

import java.util.function.Function

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row2
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
 * The route directions from Transmodel
 */
@Suppress("UNCHECKED_CAST")
open class Direction(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, DirectionRecord>?,
    aliased: Table<DirectionRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<DirectionRecord>(
    alias,
    Route.ROUTE,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("The route directions from Transmodel"),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>route.direction</code>
         */
        val DIRECTION: Direction = Direction()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<DirectionRecord> = DirectionRecord::class.java

    /**
     * The column <code>route.direction.direction</code>. The name of the route
     * direction
     */
    val DIRECTION_: TableField<DirectionRecord, String?> = createField(DSL.name("direction"), SQLDataType.CLOB.nullable(false), this, "The name of the route direction")

    /**
     * The column <code>route.direction.the_opposite_of_direction</code>. The
     * opposite direction
     */
    val THE_OPPOSITE_OF_DIRECTION: TableField<DirectionRecord, String?> = createField(DSL.name("the_opposite_of_direction"), SQLDataType.CLOB, this, "The opposite direction")

    private constructor(alias: Name, aliased: Table<DirectionRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<DirectionRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>route.direction</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>route.direction</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>route.direction</code> table reference
     */
    constructor(): this(DSL.name("direction"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, DirectionRecord>): this(Internal.createPathAlias(child, key), child, key, DIRECTION, null)
    override fun getSchema(): Schema? = if (aliased()) null else Route.ROUTE
    override fun getPrimaryKey(): UniqueKey<DirectionRecord> = DIRECTION_PKEY
    override fun getReferences(): List<ForeignKey<DirectionRecord, *>> = listOf(DIRECTION__DIRECTION_THE_OPPOSITE_OF_DIRECTION_FKEY)

    private lateinit var _direction: Direction

    /**
     * Get the implicit join path to the <code>route.direction</code> table.
     */
    fun direction(): Direction {
        if (!this::_direction.isInitialized)
            _direction = Direction(this, DIRECTION__DIRECTION_THE_OPPOSITE_OF_DIRECTION_FKEY)

        return _direction;
    }

    val direction: Direction
        get(): Direction = direction()
    override fun `as`(alias: String): Direction = Direction(DSL.name(alias), this)
    override fun `as`(alias: Name): Direction = Direction(alias, this)
    override fun `as`(alias: Table<*>): Direction = Direction(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Direction = Direction(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Direction = Direction(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Direction = Direction(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row2<String?, String?> = super.fieldsRow() as Row2<String?, String?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (String?, String?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (String?, String?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
