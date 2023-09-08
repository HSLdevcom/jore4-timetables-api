package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.records.VehicleScheduleFrameRecord
import fi.hsl.jore4.timetables.TimetablesDataset
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import mu.KotlinLogging
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.util.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val LOGGER = KotlinLogging.logger {}

@SpringBootTest
@ActiveProfiles("integration-test")
class ReplaceTimetablesServiceTest @Autowired constructor(
    val dsl: DSLContext,
    val replaceTimetablesService: ReplaceTimetablesService,
    var timetablesDataInserterRunner: TimetablesDataInserterRunner
) {
    private fun selectFrameById(frameId: UUID): VehicleScheduleFrameRecord {
        return dsl
            .selectFrom(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .where(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(frameId)
            )
            .fetchOneInto(VehicleScheduleFrameRecord)
            ?: throw Exception("Vehicle schedule frame $frameId not found")
    }

    @Nested
    @DisplayName("processSingleStagingFrameReplacements")
    inner class ProcessSingleStagingFrameReplacements {
        @Nested
        @DisplayName("when successfully replacing a single vehicle schedule frame")
        inner class SuccessfullyReplacingSingleVehicleScheduleFrame {
            private val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")
            private val replacedFrameId = UUID.fromString("35e94feb-21d0-4418-9c9a-58345066af78")

            private lateinit var result: List<UUID>
            private lateinit var initialReplacedFrame: VehicleScheduleFrameRecord
            private lateinit var initialStagingFrame: VehicleScheduleFrameRecord

            @BeforeEach
            fun runSingleFrameReplacement() {
                val testData = TimetablesDataset.createFromResource("datasets/replace.json")
                timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

                initialReplacedFrame = selectFrameById(replacedFrameId)
                initialStagingFrame = selectFrameById(stagingFrameId)

                result = replaceTimetablesService.processSingleStagingFrameReplacements(
                    stagingFrameId,
                    TimetablesPriority.STANDARD
                )
            }

            @Test
            fun `returns ids of replaced VehicleScheduleFrames`() {
                assertEquals(listOf(replacedFrameId), result)
            }

            @Test
            fun `expires the replaced vehicle schedule frame`() {
                val replacedFrame = selectFrameById(replacedFrameId)
                assertNotNull(replacedFrame)

                val expectedReplacedFrame = initialReplacedFrame
                // Ends exactly one day before the new one starts.
                assertEquals(initialStagingFrame.validityStart, LocalDate.parse("2023-01-01"))
                expectedReplacedFrame.validityEnd = LocalDate.parse("2022-12-31")
                expectedReplacedFrame.validityRange = replacedFrame.validityRange // Generated column, unimportant.

                assertEquals(expectedReplacedFrame, replacedFrame)
            }

            @Test
            fun `promotes staging timetable to target priority`() {
                val stagingFrame = selectFrameById(stagingFrameId)
                assertNotNull(stagingFrame)

                val expectedStagingFrame = initialStagingFrame
                expectedStagingFrame.set(DSL.field("priority"), TimetablesPriority.STANDARD.value)

                assertEquals(expectedStagingFrame, stagingFrame)
            }
        }

        @Nested
        @DisplayName("with multiple replaced frames")
        inner class WithMultipleReplacedFrames {
            private val stagingFrameId = UUID.fromString("9E758776-2AF1-49C7-8BD0-C0805B833B20")
            private val replaced1FrameId = UUID.fromString("B62B71C4-D1B4-4A9A-8490-D95CFE304B5D")
            private val replaced2FrameId = UUID.fromString("C85863A3-7ACC-4478-96C6-928F2CD744B3")

            private lateinit var testData: TimetablesDataset

            @BeforeEach
            fun runSingleFrameReplacement() {
                // TODO: remove separate mostly copypasted dataset json and instead create this by modifying the replace.json set.
                testData = TimetablesDataset.createFromResource("datasets/replace_multiple_replaced.json")
            }

            @Test
            fun `expires each replaced frame when successful`() {
                timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

                var result = replaceTimetablesService.processSingleStagingFrameReplacements(
                    stagingFrameId,
                    TimetablesPriority.STANDARD
                )

                assertEquals(listOf(replaced1FrameId, replaced2FrameId), result)

                val replacedFrame1 = selectFrameById(replaced1FrameId)
                val replacedFrame2 = selectFrameById(replaced2FrameId)
                assertNotNull(replacedFrame1)
                assertNotNull(replacedFrame2)

                assertEquals(replacedFrame1.validityEnd, LocalDate.parse("2022-11-30"))
                assertEquals(replacedFrame2.validityEnd, LocalDate.parse("2022-11-30"))
            }
        }

        @Test
        fun `fails when called with invalid target priority = staging`() {
            val testData = TimetablesDataset.createFromResource("datasets/replace.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")

            val exception = assertThrows(IllegalArgumentException::class.java) {
                replaceTimetablesService.processSingleStagingFrameReplacements(
                    stagingFrameId,
                    TimetablesPriority.STAGING
                )
            }

            assertContains(exception.message.toString(), "Can not set priority STAGING as target.")
        }

        @Test
        fun `fails when staging timetable does not exist`() {
            val testData = TimetablesDataset.createFromResource("datasets/replace.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val nonexistentStagingFrameId = UUID.fromString("DEADBEEF-FEED-C0DE-F00D-ABBA1234ABBA")

            val exception = assertThrows(IllegalStateException::class.java) {
                replaceTimetablesService.processSingleStagingFrameReplacements(
                    nonexistentStagingFrameId,
                    TimetablesPriority.STANDARD
                )
            }

            assertContains(exception.message.toString(), "Staging vehicle schedule frame not found")
        }

        @Test
        fun `fails when staging timetable is not staging`() {
            val testData = TimetablesDataset.createFromResource("datasets/replace.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val nonStagingFrameId = UUID.fromString(
                testData.getNested("_vehicle_schedule_frames.current")["vehicle_schedule_frame_id"].toString()
            )

            val exception = assertThrows(IllegalStateException::class.java) {
                replaceTimetablesService.processSingleStagingFrameReplacements(
                    nonStagingFrameId,
                    TimetablesPriority.STANDARD
                )
            }

            assertContains(exception.message.toString(), "Staging vehicle schedule frame not found")
        }

        @Test
        fun `returns an empty list when there are no vehicle schedule frames to replace`() {
            val testData = TimetablesDataset.createFromResource("datasets/replace.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")

            val result = replaceTimetablesService.processSingleStagingFrameReplacements(
                stagingFrameId,
                TimetablesPriority.TEMPORARY // There is an otherwise valid frame to replace, but only at Standard priority
            )

            assertEquals(listOf(), result)
        }
    }

    @Nested
    @DisplayName("fetchVehicleScheduleFramesToReplace")
    inner class FetchVehicleScheduleFramesToReplace {
        private val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")
        private val replacedFrameId = UUID.fromString("35e94feb-21d0-4418-9c9a-58345066af78")

        private lateinit var testData: TimetablesDataset
        private lateinit var partialVehicleJourneys: MutableMap<String, Any?>

        private fun setTestDataJourneys(
            stagingFrameJourneys: MutableMap<String, Any?>,
            replacedFrameJourneys: MutableMap<String, Any?>
        ) {
            testData.getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri._blocks.block")["_vehicle_journeys"] =
                stagingFrameJourneys
            testData.getNested("_vehicle_schedule_frames.current._vehicle_services.monFri._blocks.block")["_vehicle_journeys"] =
                replacedFrameJourneys
        }

        private fun insertDatasetAndFetchFrameIdsToReplace(targetPriority: TimetablesPriority = TimetablesPriority.STANDARD): List<UUID> {
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            return replaceTimetablesService.fetchVehicleScheduleFramesToReplace(
                stagingFrameId,
                targetPriority
            ).map { it.vehicleScheduleFrameId!! }
        }

        @BeforeEach
        fun loadTestDataFiles() {
            testData = TimetablesDataset.createFromResource("datasets/replace.json")
            partialVehicleJourneys = TimetablesDataset.createFromResource("datasets/partial_vehicle_journeys.json")
        }

        @Nested
        @DisplayName("finds a frame to replace")
        inner class FindsFrameToReplace {
            @Nested
            @DisplayName("with shared routes")
            inner class WithSharedRoutes {
                @Test
                fun `when staging and replaced both have the same single route`() {
                    var stagingFrameJourneys =
                        mutableMapOf("route123Outbound" to partialVehicleJourneys["route123Outbound1"])
                    var replacedFrameJourneys =
                        mutableMapOf("route123Outbound" to partialVehicleJourneys["route123Outbound2"])
                    setTestDataJourneys(
                        stagingFrameJourneys = stagingFrameJourneys,
                        replacedFrameJourneys = replacedFrameJourneys
                    )

                    val result = insertDatasetAndFetchFrameIdsToReplace()
                    assertEquals(listOf(replacedFrameId), result)
                }

                @Test
                fun `when staging and replaced frame have all routes in common`() {
                    var stagingFrameJourneys = mutableMapOf(
                        "route123Outbound" to partialVehicleJourneys["route123Outbound1"],
                        "route123Inbound" to partialVehicleJourneys["route123Inbound1"],
                        "route234Outbound" to partialVehicleJourneys["route234Outbound1"]
                    )
                    var replacedFrameJourneys = mutableMapOf(
                        "route123Outbound" to partialVehicleJourneys["route123Outbound2"],
                        "route123Inbound" to partialVehicleJourneys["route123Inbound2"],
                        "route234Outbound" to partialVehicleJourneys["route234Outbound2"]
                    )
                    setTestDataJourneys(
                        stagingFrameJourneys = stagingFrameJourneys,
                        replacedFrameJourneys = replacedFrameJourneys
                    )

                    val result = insertDatasetAndFetchFrameIdsToReplace()
                    assertEquals(listOf(replacedFrameId), result)
                }

                @Test
                fun `when staging and replaced frame have some routes in common, but staging has additional ones`() {
                    var stagingFrameJourneys = mutableMapOf(
                        "route123Outbound" to partialVehicleJourneys["route123Outbound1"],
                        "route123Inbound" to partialVehicleJourneys["route123Inbound1"],
                        "route234Outbound" to partialVehicleJourneys["route234Outbound1"]
                    )
                    var replacedFrameJourneys = mutableMapOf(
                        "route123Outbound" to partialVehicleJourneys["route123Outbound2"],
                        "route123Inbound" to partialVehicleJourneys["route123Inbound2"]
                    )
                    setTestDataJourneys(
                        stagingFrameJourneys = stagingFrameJourneys,
                        replacedFrameJourneys = replacedFrameJourneys
                    )

                    val result = insertDatasetAndFetchFrameIdsToReplace()
                    assertEquals(listOf(replacedFrameId), result)
                }

                @Test
                fun `when staging and replaced frame have some routes in common, but replaced has additional ones`() {
                    var stagingFrameJourneys = mutableMapOf(
                        "route234Outbound" to partialVehicleJourneys["route234Outbound1"]
                    )
                    var replacedFrameJourneys = mutableMapOf(
                        "route123Outbound" to partialVehicleJourneys["route123Outbound2"],
                        "route123Inbound" to partialVehicleJourneys["route123Inbound2"],
                        "route234Outbound" to partialVehicleJourneys["route234Outbound2"]
                    )
                    setTestDataJourneys(
                        stagingFrameJourneys = stagingFrameJourneys,
                        replacedFrameJourneys = replacedFrameJourneys
                    )

                    val result = insertDatasetAndFetchFrameIdsToReplace()
                    assertEquals(listOf(replacedFrameId), result)
                }
            }

            @Nested
            @DisplayName("with same day types")
            inner class WithSameDayTypes {
                private fun getStagingRoute123Service(dayTypeId: String): MutableMap<String, Any?> {
                    val stagingBlocks = testData.getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri._blocks")

                    return mutableMapOf(
                        "day_type_id" to dayTypeId,
                        "_blocks" to stagingBlocks.toMutableMap()
                    )
                }

                private fun getReplacedRoute123Service(dayTypeId: String): MutableMap<String, Any?> {
                    val replacedBlocks = testData.getNested("_vehicle_schedule_frames.current._vehicle_services.monFri._blocks")

                    return mutableMapOf(
                        "day_type_id" to dayTypeId,
                        "_blocks" to replacedBlocks.toMutableMap()
                    )
                }

                private fun setDatasetServices(
                    stagingServices: MutableMap<String, Any?>,
                    replacedServices: MutableMap<String, Any?>
                ) {
                    val stagingFrame = testData.getNested("_vehicle_schedule_frames.staging")
                    val replacedFrameFrame = testData.getNested("_vehicle_schedule_frames.current")
                    stagingFrame["_vehicle_services"] = stagingServices
                    replacedFrameFrame["_vehicle_services"] = replacedServices
                }

                @Test
                fun `returns a frame with different day type but overlapping week days`() {
                    val stagingService = testData.getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri")
                    val replacedFrameService = testData.getNested("_vehicle_schedule_frames.current._vehicle_services.monFri")

                    // Needs to work like this:
                    // if in this case WEDNESDAY didn't get replaced (= expired),
                    // then after staging is promoted there would be overlapping schedules on that day
                    // which is against our DB constraints.
                    stagingService["day_type_id"] = "MONDAY_FRIDAY"
                    replacedFrameService["day_type_id"] = "WEDNESDAY"

                    val result = insertDatasetAndFetchFrameIdsToReplace()

                    assertEquals(listOf(replacedFrameId), result)
                }

                @Test
                fun `when both have multiple day types that are exactly the same`() {
                    setDatasetServices(
                        stagingServices = mutableMapOf(
                            "monFri" to getStagingRoute123Service("MONDAY_FRIDAY")
                        ),
                        replacedServices = mutableMapOf(
                            "monFri" to getReplacedRoute123Service("MONDAY_FRIDAY")
                        )
                    )

                    val result = insertDatasetAndFetchFrameIdsToReplace()

                    assertEquals(listOf(replacedFrameId), result)
                }

                @Test
                fun `when day types overlap partially (both have extra day types)`() {
                    setDatasetServices(
                        stagingServices = mutableMapOf(
                            "fri" to getStagingRoute123Service("FRIDAY"),
                            "sat" to getStagingRoute123Service("SATURDAY")
                        ),
                        replacedServices = mutableMapOf(
                            "sat" to getReplacedRoute123Service("SATURDAY"),
                            "sun" to getReplacedRoute123Service("SUNDAY")
                        )
                    )

                    val result = insertDatasetAndFetchFrameIdsToReplace()

                    assertEquals(listOf(replacedFrameId), result)
                }

                // TODO: Corner cases here. Not 100% clear if this is how we want them to work.

                @Test
                fun `when day types overlap partially by day of week, no identical day types but some days of week are shared`() {
                    setDatasetServices(
                        stagingServices = mutableMapOf(
                            "fri" to getStagingRoute123Service("MONDAY_FRIDAY"),
                            "sat" to getStagingRoute123Service("SATURDAY")
                        ),
                        replacedServices = mutableMapOf(
                            "sat" to getReplacedRoute123Service("MONDAY_THURSDAY"),
                            "sun" to getReplacedRoute123Service("SUNDAY")
                        )
                    )

                    val result = insertDatasetAndFetchFrameIdsToReplace()

                    assertEquals(listOf(replacedFrameId), result)
                }

                @Test
                fun `when different day types are used but resulting days of week are identical`() {
                    setDatasetServices(
                        stagingServices = mutableMapOf(
                            "monFri" to getStagingRoute123Service("MONDAY_FRIDAY")
                        ),
                        replacedServices = mutableMapOf(
                            "monThu" to getReplacedRoute123Service("MONDAY_THURSDAY"),
                            "fri" to getReplacedRoute123Service("FRIDAY")
                        )
                    )

                    val result = insertDatasetAndFetchFrameIdsToReplace()

                    assertEquals(listOf(replacedFrameId), result)
                }
            }
        }

        @Test
        fun `does not return a frame with different requested priority`() {
            val result = insertDatasetAndFetchFrameIdsToReplace(TimetablesPriority.TEMPORARY)

            assertEquals(listOf(), result)
        }

        @Nested
        @DisplayName("does not return frames with incompatible validities")
        inner class IncompatibleValidities {
            private fun testWithValidities(
                stagingStart: String,
                stagingEnd: String,
                replacedStart: String,
                replacedEnd: String
            ): List<UUID> {
                val stagingFrame = testData.getNested("_vehicle_schedule_frames.staging")
                val replacedFrame = testData.getNested("_vehicle_schedule_frames.current")

                stagingFrame["validity_start"] = stagingStart
                stagingFrame["validity_end"] = stagingEnd
                replacedFrame["validity_start"] = replacedStart
                replacedFrame["validity_end"] = replacedEnd

                return insertDatasetAndFetchFrameIdsToReplace()
            }

            @Test
            fun `when there is no overlap between replaced and staging, staging is after replaced`() {
                val result = testWithValidities(
                    stagingStart = "2022-07-01",
                    stagingEnd = "2022-12-31",
                    replacedStart = "2023-01-01",
                    replacedEnd = "2023-05-31"
                )

                assertEquals(listOf(), result)
            }

            @Test
            fun `when there is no overlap between replaced and staging, replaced is after staging`() {
                val result = testWithValidities(
                    stagingStart = "2023-01-01",
                    stagingEnd = "2023-05-31",
                    replacedStart = "2023-07-01",
                    replacedEnd = "2023-09-30"
                )

                assertEquals(listOf(), result)
            }

            @Test
            fun `when staging start is before replaced frame start`() {
                val result = testWithValidities(
                    stagingStart = "2023-01-01",
                    stagingEnd = "2023-05-31",
                    replacedStart = "2023-02-02",
                    replacedEnd = "2023-09-30"
                )

                assertEquals(listOf(), result)
            }

            @Test
            fun `when staging starts when replaced frame starts`() {
                val result = testWithValidities(
                    stagingStart = "2023-01-01",
                    stagingEnd = "2023-05-31",
                    replacedStart = "2023-01-01",
                    replacedEnd = "2023-09-30"
                )

                assertEquals(listOf(), result)
            }

            @Test
            fun `when replaced frame starts on same day as staging ends`() {
                val result = testWithValidities(
                    stagingStart = "2023-01-01",
                    stagingEnd = "2023-05-31",
                    replacedStart = "2023-05-31",
                    replacedEnd = "2023-09-30"
                )

                assertEquals(listOf(), result)
            }
        }

        @Test
        fun `does not return a frame with different day type`() {
            val stagingService = testData.getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri")
            val replacedFrameService = testData.getNested("_vehicle_schedule_frames.current._vehicle_services.monFri")

            stagingService["day_type_id"] = "MONDAY_FRIDAY"
            replacedFrameService["day_type_id"] = "SATURDAY"

            val result = insertDatasetAndFetchFrameIdsToReplace()

            assertEquals(listOf(), result)
        }

        @Test
        fun `does not return a frame with no shared routes`() {
            var stagingFrameJourneys = mutableMapOf(
                "route123Outbound" to partialVehicleJourneys["route123Outbound1"],
                "route123Inbound" to partialVehicleJourneys["route123Inbound1"]
            )
            var replacedFrameJourneys = mutableMapOf(
                "route234Outbound" to partialVehicleJourneys["route234Outbound2"],
                "route234Inbound" to partialVehicleJourneys["route234Inbound2"]
            )
            setTestDataJourneys(
                stagingFrameJourneys = stagingFrameJourneys,
                replacedFrameJourneys = replacedFrameJourneys
            )

            val result = insertDatasetAndFetchFrameIdsToReplace()

            assertEquals(listOf(), result)
        }
    }

    // Most of the logic is implemented in replaceSingleTimetable and thus tested under its tests.
    @Nested
    @DisplayName("replaceTimetables")
    class ReplaceTimetables {
        /*
            TODO:
            replace multiple staging timetables
            fail when trying to call with same timetable multiple times
            fail all if any of the staging timetables fail
         */
    }
}
