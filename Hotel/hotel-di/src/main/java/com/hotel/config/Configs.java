package com.hotel.config;

import org.reflections.Reflections;

public interface Configs {
    <T> Class<? extends T> getImplClass(Class<T> ifc);
    Reflections getScanner();
}
