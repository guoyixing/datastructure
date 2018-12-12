package tree.bintree;

import utils.Compare;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * 二叉树节点
 *
 * @date 2018-12-11 08:52
 * @since 1.0.0
 */
public class BinNode<T> {
    /**
     * 父节点，左子节点，右子节点
     */
    public BinNode<T> parent, lc, rc;
    /**
     * 存储的数据
     */
    public T data;
    /**
     * 高度
     */
    public int height;

    public BinNode() {
    }

    public BinNode(T data, BinNode<T> parent) {
        this.parent = parent;
        this.data = data;
    }

    public BinNode<T> insertAsLc(T t) {
        return lc = new BinNode<T>(t, this);
    }

    public BinNode<T> insertAsRc(T t) {
        return rc = new BinNode<T>(t, this);
    }

    /**
     * 计算子树规模
     * 其算法时间复杂度不过是字数所有常数个数下的子节点个数O(n)
     */
    public int size() {
        //先加上自身高度
        int s = 1;
        if (lc != null) {
            s += lc.size();
        }
        if (rc != null) {
            s += rc.size();
        }
        return s;
    }

    /**
     * 中序意义的直接后续
     */
    public BinNode<T> succ() {
        //保存后继中的临时节点
        BinNode<T> s = this;
        if (rc != null) {
            //如果有右子节点，后继必然在右子树中
            s = rc;
            //找到右子树中最左边的节点，就是直接后继
            while (s.lc != null) {
                s = s.lc;
            }
        } else {
            //判断是否存是父节点的右节点
            while (s.parent != null && s.parent.rc == s) {
                //向左往上爬
                s = s.parent;
            }
            //其后继就是为左子节点后的父节点
            s = s.parent;
        }
        return s;
    }

    /**
     * 层次遍历
     *
     * @param t java8的lambda操作
     */
    public void travLevel(Consumer<T> t) {
        Queue<BinNode<T>> q = new LinkedList<>();
        q.add(this);
        while (!q.isEmpty()) {
            BinNode<T> x = q.poll();
            t.accept(x.data);
            if (x.lc != null) {
                q.add(x.lc);
            }
            if (x.rc != null) {
                q.add(x.rc);
            }
        }
    }
}
