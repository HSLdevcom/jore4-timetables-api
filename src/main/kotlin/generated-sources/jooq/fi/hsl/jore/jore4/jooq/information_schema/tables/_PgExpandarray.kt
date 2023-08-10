/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.information_schema.tables


import fi.hsl.jore.jore4.jooq.information_schema.InformationSchema

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
open class _PgExpandarray(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, Record>?,
    aliased: Table<Record>?,
    parameters: Array<Field<*>?>?
): TableImpl<Record>(
    alias,
    InformationSchema.INFORMATION_SCHEMA,
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
         * <code>information_schema._pg_expandarray</code>
         */
        val _PG_EXPANDARRAY: _PgExpandarray = _PgExpandarray()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val X: TableField<Record, Any?> = createField(DSL.name("x"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"anyelement\""), this, "")

    /**
     * The column <code>information_schema._pg_expandarray.n</code>.
     */
    val N: TableField<Record, Int?> = createField(DSL.name("n"), SQLDataType.INTEGER, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, arrayOf(
        DSL.value(null, SQLDataType.OTHER.getArrayDataType())
    ))
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>information_schema._pg_expandarray</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>information_schema._pg_expandarray</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>information_schema._pg_expandarray</code> table reference
     */
    constructor(): this(DSL.name("_pg_expandarray"), null)
    override fun getSchema(): Schema? = if (aliased()) null else InformationSchema.INFORMATION_SCHEMA
    override fun `as`(alias: String): _PgExpandarray = _PgExpandarray(DSL.name(alias), this, parameters)
    override fun `as`(alias: Name): _PgExpandarray = _PgExpandarray(alias, this, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: String): _PgExpandarray = _PgExpandarray(DSL.name(name), null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Name): _PgExpandarray = _PgExpandarray(name, null, parameters)

    /**
     * Call this table-valued function
     */
    fun call(
          __1: Array<Any?>?
    ): _PgExpandarray = _PgExpandarray(DSL.name("_pg_expandarray"), null, arrayOf(
        DSL.value(__1, SQLDataType.OTHER.getArrayDataType())
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }

    /**
     * Call this table-valued function
     */
    fun call(
          __1: Field<Array<Any?>?>
    ): _PgExpandarray = _PgExpandarray(DSL.name("_pg_expandarray"), null, arrayOf(
        __1
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }
}
