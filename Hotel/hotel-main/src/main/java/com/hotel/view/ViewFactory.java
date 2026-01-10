package com.hotel.view;


import com.hotel.annotations.InjectByType;
import com.hotel.config.Config;
import com.hotel.controller.FileController;
import com.hotel.di.ApplicationContext;

import java.util.Scanner;

public abstract class ViewFactory {

    protected ApplicationContext context;

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public static ViewFactory getFactory(ApplicationContext context) {
        Config config = context.getObject(Config.class);
        String type = config.getViewType();
        ViewFactory factory;
        switch (type) {
            case "console" -> factory = context.getObject(ConsoleViewFactory.class);
            case "mconsole" -> factory = context.getObject(MiniConsoleViewFactory.class);
            default ->
                throw new IllegalArgumentException("Unknown factory: " + type);
        };
        factory.setContext(context);
        return factory;
    }
    public static int readInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
    public abstract void runApplication();
}
