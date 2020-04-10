package com.liga.ru.restcurrencyconverter.service;

import com.liga.ru.restcurrencyconverter.action.CurrencyConverter;
import com.liga.ru.restcurrencyconverter.interfaces.CurrencyReader;
import com.liga.ru.restcurrencyconverter.readers.CurrencyFileReader;

public class CurrencyService {
    CurrencyConverter converter;
    CurrencyReader reader;

    public CurrencyService() {
        reader = new CurrencyFileReader();
        converter = new CurrencyConverter();
    }

    public CurrencyService(CurrencyReader reader, CurrencyConverter converter) {
        this.reader = reader;
        this.converter = converter;
    }

    public String getCodesAndInfo() {
        var list = reader.getCodesAndInfo();
        return String.join("<br>", list);
    }

    public String getConverted(double value, String from, String to) {
        return String.format("%.2f %s => %.2f %s",
                value,
                from,
                converter.convert(value, from, to, reader),
                to);
    }
}
