package fi.hsl.jore4.timetables.repository

import fi.hsl.jore.jore4.jooq.vehicle_schedule.routines.references.getOverlappingSchedules
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.GetOverlappingSchedules
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame.Companion.VEHICLE_SCHEDULE_FRAME
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.daos.VehicleScheduleFrameDao
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import org.jooq.DSLContext
import org.jooq.Name
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class VehicleScheduleFrameRepository(
    private val dsl: DSLContext,
    config: DefaultConfiguration
) : VehicleScheduleFrameDao(config) {
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    fun fetchVehicleScheduleFramesToReplace(
        stagingVehicleScheduleFrameId: UUID,
        targetPriority: TimetablesPriority
    ): List<VehicleScheduleFrame> {
        val getOverlappingSchedules = GetOverlappingSchedules.GET_OVERLAPPING_SCHEDULES
        val overlappingSchedulesCTE = DSL.name("overlapping_schedules")

        val stagingFrameIdName: Name = DSL.name("stagingVehicleScheduleFrameId")
        val replacedFrameIdName: Name = DSL.name("replacedVehicleScheduleFrameId")
        val stagingVehicleScheduleFrameIdField = DSL.field(stagingFrameIdName, UUID::class.java)
        val replacedVehicleScheduleFrameIdField = DSL.field(replacedFrameIdName, UUID::class.java)

        val stagingFrame = VEHICLE_SCHEDULE_FRAME.`as`("staging")
        val replacedFrame = VEHICLE_SCHEDULE_FRAME.`as`("replaced")

        val framesReplacedByStagingQuery =
            dsl
                .with(
                    overlappingSchedulesCTE
                        .fields(
                            stagingFrameIdName,
                            replacedFrameIdName
                        ).`as`(
                            dsl
                                .select(
                                    getOverlappingSchedules.CURRENT_VEHICLE_SCHEDULE_FRAME_ID.`as`(
                                        stagingVehicleScheduleFrameIdField.name
                                    ),
                                    getOverlappingSchedules.OTHER_VEHICLE_SCHEDULE_FRAME_ID.`as`(
                                        replacedVehicleScheduleFrameIdField.name
                                    )
                                ).from(
                                    getOverlappingSchedules(
                                        arrayOf(stagingVehicleScheduleFrameId),
                                        arrayOf(),
                                        true
                                    )
                                )
                        )
                ).select(
                    stagingVehicleScheduleFrameIdField,
                    replacedVehicleScheduleFrameIdField
                ).from(replacedFrame)
                .join(overlappingSchedulesCTE)
                .on(replacedFrame.VEHICLE_SCHEDULE_FRAME_ID.eq(replacedVehicleScheduleFrameIdField))
                .join(stagingFrame)
                .on(stagingFrame.VEHICLE_SCHEDULE_FRAME_ID.eq(stagingVehicleScheduleFrameIdField))
                // The overlapping schedules query returns other frames as well, filter out unwanted ones.
                .where(stagingFrame.VEHICLE_SCHEDULE_FRAME_ID.eq(stagingVehicleScheduleFrameId))
                .and(replacedFrame.PRIORITY.eq(targetPriority.value))
                // Must be able to update the validity time of replaced frame,
                // so it ends before staging starts.
                // Thus, also needs to start before,
                // so a valid validity range can be set without touching start time.
                .and(replacedFrame.VALIDITY_START.lessThan(stagingFrame.VALIDITY_START))

        return dsl
            .select()
            // Returns a row for each day type id.
            // We are not interested in those here, just the replaced frame ids.
            .distinctOn(replacedVehicleScheduleFrameIdField)
            .from(framesReplacedByStagingQuery)
            .join(VEHICLE_SCHEDULE_FRAME)
            .on(VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID.eq(replacedVehicleScheduleFrameIdField))
            .fetchInto(VehicleScheduleFrame::class.java)
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    fun fetchTargetVehicleScheduleFrames(
        stagingVehicleScheduleFrame: VehicleScheduleFrame,
        targetPriority: TimetablesPriority
    ): List<VehicleScheduleFrame> {
        val stagingVehicleScheduleFrameId = stagingVehicleScheduleFrame.vehicleScheduleFrameId
        val getOverlappingSchedules = GetOverlappingSchedules.GET_OVERLAPPING_SCHEDULES
        val overlappingSchedulesCTE = DSL.name("overlapping_schedules")

        val stagingFrameIdName: Name = DSL.name("stagingVehicleScheduleFrameId")
        val targetFrameIdName: Name = DSL.name("targetVehicleScheduleFrameId")
        val stagingVehicleScheduleFrameIdField = DSL.field(stagingFrameIdName, UUID::class.java)
        val targetVehicleScheduleFrameIdField = DSL.field(targetFrameIdName, UUID::class.java)

        val stagingFrame = VEHICLE_SCHEDULE_FRAME.`as`("staging")
        val targetFrame = VEHICLE_SCHEDULE_FRAME.`as`("target")

        val framesOverlappedByStagingQuery =
            dsl
                .with(
                    overlappingSchedulesCTE
                        .fields(
                            stagingFrameIdName,
                            targetFrameIdName
                        ).`as`(
                            dsl
                                .select(
                                    getOverlappingSchedules.CURRENT_VEHICLE_SCHEDULE_FRAME_ID.`as`(
                                        stagingVehicleScheduleFrameIdField.name
                                    ),
                                    getOverlappingSchedules.OTHER_VEHICLE_SCHEDULE_FRAME_ID.`as`(
                                        targetVehicleScheduleFrameIdField.name
                                    )
                                ).from(
                                    getOverlappingSchedules(
                                        arrayOf(stagingVehicleScheduleFrameId),
                                        arrayOf(),
                                        true
                                    )
                                )
                        )
                ).select(
                    stagingVehicleScheduleFrameIdField,
                    targetVehicleScheduleFrameIdField
                ).from(targetFrame)
                .join(overlappingSchedulesCTE)
                .on(targetFrame.VEHICLE_SCHEDULE_FRAME_ID.eq(targetVehicleScheduleFrameIdField))
                .join(stagingFrame)
                .on(stagingFrame.VEHICLE_SCHEDULE_FRAME_ID.eq(stagingVehicleScheduleFrameIdField))
                // The overlapping schedules query returns other frames as well, filter out unwanted ones.
                .where(stagingFrame.VEHICLE_SCHEDULE_FRAME_ID.eq(stagingVehicleScheduleFrameId))
                .and(targetFrame.PRIORITY.eq(targetPriority.value))

        return dsl
            .select()
            // Returns a row for each day type id.
            // We are not interested in those here, just the overlapping frame ids.
            .distinctOn(targetVehicleScheduleFrameIdField)
            .from(framesOverlappedByStagingQuery)
            .join(VEHICLE_SCHEDULE_FRAME)
            .on(VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID.eq(targetVehicleScheduleFrameIdField))
            // The validity times must be exactly the same.
            .where(VEHICLE_SCHEDULE_FRAME.VALIDITY_START.eq(stagingVehicleScheduleFrame.validityStart))
            .and(VEHICLE_SCHEDULE_FRAME.VALIDITY_END.eq(stagingVehicleScheduleFrame.validityEnd))
            .fetchInto(VehicleScheduleFrame::class.java)
    }
}
