package x.mt.cp;

import java.util.concurrent.Semaphore;

/**
 * Created by zyxing on 2019/2/19.
 */
public class SemaphoreImpl implements 生产者消费者问题 {

    private static Integer count = 0;
    Semaphore notFull = new Semaphore(10);
    Semaphore notEmpty = new Semaphore(0);
    Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) {
        SemaphoreImpl test = new SemaphoreImpl();
        for (int i = 0; i < 1; ++ i) {
            new Thread(test.new Producer()).start();
            new Thread(test.new Consumer()).start();
        }
    }

    class Producer implements Runnable {

        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    // notFull 一定要在 mutex前面,如果先拿互斥锁但是notFull一开始需要等待会导致互斥锁无法释放 即死锁
                    // AB锁的顺序主要看释放A锁的操作会不会被B阻塞，或者保证mutex area是原子操作即可(不会有阻塞)
                    // 这里如果A=mutex B=notFull,A的释放会被B阻塞
                    // 但是如果A=notFull B=mutex,A的释放只受另外一个线程影响但是并不会阻塞
                    notFull.acquire();
                    mutex.acquire();

                    count++;
                    System.out.println(Thread.currentThread().getName() + " produce,current size is " + count);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 注意按顺序释放
                    mutex.release();
                    notEmpty.release();
                }
            }
        }
    }

    class Consumer implements Runnable {

        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    notEmpty.acquire();
                    mutex.acquire();

                    count--;
                    System.out.println(Thread.currentThread().getName() + " consume,current size is " + count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    mutex.release();
                    notFull.release();
                }
            }
        }
    }
}
