package utils;

public class Compare {
    /**
     * 判断元素1是否大于元素2
     *
     * @param t1 元素1
     * @param t2 元素2
     */
    public static boolean greater(Object t1, Object t2) {
        if (t2.getClass() == t1.getClass() && t1 instanceof Comparable ) {
            //通过compareTo方法来计算
            return ((Comparable)t1).compareTo((Comparable) t2) > 0 ;
        } else {
            //当前元素与其左边元素比较  判断是否有序，这里做的比较简单只是通过hashCode判断
            return t1.hashCode() > t2.hashCode();
        }
    }

    /**
     * 判断元素1是否小于元素2
     *
     * @param t1 元素1
     * @param t2 元素2
     */
    public static boolean less(Object t1, Object t2) {
        if (t2.getClass() == t1.getClass() &&t1 instanceof Comparable) {
            //通过compareTo方法来计算
            return ((Comparable) t1).compareTo((Comparable) t2) < 0;
        } else {
            //当前元素与其左边元素比较  判断是否有序，这里做的比较简单只是通过hashCode判断
            return t1.hashCode() < t2.hashCode();
        }
    }
}
