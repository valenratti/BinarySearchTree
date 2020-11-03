package core;

public interface BSTreeInterface <T extends Comparable<? super T>> extends Iterable<T>{

    enum Traversal {BYLEVELS, INORDER, PREORDER, POSTORDER}

    void insert(T myData);

    void delete(T myData);

    boolean contains(T myData);

    void preOrder();

    void postOrder();

    void inOrder();

    int getHeight();

    NodeTreeInterface<T> getRoot();

    void printByLevels();

    void setTraversal(Traversal traversal);

    void inRange(T min, T max);

    boolean testAVL();


}
