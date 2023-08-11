/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.internal_utils.routines


import fi.hsl.jore.jore4.jooq.internal_utils.InternalUtils

import org.jooq.Field
import org.jooq.Parameter
import org.jooq.Record
import org.jooq.impl.AbstractRoutine
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.types.YearToSecond


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class VehicleJourneyStartTimeInterval : AbstractRoutine<YearToSecond>("vehicle_journey_start_time_interval", InternalUtils.INTERNAL_UTILS, SQLDataType.INTERVAL) {
    companion object {

        /**
         * The parameter
         * <code>internal_utils.vehicle_journey_start_time_interval.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<YearToSecond?> = Internal.createParameter("RETURN_VALUE", SQLDataType.INTERVAL, false, false)

        /**
         * The parameter
         * <code>internal_utils.vehicle_journey_start_time_interval.vj</code>.
         */
        val VJ: Parameter<Record?> = Internal.createParameter("vj", fi.hsl.jore.jore4.jooq.vehicle_journey.tables.VehicleJourney.VEHICLE_JOURNEY_.getDataType(), false, false)
    }

    init {
        returnParameter = RETURN_VALUE
        addInParameter(VJ)
    }

    /**
     * Set the <code>vj</code> parameter IN value to the routine
     */
    fun setVj(value: Record?): Unit = setValue(VJ, value)

    /**
     * Set the <code>vj</code> parameter to the function to be used with a
     * {@link org.jooq.Select} statement
     */
    fun setVj(field: Field<Record?>): Unit {
        setField(VJ, field)
    }
}
