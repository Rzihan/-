package binary_tree;

/**
 * B树中的存储记录
 */
public class Record {

    private int key;//键
    private String value;//值

    public Record(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int compareByKey(int key) {
        return Integer.compare(this.key, key);
    }

    @Override
    public String toString() {
        return key + " : " + value;
    }
}
