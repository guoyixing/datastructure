package utils;

public class Fib {
    /**
     * 斐波拉契数列
     */
    private int[] fibArray;
    /**
     * 斐波拉契数列长度
     */
    private int size;
    /**
     * 当前指向元素的下标
     */
    private int index;

    public Fib(int size) {
        fibArray = new int[size];
        fibArray[0] = 1;
        fibArray[1] = 1;
        if (size > 2) {
            for (int i = 2; i < size; i++) {
                fibArray[i] = fibArray[i - 1] + fibArray[i - 2];
            }
        }
        this.size = size;
        this.index = size - 1;
    }

    /**
     * 获取当前指向的斐波拉契数
     */
    public int get() {
        return fibArray[index];
    }

    public int get(int n) {
        return fibArray[n];
    }

    /**
     * 指向下一个斐波拉契函数,并抛出
     */
    public int prev() {
        index = Math.max(index - 1, 0);
        return fibArray[index];
    }

    /**
     * 获取第n位上的斐波拉契数值
     *
     * @param n 第几位
     */
    public static int fib(int n) {
        if (n == 1 || n == 2) {
            return 1;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }
}
