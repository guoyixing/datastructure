import test.Student;
import utils.Compare;
import utils.Fib;
import vector.MyVector;

/**
 * 测试类
 * @date 2018-12-04 15:44
 * @since 1.0.0
 */
public class Main {
    public static void main(String[] args) {
//        MyVector<Integer> myVector = new MyVector<Integer>();
//        myVector.add(1);
//        myVector.add(2);
//        myVector.add(3);
//        myVector.add(4);
//
//        myVector.remove(0);
//        myVector.remove(0);
//        myVector.remove(0);
//
//        Integer integer = myVector.get(0);
//        System.out.println(integer);


//        Student student1 = new Student(3, "张三");
//        Student student2 = new Student(2, "张二");
//
//        boolean less = Compare.less(student1, student2);
//        System.out.println(less);

        int fib = Fib.fib(6);
        System.out.println(fib);
    }


}
