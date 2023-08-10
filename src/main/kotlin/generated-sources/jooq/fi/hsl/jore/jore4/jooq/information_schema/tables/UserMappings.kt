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
open class UserMappings(
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
         * <code>information_schema.user_mappings</code>
         */
        val USER_MAPPINGS: UserMappings = UserMappings()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column
     * <code>information_schema.user_mappings.authorization_identifier</code>.
     */
    val AUTHORIZATION_IDENTIFIER: TableField<Record, String?> = createField(DSL.name("authorization_identifier"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.user_mappings.foreign_server_catalog</code>.
     */
    val FOREIGN_SERVER_CATALOG: TableField<Record, String?> = createField(DSL.name("foreign_server_catalog"), SQLDataType.VARCHAR, this, "")

    /**
     * The column
     * <code>information_schema.user_mappings.foreign_server_name</code>.
     */
    val FOREIGN_SERVER_NAME: TableField<Record, String?> = createField(DSL.name("foreign_server_name"), SQLDataType.VARCHAR, this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>information_schema.user_mappings</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>information_schema.user_mappings</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>information_schema.user_mappings</code> table reference
     */
    constructor(): this(DSL.name("user_mappings"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, USER_MAPPINGS, null)
    override fun getSchema(): Schema? = if (aliased()) null else InformationSchema.INFORMATION_SCHEMA
    override fun `as`(alias: String): UserMappings = UserMappings(DSL.name(alias), this)
    override fun `as`(alias: Name): UserMappings = UserMappings(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): UserMappings = UserMappings(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): UserMappings = UserMappings(name, null)
}
