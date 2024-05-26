package it.polimi.codexnaturalis.view.GUI;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class AlertBox {

    protected String retstring;
    protected Stage alertWindow = new Stage();
    public String display(String name, double width, double height){
        alertWindow.setMinWidth(width);
        alertWindow.setMinWidth(height);
        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(name);

        Scene scene = scene();

        alertWindow.setScene(scene);
        alertWindow.showAndWait();

        return retstring;
    }

    protected Scene scene() {
        return null;
    }
}
