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
    val databaseProperties: DatabaseProperties
) {
    @Bean
    fun timetablesDataSource() =
        DriverManagerDataSource().apply {
            setDriverClassName(databaseProperties.driver)
            url = databaseProperties.url
            username = databaseProperties.username
            password = databaseProperties.password
        }

    @Bean
    fun transactionAwareDataSource(@Qualifier("timetablesDataSource") dataSource: DataSource) =
        TransactionAwareDataSourceProxy(dataSource)

    @Bean
    fun transactionManager(@Qualifier("timetablesDataSource") dataSource: DataSource) =
        DataSourceTransactionManager(dataSource)
}
