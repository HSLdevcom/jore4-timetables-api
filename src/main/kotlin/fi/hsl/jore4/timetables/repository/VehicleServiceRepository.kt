package fi.hsl.jore4.timetables.repository

import fi.hsl.jore.jore4.jooq.vehicle_service.tables.daos.VehicleServiceDao
import org.jooq.DSLContext
import org.jooq.impl.DefaultConfiguration
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import fi.hsl.jore.jore4.jooq.vehicle_service.tables.VehicleService as VehicleServiceTable

@Repository
class VehicleServiceRepository(private val dsl: DSLContext, config: DefaultConfiguration) : VehicleServiceDao(config) {
    @Transactional(noRollbackFor = [RuntimeException::class])
    fun updateServicesSetVehicleServiceFrameId(currentFrameId: UUID, newFrameId: UUID): Int {
        return dsl.update(VehicleServiceTable.VEHICLE_SERVICE_)
            .set(
                VehicleServiceTable.VEHICLE_SERVICE_.VEHICLE_SCHEDULE_FRAME_ID,
                newFrameId
            )
            .where(
                VehicleServiceTable.VEHICLE_SERVICE_.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(currentFrameId)
            )
            .execute()
    }
}
