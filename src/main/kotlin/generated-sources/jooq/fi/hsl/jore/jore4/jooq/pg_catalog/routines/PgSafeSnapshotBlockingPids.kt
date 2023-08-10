/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.pg_catalog.routines


import fi.hsl.jore.jore4.jooq.pg_catalog.PgCatalog

import org.jooq.Field
import org.jooq.Parameter
import org.jooq.impl.AbstractRoutine
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class PgSafeSnapshotBlockingPids : AbstractRoutine<Array<Int?>>("pg_safe_snapshot_blocking_pids", PgCatalog.PG_CATALOG, SQLDataType.INTEGER.getArrayDataType()) {
    companion object {

        /**
         * The parameter
         * <code>pg_catalog.pg_safe_snapshot_blocking_pids.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<Array<Int?>?> = Internal.createParameter("RETURN_VALUE", SQLDataType.INTEGER.getArrayDataType(), false, false)

        /**
         * The parameter
         * <code>pg_catalog.pg_safe_snapshot_blocking_pids._1</code>.
         */
        val _1: Parameter<Int?> = Internal.createParameter("_1", SQLDataType.INTEGER, false, true)
    }

    init {
        returnParameter = RETURN_VALUE
        addInParameter(_1)
    }

    /**
     * Set the <code>_1</code> parameter IN value to the routine
     */
    fun set__1(value: Int?): Unit = setValue(_1, value)

    /**
     * Set the <code>_1</code> parameter to the function to be used with a
     * {@link org.jooq.Select} statement
     */
    fun set__1(field: Field<Int?>): Unit {
        setField(_1, field)
    }
}
