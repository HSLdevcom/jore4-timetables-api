package fi.hsl.jore4.timetables.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import fi.hsl.jore4.timetables.service.ReplaceTimetablesService
import fi.hsl.jore4.timetables.service.TargetPriorityParsingException
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID

@ExtendWith(MockKExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
class TimetablesToReplaceApiTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var replaceTimetablesService: ReplaceTimetablesService

    private val defaultToReplaceIds = listOf(UUID.fromString("d541ced9-8c15-4320-9c13-0c5c34067984"))

    private fun executeToReplaceTimetablesRequest(
        stagingFrameId: UUID,
        targetPriority: Int
    ): ResultActions {
        return mockMvc.perform(
            MockMvcRequestBuilders.get("/timetables/toReplace")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stagingVehicleScheduleFrameId", stagingFrameId.toString())
                .param("targetPriority", targetPriority.toString())
        )
    }

    @Test
    fun `returns 200 and correct response when called successfully`() {
        val stagingVehicleScheduleFrameId = UUID.fromString("9e758776-2af1-49c7-8bd0-c0805b833b20")
        val targetPriority = 20

        every {
            replaceTimetablesService.fetchVehicleScheduleFrameIdsToReplace(
                stagingVehicleScheduleFrameId,
                targetPriority
            )
        } answers { defaultToReplaceIds }

        executeToReplaceTimetablesRequest(stagingVehicleScheduleFrameId, targetPriority)
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                    {
                      "toReplaceVehicleScheduleFrameIds": ${TimetablesToReplaceApiTest.OBJECT_MAPPER.writeValueAsString(defaultToReplaceIds)}
                    }
                    """.trimIndent()
                )
            )

        verify(exactly = 1) {
            replaceTimetablesService.fetchVehicleScheduleFrameIdsToReplace(
                stagingVehicleScheduleFrameId,
                targetPriority
            )
        }
    }

    @Test
    fun `throws a 400 error when parsing target priority fails`() {
        val errorMessage = "Failed to parse target priority"
        val stagingVehicleScheduleFrameId = UUID.fromString("9e758776-2af1-49c7-8bd0-c0805b833b20")
        val invalidTargetPriorityInput = 9999
        every {
            replaceTimetablesService.fetchVehicleScheduleFrameIdsToReplace(
                stagingVehicleScheduleFrameId,
                invalidTargetPriorityInput
            )
        } throws TargetPriorityParsingException(errorMessage, invalidTargetPriorityInput)

        executeToReplaceTimetablesRequest(stagingVehicleScheduleFrameId, invalidTargetPriorityInput)
            .andExpect(status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 400,
                        "targetPriority": $invalidTargetPriorityInput
                      }
                    }
                    """.trimIndent()
                )
            )
        verify(exactly = 1) {
            replaceTimetablesService.fetchVehicleScheduleFrameIdsToReplace(
                stagingVehicleScheduleFrameId,
                invalidTargetPriorityInput
            )
        }
    }
    companion object {
        private val OBJECT_MAPPER = ObjectMapper()
    }
}
