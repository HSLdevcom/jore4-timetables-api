/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.passing_times.keys


import fi.hsl.jore.jore4.jooq.passing_times.tables.TimetabledPassingTime
import fi.hsl.jore.jore4.jooq.passing_times.tables.records.TimetabledPassingTimeRecord
import fi.hsl.jore.jore4.jooq.service_pattern.keys.SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_PKEY
import fi.hsl.jore.jore4.jooq.service_pattern.tables.ScheduledStopPointInJourneyPatternRef
import fi.hsl.jore.jore4.jooq.service_pattern.tables.records.ScheduledStopPointInJourneyPatternRefRecord
import fi.hsl.jore.jore4.jooq.vehicle_journey.keys.VEHICLE_JOURNEY_PKEY
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.VehicleJourney
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.records.VehicleJourneyRecord

import org.jooq.ForeignKey
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val TIMETABLED_PASSING_TIME_PKEY: UniqueKey<TimetabledPassingTimeRecord> = Internal.createUniqueKey(TimetabledPassingTime.TIMETABLED_PASSING_TIME, DSL.name("timetabled_passing_time_pkey"), arrayOf(TimetabledPassingTime.TIMETABLED_PASSING_TIME.TIMETABLED_PASSING_TIME_ID), true)

// -------------------------------------------------------------------------
// FOREIGN KEY definitions
// -------------------------------------------------------------------------

val TIMETABLED_PASSING_TIME__TIMETABLED_PASSING_TIME_SCHEDULED_STOP_POINT_IN_JOURNEY_PA_FKEY: ForeignKey<TimetabledPassingTimeRecord, ScheduledStopPointInJourneyPatternRefRecord> = Internal.createForeignKey(TimetabledPassingTime.TIMETABLED_PASSING_TIME, DSL.name("timetabled_passing_time_scheduled_stop_point_in_journey_pa_fkey"), arrayOf(TimetabledPassingTime.TIMETABLED_PASSING_TIME.SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_ID), SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_PKEY, arrayOf(ScheduledStopPointInJourneyPatternRef.SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF.SCHEDULED_STOP_POINT_IN_JOURNEY_PATTERN_REF_ID), true)
val TIMETABLED_PASSING_TIME__TIMETABLED_PASSING_TIME_VEHICLE_JOURNEY_ID_FKEY: ForeignKey<TimetabledPassingTimeRecord, VehicleJourneyRecord> = Internal.createForeignKey(TimetabledPassingTime.TIMETABLED_PASSING_TIME, DSL.name("timetabled_passing_time_vehicle_journey_id_fkey"), arrayOf(TimetabledPassingTime.TIMETABLED_PASSING_TIME.VEHICLE_JOURNEY_ID), VEHICLE_JOURNEY_PKEY, arrayOf(VehicleJourney.VEHICLE_JOURNEY_.VEHICLE_JOURNEY_ID), true)
