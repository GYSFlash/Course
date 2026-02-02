package com.hotel.config;

import com.hotel.annotations.DBProperty;
import com.hotel.annotations.PropertyType;

public class DBConfig {
    @DBProperty(
            propertyName = "db.driver",
            propertyType = PropertyType.STRING
    )
    private String driver;
    @DBProperty(
            propertyName = "db.url",
            propertyType = PropertyType.STRING
    )
    private String url;

    @DBProperty(
            propertyName = "db.username",
            propertyType = PropertyType.STRING
    )
    private String username;
    @DBProperty(
            propertyName = "db.password",
            propertyType = PropertyType.STRING
    )
    private String password;
    public String getDriver() {
        return driver;
    }
    public String getUrl() { return url; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
