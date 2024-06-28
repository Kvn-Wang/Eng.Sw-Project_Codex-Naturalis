package it.polimi.codexnaturalis.view.GUI;

import com.google.gson.Gson;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class ShopBox extends AlertBox {
    private ArrayList<ResourceCard> resourceCards;
    private ArrayList<ObjectiveCard> objectiveCards;
    private ClientContainer cc;

    protected Scene scene() {
        Label shop1 = new Label("Resource Shop");
        Label shop2 = new Label("Objective Shop");
        Gson gson = new Gson();
        Card topResourceCard = cc.getTopDeckResourceCardShop();
        Card topObjectiveCard = cc.getTopDeckObjCardShop();
        Card[] resourceCards = cc.getVisibleResourceCardShop();
        Card[] objectiveCards = cc.getVisibleObjectiveCardShop();

        topResourceCard.setIsBack(true);
        topObjectiveCard.setIsBack(true);

        String topResourceCardPath = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_back/"+topResourceCard.getPng()+".png";
        String topObjectiveCardPath = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_back/"+topObjectiveCard.getPng()+".png";
        Rectangle topRecCardRect = new Rectangle(170, 100, new ImagePattern(new Image(getClass().getResourceAsStream(topResourceCardPath))));
        topRecCardRect.setStroke(null);
        Rectangle topObjCardRect = new Rectangle(170, 100, new ImagePattern(new Image(getClass().getResourceAsStream(topObjectiveCardPath))));
        topObjCardRect.setStroke(null);


        HBox resourceShop = new HBox();
        resourceShop.getChildren().add(topRecCardRect);
        topRecCardRect.setOnMouseClicked(e -> {
            retstring = "r0";
            alertWindow.close();
        });

        for(int i = 0; i<resourceCards.length; i++){
            int num = i+1;
            String resourceCardPath = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+ resourceCards[i].getPng() + ".png";
            Rectangle resourceCardRect = new Rectangle(170, 100, new ImagePattern(new Image(getClass().getResourceAsStream(resourceCardPath))));
            topRecCardRect.setStroke(null);
            resourceCardRect.setOnMouseClicked(e -> {
                retstring = "r"+Integer.toString(num);
                alertWindow.close();
            });
            resourceShop.getChildren().add(resourceCardRect);
        }


        HBox objectiveShop = new HBox();
        objectiveShop.getChildren().add(topObjCardRect);
        topObjCardRect.setOnMouseClicked(e -> {
            retstring = "o0";
            alertWindow.close();
        });

        for(int i = 0; i<objectiveCards.length; i++){
            int num = i+1;
            String objectiveCardPath = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+ objectiveCards[i].getPng() + ".png";
            Rectangle objectiveCardRect = new Rectangle(170, 100, new ImagePattern(new Image(getClass().getResourceAsStream(objectiveCardPath))));
            topRecCardRect.setStroke(null);
            objectiveCardRect.setOnMouseClicked(e -> {
                retstring = "o"+Integer.toString(num);
                alertWindow.close();
            });
            objectiveShop.getChildren().add(objectiveCardRect);
        }

        VBox box = new VBox(10);
        box.getChildren().add(shop1);
        box.getChildren().add(resourceShop);
        box.getChildren().add(shop2);
        box.getChildren().add(objectiveShop);

        return new Scene(box);
    }

    public void setCC(ClientContainer clientContainer){
        cc=clientContainer;
    }
}

