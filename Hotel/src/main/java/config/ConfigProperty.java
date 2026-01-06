package config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigProperty {
    String configFileName() default "Hotel/src/main/resources/config.properties";
    String propertyName() default "";
    PropertyType propertyType() default PropertyType.STRING;
}
