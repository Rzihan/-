package test;

import binary_tree.BBSTree;
import binary_tree.Record;

import java.util.Map;
import java.util.Random;

public class BBTreeTest {

    public static void main(String[] args) {
//        test();
//        testInsert();
//        testDelete();
//        testMerge();
        testSplit();
    }

    private static void testInsert() {
        for (int j = 0; j < 10; j++) {
            BBSTree tree = new BBSTree();
            Random random = new Random();
            int key;
            String value;
            for (int i = 0; i < 15; i++) {
                key = random.nextInt(100);
                value = "value" + key;
                boolean put = tree.put(new Record(key, value));
            }
            tree.output();
        }
    }

    private static void testDelete() {
        BBSTree tree = new BBSTree();

        System.out.println(tree.put(new Record(625, "value625")));
        System.out.println(tree.put(new Record(808, "value808")));
        System.out.println(tree.put(new Record(522, "value522")));
        System.out.println(tree.put(new Record(454, "value454")));
        System.out.println(tree.put(new Record(517, "value517")));
        System.out.println(tree.put(new Record(635, "value635")));
        System.out.println(tree.put(new Record(921, "value921")));
        System.out.println(tree.put(new Record(753, "value753")));
        System.out.println(tree.put(new Record(359, "value359")));
        System.out.println(tree.put(new Record(617, "value617")));
        System.out.println(tree.put(new Record(179, "value179")));
        System.out.println(tree.put(new Record(438, "value438")));
        System.out.println(tree.put(new Record(592, "value592")));
        System.out.println(tree.put(new Record(941, "value941")));
        System.out.println(tree.put(new Record(424, "value424")));

        tree.output();

        System.out.println("*************删除424**************");
        System.out.println(tree.remove(424));
        tree.output();
        System.out.println("*************删除454**************");
        System.out.println(tree.remove(454));
        tree.output();
        System.out.println("*************删除454**************");
        System.out.println(tree.remove(454));
        tree.output();
        System.out.println("*************删除592**************");
        System.out.println(tree.remove(592));
        tree.output();
        System.out.println("*************删除753**************");
        System.out.println(tree.remove(753));
        tree.output();
        System.out.println("*************删除635**************");
        System.out.println(tree.remove(635));
        tree.output();
        System.out.println("*************删除808**************");
        System.out.println(tree.remove(808));
        tree.output();
        System.out.println("*************删除941**************");
        System.out.println(tree.remove(941));
        tree.output();
        System.out.println("*************删除179**************");
        System.out.println(tree.remove(179));
        tree.output();
        System.out.println("*************删除438**************");
        System.out.println(tree.remove(438));
        tree.output();
    }

    private static void test() {
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            int key = random.nextInt(1000);
            String value = "value" + key;
            System.out.println("System.out.println(tree.put(new Record(" + key+ ", \"" + value + "\")));");
        }
    }

    static void testMerge() {
        BBSTree tree1 = new BBSTree();
        BBSTree tree2 = new BBSTree();

        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            tree1.put(new Record(random.nextInt(100), "value" + random.nextInt(50)));
            tree2.put(new Record(random.nextInt(100), "value" + random.nextInt(50)));
        }
        System.out.println("第一棵树");
        tree1.output();
        System.out.println("第二棵树");
        tree2.output();

        BBSTree.merge(tree1, tree2).output();
    }

    static void testSplit() {
        BBSTree tree1 = new BBSTree();

        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            tree1.put(new Record(random.nextInt(100), "value" + random.nextInt(50)));
        }
        tree1.output();
        int x = random.nextInt(40) + 40;
        System.out.println("分裂因子:" + x);
        Map<String, BBSTree> treeMap = BBSTree.split(tree1, x);
        treeMap.get("big").output();
        treeMap.get("small").output();
    }
}
