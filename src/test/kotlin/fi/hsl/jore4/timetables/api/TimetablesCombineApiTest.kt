package fi.hsl.jore4.timetables.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.service.CombineTimetablesService
import fi.hsl.jore4.timetables.service.InvalidTargetPriorityException
import fi.hsl.jore4.timetables.service.MultipleTargetFramesFoundException
import fi.hsl.jore4.timetables.service.StagingVehicleScheduleFrameNotFoundException
import fi.hsl.jore4.timetables.service.TargetFrameNotFoundException
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
import org.springframework.transaction.TransactionSystemException
import java.util.UUID

@ExtendWith(MockKExtension::class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class TimetablesCombineApiTest(
    @Autowired val mockMvc: MockMvc
) {
    @MockkBean
    private lateinit var combineTimetablesService: CombineTimetablesService

    private val defaultStagingFrameIds =
        listOf(
            UUID.fromString("eff6e408-eb1d-4cb5-ac05-f22d7eb8dfb3"),
            UUID.fromString("d4a36212-7729-4021-95fc-1b55cc158e9d")
        )
    private val defaultTargetPriority = TimetablesPriority.STANDARD
    private val defaultTargetFrameIds =
        listOf(
            UUID.fromString("b4d9d780-5575-489e-8180-b51765f4970d"),
            UUID.fromString("006c93ab-e4ff-4148-a973-08cf80fb87e9")
        )

    private fun executeCombineTimetablesRequest(
        stagingFrameIds: List<UUID> = defaultStagingFrameIds,
        targetPriority: Int = defaultTargetPriority.value
    ): ResultActions =
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/timetables/combine")
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

    @Test
    fun `returns 200 and a correct response when called successfully`() {
        every {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } answers { defaultTargetFrameIds }

        executeCombineTimetablesRequest()
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "combinedIntoVehicleScheduleFrameIds": $defaultTargetFrameIds
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(defaultStagingFrameIds, defaultTargetPriority)
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
            .andExpect(status().isConflict)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "something unexpected happened",
                      "extensions": {
                        "code": 409,
                        "type": "UnknownError"
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(defaultStagingFrameIds, defaultTargetPriority)
        }
    }

    @Test
    fun `throws a 409 when service transaction fails to commit`() {
        // Note: there are many kinds of transaction system errors (see `TransactionSystemException`).
        // Just using `ConflictingSchedulesError` here as an example instead of plain `TransactionSystemError`
        // to get better test coverage by involving error type detection.
        // Other transaction system errors are handled similarly,
        // type detection for each error case is tested in more detail in a separate suite.
        val sqlErrorMessage =
            "ERROR: conflicting schedules detected: vehicle schedule frame Where: " +
                "PL/pgSQL function vehicle_schedule.validate_queued_schedules_uniqueness()"

        every {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } throws
            TransactionSystemException(
                "JDBC Commit Failed",
                Exception(sqlErrorMessage)
            )

        executeCombineTimetablesRequest()
            .andExpect(status().isConflict)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "JDBC Commit Failed",
                      "extensions": {
                        "code": 409,
                        "type": "ConflictingSchedulesError",
                        "sqlErrorMessage": "$sqlErrorMessage"
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(defaultStagingFrameIds, defaultTargetPriority)
        }
    }

    @Test
    fun `throws an error when called with an invalid priority value`() {
        val invalidTargetPriority = 99
        every { combineTimetablesService.combineTimetables(any(), any()) } answers { defaultTargetFrameIds }

        // TODO: this could maybe use better handling.
        executeCombineTimetablesRequest(targetPriority = invalidTargetPriority)
            .andExpect(status().isBadRequest)
            .andExpect(content().string(""))

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
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 404,
                        "type": "StagingVehicleScheduleFrameNotFoundError",
                        "stagingVehicleScheduleFrameId": "$missingStagingFrameId"
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(defaultStagingFrameIds, defaultTargetPriority)
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
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 400,
                        "type": "InvalidTargetPriorityError",
                        "targetPriority": $invalidTargetPriority
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(defaultStagingFrameIds, invalidTargetPriority)
        }
    }

    @Test
    fun `throws a 404 error when the service fails because a target frame can not be found`() {
        val errorMessage = "Target frame not found."
        every {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } throws TargetFrameNotFoundException(errorMessage, defaultStagingFrameIds[0])

        executeCombineTimetablesRequest()
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 404,
                        "type": "TargetVehicleScheduleFrameNotFoundError",
                        "stagingVehicleScheduleFrameId": ${defaultStagingFrameIds[0]}
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(defaultStagingFrameIds, defaultTargetPriority)
        }
    }

    @Test
    fun `throws a 409 error when the service fails because there are multiple possible target frames`() {
        val errorMessage = "Multiple target frames found."
        every {
            combineTimetablesService.combineTimetables(
                defaultStagingFrameIds,
                defaultTargetPriority
            )
        } throws MultipleTargetFramesFoundException(errorMessage, defaultStagingFrameIds[0], defaultTargetFrameIds)

        executeCombineTimetablesRequest()
            .andExpect(status().isConflict)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                content().json(
                    """
                    {
                      "message": "$errorMessage",
                      "extensions": {
                        "code": 409,
                        "type": "MultipleTargetFramesFoundError",
                        "stagingVehicleScheduleFrameId": ${defaultStagingFrameIds[0]},
                        "targetVehicleScheduleFrameIds": $defaultTargetFrameIds
                      }
                    }
                    """.trimIndent(),
                    true
                )
            )

        verify(exactly = 1) {
            combineTimetablesService.combineTimetables(defaultStagingFrameIds, defaultTargetPriority)
        }
    }

    companion object {
        private val MAPPER = ObjectMapper()
    }
}
