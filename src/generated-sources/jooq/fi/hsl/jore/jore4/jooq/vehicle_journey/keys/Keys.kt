/*
 * This file is generated by jOOQ.
 */
package fi.hsl.jore.jore4.jooq.vehicle_journey.keys


import fi.hsl.jore.jore4.jooq.journey_pattern.keys.JOURNEY_PATTERN_REF_PKEY
import fi.hsl.jore.jore4.jooq.journey_pattern.tables.JourneyPatternRef
import fi.hsl.jore.jore4.jooq.journey_pattern.tables.records.JourneyPatternRefRecord
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.JourneyType
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.VehicleJourney
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.records.JourneyTypeRecord
import fi.hsl.jore.jore4.jooq.vehicle_journey.tables.records.VehicleJourneyRecord
import fi.hsl.jore.jore4.jooq.vehicle_service.keys.BLOCK_PKEY
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.Block
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.records.BlockRecord

import org.jooq.ForeignKey
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val JOURNEY_TYPE_PKEY: UniqueKey<JourneyTypeRecord> = Internal.createUniqueKey(JourneyType.JOURNEY_TYPE, DSL.name("journey_type_pkey"), arrayOf(JourneyType.JOURNEY_TYPE.TYPE), true)
val VEHICLE_JOURNEY_PKEY: UniqueKey<VehicleJourneyRecord> = Internal.createUniqueKey(VehicleJourney.VEHICLE_JOURNEY_, DSL.name("vehicle_journey_pkey"), arrayOf(VehicleJourney.VEHICLE_JOURNEY_.VEHICLE_JOURNEY_ID), true)

// -------------------------------------------------------------------------
// FOREIGN KEY definitions
// -------------------------------------------------------------------------

val VEHICLE_JOURNEY__VEHICLE_JOURNEY_BLOCK_ID_FKEY: ForeignKey<VehicleJourneyRecord, BlockRecord> = Internal.createForeignKey(VehicleJourney.VEHICLE_JOURNEY_, DSL.name("vehicle_journey_block_id_fkey"), arrayOf(VehicleJourney.VEHICLE_JOURNEY_.BLOCK_ID), BLOCK_PKEY, arrayOf(Block.BLOCK.BLOCK_ID), true)
val VEHICLE_JOURNEY__VEHICLE_JOURNEY_JOURNEY_PATTERN_REF_ID_FKEY: ForeignKey<VehicleJourneyRecord, JourneyPatternRefRecord> = Internal.createForeignKey(VehicleJourney.VEHICLE_JOURNEY_, DSL.name("vehicle_journey_journey_pattern_ref_id_fkey"), arrayOf(VehicleJourney.VEHICLE_JOURNEY_.JOURNEY_PATTERN_REF_ID), JOURNEY_PATTERN_REF_PKEY, arrayOf(JourneyPatternRef.JOURNEY_PATTERN_REF.JOURNEY_PATTERN_REF_ID), true)
val VEHICLE_JOURNEY__VEHICLE_JOURNEY_JOURNEY_TYPE_FKEY: ForeignKey<VehicleJourneyRecord, JourneyTypeRecord> = Internal.createForeignKey(VehicleJourney.VEHICLE_JOURNEY_, DSL.name("vehicle_journey_journey_type_fkey"), arrayOf(VehicleJourney.VEHICLE_JOURNEY_.JOURNEY_TYPE), fi.hsl.jore.jore4.jooq.vehicle_journey.keys.JOURNEY_TYPE_PKEY, arrayOf(JourneyType.JOURNEY_TYPE.TYPE), true)
