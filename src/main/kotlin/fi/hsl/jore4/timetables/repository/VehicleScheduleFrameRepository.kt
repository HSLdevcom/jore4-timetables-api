package fi.hsl.jore4.timetables.repository

import fi.hsl.jore.jore4.jooq.vehicle_schedule.routines.references.getOverlappingSchedules
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.GetOverlappingSchedules
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.daos.VehicleScheduleFrameDao
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.*
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame as VehicleScheduleFrameTable

@Repository
class VehicleScheduleFrameRepository(private val dsl: DSLContext, config: DefaultConfiguration) : VehicleScheduleFrameDao(config) {
    @Transactional
    fun fetchVehicleScheduleFramesToReplace(
        stagingVehicleScheduleFrameId: UUID,
        targetPriority: TimetablesPriority
    ): List<VehicleScheduleFrame> {
        val getOverlappingSchedules = GetOverlappingSchedules.GET_OVERLAPPING_SCHEDULES
        val overlappingSchedulesCTE = DSL.name("overlapping_schedules")

        val stagingVehicleScheduleFrameIdField = DSL.field(DSL.name("stagingVehicleScheduleFrameId"), UUID::class.java)
        val replacedVehicleScheduleFrameIdField =
            DSL.field(DSL.name("replacedVehicleScheduleFrameId"), UUID::class.java)

        val stagingFrame = VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME.`as`("staging")
        val replacedFrame = VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME.`as`("replaced")

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
            .join(VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME)
            .on(
                VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME
                    .VEHICLE_SCHEDULE_FRAME_ID
                    .eq(replacedVehicleScheduleFrameIdField)
            )
            .fetchInto(VehicleScheduleFrame::class.java)
    }

    @Transactional
    fun setFrameEndingTime(
        vehicleScheduleFrameId: UUID,
        newValidityEnd: LocalDate
    ): Int {
        return dsl.update(VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME)
            .set(
                VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME.VALIDITY_END,
                newValidityEnd
            )
            .where(
                VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(vehicleScheduleFrameId)
            )
            .execute()
    }

    @Transactional
    fun setFramePriority(
        vehicleScheduleFrameId: UUID,
        targetPriority: TimetablesPriority
    ): Int {
        return dsl.update(VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME)
            .set(
                VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME.PRIORITY,
                targetPriority.value
            )
            .where(
                VehicleScheduleFrameTable.VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(vehicleScheduleFrameId)
            )
            .execute()
    }
}
