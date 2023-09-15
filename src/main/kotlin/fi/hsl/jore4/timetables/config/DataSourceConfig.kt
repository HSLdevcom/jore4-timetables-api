package fi.hsl.jore4.timetables.config

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
class DataSourceConfig(
    val databaseConfiguration: DatabaseConfiguration
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
}
