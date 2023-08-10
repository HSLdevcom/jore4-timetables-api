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
open class PgTsTemplate(
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
         * The reference instance of <code>pg_catalog.pg_ts_template</code>
         */
        val PG_TS_TEMPLATE: PgTsTemplate = PgTsTemplate()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>pg_catalog.pg_ts_template.oid</code>.
     */
    val OID: TableField<Record, Long?> = createField(DSL.name("oid"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_ts_template.tmplname</code>.
     */
    val TMPLNAME: TableField<Record, String?> = createField(DSL.name("tmplname"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_ts_template.tmplnamespace</code>.
     */
    val TMPLNAMESPACE: TableField<Record, Long?> = createField(DSL.name("tmplnamespace"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_ts_template.tmplinit</code>.
     */
    val TMPLINIT: TableField<Record, String?> = createField(DSL.name("tmplinit"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_ts_template.tmpllexize</code>.
     */
    val TMPLLEXIZE: TableField<Record, String?> = createField(DSL.name("tmpllexize"), SQLDataType.VARCHAR.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>pg_catalog.pg_ts_template</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>pg_catalog.pg_ts_template</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>pg_catalog.pg_ts_template</code> table reference
     */
    constructor(): this(DSL.name("pg_ts_template"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, PG_TS_TEMPLATE, null)
    override fun getSchema(): Schema? = if (aliased()) null else PgCatalog.PG_CATALOG
    override fun `as`(alias: String): PgTsTemplate = PgTsTemplate(DSL.name(alias), this)
    override fun `as`(alias: Name): PgTsTemplate = PgTsTemplate(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): PgTsTemplate = PgTsTemplate(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): PgTsTemplate = PgTsTemplate(name, null)
}
