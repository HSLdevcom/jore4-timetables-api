package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame
import mu.KotlinLogging
import org.jooq.DSLContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val LOGGER = KotlinLogging.logger {}

@Service
class ScheduleFrameService(
    private val dsl: DSLContext
) {

    @Transactional(readOnly = true)
    fun getServiceFrames(): String {
        LOGGER.trace("GetServiceFrames request")
        return dsl.selectFrom(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .fetch()
            .map { it.toString() }
            .joinToString(",")
    }
}
