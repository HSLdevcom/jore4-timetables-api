package fi.hsl.jore4.timetables.service

import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.VehicleScheduleFrame
import fi.hsl.jore.jore4.jooq.vehicle_schedule.tables.records.VehicleScheduleFrameRecord
import fi.hsl.jore4.timetables.TimetablesDataset
import fi.hsl.jore4.timetables.enumerated.TimetablesPriority
import mu.KotlinLogging
import org.jooq.DSLContext
import org.jooq.exception.DataException
import org.jooq.impl.DSL
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*
import kotlin.test.assertContains

private val LOGGER = KotlinLogging.logger {}

@SpringBootTest
class ReplaceTimetablesServiceTest @Autowired constructor(
    val dsl: DSLContext,
    val replaceTimetablesService: ReplaceTimetablesService,
    var timetablesDataInserterRunner: TimetablesDataInserterRunner
) {
    private fun selectFrameById(frameId: UUID): VehicleScheduleFrameRecord? {
        return dsl
            .selectFrom(VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME)
            .where(
                VehicleScheduleFrame.VEHICLE_SCHEDULE_FRAME.VEHICLE_SCHEDULE_FRAME_ID
                    .eq(frameId)
            )
            .fetchOneInto(VehicleScheduleFrameRecord)
    }

    @Nested
    @DisplayName("processSingleStagingFrameReplacements")
    inner class ProcessSingleStagingFrameReplacements {
        @Nested
        @DisplayName("when successfully replacing a single target timetable")
        inner class SuccessfullyReplacingSingleTargetTimetable {
            private val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")
            private val targetFrameId = UUID.fromString("35e94feb-21d0-4418-9c9a-58345066af78")

            private lateinit var result: List<UUID>
            private lateinit var initialTargetFrame: VehicleScheduleFrameRecord
            private lateinit var initialStagingFrame: VehicleScheduleFrameRecord

            @BeforeEach
            fun runSingleFrameReplacement() {
                val testData = TimetablesDataset.createFromResource("datasets/replace.json")
                val output = timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())
//                    val builtDataset = TimetablesDataset.createFromString(output)

                initialTargetFrame = selectFrameById(targetFrameId)!!
                initialStagingFrame = selectFrameById(stagingFrameId)!!

                result = replaceTimetablesService.processSingleStagingFrameReplacements(
                    stagingFrameId,
                    TimetablesPriority.STANDARD
                )
            }

            @Test
            fun `returns modified target VehicleScheduleFrame ids`() {
                assertEquals(listOf(targetFrameId), result)
            }

            @Test
            fun `expires the target timetable`() {
                val targetFrame = selectFrameById(targetFrameId)!!
                assertNotNull(targetFrame)

                val expectedTargetFrame = initialTargetFrame
                // Ends exactly one day before the new one starts.
                assertEquals(initialStagingFrame.validityStart, LocalDate.parse("2023-01-01"))
                expectedTargetFrame.validityEnd = LocalDate.parse("2022-12-31")
                expectedTargetFrame.validityRange = targetFrame.validityRange // Generated column, unimportant.

                assertEquals(expectedTargetFrame, targetFrame)
            }

            @Test
            fun `promotes staging timetable to target priority`() {
                val stagingFrame = selectFrameById(stagingFrameId)
                assertNotNull(stagingFrame)

                val expectedStagingFrame = initialStagingFrame!!
                expectedStagingFrame.set(DSL.field("priority"), TimetablesPriority.STANDARD.value)

                assertEquals(expectedStagingFrame, stagingFrame)
            }
        }

        @Nested
        @DisplayName("with multiple target timetables")
        inner class WithMultipleTargetTimetables {
            private val stagingFrameId = UUID.fromString("9E758776-2AF1-49C7-8BD0-C0805B833B20")
            private val target1FrameId = UUID.fromString("B62B71C4-D1B4-4A9A-8490-D95CFE304B5D")
            private val target2FrameId = UUID.fromString("C85863A3-7ACC-4478-96C6-928F2CD744B3")

            private lateinit var testData: TimetablesDataset

            @BeforeEach
            fun runSingleFrameReplacement() {
                testData = TimetablesDataset.createFromResource("datasets/replace_multiple_targets.json")
            }

            @Test
            fun `expires each target when successful`() {
                timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

                var result = replaceTimetablesService.processSingleStagingFrameReplacements(
                    stagingFrameId,
                    TimetablesPriority.STANDARD
                )

                assertEquals(listOf(target1FrameId, target2FrameId), result)

                val targetFrame1 = selectFrameById(target1FrameId)!!
                val targetFrame2 = selectFrameById(target2FrameId)!!
                assertNotNull(targetFrame1)
                assertNotNull(targetFrame2)

                assertEquals(targetFrame1.validityEnd, LocalDate.parse("2022-11-30"))
                assertEquals(targetFrame2.validityEnd, LocalDate.parse("2022-11-30"))
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
                testData.getNested("_vehicle_schedule_frames.target")["vehicle_schedule_frame_id"].toString()
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
        fun `fails when target timetable can not be found`() {
            val testData = TimetablesDataset.createFromResource("datasets/replace.json")
            timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

            val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")

            val exception = assertThrows(Error::class.java) {
                replaceTimetablesService.processSingleStagingFrameReplacements(
                    stagingFrameId,
                    TimetablesPriority.TEMPORARY // There is target only at Standard priority
                )
            }

            assertContains(exception.message.toString(), "Could not find vehicle schedule frame to replace")
        }

        @Nested
        @DisplayName("fails with incompatible validities")
        inner class IncompatibleValidities {
            private val stagingFrameId = UUID.fromString("77fa8187-9a8e-4ce6-9fe2-5855f438b0e2")

            private fun testWithValidities(
                stagingStart: String,
                stagingEnd: String,
                targetStart: String,
                targetEnd: String
            ): List<UUID> {
                val testData = TimetablesDataset.createFromResource("datasets/replace.json")

                val stagingFrame = testData.getNested("_vehicle_schedule_frames.staging")
                val targetFrame = testData.getNested("_vehicle_schedule_frames.target")

                stagingFrame["validity_start"] = stagingStart
                stagingFrame["validity_end"] = stagingEnd
                targetFrame["validity_start"] = targetStart
                targetFrame["validity_end"] = targetEnd

                timetablesDataInserterRunner.runTimetablesDataInserter(testData.toJSONString())

                return replaceTimetablesService.processSingleStagingFrameReplacements(
                    stagingFrameId,
                    TimetablesPriority.STANDARD
                )
            }

            @Test
            fun `when target is after staging`() {
                // Validity times need to overlap to be chosen as a target.
                val exception = assertThrows(Error::class.java) {
                    testWithValidities(
                        stagingStart = "2023-01-01", stagingEnd = "2023-05-31",
                        targetStart = "2023-07-01", targetEnd = "2023-09-30",
                    )
                }

                assertContains(exception.message.toString(), "Could not find vehicle schedule frame to replace")
            }

            @Test
            fun `when staging start is before target start`() {
                val exception = assertThrows(DataException::class.java) {
                    testWithValidities(
                        stagingStart = "2023-01-01", stagingEnd = "2023-05-31",
                        targetStart = "2023-02-02", targetEnd = "2023-09-30",
                    )
                }

                assertContains(
                    exception.message.toString(),
                    "range lower bound must be less than or equal to range upper bound"
                )
            }

            @Test
            fun `when staging starts when target starts`() {
                val exception = assertThrows(DataException::class.java) {
                    testWithValidities(
                        stagingStart = "2023-01-01", stagingEnd = "2023-05-31",
                        targetStart = "2023-01-01", targetEnd = "2023-09-30",
                    )
                }

                assertContains(
                    exception.message.toString(),
                    "range lower bound must be less than or equal to range upper bound"
                )
            }

            @Test
            fun `when target starts on same day as staging ends`() {
                val exception = assertThrows(DataException::class.java) {
                    testWithValidities(
                        stagingStart = "2023-01-01", stagingEnd = "2023-05-31",
                        targetStart = "2023-05-31", targetEnd = "2023-09-30",
                    )
                }

                assertContains(
                    exception.message.toString(),
                    "range lower bound must be less than or equal to range upper bound"
                )
            }
        }
    }

    @Nested
    @DisplayName("fetchVehicleScheduleFramesToReplace")
    inner class FetchVehicleScheduleFramesToReplace {
        /*
            // TODO:
            valid case
                corner cases for validitiies overlapping
                single route in each
                multiple routes in each: all same
                multiple routes in each: partially same
                    more in staging
                    more in target
                multiple day types, similar to routes
                    + week day corner cases
            different priority
            validities do not overlap
            no shared routes
         */
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
