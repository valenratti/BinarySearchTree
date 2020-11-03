package core;

import javax.rmi.CORBA.Util;
import java.util.Scanner;

public class ExpTree {
    private Node root;
    private String exp;

    public static void main(String args[]){
        ExpTree myExp = new ExpTree("( ( 2 + 3.5 ) * -10 )\n");
        System.out.println(myExp.evalExp());
        System.out.print(myExp.preorder());
    }

    public ExpTree(String infija) {
        // token analyzer
        Scanner inputScanner = new Scanner(infija).useDelimiter("\\n");
        String line = inputScanner.nextLine();
        inputScanner.close();
        buildTree(line);
    }



    public ExpTree() {
        System.out.print("Introduzca la expresion en notacion infija con todos los parentesis y blancos: ");

        // token analyzer
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        String line= inputScanner.nextLine();
        inputScanner.close();

        buildTree(line);
    }



    private void buildTree(String line)
    {
        // space separator among tokens
        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        root= new Node(lineScanner);
        lineScanner.close();
    }


    public String inorder() {
        exp = "";
        recInorder(root);
        return exp;
    }



    private void recInorder(Node n){
        if(Utils.isOperator(n.data)){
            exp += "(";
            recInorder(n.left);
            exp += n.data += " ";
            recInorder(n.right);
            exp += ")";
        }
        else
            exp += n.data += " ";
    }

    public String preorder(){
        exp = "";
        recPreorder(root);
        return exp;
    }

    private void recPreorder(Node n){
        if(Utils.isOperator(n.data)){
            exp += n.data + " ";
            recPreorder(n.left);
            recPreorder(n.right);
        }
        else
            exp += n.data + " ";
    }

    public String postorder(){
        exp = "";
        recPostorder(root);
        return exp;
    }

    private void recPostorder(Node n){
        if(Utils.isOperator(n.data)){
            recPostorder(n.left);
            recPostorder(n.right);
        }
        exp += n.data + " ";
    }

    public double evalExp(){
        double ret = evalRec(root);
        return ret;
    }

    private double evalRec(Node n){
        if(core.Utils.isOperator(n.data.replaceAll("\\s",""))){
            char auxi = n.data.charAt(0);
            switch(auxi){
                case '+' : return (evalRec(n.left) + evalRec(n.right));
                case '-' : return (evalRec(n.left) - evalRec(n.right));
                case '*' : return (evalRec(n.left) * evalRec(n.right));
                case '/' : return (evalRec(n.left) / evalRec(n.right));
                case '^' : return (Math.pow(evalRec(n.left),evalRec(n.right)));
            }
        }else {
            return Double.valueOf(n.data);
        }
        return 0;
    }


    private static final class Node {
        private String data;
        private Node left, right;
        private Node before;

        private int visited = 0; // 0 never visited, 1 visited once, 2 visited twice

        private Scanner lineScanner;


        public Node(Scanner theLineScanner) {
            lineScanner= theLineScanner;

            Node auxi = buildExpression();
            data= auxi.data;
            left= auxi.left;
            right= auxi.right;
            left.before = this;
            right.before = this;

            if (lineScanner.hasNext() )
                throw new RuntimeException("Bad expression");
        }

        private Node() 	{
        }




        private Node buildExpression() {
            Node n = new Node();

            if (lineScanner.hasNext("\\(")) {
                lineScanner.next(); //lo consumo

                n.left = buildExpression(); //subexpresion

                //operator
                if (!lineScanner.hasNext())
                    throw new RuntimeException("missing or invalid operator");

                n.data = lineScanner.next();

                if (!Utils.isOperator(n.data))
                    throw new RuntimeException("missng or invalid operator");

                n.right = buildExpression();

                // ) expected
                if (lineScanner.hasNext("\\)"))
                    lineScanner.next();
                else throw new RuntimeException("missing )");

                return n;
            } //constant
            else {

                if (!lineScanner.hasNext())
                    throw new RuntimeException("missing expression");

                n.data = lineScanner.next();

                if (!Utils.isConstant(n.data))
                    throw new RuntimeException(String.format("illegal termin %s", lineScanner));

            }
            return n;
        }

    }  // end Node class


}
