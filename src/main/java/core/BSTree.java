package core;

import java.util.*;

public class BSTree<T extends Comparable<? super T>> implements BSTreeInterface<T>  {

    private Node<T> root;
    String exp;
    public int height;
    Traversal traversal = Traversal.BYLEVELS;

    public static void main(String args[]){
        BSTree<Integer> BSTree = new BSTree<>();
        BSTree.insert(10);
        BSTree.insert(50);
        BSTree.insert(25);
        BSTree.insert(5);
        BSTree.insert(30);
        BSTree.insert(30);
        BSTree.inRange(20,60);

    }

    public BSTree(){
        root = new Node<T>();
    }

    @Override
    public void insert(T myData) {
        Node<T> current = root;
        Node<T> prev = root;
        int currentHeight = 0;
        boolean rightOrLeft = false; // if false insert on left, else insert on right
        if(root.data == null){
            root.data = myData;
            return;
        }
        while(current != null){
            prev = current;
            if(myData.compareTo(current.getData()) <= 0) {
                current = current.left;
                rightOrLeft = false;
                currentHeight++;
            }
            else{
                current = current.right;
                rightOrLeft = true;
                currentHeight++;
            }
        }
        if(!rightOrLeft)
            prev.left = new Node<T>(myData);
        else
            prev.right = new Node<T>(myData);
        if(currentHeight>height)
            height = currentHeight;
    }

    @Override
    public void delete(T myData) {
        Node<T> current = root;
        Node<T> prev = root;
        boolean rightOrLeft = false; // if false insert on left, else insert on right

        //Search the node to be deleted
        while(current != null && myData.compareTo(current.data) != 0){
            prev = current;
            if(myData.compareTo(current.data) < 0) {
                current = current.left;
                rightOrLeft = false;
            }
            if(myData.compareTo(current.data) > 0) {
                current = current.right;
                rightOrLeft = true;
            }
        }
        if(current == null) throw new NoSuchElementException("Element to be deleted hasn't been found.");
        //R1(hoja)
        if(current.right == null && current.left == null){
            height--;
            if(rightOrLeft)
                prev.right = null;
            else prev.left = null;
        }//R2(solo 1 hijo)
        else if((current.right != null || current.left != null) && (current.left == null || current.left == null)){
            height--;
            Node<T> aux = (current.right != null) ? current.right : current.left;
            if(rightOrLeft)
                prev.right = aux;
            else
                prev.left = aux;
        }//R3 (dos hijos)
        else{
            Node<T> auxCurrent = current.left;
            while(auxCurrent.right != null){
                auxCurrent = auxCurrent.right;
            }
            T data = auxCurrent.data;
            delete(data);
            current.data = data;
        }

    }



    @Override
    public boolean contains(T myData) {
        Node<T> current = root;
        while(current != null){
            if(myData.compareTo(current.getData()) < 0)
                current = current.left;
            else if(myData.compareTo(current.getData()) > 0)
                current = current.right;
            else
                return true;
        }
        return false;
    }

    @Override
    public void preOrder() {
        System.out.println(preOrder(new StringBuilder(), root).toString());
    }

    @Override
    public void postOrder() {
        System.out.println(postOrder(new StringBuilder(), root).toString());
    }

    @Override
    public void inOrder() {
        System.out.println(inOrder(new StringBuilder(), root).toString());
    }

    private StringBuilder inOrder(StringBuilder s, Node<T> n) {
        if(n != null) {
            s.append(inOrder(new StringBuilder(), n.left));
            s.append(n.data + " ");
            s.append(inOrder(new StringBuilder(), n.right));
            return s;
        }else
            return new StringBuilder("");
    }

    private StringBuilder preOrder(StringBuilder s, Node<T> n) {
        if(n != null) {
            s.append(n.data + " ");
            s.append(preOrder(new StringBuilder(), n.left));
            s.append(preOrder(new StringBuilder(), n.right));
            return s;
        }else
            return new StringBuilder("");
    }

