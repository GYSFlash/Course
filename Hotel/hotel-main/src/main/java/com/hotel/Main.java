package com.hotel;

import com.hotel.di.ApplicationContext;
import com.hotel.di.ObjectFactory;
import com.hotel.config.Config;
import com.hotel.config.Configurator;
import com.hotel.config.JavaConfig;
import com.hotel.controller.*;
import com.hotel.view.*;


public class Main {
    public static void main(String[] args) {

        JavaConfig javaConfig = new JavaConfig("com.hotel");
        ApplicationContext context = new ApplicationContext(javaConfig);
        new ObjectFactory(context);
        Config config = context.getObject(Config.class);
        new Configurator().configure(config, context);
        ViewFactory factory = ViewFactory.getFactory(context);

        FileController fileController = context.getObject(FileController.class);

        fileController.loadAll();

        factory.runApplication();
    }



}