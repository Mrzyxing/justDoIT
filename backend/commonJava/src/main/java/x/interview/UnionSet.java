package x.interview;

/**
 * Created by zyxing on 2019/5/9.
 */
public class UnionSet {

    static int[] f = new int[10];

    public static int find(int a) {
        return a == f[a] ? a : (f[a] = find(f[a]));
    }

    public static void union(int a, int b) {
        int fa = find(a), fb = find(b);
        if (fa == fb) {
            return;
        }
        // lazy load
        f[fb] = fa;
    }

    public static void main(String[] args) {

    }
}
