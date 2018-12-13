package tree.binsearchtree;

import tree.bintree.BinNode;
import tree.bintree.BinTree;
import utils.Compare;

/**
 * 二叉搜索树
 *
 * @date 2018-12-12 10:10
 * @since 1.0.0
 */
public class BinSearchTree<T> extends BinTree<T> {
    /**
     * 热点，命中节点的父类
     */
    public BinNode<T> hot;

    /**
     * 搜索算法
     *
     * @param t 搜索的节点
     */
    public BinNode<T> search(T t) {
        return searchIn(root, t, hot = null);
    }

    private BinNode<T> searchIn(BinNode<T> v, T t, BinNode<T> hot) {
        if (v == null || t.equals(v.data)) {
            return v;
        }
        hot = v;
        return searchIn(Compare.less(t, v.data) ? v.lc : v.rc, t, hot);
    }

    /**
     * 插入算法
     *
     * @param t 插入的元素
     */
    public BinNode<T> insert(T t) {
        //查询t是否存在
        BinNode<T> x = search(t);
        if (x == null) {
            //插入t，以hot为父节点
            x = new BinNode<>(t, hot);
            if (Compare.less(x.data, hot.data)) {
                hot.lc = x;
            } else {
                hot.rc = x;
            }
            size++;
            updateHeightAbove(x);
        }
        return x;
    }

    /**
     * 删除算法
     *
     * @param t 删除的元素
     */
    public boolean remove(T t) {
        BinNode<T> x = search(t);
        if (x == null) {
            return false;
        }
        removeAt(x, hot);
        size--;
        updateHeightAbove(hot);
        return true;
    }

    /**
     * 在指定节点处删除
     *
     * @param x   删除的元素
     * @param hot 父节点
     */
    private BinNode<T> removeAt(BinNode<T> x, BinNode<T> hot) {
        BinNode<T> w = x;
        BinNode<T> succ;
        if (x.lc == null) {
            //没有左子节点的时候
            succ = x = x.rc;
        } else if (x.rc == null) {
            //没有右子节点的时候
            succ = x = x.lc;
        } else {
            //两个节点都有的时候
            w = w.succ();
            //交换w和x的数据
            T data = x.data;
            x.data = w.data;
            w.data = data;
            BinNode<T> u = w.parent;
            if (u == x) {
                u.rc = succ = w.rc;
            } else {
                u.lc = succ = w.rc;
            }
        }
        hot = w.parent;
        if (succ != null) {
            succ.parent = hot;
        }
        return hot;
    }
}
