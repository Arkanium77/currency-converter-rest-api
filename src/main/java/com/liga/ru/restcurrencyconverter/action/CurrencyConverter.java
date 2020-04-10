package com.liga.ru.restcurrencyconverter.action;

import com.liga.ru.restcurrencyconverter.interfaces.CurrencyReader;

public class CurrencyConverter {


    public double convert(double value, String from, String to, CurrencyReader currencyReader) {
        return value * getMultiplier(from, to, currencyReader);
    }

    private double getMultiplier(String from, String to, CurrencyReader currencyReader) {
        var courses = currencyReader.getCourses();
        return courses.get(from) * courses.get(to);
    }

}
