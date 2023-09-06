package fi.hsl.jore4.timetables.service

import fi.hsl.jore4.timetables.config.DatabaseConfiguration
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File
import java.net.URI
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

private val LOGGER = KotlinLogging.logger {}

@Service
class TimetablesDataInserterRunner(
    val databaseConfiguration: DatabaseConfiguration
) {
    fun runTimetablesDataInserter(datasetJson: String): String {
        val inserterWorkDirectory = Paths.get("jore4-hasura/test/hasura")
            .toAbsolutePath().toString()
        val command = "yarn --silent timetables-data-inserter:cli insert hsl ${buildDatabaseArguments()}"

        val process = ProcessBuilder(*command.split(" ").toTypedArray())
            .directory(File(inserterWorkDirectory))
            .redirectError(ProcessBuilder.Redirect.INHERIT)
            .start()

        process.outputStream.bufferedWriter().use { writer ->
            writer.write(datasetJson)
            writer.flush()
        }

        process.waitFor(10, TimeUnit.SECONDS)

        val output = process.inputStream.bufferedReader().use { reader ->
            reader.readText()
        }

        return output
    }

    private fun buildDatabaseArguments(): String {
        val cleanJdbcUri = databaseConfiguration.url.substring(5) // Strip leading "jdbc:"
        val jdbcUri = URI.create(cleanJdbcUri)
        val databaseName = jdbcUri.path.substring(1) // Strip leading "/"

        return listOf(
            "--host ${jdbcUri.host}",
            "--port ${jdbcUri.port}",
            "--database $databaseName",
            "--user ${databaseConfiguration.username}",
            "--password ${databaseConfiguration.password}"
        ).joinToString(" ")
    }
}
