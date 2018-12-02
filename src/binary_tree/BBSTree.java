package binary_tree;

/**
 * 平衡二叉查找树
 */
public class BBSTree {

    private static final int LEFT_HIGH = 1;
    private static final int EQUAL_HIGH = 0;
    private static final int RIGHT_HIGH = -1;

    private BBSTNode root;
    private int size;

    public static class BBSTNode {

        private Record data;//记录的数据
        private int balanceFactor;//平衡因子
        private BBSTNode leftChild;//左孩子
        private BBSTNode rightChild;//右孩子

        BBSTNode(Record data, int balanceFactor) {
            this.data = data;
            this.balanceFactor = balanceFactor;
        }

        Record getData() {
            return data;
        }

        void setData(Record data) {
            this.data = data;
        }

        int getBalanceFactor() {
            return balanceFactor;
        }

        void setBalanceFactor(int balanceFactor) {
            this.balanceFactor = balanceFactor;
        }

        BBSTNode getLeftChild() {
            return leftChild;
        }

        void setLeftChild(BBSTNode leftChild) {
            this.leftChild = leftChild;
        }

        BBSTNode getRightChild() {
            return rightChild;
        }

        void setRightChild(BBSTNode rightChild) {
            this.rightChild = rightChild;
        }

    }

    /**
     * 构造一颗平衡二叉树
     */
    public BBSTree() {
    }

    /**
     * 返回平衡二叉树中元素个数
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 根据key获取记录
     * @param key
     * @return 存在对应record,则返回该record,否则返回null
     */
    public Record get(int key) {
        BBSTNode node = search(root, key);
        if (node == null) return null;
        return node.getData();
    }

    /**
     * 查找对应的记录
     * @param node
     * @param key
     * @return
     */
    private BBSTNode search(BBSTNode node, int key) {
        if (node == null) return null;
        if (node.getData().compareByKey(key) == 0) {
            return node;
        } else if (node.getData().compareByKey(key) < 0) {
            return search(node.getRightChild(), key);
        } else {
            return search(node.getLeftChild(), key);
        }
    }

    /**
     * 插入,插入成功返回true,否则返回false
     * @param record
     * @return
     */
    public boolean put(Record record) {
        InsertResult result = insertBBSTree(root, record);
        if (result.success) {
            root = result.node;
            size++;
        }
        return result.success;
    }

    /**
     * 插入之后结果
     */
    private static class InsertResult {
        private BBSTNode node;//返回结点
        private boolean success;//是否插入成功
        private boolean taller;//是否长高

        InsertResult(BBSTNode node, boolean success, boolean taller) {
            this.node = node;
            this.success = success;
            this.taller = taller;
        }

    }

    private InsertResult insertBBSTree(BBSTNode node, Record record) {
        if (node == null) {
            //树为空，树长高
            return new InsertResult(new BBSTNode(record, EQUAL_HIGH), true, true);
        } else if (node.getData().compareByKey(record.getKey()) == 0) {
            //树中存在对应的节点,更新记录
            node.setData(record);
            return new InsertResult(node, false, false);
        } else if (node.getData().compareByKey(record.getKey()) > 0) {
            //插入左子树中
            InsertResult result = insertBBSTree(node.getLeftChild(), record);
            node.setLeftChild(result.node);
            if (!result.success) return new InsertResult(node, false, false);
            if (result.taller) {//树长高,检查平衡因子,调整
                switch (node.getBalanceFactor()) {
                    case LEFT_HIGH :
                        return new InsertResult(insertLeftBalance(node), true, false);
                    case EQUAL_HIGH :
                        node.setBalanceFactor(LEFT_HIGH);
                        return new InsertResult(node, true, true);
                    case RIGHT_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        return new InsertResult(node, true, false);
                }
            }
            return new InsertResult(node, true, false);
        } else {
            //插入右子树
            InsertResult result = insertBBSTree(node.getRightChild(), record);
            node.setRightChild(result.node);
            if (!result.success) return new InsertResult(node, false, false);
            if (result.taller) {
                switch (node.getBalanceFactor()) {
                    case LEFT_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        return new InsertResult(node, true, false);
                    case EQUAL_HIGH :
                        node.setBalanceFactor(RIGHT_HIGH);
                        return new InsertResult(node, true, true);
                    case RIGHT_HIGH :
                        return new InsertResult(insertRightBalance(node), true, false);
                }
            }
            return new InsertResult(node, true, false);
        }
    }

