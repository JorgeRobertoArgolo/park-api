package com.jorgeroberto.park_api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;
import java.util.TimeZone;

// Indica que a classe define uma classe de configuração do Spring
@Configuration
public class SpringTimezoneConfig {

    /*
    * Executa o métod anotado logo após a
    * construção do bean e antes da
    * aplicação estar completamente pronta
    * */

    /*
     * Define o fuso horário padrão para toda a JVM como "America/Sao_Paulo".
     * */
    @PostConstruct
    public void timezoneConfig () {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
/*
    @Bean
    public LocaleResolver localeResolver() {
        return new FixedLocaleResolver(new Locale("pt", "BR"));
    }*/
}
