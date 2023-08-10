/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.internal_utils.routines


import fi.hsl.jore.jore4.jooq.internal_utils.InternalUtils

import org.jooq.Parameter
import org.jooq.impl.AbstractRoutine
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class ConstDefaultTimezone : AbstractRoutine<String>("const_default_timezone", InternalUtils.INTERNAL_UTILS, SQLDataType.CLOB) {
    companion object {

        /**
         * The parameter
         * <code>internal_utils.const_default_timezone.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<String?> = Internal.createParameter("RETURN_VALUE", SQLDataType.CLOB, false, false)
    }

    init {
        returnParameter = RETURN_VALUE
    }
}
