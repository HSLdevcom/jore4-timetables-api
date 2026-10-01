package fi.hsl.jore4.timetables.extensions

import io.github.oshai.kotlinlogging.KotlinLogging
import tools.jackson.core.type.TypeReference
import tools.jackson.databind.json.JsonMapper

private val LOGGER = KotlinLogging.logger {}

fun MutableMap<String, Any?>.getNested(propertyPath: String): MutableMap<String, Any?> {
    val parts = propertyPath.split(".")

    var child: MutableMap<String, Any?> = this
    for (childPath in parts) {
        LOGGER.debug { "Get path: $childPath" }
        if (childPath !in child) {
            throw IllegalStateException("No child item found at property '$childPath'")
        }

        @Suppress("UNCHECKED_CAST")
        child = child[childPath] as MutableMap<String, Any?>
    }

    return child
}

private val OBJECT_MAPPER = JsonMapper()

fun MutableMap<String, Any?>.deepClone(): MutableMap<String, Any?> {
    val asString = OBJECT_MAPPER.writeValueAsString(this)
    return OBJECT_MAPPER.readValue(asString, object : TypeReference<MutableMap<String, Any?>>() {})
}
