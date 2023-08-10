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
open class PgReplicationOriginSessionSetup : AbstractRoutine<java.lang.Void>("pg_replication_origin_session_setup", PgCatalog.PG_CATALOG) {
    companion object {

        /**
         * The parameter
         * <code>pg_catalog.pg_replication_origin_session_setup._1</code>.
         */
        val _1: Parameter<String?> = Internal.createParameter("_1", SQLDataType.CLOB, false, true)
    }

    init {
        addInParameter(_1)
    }

    /**
     * Set the <code>_1</code> parameter IN value to the routine
     */
    fun set__1(value: String?): Unit = setValue(_1, value)
}
