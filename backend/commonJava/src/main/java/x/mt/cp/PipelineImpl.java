package x.mt.cp;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by zyxing on 2019/2/19.
 */
public class PipelineImpl implements 生产者消费者问题 {

    final PipedInputStream PIS = new PipedInputStream();
    final PipedOutputStream POS = new PipedOutputStream();

    {
        try {
            PIS.connect(POS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PipelineImpl test = new PipelineImpl();
        for (int i = 0; i < 2; ++ i) {
            new Thread(test.new Producer()).start();
            new Thread(test.new Consumer()).start();
        }
    }

    class Producer implements Runnable {

        public void run() {
            try (PipedInputStream pis = PIS; PipedOutputStream pos = POS) {
                while (true) {
                    Thread.sleep(2500L);

                    long randomNum = Math.round(Math.random() * 10000) % 100;
                    System.out.println(Thread.currentThread().getName() + " produce " + randomNum);
                    pos.write((int) randomNum);
                    pos.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            try (PipedInputStream pis = PIS; PipedOutputStream pos = POS) {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " consume " + pis.read());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
