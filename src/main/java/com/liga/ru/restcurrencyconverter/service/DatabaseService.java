package com.liga.ru.restcurrencyconverter.service;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {
    JdbcTemplate jdbcTemplate;

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //TODO
    public boolean deleteByCode(String code) {
        try {
            jdbcTemplate.update("delete from currency where code = ?", code);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public boolean insertCurrency(String code, String name, Double inOneRouble) {
        var b = jdbcTemplate.query("select CODE from CURRENCY where CODE=?", new String[]{code}, ResultSet::next);
        if (b == null || b.equals(true))
            return false;
        try {
            jdbcTemplate.update("insert into CURRENCY values (?,?,?)", code, name, inOneRouble);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    //TODO
    public boolean updateCurrency(String code, String name, Double inOneRouble) {
        try {
            jdbcTemplate.update("update CURRENCY set name = ?, inOneRouble = ? where code=?", name, inOneRouble, code);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    public String getTable() {
        var list = getData();
        var builder = new StringBuilder("<table>");
        builder.append("<tr><th>Code</th><th>Name</th><th>inOneRouble</th></tr>");
        for (var row : list) {
            builder.append("<tr>");
            for (var element : row) {
                builder.append("<th>").append(element).append("</th>");
            }
            builder.append("</tr>");
        }

        builder.append("</table>");
        return builder.toString();

    }

    private List<List<String>> getData() {
        return jdbcTemplate.query("select code,name, inOneRouble from currency", (ResultSetExtractor<List<List<String>>>) resultSet -> {
            var list = new ArrayList<List<String>>();
            while (resultSet.next()) {
                var innerList = new ArrayList<String>();
                var code = resultSet.getString("code");
                var name = resultSet.getString("name");
                var inOneRouble = String.valueOf(resultSet.getDouble("inOneRouble"));
                innerList.add(code);
                innerList.add(name);
                innerList.add(inOneRouble);
                list.add(innerList);
            }
            return list;
        });
    }
}
