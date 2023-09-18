package fi.hsl.jore4.timetables.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class DataSourceConfig(
    val databaseProperties: DatabaseProperties
) {
    // Creates configuration for Hikari database connection pool.
    // Consider adding another configuration (besides "Standard") e.g. for integration tests.
    private fun createStandardHikariConfig(): HikariConfig {
        val hikariConfig = HikariConfig()

        databaseProperties.apply {
            // The pool name is fixed. There is only one database to connect to.
            hikariConfig.poolName = "timetablesdb-pool"

            hikariConfig.driverClassName = driver
            hikariConfig.jdbcUrl = url
            hikariConfig.username = username
            hikariConfig.password = password
            hikariConfig.minimumIdle = minConnections
            hikariConfig.maximumPoolSize = maxConnections

            // Currently, the SERIALIZABLE isolation level is used throughout Jore4.
            // This prevents conflicts between parallel write transactions.
            //
            // Note: If in the future it is found that this does not scale well enough,
            // overriding the isolation level can be replaced by SELECT ... FOR UPDATE constructs
            // while creating SQL statements.
            hikariConfig.transactionIsolation = "TRANSACTION_SERIALIZABLE"
        }

        return hikariConfig
    }

    // The dataSource is a Hikari connection pool.
    @Bean(destroyMethod = "close")
    fun timetablesDataSource() = HikariDataSource(createStandardHikariConfig())

    @Bean
    fun transactionAwareDataSource(@Qualifier("timetablesDataSource") dataSource: DataSource) =
        TransactionAwareDataSourceProxy(dataSource)

    @Bean
    fun transactionManager(@Qualifier("timetablesDataSource") dataSource: DataSource) =
        DataSourceTransactionManager(dataSource)
}
