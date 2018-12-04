package Vector;

import java.util.ArrayList;

/**
 * 扩充向量
 *
 * @date 2018-12-04 13:48
 * @since 1.0.0
 */
public class MyVector<T> {
    /**
     * 默认的数组大小
     */
    private static final int DEFAULT_CAPACITY = 3;
    /**
     * 装载因子
     */
    private static final float LOAD_FACTOR = 0.5f;
    /**
     * 数组长度  已使用的大小
     */
    private int size;
    /**
     * 整体空间 最大大度
     */
    private int capacity;
    /**
     * 所包含的对象
     */
    private Object[] elem;

    /**
     * 默认构造函数
     */
    public MyVector() {
        this.size = 0;
        this.elem = new Object[capacity = DEFAULT_CAPACITY];
    }

    /**
     * 通过数组加载的构造函数
     *
     * @param t  数组值
     * @param lo 前至哨兵
     * @param hi 后置哨兵
     */
    public MyVector(T[] t, int lo, int hi) {
        copyFrom(t, lo, hi);
    }

    /**
     * 通过向量加载的构造函数  选择需要的区间
     *
     * @param t  向量对象
     * @param lo 前至哨兵
     * @param hi 后置哨兵
     */
    public MyVector(MyVector<T> t, int lo, int hi) {
        copyFrom(t.elem, lo, hi);
    }

    /**
     * 通过向量加载的构造函数  全量复制
     *
     * @param t 向量对象
     */
    public MyVector(MyVector<T> t) {
        copyFrom(t.elem, 0, t.size);
    }

    /**
     * 数组的拷贝
     * 算法的效率O(n)
     *
     * @param t  数组对象
     * @param lo 前至哨兵
     * @param hi 后置哨兵
     */
    private void copyFrom(Object[] t, int lo, int hi) {
        //对数组空间进行分配
        this.elem = new Object[this.capacity = 2 * (hi - lo)];
        //对数组的长度进行初始化
        this.size = 0;
        //把对象逐一的复制进入向量中
        while (lo < hi) {
            //两个对象逐一增加
            this.elem[size++] = t[lo++];
        }
    }

    /**
     * 扩充算法（容量加倍策略）
     */
    private void expand() {
        //容器尚未盛满的时候不需要进行扩容
        if (this.size < this.capacity) {
            return;
        }
        //容器的最小容量不得小于默认值
        this.capacity = Math.max(this.capacity, DEFAULT_CAPACITY);
        //缓存住原本向量中的数据
        Object[] oldElem = this.elem;
        //创建新的容器 并且将容量翻倍 this.capacity<<1 位运算左移1位 等价于乘2
        this.elem = new Object[this.capacity << 1];
        //拷贝数据到新的容器中
        for (int i = 0; i < this.size; i++) {
            this.elem[i] = oldElem[i];
        }
        //最好将旧容器清空 保证cg能够及时的清理
        oldElem = null;
    }

    /**
     * 缩容算法（容量倍减策略）
     * 相比扩容算法 缩容算法就要简单的多  截取多余的空间即可
     */
    private void shrink() {
        //容器尚未盛满的时候不需要进行扩容
        if (this.size > this.capacity * LOAD_FACTOR) {
            return;
        }
        //容器的最小容量不得小于默认值
        this.capacity = Math.max(this.size, DEFAULT_CAPACITY);
        //缓存住原本向量中的数据
        Object[] oldElem = this.elem;
        //创建新的容器
        this.elem = new Object[this.capacity];
        //拷贝数据到新的容器中
        for (int i = 0; i < this.size; i++) {
            this.elem[i] = oldElem[i];
        }
        //最好将旧容器清空 保证cg能够及时的清理
        oldElem = null;
    }

    /**
     * 寻址获取元素
     *
     * @param i 数组元素的地址
     */
    public T get(int i) {
        return (T) this.elem[i];
    }

    /**
     * 插入算法
     *
     * @param r 所需要插入的地址
     * @param t 所需要插入的元素
     */
    private int insert(int r, T t) {
        //检测是否需要扩容
        expand();
        //自后向前遍历 如果需要插入到第x的位置上 只需要x位置上以及x之后的元素向数组的尾端移动一格，这样第x的位置将会被空出
        for (int i = this.size; i > r; i--) {
            this.elem[i] = this.elem[i - 1];
        }
        //在空出的位置上添加上需要插入的元素
        this.elem[r] = t;
        //数组的长度+1
        this.size++;
        //返回所插入的位置
        return r;
    }

