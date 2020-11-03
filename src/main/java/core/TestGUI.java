package core;

import controller.GraphicsTree;
import core.BSTree;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class TestGUI extends Application {

    public static void main(String[] args) {
        // GUI
        launch(args);
    }

    public void start(Stage stage) {
        stage.setTitle("Drawing the BST");
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 500, 500);

        BSTree<Integer> myTree = createModel();
        GraphicsTree<Integer> c = new GraphicsTree<>(myTree);


        c.widthProperty().bind(scene.widthProperty());
        c.heightProperty().bind(scene.heightProperty());
        root.getChildren().add(c);
        stage.setScene(scene);
        stage.show();
        myTree.preOrder();

    }

    private BSTree<Integer> createModel() {
        BSTree<Integer> myTree = new BSTree<>();
        myTree.insert(70);
        myTree.insert(10);
        myTree.insert(90);
        myTree.insert(80);
       // myTree.insert(20);
        //myTree.insert(50);
       // myTree.insert(30);
        System.out.println(myTree.testAVL());
        return myTree;
    }



}
