package task9;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Task3 {

    public static void main(String[] args) {
        Factory factory = new Factory(3);
        Random random = new Random();

        Thread consumer = new Thread(() -> {
            while(true) {
                System.out.println("Consumed " + factory.consume());
            }
        });
        Thread producer = new Thread(() -> {
            while (true) {
                int number = random.nextInt(10);
                factory.produce(number);
                System.out.println("Produced " + number);
            }
        });
        producer.start();
        consumer.start();
    }

    static class Factory {
        private final ArrayBlockingQueue<Integer> buffer;
        public Factory(int maxSize) {
            buffer = new ArrayBlockingQueue<>(maxSize);
        }

        public void produce(int number) {
            try {
                buffer.put(number);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public int consume() {
            int number = 0;
            try {
                number = buffer.take();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return number;
        }
    }
}
