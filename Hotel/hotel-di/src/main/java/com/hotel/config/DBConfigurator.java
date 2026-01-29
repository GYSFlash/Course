package com.hotel.config;

import com.hotel.annotations.ConfigProperty;
import com.hotel.annotations.DBProperty;
import com.hotel.di.ApplicationContext;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class DBConfigurator implements ObjectConfigurator {
    private Map<String, String> propertiesMap;

    @SneakyThrows
    public DBConfigurator() {}

    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {

        Class<?> implClass = t.getClass();
        String path = "DB.properties";

        InputStream is = implClass.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException(path + " not found in resources");
        }
        Stream<String> lines = new BufferedReader(new InputStreamReader(is)).lines();

        propertiesMap = lines
                .filter(line -> !line.isBlank())
                .map(line -> line.split("="))
                .collect(toMap(arr -> arr[0].trim(),
                        arr -> arr[1].trim()));

        for (Field field : implClass.getDeclaredFields()) {

            DBProperty annotation = field.getAnnotation(DBProperty.class);

            if (annotation == null) {
                continue;
            }

            String propertyName = annotation.propertyName().isEmpty()
                    ? implClass.getSimpleName() + "." + field.getName()
                    : annotation.propertyName();

            String rawValue = propertiesMap.get(propertyName);

            field.setAccessible(true);
            field.set(t, rawValue);
        }
    }
}
