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
import javax.sql.DataSource

@Configuration
class JOOQConfig(
    val jooqProperties: JOOQProperties
) {
    @Bean
    fun connectionProvider(@Qualifier("timetablesDataSource") dataSource: DataSource): ConnectionProvider =
        DataSourceConnectionProvider(dataSource)

    @Bean
    fun configuration(connectionProvider: ConnectionProvider): org.jooq.Configuration {
        val dialect = SQLDialect.valueOf(jooqProperties.dialect)

        val config = DefaultConfiguration()
        config.set(connectionProvider)
        config.set(dialect)
        return config
    }

    @Bean
    fun dslContext(configuration: org.jooq.Configuration): DSLContext = DefaultDSLContext(configuration)
}
