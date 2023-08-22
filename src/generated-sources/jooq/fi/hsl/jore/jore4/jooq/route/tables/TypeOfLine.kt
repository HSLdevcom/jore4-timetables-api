/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.route.tables


import fi.hsl.jore.jore4.jooq.route.Route
import fi.hsl.jore.jore4.jooq.route.keys.TYPE_OF_LINE_PKEY
import fi.hsl.jore.jore4.jooq.route.tables.records.TypeOfLineRecord

import java.util.function.Function

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Records
import org.jooq.Row1
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
 * Type of line. https://www.transmodel-cen.eu/model/index.htm?goto=2:1:3:424
 */
@Suppress("UNCHECKED_CAST")
open class TypeOfLine(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, TypeOfLineRecord>?,
    aliased: Table<TypeOfLineRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<TypeOfLineRecord>(
    alias,
    Route.ROUTE,
    child,
    path,
    aliased,
    parameters,
    DSL.comment("Type of line. https://www.transmodel-cen.eu/model/index.htm?goto=2:1:3:424"),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>route.type_of_line</code>
         */
        val TYPE_OF_LINE: TypeOfLine = TypeOfLine()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<TypeOfLineRecord> = TypeOfLineRecord::class.java

    /**
     * The column <code>route.type_of_line.type_of_line</code>. GTFS route type:
     * https://developers.google.com/transit/gtfs/reference/extended-route-types
     */
    val TYPE_OF_LINE_: TableField<TypeOfLineRecord, String?> = createField(DSL.name("type_of_line"), SQLDataType.CLOB.nullable(false), this, "GTFS route type: https://developers.google.com/transit/gtfs/reference/extended-route-types")

    private constructor(alias: Name, aliased: Table<TypeOfLineRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<TypeOfLineRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>route.type_of_line</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>route.type_of_line</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>route.type_of_line</code> table reference
     */
    constructor(): this(DSL.name("type_of_line"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, TypeOfLineRecord>): this(Internal.createPathAlias(child, key), child, key, TYPE_OF_LINE, null)
    override fun getSchema(): Schema? = if (aliased()) null else Route.ROUTE
    override fun getPrimaryKey(): UniqueKey<TypeOfLineRecord> = TYPE_OF_LINE_PKEY
    override fun `as`(alias: String): TypeOfLine = TypeOfLine(DSL.name(alias), this)
    override fun `as`(alias: Name): TypeOfLine = TypeOfLine(alias, this)
    override fun `as`(alias: Table<*>): TypeOfLine = TypeOfLine(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): TypeOfLine = TypeOfLine(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): TypeOfLine = TypeOfLine(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): TypeOfLine = TypeOfLine(name.getQualifiedName(), null)

    // -------------------------------------------------------------------------
    // Row1 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row1<String?> = super.fieldsRow() as Row1<String?>

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    fun <U> mapping(from: (String?) -> U): SelectField<U> = convertFrom(Records.mapping(from))

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    fun <U> mapping(toType: Class<U>, from: (String?) -> U): SelectField<U> = convertFrom(toType, Records.mapping(from))
}
