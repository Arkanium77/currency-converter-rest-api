package com.liga.ru.restcurrencyconverter.configuration;

import com.liga.ru.restcurrencyconverter.action.CurrencyConverter;
import com.liga.ru.restcurrencyconverter.controller.CurrencyDBController;
import com.liga.ru.restcurrencyconverter.readers.CurrencyDBReader;
import com.liga.ru.restcurrencyconverter.readers.CurrencyFileReader;
import com.liga.ru.restcurrencyconverter.service.CurrencyService;
import com.liga.ru.restcurrencyconverter.service.DatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AppJavaConfig {

    @Bean
    public CurrencyFileReader currencyFileReader() {
        return new CurrencyFileReader();
    }

    @Bean
    public DatabaseService databaseService(){
        return new DatabaseService(jdbcTemplate());
    }


    @Bean
    public CurrencyDBReader currencyDBReader() {
        return new CurrencyDBReader(jdbcTemplate());
    }

    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(
                "jdbc:hsqldb:file:D:/Java/Currency Converter REST/src/main/resources/database/currency.db",
                "root",
                "1501");
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public CurrencyConverter currencyConverter() {
        return new CurrencyConverter();
    }

    @Bean
    public CurrencyService currencyService() {
        return new CurrencyService(currencyDBReader(), currencyConverter());
    }

}
