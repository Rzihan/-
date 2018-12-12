package test;

import binary_tree.BBSTree;
import binary_tree.Record;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    /**
     * 主函数
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        BBSTree tree = new BBSTree();
        int select = 0;
        //产生5-10个数据插入平衡二叉查找树中
        int n = random.nextInt(5) + 5;

        for (int i = 0; i < n; i++) {
            int key = random.nextInt(100);
            String value = "value" + key;
            tree.put(new Record(key, value));
        }

        do {
            tree.output();
            System.out.println("select 1 求结点个数size()");
            System.out.println("select 2 取值get(int key)");
            System.out.println("select 3 插入值put(Record record)");
            System.out.println("select 4 删除值remove(int key)");
            System.out.println("select 5 前序遍历preOrder");
            System.out.println("select 6 后序遍历postOrder");
            System.out.println("select 7 中序遍历inOrder");
            System.out.println("select 8 测试合并平衡二叉树");
            System.out.println("select 9 测试分裂平衡二叉树");

            select = scanner.nextInt();
            switch (select) {
                case 1 :
                    System.out.println("结点数" + tree.size());
                    break;
                case 2 :
                    System.out.println("请输入要查找的key(整型)");
                    int searchKey = scanner.nextInt();
                    System.out.println("查找结果: " + tree.get(searchKey));
                    break;
                case 3 :
                    System.out.println("请输入要插入的key(整型)");
                    int insertKey = scanner.nextInt();
                    System.out.println("请输入对应的value");
                    String insertValue = scanner.next();
                    System.out.println("插入结果: " + tree.put(new Record(insertKey, insertValue)));
                    break;
                case 4 :
                    System.out.println("请输入要删除的key(整型)");
                    int deleteKey = scanner.nextInt();
                    System.out.println("删除结果：" + tree.remove(deleteKey));
                    break;
                case 5 :
                    tree.preOrder();
                    break;
                case 6 :
                    tree.postOrder();
                    break;
                case 7 :
                    tree.inOrder();
                    break;
                case 8 :
                    BBTreeTest.testMerge();
                    break;
                case 9 :
                    BBTreeTest.testSplit();
                    break;
            }
        } while (select > 0 && select < 10);
    }
}
