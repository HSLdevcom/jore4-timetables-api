package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.TimetablesDataset
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.extensions.getNested
import fi.hsl.jore4.timetables.repository.VehicleScheduleFrameRepository
import fi.hsl.jore4.timetables.repository.VehicleServiceRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import mu.KotlinLogging
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

private val LOGGER = KotlinLogging.logger {}

@SpringBootTest
class CombineTimetablesServiceTest @Autowired constructor(
    val combineTimetablesService: CombineTimetablesService,
    var timetablesDataInserterRunner: TimetablesDataInserterRunner,
    val vehicleServiceRepository: VehicleServiceRepository,
    val vehicleScheduleFrameRepository: VehicleScheduleFrameRepository
) {

    private fun fetchSingleFrameById(frameId: UUID): VehicleScheduleFrame {
        return vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(frameId)
            ?: throw Exception("Vehicle schedule frame $frameId not found")
    }

    // Most of the logic is implemented in combineSingleTimetable and thus tested under its tests.
    @Nested
    @ExtendWith(MockKExtension::class)
    @MockKExtension.ConfirmVerification
    @DisplayName("combineTimetables")
    class CombineTimetables {
        @MockK
        private val combineTimetablesServiceMock = mockk<CombineTimetablesService>()

        private val stagingFrameIds = listOf(
            UUID.fromString("481f8c53-ac32-41c6-8935-4582cfc04528"),
            UUID.fromString("fe590eda-159d-418f-87ef-6bf61a4a67f5"),
            UUID.fromString("e5256a94-0d42-4767-ad78-58f2da526ec0")
        )
        private val targetFrameIds = listOf(
            UUID.fromString("29b04636-7127-45c7-bc20-1dfc25972127"),
            UUID.fromString("ca97530d-744a-42e3-b3cf-7a92eb625dbf"),
            UUID.fromString("a25bba37-308f-484c-905e-bf66ceaf4bdc")
        )

        @BeforeEach
        fun setupCombineTimetables() {
            every { combineTimetablesServiceMock.combineTimetables(any(), any()) } answers { callOriginal() }
        }

        @AfterEach
        fun clearCombineTimetablesVerification() {
            verify { combineTimetablesServiceMock.combineTimetables(any(), any()) }
        }

        @Test
        fun `combine each staging timetables and returns target vehicle schedule frame ids`() {
            every {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[0],
                    TimetablesPriority.STANDARD
                )
            } returns targetFrameIds[0]
            every {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[1],
                    TimetablesPriority.STANDARD
                )
            } returns targetFrameIds[1]
            every {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[2],
                    TimetablesPriority.STANDARD
                )
            } returns targetFrameIds[2]

            val result = combineTimetablesServiceMock.combineTimetables(
                stagingFrameIds,
                TimetablesPriority.STANDARD
            )
            assertEquals(result, targetFrameIds)

            verify(exactly = 1) {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[0],
                    TimetablesPriority.STANDARD
                )
            }
            verify(exactly = 1) {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[1],
                    TimetablesPriority.STANDARD
                )
            }
            verify(exactly = 1) {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[2],
                    TimetablesPriority.STANDARD
                )
            }
        }

        @Test
        fun `fails if any of the requested staging frames do not get handled successfully`() {
            every {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[0],
                    TimetablesPriority.STANDARD
                )
            } returns targetFrameIds[0]
            every {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[1],
                    TimetablesPriority.STANDARD
                )
            } throws TargetTimetableNotFoundException(
                "Target timetable not found",
                stagingFrameIds[1]
            )
            every {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[2],
                    TimetablesPriority.STANDARD
                )
            } returns targetFrameIds[2]

            assertFailsWith<TargetTimetableNotFoundException> {
                combineTimetablesServiceMock.combineTimetables(
                    stagingFrameIds,
                    TimetablesPriority.STANDARD
                )
            }

            verify(exactly = 1) {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[0],
                    TimetablesPriority.STANDARD
                )
            }
            verify(exactly = 1) {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[1],
                    TimetablesPriority.STANDARD
                )
            }
            verify(exactly = 0) {
                combineTimetablesServiceMock.combineSingleTimetable(
                    stagingFrameIds[2],
                    TimetablesPriority.STANDARD
                )
            }
        }
    }

    @Nested
    @DisplayName("combineSingleTimetable")
    inner class CombineSingleTimetable {
        @Nested
        @DisplayName("when successfully combining a single new vehicle journey")
        inner class WhenSuccessfullyCombiningSingleNewVehicleJourney {
            private val stagingFrameId = UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494")
            private val targetFrameId = UUID.fromString("bb2abc90-8e91-4b0b-a7e4-751a04e81ba3")

            private lateinit var result: UUID
            private lateinit var initialTargetFrame: VehicleScheduleFrame
            private lateinit var initialStagingFrame: VehicleScheduleFrame

            @BeforeEach
            fun runCombineSingleTimetable() {
                val testData = TimetablesDataset.createFromResource("datasets/combine.json")
                timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

                initialTargetFrame = fetchSingleFrameById(targetFrameId)
                initialStagingFrame = fetchSingleFrameById(stagingFrameId)

                result = combineTimetablesService.combineSingleTimetable(
                    stagingFrameId,
                    TimetablesPriority.STANDARD
                )
            }

            @Test
            fun `returns id of the target vehicle schedule frame`() {
                assertEquals(targetFrameId, result)
            }

            @Test
            fun `moves vehicle services under target vehicle schedule frame`() {
                val combinedVehicleService =
                    vehicleServiceRepository.findById(UUID.fromString("535d3088-acff-41c8-b03d-2d25e468f7f8"))
                assertNotNull(combinedVehicleService)
                assertEquals(combinedVehicleService.vehicleScheduleFrameId, targetFrameId)
            }

            @Test
            fun `deletes the old staging vehicle schedule frame`() {
                val stagingFrame = vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(stagingFrameId)
                assertNull(stagingFrame)
            }
        }

        @Test
        fun `fails when called with invalid target priority = staging`() {
            val testData = TimetablesDataset.createFromResource("datasets/combine.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val stagingFrameId = UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494")

            val exception = assertFailsWith<InvalidTargetPriorityException> {
                combineTimetablesService.combineSingleTimetable(
                    stagingFrameId,
                    TimetablesPriority.STAGING
                )
            }

            assertContains(exception.message.toString(), "Can not set priority STAGING as target.")
        }

        @Test
        fun `fails when staging timetable does not exist`() {
            val testData = TimetablesDataset.createFromResource("datasets/combine.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val nonexistentStagingFrameId = UUID.fromString("DEADBEEF-FEED-C0DE-F00D-ABBA1234ABBA")

            assertFailsWith<StagingVehicleScheduleFrameNotFoundException> {
                combineTimetablesService.combineSingleTimetable(
                    nonexistentStagingFrameId,
                    TimetablesPriority.STANDARD
                )
            }
        }

        @Test
        fun `fails when staging timetable is not staging`() {
            val testData = TimetablesDataset.createFromResource("datasets/combine.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val nonStagingFrameId = UUID.fromString(
                testData.getNested("_vehicle_schedule_frames.target")["vehicle_schedule_frame_id"].toString()
            )

            assertFailsWith<StagingVehicleScheduleFrameNotFoundException> {
                combineTimetablesService.combineSingleTimetable(
                    nonStagingFrameId,
                    TimetablesPriority.STANDARD
                )
            }
        }

        @Nested
        @DisplayName("fails when target vehicle schedule can not be found")
        inner class FailsWhenTargetVehicleScheduleCanNotBeFound {
            private lateinit var testData: TimetablesDataset

            @BeforeEach
            private fun loadTestDataResource() {
                testData = TimetablesDataset.createFromResource("datasets/combine.json")
            }

            private fun runCombineAndAssertTargetNotFoundException() {
                timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

                assertFailsWith<TargetTimetableNotFoundException> {
                    combineTimetablesService.combineSingleTimetable(
                        UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494"),
                        TimetablesPriority.STANDARD
                    )
                }
            }

            @Test
            fun `because it has no shared routes`() {
                // Drop most vehicle journeys from staging and target, only leaving routes not found from both.
                val stagingBlock = testData
                    .getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri._blocks.block")
                val targetBlock = testData
                    .getNested("_vehicle_schedule_frames.target._vehicle_services.monFri._blocks.block")
                var stagingVehicleJourneys = stagingBlock.getNested("_vehicle_journeys")
                var targetVehicleJourneys = targetBlock.getNested("_vehicle_journeys")

                stagingVehicleJourneys = mutableMapOf(
                    "route123Outbound1" to stagingVehicleJourneys["route123Outbound1"]
                )
                targetVehicleJourneys = mutableMapOf(
                    "route234Outbound1" to targetVehicleJourneys["route234Outbound1"]
                )

                stagingBlock["_vehicle_journeys"] = stagingVehicleJourneys
                targetBlock["_vehicle_journeys"] = targetVehicleJourneys

                runCombineAndAssertTargetNotFoundException()
            }

            @Test
            fun `because its validity start is different`() {
                testData.getNested("_vehicle_schedule_frames.target")["validity_start"] = "2022-01-23"

                runCombineAndAssertTargetNotFoundException()
            }

            @Test
            fun `because its validity end is different`() {
                testData.getNested("_vehicle_schedule_frames.target")["validity_end"] = "2023-07-11"

                runCombineAndAssertTargetNotFoundException()
            }

            @Test
            fun `because priority does not match requested target priority`() {
                testData.getNested("_vehicle_schedule_frames.target")["priority"] = "Temporary"

                runCombineAndAssertTargetNotFoundException()
            }
        }

        @Test
        fun `fails when target frame already contains the staging vehicle journey`() {
            val testData = TimetablesDataset.createFromResource("datasets/combine.json")

            val stagingBlock = testData
                .getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri._blocks.block")
            val targetBlock = testData
                .getNested("_vehicle_schedule_frames.target._vehicle_services.monFri._blocks.block")
            var stagingVehicleJourneys = stagingBlock.getNested("_vehicle_journeys")
            var targetVehicleJourneys = targetBlock.getNested("_vehicle_journeys")

            stagingVehicleJourneys = mutableMapOf(
                "route123Outbound1" to stagingVehicleJourneys["route123Outbound1"]
            )
            targetVehicleJourneys = mutableMapOf(
                // Identical vehicle journey as the one about to come from staging -> fail.
                "route123Outbound1" to stagingVehicleJourneys["route123Outbound1"],
                // Some other routes that don't interfere.
                "route123Outbound1" to targetVehicleJourneys["route123Outbound1"],
                "route234Outbound1" to targetVehicleJourneys["route234Outbound1"]
            )

            stagingBlock["_vehicle_journeys"] = stagingVehicleJourneys
            targetBlock["_vehicle_journeys"] = targetVehicleJourneys
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            // FIXME: this should fail.
            // Also, check correct exception class.
            // Might need more robust tests than just this one case (at least for valid corner cases).
            val result = combineTimetablesService.combineSingleTimetable(
                UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494"),
                TimetablesPriority.STANDARD
            )
            assertEquals(UUID.fromString("bb2abc90-8e91-4b0b-a7e4-751a04e81ba3"), result)
        }
    }
}
