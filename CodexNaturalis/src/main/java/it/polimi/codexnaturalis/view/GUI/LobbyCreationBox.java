package it.polimi.codexnaturalis.view.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LobbyCreationBox extends AlertBox{

    protected Scene scene() {
        Label request = new Label("inserisci il nome della lobby");
        TextField name = new TextField();

        VBox box = new VBox(10);
        box.getChildren().add(request);
        box.getChildren().add(name);

        return new Scene(box);
    }
}
