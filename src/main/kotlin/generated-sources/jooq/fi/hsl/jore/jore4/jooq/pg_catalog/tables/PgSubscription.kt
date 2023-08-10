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
open class PgSubscription(
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
         * The reference instance of <code>pg_catalog.pg_subscription</code>
         */
        val PG_SUBSCRIPTION: PgSubscription = PgSubscription()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>pg_catalog.pg_subscription.subdbid</code>.
     */
    val SUBDBID: TableField<Record, Long?> = createField(DSL.name("subdbid"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_subscription.subname</code>.
     */
    val SUBNAME: TableField<Record, String?> = createField(DSL.name("subname"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_subscription.subowner</code>.
     */
    val SUBOWNER: TableField<Record, Long?> = createField(DSL.name("subowner"), SQLDataType.BIGINT.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_subscription.subenabled</code>.
     */
    val SUBENABLED: TableField<Record, Boolean?> = createField(DSL.name("subenabled"), SQLDataType.BOOLEAN.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_subscription.subslotname</code>.
     */
    val SUBSLOTNAME: TableField<Record, String?> = createField(DSL.name("subslotname"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>pg_catalog.pg_subscription.subpublications</code>.
     */
    val SUBPUBLICATIONS: TableField<Record, Array<String?>?> = createField(DSL.name("subpublications"), SQLDataType.CLOB.getArrayDataType(), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>pg_catalog.pg_subscription</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>pg_catalog.pg_subscription</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>pg_catalog.pg_subscription</code> table reference
     */
    constructor(): this(DSL.name("pg_subscription"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, PG_SUBSCRIPTION, null)
    override fun getSchema(): Schema? = if (aliased()) null else PgCatalog.PG_CATALOG
    override fun `as`(alias: String): PgSubscription = PgSubscription(DSL.name(alias), this)
    override fun `as`(alias: Name): PgSubscription = PgSubscription(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): PgSubscription = PgSubscription(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): PgSubscription = PgSubscription(name, null)
}
