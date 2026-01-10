package com.hotel.di;

import com.hotel.annotations.Singleton;
import com.hotel.config.Configs;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    @Setter
    private ObjectFactory factory;
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Configs config;

    public ApplicationContext(Configs config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) {

        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        if (cache.containsKey(implClass)) {
            return (T) cache.get(implClass);
        }
        T t = factory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(implClass, t);
        }

        return t;
    }
}