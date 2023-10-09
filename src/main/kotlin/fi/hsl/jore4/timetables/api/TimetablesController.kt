package fi.hsl.jore4.timetables.api

import fi.hsl.jore4.timetables.api.util.HasuraErrorExtensions
import fi.hsl.jore4.timetables.api.util.HasuraErrorResponse
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.CombineTimetablesService
import fi.hsl.jore4.timetables.service.InvalidTargetPriorityException
import fi.hsl.jore4.timetables.service.MultipleTargetFramesFoundException
import fi.hsl.jore4.timetables.service.ReplaceTimetablesService
import fi.hsl.jore4.timetables.service.StagingVehicleScheduleFrameNotFoundException
import fi.hsl.jore4.timetables.service.TargetFrameNotFoundException
import fi.hsl.jore4.timetables.service.TargetPriorityParsingException
import jakarta.validation.Valid
import jakarta.validation.constraints.AssertTrue
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        fun isTargetPriorityValid(): Boolean = runCatching { TimetablesPriority.fromInt(targetPriority) }.isSuccess
    }

    data class CombineTimetablesResponseBody(
        val combinedIntoVehicleScheduleFrameIds: List<UUID>
    )

    @PostMapping("combine")
    fun combine(
        @Valid @RequestBody
        requestBody: CombineOrReplaceTimetablesRequestBody
    ): ResponseEntity<CombineTimetablesResponseBody> {
        LOGGER.debug { "Combine api, request: $requestBody" }
        val combineResult = combineTimetablesService.combineTimetables(
            requestBody.stagingVehicleScheduleFrameIds,
            TimetablesPriority.fromInt(requestBody.targetPriority)
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
            TimetablesPriority.fromInt(requestBody.targetPriority)
        )

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ReplaceTimetablesResponseBody(replacedVehicleScheduleFrameIds = replaceResult))
    }

    data class ToReplaceTimetablesResponseBody(
        val toReplaceVehicleScheduleFrameIds: List<UUID>
    )

    @GetMapping("toReplace")
    fun replaced(
        @Valid @RequestParam
        targetPriority: Int,
        @Valid @RequestParam
        stagingVehicleScheduleFrameId: UUID
    ): ResponseEntity<ToReplaceTimetablesResponseBody> {
        LOGGER.info { "ToReplace api, stagingVehicleScheduleFrameId: $stagingVehicleScheduleFrameId, targetPriority: $targetPriority" }

        val ids = replaceTimetablesService.fetchVehicleScheduleFrameIdsToReplace(
            stagingVehicleScheduleFrameId,
            targetPriority
        )

        return ResponseEntity.status(HttpStatus.OK)
            .body(ToReplaceTimetablesResponseBody(toReplaceVehicleScheduleFrameIds = ids))
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<HasuraErrorResponse> {
        LOGGER.error { "Exception during request:$ex" }
        LOGGER.error(ex.stackTraceToString())

        val httpStatus = HttpStatus.CONFLICT // Hasura only wants errors on 4xx range.
        val hasuraErrorExtensions = HasuraErrorExtensions(httpStatus.value())
        val hasuraErrorResponse = HasuraErrorResponse(ex.message, hasuraErrorExtensions)

        return ResponseEntity(hasuraErrorResponse, httpStatus)
    }

    class InvalidTargetPriorityExtensions(
        override val code: Int,
        val targetPriority: TimetablesPriority
    ) : HasuraErrorExtensions(code)

    @ExceptionHandler(InvalidTargetPriorityException::class)
    fun handleInvalidTargetPriorityException(ex: InvalidTargetPriorityException): ResponseEntity<HasuraErrorResponse> {
        val httpStatus = HttpStatus.BAD_REQUEST
        val hasuraErrorExtensions = InvalidTargetPriorityExtensions(httpStatus.value(), ex.targetPriority)
        val hasuraErrorResponse = HasuraErrorResponse(ex.message, hasuraErrorExtensions)

        return ResponseEntity(hasuraErrorResponse, httpStatus)
    }

    class StagingVehicleScheduleFrameNotFoundExtensions(
        override val code: Int,
        val stagingVehicleScheduleFrameId: UUID
    ) : HasuraErrorExtensions(code)

    @ExceptionHandler(StagingVehicleScheduleFrameNotFoundException::class)
    fun handleStagingVehicleScheduleFrameNotFoundException(ex: StagingVehicleScheduleFrameNotFoundException): ResponseEntity<HasuraErrorResponse> {
        val httpStatus = HttpStatus.NOT_FOUND
        val hasuraErrorExtensions = StagingVehicleScheduleFrameNotFoundExtensions(
            httpStatus.value(),
            ex.stagingVehicleScheduleFrameId
        )
        val hasuraErrorResponse = HasuraErrorResponse(ex.message, hasuraErrorExtensions)

        return ResponseEntity(hasuraErrorResponse, httpStatus)
    }

    class TargetVehicleScheduleFrameNotFoundExtensions(
        override val code: Int,
        val stagingVehicleScheduleFrameId: UUID
    ) : HasuraErrorExtensions(code)

    @ExceptionHandler(TargetFrameNotFoundException::class)
    fun handleTargetFrameNotFoundException(ex: TargetFrameNotFoundException): ResponseEntity<HasuraErrorResponse> {
        val httpStatus = HttpStatus.NOT_FOUND
        val hasuraErrorExtensions = TargetVehicleScheduleFrameNotFoundExtensions(
            httpStatus.value(),
            ex.stagingVehicleScheduleFrameId
        )
        val hasuraErrorResponse = HasuraErrorResponse(ex.message, hasuraErrorExtensions)

        return ResponseEntity(hasuraErrorResponse, httpStatus)
    }

    class MultipleTargetFramesFoundExtensions(
        override val code: Int,
        val stagingVehicleScheduleFrameId: UUID,
        val targetVehicleScheduleFrameIds: List<UUID>
    ) : HasuraErrorExtensions(code)

    @ExceptionHandler(MultipleTargetFramesFoundException::class)
    fun handleTargetFrameNotFoundException(ex: MultipleTargetFramesFoundException): ResponseEntity<HasuraErrorResponse> {
        val httpStatus = HttpStatus.CONFLICT
        val hasuraErrorExtensions = MultipleTargetFramesFoundExtensions(
            httpStatus.value(),
            ex.stagingVehicleScheduleFrameId,
            ex.targetVehicleScheduleFrameIds
        )
        val hasuraErrorResponse = HasuraErrorResponse(ex.message, hasuraErrorExtensions)

        return ResponseEntity(hasuraErrorResponse, httpStatus)
    }

    class TargetPriorityParsingExtensions(
        override val code: Int,
        val targetPriority: Int
    ) : HasuraErrorExtensions(code)

    @ExceptionHandler(TargetPriorityParsingException::class)
    fun handleIncompatibleTargetPriorityException(ex: TargetPriorityParsingException): ResponseEntity<HasuraErrorResponse> {
        val httpStatus = HttpStatus.BAD_REQUEST
        val hasuraErrorExtensions = TargetPriorityParsingExtensions(
            httpStatus.value(),
            ex.targetPriority
        )
        val hasuraErrorResponse = HasuraErrorResponse(ex.message, hasuraErrorExtensions)

        return ResponseEntity(hasuraErrorResponse, httpStatus)
    }
}
