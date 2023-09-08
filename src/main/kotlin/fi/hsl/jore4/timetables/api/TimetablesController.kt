package fi.hsl.jore4.timetables.api

import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.CombineTimetablesService
import fi.hsl.jore4.timetables.service.ReplaceTimetablesService
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
    private val combineTimetablesService: CombineTimetablesService,
    private val replaceTimetablesService: ReplaceTimetablesService
) {
    data class CombineOrReplaceTimetablesRequestBody(
        val stagingVehicleScheduleFrameIds: List<UUID>,
        val targetPriority: Int
    )

    data class CombineTimetablesResponseBody(
        val combinedIntoVehicleScheduleFrameIds: List<UUID>,
    )

    @PostMapping("combine")
    fun combine(@RequestBody requestBody: CombineOrReplaceTimetablesRequestBody): ResponseEntity<CombineTimetablesResponseBody> {
        LOGGER.info("Combine api, request: ${requestBody.stagingVehicleScheduleFrameIds}, ${requestBody.targetPriority}")
        val combineResult = combineTimetablesService.combineTimetables(
            requestBody.stagingVehicleScheduleFrameIds,
            TimetablesPriority.fromInt(requestBody.targetPriority)
        )
        LOGGER.info("Combined, return response..")

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(CombineTimetablesResponseBody(combinedIntoVehicleScheduleFrameIds = combineResult))
    }

    data class ReplaceTimetablesResponseBody(
        val replacedVehicleScheduleFrameIds: List<UUID>,
    )

    @PostMapping("replace")
    fun replace(@RequestBody requestBody: CombineOrReplaceTimetablesRequestBody): ResponseEntity<ReplaceTimetablesResponseBody> {
        LOGGER.info("Replace api, request: ${requestBody.stagingVehicleScheduleFrameIds}, ${requestBody.targetPriority}")
        val replaceResult = replaceTimetablesService.replaceTimetables(
            requestBody.stagingVehicleScheduleFrameIds,
            TimetablesPriority.fromInt(requestBody.targetPriority)
        )
        LOGGER.info("Combined, return response..")

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ReplaceTimetablesResponseBody(replacedVehicleScheduleFrameIds = replaceResult))
    }
}
