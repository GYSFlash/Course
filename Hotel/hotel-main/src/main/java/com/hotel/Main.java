package com.hotel;

import com.hotel.config.AppConfig;

import com.hotel.di.ObjectFactory;
import com.hotel.config.Config;
import com.hotel.config.Configurator;
import com.hotel.config.JavaConfig;
import com.hotel.controller.*;
import com.hotel.repository.DBConnection;
import com.hotel.view.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {

        /*JavaConfig javaConfig = new JavaConfig("com.hotel");
        ApplicationContext context = new ApplicationContext(javaConfig);
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        Config config = context.getObject(Config.class);
        Configurator configurator = new Configurator();
        configurator.configure(config, context);*/
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ViewFactory factory = context.getBean(ConsoleViewFactory.class);
       /* DBConnection dbConnection = context.getObject(DBConnection.class);
        dbConnection.getConnection();*/
        factory.runApplication();
    }



}