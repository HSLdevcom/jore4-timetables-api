package fi.hsl.jore4.timetables

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging

private val LOGGER = KotlinLogging.logger {}

class TimetablesDataset : MutableMap<String, Any?> by mutableMapOf() {
    companion object {
        fun createFromResource(resourcePath: String): TimetablesDataset {
            val objectMapper = ObjectMapper()
            val jsonStream = this::class.java.classLoader.getResourceAsStream(resourcePath)
            return objectMapper.readValue(jsonStream, object : TypeReference<TimetablesDataset>() {})
        }

        fun createFromString(datasetJson: String): TimetablesDataset {
            val objectMapper = ObjectMapper()
            return objectMapper.readValue(datasetJson, object : TypeReference<TimetablesDataset>() {})
        }
    }

    fun getNested(propertyPath: String): MutableMap<String, Any?> {
        var parts = propertyPath.split(".")

        var child: MutableMap<String, Any?> = this
        for (childPath in parts) {
            LOGGER.debug { "Get path: $childPath" }
            @Suppress("UNCHECKED_CAST")
            child = child[childPath] as MutableMap<String, Any?>
        }

        return child
    }

    fun toJSONString(): String {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(this)
    }
}
