package com.liga.ru.restcurrencyconverter.readers;

import com.liga.ru.restcurrencyconverter.interfaces.CurrencyReader;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CurrencyFileReader implements CurrencyReader {
    List<List<String>> content;

    public CurrencyFileReader() {
        content = read();
    }

    private List<List<String>> read() {
        List<List<String>> list = new ArrayList<>();
        String fileContent = readFile();
        for (String line : fileContent.split("\n")) {
            List<String> args = Arrays.asList(line.split("\t"));
            list.add(args);
        }
        return list;
    }

    public Map<String, Double> getCourses() {
        Map<String, Double> course = new HashMap<>();
        for (List<String> line : content) {
            double d;
            try {
                d = Double.parseDouble(line.get(2).trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Не удалось преобразовать одно или несколько значений");
                return null;
            }
            course.put(line.get(0), d);
        }
        return course;
    }

    public List<String> getCodesAndInfo() {
        return content.stream()
                .map(list -> list.get(0) + " " + list.get(1))
                .collect(Collectors.toList());
    }

    private String readFile() {
        StringBuilder fileContent = new StringBuilder();
        try (FileReader reader = new FileReader(Objects.requireNonNull(getFile()))) {
            char[] buf = new char[256];
            int c;
            while ((c = reader.read(buf)) > 0) {

                if (c < 256) {
                    buf = Arrays.copyOf(buf, c);
                }
                fileContent.append(buf);
            }
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
        return fileContent.toString();
    }

    private File getFile() {
        File f;
        try {
            f = ResourceUtils.getFile("classpath:static/currency.tsv");
        } catch (FileNotFoundException e) {
            System.out.println("---> Нет файла с курсом");
            return null;
        }
        return f;
    }


}
