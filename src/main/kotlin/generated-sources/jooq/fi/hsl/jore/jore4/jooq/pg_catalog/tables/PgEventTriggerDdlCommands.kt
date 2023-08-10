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
open class PgEventTriggerDdlCommands(
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
         * The reference instance of
         * <code>pg_catalog.pg_event_trigger_ddl_commands</code>
         */
        val PG_EVENT_TRIGGER_DDL_COMMANDS: PgEventTriggerDdlCommands = PgEventTriggerDdlCommands()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>pg_catalog.pg_event_trigger_ddl_commands.classid</code>.
     */
    val CLASSID: TableField<Record, Long?> = createField(DSL.name("classid"), SQLDataType.BIGINT, this, "")

    /**
     * The column <code>pg_catalog.pg_event_trigger_ddl_commands.objid</code>.
     */
    val OBJID: TableField<Record, Long?> = createField(DSL.name("objid"), SQLDataType.BIGINT, this, "")

    /**
     * The column
     * <code>pg_catalog.pg_event_trigger_ddl_commands.objsubid</code>.
     */
    val OBJSUBID: TableField<Record, Int?> = createField(DSL.name("objsubid"), SQLDataType.INTEGER, this, "")

    /**
     * The column
     * <code>pg_catalog.pg_event_trigger_ddl_commands.command_tag</code>.
     */
    val COMMAND_TAG: TableField<Record, String?> = createField(DSL.name("command_tag"), SQLDataType.CLOB, this, "")

    /**
     * The column
     * <code>pg_catalog.pg_event_trigger_ddl_commands.object_type</code>.
     */
    val OBJECT_TYPE: TableField<Record, String?> = createField(DSL.name("object_type"), SQLDataType.CLOB, this, "")

    /**
     * The column
     * <code>pg_catalog.pg_event_trigger_ddl_commands.schema_name</code>.
     */
    val SCHEMA_NAME: TableField<Record, String?> = createField(DSL.name("schema_name"), SQLDataType.CLOB, this, "")

    /**
     * The column
     * <code>pg_catalog.pg_event_trigger_ddl_commands.object_identity</code>.
     */
    val OBJECT_IDENTITY: TableField<Record, String?> = createField(DSL.name("object_identity"), SQLDataType.CLOB, this, "")

    /**
     * The column
     * <code>pg_catalog.pg_event_trigger_ddl_commands.in_extension</code>.
     */
    val IN_EXTENSION: TableField<Record, Boolean?> = createField(DSL.name("in_extension"), SQLDataType.BOOLEAN, this, "")
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    val COMMAND: TableField<Record, Any?> = createField(DSL.name("command"), org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"pg_ddl_command\""), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, arrayOf(
    ))
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>pg_catalog.pg_event_trigger_ddl_commands</code>
     * table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>pg_catalog.pg_event_trigger_ddl_commands</code>
     * table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>pg_catalog.pg_event_trigger_ddl_commands</code> table
     * reference
     */
    constructor(): this(DSL.name("pg_event_trigger_ddl_commands"), null)
    override fun getSchema(): Schema? = if (aliased()) null else PgCatalog.PG_CATALOG
    override fun `as`(alias: String): PgEventTriggerDdlCommands = PgEventTriggerDdlCommands(DSL.name(alias), this, parameters)
    override fun `as`(alias: Name): PgEventTriggerDdlCommands = PgEventTriggerDdlCommands(alias, this, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: String): PgEventTriggerDdlCommands = PgEventTriggerDdlCommands(DSL.name(name), null, parameters)

    /**
     * Rename this table
     */
    override fun rename(name: Name): PgEventTriggerDdlCommands = PgEventTriggerDdlCommands(name, null, parameters)

    /**
     * Call this table-valued function
     */
    fun call(): PgEventTriggerDdlCommands = PgEventTriggerDdlCommands(DSL.name("pg_event_trigger_ddl_commands"), null, arrayOf()).let { if (aliased()) it.`as`(unqualifiedName) else it }
}
