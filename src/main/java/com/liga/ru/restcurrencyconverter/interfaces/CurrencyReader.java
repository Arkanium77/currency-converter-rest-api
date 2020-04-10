package com.liga.ru.restcurrencyconverter.interfaces;

import java.util.List;
import java.util.Map;

public interface CurrencyReader {
    Map<String, Double> getCourses();
    List<String> getCodesAndInfo();
}
