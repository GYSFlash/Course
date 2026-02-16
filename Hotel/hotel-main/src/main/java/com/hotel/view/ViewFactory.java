package com.hotel.view;


import com.hotel.annotations.InjectByType;
import com.hotel.config.Config;
import com.hotel.controller.FileController;
import com.hotel.di.ApplicationContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.Scanner;

public abstract class ViewFactory {


    public static int readInt(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
    public abstract void runApplication();
}
