# datastructure
数据结构
## 项目结构
Main.java（测试启动类）<br/>
|--test(测试用到的类)<br/>
|--utils（工具类）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;|--Compare.java（比较对象的大小）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;|--Fib.java（斐波那契数列对象）<br/>
|--vector（动态数组）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;|--MyVector.java<br/>
|--list（链表）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;|--ListNode.java（链表节点）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;|--List.java（链表）<br/>
|--tree（树）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;|--bintree（二叉树）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|--BinNode.java（二叉树节点）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|--BinTree.java（二叉树）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;|--binsearchtree（二叉搜索树）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|--BinSearchTree.java（二叉搜索树）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;|--btree（b树）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|--BTNode.java（b树节点）<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|--BTree.java（b树）<br/>
## vector：动态数组（可变向量）
1.扩充算法<br/>
2.缩容算法<br/>
3.插入算法<br/>
4.删除算法<br/>
5.归并排序算法<br/>
### 无序动态数组
1.查询算法<br/>
2.唯一化算法<br/>
### 有序动态数组
1.有序甄别算法（判断宿主是否有序）<br/>
2.低效唯一化算法<br/>
3.高效唯一化算法<br/>
4.二分查询算法（三种二分查询算法A->B->c演进）<br/>
5.斐波拉契查询算法<br/>
## list：链表
1.首节点插入<br/>
2.尾节点插入<br/>
3.在指定节点前方插入<br/>
4.在指定节点后方插入<br/>
5.节点删除算法<br/>
6.链表拷贝算法<br/>
7.遍历操作算法<br/>
8.选择排序算法<br/>
9.插入排序算法<br/>
### 无序链表
1.查询算法<br/>
2.无序链表唯一化算法<br/>
### 有序链表
1.有序链表唯一化算法<br/>
2.有序链表查询<br/>
## tree：树
### bintree：二叉树
1.左、右子节点插入算法<br/>
2.左、右子树接入算法<br/>
3.子树删除算法<br/>
4.子树分离算法<br/>
5.先序遍历（递归、迭代）算法<br/>
6.中序遍历（递归、迭代）算法<br/>
7.后序遍历（递归、迭代）算法<br/>
8.层级遍历（迭代）算法<br/>
### BinSearchTree：二叉搜索树（继承二叉树）
1.搜索算法<br/>
2.插入算法<br/>
3.删除算法<br/>
### btree：b树
1.上溢修复算法<br/>
2.下溢修复算法<br/>
3.搜索算法<br/>
4.插入算法<br/>
5.删除算法<br/>
