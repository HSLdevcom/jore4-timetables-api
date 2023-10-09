package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.repository.VehicleScheduleFrameRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

private val LOGGER = KotlinLogging.logger {}

class InvalidTargetPriorityException(message: String, val targetPriority: TimetablesPriority) : RuntimeException(message)

class StagingVehicleScheduleFrameNotFoundException(message: String, val stagingVehicleScheduleFrameId: UUID) : RuntimeException(message)

class TargetPriorityParsingException(message: String, val targetPriority: Int) : RuntimeException(message)

@Service
class ReplaceTimetablesService(
    private val vehicleScheduleFrameRepository: VehicleScheduleFrameRepository
) {
    @Transactional
    fun replaceTimetables(stagingVehicleScheduleFrameIds: List<UUID>, targetPriority: TimetablesPriority): List<UUID> =
        stagingVehicleScheduleFrameIds.map {
            processSingleStagingFrameReplacements(it, targetPriority)
        }.flatten()

    private fun processSingleStagingFrameReplacements(
        stagingVehicleScheduleFrameId: UUID,
        targetPriority: TimetablesPriority
    ): List<UUID> {
        validateTargetPriority(targetPriority)

        LOGGER.info { "Replacing timetables... $stagingVehicleScheduleFrameId to priority $targetPriority" }

        val stagingFrame = fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId)
        LOGGER.debug { "Fetched staging frame: $stagingFrame" }

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

        return replacements.map {
            it.vehicleScheduleFrameId!! // ID of an existing row, can never be null.
        }
    }

    @Transactional(readOnly = true)
    fun fetchVehicleScheduleFramesToReplace(
        stagingVehicleScheduleFrameId: UUID,
        targetPriority: TimetablesPriority
    ): List<VehicleScheduleFrame> {
        return vehicleScheduleFrameRepository.fetchVehicleScheduleFramesToReplace(
            stagingVehicleScheduleFrameId,
            targetPriority
        )
    }

    private fun fetchStagingVehicleScheduleFrame(stagingVehicleScheduleFrameId: UUID): VehicleScheduleFrame {
        val stagingFrame = vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(stagingVehicleScheduleFrameId)
        if (stagingFrame != null && stagingFrame.priority == TimetablesPriority.STAGING.value) {
            return stagingFrame
        }

        throw StagingVehicleScheduleFrameNotFoundException("Staging vehicle schedule frame not found", stagingVehicleScheduleFrameId)
    }

    private fun expireReplacedFrameBeforeStaging(
        replacedFrame: VehicleScheduleFrame,
        stagingFrame: VehicleScheduleFrame
    ) {
        val newEnd = stagingFrame.validityStart.minusDays(1)
        replacedFrame.validityEnd = newEnd
        vehicleScheduleFrameRepository.update(replacedFrame)
    }

    private fun promoteStagingFrameToPriority(
        stagingFrame: VehicleScheduleFrame,
        targetPriority: TimetablesPriority
    ) {
        stagingFrame.priority = targetPriority.value
        vehicleScheduleFrameRepository.update(stagingFrame)
    }

    companion object {
        private fun validateTargetPriority(targetPriority: TimetablesPriority) {
            if (targetPriority in listOf(TimetablesPriority.STAGING)) {
                throw InvalidTargetPriorityException("Can not set priority $targetPriority as target.", targetPriority)
            }
        }
    }
}
