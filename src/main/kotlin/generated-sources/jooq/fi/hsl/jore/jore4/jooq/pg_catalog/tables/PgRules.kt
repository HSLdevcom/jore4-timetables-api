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
open class PgRules(
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
    TableOptions.view()
) {
    companion object {

        /**
         * The reference instance of <code>pg_catalog.pg_rules</code>
         */
        val PG_RULES: PgRules = PgRules()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>pg_catalog.pg_rules.schemaname</code>.
     */
    val SCHEMANAME: TableField<Record, String?> = createField(DSL.name("schemaname"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>pg_catalog.pg_rules.tablename</code>.
     */
    val TABLENAME: TableField<Record, String?> = createField(DSL.name("tablename"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>pg_catalog.pg_rules.rulename</code>.
     */
    val RULENAME: TableField<Record, String?> = createField(DSL.name("rulename"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>pg_catalog.pg_rules.definition</code>.
     */
    val DEFINITION: TableField<Record, String?> = createField(DSL.name("definition"), SQLDataType.CLOB, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>pg_catalog.pg_rules</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>pg_catalog.pg_rules</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>pg_catalog.pg_rules</code> table reference
     */
    constructor(): this(DSL.name("pg_rules"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, PG_RULES, null)
    override fun getSchema(): Schema? = if (aliased()) null else PgCatalog.PG_CATALOG
    override fun `as`(alias: String): PgRules = PgRules(DSL.name(alias), this)
    override fun `as`(alias: Name): PgRules = PgRules(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): PgRules = PgRules(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): PgRules = PgRules(name, null)
}