    private StringBuilder postOrder(StringBuilder s, Node<T> n) {
        if(n != null) {
            s.append(postOrder(new StringBuilder(), n.left));
            s.append(postOrder(new StringBuilder(), n.right));
            s.append(n.data + " ");
            return s;
        }else
            return new StringBuilder("");
    }



    @Override
    public int getHeight() {
        return height;
    }

    private int getHeight(NodeTreeInterface<T> node){
        if (node == null)
            return -1;
        else
            return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
    }

    @Override
    public boolean testAVL() {
        if(getHeight(root.getLeft())-getHeight(root.getRight()) > 1)
            return false;
        return true;
    }


    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public void printByLevels() {
        if(root == null)
            return;

        LinkedList<Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            Node<T> current = queue.removeFirst();

            if(current!=null) {
                System.out.print(String.format("%s ",current.data));
                if(current.left != null) queue.add(current.left);
                if(current.right != null) queue.add(current.right);
            }
        }
    }

    @Override
    public void setTraversal(Traversal traversal) {
        this.traversal = traversal;
    }

    @Override
    public void inRange(T min, T max){
        if(min.compareTo(max) > 0)
            return;
        inRangeRec(root, min, max);
    }

    private void inRangeRec(Node node, T min, T max) {
        if(node == null)
            return;
        if(node.data.compareTo(min) < 0)//n<min
            inRangeRec(node.right, min, max);
        else{//n>=min
            inRangeRec(node.left, min, max);
            if(node.data.compareTo(max) <= 0){//n<=max
                System.out.print(node.data + " ");
                inRangeRec(node.right, min, max);
            }//n>max => ret
        }
    }


    @Override
    public Iterator<T> iterator() {
        switch(traversal) {
            case BYLEVELS: return new BSTreeByLevelIterator();
            case INORDER: return new BSTreeInOrderIterator();
            case PREORDER: return new BSTreePreOrderIterator();
            case POSTORDER: return new BSTreePostOrderIterator();
        }
        return null;
    }

    private class BSTreeInOrderIterator implements Iterator<T>{
        LinkedList<T> list;

        public BSTreeInOrderIterator(){
            list = new LinkedList<>();
            addToList(root);
        }

        private void addToList(Node<T> node){
            if(node.left != null) addToList(node.left);
            list.add(node.data);
            if(node.right != null) addToList(node.right);
        }

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public T next() {
            T current = list.removeFirst();
            return current;
        }
    }//End of iterator inorder class

    private class BSTreePreOrderIterator implements Iterator<T>{
        LinkedList<T> list;

        public BSTreePreOrderIterator(){
            list = new LinkedList<>();
            addToList(root);
        }

        private void addToList(Node<T> node){
            list.add(node.data);
            if(node.left != null) addToList(node.left);
            if(node.right != null) addToList(node.right);
        }

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public T next() {
            T current = list.removeFirst();
            return current;
        }
    }//End of iterator preorder class


    private class BSTreePostOrderIterator implements Iterator<T>{
        LinkedList<T> list;

        public BSTreePostOrderIterator(){
            list = new LinkedList<>();
            addToList(root);
        }

        private void addToList(Node<T> node){
            if(node.left != null) addToList(node.left);
            if(node.right != null) addToList(node.right);
            list.add(node.data);
        }

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public T next() {
            T current = list.removeFirst();
            return current;
        }
    }//End of iterator postorder class


    private class BSTreeByLevelIterator implements Iterator<T> {
        LinkedList<Node<T>> queue = new LinkedList<>();

        public BSTreeByLevelIterator() {
            if (root != null) queue.add(root);
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            Node<T> current = queue.removeFirst();
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
            return current.data;
        }
    }//End of iterator by level class

    static private class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T>{

        private T data;
        private Node left, right;


        public Node(){

        }
        public Node(T data) {
            this.data = data;
        }

        @Override
        public T getData() {
            return data;
        }

        @Override
        public NodeTreeInterface<T> getLeft() {
            return left;
        }

        @Override
        public NodeTreeInterface<T> getRight() {
            return right;
        }
    }//End of node Class


}
