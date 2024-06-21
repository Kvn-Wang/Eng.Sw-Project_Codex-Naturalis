package it.polimi.codexnaturalis.view.GUI;

import com.google.gson.Gson;
import it.polimi.codexnaturalis.model.mission.Mission;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class MissionSelectBox extends AlertBox {
    private Mission mission1;
    private Mission mission2;
    protected Scene scene() {
        Gson gson = new Gson();
        Label request = new Label("seleziona la missione");
        String mission1Path = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+mission1.getPngNumber()+".png";
        String mission2Path = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+mission2.getPngNumber()+".png";
        Rectangle missionRect1= new Rectangle(170, 100, new ImagePattern(new Image(getClass().getResourceAsStream(mission1Path))));
        missionRect1.setStroke(null);
        Rectangle missionRect2= new Rectangle(170, 100, new ImagePattern(new Image(getClass().getResourceAsStream(mission2Path))));
        missionRect2.setStroke(null);

        missionRect1.setOnMouseClicked(e -> {
            retstring=gson.toJson(mission1);
            alertWindow.close();
        });
        missionRect2.setOnMouseClicked(e -> {
            retstring=gson.toJson(mission2);
            alertWindow.close();
        });

        VBox box = new VBox(10);
        box.getChildren().add(request);
        box.getChildren().add(missionRect1);
        box.getChildren().add(missionRect2);

        return new Scene(box);
    }

    public void setMission(Mission m1,Mission m2){
        mission1=m1;
        mission2=m2;
    }
}
