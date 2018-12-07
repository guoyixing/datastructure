package list;

/**
 * 链表节点
 *
 * @date 2018-12-07 13:14
 * @since 1.0.0
 */
public class ListNode<T> {
    /**
     * 数据
     */
    protected T data;
    /**
     * 前驱
     */
    protected ListNode<T> pred;
    /**
     * 后继
     */
    protected ListNode<T> succ;

    ListNode() {

    }

    public ListNode(T data, ListNode<T> pred, ListNode<T> succ) {
        this.data = data;
        this.pred = pred;
        this.succ = succ;
    }

    /**
     * 插入前驱节点，存入被引用对象t，并返回新节点位置
     *
     * @param t 插入的元素
     */
    public ListNode<T> insertAsPred(T t) {
        ListNode<T> predListNode = new ListNode<>(t, this.pred, this);
        this.pred.succ = predListNode;
        this.pred = predListNode;
        return predListNode;
    }

    /**
     * 插入后继节点，存入被引用对象t，并返回新节点位置
     *
     * @param t 插入的元素
     */
    public ListNode<T> insertAsSucc(T t) {
        ListNode<T> succListNode = new ListNode<>(t, this, this.succ);
        this.succ.pred = succListNode;
        this.succ = succListNode;
        return succListNode;
    }
}