    /**
     * 左旋
     * @param node
     * @return
     */
    private BBSTNode leftRotate(BBSTNode node) {
        BBSTNode newRoot = node.getRightChild();
        node.setRightChild(newRoot.getLeftChild());
        newRoot.setLeftChild(node);
        return newRoot;
    }

    /**
     * 右旋
     * @param node
     * @return
     */
    private BBSTNode rightRotate(BBSTNode node) {
        BBSTNode newRoot = node.getLeftChild();
        node.setLeftChild(newRoot.getRightChild());
        newRoot.setRightChild(node);
        return newRoot;
    }

    /**
     * 左左模型调整
     * @param node
     * @return
     */
    private BBSTNode leftLeftRotate(BBSTNode node) {
        return rightRotate(node);
    }

    /**
     * 右右模型调整
     * @param node
     * @return
     */
    private BBSTNode rightRightRotate(BBSTNode node) {
        return leftRotate(node);
    }

    /**
     * 左右模型调整
     * @param node
     * @return
     */
    private BBSTNode leftRightRotate(BBSTNode node) {
        node.setLeftChild(leftRotate(node.getLeftChild()));
        return rightRotate(node);
    }

    /**
     * 右左模型调整
     * @param node
     * @return
     */
    private BBSTNode rightLeftRotate(BBSTNode node) {
        node.setRightChild(rightRotate(node.getRightChild()));
        return leftRotate(node);
    }

    /**
     * 左平衡处理
     * @param node
     * @return
     */
    private BBSTNode insertLeftBalance(BBSTNode node) {
        BBSTNode leftChild = node.getLeftChild();
        switch (leftChild.getBalanceFactor()) {
            case LEFT_HIGH : /*LL型，右旋处理*/
                node.setBalanceFactor(EQUAL_HIGH);
                leftChild.setBalanceFactor(EQUAL_HIGH);
                return leftLeftRotate(node);
            case RIGHT_HIGH : /*LR型，双旋处理*/
                BBSTNode leftChildRightChild = leftChild.getRightChild();
                switch (leftChildRightChild.getBalanceFactor()) {
                    case LEFT_HIGH :
                        node.setBalanceFactor(RIGHT_HIGH);
                        leftChild.setBalanceFactor(EQUAL_HIGH);
                        leftChildRightChild.setBalanceFactor(EQUAL_HIGH);
                        break;
                    case EQUAL_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        leftChild.setBalanceFactor(EQUAL_HIGH);
                        leftChildRightChild.setBalanceFactor(EQUAL_HIGH);
                        break;
                    case RIGHT_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        leftChild.setBalanceFactor(LEFT_HIGH);
                        leftChildRightChild.setBalanceFactor(EQUAL_HIGH);
                        break;
                }
                return leftRightRotate(node);
        }
        return node;
    }

    /**
     * 右平衡处理
     * @param node
     * @return
     */
    private BBSTNode insertRightBalance(BBSTNode node) {
        BBSTNode rightChild = node.getRightChild();
        switch (rightChild.getBalanceFactor()) {
            case RIGHT_HIGH :/*RR型*/
                node.setBalanceFactor(EQUAL_HIGH);
                rightChild.setBalanceFactor(EQUAL_HIGH);
                return rightRightRotate(node);
            case LEFT_HIGH : /*RL型*/
                BBSTNode rightChildLeftChild = rightChild.getLeftChild();
                switch (rightChildLeftChild.getBalanceFactor()) {
                    case LEFT_HIGH :
                        node.setBalanceFactor(RIGHT_HIGH);
                        rightChild.setBalanceFactor(EQUAL_HIGH);
                        rightChildLeftChild.setBalanceFactor(LEFT_HIGH);
                        break;
                    case EQUAL_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        rightChild.setBalanceFactor(EQUAL_HIGH);
                        rightChildLeftChild.setBalanceFactor(EQUAL_HIGH);
                        break;
                    case RIGHT_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        rightChild.setBalanceFactor(LEFT_HIGH);
                        rightChildLeftChild.setBalanceFactor(RIGHT_HIGH);
                        break;
                }
                return rightLeftRotate(node);
        }
        return node;
    }

