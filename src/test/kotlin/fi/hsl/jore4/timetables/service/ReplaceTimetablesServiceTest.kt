package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.TimetablesDataset
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.extensions.deepClone
import fi.hsl.jore4.timetables.extensions.getNested
import fi.hsl.jore4.timetables.repository.VehicleScheduleFrameRepository
import mu.KotlinLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.UUID
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

private val LOGGER = KotlinLogging.logger {}

@SpringBootTest
class ReplaceTimetablesServiceTest @Autowired constructor(
    val replaceTimetablesService: ReplaceTimetablesService,
    var timetablesDataInserterRunner: TimetablesDataInserterRunner,
    val vehicleScheduleFrameRepository: VehicleScheduleFrameRepository
) {
    private fun fetchSingleFrameById(frameId: UUID): VehicleScheduleFrame {
        return vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(frameId)
            ?: throw Exception("Vehicle schedule frame $frameId not found")
    }

    @Nested
    @DisplayName("replaceTimetables")
    inner class replaceTimetables {
        @Nested
        @DisplayName("when successfully replacing a single vehicle schedule frame")
        inner class SuccessfullyReplacingSingleVehicleScheduleFrame {
            private val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")
            private val replacedFrameId = UUID.fromString("35e94feb-21d0-4418-9c9a-58345066af78")

            private lateinit var result: List<UUID>
            private lateinit var initialReplacedFrame: VehicleScheduleFrame
            private lateinit var initialStagingFrame: VehicleScheduleFrame

            @BeforeEach
            fun runSingleFrameReplacement() {
                val testData = TimetablesDataset.createFromResource("datasets/replace.json")
                timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

                initialReplacedFrame = fetchSingleFrameById(replacedFrameId)
                initialStagingFrame = fetchSingleFrameById(stagingFrameId)

                result = replaceTimetablesService.replaceTimetables(
                    listOf(stagingFrameId),
                    TimetablesPriority.STANDARD
                )
            }

            @Test
            fun `returns ids of replaced VehicleScheduleFrames`() {
                assertEquals(listOf(replacedFrameId), result)
            }

            @Test
            fun `expires the replaced vehicle schedule frame`() {
                val replacedFrame = fetchSingleFrameById(replacedFrameId)
                assertNotNull(replacedFrame)

                val expectedReplacedFrame = initialReplacedFrame
                // Ends exactly one day before the new one starts.
                assertEquals(LocalDate.of(2023, 1, 1), initialStagingFrame.validityStart)
                expectedReplacedFrame.validityEnd = LocalDate.of(2022, 12, 31)
                assertEquals(expectedReplacedFrame, replacedFrame)
            }

            @Test
            fun `promotes staging timetable to target priority`() {
                val stagingFrame = fetchSingleFrameById(stagingFrameId)
                assertNotNull(stagingFrame)

                val expectedStagingFrame = initialStagingFrame
                expectedStagingFrame.priority = TimetablesPriority.STANDARD.value

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

                val result = replaceTimetablesService.replaceTimetables(
                    listOf(stagingFrameId),
                    TimetablesPriority.STANDARD
                )

                assertEquals(listOf(replaced1FrameId, replaced2FrameId), result)

                val replacedFrame1 = fetchSingleFrameById(replaced1FrameId)
                val replacedFrame2 = fetchSingleFrameById(replaced2FrameId)
                assertNotNull(replacedFrame1)
                assertNotNull(replacedFrame2)

                assertEquals(LocalDate.of(2022, 11, 30), replacedFrame1.validityEnd)
                assertEquals(LocalDate.of(2022, 11, 30), replacedFrame2.validityEnd)
            }
        }

        @Test
        fun `fails when called with invalid target priority = staging`() {
            val testData = TimetablesDataset.createFromResource("datasets/replace.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")
            val initialStagingFrame = fetchSingleFrameById(stagingFrameId)

            val exception = assertFailsWith<InvalidTargetPriorityException> {
                replaceTimetablesService.replaceTimetables(
                    listOf(stagingFrameId),
                    TimetablesPriority.STAGING
                )
            }

            assertContains(exception.message.toString(), "Can not set priority STAGING as target.")
            assertEquals(initialStagingFrame, fetchSingleFrameById(stagingFrameId))
        }

        @Test
        fun `fails when staging timetable does not exist`() {
            val testData = TimetablesDataset.createFromResource("datasets/replace.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val nonexistentStagingFrameId = UUID.fromString("DEADBEEF-FEED-C0DE-F00D-ABBA1234ABBA")

            assertFailsWith<StagingVehicleScheduleFrameNotFoundException> {
                replaceTimetablesService.replaceTimetables(
                    listOf(nonexistentStagingFrameId),
                    TimetablesPriority.STANDARD
                )
            }
        }

        @Test
        fun `fails when staging timetable is not staging`() {
            val testData = TimetablesDataset.createFromResource("datasets/replace.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val nonStagingFrameId = UUID.fromString(
                testData.getNested("_vehicle_schedule_frames.current")["vehicle_schedule_frame_id"].toString()
            )
            val initialNonStagingFrame = fetchSingleFrameById(nonStagingFrameId)

            assertFailsWith<StagingVehicleScheduleFrameNotFoundException> {
                replaceTimetablesService.replaceTimetables(
                    listOf(nonStagingFrameId),
                    TimetablesPriority.STANDARD
                )
            }
            assertEquals(initialNonStagingFrame, fetchSingleFrameById(nonStagingFrameId))
        }

        @Test
        fun `returns an empty list when there are no vehicle schedule frames to replace`() {
            val testData = TimetablesDataset.createFromResource("datasets/replace.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")

            val result = replaceTimetablesService.replaceTimetables(
                listOf(stagingFrameId),
                TimetablesPriority.TEMPORARY // There is an otherwise valid frame to replace, but only at Standard priority
            )

            assertEquals(listOf(), result)
            assertEquals(TimetablesPriority.TEMPORARY.value, fetchSingleFrameById(stagingFrameId).priority)
        }

        @Nested
        @DisplayName("with multiple staging frames")
        inner class WithMultipleStagingFrames {
            private val stagingFrameIds = listOf(
                UUID.fromString("578895c3-ad0d-45a1-a4e5-3b01046369e5"),
                UUID.fromString("ec474a2c-3e1e-4141-a8f6-aa09301f2ac9"),
                UUID.fromString("9d22447c-3e81-4f4c-9356-a9b0f712a3cb")
            )
            private val replacedFrameIds = listOf(
                UUID.fromString("ea9c8c74-701c-472e-bcc0-caf55ec75972"),
                UUID.fromString("a0830628-4dde-488f-91da-ac4eb966b37a"),
                UUID.fromString("d8023c8c-fdca-4686-be85-89e5bfb00591")
            )

            private lateinit var testData: TimetablesDataset

            @BeforeEach
            fun setupDataset() {
                val baseDataset = TimetablesDataset.createFromResource("datasets/replace.json")
                val baseStaging = baseDataset.getNested("_vehicle_schedule_frames.staging")
                val baseCurrent = baseDataset.getNested("_vehicle_schedule_frames.current")

                // Create new test data set with multiple staging-current pairs,
                // using the default dataset as base.
                testData = TimetablesDataset.createFromMutableMap(
                    mutableMapOf(
                        "_vehicle_schedule_frames" to mutableMapOf(
                            "current1" to baseCurrent.deepClone().setFrameProperties(
                                mutableMapOf(
                                    "vehicle_schedule_frame_id" to replacedFrameIds[0],
                                    "name" to "2023 talvi",
                                    "validity_start" to "2023-01-01",
                                    "validity_end" to "2023-02-28"
                                )
                            ),
                            "staging1" to baseStaging.toMutableMap().setFrameProperties(
                                mutableMapOf(
                                    "vehicle_schedule_frame_id" to stagingFrameIds[0],
                                    "name" to "2023 talvi luonnos",
                                    "validity_start" to "2023-02-01",
                                    "validity_end" to "2023-03-31"
                                )
                            ),
                            "current2" to baseCurrent.deepClone().setFrameProperties(
                                mutableMapOf(
                                    "vehicle_schedule_frame_id" to replacedFrameIds[1],
                                    "name" to "2023 kesä",
                                    "validity_start" to "2023-04-01",
                                    "validity_end" to "2023-05-31"
                                )
                            ),
                            "staging2" to baseStaging.deepClone().setFrameProperties(
                                mutableMapOf(
                                    "vehicle_schedule_frame_id" to stagingFrameIds[1],
                                    "name" to "2023 kesä luonnos",
                                    "validity_start" to "2023-05-01",
                                    "validity_end" to "2023-06-30"
                                )
                            ),
                            "current3" to baseCurrent.deepClone().setFrameProperties(
                                mutableMapOf(
                                    "vehicle_schedule_frame_id" to replacedFrameIds[2],
                                    "name" to "2023 syksy",
                                    "validity_start" to "2023-09-01",
                                    "validity_end" to "2023-10-31"
                                )
                            ),
                            "staging3" to baseStaging.deepClone().setFrameProperties(
                                mutableMapOf(
                                    "vehicle_schedule_frame_id" to stagingFrameIds[2],
                                    "name" to "2023 syksy luonnos",
                                    "validity_start" to "2023-10-01",
                                    "validity_end" to "2023-11-30"
                                )
                            )
                        ),
                        "_journey_pattern_refs" to baseDataset["_journey_pattern_refs"]
                    )
                )
            }

            private fun MutableMap<String, Any?>.setFrameProperties(
                patch: MutableMap<String, Any?>
            ): MutableMap<String, Any?> {
                this.putAll(patch)
                return this
            }

            @Test
            fun `replaces each staging timetables and returns replaced vehicle schedule frame ids`() {
                timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

                val result = replaceTimetablesService.replaceTimetables(
                    stagingFrameIds,
                    TimetablesPriority.STANDARD
                )

                assertEquals(result, replacedFrameIds)
                // Each staging frame got processed: promoted to target priority.
                assertEquals(TimetablesPriority.STANDARD.value, fetchSingleFrameById(stagingFrameIds[0]).priority)
                assertEquals(TimetablesPriority.STANDARD.value, fetchSingleFrameById(stagingFrameIds[1]).priority)
                assertEquals(TimetablesPriority.STANDARD.value, fetchSingleFrameById(stagingFrameIds[2]).priority)
            }

            @Test
            fun `fails if any of the requested staging frames do not get handled successfully`() {
                timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

                val initialFrames = vehicleScheduleFrameRepository.findAll().sortedBy { it.label }
                val nonexistentStagingFrameId = UUID.fromString("DEADBEEF-FEED-C0DE-F00D-ABBA1234ABBA")

                val exception = assertFailsWith<StagingVehicleScheduleFrameNotFoundException> {
                    replaceTimetablesService.replaceTimetables(
                        listOf(
                            stagingFrameIds[0],
                            nonexistentStagingFrameId, // This will cause the whole operation to fail.
                            stagingFrameIds[2]
                        ),
                        TimetablesPriority.STANDARD
                    )
                }

                assertEquals(nonexistentStagingFrameId, exception.stagingVehicleScheduleFrameId)
                // Whole transaction rolled back, no changes persisted.
                val framesAfter = vehicleScheduleFrameRepository.findAll().sortedBy { it.label }
                assertEquals(initialFrames, framesAfter)
            }
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
            ).map {
                it.vehicleScheduleFrameId!! // ID of an existing row, can never be null.
            }
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
                    val stagingFrameJourneys =
                        mutableMapOf("route123Outbound" to partialVehicleJourneys["route123Outbound1"])
                    val replacedFrameJourneys =
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
                    val stagingFrameJourneys = mutableMapOf(
                        "route123Outbound" to partialVehicleJourneys["route123Outbound1"],
                        "route123Inbound" to partialVehicleJourneys["route123Inbound1"],
                        "route234Outbound" to partialVehicleJourneys["route234Outbound1"]
                    )
                    val replacedFrameJourneys = mutableMapOf(
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
                    val stagingFrameJourneys = mutableMapOf(
                        "route123Outbound" to partialVehicleJourneys["route123Outbound1"],
                        "route123Inbound" to partialVehicleJourneys["route123Inbound1"],
                        "route234Outbound" to partialVehicleJourneys["route234Outbound1"]
                    )
                    val replacedFrameJourneys = mutableMapOf(
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
                    val stagingFrameJourneys = mutableMapOf(
                        "route234Outbound" to partialVehicleJourneys["route234Outbound1"]
                    )
                    val replacedFrameJourneys = mutableMapOf(
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
                    val stagingBlocks =
                        testData.getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri._blocks")

                    return mutableMapOf(
                        "day_type_id" to dayTypeId,
                        "_blocks" to stagingBlocks.toMutableMap()
                    )
                }

                private fun getReplacedRoute123Service(dayTypeId: String): MutableMap<String, Any?> {
                    val replacedBlocks =
                        testData.getNested("_vehicle_schedule_frames.current._vehicle_services.monFri._blocks")

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
                    val replacedFrameService =
                        testData.getNested("_vehicle_schedule_frames.current._vehicle_services.monFri")

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

                // TODO: The following tests are corner cases. Not 100% clear if this is how we want them to work.

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
            val stagingFrameJourneys = mutableMapOf(
                "route123Outbound" to partialVehicleJourneys["route123Outbound1"],
                "route123Inbound" to partialVehicleJourneys["route123Inbound1"]
            )
            val replacedFrameJourneys = mutableMapOf(
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
}
