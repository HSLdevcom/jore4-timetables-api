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
open class PgStatGetBufAlloc : AbstractRoutine<Long>("pg_stat_get_buf_alloc", PgCatalog.PG_CATALOG, SQLDataType.BIGINT) {
    companion object {

        /**
         * The parameter
         * <code>pg_catalog.pg_stat_get_buf_alloc.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<Long?> = Internal.createParameter("RETURN_VALUE", SQLDataType.BIGINT, false, false)
    }

    init {
        returnParameter = RETURN_VALUE
    }
}
