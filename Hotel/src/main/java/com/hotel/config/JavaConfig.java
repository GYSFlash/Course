package com.hotel.config;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JavaConfig implements Configs {
    @Getter
    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass = new HashMap<>();

    public JavaConfig(String packageToScan) {
        this.scanner = new Reflections(packageToScan);
    }
    @Override
    public Reflections getScanner() {
        return scanner;
    }
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new RuntimeException(ifc + " has 0 or more than one impl please update your config");
            }

            return classes.iterator().next();
        });

    }
}
