/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.internal_service_calendar.routines


import fi.hsl.jore.jore4.jooq.internal_service_calendar.InternalServiceCalendar

import java.time.LocalDate
import java.util.UUID

import org.jooq.Field
import org.jooq.Parameter
import org.jooq.impl.AbstractRoutine
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class GetSubstituteOperatingPeriodEndDate : AbstractRoutine<LocalDate>("get_substitute_operating_period_end_date", InternalServiceCalendar.INTERNAL_SERVICE_CALENDAR, SQLDataType.LOCALDATE) {
    companion object {

        /**
         * The parameter
         * <code>internal_service_calendar.get_substitute_operating_period_end_date.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<LocalDate?> = Internal.createParameter("RETURN_VALUE", SQLDataType.LOCALDATE, false, false)

        /**
         * The parameter
         * <code>internal_service_calendar.get_substitute_operating_period_end_date.substitute_operating_period_uuid</code>.
         */
        val SUBSTITUTE_OPERATING_PERIOD_UUID: Parameter<UUID?> = Internal.createParameter("substitute_operating_period_uuid", SQLDataType.UUID, false, false)
    }

    init {
        returnParameter = RETURN_VALUE
        addInParameter(SUBSTITUTE_OPERATING_PERIOD_UUID)
    }

    /**
     * Set the <code>substitute_operating_period_uuid</code> parameter IN value
     * to the routine
     */
    fun setSubstituteOperatingPeriodUuid(value: UUID?): Unit = setValue(SUBSTITUTE_OPERATING_PERIOD_UUID, value)

    /**
     * Set the <code>substitute_operating_period_uuid</code> parameter to the
     * function to be used with a {@link org.jooq.Select} statement
     */
    fun setSubstituteOperatingPeriodUuid(field: Field<UUID?>): Unit {
        setField(SUBSTITUTE_OPERATING_PERIOD_UUID, field)
    }
}
