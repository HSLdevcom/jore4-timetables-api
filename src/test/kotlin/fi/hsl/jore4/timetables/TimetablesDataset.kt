package fi.hsl.jore4.timetables

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging

private val LOGGER = KotlinLogging.logger {}

class TimetablesDataset : MutableMap<String, Any?> by mutableMapOf() {
    fun toJSONString(): String {
        return OBJECT_MAPPER.writeValueAsString(this)
    }

    companion object {
        private val OBJECT_MAPPER = ObjectMapper()

        fun createFromResource(resourcePath: String): TimetablesDataset {
            val jsonStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
            return OBJECT_MAPPER.readValue(jsonStream, object : TypeReference<TimetablesDataset>() {})
        }

        fun createFromMutableMap(data: MutableMap<String, Any?>) =
            TimetablesDataset().also { it.putAll(data) }
    }
}
