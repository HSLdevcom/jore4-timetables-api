package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.repository.VehicleScheduleFrameRepository
import fi.hsl.jore4.timetables.repository.VehicleServiceRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

private val LOGGER = KotlinLogging.logger {}

class TargetFrameNotFoundException(
    message: String,
    val stagingVehicleScheduleFrameId: UUID
) :
    RuntimeException(message)

class MultipleTargetFramesFoundException(
    message: String,
    val stagingVehicleScheduleFrameId: UUID,
    val targetVehicleScheduleFrameIds: List<UUID>
) :
    RuntimeException(message)

@Service
class CombineTimetablesService(
    private val vehicleScheduleFrameRepository: VehicleScheduleFrameRepository,
    private val vehicleServiceRepository: VehicleServiceRepository
) {
    @Transactional
    fun combineTimetables(
        stagingVehicleScheduleFrameIds: List<UUID>,
        targetPriority: TimetablesPriority
    ): List<UUID> = stagingVehicleScheduleFrameIds.map { stagingFrameId ->
        combineSingleTimetable(stagingFrameId, targetPriority)
    }

    private fun combineSingleTimetable(stagingVehicleScheduleFrameId: UUID, targetPriority: TimetablesPriority): UUID {
        validateTargetPriority(targetPriority)

        LOGGER.info { "Combining timetables... $stagingVehicleScheduleFrameId to priority $targetPriority" }

        val stagingVehicleScheduleFrame = fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId)

        val targetVehicleScheduleFrame = fetchTargetVehicleScheduleFrame(stagingVehicleScheduleFrame, targetPriority)

        LOGGER.info("Moving staging vehicle services to target...")
        moveStagingVehicleServicesToTarget(
            stagingFrame = stagingVehicleScheduleFrame,
            targetFrame = targetVehicleScheduleFrame
        )

        LOGGER.info("Deleting the empty staging frame...")
        deleteStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId)

        return targetVehicleScheduleFrame.vehicleScheduleFrameId!! // ID of an existing row, can never be null.
    }

    @Transactional(readOnly = true)
    fun fetchTargetVehicleScheduleFrame(
        stagingVehicleScheduleFrameId: UUID,
        targetPriority: TimetablesPriority
    ): VehicleScheduleFrame {
        val stagingVehicleScheduleFrame = fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId)
        return fetchTargetVehicleScheduleFrame(stagingVehicleScheduleFrame, targetPriority)
    }

    private fun fetchTargetVehicleScheduleFrame(
        stagingVehicleScheduleFrame: VehicleScheduleFrame,
        targetPriority: TimetablesPriority
    ): VehicleScheduleFrame {
        // ID of an existing row, can never be null.
        val stagingVehicleScheduleFrameId = stagingVehicleScheduleFrame.vehicleScheduleFrameId!!

        val targetVehicleScheduleFrames = vehicleScheduleFrameRepository
            .fetchTargetVehicleScheduleFrames(stagingVehicleScheduleFrame, targetPriority)
        LOGGER.info { "Found ${targetVehicleScheduleFrames.size} target vehicle schedule frames." }

        val targetVehicleScheduleFrame = targetVehicleScheduleFrames.firstOrNull()
            ?: throw TargetFrameNotFoundException("Target frame not found", stagingVehicleScheduleFrameId)
        if (targetVehicleScheduleFrames.size > 1) {
            val targetFrameIds = targetVehicleScheduleFrames
                .map { it.vehicleScheduleFrameId!! } // Primary key, can never be null.
            // Supporting this case would require splitting the frame somehow.
            throw MultipleTargetFramesFoundException(
                "Multiple target frames found.",
                stagingVehicleScheduleFrameId,
                targetFrameIds
            )
        }

        return targetVehicleScheduleFrame
    }

    private fun fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId: UUID): VehicleScheduleFrame {
        return vehicleScheduleFrameRepository
            .fetchOneByVehicleScheduleFrameId(stagingVehicleScheduleFrameId)
            ?.takeIf { it.priority == TimetablesPriority.STAGING.value }
            ?: throw StagingVehicleScheduleFrameNotFoundException(
                "Staging vehicle schedule frame not found",
                stagingVehicleScheduleFrameId
            )
    }

    private fun moveStagingVehicleServicesToTarget(
        stagingFrame: VehicleScheduleFrame,
        targetFrame: VehicleScheduleFrame
    ): Int {
        return vehicleServiceRepository.updateServicesSetVehicleServiceFrameId(
            // IDs of existing rows here, can never be null.
            currentFrameId = stagingFrame.vehicleScheduleFrameId!!,
            newFrameId = targetFrame.vehicleScheduleFrameId!!
        )
    }

    private fun deleteStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId: UUID) {
        return vehicleScheduleFrameRepository.deleteById(stagingVehicleScheduleFrameId)
    }

    companion object {
        private fun validateTargetPriority(targetPriority: TimetablesPriority) {
            if (targetPriority in listOf(TimetablesPriority.STAGING)) {
                throw InvalidTargetPriorityException("Can not set priority $targetPriority as target.", targetPriority)
            }
        }
    }
}
