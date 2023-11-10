package fi.hsl.jore4.timetables.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.InvalidTargetPriorityException
import fi.hsl.jore4.timetables.service.ReplaceTimetablesService
import fi.hsl.jore4.timetables.service.StagingVehicleScheduleFrameNotFoundException
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import mu.KotlinLogging
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
import org.springframework.transaction.TransactionSystemException
import java.util.UUID

private val LOGGER = KotlinLogging.logger {}

@ExtendWith(MockKExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class TimetablesReplaceApiTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var replaceTimetablesService: ReplaceTimetablesService

    private val defaultStagingFrameIds = listOf(
        UUID.fromString("94cc71bc-c276-4c34-8e7c-47452f9481ac"),
        UUID.fromString("83e04376-81c7-4b83-8301-a5c9ee253014")
    )
    private val defaultTargetPriority = TimetablesPriority.STANDARD
    private val defaultReplacedFrameIds = listOf(
        UUID.fromString("976b9c25-2d7e-42fa-8a59-b538486a5554"),
        UUID.fromString("db9bb4d3-8ea2-4041-bf5a-f6c9b86c7fcc"),
        UUID.fromString("ae791d72-6f3d-4618-940b-16a440019120")
    )

    private fun executeReplaceTimetablesRequest(
        stagingFrameIds: List<UUID> = defaultStagingFrameIds,
        targetPriority: Int = defaultTargetPriority.value
    ): ResultActions {
        return mockMvc.perform(
            MockMvcRequestBuilders.post("/timetables/replace")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "stagingVehicleScheduleFrameIds": ${OBJECT_MAPPER.writeValueAsString(stagingFrameIds)},
                      "targetPriority": $targetPriority
                    }
                    """.trimIndent()
                )
        )
    }

    @Test
    fun `returns 200 and a correct response when called successfully`() {
        every {
            replaceTimetablesService.replaceTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } answers { defaultReplacedFrameIds }

        executeReplaceTimetablesRequest()
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "replacedVehicleScheduleFrameIds": $defaultReplacedFrameIds
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            replaceTimetablesService.replaceTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        }
    }

    @Test
    fun `throws a 409 when an unexpected RuntimeException occurs`() {
        every {
            replaceTimetablesService.replaceTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } throws RuntimeException("something unexpected happened")

        executeReplaceTimetablesRequest()
            .andExpect(status().isConflict)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "something unexpected happened",
                      "extensions": {
                        "code": 409
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            replaceTimetablesService.replaceTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        }
    }

    @Test
    fun `throws a 409 when service transaction fails to commit`() {
        val sqlErrorMessage = "ERROR: conflicting schedules detected: vehicle schedule frame Where: PL/pgSQL function vehicle_schedule.validate_queued_schedules_uniqueness()"

        every {
            replaceTimetablesService.replaceTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } throws TransactionSystemException(
            "JDBC Commit Failed",
            Exception(sqlErrorMessage)
        )

        executeReplaceTimetablesRequest()
            .andExpect(status().isConflict)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "JDBC Commit Failed",
                      "extensions": {
                        "code": 409,
                        "sqlErrorMessage": "$sqlErrorMessage"
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            replaceTimetablesService.replaceTimetables(defaultStagingFrameIds, defaultTargetPriority)
        }
    }

    @Test
    fun `throws an error when called with an invalid priority value`() {
        val invalidTargetPriority = 99
        every { replaceTimetablesService.replaceTimetables(any(), any()) } answers { defaultReplacedFrameIds }

        // TODO: this could maybe use better handling.
        executeReplaceTimetablesRequest(targetPriority = invalidTargetPriority)
            .andExpect(status().isBadRequest)
            .andExpect(content().string(""))

        verify(exactly = 0) { replaceTimetablesService.replaceTimetables(any(), any()) }
    }

    @Test
    fun `throws a 404 error when the service fails because the staging frame can not be found`() {
        val errorMessage = "Staging vehicle schedule frame not found"
        val missingStagingFrameId = defaultStagingFrameIds[1]
        every {
            replaceTimetablesService.replaceTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } throws StagingVehicleScheduleFrameNotFoundException(errorMessage, missingStagingFrameId)

        executeReplaceTimetablesRequest()
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 404,
                        "stagingVehicleScheduleFrameId": "$missingStagingFrameId"
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            replaceTimetablesService.replaceTimetables(
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
            replaceTimetablesService.replaceTimetables(
                defaultStagingFrameIds,
                invalidTargetPriority
            )
        } throws InvalidTargetPriorityException(errorMessage, invalidTargetPriority)

        executeReplaceTimetablesRequest(targetPriority = invalidTargetPriority.value)
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 400,
                        "targetPriority": $invalidTargetPriority
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            replaceTimetablesService.replaceTimetables(
                defaultStagingFrameIds,
                invalidTargetPriority
            )
        }
    }

    companion object {
        private val OBJECT_MAPPER = ObjectMapper()
    }
}
