package x.mt.cp;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zyxing on 2019/2/18.
 *
 * lock配合condition可以将隐形锁synchronize关键字 变成显示锁
 */
public class ConditionImpl implements 生产者消费者问题 {

    private static Integer count = 0;
    private static final Integer CAPACITY = 10;

    private Lock LOCK = new ReentrantLock();
    private Condition notFull = LOCK.newCondition();
    private Condition notEmpty = LOCK.newCondition();

    public static void main(String[] args) {
        ConditionImpl test = new ConditionImpl();
        for (int i = 0; i < 1; ++ i) {
            new Thread(test.new Producer()).start();
            new Thread(test.new Consumer()).start();
        }
    }

    class Producer implements Runnable {

        public void run() {
            for (int i = 0; i < 10; i++) {
                LOCK.lock();
                try {
                    while (count == CAPACITY) {
                        try {
                            notFull.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    count++;
                    System.out.println(Thread.currentThread().getName() + " produce,current size is " + count);
                    notEmpty.signal();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }

    class Consumer implements Runnable {

        public void run() {
            for (int i = 0; i < 10; i++) {
                LOCK.lock();
                try {
                    while (count == 0) {
                        try {
                            notEmpty.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    count--;
                    System.out.println(Thread.currentThread().getName() + " consume,current size is " + count);
                    notFull.signal();
                } finally {
                    LOCK.unlock();
                }
            }
        }
    }
}
