import Vector.MyVector;

/**
 * 测试类
 * @date 2018-12-04 15:44
 * @since 1.0.0
 */
public class Main {
    public static void main(String[] args) {
        MyVector<Integer> myVector = new MyVector<Integer>();
        myVector.add(1);
        myVector.add(2);
        myVector.add(3);
        myVector.add(4);

        myVector.remove(0);
        myVector.remove(0);
        myVector.remove(0);

        Integer integer = myVector.get(0);
        System.out.println(integer);
    }
}
