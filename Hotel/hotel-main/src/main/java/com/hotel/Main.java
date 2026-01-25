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
        ObjectFactory objectFactory = new ObjectFactory(context);
        context.setFactory(objectFactory);
        Config config = context.getObject(Config.class);
        Configurator configurator = new Configurator();
        configurator.configure(config, context);
        ViewFactory factory = ViewFactory.getFactory(context);



        factory.runApplication();
    }



}