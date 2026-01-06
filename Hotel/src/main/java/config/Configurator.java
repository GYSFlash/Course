package config;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;
import lombok.*;

import static java.util.stream.Collectors.toMap;

public class Configurator implements ObjectConfigurator {
    private Map<String, String> propertiesMap;

    @SneakyThrows
    public Configurator() {}

    @Override
    @SneakyThrows
    public void configure(Object t) {

        Class<?> implClass = t.getClass();
        String path = "config.properties";
        for (Field field : implClass.getDeclaredFields()) {
            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);
            if (annotation != null) {
                path = annotation.configFileName();
                break;
            }
        }
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();

        propertiesMap = lines
                .filter(line -> !line.isBlank())
                .map(line -> line.split("="))
                .collect(toMap(arr -> arr[0].trim(),
                        arr -> arr[1].trim()));

        for (Field field : implClass.getDeclaredFields()) {

            ConfigProperty annotation = field.getAnnotation(ConfigProperty.class);

            if (annotation == null) {
                continue;
            }

            String propertyName = annotation.propertyName().isEmpty()
                    ? implClass.getSimpleName() + "." + field.getName()
                    : annotation.propertyName();
            System.out.println("Looking for key: " + propertyName);

            String rawValue = propertiesMap.get(propertyName);

            Object value = convert(rawValue, annotation.propertyType());

            field.setAccessible(true);
            field.set(t, value);
        }
    }

    private Object convert(String value, PropertyType type) {

        return switch (type) {
            case STRING -> value;
            case INT -> Integer.parseInt(value);
            case BOOLEAN -> Boolean.parseBoolean(value);
        };
    }
}
