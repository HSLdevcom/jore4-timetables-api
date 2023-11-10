package fi.hsl.jore4.timetables.api

import com.ninjasquad.springmockk.MockkBean
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.ReplaceTimetablesService
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.util.UUID

@ExtendWith(MockKExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class TimetablesToReplaceApiTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var replaceTimetablesService: ReplaceTimetablesService

    private val defaultVehicleScheduleFrames = listOf(
        VehicleScheduleFrame(
            vehicleScheduleFrameId = UUID.fromString("d541ced9-8c15-4320-9c13-0c5c34067984"),
            validityStart = LocalDate.now(),
            validityEnd = LocalDate.now(),
            priority = 20,
            label = "label"
        ),
        VehicleScheduleFrame(
            vehicleScheduleFrameId = UUID.fromString("c7f851c7-016c-4ba9-ac2e-34fc285ce233"),
            validityStart = LocalDate.now(),
            validityEnd = LocalDate.now(),
            priority = 20,
            label = "label"
        )
    )
    private val defaultToReplaceIds = defaultVehicleScheduleFrames.map { it.vehicleScheduleFrameId }
    private fun executeToReplaceTimetablesRequest(
        stagingFrameId: UUID,
        targetPriority: Int
    ): ResultActions {
        return mockMvc.perform(
            MockMvcRequestBuilders.get("/timetables/to-replace")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stagingVehicleScheduleFrameId", stagingFrameId.toString())
                .param("targetPriority", targetPriority.toString())
        )
    }

    @Test
    fun `returns 200 and correct response when called successfully`() {
        val stagingVehicleScheduleFrameId = UUID.fromString("9e758776-2af1-49c7-8bd0-c0805b833b20")
        val targetPriority = 10

        every {
            replaceTimetablesService.fetchVehicleScheduleFramesToReplace(
                stagingVehicleScheduleFrameId,
                TimetablesPriority.fromInt(targetPriority)
            )
        } answers { defaultVehicleScheduleFrames }

        executeToReplaceTimetablesRequest(stagingVehicleScheduleFrameId, targetPriority)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "toReplaceVehicleScheduleFrameIds": $defaultToReplaceIds
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            replaceTimetablesService.fetchVehicleScheduleFramesToReplace(
                stagingVehicleScheduleFrameId,
                TimetablesPriority.fromInt(targetPriority)
            )
        }
    }

    @Test
    fun `throws a 400 error when parsing target priority fails`() {
        val errorMessage = "Failed to parse target priority"
        val stagingVehicleScheduleFrameId = UUID.fromString("9e758776-2af1-49c7-8bd0-c0805b833b20")
        val invalidTargetPriorityInput = 9999

        executeToReplaceTimetablesRequest(stagingVehicleScheduleFrameId, invalidTargetPriorityInput)
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 400,
                        "type": "TargetPriorityParsingError",
                        "targetPriority": $invalidTargetPriorityInput
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )
        verify(exactly = 0) {
            replaceTimetablesService.fetchVehicleScheduleFramesToReplace(
                stagingVehicleScheduleFrameId,
                any()
            )
        }
    }
}
