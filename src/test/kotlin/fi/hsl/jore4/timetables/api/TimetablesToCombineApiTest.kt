package fi.hsl.jore4.timetables.api

import com.ninjasquad.springmockk.MockkBean
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.CombineTimetablesService
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
class TimetablesToCombineApiTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockkBean
    private lateinit var combineTimetablesService: CombineTimetablesService

    private val defaultTargetFrame =
        VehicleScheduleFrame(
            vehicleScheduleFrameId = UUID.fromString("379076ee-d595-47e3-8050-2610d594b57c"),
            validityStart = LocalDate.now(),
            validityEnd = LocalDate.now(),
            priority = 20,
            label = "label"
        )
    private val defaultToCombineTargetId = defaultTargetFrame.vehicleScheduleFrameId

    private fun executeToCombineTimetablesRequest(
        stagingFrameId: UUID,
        targetPriority: Int
    ): ResultActions =
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/timetables/to-combine")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stagingVehicleScheduleFrameId", stagingFrameId.toString())
                .param("targetPriority", targetPriority.toString())
        )

    @Test
    fun `returns 200 and correct response when called successfully`() {
        val stagingVehicleScheduleFrameId = UUID.fromString("81f109d1-dbe2-412a-996e-aa510416b2e4")
        val targetPriority = TimetablesPriority.STANDARD

        every {
            combineTimetablesService.fetchTargetVehicleScheduleFrame(
                stagingVehicleScheduleFrameId,
                targetPriority
            )
        } answers { defaultTargetFrame }

        executeToCombineTimetablesRequest(stagingVehicleScheduleFrameId, targetPriority.value)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "toCombineTargetVehicleScheduleFrameId": $defaultToCombineTargetId
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.fetchTargetVehicleScheduleFrame(
                stagingVehicleScheduleFrameId,
                targetPriority
            )
        }
    }

    @Test
    fun `throws a 400 error when parsing target priority fails`() {
        val errorMessage = "Failed to parse target priority"
        val stagingVehicleScheduleFrameId = UUID.fromString("023281cd-51e9-4544-a2af-7b7e268e3a3a")
        val invalidTargetPriorityInput = 9999

        executeToCombineTimetablesRequest(stagingVehicleScheduleFrameId, invalidTargetPriorityInput)
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
            combineTimetablesService.fetchTargetVehicleScheduleFrame(
                stagingVehicleScheduleFrameId,
                any()
            )
        }
    }
}
