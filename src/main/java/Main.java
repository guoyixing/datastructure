import list.List;
import test.Student;
import tree.btree.BTNode;
import tree.btree.BTree;
import tree.rbtree.RBTree;
import utils.Compare;
import utils.Fib;
import vector.MyVector;

/**
 * 测试类
 *
 * @date 2018-12-04 15:44
 * @since 1.0.0
 */
public class Main {
    private static final int a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80};
    private static final boolean mDebugInsert = true;    // "插入"动作的检测开关(false，关闭；true，打开)
    private static final boolean mDebugDelete = true;    // "删除"动作的检测开关(false，关闭；true，打开)

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

//        List<Integer> objectList = new List<>();
//        objectList.insertAsLast(3);
//        objectList.insertAsLast(2);
//        objectList.insertAsLast(1);
//        objectList.insertAsLast(6);
//        objectList.insertAsLast(5);
//        objectList.insertAsLast(4);
//        objectList.insertAsLast(8);
//        objectList.insertAsLast(9);

        int i, ilen = a.length;
        RBTree<Integer> tree = new RBTree<Integer>();

        System.out.printf("== 原始数据: ");
        for (i = 0; i < ilen; i++) {
            System.out.printf("%d ", a[i]);
        }
        System.out.printf("\n");

        for (i = 0; i < ilen; i++) {
            tree.insert(a[i]);
            // 设置mDebugInsert=true,测试"添加函数"
            if (mDebugInsert) {
                System.out.printf("== 添加节点: %d\n", a[i]);
                System.out.printf("== 树的详细信息: \n");
                tree.print();
                System.out.printf("\n");
            }
        }

        System.out.printf("== 前序遍历: ");
        tree.preOrder();

        System.out.printf("\n== 中序遍历: ");
        tree.inOrder();

        System.out.printf("\n== 后序遍历: ");
        tree.postOrder();
        System.out.printf("\n");

        System.out.printf("== 最小值: %s\n", tree.minValue());
        System.out.printf("== 最大值: %s\n", tree.maxValue());
        System.out.printf("== 树的详细信息: \n");
        tree.print();
        System.out.printf("\n");

        // 设置mDebugDelete=true,测试"删除函数"
        if (mDebugDelete) {
            for (i = 0; i < ilen; i++) {
                tree.remove(a[i]);

                System.out.printf("== 删除节点: %d\n", a[i]);
                System.out.printf("== 树的详细信息: \n");
                tree.print();
                System.out.printf("\n");
            }
        }
    }
}
