/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.information_schema.tables


import fi.hsl.jore.jore4.jooq.information_schema.InformationSchema

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


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Triggers(
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
    TableOptions.view()
) {
    companion object {

        /**
         * The reference instance of <code>information_schema.triggers</code>
         */
        val TRIGGERS = Triggers()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>information_schema.triggers.trigger_catalog</code>.
     */
    val TRIGGER_CATALOG: TableField<Record, String?> = createField(DSL.name("trigger_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.trigger_schema</code>.
     */
    val TRIGGER_SCHEMA: TableField<Record, String?> = createField(DSL.name("trigger_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.trigger_name</code>.
     */
    val TRIGGER_NAME: TableField<Record, String?> = createField(DSL.name("trigger_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.event_manipulation</code>.
     */
    val EVENT_MANIPULATION: TableField<Record, String?> = createField(DSL.name("event_manipulation"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.event_object_catalog</code>.
     */
    val EVENT_OBJECT_CATALOG: TableField<Record, String?> = createField(DSL.name("event_object_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.event_object_schema</code>.
     */
    val EVENT_OBJECT_SCHEMA: TableField<Record, String?> = createField(DSL.name("event_object_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.event_object_table</code>.
     */
    val EVENT_OBJECT_TABLE: TableField<Record, String?> = createField(DSL.name("event_object_table"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.action_order</code>.
     */
    val ACTION_ORDER: TableField<Record, Int?> = createField(DSL.name("action_order"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>information_schema.triggers.action_condition</code>.
     */
    val ACTION_CONDITION: TableField<Record, String?> = createField(DSL.name("action_condition"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.action_statement</code>.
     */
    val ACTION_STATEMENT: TableField<Record, String?> = createField(DSL.name("action_statement"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.action_orientation</code>.
     */
    val ACTION_ORIENTATION: TableField<Record, String?> = createField(DSL.name("action_orientation"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.action_timing</code>.
     */
    val ACTION_TIMING: TableField<Record, String?> = createField(DSL.name("action_timing"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.action_reference_old_table</code>.
     */
    val ACTION_REFERENCE_OLD_TABLE: TableField<Record, String?> = createField(DSL.name("action_reference_old_table"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.action_reference_new_table</code>.
     */
    val ACTION_REFERENCE_NEW_TABLE: TableField<Record, String?> = createField(DSL.name("action_reference_new_table"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.action_reference_old_row</code>.
     */
    val ACTION_REFERENCE_OLD_ROW: TableField<Record, String?> = createField(DSL.name("action_reference_old_row"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.action_reference_new_row</code>.
     */
    val ACTION_REFERENCE_NEW_ROW: TableField<Record, String?> = createField(DSL.name("action_reference_new_row"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.triggers.created</code>.
     */
    val CREATED: TableField<Record, OffsetDateTime?> = createField(DSL.name("created"), SQLDataType.TIMESTAMPWITHTIMEZONE(2), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>information_schema.triggers</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>information_schema.triggers</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>information_schema.triggers</code> table reference
     */
    constructor(): this(DSL.name("triggers"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, TRIGGERS, null)
    override fun getSchema(): Schema = InformationSchema.INFORMATION_SCHEMA
    override fun `as`(alias: String): Triggers = Triggers(DSL.name(alias), this)
    override fun `as`(alias: Name): Triggers = Triggers(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Triggers = Triggers(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Triggers = Triggers(name, null)
}
