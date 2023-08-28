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
open class Columns(
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
         * The reference instance of <code>information_schema.columns</code>
         */
        val COLUMNS: Columns = Columns()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>information_schema.columns.table_catalog</code>.
     */
    val TABLE_CATALOG: TableField<Record, String?> = createField(DSL.name("table_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.table_schema</code>.
     */
    val TABLE_SCHEMA: TableField<Record, String?> = createField(DSL.name("table_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.table_name</code>.
     */
    val TABLE_NAME: TableField<Record, String?> = createField(DSL.name("table_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.column_name</code>.
     */
    val COLUMN_NAME: TableField<Record, String?> = createField(DSL.name("column_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.ordinal_position</code>.
     */
    val ORDINAL_POSITION: TableField<Record, Int?> = createField(DSL.name("ordinal_position"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>information_schema.columns.column_default</code>.
     */
    val COLUMN_DEFAULT: TableField<Record, String?> = createField(DSL.name("column_default"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.is_nullable</code>.
     */
    val IS_NULLABLE: TableField<Record, String?> = createField(DSL.name("is_nullable"), SQLDataType.VARCHAR(3), this, "")

    /**
     * The column <code>information_schema.columns.data_type</code>.
     */
    val DATA_TYPE: TableField<Record, String?> = createField(DSL.name("data_type"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.columns.character_maximum_length</code>.
     */
    val CHARACTER_MAXIMUM_LENGTH: TableField<Record, Int?> = createField(DSL.name("character_maximum_length"), SQLDataType.INTEGER, this, "")

    /**
     * The column
     * <code>information_schema.columns.character_octet_length</code>.
     */
    val CHARACTER_OCTET_LENGTH: TableField<Record, Int?> = createField(DSL.name("character_octet_length"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>information_schema.columns.numeric_precision</code>.
     */
    val NUMERIC_PRECISION: TableField<Record, Int?> = createField(DSL.name("numeric_precision"), SQLDataType.INTEGER, this, "")

    /**
     * The column
     * <code>information_schema.columns.numeric_precision_radix</code>.
     */
    val NUMERIC_PRECISION_RADIX: TableField<Record, Int?> = createField(DSL.name("numeric_precision_radix"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>information_schema.columns.numeric_scale</code>.
     */
    val NUMERIC_SCALE: TableField<Record, Int?> = createField(DSL.name("numeric_scale"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>information_schema.columns.datetime_precision</code>.
     */
    val DATETIME_PRECISION: TableField<Record, Int?> = createField(DSL.name("datetime_precision"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>information_schema.columns.interval_type</code>.
     */
    val INTERVAL_TYPE: TableField<Record, String?> = createField(DSL.name("interval_type"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.interval_precision</code>.
     */
    val INTERVAL_PRECISION: TableField<Record, Int?> = createField(DSL.name("interval_precision"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>information_schema.columns.character_set_catalog</code>.
     */
    val CHARACTER_SET_CATALOG: TableField<Record, String?> = createField(DSL.name("character_set_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.character_set_schema</code>.
     */
    val CHARACTER_SET_SCHEMA: TableField<Record, String?> = createField(DSL.name("character_set_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.character_set_name</code>.
     */
    val CHARACTER_SET_NAME: TableField<Record, String?> = createField(DSL.name("character_set_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.collation_catalog</code>.
     */
    val COLLATION_CATALOG: TableField<Record, String?> = createField(DSL.name("collation_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.collation_schema</code>.
     */
    val COLLATION_SCHEMA: TableField<Record, String?> = createField(DSL.name("collation_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.collation_name</code>.
     */
    val COLLATION_NAME: TableField<Record, String?> = createField(DSL.name("collation_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.domain_catalog</code>.
     */
    val DOMAIN_CATALOG: TableField<Record, String?> = createField(DSL.name("domain_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.domain_schema</code>.
     */
    val DOMAIN_SCHEMA: TableField<Record, String?> = createField(DSL.name("domain_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.domain_name</code>.
     */
    val DOMAIN_NAME: TableField<Record, String?> = createField(DSL.name("domain_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.udt_catalog</code>.
     */
    val UDT_CATALOG: TableField<Record, String?> = createField(DSL.name("udt_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.udt_schema</code>.
     */
    val UDT_SCHEMA: TableField<Record, String?> = createField(DSL.name("udt_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.udt_name</code>.
     */
    val UDT_NAME: TableField<Record, String?> = createField(DSL.name("udt_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.scope_catalog</code>.
     */
    val SCOPE_CATALOG: TableField<Record, String?> = createField(DSL.name("scope_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.scope_schema</code>.
     */
    val SCOPE_SCHEMA: TableField<Record, String?> = createField(DSL.name("scope_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.scope_name</code>.
     */
    val SCOPE_NAME: TableField<Record, String?> = createField(DSL.name("scope_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.maximum_cardinality</code>.
     */
    val MAXIMUM_CARDINALITY: TableField<Record, Int?> = createField(DSL.name("maximum_cardinality"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>information_schema.columns.dtd_identifier</code>.
     */
    val DTD_IDENTIFIER: TableField<Record, String?> = createField(DSL.name("dtd_identifier"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.is_self_referencing</code>.
     */
    val IS_SELF_REFERENCING: TableField<Record, String?> = createField(DSL.name("is_self_referencing"), SQLDataType.VARCHAR(3), this, "")

    /**
     * The column <code>information_schema.columns.is_identity</code>.
     */
    val IS_IDENTITY: TableField<Record, String?> = createField(DSL.name("is_identity"), SQLDataType.VARCHAR(3), this, "")

    /**
     * The column <code>information_schema.columns.identity_generation</code>.
     */
    val IDENTITY_GENERATION: TableField<Record, String?> = createField(DSL.name("identity_generation"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.identity_start</code>.
     */
    val IDENTITY_START: TableField<Record, String?> = createField(DSL.name("identity_start"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.identity_increment</code>.
     */
    val IDENTITY_INCREMENT: TableField<Record, String?> = createField(DSL.name("identity_increment"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.identity_maximum</code>.
     */
    val IDENTITY_MAXIMUM: TableField<Record, String?> = createField(DSL.name("identity_maximum"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.identity_minimum</code>.
     */
    val IDENTITY_MINIMUM: TableField<Record, String?> = createField(DSL.name("identity_minimum"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.identity_cycle</code>.
     */
    val IDENTITY_CYCLE: TableField<Record, String?> = createField(DSL.name("identity_cycle"), SQLDataType.VARCHAR(3), this, "")

    /**
     * The column <code>information_schema.columns.is_generated</code>.
     */
    val IS_GENERATED: TableField<Record, String?> = createField(DSL.name("is_generated"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.generation_expression</code>.
     */
    val GENERATION_EXPRESSION: TableField<Record, String?> = createField(DSL.name("generation_expression"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.columns.is_updatable</code>.
     */
    val IS_UPDATABLE: TableField<Record, String?> = createField(DSL.name("is_updatable"), SQLDataType.VARCHAR(3), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>information_schema.columns</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>information_schema.columns</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>information_schema.columns</code> table reference
     */
    constructor(): this(DSL.name("columns"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, COLUMNS, null)
    override fun getSchema(): Schema? = if (aliased()) null else InformationSchema.INFORMATION_SCHEMA
    override fun `as`(alias: String): Columns = Columns(DSL.name(alias), this)
    override fun `as`(alias: Name): Columns = Columns(alias, this)
    override fun `as`(alias: Table<*>): Columns = Columns(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Columns = Columns(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Columns = Columns(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Columns = Columns(name.getQualifiedName(), null)
}
