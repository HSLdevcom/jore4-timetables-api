package fi.hsl.jore4.timetables.api

import fi.hsl.jore4.timetables.service.ScheduleFrameService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val LOGGER = KotlinLogging.logger {}

@RestController
@RequestMapping("/hello")
class HelloWorld(
    private val scheduleFrameService: ScheduleFrameService
) {

    @GetMapping
    fun helloWorld(): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body("hello")
    }

    @GetMapping("testi")
    fun vehicleScheduleFrames(): ResponseEntity<String> {
        LOGGER.info("Request for frames")
        val framet = scheduleFrameService.getServiceFrames()
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(framet)
    }
}
