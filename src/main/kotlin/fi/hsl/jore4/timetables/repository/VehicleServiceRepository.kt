package fi.hsl.jore4.timetables.repository

import fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService.Companion.VEHICLE_SERVICE_
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.daos.VehicleServiceDao
import org.jooq.DSLContext
import org.jooq.impl.DefaultConfiguration
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
class VehicleServiceRepository(private val dsl: DSLContext, config: DefaultConfiguration) : VehicleServiceDao(config) {
    @Transactional(noRollbackFor = [RuntimeException::class], propagation = Propagation.MANDATORY)
    fun updateServicesSetVehicleServiceFrameId(currentFrameId: UUID, newFrameId: UUID): Int {
        return dsl.update(VEHICLE_SERVICE_)
            .set(VEHICLE_SERVICE_.VEHICLE_SCHEDULE_FRAME_ID, newFrameId)
            .where(VEHICLE_SERVICE_.VEHICLE_SCHEDULE_FRAME_ID.eq(currentFrameId))
            .execute()
    }
}
