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
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class PgShdescription(
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
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>pg_catalog.pg_shdescription</code>
         */
        val PG_SHDESCRIPTION: PgShdescription = PgShdescription()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>pg_catalog.pg_shdescription.objoid</code>.
     */
    val OBJOID: TableField<Record, Long?> = createField(DSL.name("objoid"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_shdescription.classoid</code>.
     */
    val CLASSOID: TableField<Record, Long?> = createField(DSL.name("classoid"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_shdescription.description</code>.
     */
    val DESCRIPTION: TableField<Record, String?> = createField(DSL.name("description"), SQLDataType.CLOB.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>pg_catalog.pg_shdescription</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>pg_catalog.pg_shdescription</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>pg_catalog.pg_shdescription</code> table reference
     */
    constructor(): this(DSL.name("pg_shdescription"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, PG_SHDESCRIPTION, null)
    override fun getSchema(): Schema? = if (aliased()) null else PgCatalog.PG_CATALOG
    override fun `as`(alias: String): PgShdescription = PgShdescription(DSL.name(alias), this)
    override fun `as`(alias: Name): PgShdescription = PgShdescription(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): PgShdescription = PgShdescription(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): PgShdescription = PgShdescription(name, null)
}