    /**
     * 对外的插入接口
     *
     * @param t 所需要插入的元素
     */
    public int add(T t) {
        //这里只需要调用插入算法即可  尾端插入  插入的位置就是当前数组的长度
        return insert(this.size, t);
    }

    /**
     * 区间删除
     *
     * @param lo 区间的开始位置
     * @param hi 区间的结束位置
     */
    public int remove(int lo, int hi) {
        //为了效率考虑 快速的将这种没有任何删除的情况抛出
        if (lo == hi) {
            return 0;
        }
        //删除a到b位置上的数据（取前不去后），将b位置上的元素放到a位置上，b+1位置上大的元素放到a+1位置上以此类推
        while (hi < this.size) {
            this.elem[lo++] = this.elem[hi++];
        }
        //重新赋值数组长度
        this.size = lo;
        //判断是否缩容
        shrink();
        //返回删除元素的个数
        return hi - lo;
    }

    /**
     * 单元素删除
     *
     * @param r 需要删除元素的位置
     */
    public T remove(int r) {
        //保留住元素
        T t = (T) this.elem[r];
        //单元素删除是区间删除的一种特殊情况，直接调用区间删除方法即可
        remove(r, r + 1);
        return t;
    }

    /**
     * 查询元素
     *
     * @param t  需要查询的元素
     * @param lo 区间首位
     * @param hi 区间末位
     */
    public int find(T t, int lo, int hi) {
        //逐一遍历区间内的元素 直到找到
        while (lo < hi-- && !((T) this.elem[hi]).equals(t)) {

        }
        //元素所在位置 返回hi小于lo则是失败；
        return hi;
    }

    /**
     * 无序向量唯一化，虽然有序向量也可以这样使用唯一化策略，但其效率过低
     * 算法复杂度O(n^2)
     */
    public int deduplicate() {
        int oldSize = this.size;
        int i = 1;
        while (i < this.size) {
            //查找一样的元素
            if (find((T) this.elem[i], 0, i) < 0) {
                //不存在则继续查找
                i++;
            } else {
                //存在则删除
                remove(i);
            }
        }
        return oldSize - this.size;
    }

    /**
     * 有序性甄别算法
     */
    public int disorderd() {
        //计数器
        int n = 0;
        //遍历元素
        for (int i = 1; i < this.size; i++) {
            if (this.elem[i] instanceof Comparable) {
                //通过compareTo方法来计算
                n += ((Comparable) this.elem[i - 1]).compareTo((Comparable) this.elem[i]) > 0 ? 1 : 0;
            } else {
                //当前元素与其左边元素比较  判断是否有序，这里做的比较简单只是通过hashCode判断
                n += this.elem[i - 1].hashCode() > this.elem[i].hashCode() ? 1 : 0;
            }
        }
        return n;
    }

    /**
     * 低效的有序向量唯一化算法
     * 算法的耗时取决于while的次数
     * 并且每次都需要调用remove方法（耗时为O(n-1)~O(1)）
     * 整个算法的复杂度为O(n^2)
     * 即便相较于deduplicate方法去掉了find方法 但是复杂度并为发生变化，实际使用中速度提升也比较有限
     */
    public int uniquifyLow() {
        int oldSize = this.size;
        int i = 1;
        //送第二个元素开始比较
        while (i < this.size) {
            //判断左边的元素是否与当前元素相等
            if (this.elem[i - 1].equals(this.elem[i])) {
                //相等则删除当前元素
                remove(i);
            } else {
                //否则判断下一个元素
                i++;
            }
        }
        return oldSize - this.size;
    }

    /**
     * 高效的有序向量唯一化算法
     * 低效的算法问题的根源在于，同一个元素可以被删除元素的后继（也就是右边的元素）需要多次的前移
     * 若是以重复的区间为单位删除，性能必然能大幅度的提升
     * 通过区间删除可以将算法复杂度提升至O(n)
     */
    public int uniquify() {
        //相邻的元素的秩（位置）
        int i = 0, j = 0;
        //逐一扫描每个元素
        while (++j < this.size) {
            //如果元素相同则不处理，发现不同元素的时候，将元素向前移至紧邻于前者的右侧
            if (!this.elem[i].equals(this.elem[j])) {
                this.elem[++i] = this.elem[j];
            }
        }
        this.size = ++i;
        //通过缩容算法直接将多余的元素全部删除
        shrink();
        return j - i;
    }
}
