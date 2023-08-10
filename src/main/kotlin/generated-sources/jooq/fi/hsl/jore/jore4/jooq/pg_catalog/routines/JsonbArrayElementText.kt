/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.pg_catalog.routines


import fi.hsl.jore.jore4.jooq.pg_catalog.PgCatalog

import org.jooq.Field
import org.jooq.JSONB
import org.jooq.Parameter
import org.jooq.impl.AbstractRoutine
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class JsonbArrayElementText : AbstractRoutine<String>("jsonb_array_element_text", PgCatalog.PG_CATALOG, SQLDataType.CLOB) {
    companion object {

        /**
         * The parameter
         * <code>pg_catalog.jsonb_array_element_text.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<String?> = Internal.createParameter("RETURN_VALUE", SQLDataType.CLOB, false, false)

        /**
         * The parameter
         * <code>pg_catalog.jsonb_array_element_text.from_json</code>.
         */
        val FROM_JSON: Parameter<JSONB?> = Internal.createParameter("from_json", SQLDataType.JSONB, false, false)

        /**
         * The parameter
         * <code>pg_catalog.jsonb_array_element_text.element_index</code>.
         */
        val ELEMENT_INDEX: Parameter<Int?> = Internal.createParameter("element_index", SQLDataType.INTEGER, false, false)
    }

    init {
        returnParameter = RETURN_VALUE
        addInParameter(FROM_JSON)
        addInParameter(ELEMENT_INDEX)
    }

    /**
     * Set the <code>from_json</code> parameter IN value to the routine
     */
    fun setFromJson(value: JSONB?): Unit = setValue(FROM_JSON, value)

    /**
     * Set the <code>from_json</code> parameter to the function to be used with
     * a {@link org.jooq.Select} statement
     */
    fun setFromJson(field: Field<JSONB?>): Unit {
        setField(FROM_JSON, field)
    }

    /**
     * Set the <code>element_index</code> parameter IN value to the routine
     */
    fun setElementIndex(value: Int?): Unit = setValue(ELEMENT_INDEX, value)

    /**
     * Set the <code>element_index</code> parameter to the function to be used
     * with a {@link org.jooq.Select} statement
     */
    fun setElementIndex(field: Field<Int?>): Unit {
        setField(ELEMENT_INDEX, field)
    }
}
