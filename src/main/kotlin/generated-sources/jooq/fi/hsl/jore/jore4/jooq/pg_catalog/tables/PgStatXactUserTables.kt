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
open class PgStatXactUserTables(
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
         * The reference instance of
         * <code>pg_catalog.pg_stat_xact_user_tables</code>
         */
        val PG_STAT_XACT_USER_TABLES: PgStatXactUserTables = PgStatXactUserTables()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>pg_catalog.pg_stat_xact_user_tables.relid</code>.
     */
    val RELID: TableField<Record, Long?> = createField(DSL.name("relid"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_xact_user_tables.schemaname</code>.
     */
    val SCHEMANAME: TableField<Record, String?> = createField(DSL.name("schemaname"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_xact_user_tables.relname</code>.
     */
    val RELNAME: TableField<Record, String?> = createField(DSL.name("relname"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_xact_user_tables.seq_scan</code>.
     */
    val SEQ_SCAN: TableField<Record, Long?> = createField(DSL.name("seq_scan"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_xact_user_tables.seq_tup_read</code>.
     */
    val SEQ_TUP_READ: TableField<Record, Long?> = createField(DSL.name("seq_tup_read"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_xact_user_tables.idx_scan</code>.
     */
    val IDX_SCAN: TableField<Record, Long?> = createField(DSL.name("idx_scan"), SQLDataType.BIGINT, this, "")

    /**
     * The column
     * <code>pg_catalog.pg_stat_xact_user_tables.idx_tup_fetch</code>.
     */
    val IDX_TUP_FETCH: TableField<Record, Long?> = createField(DSL.name("idx_tup_fetch"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_xact_user_tables.n_tup_ins</code>.
     */
    val N_TUP_INS: TableField<Record, Long?> = createField(DSL.name("n_tup_ins"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_xact_user_tables.n_tup_upd</code>.
     */
    val N_TUP_UPD: TableField<Record, Long?> = createField(DSL.name("n_tup_upd"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_xact_user_tables.n_tup_del</code>.
     */
    val N_TUP_DEL: TableField<Record, Long?> = createField(DSL.name("n_tup_del"), SQLDataType.BIGINT, this, "")

    /**
     * The column
     * <code>pg_catalog.pg_stat_xact_user_tables.n_tup_hot_upd</code>.
     */
    val N_TUP_HOT_UPD: TableField<Record, Long?> = createField(DSL.name("n_tup_hot_upd"), SQLDataType.BIGINT, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>pg_catalog.pg_stat_xact_user_tables</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>pg_catalog.pg_stat_xact_user_tables</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>pg_catalog.pg_stat_xact_user_tables</code> table reference
     */
    constructor(): this(DSL.name("pg_stat_xact_user_tables"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, PG_STAT_XACT_USER_TABLES, null)
    override fun getSchema(): Schema? = if (aliased()) null else PgCatalog.PG_CATALOG
    override fun `as`(alias: String): PgStatXactUserTables = PgStatXactUserTables(DSL.name(alias), this)
    override fun `as`(alias: Name): PgStatXactUserTables = PgStatXactUserTables(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): PgStatXactUserTables = PgStatXactUserTables(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): PgStatXactUserTables = PgStatXactUserTables(name, null)
}
