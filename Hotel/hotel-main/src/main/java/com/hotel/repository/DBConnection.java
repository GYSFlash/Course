package com.hotel.repository;

import com.hotel.annotations.InjectByType;
import com.hotel.annotations.Singleton;
import com.hotel.config.DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Singleton
public class DBConnection {

    @InjectByType
    private DBConfig dbConfig;

    private boolean driverLoaded = false;

    public DBConnection(){
    }

    private void loadDriver() {
        if (driverLoaded) return;

        try {
            Class.forName(dbConfig.getDriver());
            driverLoaded = true;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Драйвер БД не найден: " + dbConfig.getDriver(), e);
        }
    }

    public Connection getConnection(){
        loadDriver();
        try {
            return DriverManager.getConnection(
                    dbConfig.getUrl(),
                    dbConfig.getUsername(),
                    dbConfig.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к БД: " + dbConfig.getUrl(), e);
        }
    }

}
