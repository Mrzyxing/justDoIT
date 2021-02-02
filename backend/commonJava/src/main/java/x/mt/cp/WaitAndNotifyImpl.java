package x.mt.cp;

/**
 * Created by zyxing on 2019/2/18.
 * wait and notify(unfair always use notifyAll)需要配合一个lock object使用
 * 1. wait 会强制释放锁,配合synchronized可以确保不抛出异常
 * 2. 容易造成死锁(因为无临界区但是又是先有判断后 加减锁)
 */
public class WaitAndNotifyImpl implements 生产者消费者问题 {

    private static Integer count = 0;
    private static final Integer capacity = 10;
    private static Object LOCK = new Object();

    public static void main(String[] args) {
        WaitAndNotifyImpl test = new WaitAndNotifyImpl();
        for (int i = 0; i < 1; ++ i) {
            new Thread(test.new Producer()).start();
            new Thread(test.new Consumer()).start();
        }
    }

    class Producer implements Runnable {

        public void run() {
            for (int i = 0; i < 10; ++ i) {

                // random sleep from 1 to 3 seconds
                try {
                    long randomMilliSeconds = Math.round(Math.random() * 10000) % 3000 + 1000;
                    //                    System.out.println(Thread.currentThread().getName() + " produce,sleep " + randomMilliSeconds);
                    Thread.sleep(randomMilliSeconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // wait must with LOCK object
                synchronized (LOCK) {
                    // if out of capacity then lock this thread
                    while (count == capacity) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // produce
                    count++;
                    System.out.println(Thread.currentThread().getName() + " produce,current size is " + count);
                    // release LOCK
                    LOCK.notifyAll();
                }
            }
        }

    }

    class Consumer implements Runnable {

        public void run() {
            for (int i = 0; i < 10; ++ i) {
                try {
                    long randomMilliSeconds = Math.round(Math.random() * 10000) % 5000 + 1000;
                    //                    System.out.println(Thread.currentThread().getName() + " consume,sleep " + randomMilliSeconds);
                    Thread.sleep(randomMilliSeconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK) {
                    while (count == 0) {
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName() + " consume,current size is " + count);
                    LOCK.notifyAll();
                }
            }
        }
    }
}
