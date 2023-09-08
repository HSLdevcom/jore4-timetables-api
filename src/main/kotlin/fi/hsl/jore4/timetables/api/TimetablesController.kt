package fi.hsl.jore4.timetables.api

import fi.hsl.jore4.timetables.api.util.HasuraErrorExtensions
import fi.hsl.jore4.timetables.api.util.HasuraErrorResponse
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.InvalidTargetPriorityException
import fi.hsl.jore4.timetables.service.ReplaceTimetablesService
import fi.hsl.jore4.timetables.service.StagingVehicleScheduleFrameNotFoundException
import jakarta.validation.Valid
import jakarta.validation.constraints.AssertTrue
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException
import java.util.*

private val LOGGER = KotlinLogging.logger {}

@RestController
@RequestMapping("/timetables")
class TimetablesController(
    private val replaceTimetablesService: ReplaceTimetablesService
) {
    data class CombineOrReplaceTimetablesRequestBody(
        val stagingVehicleScheduleFrameIds: List<UUID>,
        val targetPriority: Int

    ) {
        @AssertTrue(message = "false")
        fun isTargetPriorityValid(): Boolean = runCatching { TimetablesPriority.fromInt(targetPriority) }.isSuccess
    }

    data class ReplaceTimetablesResponseBody(
        val replacedVehicleScheduleFrameIds: List<UUID>
    )

    @PostMapping("replace")
    fun replace(
        @Valid @RequestBody
        requestBody: CombineOrReplaceTimetablesRequestBody
    ): ResponseEntity<ReplaceTimetablesResponseBody> {
        LOGGER.info { "Replace api, request: $requestBody" }
        val replaceResult = replaceTimetablesService.replaceTimetables(
            requestBody.stagingVehicleScheduleFrameIds,
            TimetablesPriority.fromInt(requestBody.targetPriority)
        )

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ReplaceTimetablesResponseBody(replacedVehicleScheduleFrameIds = replaceResult))
    }

    class InvalidTargetPriorityExtensions(
        override val code: Int,
        val targetPriority: TimetablesPriority
    ) : HasuraErrorExtensions(code)

    class StagingVehicleScheduleFrameNotFoundExtensions(
        override val code: Int,
        val stagingVehicleScheduleFrameId: UUID
    ) : HasuraErrorExtensions(code)

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<HasuraErrorResponse> {
        LOGGER.error { "Exception during request:$ex" }
        LOGGER.error(ex.stackTraceToString())

        var httpStatus = HttpStatus.CONFLICT // Hasura only wants errors on 4xx range.
        var hasuraErrorExtensions = HasuraErrorExtensions(httpStatus.value())
        var hasuraErrorResponse = HasuraErrorResponse(ex.message, hasuraErrorExtensions)

        return ResponseEntity(hasuraErrorResponse, httpStatus)
    }

    @ExceptionHandler(InvalidTargetPriorityException::class)
    fun handleInvalidTargetPriorityException(ex: InvalidTargetPriorityException): ResponseEntity<HasuraErrorResponse> {
        var httpStatus = HttpStatus.BAD_REQUEST
        var hasuraErrorExtensions = InvalidTargetPriorityExtensions(httpStatus.value(), ex.targetPriority)
        var hasuraErrorResponse = HasuraErrorResponse(ex.message, hasuraErrorExtensions)

        return ResponseEntity(hasuraErrorResponse, httpStatus)
    }

    @ExceptionHandler(StagingVehicleScheduleFrameNotFoundException::class)
    fun handleStagingVehicleScheduleFrameNotFoundException(ex: StagingVehicleScheduleFrameNotFoundException): ResponseEntity<HasuraErrorResponse> {
        var httpStatus = HttpStatus.NOT_FOUND
        var hasuraErrorExtensions = StagingVehicleScheduleFrameNotFoundExtensions(httpStatus.value(), ex.stagingVehicleScheduleFrameId)
        var hasuraErrorResponse = HasuraErrorResponse(ex.message, hasuraErrorExtensions)

        return ResponseEntity(hasuraErrorResponse, httpStatus)
    }
}
