import list.List;
import test.Student;
import tree.btree.BTNode;
import tree.btree.BTree;
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

//        int fib = Fib.fib(6);
//        System.out.println(fib);

//        Student student1 = new Student(1, "张三");
//        Student student2 = new Student(2, "张三");
//        Student student3 = new Student(3, "张三");
//        Student student4 = new Student(4, "张三");
//        Student student5 = new Student(5, "张三");
//
//        MyVector<Student> myVector = new MyVector<>();
//        myVector.add(student5);
//        myVector.add(student3);
//        myVector.add(student1);
//        myVector.add(student2);
//        myVector.add(student4);
//
//        System.out.println(myVector);
//
//        myVector.mergeSort(0,myVector.getSize());
//        System.out.println(myVector);

//        BTree<Integer> bTree = new BTree<>();
//        bTree.insert(1);
//        bTree.insert(2);
//        bTree.insert(3);
//        bTree.insert(4);
//        bTree.insert(5);
//        bTree.insert(6);
//        bTree.insert(7);
//
//        System.out.println(bTree);
//        bTree.remove(1);
//        bTree.remove(2);
//        bTree.remove(3);
//        bTree.remove(4);
//        bTree.remove(5);
//        bTree.remove(6);
//        bTree.remove(7);
//
//        System.out.println(bTree);
        Student student1 = new Student(1,"张三");
        Student student2 = new Student(1,"张三");
        Student student3 = new Student(2,"张三");
        Student student4 = new Student(3,"张三");
        List<Object> objectList = new List<>();
        objectList.insertAsLast(student1);
        objectList.insertAsLast(student2);
        objectList.insertAsLast(student3);
        objectList.insertAsLast(student4);
        objectList.uniquify();
    }


}
