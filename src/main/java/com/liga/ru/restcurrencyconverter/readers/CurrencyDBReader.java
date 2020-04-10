package com.liga.ru.restcurrencyconverter.readers;

import com.liga.ru.restcurrencyconverter.interfaces.CurrencyReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyDBReader implements CurrencyReader {
    JdbcTemplate jdbcTemplate;

    public CurrencyDBReader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Map<String, Double> getCourses() {
        return jdbcTemplate.query("Select code, inOneRouble from currency",
                (ResultSetExtractor<Map<String, Double>>) resultSet -> {
                    var queryMap = new HashMap<String, Double>();
                    while (resultSet.next()) {
                        queryMap.put(resultSet.getString("code"), resultSet.getDouble("inOneRouble"));
                    }
                    return queryMap;
                });
    }

    @Override
    public List<String> getCodesAndInfo() {
        return jdbcTemplate.query("Select code, name from currency",
                (ResultSetExtractor<List<String>>) resultSet -> {
                    var list = new ArrayList<String>();
                    while (resultSet.next()) {
                        list.add(resultSet.getString("code")
                                + " "
                                + resultSet.getString("name"));
                    }
                    return list;
                });
    }
}
