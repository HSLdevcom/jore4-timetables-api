package fi.hsl.jore4.timetables.service

import fi.hsl.jore4.timetables.config.DataInserterDatabaseProperties
import fi.hsl.jore4.timetables.config.DatabaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.io.File
import java.io.Reader
import java.net.URI
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

@Service
@EnableConfigurationProperties(DataInserterDatabaseProperties::class)
class TimetablesDataInserterRunner(
    val dataInserterDatabaseConfiguration: DataInserterDatabaseProperties,
    val databaseConfiguration: DatabaseProperties
) {
    fun truncateAndInsertDataset(datasetJson: String): String {
        val inserterWorkDirectory = Paths.get("jore4-hasura/test/hasura").toAbsolutePath().toString()

        // Note: this performs DB truncation internally.
        val command = "yarn timetables-data-inserter:cli insert hsl ${buildDatabaseArguments()}"

        val process =
            ProcessBuilder(*command.split(" ").toTypedArray())
                .directory(File(inserterWorkDirectory))
                .redirectError(ProcessBuilder.Redirect.INHERIT)
                .start()

        process.outputStream.use {
            val writer = it.bufferedWriter()
            writer.write(datasetJson)
            writer.flush()
        }

        val exitedBeforeTimeout = process.waitFor(10, TimeUnit.SECONDS)
        if (!exitedBeforeTimeout) {
            throw RuntimeException("Running timetables-data-inserter timed out.")
        }

        val exitValue = process.exitValue()
        val output = process.inputStream.bufferedReader().use(Reader::readText)
        if (exitValue != 0) {
            throw RuntimeException("Running timetables-data-inserter failed with exit code $exitValue. Process output:\n$output")
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
            "--user ${dataInserterDatabaseConfiguration.username}",
            "--password ${dataInserterDatabaseConfiguration.password}"
        ).joinToString(" ")
    }
}
