package view;

import java.util.List;

public abstract class BaseView {




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