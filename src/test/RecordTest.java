package test;

import binary_tree.Record;

public class RecordTest {

    public static void main(String[] args) {
        Record record0 = new Record(10, "value10");
        Record record = new Record(1, "value1");
        System.out.println(record.compareByKey(record0.getKey()));
    }

}
