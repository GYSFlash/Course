package Hotel.View;

import Hotel.Controller.BaseController;

import java.util.List;
import java.util.Scanner;

public abstract class BaseView {

    private BaseController controller;

    public void setController(BaseController controller) {
        this.controller = controller;
    }



    public void showList(String title, List<?> items) {
        System.out.println("\n=== " + title + " ===");
        if (items == null || items.isEmpty()) {
            System.out.println("Пусто");
        } else {
            items.forEach(System.out::println);
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String error) {
        System.out.println(error);
    }
    public void showObject(Object object) {
        object.toString();
    }

    public abstract void showMenu();
    public abstract boolean processOperation(int choice);

}