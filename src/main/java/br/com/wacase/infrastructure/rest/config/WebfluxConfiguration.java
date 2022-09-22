package br.com.wacase.infrastructure.rest.config;

import java.time.format.DateTimeFormatter;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class WebfluxConfiguration implements WebFluxConfigurer {

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer
                .defaultCodecs()
                .maxInMemorySize(2 * 1024 * 1024);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }

    @Primary
    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

}
