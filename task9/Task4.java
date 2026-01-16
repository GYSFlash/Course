package task9;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Task4 {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static void main(String[] args) throws InterruptedException {

        Scanner in = new Scanner(System.in);
        System.out.println("Введите промежуток времени в секундах: ");
        int second = in.nextInt();
        System.out.println("Введите общее время работы программы в секундах: ");
        int total = in.nextInt();
        DemonThread demon = new DemonThread(second);
        demon.start();

        Thread.sleep(total * 1000);
    }
    static class DemonThread extends Thread {
        int second;
        DemonThread(int second) {
            this.second = second;
            this.setDaemon(true);

        }
        @Override
        public void run() {
                while (true) {
                    System.out.println(LocalDateTime.now().format(formatter));
                    try{
                        Thread.sleep(second * 1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        }

    }
}
