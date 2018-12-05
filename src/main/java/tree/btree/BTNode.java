package tree.btree;

import vector.MyVector;

/**
 * B树节点
 *
 * @date 2018-12-05 11:33
 * @since 1.0.0
 */
public class BTNode<T> {
    /**
     * 父级对象
     */
    BTNode<T> parent;
    /**
     * 当前节点存放的数据
     */
    MyVector<T> key = new MyVector<>();
    /**
     * 所有的子集节点
     */
    MyVector<BTNode<T>> child = new MyVector<>();

    BTNode() {
        this.parent = null;
        child.insert(0, null);
    }

    BTNode(T t, BTNode<T> lc, BTNode<T> rc) {
        this.parent = null;
        key.insert(0, t);
        child.insert(0, lc);
        child.insert(1, rc);
        if (lc != null) {
            lc.parent = this;
        }
        if (rc != null) {
            rc.parent = this;
        }
    }
}
