/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.route


import fi.hsl.jore.jore4.jooq.DefaultCatalog
import fi.hsl.jore.jore4.jooq.route.tables.Direction
import fi.hsl.jore.jore4.jooq.route.tables.TypeOfLine

import kotlin.collections.List

import org.jooq.Catalog
import org.jooq.Table
import org.jooq.impl.SchemaImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Route : SchemaImpl("route", DefaultCatalog.DEFAULT_CATALOG) {
    public companion object {

        /**
         * The reference instance of <code>route</code>
         */
        val ROUTE: Route = Route()
    }

    /**
     * The route directions from Transmodel
     */
    val DIRECTION: Direction get() = Direction.DIRECTION

    /**
     * Type of line.
     * https://www.transmodel-cen.eu/model/index.htm?goto=2:1:3:424
     */
    val TYPE_OF_LINE: TypeOfLine get() = TypeOfLine.TYPE_OF_LINE

    override fun getCatalog(): Catalog = DefaultCatalog.DEFAULT_CATALOG

    override fun getTables(): List<Table<*>> = listOf(
        Direction.DIRECTION,
        TypeOfLine.TYPE_OF_LINE
    )
}
