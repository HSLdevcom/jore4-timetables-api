package fi.hsl.jore4.timetables.api

import fi.hsl.jore4.timetables.service.ScheduleFrameService
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DisplayName("Test Hello World API")
@MockKExtension.ConfirmVerification
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

        assertNotNull(returned.body)
        assertEquals(expected, returned.body, "Body content was not what was expected")

        verify(exactly = 0) { scheduleFrameService.getServiceFrames() }
    }

    @Test
    @DisplayName("Test the API returns hello")
    fun scheduleFrameLabelsTest() {
        val expected = "frame1, frame2"
        val api = HelloWorld(scheduleFrameService)

        val returned = api.vehicleScheduleFrames()

        assertNotNull(returned.body)
        assertEquals(expected, returned.body, "Body did not contain expected frames")

        verify { scheduleFrameService.getServiceFrames() }
    }
}
