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
import org.jooq.types.YearToSecond


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class Max14 : AbstractRoutine<YearToSecond>("max", PgCatalog.PG_CATALOG, SQLDataType.INTERVAL) {
    companion object {

        /**
         * The parameter <code>pg_catalog.max.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<YearToSecond?> = Internal.createParameter("RETURN_VALUE", SQLDataType.INTERVAL, false, false)

        /**
         * The parameter <code>pg_catalog.max._1</code>.
         */
        val _1: Parameter<YearToSecond?> = Internal.createParameter("_1", SQLDataType.INTERVAL, false, true)
    }

    init {
        returnParameter = RETURN_VALUE
        addInParameter(_1)
        setOverloaded(true)
    }

    /**
     * Set the <code>_1</code> parameter IN value to the routine
     */
    fun set__1(value: YearToSecond?): Unit = setValue(_1, value)

    /**
     * Set the <code>_1</code> parameter to the function to be used with a
     * {@link org.jooq.Select} statement
     */
    fun set__1(field: Field<YearToSecond?>): Unit {
        setField(_1, field)
    }
}
