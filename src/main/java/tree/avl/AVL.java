package tree.avl;

import tree.binsearchtree.BinSearchTree;
import tree.bintree.BinNode;

/**
 * AVL树
 *
 * @date 2018-12-13 08:43
 * @since 1.0.0
 */
public class AVL<T> extends BinSearchTree<T> {
    public boolean balanced(BinNode<T> x) {
        return stature(x.lc) == stature(x.rc);
    }

    public int balFac(BinNode<T> x) {
        return stature(x.lc) - stature(x.rc);
    }

    public boolean AvlBalanced(BinNode<T> x) {
        return (-2 < balFac(x)) && (balFac(x) < 2);
    }

    public BinNode<T> tallerChild(BinNode<T> x) {
        return stature(x.lc) > stature(x.rc) ?
                x.lc : (stature(x.rc) > stature(x.lc) ?
                x.rc : (super.isLChild(x) ? x.lc : x.rc));
    }

    /**
     * 插入算法
     *
     * @param t 插入的元素
     */
    @Override
    public BinNode<T> insert(T t) {
        //判断t是否存在
        BinNode<T> x = search(t);
        if (x != null) {
            return x;
        }
        BinNode<T> xx = x = new BinNode<>(t, hot);
        size++;
        //插入新元素后x的父节点增高，父祖父则有可能失衡，向上判断各祖父是否失衡
        for (BinNode<T> g = hot; g != null; g = g.parent) {
            if (!AvlBalanced(g)) {
                //如果失衡则修复
                super.fromParentTo(g).replace(rotateAt(tallerChild(tallerChild(g))));
                break;
            } else {
                //没有就更新高度
                updateHeight(g);
            }
        }
        return xx;
    }

    /**
     * 删除算法
     *
     * @param t 删除的元素
     */
    @Override
    public boolean remove(T t) {
        BinNode<T> x = search(t);
        if (x == null) {
            return false;
        }
        for (BinNode<T> g = hot; g != null; g = g.parent) {
            if (!AvlBalanced(g)) {
                //如果失衡则修复
                g = super.fromParentTo(g).replace(rotateAt(tallerChild(tallerChild(g))));
            }
            //同时更新树高
            updateHeight(g);
        }
        return true;
    }

    /**
     * 自平衡实现
     *
     * @param v 需要平衡的节点
     */
    private BinNode<T> rotateAt(BinNode<T> v) {
        BinNode<T> p = v.parent, g = p.parent;
        if (isLChild(p)) {
            //zig
            if (isLChild(v)) {
                //zig-zag
                p.parent = g.parent;
                return connect34(v, p, g, v.lc, v.lc, p.rc, g.rc);
            } else {
                //zig-zig
                v.parent = g.parent;
                return connect34(p, v, g, p.lc, v.lc, v.rc, g.rc);
            }
        } else {
            //zag
            if (isRChild(v)) {
                //zag-zag
                p.parent = g.parent;
                return connect34(g, p, v, g.lc, p.lc, v.lc, v.rc);
            } else {
                //zag-zig
                v.parent = g.parent;
                return connect34(g, v, p, g.lc, v.lc, v.rc, p.rc);
            }
        }
    }


    /**
     * 3+4重构算法（zag,zig的实现）
     */
    public BinNode<T> connect34(BinNode<T> a, BinNode<T> b, BinNode<T> c
            , BinNode<T> t0, BinNode<T> t1, BinNode<T> t2, BinNode<T> t3) {
        a.lc = t0;
        if (t0 != null) {
            t0.parent = a;
        }
        a.rc = t1;
        if (t1 != null) {
            t1.parent = a;
        }
        updateHeight(a);
        c.lc = t2;
        if (t2 != null) {
            t2.parent = c;
        }
        c.rc = t3;
        if (t3 != null) {
            t3.parent = c;
        }
        updateHeight(c);
        b.lc = a;
        a.parent = b;
        b.rc = c;
        c.parent = b;
        updateHeight(b);
        return b;
    }
}
