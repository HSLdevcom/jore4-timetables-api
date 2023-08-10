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
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class ColumnColumnUsage(
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
         * The reference instance of
         * <code>information_schema.column_column_usage</code>
         */
        val COLUMN_COLUMN_USAGE: ColumnColumnUsage = ColumnColumnUsage()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column
     * <code>information_schema.column_column_usage.table_catalog</code>.
     */
    val TABLE_CATALOG: TableField<Record, String?> = createField(DSL.name("table_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.column_column_usage.table_schema</code>.
     */
    val TABLE_SCHEMA: TableField<Record, String?> = createField(DSL.name("table_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.column_column_usage.table_name</code>.
     */
    val TABLE_NAME: TableField<Record, String?> = createField(DSL.name("table_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.column_column_usage.column_name</code>.
     */
    val COLUMN_NAME: TableField<Record, String?> = createField(DSL.name("column_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.column_column_usage.dependent_column</code>.
     */
    val DEPENDENT_COLUMN: TableField<Record, String?> = createField(DSL.name("dependent_column"), SQLDataType.VARCHAR, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>information_schema.column_column_usage</code>
     * table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>information_schema.column_column_usage</code>
     * table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>information_schema.column_column_usage</code> table
     * reference
     */
    constructor(): this(DSL.name("column_column_usage"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, COLUMN_COLUMN_USAGE, null)
    override fun getSchema(): Schema? = if (aliased()) null else InformationSchema.INFORMATION_SCHEMA
    override fun `as`(alias: String): ColumnColumnUsage = ColumnColumnUsage(DSL.name(alias), this)
    override fun `as`(alias: Name): ColumnColumnUsage = ColumnColumnUsage(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): ColumnColumnUsage = ColumnColumnUsage(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): ColumnColumnUsage = ColumnColumnUsage(name, null)
}
