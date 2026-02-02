package com.hotel.config;

import com.hotel.di.ApplicationContext;
import com.hotel.annotations.InjectByType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        Class<?> clazz = t.getClass();
        while (clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {

                if (field.isAnnotationPresent(InjectByType.class)) {
                    field.setAccessible(true);

                    Object dependency = context.getObject(field.getType());
                    field.set(t, dependency);
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}


