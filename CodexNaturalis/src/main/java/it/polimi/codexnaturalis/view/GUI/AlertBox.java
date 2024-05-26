package it.polimi.codexnaturalis.view.GUI;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class AlertBox {

    public void display(String name, double width, double height){
        Stage alertWindow = new Stage();

        alertWindow.setMinWidth(width);
        alertWindow.setMinWidth(height);
        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(name);

        Scene scene = scene();

        alertWindow.setScene(scene);
        alertWindow.showAndWait();
    }

    protected Scene scene() {
        return null;
    }
}
