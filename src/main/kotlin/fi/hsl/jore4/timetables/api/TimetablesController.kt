package fi.hsl.jore4.timetables.api

import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.ReplaceTimetablesService
import jakarta.validation.Valid
import jakarta.validation.constraints.AssertTrue
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
}
