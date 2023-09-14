package fi.hsl.jore4.timetables.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.CombineTimetablesService
import fi.hsl.jore4.timetables.service.InvalidTargetPriorityException
import fi.hsl.jore4.timetables.service.StagingVehicleScheduleFrameNotFoundException
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
import java.util.UUID

@ExtendWith(MockKExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
class TimetablesCombineApiTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var combineTimetablesService: CombineTimetablesService

    private val defaultStagingFrameIds = listOf(
        UUID.fromString("eff6e408-eb1d-4cb5-ac05-f22d7eb8dfb3"),
        UUID.fromString("d4a36212-7729-4021-95fc-1b55cc158e9d")
    )
    private val defaultTargetPriority = TimetablesPriority.STANDARD
    private val defaultTargetFrameIds = listOf(
        UUID.fromString("b4d9d780-5575-489e-8180-b51765f4970d"),
        UUID.fromString("006c93ab-e4ff-4148-a973-08cf80fb87e9")
    )

    private fun executeCombineTimetablesRequest(
        stagingFrameIds: List<UUID> = defaultStagingFrameIds,
        targetPriority: Int = defaultTargetPriority.value
    ): ResultActions {
        return mockMvc.perform(
            MockMvcRequestBuilders.post("/timetables/combine")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "stagingVehicleScheduleFrameIds": ${MAPPER.writeValueAsString(stagingFrameIds)},
                      "targetPriority": $targetPriority
                    }
                    """.trimIndent()
                )
        )
    }

    @Test
    fun `returns 200 and a correct response when called successfully`() {
        every {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } answers { defaultTargetFrameIds }

        executeCombineTimetablesRequest()
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                    {
                      "combinedIntoVehicleScheduleFrameIds": ${MAPPER.writeValueAsString(defaultTargetFrameIds)}
                    }
                    """.trimIndent()
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        }
    }

    @Test
    fun `throws a 409 when an unexpected RuntimeException occurs`() {
        every {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } throws RuntimeException("something unexpected happened")

        executeCombineTimetablesRequest()
            .andExpect(MockMvcResultMatchers.status().isConflict)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                    {
                      "message": "something unexpected happened",
                      "extensions": {
                        "code": 409
                      }
                    }
                    """.trimIndent()
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        }
    }

    @Test
    fun `throws an error when called with an invalid priority value`() {
        val invalidTargetPriority = 99
        every { combineTimetablesService.combineTimetables(any(), any()) } answers { defaultTargetFrameIds }

        // TODO: this could maybe use better handling.
        executeCombineTimetablesRequest(targetPriority = invalidTargetPriority)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string(""))

        verify(exactly = 0) { combineTimetablesService.combineTimetables(any(), any()) }
    }

    @Test
    fun `throws a 404 error when the service fails because the staging frame can not be found`() {
        val errorMessage = "Staging vehicle schedule frame not found"
        val missingStagingFrameId = defaultStagingFrameIds[1]
        every {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } throws StagingVehicleScheduleFrameNotFoundException(errorMessage, missingStagingFrameId)

        executeCombineTimetablesRequest()
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 404,
                        "stagingVehicleScheduleFrameId": "$missingStagingFrameId"
                      }
                    }
                    """.trimIndent()
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        }
    }

    @Test
    fun `throws a 400 error when the service fails because the target priority is invalid`() {
        val errorMessage = "Can not set priority STAGING as target."
        val invalidTargetPriority = TimetablesPriority.STAGING
        every {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                invalidTargetPriority
            )
        } throws InvalidTargetPriorityException(errorMessage, invalidTargetPriority)

        executeCombineTimetablesRequest(targetPriority = invalidTargetPriority.value)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 400,
                        "targetPriority": $invalidTargetPriority
                      }
                    }
                    """.trimIndent()
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                invalidTargetPriority
            )
        }
    }

    companion object {
        private val MAPPER = ObjectMapper()
    }
}
