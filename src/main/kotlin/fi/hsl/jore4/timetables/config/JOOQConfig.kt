package fi.hsl.jore4.timetables.config

import org.jooq.ConnectionProvider
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class JOOQConfig(
    val databaseConfiguration: DatabaseConfiguration,
    val jooqConfiguration: JOOQConfiguration
) {

    @Bean
    fun timetablesDataSource() =
        DriverManagerDataSource().apply {
            setDriverClassName(databaseConfiguration.driver)
            url = databaseConfiguration.url
            username = databaseConfiguration.username
            password = databaseConfiguration.password
        }

    @Bean
    fun transactionAwareDataSource(@Qualifier("timetablesDataSource") dataSource: DataSource) =
        TransactionAwareDataSourceProxy(dataSource)

    @Bean
    fun transactionManager(@Qualifier("timetablesDataSource") dataSource: DataSource) =
        DataSourceTransactionManager(dataSource)

    @Bean
    fun jooqConnectionProvider(@Qualifier("timetablesDataSource") dataSource: DataSource): ConnectionProvider =
        DataSourceConnectionProvider(dataSource)

    @Bean
    fun jooqConfiguration(jooqConnectionProvider: ConnectionProvider): org.jooq.Configuration {
        val dialect = SQLDialect.valueOf(jooqConfiguration.dialect)

        val config = DefaultConfiguration()
        config.set(jooqConnectionProvider)
        config.set(dialect)
        return config
    }

    @Bean
    fun jooqDslContext(jooqConfiguration: org.jooq.Configuration): DSLContext = DefaultDSLContext(jooqConfiguration)
}
