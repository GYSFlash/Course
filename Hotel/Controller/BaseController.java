package Hotel.Controller;

import Hotel.View.BaseView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class BaseController {
    private BaseView view;
    private Scanner scanner;

    public BaseController() {
        scanner = new Scanner(System.in);

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
    public Date parseDate(String dateStr) {

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.parse(dateStr);
        } catch (Exception e) {
            view.showError("Неверный формат даты. Используется текущая дата");
            return new Date();
        }
    }
    public Object getObject() {
        return Object.class;
    }
    public Scanner getScanner() {
        return scanner;
    }
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    public BaseView getView() {
        return view;
    }
    public void setView(BaseView view) {
        this.view = view;
    }
    public abstract boolean run(int choice);
}
