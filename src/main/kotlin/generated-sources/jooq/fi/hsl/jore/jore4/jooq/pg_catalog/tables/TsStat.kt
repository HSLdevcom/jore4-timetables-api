/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.pg_catalog.tables


import fi.hsl.jore.jore4.jooq.pg_catalog.PgCatalog

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
open class TsStat(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, Record>?,
    aliased: Table<Record>?,
    parameters: Array<Field<*>?>?
): TableImpl<Record>(
    alias,
    PgCatalog.PG_CATALOG,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.function()
) {
    companion object {

        /**
         * The reference instance of <code>pg_catalog.ts_stat</code>
         */
        val TS_STAT: TsStat = TsStat()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>pg_catalog.ts_stat.word</code>.
     */
    val WORD: TableField<Record, String?> = createField(DSL.name("word"), SQLDataType.CLOB, this, "")

    /**
     * The column <code>pg_catalog.ts_stat.ndoc</code>.
     */
    val NDOC: TableField<Record, Int?> = createField(DSL.name("ndoc"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>pg_catalog.ts_stat.nentry</code>.
     */
    val NENTRY: TableField<Record, Int?> = createField(DSL.name("nentry"), SQLDataType.INTEGER, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, arrayOf(
        DSL.value(null, SQLDataType.CLOB)
    ))
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>pg_catalog.ts_stat</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>pg_catalog.ts_stat</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>pg_catalog.ts_stat</code> table reference
     */
    constructor(): this(DSL.name("ts_stat"), null)
    override fun getSchema(): Schema? = if (aliased()) null else PgCatalog.PG_CATALOG
    override fun `as`(alias: String): TsStat = TsStat(DSL.name(alias), this, parameters)
    override fun `as`(alias: Name): TsStat = TsStat(alias, this, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: String): TsStat = TsStat(DSL.name(name), null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Name): TsStat = TsStat(name, null, parameters)

    /**
     * Call this table-valued function
     */
    fun call(
          query: String?
    ): TsStat = TsStat(DSL.name("ts_stat"), null, arrayOf(
        DSL.value(query, SQLDataType.CLOB)
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }

    /**
     * Call this table-valued function
     */
    fun call(
          query: Field<String?>
    ): TsStat = TsStat(DSL.name("ts_stat"), null, arrayOf(
        query
    )).let { if (aliased()) it.`as`(unqualifiedName) else it }
}
