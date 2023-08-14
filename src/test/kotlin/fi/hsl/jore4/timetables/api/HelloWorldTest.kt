package fi.hsl.jore4.timetables.api

import fi.hsl.jore4.timetables.service.ScheduleFrameService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@DisplayName("Test Hello World API")
class HelloWorldTest {

    private val scheduleFrameService = mockk<ScheduleFrameService>()

    @BeforeEach
    fun setup() {
        every { scheduleFrameService.getServiceFrames() } returns "frame1, frame2"
    }

    @Test
    @DisplayName("Test the API returns hello")
    fun helloWorldApiTest() {
        val expected = "hello"
        val api = HelloWorld(scheduleFrameService)

        val returned = api.helloWorld()

        assertEquals(expected, returned.body!!, "Body content was not what was expected")
    }

    @Test
    @DisplayName("Test the API returns hello")
    fun scheduleFrameLabelsTest() {
        val expected = "frame1, frame2"
        val api = HelloWorld(scheduleFrameService)

        val returned = api.vehicleScheduleFrames()

        assertEquals(expected, returned.body!!, "Body did not contain expected frames")
    }
}
