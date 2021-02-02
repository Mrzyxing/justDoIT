package x.mt.cp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zyxing on 2019/2/18.
 * array blocking queue 其实就是用的condition+reentrantLock实现的
 */
public class BlockingQueueImpl implements 生产者消费者问题 {

    private static Integer count = 0;
    BlockingQueue blockingQueue = new ArrayBlockingQueue(10);

    public static void main(String[] args) {
        BlockingQueueImpl test = new BlockingQueueImpl();
        for (int i = 0; i < 1; ++ i) {
            new Thread(test.new Producer()).start();
            new Thread(test.new Consumer()).start();
        }
    }

    class Producer implements Runnable {

        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    // put 会阻塞当前线程
                    blockingQueue.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                System.out.println(Thread.currentThread().getName() + " produce,current size is " + count);
            }
        }
    }

    class Consumer implements Runnable {

        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    blockingQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count--;
                System.out.println(Thread.currentThread().getName() + " consume,current size is " + count);
            }
        }
    }
}
