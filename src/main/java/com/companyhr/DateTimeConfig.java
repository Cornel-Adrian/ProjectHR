package com.companyhr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.time.format.DateTimeFormatter;

@Configuration
class DateTimeConfig {

    @Bean
    public FormattingConversionService conversionService() {
        DefaultFormattingConversionService conversionService =
                new DefaultFormattingConversionService(true);

        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        /*registrar.setDateFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));*/
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        registrar.registerFormatters(conversionService);


        return conversionService;
    }
}