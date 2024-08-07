/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_journey.routines


import fi.hsl.jore.jore4.jooq.vehicle_journey.VehicleJourney
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.records.VehicleJourneyRecord

import org.jooq.Field
import org.jooq.Parameter
import org.jooq.impl.AbstractRoutine
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class VehicleJourneyEndTime : AbstractRoutine<String>("vehicle_journey_end_time", VehicleJourney.VEHICLE_JOURNEY, SQLDataType.CLOB) {
    companion object {

        /**
         * The parameter
         * <code>vehicle_journey.vehicle_journey_end_time.RETURN_VALUE</code>.
         */
        val RETURN_VALUE: Parameter<String?> = Internal.createParameter("RETURN_VALUE", SQLDataType.CLOB, false, false)

        /**
         * The parameter
         * <code>vehicle_journey.vehicle_journey_end_time.vj</code>.
         */
        val VJ: Parameter<VehicleJourneyRecord?> = Internal.createParameter("vj", fi.hsl.jore.jore4.jooq.vehicle_journey.tables.VehicleJourney.VEHICLE_JOURNEY_.dataType, false, false)
    }

    init {
        returnParameter = VehicleJourneyEndTime.RETURN_VALUE
        addInParameter(VehicleJourneyEndTime.VJ)
    }

    /**
     * Set the <code>vj</code> parameter IN value to the routine
     */
    fun setVj(value: VehicleJourneyRecord?): Unit = setValue(VehicleJourneyEndTime.VJ, value)

    /**
     * Set the <code>vj</code> parameter to the function to be used with a
     * {@link org.jooq.Select} statement
     */
    fun setVj(field: Field<VehicleJourneyRecord?>): Unit {
        setField(VehicleJourneyEndTime.VJ, field)
    }
}
