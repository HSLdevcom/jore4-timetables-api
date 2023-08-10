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
open class ApplicableRoles(
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
         * <code>information_schema.applicable_roles</code>
         */
        val APPLICABLE_ROLES: ApplicableRoles = ApplicableRoles()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>information_schema.applicable_roles.grantee</code>.
     */
    val GRANTEE: TableField<Record, String?> = createField(DSL.name("grantee"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.applicable_roles.role_name</code>.
     */
    val ROLE_NAME: TableField<Record, String?> = createField(DSL.name("role_name"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>information_schema.applicable_roles.is_grantable</code>.
     */
    val IS_GRANTABLE: TableField<Record, String?> = createField(DSL.name("is_grantable"), SQLDataType.VARCHAR(3), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>information_schema.applicable_roles</code> table
     * reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>information_schema.applicable_roles</code> table
     * reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>information_schema.applicable_roles</code> table reference
     */
    constructor(): this(DSL.name("applicable_roles"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, APPLICABLE_ROLES, null)
    override fun getSchema(): Schema? = if (aliased()) null else InformationSchema.INFORMATION_SCHEMA
    override fun `as`(alias: String): ApplicableRoles = ApplicableRoles(DSL.name(alias), this)
    override fun `as`(alias: Name): ApplicableRoles = ApplicableRoles(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): ApplicableRoles = ApplicableRoles(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): ApplicableRoles = ApplicableRoles(name, null)
}
