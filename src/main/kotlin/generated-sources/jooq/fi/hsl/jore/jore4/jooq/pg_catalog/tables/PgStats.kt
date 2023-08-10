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
open class PgStats(
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
         * The reference instance of <code>pg_catalog.pg_stats</code>
         */
        val PG_STATS: PgStats = PgStats()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<Record> = Record::class.java

    /**
     * The column <code>pg_catalog.pg_stats.schemaname</code>.
     */
    val SCHEMANAME: TableField<Record, String?> = createField(DSL.name("schemaname"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>pg_catalog.pg_stats.tablename</code>.
     */
    val TABLENAME: TableField<Record, String?> = createField(DSL.name("tablename"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>pg_catalog.pg_stats.attname</code>.
     */
    val ATTNAME: TableField<Record, String?> = createField(DSL.name("attname"), SQLDataType.VARCHAR, this, "")

    /**
     * The column <code>pg_catalog.pg_stats.inherited</code>.
     */
    val INHERITED: TableField<Record, Boolean?> = createField(DSL.name("inherited"), SQLDataType.BOOLEAN, this, "")

    /**
     * The column <code>pg_catalog.pg_stats.null_frac</code>.
     */
    val NULL_FRAC: TableField<Record, Float?> = createField(DSL.name("null_frac"), SQLDataType.REAL, this, "")

    /**
     * The column <code>pg_catalog.pg_stats.avg_width</code>.
     */
    val AVG_WIDTH: TableField<Record, Int?> = createField(DSL.name("avg_width"), SQLDataType.INTEGER, this, "")

    /**
     * The column <code>pg_catalog.pg_stats.n_distinct</code>.
     */
    val N_DISTINCT: TableField<Record, Float?> = createField(DSL.name("n_distinct"), SQLDataType.REAL, this, "")

    /**
     * The column <code>pg_catalog.pg_stats.most_common_vals</code>.
     */
    val MOST_COMMON_VALS: TableField<Record, Array<Any?>?> = createField(DSL.name("most_common_vals"), SQLDataType.OTHER.getArrayDataType(), this, "")

    /**
     * The column <code>pg_catalog.pg_stats.most_common_freqs</code>.
     */
    val MOST_COMMON_FREQS: TableField<Record, Array<Float?>?> = createField(DSL.name("most_common_freqs"), SQLDataType.REAL.getArrayDataType(), this, "")

    /**
     * The column <code>pg_catalog.pg_stats.histogram_bounds</code>.
     */
    val HISTOGRAM_BOUNDS: TableField<Record, Array<Any?>?> = createField(DSL.name("histogram_bounds"), SQLDataType.OTHER.getArrayDataType(), this, "")

    /**
     * The column <code>pg_catalog.pg_stats.correlation</code>.
     */
    val CORRELATION: TableField<Record, Float?> = createField(DSL.name("correlation"), SQLDataType.REAL, this, "")

    /**
     * The column <code>pg_catalog.pg_stats.most_common_elems</code>.
     */
    val MOST_COMMON_ELEMS: TableField<Record, Array<Any?>?> = createField(DSL.name("most_common_elems"), SQLDataType.OTHER.getArrayDataType(), this, "")

    /**
     * The column <code>pg_catalog.pg_stats.most_common_elem_freqs</code>.
     */
    val MOST_COMMON_ELEM_FREQS: TableField<Record, Array<Float?>?> = createField(DSL.name("most_common_elem_freqs"), SQLDataType.REAL.getArrayDataType(), this, "")

    /**
     * The column <code>pg_catalog.pg_stats.elem_count_histogram</code>.
     */
    val ELEM_COUNT_HISTOGRAM: TableField<Record, Array<Float?>?> = createField(DSL.name("elem_count_histogram"), SQLDataType.REAL.getArrayDataType(), this, "")

    private constructor(alias: Name, aliased: Table<Record>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<Record>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>pg_catalog.pg_stats</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>pg_catalog.pg_stats</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>pg_catalog.pg_stats</code> table reference
     */
    constructor(): this(DSL.name("pg_stats"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, Record>): this(Internal.createPathAlias(child, key), child, key, PG_STATS, null)
    override fun getSchema(): Schema? = if (aliased()) null else PgCatalog.PG_CATALOG
    override fun `as`(alias: String): PgStats = PgStats(DSL.name(alias), this)
    override fun `as`(alias: Name): PgStats = PgStats(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): PgStats = PgStats(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): PgStats = PgStats(name, null)
}
