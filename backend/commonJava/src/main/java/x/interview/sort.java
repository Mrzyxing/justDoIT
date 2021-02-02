package x.interview;

import com.alibaba.fastjson.JSON;

/**
 * Created by zyxing on 2019/5/4.
 */
public class sort {

    public static void main(String[] args) {
        int[] arr = new int[]{1, 9, 4, 5, 7, 6, 2, 4, 8};
        //        selectSort(arr);
        quickSort(arr, 0, arr.length - 1);
        System.out.println(JSON.toJSONString(arr));

        int[] test = new int[]{1, 2, 1};
        swap(test, 0, 2);
        System.out.println(JSON.toJSONString(test));
    }

    /**
     * 冒泡如名字 大的数字会像泡泡一样从下面一步一步冒上来
     * 步骤最简单 每次比较后面一位 如果大于则交换一下顺序
     * 时间复杂度 O(n^2)
     * 空间复杂度 O(1)
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; ++ i) {
            for (int j = 0; j < arr.length - i - 1; ++ j) {
                if (arr[j + 1] < arr[j]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * 冒泡的优化之一就是如果某一次冒泡过程中没有冒泡过了其实就已经完全有序了
     * 还有一个优化是记录上次发生交换的位置 这个位置后面的显然已经有序了(因为没有发生过交换)
     */
    public static void bubbleSortOptimized(int[] arr) {
        boolean flag;
        int k = arr.length - 1;
        int last = k;
        for (int i = 0; i < arr.length; ++ i) {
            flag = true;
            for (int j = 0; j < last; ++ j) {
                if (arr[j + 1] < arr[j]) {
                    flag = false;
                    swap(arr, j, j + 1);
                    // 直接在这里记会导致k立即更新
                    k = j + 1;
                }
            }
            if (flag) {
                break;
            }
            last = k;
        }
    }

    /**
     * 简化版的冒泡:每次从序列中选择最大的数放到已经有序的序列中
     */
    public static void selectSort(int[] arr) {
        for (int i = arr.length - 1; i >= 0; -- i) {
            int max = i;
            for (int j = 0; j < i; ++ j) {
                if (arr[j] > arr[max]) {
                    max = j;
                }
            }
            swap(arr, max, i);
        }
    }

    /**
     * 简化版的选择排序:选择排序维护序列是全局有序(因为每次加入的一定全局最值)
     * 插入排序只是局部有序 每次吞掉最近的一个数需要看情况并入已有序列
     */
    public static void insertSort(int[] arr) {

    }

    /**
     * 每次选择一个步长 重新构建序列进行插入排序 直到步长为1 进行全数组的插入排序
     * 时间复杂度和步长的选择强相关
     */
    public static void shellSort(int[] arr) {

    }

    /**
     * 分治合并 注意每次需要新生成数组然后合并或者flush到相应序列中 空间复杂度
     */
    public static void mergeSort(int[] arr, int l, int r) {

    }

    /**
     * 快排每次选一个数(通常是第一个) 比这个数大的放到右边 小的放左边
     * 快排也是分治 但是分治的同时利用了上次排序的结果,即左边的永远不需要和右边的再比一次了
     */
    public static void quickSort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int pl = l, pr = r;
        int base = l;
        while (pl < pr) {
            // 先找左还是右有讲究
            // 如果左边第一个为base 防止找不到指针能移到自己身上只能从右向左移动 所以要先找右边的
            // 找到一个比base小的但是在右边
            while (pl < pr && arr[pr] >= arr[base]) {
                -- pr;
            }
            // 找到一个比base大的但是在左边的
            // 注意相等也要移动
            while (pl < pr && arr[pl] <= arr[base]) {
                ++ pl;
            }
            swap(arr, pl, pr);
        }
        // 调整base的位
        swap(arr, base, pl);
        quickSort(arr, l, pl - 1);
        quickSort(arr, pr + 1, r);
    }

    private static void swap(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        arr[l] ^= arr[r];
        arr[r] = arr[l] ^ arr[r];
        arr[l] ^= arr[r];
    }

    /**
     * 树结构 求解TOP N
     * 一般树结构麻烦在维护结构 优势在读取
     * 所以读多写少的情况或者对读要求很高优先考虑树结构
     * 如mysql的索引(红黑树)和java的hashmap对key值的存储(数组+链表实现 只有链表即冲突大于8时用红黑树)
     */
    public static void heapSort(int[] arr) {

    }
}
