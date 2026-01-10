package com.hotel.config;

import com.hotel.di.ApplicationContext;
import com.hotel.annotations.InjectByType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectByTypeAnnotationObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object t, ApplicationContext context) {
        for (Field field : t.getClass().getDeclaredFields()) {

            if (field.isAnnotationPresent(InjectByType.class)) {
                field.setAccessible(true);

                Object dependency = context.getObject(field.getType());
                field.set(t, dependency);
            }
        }
    }
}


