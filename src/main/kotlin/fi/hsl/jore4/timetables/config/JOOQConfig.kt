package fi.hsl.jore4.timetables.config

import org.jooq.SQLDialect
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement

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
    fun transactionAwareDataSource() = TransactionAwareDataSourceProxy(timetablesDataSource())

    @Bean
    fun transactionManager() = DataSourceTransactionManager(timetablesDataSource())

    @Bean
    fun connectionProvider() = DataSourceConnectionProvider(transactionAwareDataSource())

    @Bean
    fun configuration(): DefaultConfiguration {
        val dialect = SQLDialect.valueOf(jooqConfiguration.dialect)

        val config = DefaultConfiguration()
        config.set(connectionProvider())
        config.set(dialect)
        return config
    }

    @Bean
    fun dsl() = DefaultDSLContext(configuration())
}
