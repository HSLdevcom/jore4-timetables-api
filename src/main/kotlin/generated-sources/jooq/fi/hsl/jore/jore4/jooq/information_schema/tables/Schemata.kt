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
open class Schemata(
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
         * The reference instance of <code>information_schema.schemata</code>
         */
        val SCHEMATA: Schemata = Schemata()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>information_schema.schemata.catalog_name</code>.
     */
    val CATALOG_NAME: TableField<Record, String?> = createField(DSL.name("catalog_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.schemata.schema_name</code>.
     */
    val SCHEMA_NAME: TableField<Record, String?> = createField(DSL.name("schema_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.schemata.schema_owner</code>.
     */
    val SCHEMA_OWNER: TableField<Record, String?> = createField(DSL.name("schema_owner"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.schemata.default_character_set_catalog</code>.
     */
    val DEFAULT_CHARACTER_SET_CATALOG: TableField<Record, String?> = createField(DSL.name("default_character_set_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.schemata.default_character_set_schema</code>.
     */
    val DEFAULT_CHARACTER_SET_SCHEMA: TableField<Record, String?> = createField(DSL.name("default_character_set_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.schemata.default_character_set_name</code>.
     */
    val DEFAULT_CHARACTER_SET_NAME: TableField<Record, String?> = createField(DSL.name("default_character_set_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.schemata.sql_path</code>.
     */
    val SQL_PATH: TableField<Record, String?> = createField(DSL.name("sql_path"), SQLDataType.VARCHAR, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>information_schema.schemata</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>information_schema.schemata</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>information_schema.schemata</code> table reference
     */
    constructor(): this(DSL.name("schemata"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, SCHEMATA, null)
    override fun getSchema(): Schema? = if (aliased()) null else InformationSchema.INFORMATION_SCHEMA
    override fun `as`(alias: String): Schemata = Schemata(DSL.name(alias), this)
    override fun `as`(alias: Name): Schemata = Schemata(alias, this)
    override fun `as`(alias: Table<*>): Schemata = Schemata(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Schemata = Schemata(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Schemata = Schemata(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Schemata = Schemata(name.getQualifiedName(), null)
}
