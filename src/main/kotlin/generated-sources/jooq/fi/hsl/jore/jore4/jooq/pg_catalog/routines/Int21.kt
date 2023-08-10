/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.pg_catalog.routines


import fi.hsl.jore.jore4.jooq.pg_catalog.PgCatalog

import java.math.BigDecimal

import org.jooq.Field
import org.jooq.Parameter
import org.jooq.impl.AbstractRoutine
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Int21 : AbstractRoutine<Short>("int2", PgCatalog.PG_CATALOG, SQLDataType.SMALLINT) {
    companion object {

        /**
         * The parameter <code>pg_catalog.int2.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<Short?> = Internal.createParameter("RETURN_VALUE", SQLDataType.SMALLINT, false, false)

        /**
         * The parameter <code>pg_catalog.int2._1</code>.
         */
        val _1: Parameter<BigDecimal?> = Internal.createParameter("_1", SQLDataType.NUMERIC, false, true)
    }

    init {
        returnParameter = RETURN_VALUE
        addInParameter(_1)
        setOverloaded(true)
    }

    /**
     * Set the <code>_1</code> parameter IN value to the routine
     */
    fun set__1(value: BigDecimal?): Unit = setValue(_1, value)

    /**
     * Set the <code>_1</code> parameter to the function to be used with a
     * {@link org.jooq.Select} statement
     */
    fun set__1(field: Field<BigDecimal?>): Unit {
        setField(_1, field)
    }
}
