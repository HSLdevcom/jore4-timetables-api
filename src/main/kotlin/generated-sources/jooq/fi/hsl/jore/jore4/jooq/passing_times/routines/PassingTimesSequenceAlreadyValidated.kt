/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.passing_times.routines


import fi.hsl.jore.jore4.jooq.passing_times.PassingTimes

import org.jooq.Parameter
import org.jooq.impl.AbstractRoutine
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class PassingTimesSequenceAlreadyValidated : AbstractRoutine<Boolean>("passing_times_sequence_already_validated", PassingTimes.PASSING_TIMES, SQLDataType.BOOLEAN) {
    companion object {

        /**
         * The parameter
         * <code>passing_times.passing_times_sequence_already_validated.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<Boolean?> = Internal.createParameter("RETURN_VALUE", SQLDataType.BOOLEAN, false, false)
    }

    init {
        returnParameter = RETURN_VALUE
    }
}
