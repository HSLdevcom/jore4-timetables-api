package fi.hsl.jore4.timetables

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging

private val LOGGER = KotlinLogging.logger {}

class TimetablesDataset : MutableMap<String, Any?> by mutableMapOf() {
    fun getNested(propertyPath: String): MutableMap<String, Any?> {
        val parts = propertyPath.split(".")

        var child: MutableMap<String, Any?> = this
        for (childPath in parts) {
            LOGGER.debug { "Get path: $childPath" }
            @Suppress("UNCHECKED_CAST")
            child = child[childPath] as MutableMap<String, Any?>
        }

        return child
    }

    fun toJSONString(): String {
        return OBJECT_MAPPER.writeValueAsString(this)
    }

    companion object {
        private val OBJECT_MAPPER = ObjectMapper()

        fun createFromResource(resourcePath: String): TimetablesDataset {
            val jsonStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
            return OBJECT_MAPPER.readValue(jsonStream, object : TypeReference<TimetablesDataset>() {})
        }
    }
}
