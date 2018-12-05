package tree.btree;

import java.util.ArrayList;
import java.util.List;

/**
 * b树
 *
 * @date 2018-12-05 12:17
 * @since 1.0.0
 */
public class BTree<T> {
    /**
     * 关键码总数
     */
    protected int size;
    /**
     * 阶次
     */
    protected int order = 4;
    /**
     * 根节点
     */
    protected BTNode<T> root = new BTNode<>();
    /**
     * 最后访问节点
     */
    protected BTNode<T> hot;

    private void solveOverflow(BTNode<T> v) {
        if (this.order >= v.child.getSize()) {
            //递归基 停止继续往上层修复
            return;
        }
        int s = order / 2;
        BTNode<T> u = new BTNode<>();
        for (int j = 0; j < this.order - s - 1; j++) {
            u.child.insert(j, v.child.remove(s + 1));
            u.key.insert(j, v.key.remove(s + 1));
        }

        u.child.replace(this.order - s - 1, v.child.remove(s + 1));
        if (u.child.get(0) != null) {
            for (int j = 0; j < this.order - s; j++) {
                u.child.get(j).parent = u;
            }
        }
        BTNode<T> p = v.parent;
        if (p == null) {
            this.root = p = new BTNode<>();
            p.child.replace(0, v);
            v.parent = p;
        }
        int r = 1 + p.key.search(v.key.get(0));
        p.key.insert(r, v.key.remove(s));
        p.child.insert(r + 1, u);
        u.parent = p;
        solveOverflow(p);
    }

    private void solveUnderflow(BTNode<T> v) {
        if ((this.order + 1) / 2 <= v.child.getSize()) {
            return;
        }
        BTNode<T> p = v.parent;
        if (p == null) {
            return;
        }
        int r = 0;
        while (!p.child.get(r).equals(v)) {
            r++;
        }
        if (0 < r) {
            BTNode<T> ls = p.child.get(r - 1);
            if ((this.order + 1) / 2 < ls.child.getSize()) {
                v.key.insert(0, p.key.get(r - 1));
                p.key.replace(r - 1, ls.key.remove(ls.key.getSize() - 1));
                v.child.insert(0, ls.child.remove(ls.child.getSize() - 1));
                if (v.child.get(0) != null) {
                    v.child.get(0).parent = v;
                }
                return;
            }
        }
        if (p.child.getSize() - 1 > r) {
            BTNode<T> rs = p.child.get(r);
            if ((this.order + 1) / 2 < rs.child.getSize()) {
                v.key.insert(v.key.getSize(), p.key.get(r));
                p.key.replace(r, rs.key.remove(0));
                v.child.insert(v.child.getSize(), rs.child.remove(0));
                if (v.child.get(v.child.getSize() - 1) != null) {
                    v.child.get(v.child.getSize() - 1).parent = v;
                }
                return;
            }
        }
        if (0 < r) {
            //获取左边节点的孩子
            BTNode<T> ls = p.child.get(r - 1);
            //将需要修复的对象的父节点移到 父节点左边节点的孩子的最后的位置上
            ls.key.insert(ls.key.getSize(), p.key.remove(r - 1));
            //v已经不是p的孩子  这里删除r位置上的元素便是v
            p.child.remove(r);
            //将需要修复的节点的首位  移至左节点的孩子的末尾
            ls.child.insert(ls.child.getSize(), v.child.remove(0));
            //如果这个孩子不是一个空的
            if (ls.child.get(ls.child.getSize() - 1) != null) {
                //将这个孩子的父对象 指向当前的左节点
                ls.child.get(ls.child.getSize() - 1).parent = ls;
            }
            //如果v中依旧有元素存在
            while (v.key.getSize() != 0) {
                //继续吧元素逐一的移到左节点的末尾
                ls.key.insert(ls.key.getSize(), v.key.remove(0));
                ls.child.insert(ls.child.getSize(), v.child.remove(0));
                if (ls.child.get(ls.child.getSize() - 1) != null) {
                    ls.child.get(ls.child.getSize() - 1).parent = ls;
                }
            }
        } else {
            //与之前的处理完全对称
            BTNode<T> rs = p.child.get(r+1);
            rs.key.insert(0, p.key.remove(0));
            p.child.remove(r);
            rs.child.insert(0, v.child.remove(r));
            if (rs.child.get(0) != null) {
                rs.child.get(0).parent = rs;
            }
            while (v.key.getSize() != 0) {
                rs.key.insert(0, v.key.remove(r));
                rs.child.insert(0, v.child.remove(r));
                if (rs.child.get(0) != null) {
                    rs.child.get(0).parent = rs;
                }
            }
        }
        solveUnderflow(p);
        return;
    }

    public BTNode<T> search(T t) {
        BTNode<T> v = this.root;
        this.hot = null;
        while (v != null) {
            int r = v.key.search(t);
            if (0 <= r && t.equals(v.key.get(r))) {
                return v;
            }
            this.hot = v;
            v = v.child.get(r + 1);
        }
        return null;
    }

    public boolean insert(T t) {
        BTNode<T> v = search(t);
        if (v != null) {
            return false;
        }
        int r = this.hot.key.search(t);
        this.hot.key.insert(r + 1, t);
        this.hot.child.insert(r + 2, null);
        this.size++;
        solveOverflow(this.hot);
        return true;
    }

    public boolean remove(T t) {
        BTNode<T> v = search(t);
        if (v == null) {
            return false;
        }
        int r = v.key.search(t);
        if (v.child.get(0) != null) {
            BTNode<T> u = v.child.get(r + 1);
            while (u.child.get(0) != null) {
                u = u.child.get(0);
            }
            v.key.replace(r, u.key.get(0));
            v = u;
            r = 0;
        }
        v.key.remove(r);
        v.child.remove(r + 1);
        solveUnderflow(v);
        return true;
    }
}
