/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.pg_catalog.tables


import fi.hsl.jore.jore4.jooq.pg_catalog.PgCatalog

import java.time.OffsetDateTime

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
import org.jooq.types.YearToSecond


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class PgStatReplication(
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
         * The reference instance of <code>pg_catalog.pg_stat_replication</code>
         */
        val PG_STAT_REPLICATION: PgStatReplication = PgStatReplication()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>pg_catalog.pg_stat_replication.pid</code>.
     */
    val PID: TableField<Record, Int?> = createField(DSL.name("pid"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.usesysid</code>.
     */
    val USESYSID: TableField<Record, Long?> = createField(DSL.name("usesysid"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.usename</code>.
     */
    val USENAME: TableField<Record, String?> = createField(DSL.name("usename"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.application_name</code>.
     */
    val APPLICATION_NAME: TableField<Record, String?> = createField(DSL.name("application_name"), SQLDataType.CLOB, this, "")
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val CLIENT_ADDR: TableField<Record, Any?> = createField(DSL.name("client_addr"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"inet\""), this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.client_hostname</code>.
     */
    val CLIENT_HOSTNAME: TableField<Record, String?> = createField(DSL.name("client_hostname"), SQLDataType.CLOB, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.client_port</code>.
     */
    val CLIENT_PORT: TableField<Record, Int?> = createField(DSL.name("client_port"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.backend_start</code>.
     */
    val BACKEND_START: TableField<Record, OffsetDateTime?> = createField(DSL.name("backend_start"), SQLDataType.TIMESTAMPWITHTIMEZONE(6), this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.backend_xmin</code>.
     */
    val BACKEND_XMIN: TableField<Record, Long?> = createField(DSL.name("backend_xmin"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.state</code>.
     */
    val STATE: TableField<Record, String?> = createField(DSL.name("state"), SQLDataType.CLOB, this, "")
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val SENT_LSN: TableField<Record, Any?> = createField(DSL.name("sent_lsn"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"pg_lsn\""), this, "")
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val WRITE_LSN: TableField<Record, Any?> = createField(DSL.name("write_lsn"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"pg_lsn\""), this, "")
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val FLUSH_LSN: TableField<Record, Any?> = createField(DSL.name("flush_lsn"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"pg_lsn\""), this, "")
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val REPLAY_LSN: TableField<Record, Any?> = createField(DSL.name("replay_lsn"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"pg_lsn\""), this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.write_lag</code>.
     */
    val WRITE_LAG: TableField<Record, YearToSecond?> = createField(DSL.name("write_lag"), SQLDataType.INTERVAL, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.flush_lag</code>.
     */
    val FLUSH_LAG: TableField<Record, YearToSecond?> = createField(DSL.name("flush_lag"), SQLDataType.INTERVAL, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.replay_lag</code>.
     */
    val REPLAY_LAG: TableField<Record, YearToSecond?> = createField(DSL.name("replay_lag"), SQLDataType.INTERVAL, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.sync_priority</code>.
     */
    val SYNC_PRIORITY: TableField<Record, Int?> = createField(DSL.name("sync_priority"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.sync_state</code>.
     */
    val SYNC_STATE: TableField<Record, String?> = createField(DSL.name("sync_state"), SQLDataType.CLOB, this, "")

    /**
     * The column <code>pg_catalog.pg_stat_replication.reply_time</code>.
     */
    val REPLY_TIME: TableField<Record, OffsetDateTime?> = createField(DSL.name("reply_time"), SQLDataType.TIMESTAMPWITHTIMEZONE(6), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>pg_catalog.pg_stat_replication</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>pg_catalog.pg_stat_replication</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>pg_catalog.pg_stat_replication</code> table reference
     */
    constructor(): this(DSL.name("pg_stat_replication"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, PG_STAT_REPLICATION, null)
    override fun getSchema(): Schema? = if (aliased()) null else PgCatalog.PG_CATALOG
    override fun `as`(alias: String): PgStatReplication = PgStatReplication(DSL.name(alias), this)
    override fun `as`(alias: Name): PgStatReplication = PgStatReplication(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): PgStatReplication = PgStatReplication(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): PgStatReplication = PgStatReplication(name, null)
}
