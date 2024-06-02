package it.polimi.codexnaturalis.view.GUI;

import it.polimi.codexnaturalis.model.mission.Mission;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class CommonMissionBox extends AlertBox{
    private Mission mission1;
    private Mission mission2;
    protected Scene scene() {
        Label request = new Label("Missioni Comuni");
        Rectangle missionRect1= new Rectangle();
        missionRect1.setStroke(null);
        missionRect1.setFill(new ImagePattern(new Image("path"+mission1.getPngNumber()+".png")));
        Rectangle missionRect2= new Rectangle();
        missionRect2.setStroke(null);
        missionRect2.setFill(new ImagePattern(new Image("path"+mission2.getPngNumber()+".png")));


        VBox box = new VBox(10);
        box.getChildren().add(request);
        box.getChildren().add(missionRect1);
        box.getChildren().add(missionRect2);

        return new Scene(box);
    }
    public void setMission(Mission m1, Mission m2){
        mission1=m1;
        mission2=m2;
    }
}
