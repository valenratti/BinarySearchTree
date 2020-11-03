package parcial;


public class FiboTree {
    private Node root;

    public FiboTree(int n) {
        if(n<0) throw new IllegalArgumentException("No se aceptan valores negativos");
        root = FiboTreeRecur(n);
    }

    private Node FiboTreeRecur( int n){
        if(n <= 0) return null;
        if(n==1) return new Node();
        Node aux = new Node();
        aux.left = FiboTreeRecur(n-1);
        aux.right = FiboTreeRecur(n-2);
        return aux;
    }

    public Node getRoot() {
        return root;
    }


    private class Node implements NodeFiboInterface{
        private String data;
        private Node left;
        private Node right;

        public Node() {
            this.data = "*";
        }

        @Override
        public String getData() {
            return data;
        }

        @Override
        public NodeFiboInterface getLeft() {
            return left;
        }

        @Override
        public NodeFiboInterface getRight() {
            return right;
        }
    }

}
