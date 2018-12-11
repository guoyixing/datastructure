package tree.bintree;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * 二叉树
 *
 * @date 2018-12-11 09:07
 * @since 1.0.0
 */
public class BinTree<T> {
    /**
     * 规模
     */
    protected int size;
    /**
     * 根节点
     */
    protected BinNode<T> root;

    public int size() {
        return size;
    }

    public boolean empty() {
        return root == null;
    }

    public BinNode<T> root() {
        return root;
    }

    /**
     * 更新当前节点的树高
     *
     * @param x 操作节点
     */
    public int updateHeight(BinNode<T> x) {
        return x.height = 1 + Math.max(stature(x.lc), stature(x.rc));
    }

    public int stature(BinNode<T> t) {
        return t != null ? t.height : -1;
    }

    /**
     * 更新当前节点的所有父类节点的树高
     *
     * @param x 操作节点
     */
    public void updateHeightAbove(BinNode<T> x) {
        while (x != null) {
            updateHeight(x);
            x = x.parent;
        }
    }

    /**
     * 插入右子节点
     *
     * @param x 操作节点
     * @param t 作为右子节点的插入参数
     */
    public BinNode<T> insertAsRc(BinNode<T> x, T t) {
        size++;
        x.insertAsRc(t);
        updateHeightAbove(x);
        return x.rc;
    }

    /**
     * 插入左子节点
     *
     * @param x 操作节点
     * @param t 作为左子节点的插入参数
     */
    public BinNode<T> insertAsLc(BinNode<T> x, T t) {
        size++;
        x.insertAsRc(t);
        updateHeightAbove(x);
        return x.lc;
    }

    /**
     * 右子树接入
     */
    public BinNode<T> attachAsRc(BinNode<T> x, BinTree<T> s) {
        if ((x.rc = s.root) != null) {
            x.rc.parent = x;
        }
        size += s.size;
        updateHeightAbove(x);
        s = null;
        return x;
    }

    /**
     * 左子树接入
     */
    public BinNode<T> attachAsLc(BinNode<T> x, BinTree<T> s) {
        if ((x.lc = s.root) != null) {
            x.lc.parent = x;
        }
        size += s.size;
        updateHeightAbove(x);
        s = null;
        return x;
    }

    /**
     * 删除子树
     *
     * @param x 删除子树的根
     */
    public int remove(BinNode<T> x) {
        fromParentTo(x);
        int n = removeAt(x);
        size -= n;
        return n;
    }

    /**
     * 清除和父节点的关联
     *
     * @param x 操作的节点
     */
    private void fromParentTo(BinNode<T> x) {
        if (x.parent != null && x.parent.lc == x) {
            x.parent.lc = null;
        }
        if (x.parent != null && x.parent.rc == x) {
            x.parent.rc = null;
        }
        x.parent = null;
    }

    /**
     * 删除子树的所有节点
     *
     * @param x 删除的子树根
     */
    private int removeAt(BinNode<T> x) {
        if (x == null) {
            return 0;
        }
        //删除的数量等于左子树删除的数量+右子树删除的数量
        int n = 1 + removeAt(x.lc) + removeAt(x.rc);
        //清除数据
        x.data = null;
        //清除节点
        x = null;
        return n;
    }

    /**
     * 分离子树
     *
     * @param x 分离的子树根
     */
    public BinTree<T> secede(BinNode<T> x) {
        //删除父节点关系
        fromParentTo(x);
        updateHeightAbove(x);
        //创建空树
        BinTree<T> s = new BinTree<>();
        //新树的根
        s.root = x;
        x.parent = null;
        s.size = x.size();
        size -= s.size;
        return s;
    }

    /**
     * 先序遍历（递归）
     *
     * @param x 遍历的树的树根
     * @param t java8的lambda操作
     */
    public void traverseDLR(BinNode<T> x, Consumer<T> t) {
        if (x == null) {
            return;
        }
        t.accept(x.data);
        traverseDLR(x.lc, t);
        traverseDLR(x.rc, t);
    }

