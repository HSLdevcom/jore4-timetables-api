package fi.hsl.jore4.timetables

import io.github.oshai.kotlinlogging.KotlinLogging
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.json.JsonMapper

private val LOGGER = KotlinLogging.logger {}

class TimetablesDataset : MutableMap<String, Any?> by mutableMapOf() {
    fun toJSONString(): String = OBJECT_MAPPER.writeValueAsString(this)

    companion object {
        private val OBJECT_MAPPER = JsonMapper()

        fun createFromResource(resourcePath: String): TimetablesDataset {
            val jsonStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
            return OBJECT_MAPPER.readValue(jsonStream, object : TypeReference<TimetablesDataset>() {})
        }

        fun createFromMutableMap(data: MutableMap<String, Any?>) = TimetablesDataset().also { it.putAll(data) }
    }
}
