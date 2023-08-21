package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.routines.references.getOverlappingSchedules
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.GetOverlappingSchedules
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.records.VehicleScheduleFrameRecord
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import mu.KotlinLogging
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

private val LOGGER = KotlinLogging.logger {}

val VehicleScheduleFrameRecord = VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.recordType

data class FramesReplacedByStaging(
    val stagingVehicleScheduleFrameId: UUID,
    val replacedVehicleScheduleFrameId: UUID
)

@Service
class ReplaceTimetablesService(private val dsl: DSLContext) {
    @Transactional
    fun replaceTimetables(stagingVehicleScheduleFrameIds: List<UUID>, targetPriority: TimetablesPriority): List<UUID> =
        stagingVehicleScheduleFrameIds.map { stagingFrameId ->
            processSingleStagingFrameReplacements(stagingFrameId, targetPriority)
        }.flatten()

    @Transactional
    fun processSingleStagingFrameReplacements(
        stagingVehicleScheduleFrameId: UUID,
        targetPriority: TimetablesPriority
    ): List<UUID> {
        validateTargetPriority(targetPriority)

        LOGGER.info { "Replacing timetables... $stagingVehicleScheduleFrameId to priority $targetPriority" }

        val stagingFrame = fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId)

        val replacements = fetchVehicleScheduleFramesToReplace(stagingVehicleScheduleFrameId, targetPriority)
        LOGGER.info { "Found ${replacements.size} vehicle schedule frames to be replaced." }

        for (replacedFrame in replacements) {
            LOGGER.info { "Expiring vehicle schedule frame ${replacedFrame.vehicleScheduleFrameId} ..." }
            expireReplacedFrameBeforeStaging(
                replacedFrame = replacedFrame,
                stagingFrame = stagingFrame
            )
        }

        LOGGER.info("Promoting staging vehicle schedule frame to target priority...")
        promoteStagingFrameToPriority(stagingFrame, targetPriority)

        return replacements.map { it.vehicleScheduleFrameId!! }
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
            .fetchOneInto(VehicleScheduleFrameRecord)
            ?: throw IllegalStateException("Staging vehicle schedule frame not found")
    }

    fun fetchVehicleScheduleFramesToReplace(
        stagingVehicleScheduleFrameId: UUID,
        targetPriority: TimetablesPriority
    ): List<VehicleScheduleFrameRecord> {
        val getOverlappingSchedules = GetOverlappingSchedules.GET_OVERLAPPING_SCHEDULES
        val overlappingSchedulesCTE = DSL.name("overlapping_schedules")

        val stagingVehicleScheduleFrameIdField = DSL.field(DSL.name("stagingVehicleScheduleFrameId"), UUID::class.java)
        val replacedVehicleScheduleFrameIdField =
            DSL.field(DSL.name("replacedVehicleScheduleFrameId"), UUID::class.java)

        val stagingFrame = VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.`as`("staging")
        val replacedFrame = VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.`as`("replaced")

        val framesReplacedByStagingQuery = dsl
            .with(
                overlappingSchedulesCTE
                    .fields(
                        DSL.name("stagingVehicleScheduleFrameId"),
                        DSL.name("replacedVehicleScheduleFrameId")
                    )
                    .`as`(
                        dsl
                            .select(
                                getOverlappingSchedules.CURRENT_VEHICLE_SCHEDULE_FRAME_ID.`as`(
                                    stagingVehicleScheduleFrameIdField.name
                                ),
                                getOverlappingSchedules.OTHER_VEHICLE_SCHEDULE_FRAME_ID.`as`(
                                    replacedVehicleScheduleFrameIdField.name
                                )
                            )
                            .from(
                                getOverlappingSchedules(
                                    arrayOf(stagingVehicleScheduleFrameId),
                                    arrayOf<UUID?>(),
                                    true
                                )
                            )
                    )
            )
            .select(
                stagingVehicleScheduleFrameIdField,
                replacedVehicleScheduleFrameIdField
            )
            .from(replacedFrame)
            .join(overlappingSchedulesCTE)
            .on(
                replacedFrame
                    .VEHICLE_SCHEDULE_FRAME_ID
                    .eq(replacedVehicleScheduleFrameIdField)
            )
            .join(stagingFrame)
            .on(
                stagingFrame
                    .VEHICLE_SCHEDULE_FRAME_ID
                    .eq(stagingVehicleScheduleFrameId)
            )
            .where(
                replacedFrame.PRIORITY
                    .eq(targetPriority.value)
            )
            // Must be able to update the validity time of replaced frame,
            // so it ends before staging starts.
            // Thus, also needs to start before,
            // so a valid validity range can be set without touching start time.
            .and(
                replacedFrame.VALIDITY_START
                    .lessThan(stagingFrame.VALIDITY_START)
            )

        return dsl
            .select()
            // Returns a row for each day type id.
            // We are not interested in those here, just the replaced frame ids.
            .distinctOn(replacedVehicleScheduleFrameIdField)
            .from(
                framesReplacedByStagingQuery
            )
            .join(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .on(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME
                    .VEHICLE_SCHEDULE_FRAME_ID
                    .eq(replacedVehicleScheduleFrameIdField)
            )
            .fetchInto(VehicleScheduleFrameRecord)
    }

    private fun expireReplacedFrameBeforeStaging(
        replacedFrame: VehicleScheduleFrameRecord,
        stagingFrame: VehicleScheduleFrameRecord
    ): Int {
        val newEnd = stagingFrame.validityStart!!.minusDays(1)

        return dsl.update(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .set(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VALIDITY_END,
                newEnd
            )
            .where(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(replacedFrame.vehicleScheduleFrameId)
            )
            .execute()
    }

    private fun promoteStagingFrameToPriority(
        stagingFrame: VehicleScheduleFrameRecord,
        targetPriority: TimetablesPriority
    ): Int {
        return dsl.update(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .set(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.PRIORITY,
                targetPriority.value
            )
            .where(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(stagingFrame.vehicleScheduleFrameId)
            )
            .execute()
    }
}
