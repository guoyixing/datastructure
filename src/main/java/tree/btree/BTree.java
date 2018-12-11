package tree.btree;

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

    /**
     * 上溢修复
     * 当节点的子集超过 规定的个数就会发生上溢
     * 节点的个数应当是磁盘单元空间/单条数据大小 并且不小于2
     *
     * @param v 需要处理上溢的节点
     */
    private void solveOverflow(BTNode<T> v) {
        if (this.order >= v.child.getSize()) {
            //递归基 停止继续往上层修复
            return;
        }
        //获取中间节点位置
        int s = order / 2;
        //创建新的分裂节点
        BTNode<T> u = new BTNode<>();
        //将做中间节点右侧的节点复制到 分裂出的新节点上  子节点永远比key要多出1 所以会剩下一个子节点
        for (int j = 0; j < this.order - s - 1; j++) {
            u.child.insert(j, v.child.remove(s + 1));
            u.key.insert(j, v.key.remove(s + 1));
        }
        //单独处理剩下的这个最右侧的子节点
        u.child.replace(this.order - s - 1, v.child.remove(s + 1));
        //如果子节点存在数值则 重新定位这些节点的父节点
        if (u.child.get(0) != null) {
            for (int j = 0; j < this.order - s; j++) {
                u.child.get(j).parent = u;
            }
        }
        //如果父节点为空 说明已经上溢到顶层 需要生成新的父节点
        BTNode<T> p = v.parent;
        if (p == null) {
            this.root = p = new BTNode<>();
            p.child.replace(0, v);
            v.parent = p;
        }
        int r = 1 + p.key.search(v.key.get(0));
        //将节点上移到新的根节点
        p.key.insert(r, v.key.remove(s));
        p.child.insert(r + 1, u);
        //建立关系
        u.parent = p;
        //递归上溢
        solveOverflow(p);
    }

    /**
     * 下溢修复
     * 当节点的子集达不到最小要求数目的时候 节点就向着父节点接一个节点会发生合并
     * 或者想左右兄弟节点借一个节点发生旋转
     *
     * @param v 需要处理下溢的节点
     */
    private void solveUnderflow(BTNode<T> v) {
        if ((this.order + 1) / 2 <= v.child.getSize()) {
            return;
        }
        BTNode<T> p = v.parent;
        //当父节点不存在的时候 也就是最顶层  说明树的层数减少了一层
        if (p == null) {
            this.root = v.child.get(0);
            return;
        }
        int r = 0;
        //获取节点所在位置
        while (!p.child.get(r).equals(v)) {
            r++;
        }
        if (0 < r) {
            //当节点不在最左边的时候  一定会有左兄弟
            BTNode<T> ls = p.child.get(r - 1);
            //判断左兄弟能否借出一个节点给他  如果可以变将借出的节点放置父节点的位置 父节点下移到当前节点的最左边  以此整棵树依旧保持中序意义上的顺序
            //顺时针旋转
            if ((this.order + 1) / 2 < ls.child.getSize()) {
                //将父节点的key转移过来
                v.key.insert(0, p.key.get(r - 1));
                //将父节点与借出节点交换
                p.key.replace(r - 1, ls.key.remove(ls.key.getSize() - 1));
                //将的借出节点的子节点转移到  需要修复的节点下
                v.child.insert(0, ls.child.remove(ls.child.getSize() - 1));
                //修改父节点连接
                if (v.child.get(0) != null) {
                    v.child.get(0).parent = v;
                }
                return;
            }
        }
        if (p.child.getSize() - 1 > r) {
            //当节点不在最右边的时候 一定存在右兄弟  与左兄弟的处理方式对称
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
            BTNode<T> rs = p.child.get(r + 1);
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

    /**
     * b树的搜索方法
     * 借用向量的搜索方法可以快速实现
     *
     * @param t 查询的节点
     */
    public BTNode<T> search(T t) {
        //从根节点出发
        BTNode<T> v = this.root;
        this.hot = null;
        //循环每个节点
        while (v != null) {
            //通过向量的搜索方法查询节点内容
            int r = v.key.search(t);
            //命中则返回节点
            if (0 <= r && t.equals(v.key.get(r))) {
                return v;
            }
            //并且将热点执行访问过的节点
            this.hot = v;
            //没就继续访问下一个子节点
            v = v.child.get(r + 1);
        }
        return null;
    }

    /**
     * 插入算法
     *
     * @param t 插入的节点
     */
    public boolean insert(T t) {
        //通过搜索算法 查询是否已经存在
        BTNode<T> v = search(t);
        if (v != null) {
            return false;
        }
        //找到之前访问热点的位置  便是最适合插入的位置
        int r = this.hot.key.search(t);
        //热点节点末端加入新节点
        this.hot.key.insert(r + 1, t);
        this.hot.child.insert(r + 2, null);
        this.size++;
        //判断是否上溢
        solveOverflow(this.hot);
        return true;
    }

    /**
     * 删除算法
     *
     * @param t 需要删除的节点
     */
    public boolean remove(T t) {
        //查找节点是否存在  不存在返回删除失败
        BTNode<T> v = search(t);
        if (v == null) {
            return false;
        }
        //存在则通过向量的查询算法 找到节点的地址
        int r = v.key.search(t);
        //如果v不是子节点
        if (v.child.get(0) != null) {
            //右子树一直左走
            BTNode<T> u = v.child.get(r + 1);
            //直到走到叶子节点上
            while (u.child.get(0) != null) {
                u = u.child.get(0);
            }
            //将v与获取到的子节点替换  u便是在v中最大的子节点
            v.key.replace(r, u.key.get(0));
            v = u;
            r = 0;
        }
        //此时v一定是底层 并且r就是要删除掉的节点
        v.key.remove(r);
        v.child.remove(r + 1);
        this.size--;
        //判断是否要下溢修复
        if (this.size != 0) {
            solveUnderflow(v);
        }
        return true;
    }
}
