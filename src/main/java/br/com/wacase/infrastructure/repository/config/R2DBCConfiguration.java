package br.com.wacase.infrastructure.repository.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.h2.H2ConnectionOption;
import io.r2dbc.spi.ConnectionFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
@EnableR2dbcRepositories
@Slf4j
public class R2DBCConfiguration extends AbstractR2dbcConfiguration {

//    @Bean
    public H2ConnectionFactory connectionFactory() {

        return new H2ConnectionFactory(H2ConnectionConfiguration.builder()
                .inMemory("wa")
                .url("r2dbc:h2:mem:db;DB_CLOSE_DELAY=-1;NON_KEYWORDS=USER")
                .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
                .property(H2ConnectionOption.MODE, "PostgreSQL")
                .property(H2ConnectionOption.TRACE_LEVEL_FILE, "3")
                .build());

//        return new H2ConnectionFactory(
//                H2ConnectionConfiguration.builder()
//                        .url("mem:testdb;DB_CLOSE_DELAY=-1;")
//                        .username("sa")
//                        .
//                        .build()
//        );
    }

    @Bean
    @Primary
    public ConnectionFactoryInitializer initializerH2(
            final ConnectionFactory connectionFactory,
            @Value("${database.h2.initialization-script}")
            final String initializationScript) {

        final Resource resource = new ClassPathResource(initializationScript);
        final var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(resource));
        return initializer;
    }
}
