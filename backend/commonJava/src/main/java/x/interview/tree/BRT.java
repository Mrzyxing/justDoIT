package x.interview.tree;

import com.google.common.collect.Maps;
import java.util.TreeMap;

/**
 * Created by zyxing on 2019/5/6.
 * 1
 * 2 root and 3 leaf(always is null node) is black
 * 4 red both children is black
 * 5 all simple paths from the node to descendant leaves contain the same number of black nodes.
 */
public class BRT {

    public static void main(String[] args) {
        TreeMap a = Maps.newTreeMap();
    }

    public static void rotateLeft(Entry p) {
        Entry pr = p.right;
        Entry pp = p.parent;

        // 1. p认新儿子 因为p的小儿子(右结点)已经记下来了 所以p和小儿子的关系可以断掉了
        // 1.1 大儿子(左结点)不变
        // 1.2 重新认个小儿子
        p.right = pr.left;
        if (pr.left != null) {
            // 1.2.1 新儿子 pr.left 认新爸爸
            pr.left.parent = p;
        }

        // 2. pr还认p为父亲 要改
        // 注意这里由于已经记录了p的爸爸所以可以直接断掉和pr的关系 否则需要先更新掉p的爸爸认新儿子
        // 要不没办法通过p找到p的爸爸了(因为p要认pr做爸爸了)
        // 2.1 pr自己当爸爸了 认新儿子 p
        pr.left = p;
        // 2.2 新儿子 p 认新父亲
        p.parent = pr;

        // 2 p认了新爸爸 老爸爸也要认新儿子
        // 2.1
        pr.parent = pp;
        // 2.1 每次变更需要两次 p.parent 认新儿子
        if (pp == null) {
            // 2.1.1 原p节点的父节点是root
        } else if (pp.left == p) {
            // 2.1.2 原p节点是父节点的左结点
            pp.left = pr;
        } else {
            // 2.1.3 原p节点是父节点的右结点
            pp.right = pr;
        }
    }

    public static void rotateRight(Entry p) {
        Entry pl = p.left;
        p.left = pl.right;
        if (pl.right != null) {
            pl.right.parent = p;
        }
        // 这里不记p的爸爸 只能先更新p爸爸认pl做了儿子后才能让p认新爸爸
        pl.parent = p.parent;
        if (p.parent == null) {

        } else if (p.parent.left == p) {
            p.parent.left = pl;
        } else {
            p.parent.right = pl;
        }

        // p要认新爸爸
        p.parent = pl;
        pl.right = p;

    }

    private static Entry root;

    /**
     * 假设新入结点为 N 父节点为 P 爷爷结点为 G 叔叔结点为 U
     *
     * 1. N 直接红色可以避免保证规则(5) 因为黑色节点数肯定还是一样的
     * 2.1 此时 P 有两种情况(1) 黑色不用考虑 直接就能插入
     * 2.2 红色的话 G 肯定是黑色的(1,4) 但是叔叔两种情况都可以
     * 2.2.1 U是红色的
     * P和U变黑 G变红 并以G为新加入结点递归调用 直至不为红
     * 2.2.2 U是黑色的
     * 2.2.2.1 N是P大儿子(左结点) P 右旋后 P 黑 G 红 可以平衡
     * 2.2.2.2 N是P小儿子 P 先左旋 然后和2.2.2.1一样操作
     */
    public static void fixAfterInsert(Entry n) {
        // 新入结点直接红色只需要保证 4 即可
        n.color = Color.RED;

        while (n != null && n != root && n.parent.color != Color.BLACK) {
            if (parentOf(n) == leftOf(grandParentOf(n))) {
                // P是大儿子
                Entry u = rightOf(grandParentOf(n));
                if (u.color == Color.RED) {
                    // 2.2.1
                    n.parent.color = Color.BLACK;
                    u.color = Color.BLACK;
                    grandParentOf(n).color = Color.RED;
                    n = grandParentOf(n);
                } else {
                    // 2.2.2
                    if (n == rightOf(n.parent)) {
                        // 2.2.2.2
                        // 需要重新记录n
                        n = parentOf(n);
                        rotateLeft(n);
                    }
                    // 2.2.2.1
                    n.parent.color = Color.BLACK;
                    grandParentOf(n).color = Color.RED;
                    rotateRight(parentOf(n));
                }
            } else {
                // P是小儿子
                Entry u = leftOf(grandParentOf(n));
                if (u.color == Color.RED) {
                    parentOf(n).color = Color.BLACK;
                    u.color = Color.BLACK;
                    grandParentOf(n).color = Color.RED;
                    n = grandParentOf(n);
                } else {
                    if (n == leftOf(n)) {
                        rotateRight(parentOf(n));
                    }
                    parentOf(n).color = Color.BLACK;
                    grandParentOf(n).color = Color.RED;
                    rotateLeft(grandParentOf(n));
                }
            }
        }
        // root.color = BLACK
    }


    private static Entry leftOf(Entry n) {
        return n.left;
    }

    private static Entry rightOf(Entry n) {
        return n.right;
    }

    private static Entry grandParentOf(Entry n) {
        return n.parent.parent;
    }

    private static Entry parentOf(Entry n) {
        return n.parent;
    }

    class Entry {

        String key;
        Object val;
        Color color;
        Entry parent;
        Entry left;
        Entry right;
    }

    enum Color {
        RED,
        BLACK
    }
}
