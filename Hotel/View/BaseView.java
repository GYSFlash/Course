// src/Hotel/View/BaseView.java
package Hotel.View;

import java.util.List;
import java.util.Scanner;

public abstract class BaseView {
    private Scanner scanner;

    public BaseView() {
        this.scanner = new Scanner(System.in);
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

    public int readInt(String value) {
        System.out.print(value + ": ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public long readLong(String value) {
        System.out.print(value + ": ");
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public String readString(String value) {
        System.out.print(value + ": ");
        return scanner.nextLine();
    }

    public double readDouble(String value) {
        System.out.print(value + ": ");
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public Scanner getScanner() {
        return scanner;
    }

    public abstract void showMenu();
    public abstract boolean processOperation(int choice);
}