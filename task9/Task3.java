package task9;

import java.util.*;

public class Task3 {

    public static void main(String[] args) throws InterruptedException {
        Factory factory = new Factory(3);
        Random random = new Random();
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {

                System.out.println("Consumed " + factory.consume());
            }
        });
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                int number = random.nextInt(10);
                factory.produce(number);
                System.out.println("Produced " + number);
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }

    static class Factory {
        public int maxSize;
        public Deque<Integer> buffer = new ArrayDeque<>();
        public Factory(int maxSize) {
            this.maxSize = maxSize;
        }

        public synchronized void produce(int number) {
            while(buffer.size() == maxSize) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
                buffer.add(number);
                notifyAll();

        }

        public synchronized int consume() {
            while (buffer.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
                int number = buffer.remove();
                notifyAll();
                return number;
        }
    }
}
