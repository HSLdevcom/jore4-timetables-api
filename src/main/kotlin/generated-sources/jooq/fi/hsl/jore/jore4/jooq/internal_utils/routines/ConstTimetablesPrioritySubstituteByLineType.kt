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
open class ConstTimetablesPrioritySubstituteByLineType : AbstractRoutine<Int>("const_timetables_priority_substitute_by_line_type", InternalUtils.INTERNAL_UTILS, SQLDataType.INTEGER) {
    companion object {

        /**
         * The parameter
         * <code>internal_utils.const_timetables_priority_substitute_by_line_type.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<Int?> = Internal.createParameter("RETURN_VALUE", SQLDataType.INTEGER, false, false)
    }

    init {
        returnParameter = RETURN_VALUE
    }
}
