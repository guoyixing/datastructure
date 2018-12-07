package list;

import utils.Compare;

import java.util.function.Consumer;

/**
 * 链表结构
 *
 * @date 2018-12-07 13:20
 * @since 1.0.0
 */
public class List<T> {
    /**
     * 规模
     */
    private int size;
    /**
     * 前哨兵
     */
    private ListNode<T> header = new ListNode<>();
    /**
     * 后哨兵
     */
    private ListNode<T> trailer = new ListNode<>();

    /**
     * 初始化
     */
    public void init() {
        header = new ListNode<T>();
        trailer = new ListNode<T>();
        this.header.succ = this.trailer;
        this.header.pred = null;
        this.trailer.pred = this.header;
        this.trailer.succ = null;
        this.size = 0;
    }

    /**
     * 获取首位节点
     */
    public ListNode<T> first() {
        return header.succ;
    }

    /**
     * 获取末尾节点
     */
    public ListNode<T> list() {
        return trailer.pred;
    }

    /**
     * 查询下标为r节点的数据
     *
     * @param r 下标
     */
    public T operator(int r) {
        return getNodeByRank(r).data;
    }

    /**
     * 查询下标为r的节点
     *
     * @param r 下标
     */
    public ListNode<T> getNodeByRank(int r) {
        ListNode<T> p = header.succ;
        while (0 < r--) {
            p = p.succ;
        }
        return p;
    }

    /**
     * 在规定区域查询元素所在位置
     *
     * @param t 查询的元素
     * @param n 查询的范围
     * @param p 从当前节点开始查询
     */
    public ListNode<T> find(T t, int n, ListNode<T> p) {
        while (0 < n--) {
            if (t.equals((p = p.pred).data)) {
                return p;
            }
        }
        return null;
    }

    /**
     * 查询元素所在位置
     *
     * @param t 查询的元素
     */
    public ListNode<T> find(T t) {
        return find(t, size, trailer);
    }

    /**
     * 将e当做节点p的直接前驱插入
     *
     * @param p 操作节点
     * @param t 插入的元素
     */
    public ListNode<T> insertB(ListNode<T> p, T t) {
        this.size++;
        return p.insertAsPred(t);
    }

    /**
     * 将e当做节点p的直接后继插入
     *
     * @param p 操作节点
     * @param t 插入的元素
     */
    public ListNode<T> insertA(ListNode<T> p, T t) {
        this.size++;
        return p.insertAsSucc(t);
    }

    /**
     * 插入首位上的节点
     *
     * @param t 插入的数据
     */
    public void insertAsFirst(T t) {
        //插入前置哨兵的后继节点的前驱
        this.first().insertAsPred(t);
    }

    /**
     * 插入末位上的节点
     *
     * @param t 插入的数据
     */
    public void insertAsLast(T t) {
        //插入后置哨兵的前驱的后继
        trailer.pred.insertAsSucc(t);
    }

    /**
     * 复制节点
     *
     * @param p 复制的节点
     * @param n 复制的个数
     */
    public void copyNodes(ListNode<T> p, int n) {
        //初始化
        init();
        //循环复制
        while (0 < n--) {
            //在末尾插入数据
            insertAsLast(p.data);
            p = p.succ;
        }
    }

    /**
     * 全量复制元素
     *
     * @param t 复制的元素
     */
    public void list(List<T> t) {
        copyNodes(t.first(), t.size);
    }

    /**
     * 区间复制元素
     *
     * @param t 复制的元素
     * @param r 下标
     * @param n 复制的个数
     */
    public void list(List<T> t, int r, int n) {
        copyNodes(t.getNodeByRank(r), n);
    }

    /**
     * 删除节点
     *
     * @param p 需要删除的节点
     */
    public T remove(ListNode<T> p) {
        T data = p.data;
        p.pred.succ = p.succ;
        p.succ.pred = p.pred;
        this.size--;
        return data;
    }

    /**
     * 无序链表唯一化
     */
    public int deduplicate() {
        //只有一个节点无需
        if (this.size < 2) {
            return 0;
        }
        //记录原规模
        int oldSize = this.size;
        //获取首位节点
        ListNode<T> p = first();
        int r = 1;
        //删除重复节点
        while (trailer != (p = p.succ)) {
            ListNode<T> q = find(p.data, r, p);
            if (q != null) {
                remove(q);
            } else {
                r++;
            }
        }
        //删除的个数
        return oldSize - this.size;
    }

    /**
     * 遍历的方式操作元素
     */
    public void traverse(Consumer<T> t) {
        ListNode<T> p = this.header;
        while ((p = p.succ) != trailer) {
            t.accept(p.data);
        }
    }

    /**
     * 成批删除重复元素
     */
    public int uniquify() {
        if (this.size < 2) {
            return 0;
        }
        int oldSize = this.size;
        //p为各区段起点
        ListNode<T> p = first();
        //q为其后继
        ListNode<T> q;
        while (trailer != (q = p.succ)) {
            if (!p.data.equals(q.data)) {
                p = q;
            } else {
                //雷同删除后者
                remove(q);
            }
        }
        return oldSize - size;
    }
}
