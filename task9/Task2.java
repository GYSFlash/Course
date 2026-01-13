package task9;

public class Task2 {
    public static final Object object = new Object();
    public static boolean T = true;
    public static void main(String[] args)  {

        Thread thread = new Thread(() -> { run(true);
                }, "Thread 1");
        Thread thread2 = new Thread(() -> { run(false);
        }, "Thread 2");
        thread.start();
        thread2.start();


        }
    public static void run(boolean t) {
        for (int i = 0; i < 10; i++) {
            synchronized (object) {
                while (T != t) {
                    try{
                    object.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println(Thread.currentThread().getName());
                T = !T;
                object.notify();
            }

        }
    }
}
