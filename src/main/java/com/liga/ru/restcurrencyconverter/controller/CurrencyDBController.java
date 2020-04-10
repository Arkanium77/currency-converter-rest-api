package com.liga.ru.restcurrencyconverter.controller;

import com.liga.ru.restcurrencyconverter.configuration.AppJavaConfig;
import com.liga.ru.restcurrencyconverter.service.DatabaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("database")
public class CurrencyDBController {
    private DatabaseService service;

    public CurrencyDBController() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppJavaConfig.class);
        service = (DatabaseService) context.getBean("databaseService");
    }

    @GetMapping(value = "/table")
    public ResponseEntity<String> getTable() {
        String table = service.getTable();
        return table != null
                ? new ResponseEntity<>(table, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<String> deleteCurrency(@RequestParam String code) {
        return service.deleteByCode(code)
                ? new ResponseEntity<>("Успешно удалено", HttpStatus.OK)
                : new ResponseEntity<>("Удалить не удалось", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/add")
    public ResponseEntity<String> addCurrency(@RequestParam String code,
                                              @RequestParam String name,
                                              @RequestParam Double value) {
        return service.insertCurrency(code,name,value)
                ? new ResponseEntity<>("Успешно добавлено", HttpStatus.OK)
                : new ResponseEntity<>("Добавить не удалось", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/update")
    public ResponseEntity<String> updateCurrency(@RequestParam String code,
                                              @RequestParam String name,
                                              @RequestParam Double value) {
        return service.updateCurrency(code,name,value)
                ? new ResponseEntity<>("Успешно обновлено", HttpStatus.OK)
                : new ResponseEntity<>("Обновить не удалось", HttpStatus.NOT_FOUND);
    }
}
