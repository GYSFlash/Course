package com.hotel;

import com.hotel.config.AppConfig;

import com.hotel.di.ObjectFactory;
import com.hotel.config.Config;
import com.hotel.config.Configurator;
import com.hotel.config.JavaConfig;
import com.hotel.controller.*;
import com.hotel.repository.DBConnection;
import com.hotel.view.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {


        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        /*ViewFactory factory = context.getBean(ViewFactory.class);
        factory.runApplication();*/
    }



}