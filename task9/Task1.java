package task9;

public class Task1 {
    public static void main(String[] args) throws InterruptedException {
        final Object object = new Object();
        Thread t = new Thread(() -> {
            try {
                synchronized (object) {

                    object.wait();

                    object.wait(300);

                    Thread.sleep(500);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Состояние NEW: " + t.getState());

        t.start();
        System.out.println("Состояние RUNNABLE: " + t.getState());
        Thread.sleep(200);
        System.out.println("Состояние WAITING: " + t.getState());
        synchronized (object) {
            object.notify();
            Thread.sleep(200);
            System.out.println("Состояние BLOCKED: " + t.getState());
        }

        Thread.sleep(200);
        System.out.println("Состояние TIMED_WAITING: " + t.getState());
        Thread.sleep(800);
        System.out.println("Состояние TEMINATED: " + t.getState());
    }
}
