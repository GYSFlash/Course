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
    private Connection connection;

    private boolean driverLoaded = false;

    public DBConnection() {
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
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        dbConfig.getUrl(),
                        dbConfig.getUsername(),
                        dbConfig.getPassword()
                );
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к БД: " + dbConfig.getUrl(), e);
        }
    }
}
