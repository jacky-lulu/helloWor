package hello.wait_notify;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jacky on 2017/3/25 0025.
 */
public class Waint_Notify {
    
        public static void main(String args[])
        {
            ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
            int maxSize = 10;
            Thread producer = new Producer(queue, maxSize, "PRODUCER");
            Thread consumer = new Consumer(queue, maxSize, "CONSUMER");
            producer.start();
            consumer.start();

        }


   static class Producer extends Thread {
        private ConcurrentLinkedQueue queue;
        private int maxSize;

        public Producer(ConcurrentLinkedQueue queue, int maxSize, String name) {
            super(name);
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == maxSize) {
                        try {
                            System.out.println("队列中已经满了，等待消费者消费!");
                            queue.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.println("Producing value : " + i);
                    queue.add(i);
                    queue.notifyAll();
                }
            }
        }
    }

   static class Consumer extends Thread {
        private ConcurrentLinkedQueue queue;
        private int maxSize;

        public Consumer(ConcurrentLinkedQueue queue, int maxSize, String name) {
            super(name);
            this.queue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        System.out.println("队列已经空了,等待生产者放数据" );
                        try {
                            queue.wait();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.out.println("Consuming value : " + queue.remove());
                    queue.notifyAll();
                }
            }
        }
    }
}