package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.routines.references.getOverlappingSchedules
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.GetOverlappingSchedules
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.records.VehicleScheduleFrameRecord
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import mu.KotlinLogging
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

private val LOGGER = KotlinLogging.logger {}

@Service
class CombineTimetablesService(private val dsl: DSLContext) {
    @Transactional(readOnly = false)
    fun combineTimetables(
        stagingVehicleScheduleFrameIds: List<UUID>,
        targetPriority: TimetablesPriority
    ): List<UUID> {
        val results = stagingVehicleScheduleFrameIds.map { stagingFrameId ->
            combineSingleTimetable(stagingFrameId, targetPriority)
        }
        return results
    }

    @Transactional(readOnly = false)
    fun combineSingleTimetable(stagingVehicleScheduleFrameId: UUID, targetPriority: TimetablesPriority): UUID {
        validateTargetPriority(targetPriority)

        LOGGER.info("Combining timetables... $stagingVehicleScheduleFrameId to priority $targetPriority")

        val stagingVehicleScheduleFrame = fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId)

        val targetVehicleScheduleFrames = fetchTargetVehicleScheduleFrames(stagingVehicleScheduleFrame, targetPriority)
        LOGGER.info("Found ${targetVehicleScheduleFrames.size} target vehicle schedule frames.")
        val targetVehicleScheduleFrame =
            targetVehicleScheduleFrames.firstOrNull() ?: throw IllegalStateException("Target timetable not found");
        if (targetVehicleScheduleFrames.size > 1) {
            // TODO: this probably needs better handling.
            throw Error("Multiple target timetables found.")
        }

        // TODO: ensure that there are no identical journeys in staging and target.
        // DB triggers do not ensure this.

        LOGGER.info("Moving staging timetables to target...")
        moveStagingTimetablesToTarget(
            stagingVehicleScheduleFrameId,
            targetVehicleScheduleFrame.vehicleScheduleFrameId!!
        );

        LOGGER.info("Deleting the empty staging timetable...")
        deleteStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId)

        return targetVehicleScheduleFrame.vehicleScheduleFrameId!!
    }

    private fun validateTargetPriority(targetPriority: TimetablesPriority) {
        if (targetPriority in listOf(TimetablesPriority.STAGING)) {
            throw IllegalArgumentException("Can not set priority $targetPriority as target.")
        }
    }

    private fun fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId: UUID): VehicleScheduleFrameRecord {
        return dsl.selectFrom(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .where(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(stagingVehicleScheduleFrameId)
            )
            .and(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.PRIORITY
                    .eq(TimetablesPriority.STAGING.value)
            )
            .fetchOneInto(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.recordType)
            ?: throw IllegalStateException("Staging vehicle schedule frame not found")
    }

    private fun fetchTargetVehicleScheduleFrames(
        stagingVehicleScheduleFrame: VehicleScheduleFrameRecord,
        targetPriority: TimetablesPriority
    ): MutableList<VehicleScheduleFrameRecord> {
        val getOverlappingSchedules = GetOverlappingSchedules.GET_OVERLAPPING_SCHEDULES
        val overlappingSchedulesCTE = DSL.name("overlapping_schedules")

        val stagingVehicleScheduleFrameIdField = DSL.field(DSL.name("stagingVehicleScheduleFrameId"), UUID::class.java)
        val targetVehicleScheduleFrameIdField = DSL.field(DSL.name("targetVehicleScheduleFrameId"), UUID::class.java)

        val targetFramesForCombineQuery = dsl
            .with(
                overlappingSchedulesCTE
                    .fields(
                        DSL.name("stagingVehicleScheduleFrameId"),
                        DSL.name("targetVehicleScheduleFrameId")
                    )
                    .`as`(
                        dsl
                            .select(
                                getOverlappingSchedules.CURRENT_VEHICLE_SCHEDULE_FRAME_ID.`as`(
                                    stagingVehicleScheduleFrameIdField.name
                                ),
                                getOverlappingSchedules.OTHER_VEHICLE_SCHEDULE_FRAME_ID.`as`(
                                    targetVehicleScheduleFrameIdField.name
                                )
                            )
                            .from(
                                getOverlappingSchedules(
                                    arrayOf(stagingVehicleScheduleFrame.vehicleScheduleFrameId),
                                    arrayOf<UUID?>(),
                                    true
                                )
                            )
                    )
            )
            .select(
                stagingVehicleScheduleFrameIdField,
                targetVehicleScheduleFrameIdField
            )
            .from(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .join(overlappingSchedulesCTE)
            .on(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(targetVehicleScheduleFrameIdField)
            )
            .where(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.PRIORITY
                    .eq(targetPriority.value)
            )
            .and(
                stagingVehicleScheduleFrameIdField
                    .eq(stagingVehicleScheduleFrame.vehicleScheduleFrameId)
            )

        return dsl.selectFrom(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .where(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VALIDITY_START
                    .eq(stagingVehicleScheduleFrame.validityStart)
            )
            .and(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VALIDITY_END
                    .eq(stagingVehicleScheduleFrame.validityEnd)
            )
            .and(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.PRIORITY
                    .eq(targetPriority.value)
            )
            .andExists(
                dsl.selectOne().from(
                    targetFramesForCombineQuery
                )
            )
            .fetchInto(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.recordType)
    }

    private fun moveStagingTimetablesToTarget(
        stagingVehicleScheduleFrameId: UUID,
        targetVehicleScheduleFrameId: UUID
    ): Int {
        return dsl.update(VehicleService.VEHICLE_SERVICE_)
            .set(
                VehicleService.VEHICLE_SERVICE_.VEHICLE_SCHEDULE_FRAME_ID,
                targetVehicleScheduleFrameId
            )
            .where(
                VehicleService.VEHICLE_SERVICE_.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(stagingVehicleScheduleFrameId)
            )
            .execute()
    }

    private fun deleteStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId: UUID): Int {
        return dsl.deleteFrom(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .where(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(stagingVehicleScheduleFrameId)
            )
            .execute()
    }
}
