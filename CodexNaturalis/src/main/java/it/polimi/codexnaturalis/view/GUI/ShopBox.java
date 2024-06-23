package it.polimi.codexnaturalis.view.GUI;

import com.google.gson.Gson;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class ShopBox extends AlertBox {
    private ArrayList<ResourceCard> resourceCards;
    private ArrayList<ObjectiveCard> objectiveCards;

    protected Scene scene() {
//        Label request = new Label("Shop");
//        Gson gson = new Gson();
//        String mission1Path = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+mission1.getPngNumber()+".png";
//        String mission2Path = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+mission2.getPngNumber()+".png";
//        Rectangle missionRect1 = new Rectangle();
//        missionRect1.setStroke(null);
//        missionRect1.setFill(new ImagePattern(new Image("path" + mission1.getPngNumber() + ".png")));
//        Rectangle missionRect2 = new Rectangle();
//        missionRect2.setStroke(null);
//        missionRect2.setFill(new ImagePattern(new Image("path" + mission2.getPngNumber() + ".png")));
//
//
//        missionRect1.setOnMouseClicked(e -> {
//            retstring = "1";
//            alertWindow.close();
//        });
//        missionRect2.setOnMouseClicked(e -> {
//            retstring = "2";
//            alertWindow.close();
//        });
//
        VBox box = new VBox(10);
//        box.getChildren().add(request);
//        box.getChildren().add(missionRect1);
//        box.getChildren().add(missionRect2);
//
        return new Scene(box);
    }

    public void setMission(Mission m1, Mission m2){
    //    resourceCards.add();
    //    objectiveCards
    //    mission2=m2;
    }
}

