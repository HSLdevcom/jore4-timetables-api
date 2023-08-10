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
open class PgReplicationSlotAdvance : AbstractRoutine<java.lang.Void>("pg_replication_slot_advance", PgCatalog.PG_CATALOG) {
    companion object {

        /**
         * The parameter
         * <code>pg_catalog.pg_replication_slot_advance.slot_name</code>.
         */
        val SLOT_NAME1: Parameter<String?> = Internal.createParameter("slot_name", SQLDataType.VARCHAR, false, false)
        @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
        val UPTO_LSN: Parameter<Any?> = Internal.createParameter("upto_lsn", org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"pg_lsn\""), false, false)

        /**
         * The parameter
         * <code>pg_catalog.pg_replication_slot_advance.slot_name</code>.
         */
        val SLOT_NAME3: Parameter<String?> = Internal.createParameter("slot_name", SQLDataType.VARCHAR, false, false)
        @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
        val END_LSN: Parameter<Any?> = Internal.createParameter("end_lsn", org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"pg_lsn\""), false, false)
    }

    init {
        addInOutParameter(SLOT_NAME1)
        addInParameter(UPTO_LSN)
        addInOutParameter(SLOT_NAME3)
        addOutParameter(END_LSN)
    }

    /**
     * Set the <code>slot_name</code> parameter IN value to the routine
     */
    fun setSlotName1(value: String?): Unit = setValue(SLOT_NAME1, value)

    /**
     * Set the <code>upto_lsn</code> parameter IN value to the routine
     */
    fun setUptoLsn(value: Any?): Unit = setValue(UPTO_LSN, value)

    /**
     * Get the <code>slot_name</code> parameter OUT value from the routine
     */
    fun getSlotName1(): String? = get(SLOT_NAME1)

    /**
     * Get the <code>slot_name</code> parameter OUT value from the routine
     */
    fun getSlotName3(): String? = get(SLOT_NAME3)
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    fun getEndLsn(): Any? = get(END_LSN)
}
