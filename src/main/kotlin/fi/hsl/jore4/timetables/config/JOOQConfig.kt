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

@Configuration
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
    fun transactionAwareDataSource(): TransactionAwareDataSourceProxy? {
        return TransactionAwareDataSourceProxy(timetablesDataSource())
    }

    @Bean
    fun transactionManager(): DataSourceTransactionManager? {
        return DataSourceTransactionManager(timetablesDataSource())
    }

    @Bean
    fun connectionProvider(): DataSourceConnectionProvider? {
        return DataSourceConnectionProvider(transactionAwareDataSource())
    }

    @Bean
    fun configuration(): DefaultConfiguration? {
        val config = DefaultConfiguration()
        config.set(connectionProvider())
        val dialect = SQLDialect.valueOf(jooqConfiguration.dialect)
        config.set(dialect)
        return config
    }

    @Bean
    fun dsl(): DefaultDSLContext? {
        return DefaultDSLContext(configuration())
    }
}
