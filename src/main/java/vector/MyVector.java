package vector;

import utils.Compare;
import utils.Fib;

import java.util.Arrays;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            sb.append(((T) elem[i]).toString()).append("\n");
        }
        return sb.toString();
    }

    public int getSize() {
        return size;
    }

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
        this.elem = new Object[(this.capacity << 1)];
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
        //容器存放量大于缩容范围
        if (this.size > this.capacity * LOAD_FACTOR) {
            for (int i = this.size; i < this.capacity; i++) {
                this.elem[i] = null;
            }
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

    public void replace(int i, T t) {
        this.elem[i] = t;
    }

    /**
     * 插入算法
     *
     * @param r 所需要插入的地址
     * @param t 所需要插入的元素
     */
    public int insert(int r, T t) {
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
            //通过compare方法来计算
            n += Compare.greater(this.elem[i - 1], this.elem[i]) ? 1 : 0;
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

    /**
     * 有序向量 统一的查询接口
     * 在查询算法中有一些情况不可以被忽略
     * 1.元素不存在的时候
     * 2.元素存在多少个的时候
     * 对于这两种情况至少要保证
     * 查询即便失败的情况下也应该给出适合插入的位置
     * 如果出现重复的元素，则每组相同的元素也应该根据插入的顺序排序
     *
     * @param t    查询的元素
     * @param lo   查询区间的首位
     * @param hi   查询区间的末尾
     * @param flag true的时候采用二分查找 false的时候采用斐波那契查找
     */
    public int search(T t, int lo, int hi, boolean flag) {
        if (flag) {
            return binSearchC(this.elem, t, lo, hi);
        } else {
            return fibSearch(this.elem, t, lo, hi);
        }
    }

    public int search(T t) {
        return binSearchC(this.elem, t, 0, this.size);
    }

    /**
     * 二分查找A版本
     * 算法复杂度 O(1.5*logn)
     *
     * @param objs 需要搜索的素组
     * @param t    需要搜索的元素
     * @param lo   区间的首位
     * @param hi   区间的末尾
     */
    private int binSearchA(Object[] objs, T t, int lo, int hi) {
        //每一次循环 都有可能出现3种情况
        while (lo < hi) {
            //获取这次区间的中间元素
            int mi = ((lo + hi) >> 1);
            if (Compare.less(t, objs[mi])) {
                //情况1：t小于中间元素 则将搜索的范围缩小至0到中间元素的位置S[lo,mi)
                hi = mi;
            } else if (Compare.less(objs[mi], t)) {
                //情况2：t大于中间元素 则将搜索的范围缩小至中间元素的位置到hi范围 S(mi,hi)
                lo = mi + 1;
            } else {
                //情况3：命中元素，随即返回
                return mi;
            }
        }
        //命中失败
        return -1;
    }

    /**
     * 斐波拉契查询算法
     *
     * @param objs 需要搜索的素组
     * @param t    需要搜索的元素
     * @param lo   区间的首位
     * @param hi   区间的末尾
     */
    private int fibSearch(Object[] objs, T t, int lo, int hi) {
        //获取长度为区间个数的斐波那契数列
        Fib fib = new Fib(hi - lo);
        while (lo < hi) {
            //将数列的指针指向一个不大于 数组个数的斐波拉契数值
            while (hi - lo < fib.get()) {
                fib.prev();
            }
            //用这个斐波拉契数获取中间值  -1保证不会超出数组范围
            int mi = lo + fib.get() - 1;
            //和二分查找一样的三种情况
            if (Compare.less(t, objs[mi])) {
                hi = mi;
            } else if (Compare.less(objs[mi], t)) {
                lo = mi + 1;
            } else {
                return mi;
            }
        }
        return -1;
    }

    /**
     * 二分查找B版本
     * 相较于斐波那契查询 B版本更加的简单粗暴的解决了左右转向次数不均衡的问题
     * 所有的分支也将只有两种情况
     *
     * @param objs 需要搜索的素组
     * @param t    需要搜索的元素
     * @param lo   区间的首位
     * @param hi   区间的末尾
     */
    private int binSearchB(Object[] objs, T t, int lo, int hi) {
        //每一次循环 都有可能出现2种情况  当有效的搜索区间小于1的时候 算法变会终止
        while (1 < hi - lo) {
            //获取这次区间的中间元素
            int mi = ((lo + hi) >> 1);
            if (Compare.less(t, objs[mi])) {
                //情况1：t小于中间元素 则将搜索的范围缩小至0到中间元素的位置S[lo,mi)
                hi = mi;
            } else {
                //情况2：t不小于中间元素 则将搜索的范围缩小至中间元素的位置到末尾元素 S[mi,hi)
                lo = mi;
            }
        }
        //最后获取到的元素便是最有可能是相同元素的位置，如果这都没用命中 则命中失败
        return t.equals(objs[lo]) ? lo : -1;
    }

    /**
     * 二分查找C版本
     * 在A，B两个版本中 算法都没有完全的执行查询的约定
     * 查询即便失败的情况下也应该给出适合插入的位置
     *
     * @param objs 需要搜索的素组
     * @param t    需要搜索的元素
     * @param lo   区间的首位
     * @param hi   区间的末尾
     */
    private int binSearchC(Object[] objs, T t, int lo, int hi) {
        //每一次循环 都有可能出现2种情况  当有效的搜索区间小于1的时候 算法变会终止
        while (lo < hi) {
            //获取这次区间的中间元素
            int mi = ((lo + hi) >> 1);
            if (Compare.less(t, objs[mi])) {
                //情况1：S[lo,mi)
                hi = mi;
            } else {
                //情况2：S(mi,hi)
                lo = mi + 1;
            }
        }
        return --lo;
    }

    /**
     * 归并排序
     *
     * @param lo 区间的首位
     * @param hi 区间的末尾
     */
    public void mergeSort(int lo, int hi) {
        //单个元素无需处理
        if (hi - lo < 2) {
            return;
        }
        //获取中间数
        int mi = ((lo + hi) >> 1);
        //左边的区间继续拆分
        mergeSort(lo, mi);
        //右边的区间继续拆分
        mergeSort(mi, hi);
        //将最终拆分的结果合并
        merge(lo, mi, hi);
    }

    /**
     * 归并排序中的合并算法
     *
     * @param lo 区间的首位
     * @param mi 区间的中位
     * @param hi 区间的末尾
     */
    private void merge(int lo, int mi, int hi) {
        Object[] objA = Arrays.copyOfRange(this.elem, lo, hi);
        int lb = mi - lo;
        Object[] objB = new Object[lb];
        for (int i = 0; i < lb; objB[i] = objA[i++]) {
        }
        int lc = hi - mi;
        Object[] objC = Arrays.copyOfRange(this.elem, mi, hi);
        for (int i = 0, j = 0, k = 0; j < lb; ) {
            if (k < lc && Compare.less(objC[k], objB[j])) {
                objA[i++] = objC[k++];
            }
            if (lc <= k || Compare.lessAndEquals(objB[j], objC[k])) {
                objA[i++] = objB[j++];
            }
        }
        // 把新数组中的数覆盖nums数组
        System.arraycopy(objA, 0, this.elem, lo, objA.length);
    }
}
