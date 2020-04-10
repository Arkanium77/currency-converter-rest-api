package com.liga.ru.restcurrencyconverter.controller;

import com.liga.ru.restcurrencyconverter.configuration.AppJavaConfig;
import com.liga.ru.restcurrencyconverter.service.CurrencyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("currency-converter")
public class CurrencyServiceController {

    private CurrencyService service;

    public CurrencyServiceController() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppJavaConfig.class);
        service = (CurrencyService) context.getBean("currencyService");
    }

    @GetMapping(value = "/codes")
    public ResponseEntity<String> getCodes() {
        String codes = service.getCodesAndInfo();
        return codes != null
                ? new ResponseEntity<>(codes, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/convert")
    public ResponseEntity<String> getConverted(@RequestParam(name = "value") double value,
                                               @RequestParam(name = "from") String from,
                                               @RequestParam(name = "to") String to) {
        String convertedValue;
        try {
            convertedValue = service.getConverted(value, from, to);
        } catch (Exception e) {
            convertedValue = null;
        }
        return convertedValue != null
                ? new ResponseEntity<>(convertedValue, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
