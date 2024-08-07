package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.pojos.VehicleScheduleFrame
import fi.hsl.jore4.timetables.IntTest
import fi.hsl.jore4.timetables.TimetablesDataset
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import fi.hsl.jore4.timetables.extensions.deepClone
import fi.hsl.jore4.timetables.extensions.getNested
import fi.hsl.jore4.timetables.repository.VehicleScheduleFrameRepository
import fi.hsl.jore4.timetables.repository.VehicleServiceRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.TransactionSystemException
import java.util.UUID
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

private val LOGGER = KotlinLogging.logger {}

@IntTest
class CombineTimetablesServiceTest
    @Autowired
    constructor(
        val combineTimetablesService: CombineTimetablesService,
        var timetablesDataInserterRunner: TimetablesDataInserterRunner,
        val vehicleServiceRepository: VehicleServiceRepository,
        val vehicleScheduleFrameRepository: VehicleScheduleFrameRepository
    ) {
        private fun fetchSingleFrameById(frameId: UUID): VehicleScheduleFrame =
            vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(frameId)
                ?: throw Exception("Vehicle schedule frame $frameId not found")

        @Nested
        @DisplayName("combineTimetables with single staging frame")
        inner class CombineTimetablesWithSingleStagingFrame {
            @Nested
            @DisplayName("when successfully combining a single new vehicle journey")
            inner class WhenSuccessfullyCombiningSingleNewVehicleJourney {
                private val stagingFrameId = UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494")
                private val targetFrameId = UUID.fromString("bb2abc90-8e91-4b0b-a7e4-751a04e81ba3")

                private lateinit var result: List<UUID>
                private lateinit var initialTargetFrame: VehicleScheduleFrame
                private lateinit var initialStagingFrame: VehicleScheduleFrame

                @BeforeEach
                fun runCombineSingleTimetable() {
                    val testData = TimetablesDataset.createFromResource("datasets/combine.json")
                    timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())

                    initialTargetFrame = fetchSingleFrameById(targetFrameId)
                    initialStagingFrame = fetchSingleFrameById(stagingFrameId)

                    result =
                        combineTimetablesService.combineTimetables(
                            listOf(stagingFrameId),
                            TimetablesPriority.STANDARD
                        )
                }

                @Test
                fun `returns id of the target vehicle schedule frame`() {
                    assertEquals(listOf(targetFrameId), result)
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

            // Just happens to work like this at the moment, not clear if it should.
            // Probably needs to be changed if/when we ensure that different vehicle services don't overlap.
            // Also to consider, would this need more tests for cases when there are multiple day types? Are they even realistic cases, even if possible?
            @Test
            fun `combines the timetable when staging and target have different day types but are active on same day of week`() {
                val testData = TimetablesDataset.createFromResource("datasets/combine.json")
                testData.getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri")["day_type_id"] =
                    "MONDAY_FRIDAY"
                testData.getNested("_vehicle_schedule_frames.target._vehicle_services.monFri")["day_type_id"] =
                    "WEDNESDAY"

                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())
                val stagingFrameId = UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494")

                val result =
                    combineTimetablesService.combineTimetables(
                        listOf(stagingFrameId),
                        TimetablesPriority.STANDARD
                    )
                assertEquals(listOf(UUID.fromString("bb2abc90-8e91-4b0b-a7e4-751a04e81ba3")), result)
                assertNull(vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(stagingFrameId))
            }

            @Test
            @Suppress("ktlint:standard:max-line-length")
            fun `combines the timetable when in addition to actual target there also exists other frames with identical validity but different days of week`() {
                val testData = TimetablesDataset.createFromResource("datasets/combine.json")
                testData.getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri")["day_type_id"] =
                    "MONDAY_FRIDAY"
                testData.getNested("_vehicle_schedule_frames.target._vehicle_services.monFri")["day_type_id"] =
                    "WEDNESDAY"

                // Add another frame with identical validity range (should match combined) but different days of week (-> should not match).
                val saturdayFrameId = UUID.fromString("ccc9f432-f1f1-4baa-935b-39590a8d9380")
                val saturdayTarget =
                    testData
                        .getNested("_vehicle_schedule_frames.target")
                        .deepClone()
                        .setFrameProperties(
                            mutableMapOf(
                                "vehicle_schedule_frame_id" to saturdayFrameId,
                                "name" to "2023 tammikuu lauantai"
                            )
                        )
                saturdayTarget.getNested("_vehicle_services.monFri")["day_type_id"] = "SATURDAY"
                testData.getNested("_vehicle_schedule_frames")["saturdayTarget"] = saturdayTarget

                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())
                val stagingFrameId = UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494")

                val result =
                    combineTimetablesService.combineTimetables(
                        listOf(stagingFrameId),
                        TimetablesPriority.STANDARD
                    )
                assertEquals(listOf(UUID.fromString("bb2abc90-8e91-4b0b-a7e4-751a04e81ba3")), result)
                assertNull(vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(stagingFrameId))
            }

            @Test
            fun `fails when called with invalid target priority = staging`() {
                val testData = TimetablesDataset.createFromResource("datasets/combine.json")
                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())

                val stagingFrameId = UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494")
                val initialStagingFrame = fetchSingleFrameById(stagingFrameId)
                val initialFrames = vehicleScheduleFrameRepository.findAll().toSet()

                val exception =
                    assertFailsWith<InvalidTargetPriorityException> {
                        combineTimetablesService.combineTimetables(
                            listOf(stagingFrameId),
                            TimetablesPriority.STAGING
                        )
                    }

                assertContains(exception.message.toString(), "Can not set priority STAGING as target.")
                assertEquals(initialStagingFrame, fetchSingleFrameById(stagingFrameId))
                assertEquals(initialFrames, vehicleScheduleFrameRepository.findAll().toSet())
            }

            @Test
            fun `fails when staging timetable does not exist`() {
                val testData = TimetablesDataset.createFromResource("datasets/combine.json")
                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())

                val nonexistentStagingFrameId = UUID.fromString("DEADBEEF-FEED-C0DE-F00D-ABBA1234ABBA")
                val initialFrames = vehicleScheduleFrameRepository.findAll().toSet()

                assertFailsWith<StagingVehicleScheduleFrameNotFoundException> {
                    combineTimetablesService.combineTimetables(
                        listOf(nonexistentStagingFrameId),
                        TimetablesPriority.STANDARD
                    )
                }
                assertEquals(initialFrames, vehicleScheduleFrameRepository.findAll().toSet())
            }

            @Test
            fun `fails when staging timetable is not staging`() {
                val testData = TimetablesDataset.createFromResource("datasets/combine.json")
                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())

                val nonStagingFrameId =
                    UUID.fromString(
                        testData.getNested("_vehicle_schedule_frames.target")["vehicle_schedule_frame_id"].toString()
                    )
                val initialNonStagingFrame = fetchSingleFrameById(nonStagingFrameId)
                val initialFrames = vehicleScheduleFrameRepository.findAll().toSet()

                assertFailsWith<StagingVehicleScheduleFrameNotFoundException> {
                    combineTimetablesService.combineTimetables(
                        listOf(nonStagingFrameId),
                        TimetablesPriority.STANDARD
                    )
                }
                assertEquals(initialNonStagingFrame, fetchSingleFrameById(nonStagingFrameId))
                assertEquals(initialFrames, vehicleScheduleFrameRepository.findAll().toSet())
            }

            @Nested
            @DisplayName("fails when target vehicle schedule can not be found")
            inner class FailsWhenTargetVehicleScheduleCanNotBeFound {
                private lateinit var testData: TimetablesDataset

                @BeforeEach
                fun loadTestDataResource() {
                    testData = TimetablesDataset.createFromResource("datasets/combine.json")
                }

                private fun runCombineAndAssertTargetNotFoundException() {
                    timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())

                    val stagingFrameId = UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494")
                    val targetFrameId = UUID.fromString("bb2abc90-8e91-4b0b-a7e4-751a04e81ba3")
                    val initialStagingFrame = fetchSingleFrameById(stagingFrameId)
                    val initialTargetFrame = fetchSingleFrameById(targetFrameId)

                    val exception =
                        assertFailsWith<TargetFrameNotFoundException> {
                            combineTimetablesService.combineTimetables(
                                listOf(stagingFrameId),
                                TimetablesPriority.STANDARD
                            )
                        }
                    assertEquals(stagingFrameId, exception.stagingVehicleScheduleFrameId)
                    assertEquals(initialStagingFrame, fetchSingleFrameById(stagingFrameId))
                    assertEquals(initialTargetFrame, fetchSingleFrameById(targetFrameId))
                }

                // This could probably be extended to more tests if needed, to cover more cases: ie. when routes partially overlap.
                // Most such cases are valid, as long as there's any shared route.
                // This logic is implemented by getOverlappingSchedules function
                // and that is already covered in ReplaceTimetablesService tests.
                @Test
                fun `because it has no shared routes`() {
                    // Drop most vehicle journeys from staging and target, only leaving routes not found from both.
                    val stagingBlock =
                        testData
                            .getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri._blocks.block")
                    val targetBlock =
                        testData
                            .getNested("_vehicle_schedule_frames.target._vehicle_services.monFri._blocks.block")
                    val stagingVehicleJourneys = stagingBlock.getNested("_vehicle_journeys")
                    val targetVehicleJourneys = targetBlock.getNested("_vehicle_journeys")

                    stagingBlock["_vehicle_journeys"] =
                        mutableMapOf(
                            "route133Outbound1" to stagingVehicleJourneys["route133Outbound1"]
                        )
                    targetBlock["_vehicle_journeys"] =
                        mutableMapOf(
                            "route241Outbound1" to targetVehicleJourneys["route241Outbound1"]
                        )

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

                @Test
                fun `because it has different day type and active week of day does not overlap`() {
                    testData.getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri")["day_type_id"] =
                        "MONDAY_FRIDAY"
                    testData.getNested("_vehicle_schedule_frames.target._vehicle_services.monFri")["day_type_id"] =
                        "SATURDAY"

                    runCombineAndAssertTargetNotFoundException()
                }
            }

            @Test
            fun `fails when there are multiple possible target vehicle schedule frames`() {
                val testData = TimetablesDataset.createFromResource("datasets/combine_multiple_targets.json")
                val stagingFrameId = UUID.fromString("e8d07c0d-575f-4cbe-bddb-ead5b2943638")
                val target1FrameId = UUID.fromString("c89e23c0-bbb0-4c0e-aa68-6ec6043079c5")
                val target2FrameId = UUID.fromString("4cb38f55-b2d7-4cf4-bdaa-50db6e7c32e7")

                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())
                val initialStagingFrame = fetchSingleFrameById(stagingFrameId)

                val exception =
                    assertFailsWith<MultipleTargetFramesFoundException> {
                        combineTimetablesService.combineTimetables(
                            listOf(stagingFrameId),
                            TimetablesPriority.STANDARD
                        )
                    }
                assertEquals(exception.stagingVehicleScheduleFrameId, stagingFrameId)
                assertEquals(setOf(target1FrameId, target2FrameId), exception.targetVehicleScheduleFrameIds.toSet())
                assertEquals(initialStagingFrame, fetchSingleFrameById(stagingFrameId))
            }

            @Test
            fun `combines a vehicle journey that already exists in the target frame`() {
                val testData = TimetablesDataset.createFromResource("datasets/combine.json")

                val stagingBlock =
                    testData
                        .getNested("_vehicle_schedule_frames.staging._vehicle_services.monFri._blocks.block")
                val targetBlock =
                    testData
                        .getNested("_vehicle_schedule_frames.target._vehicle_services.monFri._blocks.block")
                val stagingVehicleJourneys = stagingBlock.getNested("_vehicle_journeys")
                val targetVehicleJourneys = targetBlock.getNested("_vehicle_journeys")

                stagingBlock["_vehicle_journeys"] =
                    mutableMapOf(
                        "route133Outbound1" to stagingVehicleJourneys["route133Outbound1"]
                    )
                targetBlock["_vehicle_journeys"] =
                    mutableMapOf(
                        // Identical vehicle journey as the one about to come from staging -> fail.
                        "route133Outbound1" to stagingVehicleJourneys["route133Outbound1"],
                        // Some other routes that don't interfere.
                        "route133Outbound1" to targetVehicleJourneys["route133Outbound1"],
                        "route241Outbound1" to targetVehicleJourneys["route241Outbound1"]
                    )
                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())
                val stagingFrameId = UUID.fromString("aa0e95c6-34d1-4d09-8546-3789b04ea494")

                val result =
                    combineTimetablesService.combineTimetables(
                        listOf(stagingFrameId),
                        TimetablesPriority.STANDARD
                    )
                assertEquals(listOf(UUID.fromString("bb2abc90-8e91-4b0b-a7e4-751a04e81ba3")), result)
                assertNull(vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(stagingFrameId))
            }

            @Test
            fun `fails when combining would result in invalid data`() {
                // There are not too many cases that can produce a commit fail here,
                // since the frames have already been (mostly) validated when created as staging.
                // Setup for one such case here:
                // Change target2 validity times to differ from staging -> not detected as target.
                // But since we have another target ("target" in dataset), we can still combine.
                // However, since the staging frame  overlaps with target2 (timetables active for same routes on same days),
                // the commit will fail on DB constraints.
                // There might be more realistic cases to reproduce similar error with, I think,
                // but this is easiest with current datasets.
                val testData = TimetablesDataset.createFromResource("datasets/combine_multiple_targets.json")
                testData.getNested("_vehicle_schedule_frames.target2")["validity_start"] = "2022-07-15"
                testData.getNested("_vehicle_schedule_frames.target2")["validity_end"] = "2023-05-15"

                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())

                val stagingFrameId = UUID.fromString("e8d07c0d-575f-4cbe-bddb-ead5b2943638")
                assertFailsWith<TransactionSystemException> {
                    combineTimetablesService.combineTimetables(
                        listOf(stagingFrameId),
                        TimetablesPriority.STANDARD
                    )
                }.also { exception ->
                    // Check that the error messages are somewhat in the format we expect. TransactionSystemExtensions depends on these.
                    assertEquals(exception.message, "JDBC commit failed")
                    val causeMessage = exception?.cause?.message
                    assertNotNull(causeMessage)
                    assertContains(causeMessage, "ERROR: conflicting schedules detected: vehicle schedule frame")
                    assertContains(causeMessage, "Where: PL/pgSQL function vehicle_schedule.validate_queued_schedules_uniqueness()")
                    assertContains(causeMessage, "SQL statement \"SELECT vehicle_schedule.validate_queued_schedules_uniqueness()")
                }
            }
        }

        @Nested
        @DisplayName("combineTimetables with multiple staging frames")
        inner class CombineTimetablesWithMultipleStagingFrames {
            private val stagingFrameIds =
                listOf(
                    UUID.fromString("481f8c53-ac32-41c6-8935-4582cfc04528"),
                    UUID.fromString("fe590eda-159d-418f-87ef-6bf61a4a67f5"),
                    UUID.fromString("e5256a94-0d42-4767-ad78-58f2da526ec0")
                )
            private val targetFrameIds =
                listOf(
                    UUID.fromString("29b04636-7127-45c7-bc20-1dfc25972127"),
                    UUID.fromString("ca97530d-744a-42e3-b3cf-7a92eb625dbf"),
                    UUID.fromString("a25bba37-308f-484c-905e-bf66ceaf4bdc")
                )

            private lateinit var testData: TimetablesDataset

            @BeforeEach
            fun setupDataset() {
                val baseDataset = TimetablesDataset.createFromResource("datasets/combine.json")
                val baseStaging = baseDataset.getNested("_vehicle_schedule_frames.staging")
                val baseTarget = baseDataset.getNested("_vehicle_schedule_frames.target")

                // Create new test data set with multiple staging-target pairs,
                // using the default dataset as base.
                testData =
                    TimetablesDataset.createFromMutableMap(
                        mutableMapOf(
                            "_vehicle_schedule_frames" to
                                mutableMapOf(
                                    "staging1" to
                                        baseStaging.toMutableMap().setFrameProperties(
                                            mutableMapOf(
                                                "vehicle_schedule_frame_id" to stagingFrameIds[0],
                                                "name" to "2023 tammikuu luonnos",
                                                "validity_start" to "2023-01-01",
                                                "validity_end" to "2023-01-31"
                                            )
                                        ),
                                    "target1" to
                                        baseTarget.deepClone().setFrameProperties(
                                            mutableMapOf(
                                                "vehicle_schedule_frame_id" to targetFrameIds[0],
                                                "name" to "2023 tammikuu",
                                                "validity_start" to "2023-01-01",
                                                "validity_end" to "2023-01-31"
                                            )
                                        ),
                                    "staging2" to
                                        baseStaging.deepClone().setFrameProperties(
                                            mutableMapOf(
                                                "vehicle_schedule_frame_id" to stagingFrameIds[1],
                                                "name" to "2023 helmikuu luonnos",
                                                "validity_start" to "2023-02-01",
                                                "validity_end" to "2023-02-28"
                                            )
                                        ),
                                    "target2" to
                                        baseTarget.deepClone().setFrameProperties(
                                            mutableMapOf(
                                                "vehicle_schedule_frame_id" to targetFrameIds[1],
                                                "name" to "2023 helmikuu",
                                                "validity_start" to "2023-02-01",
                                                "validity_end" to "2023-02-28"
                                            )
                                        ),
                                    "staging3" to
                                        baseStaging.deepClone().setFrameProperties(
                                            mutableMapOf(
                                                "vehicle_schedule_frame_id" to stagingFrameIds[2],
                                                "name" to "2023 maaliskuu luonnos",
                                                "validity_start" to "2023-03-01",
                                                "validity_end" to "2023-03-31"
                                            )
                                        ),
                                    "target3" to
                                        baseTarget.deepClone().setFrameProperties(
                                            mutableMapOf(
                                                "vehicle_schedule_frame_id" to targetFrameIds[2],
                                                "name" to "2023 maaliskuu",
                                                "validity_start" to "2023-03-01",
                                                "validity_end" to "2023-03-31"
                                            )
                                        )
                                ),
                            "_journey_pattern_refs" to baseDataset["_journey_pattern_refs"]
                        )
                    )
            }

            @Test
            fun `combine each staging timetables and returns target vehicle schedule frame ids`() {
                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())

                val result =
                    combineTimetablesService.combineTimetables(
                        stagingFrameIds,
                        TimetablesPriority.STANDARD
                    )

                assertEquals(result, targetFrameIds)
                // Each staging frame got processed: old frames deleted.
                assertNull(vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(stagingFrameIds[0]))
                assertNull(vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(stagingFrameIds[1]))
                assertNull(vehicleScheduleFrameRepository.fetchOneByVehicleScheduleFrameId(stagingFrameIds[2]))
            }

            @Test
            fun `fails if any of the requested staging frames do not get handled successfully`() {
                // Change priority of one target -> target can't be found for matching staging frame -> fails.
                testData.getNested("_vehicle_schedule_frames.target2")["priority"] = "Temporary"
                timetablesDataInserterRunner.truncateAndInsertDataset(testData.toJSONString())

                val initialFrames = vehicleScheduleFrameRepository.findAll().toSet()
                val initialServices = vehicleServiceRepository.findAll().toSet()

                val exception =
                    assertFailsWith<TargetFrameNotFoundException> {
                        combineTimetablesService.combineTimetables(
                            stagingFrameIds,
                            TimetablesPriority.STANDARD
                        )
                    }

                assertEquals(stagingFrameIds[1], exception.stagingVehicleScheduleFrameId)
                // Whole transaction rolled back, no changes persisted.
                val framesAfter = vehicleScheduleFrameRepository.findAll().toSet()
                val servicesAfter = vehicleServiceRepository.findAll().toSet()
                assertEquals(initialFrames, framesAfter)
                assertEquals(initialServices, servicesAfter)
            }
        }

        private fun MutableMap<String, Any?>.setFrameProperties(
            patch: MutableMap<String, Any?>,
            serviceId: UUID = UUID.randomUUID()
        ): MutableMap<String, Any?> {
            this.putAll(patch)
            // Service id is hard coded in the dataset (for asserts),
            // need to reset since these will be cloned.
            this.getNested("_vehicle_services.monFri")["vehicle_service_id"] = serviceId
            return this
        }
    }
