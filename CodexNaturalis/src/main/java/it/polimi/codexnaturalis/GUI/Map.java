package it.polimi.codexnaturalis.GUI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Map extends Application {
    private Parent createContent() {
        Rectangle box = new Rectangle(100,50, Color.BLUE);

        Transform(box);

        return new Pane(box);
    }
    private void Transform(Rectangle box){

    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent(), 300, 300));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
