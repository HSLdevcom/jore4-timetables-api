package fi.hsl.jore4.timetables.api

import fi.hsl.jore4.timetables.api.util.InvalidTargetPriorityExtensions
import fi.hsl.jore4.timetables.api.util.JoreErrorExtensions
import fi.hsl.jore4.timetables.api.util.JoreErrorResponse
import fi.hsl.jore4.timetables.api.util.MultipleTargetFramesFoundExtensions
import fi.hsl.jore4.timetables.api.util.PlainStatusExtensions
import fi.hsl.jore4.timetables.api.util.StagingVehicleScheduleFrameNotFoundExtensions
import fi.hsl.jore4.timetables.api.util.TargetPriorityParsingExtensions
import fi.hsl.jore4.timetables.api.util.TargetVehicleScheduleFrameNotFoundExtensions
import fi.hsl.jore4.timetables.api.util.TransactionSystemExtensions
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.CombineTimetablesService
import fi.hsl.jore4.timetables.service.InvalidTargetPriorityException
import fi.hsl.jore4.timetables.service.MultipleTargetFramesFoundException
import fi.hsl.jore4.timetables.service.ReplaceTimetablesService
import fi.hsl.jore4.timetables.service.StagingVehicleScheduleFrameNotFoundException
import fi.hsl.jore4.timetables.service.TargetFrameNotFoundException
import jakarta.validation.Valid
import jakarta.validation.constraints.AssertTrue
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.TransactionSystemException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

private val LOGGER = KotlinLogging.logger {}

@RestController
@RequestMapping("/timetables")
class TimetablesController(
    private val combineTimetablesService: CombineTimetablesService,
    private val replaceTimetablesService: ReplaceTimetablesService
) {
    data class CombineOrReplaceTimetablesRequestBody(
        val stagingVehicleScheduleFrameIds: List<UUID>,
        val targetPriority: Int

    ) {
        @AssertTrue(message = "false")
        fun isTargetPriorityValid(): Boolean = TimetablesPriority.fromInt(targetPriority) != null
    }

    data class CombineTimetablesResponseBody(
        val combinedIntoVehicleScheduleFrameIds: List<UUID>
    )

    private fun parseTargetPriority(targetPriority: Int) = TimetablesPriority.fromInt(targetPriority)
        ?: throw TargetPriorityParsingException("Failed to parse target priority", targetPriority)

    @PostMapping("combine")
    fun combine(
        @Valid @RequestBody
        requestBody: CombineOrReplaceTimetablesRequestBody
    ): ResponseEntity<CombineTimetablesResponseBody> {
        LOGGER.debug { "Combine api, request: $requestBody" }
        val combineResult = combineTimetablesService.combineTimetables(
            requestBody.stagingVehicleScheduleFrameIds,
            parseTargetPriority(requestBody.targetPriority)
        )

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CombineTimetablesResponseBody(combinedIntoVehicleScheduleFrameIds = combineResult))
    }

    data class ReplaceTimetablesResponseBody(
        val replacedVehicleScheduleFrameIds: List<UUID>
    )

    @PostMapping("replace")
    fun replace(
        @Valid @RequestBody
        requestBody: CombineOrReplaceTimetablesRequestBody
    ): ResponseEntity<ReplaceTimetablesResponseBody> {
        LOGGER.debug { "Replace api, request: $requestBody" }
        val replaceResult = replaceTimetablesService.replaceTimetables(
            requestBody.stagingVehicleScheduleFrameIds,
            parseTargetPriority(requestBody.targetPriority)
        )

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ReplaceTimetablesResponseBody(replacedVehicleScheduleFrameIds = replaceResult))
    }

    data class ToReplaceTimetablesResponseBody(
        val toReplaceVehicleScheduleFrameIds: List<UUID>
    )

    data class ToCombineTimetablesResponseBody(
        val toCombineTargetVehicleScheduleFrameId: UUID
    )

    class TargetPriorityParsingException(message: String, val targetPriority: Int) : RuntimeException(message)

    @GetMapping("to-replace")
    fun getFrameIdsToBeReplaced(
        @RequestParam
        targetPriority: Int,
        @RequestParam
        stagingVehicleScheduleFrameId: UUID
    ): ResponseEntity<ToReplaceTimetablesResponseBody> {
        LOGGER.info { "ToReplace api, stagingVehicleScheduleFrameId: $stagingVehicleScheduleFrameId, targetPriority: $targetPriority" }

        val vehicleScheduleFrameIds = replaceTimetablesService.fetchVehicleScheduleFramesToReplace(
            stagingVehicleScheduleFrameId,
            parseTargetPriority(targetPriority)
        ).mapNotNull { it.vehicleScheduleFrameId }

        return ResponseEntity.status(HttpStatus.OK)
            .body(ToReplaceTimetablesResponseBody(toReplaceVehicleScheduleFrameIds = vehicleScheduleFrameIds))
    }

    @GetMapping("to-combine")
    fun getTargetFrameIdsForCombine(
        @RequestParam
        targetPriority: Int,
        @RequestParam
        stagingVehicleScheduleFrameId: UUID
    ): ResponseEntity<ToCombineTimetablesResponseBody> {
        LOGGER.info { "ToCombine api, stagingVehicleScheduleFrameId: $stagingVehicleScheduleFrameId, targetPriority: $targetPriority" }

        val targetVehicleScheduleFrame = combineTimetablesService.fetchTargetVehicleScheduleFrame(
            stagingVehicleScheduleFrameId,
            parseTargetPriority(targetPriority)
        )

        return ResponseEntity.status(HttpStatus.OK)
            .body(
                ToCombineTimetablesResponseBody(
                    // ID of an existing row, can never be null.
                    toCombineTargetVehicleScheduleFrameId = targetVehicleScheduleFrame.vehicleScheduleFrameId!!
                )
            )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<JoreErrorResponse> {
        val errorExtensions: JoreErrorExtensions = when (ex) {
            is InvalidTargetPriorityException -> InvalidTargetPriorityExtensions.from(ex)

            is StagingVehicleScheduleFrameNotFoundException -> StagingVehicleScheduleFrameNotFoundExtensions.from(ex)

            is TargetFrameNotFoundException -> TargetVehicleScheduleFrameNotFoundExtensions.from(ex)

            is MultipleTargetFramesFoundException -> MultipleTargetFramesFoundExtensions.from(ex)

            is TargetPriorityParsingException -> TargetPriorityParsingExtensions.from(ex)

            // Occurs on Commit / Rollback errors.
            is TransactionSystemException -> TransactionSystemExtensions.from(ex)

            else -> {
                LOGGER.error { "Exception during request:$ex" }
                LOGGER.error(ex.stackTraceToString())

                PlainStatusExtensions(HttpStatus.CONFLICT)
            }
        }

        val httpStatus: HttpStatus = errorExtensions.run {
            if (code !in 400..499) {
                LOGGER.warn { "Violating Hasura error response contract by returning code not like 4xx: $code" }
            }

            HttpStatus.resolve(code) ?: run {
                // This block should never be entered (and never will when using valid HTTP status codes).
                LOGGER.warn { "Could not resolve HttpStatus from code $code" }
                HttpStatus.BAD_REQUEST // default in case not resolved
            }
        }

        return ResponseEntity(JoreErrorResponse(ex.message, errorExtensions), httpStatus)
    }
}
