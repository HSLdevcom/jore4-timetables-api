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
open class PgCopyLogicalReplicationSlot1 : AbstractRoutine<java.lang.Void>("pg_copy_logical_replication_slot", PgCatalog.PG_CATALOG) {
    companion object {

        /**
         * The parameter
         * <code>pg_catalog.pg_copy_logical_replication_slot.src_slot_name</code>.
         */
        val SRC_SLOT_NAME: Parameter<String?> = Internal.createParameter("src_slot_name", SQLDataType.VARCHAR, false, false)

        /**
         * The parameter
         * <code>pg_catalog.pg_copy_logical_replication_slot.dst_slot_name</code>.
         */
        val DST_SLOT_NAME: Parameter<String?> = Internal.createParameter("dst_slot_name", SQLDataType.VARCHAR, false, false)

        /**
         * The parameter
         * <code>pg_catalog.pg_copy_logical_replication_slot.temporary</code>.
         */
        val TEMPORARY: Parameter<Boolean?> = Internal.createParameter("temporary", SQLDataType.BOOLEAN, false, false)

        /**
         * The parameter
         * <code>pg_catalog.pg_copy_logical_replication_slot.plugin</code>.
         */
        val PLUGIN: Parameter<String?> = Internal.createParameter("plugin", SQLDataType.VARCHAR, false, false)

        /**
         * The parameter
         * <code>pg_catalog.pg_copy_logical_replication_slot.slot_name</code>.
         */
        val SLOT_NAME: Parameter<String?> = Internal.createParameter("slot_name", SQLDataType.VARCHAR, false, false)
        @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
        val LSN: Parameter<Any?> = Internal.createParameter("lsn", org.jooq.impl.DefaultDataType.getDefaultDataType("\"pg_catalog\".\"pg_lsn\""), false, false)
    }

    init {
        addInParameter(SRC_SLOT_NAME)
        addInParameter(DST_SLOT_NAME)
        addInParameter(TEMPORARY)
        addInParameter(PLUGIN)
        addOutParameter(SLOT_NAME)
        addOutParameter(LSN)
        setOverloaded(true)
    }

    /**
     * Set the <code>src_slot_name</code> parameter IN value to the routine
     */
    fun setSrcSlotName(value: String?): Unit = setValue(SRC_SLOT_NAME, value)

    /**
     * Set the <code>dst_slot_name</code> parameter IN value to the routine
     */
    fun setDstSlotName(value: String?): Unit = setValue(DST_SLOT_NAME, value)

    /**
     * Set the <code>temporary</code> parameter IN value to the routine
     */
    fun setTemporary(value: Boolean?): Unit = setValue(TEMPORARY, value)

    /**
     * Set the <code>plugin</code> parameter IN value to the routine
     */
    fun setPlugin(value: String?): Unit = setValue(PLUGIN, value)

    /**
     * Get the <code>slot_name</code> parameter OUT value from the routine
     */
    fun getSlotName(): String? = get(SLOT_NAME)
    @Deprecated(message = "Unknown data type. If this is a qualified, user-defined type, it may have been excluded from code generation. If this is a built-in type, you can define an explicit org.jooq.Binding to specify how this type should be handled. Deprecation can be turned off using <deprecationOnUnknownTypes/> in your code generator configuration.")
    fun getLsn(): Any? = get(LSN)
}
