package x.lang;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * Created by zyxing on 2019/2/25.
 */
public class 可变长度参数 {

    public static void main(String[] args) {
        print(1, 2, 4);
        List list = Lists.newArrayList();
        list.add(1);
        int[] arr = new int[2];
        arr[0] = 1;
        print(arr);

    }

    public static void print(int... args) {
        for (int arg : args) {
            System.out.println(arg);
        }
    }

}
