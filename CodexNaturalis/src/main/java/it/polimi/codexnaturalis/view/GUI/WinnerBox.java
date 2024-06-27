package it.polimi.codexnaturalis.view.GUI;

import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.utils.observer.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class WinnerBox extends AlertBox{

    ObservableList<String> lead;

    protected Scene scene() {
        Label request = new Label("Il vincitore é " + lead.getFirst());
        request.setFont(new Font("Arial", 30));
        Button endButton = new Button("Yay!");

        ListView<String> leaderboard = new ListView<>();

        leaderboard.setItems(lead);
        Pane winneingPane = new Pane();

        request.setTranslateY(-200);
        endButton.setTranslateY(200);

        winneingPane.setPrefSize(600,600);
        winneingPane.getChildren().add(request);
        winneingPane.getChildren().add(leaderboard);
        winneingPane.getChildren().add(endButton);

        endButton.setOnAction(e -> {
            retstring = "end";
            alertWindow.close();
        });

        return new Scene(winneingPane);
    }

    public void setLeaderboard(TreeMap<Integer, String> list){
        list.descendingKeySet().forEach(i ->{
            lead.add(") " + list.get(i));
        });
        for(int i=0 ; i<=lead.size(); i++){
            lead.set(i, Integer.toString(i+1) + lead.get(i));
        }
    }
}