    /**
     * 先序遍历（迭代1）
     * 树的先序遍历（根节点->左节点->右节点）  用了栈后进先出的特点
     * 栈中成反向的顺序排队等待访问
     *
     * @param x 遍历的树的树根
     * @param t java8的lambda操作
     */
    public void travPreI1(BinNode<T> x, Consumer<T> t) {
        //创建栈
        Stack<BinNode<T>> s = new Stack<>();
        //如果x不为空放入栈中
        if (x != null) {
            s.push(x);
        }
        //对栈进行循环操作
        while (!s.empty()) {
            //访问栈顶元素
            x = s.pop();
            //操作元素
            t.accept(x.data);
            //由于是先序遍历，又根据栈结构 LIFO的特点先将右节点装载进入栈中，在将左节点装载入栈中
            if (x.rc != null) {
                s.push(x.rc);
            }
            if (x.lc != null) {
                s.push(x.lc);
            }
        }
    }

    /**
     * 先序遍历（迭代2）
     * 宏观上将所有的节点和其子节点都看成一颗子树
     * 自上而下，自左而右的访问每个子树，将其每个子树都看做互相独立的任务
     *
     * @param x 遍历的树的树根
     * @param t java8的lambda操作
     */
    public void travPreI2(BinNode<T> x, Consumer<T> t) {
        Stack<BinNode<T>> s = new Stack<>();
        while (true) {
            visitAlongLeftBranch(x, t, s);
            if (s.empty()) {
                break;
            }
            x = s.pop();
        }
    }

    /**
     * 左节点操作
     *
     * @param x 操作的节点
     * @param t 操作的步骤
     * @param s 辅助栈
     */
    private void visitAlongLeftBranch(BinNode<T> x, Consumer<T> t, Stack<BinNode<T>> s) {
        while (x != null) {
            t.accept(x.data);
            s.push(x.rc);
            x = x.lc;
        }
    }

    /**
     * 中序遍历（递归）
     *
     * @param x 遍历的树的树根
     * @param t java8的lambda操作
     */
    public void traverseLDR(BinNode<T> x, Consumer<T> t) {
        if (x == null) {
            return;
        }
        traverseDLR(x.lc, t);
        t.accept(x.data);
        traverseDLR(x.rc, t);
    }

    /**
     * 中序遍历(迭代)
     *
     * @param x 遍历的树的树根
     * @param t java8的lambda操作
     */
    public void travIn(BinNode<T> x, Consumer<T> t) {
        Stack<BinNode<T>> s = new Stack<>();
        while (true) {
            goAlongLeftBranch(x, s);
            if (s.empty()) {
                break;
            }
            x = s.pop();
            t.accept(x.data);
            x = x.rc;
        }
    }

    /**
     * 中序遍历左节点操作
     *
     * @param x 操作的节点
     * @param s 辅助栈
     */
    private void goAlongLeftBranch(BinNode<T> x, Stack<BinNode<T>> s) {
        //一直向左深入
        while (x != null) {
            //左节点入栈
            s.push(x);
            x = x.lc;
        }
    }

    /**
     * 后序遍历（递归）
     *
     * @param x 遍历的树的树根
     * @param t java8的lambda操作
     */
    public void traverseLRD(BinNode<T> x, Consumer<T> t) {
        if (x == null) {
            return;
        }
        traverseLRD(x.lc, t);
        traverseLRD(x.rc, t);
        t.accept(x.data);
    }

    /**
     * 后序遍历（迭代）
     *
     * @param x 遍历的树的树根
     * @param t java8的lambda操作
     */
    public void travPost(BinNode<T> x, Consumer<T> t) {
        Stack<BinNode<T>> s = new Stack<>();
        //x不为空入栈
        if (x != null) {
            s.push(x);
        }
        //循环处理栈顶元素
        while (!s.empty()) {
            //栈顶不是x的父节点，必定是右子节点
            if (s.peek() != x.parent) {
                gotoHLVFL(s);
            }
            x = s.pop();
            t.accept(x.data);
        }
    }

    /**
     * 在右子树中找到HLVFL（highest leaf visible from left）
     *
     * @param s 辅助栈
     */
    private void gotoHLVFL(Stack<BinNode<T>> s) {
        BinNode<T> x = s.peek();
        //自上而下的检查栈顶是否已空
        while (x != null) {
            x = s.peek();
            //尽可能的向左子节点移动
            if (x.lc != null) {
                //如果这个节点有有孩子，则放入栈中
                if (x.rc != null) {
                    s.push(x.rc);
                }
                //再将左子节点放入栈中
                s.push(x.lc);
            } else {
                //已经走到左子树的镜头则转向右子树
                s.push(x.rc);
            }
        }
        //空的栈顶抛出
        s.pop();
    }
}