    /**
     * 根据key删除记录
     * @param key
     * @return 删除成功返回true,否则返回false
     */
    public boolean remove(int key) {
        DeleteResult deleteResult = deleteNode(root, key);
        if (deleteResult.success) {
            root = deleteResult.node;
            size--;
        }
        return deleteResult.success;
    }

    /**
     * 删除结点的返回结果
     */
    private static class DeleteResult {
        private BBSTNode node;//节点
        private boolean success;//是否删除成功
        private boolean shorter;//是否变矮

        DeleteResult(BBSTNode node, boolean success, boolean shorter) {
            this.node = node;
            this.success = success;
            this.shorter = shorter;
        }
    }

    /**
     * 内部删除结点的操作
     * @param node
     * @param key
     * @return
     */
    private DeleteResult deleteNode(BBSTNode node, int key) {
        if (node == null) return new DeleteResult(null, false, false);
        if (node.getData().compareByKey(key) < 0) {
            //在右子树中寻找删除的点
            DeleteResult deleteResult = deleteNode(node.getRightChild(), key);
            node.setRightChild(deleteResult.node);

            if (!deleteResult.success) return new DeleteResult(node, false, false);
            if (deleteResult.shorter) {
                switch (node.getBalanceFactor()) {
                    case LEFT_HIGH :
                        //树失衡，进行调整
                        return new DeleteResult(deleteRightBalance(node), true, true);
                    case EQUAL_HIGH :
                        node.setBalanceFactor(LEFT_HIGH);
                        return new DeleteResult(node, true, false);
                    case RIGHT_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        return new DeleteResult(node, true, true);
                }
            }
            return new DeleteResult(node, true, false);

        } else if (node.getData().compareByKey(key) > 0) {
            //在左子树中寻找删除的点
            DeleteResult deleteResult = deleteNode(node.getLeftChild(), key);
            node.setLeftChild(deleteResult.node);

            if (!deleteResult.success) return new DeleteResult(node, false, false);
            if (deleteResult.shorter) {
                switch (node.getBalanceFactor()) {
                    case LEFT_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        return new DeleteResult(node, true, true);
                    case EQUAL_HIGH :
                        node.setBalanceFactor(RIGHT_HIGH);
                        return new DeleteResult(node, true, false);
                    case RIGHT_HIGH :
                        return new DeleteResult(deleteLeftBalance(node), true, true);
                }
            }
            return new DeleteResult(node, true, false);

        } else {
            //删除节点有两个孩子
            if (node.getLeftChild() != null && node.getRightChild() != null) {
                //在右子树中寻找值最小的数据项,并进行替换
                node.setData(findMin(node.getRightChild()));
                //递归调用，进行删除
                DeleteResult deleteResult = deleteNode(node.getRightChild(), node.getData().getKey());
                node.setRightChild(deleteResult.node);
                if (!deleteResult.success) return new DeleteResult(node, false, false);
                if (deleteResult.shorter) {
                    switch (node.getBalanceFactor()) {
                        case LEFT_HIGH :
                            //树失衡，进行调整
                            return new DeleteResult(deleteRightBalance(node), true, true);
                        case EQUAL_HIGH :
                            node.setBalanceFactor(LEFT_HIGH);
                            return new DeleteResult(node, true, false);
                        case RIGHT_HIGH :
                            node.setBalanceFactor(EQUAL_HIGH);
                            return new DeleteResult(node, true, true);
                    }
                }
                return new DeleteResult(node, true, false);
            } else {
                //删除节点只有一个孩子或没有孩子
                node = node.getLeftChild() != null ? node.getLeftChild() : node.getRightChild();
                return new DeleteResult(node, true, true);
            }
        }
    }

    /**
     * 查找最小结点
     * @param node
     * @return
     */
    private Record findMin(BBSTNode node) {
        if (node.getLeftChild() == null) {
            return node.getData();
        } else {
            return findMin(node.getLeftChild());
        }
    }

