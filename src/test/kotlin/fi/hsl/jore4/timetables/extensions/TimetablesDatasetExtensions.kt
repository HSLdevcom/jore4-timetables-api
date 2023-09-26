package fi.hsl.jore4.timetables.extensions

import mu.KotlinLogging

private val LOGGER = KotlinLogging.logger {}

fun MutableMap<String, Any?>.getNested(propertyPath: String): MutableMap<String, Any?> {
    val parts = propertyPath.split(".")

    var child: MutableMap<String, Any?> = this
    for (childPath in parts) {
        LOGGER.debug { "Get path: $childPath" }
        @Suppress("UNCHECKED_CAST")
        child = child[childPath] as MutableMap<String, Any?>
    }

    return child
}
