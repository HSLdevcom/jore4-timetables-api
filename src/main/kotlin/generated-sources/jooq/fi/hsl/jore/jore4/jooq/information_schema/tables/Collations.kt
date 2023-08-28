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
open class Collations(
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
         * The reference instance of <code>information_schema.collations</code>
         */
        val COLLATIONS: Collations = Collations()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>information_schema.collations.collation_catalog</code>.
     */
    val COLLATION_CATALOG: TableField<Record, String?> = createField(DSL.name("collation_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.collations.collation_schema</code>.
     */
    val COLLATION_SCHEMA: TableField<Record, String?> = createField(DSL.name("collation_schema"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.collations.collation_name</code>.
     */
    val COLLATION_NAME: TableField<Record, String?> = createField(DSL.name("collation_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.collations.pad_attribute</code>.
     */
    val PAD_ATTRIBUTE: TableField<Record, String?> = createField(DSL.name("pad_attribute"), SQLDataType.VARCHAR, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>information_schema.collations</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>information_schema.collations</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>information_schema.collations</code> table reference
     */
    constructor(): this(DSL.name("collations"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, COLLATIONS, null)
    override fun getSchema(): Schema? = if (aliased()) null else InformationSchema.INFORMATION_SCHEMA
    override fun `as`(alias: String): Collations = Collations(DSL.name(alias), this)
    override fun `as`(alias: Name): Collations = Collations(alias, this)
    override fun `as`(alias: Table<*>): Collations = Collations(alias.getQualifiedName(), this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Collations = Collations(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Collations = Collations(name, null)

    /**
     * Rename this table
     */
    override fun rename(name: Table<*>): Collations = Collations(name.getQualifiedName(), null)
}
