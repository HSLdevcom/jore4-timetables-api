package fi.hsl.jore4.timetables.service

// import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.repository.VehicleScheduleFrameRepository
import fi.hsl.jore4.timetables.repository.VehicleServiceRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

private val LOGGER = KotlinLogging.logger {}

class TargetTimetableNotFoundException(message: String, val stagingVehicleScheduleFrameId: UUID) :
    RuntimeException(message)

class MultipleTargetTimetableFoundException(message: String, val stagingVehicleScheduleFrameId: UUID) :
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

    // This is currently not used from outside this service, only exposed for testing.
    @Transactional(noRollbackFor = [RuntimeException::class])
    fun combineSingleTimetable(stagingVehicleScheduleFrameId: UUID, targetPriority: TimetablesPriority): UUID {
        validateTargetPriority(targetPriority)

        LOGGER.info { "Combining timetables... $stagingVehicleScheduleFrameId to priority $targetPriority" }

        val stagingVehicleScheduleFrame = fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId)

        val targetVehicleScheduleFrames = vehicleScheduleFrameRepository
            .fetchTargetVehicleScheduleFrames(stagingVehicleScheduleFrame, targetPriority)
        LOGGER.info { "Found ${targetVehicleScheduleFrames.size} target vehicle schedule frames." }

        val targetVehicleScheduleFrame = targetVehicleScheduleFrames.firstOrNull()
            ?: throw TargetTimetableNotFoundException("Target timetable not found", stagingVehicleScheduleFrameId)
        if (targetVehicleScheduleFrames.size > 1) {
            // TODO: this probably needs better handling.
            throw MultipleTargetTimetableFoundException(
                "Multiple target timetables found.",
                stagingVehicleScheduleFrameId
            )
        }

        // TODO: ensure that there are no identical journeys in staging and target.
        // DB triggers do not ensure this.

        LOGGER.info("Moving staging vehicle services to target...")
        moveStagingVehicleServicesToTarget(
            stagingFrame = stagingVehicleScheduleFrame,
            targetFrame = targetVehicleScheduleFrame
        )

        LOGGER.info("Deleting the empty staging timetable...")
        deleteStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId)

        return targetVehicleScheduleFrame.vehicleScheduleFrameId!! // ID of an existing row, can never be null.
    }

    private fun fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId: UUID): VehicleScheduleFrame {
        val stagingFrame =
            vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(stagingVehicleScheduleFrameId)
        if (stagingFrame != null && stagingFrame.priority == TimetablesPriority.STAGING.value) {
            return stagingFrame
        }

        throw StagingVehicleScheduleFrameNotFoundException(
            "Staging vehicle schedule frame not found",
            stagingVehicleScheduleFrameId
        )
    }

    private fun moveStagingVehicleServicesToTarget(
        stagingFrame: VehicleScheduleFrame,
        targetFrame: VehicleScheduleFrame
    ): Int {
        return vehicleServiceRepository.updateServicesSetVehicleServiceFrameId(
            // // IDs of existing rows here, can never be null.
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
