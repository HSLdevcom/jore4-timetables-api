/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.pg_catalog.routines


import fi.hsl.jore.jore4.jooq.pg_catalog.PgCatalog

import org.jooq.Parameter
import org.jooq.impl.AbstractRoutine
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class CurrentSchema : AbstractRoutine<String>("current_schema", PgCatalog.PG_CATALOG, SQLDataType.VARCHAR) {
    companion object {

        /**
         * The parameter <code>pg_catalog.current_schema.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<String?> = Internal.createParameter("RETURN_VALUE", SQLDataType.VARCHAR, false, false)
    }

    init {
        returnParameter = RETURN_VALUE
    }
}