    /**
     * 在右子树中删除结点之后的平衡调整
     * @param node
     * @return
     */
    private BBSTNode deleteRightBalance(BBSTNode node) {
        BBSTNode leftChild = node.getLeftChild();
        switch (leftChild.getBalanceFactor()) {
            case EQUAL_HIGH :
                node.setBalanceFactor(LEFT_HIGH);
                leftChild.setBalanceFactor(RIGHT_HIGH);
                return rightRotate(node);
            case LEFT_HIGH : /*LL型，右旋处理*/
                node.setBalanceFactor(EQUAL_HIGH);
                leftChild.setBalanceFactor(EQUAL_HIGH);
                return leftLeftRotate(node);
            case RIGHT_HIGH : /*LR型，双旋处理*/
                BBSTNode leftChildRightChild = leftChild.getRightChild();
                switch (leftChildRightChild.getBalanceFactor()) {
                    case LEFT_HIGH :
                        node.setBalanceFactor(RIGHT_HIGH);
                        leftChild.setBalanceFactor(EQUAL_HIGH);
                        leftChildRightChild.setBalanceFactor(EQUAL_HIGH);
                        break;
                    case EQUAL_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        leftChild.setBalanceFactor(EQUAL_HIGH);
                        leftChildRightChild.setBalanceFactor(EQUAL_HIGH);
                        break;
                    case RIGHT_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        leftChild.setBalanceFactor(LEFT_HIGH);
                        leftChildRightChild.setBalanceFactor(EQUAL_HIGH);
                        break;
                }
                return leftRightRotate(node);
        }
        return node;
    }

    /**
     * 在左子树删除结点之后的平衡调整
     * @param node
     * @return
     */
    private BBSTNode deleteLeftBalance(BBSTNode node) {
        BBSTNode rightChild = node.getRightChild();
        switch (rightChild.getBalanceFactor()) {
            case EQUAL_HIGH :
                node.setBalanceFactor(RIGHT_HIGH);
                rightChild.setBalanceFactor(LEFT_HIGH);
                return leftRotate(node);
            case RIGHT_HIGH :/*RR型*/
                node.setBalanceFactor(EQUAL_HIGH);
                rightChild.setBalanceFactor(EQUAL_HIGH);
                return rightRightRotate(node);
            case LEFT_HIGH : /*RL型*/
                BBSTNode rightChildLeftChild = rightChild.getLeftChild();
                switch (rightChildLeftChild.getBalanceFactor()) {
                    case LEFT_HIGH :
                        node.setBalanceFactor(RIGHT_HIGH);
                        rightChild.setBalanceFactor(EQUAL_HIGH);
                        rightChildLeftChild.setBalanceFactor(LEFT_HIGH);
                        break;
                    case EQUAL_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        rightChild.setBalanceFactor(EQUAL_HIGH);
                        rightChildLeftChild.setBalanceFactor(EQUAL_HIGH);
                        break;
                    case RIGHT_HIGH :
                        node.setBalanceFactor(EQUAL_HIGH);
                        rightChild.setBalanceFactor(LEFT_HIGH);
                        rightChildLeftChild.setBalanceFactor(RIGHT_HIGH);
                        break;
                }
                return rightLeftRotate(node);
        }
        return node;
    }


    /**
     * 输出
     */
    public void output() {
        System.out.println("******输出平衡二叉树*****");
        output(root, 1);
    }

    private void output(BBSTNode node, int i) {
        if (node == null) return;
        StringBuilder base = new StringBuilder();
        for (int i0 = 1; i0 < i; i0++) {
            base.append("\t");
        }
        System.out.println(base.toString() + node.getData().getKey());
        output(node.getLeftChild(), i + 1);
        output(node.getRightChild(), i + 1);
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        System.out.println("*****前序遍历*****");
        preOrder(root);
        System.out.println();
    }

    private void preOrder(BBSTNode node) {
        if (node == null) return;
        System.out.print(node.getData().getKey() + ",");
        preOrder(node.getLeftChild());
        preOrder(node.getRightChild());
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        System.out.println("*****后序遍历*****");
        postOrder(root);
        System.out.println();
    }

    private void postOrder(BBSTNode node) {
        if (node == null) return;
        postOrder(node.getLeftChild());
        postOrder(node.getRightChild());
        System.out.print(node.getData().getKey() + ",");
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        System.out.println("*****中序遍历*****");
        inOrder(root);
        System.out.println();
    }

    private void inOrder(BBSTNode node) {
        if (node == null) return;
        inOrder(node.getLeftChild());
        System.out.print(node.getData().getKey() + ",");
        inOrder(node.getRightChild());
    }
}
