package com.hotel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DBProperty {
    String configFileName() default "Hotel/hotel-main/src/main/resources/DB.properties";
    String propertyName() default "";
    PropertyType propertyType() default PropertyType.STRING;
}
