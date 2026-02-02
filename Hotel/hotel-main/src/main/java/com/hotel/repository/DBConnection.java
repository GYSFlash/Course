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
                connection.setAutoCommit(true);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось подключиться к БД: " + dbConfig.getUrl(), e);
        }
    }
    public void beginTransaction() {
        try{
            getConnection().setAutoCommit(false);
        }
        catch (SQLException e){
            throw new RuntimeException("Ошибка при начале транзакции",e);
        }
    }
    public void commitTransaction() {
        try{
            getConnection().commit();
        }
        catch (SQLException e){
            throw new RuntimeException("Ошибка при коммите транзакции",e);
        }
    }
    public void rollbackTransaction() {
        try{
            getConnection().rollback();
        }
        catch (SQLException e){
            throw new RuntimeException("Ошибка при откате транзакции",e);
        }
    }

}
