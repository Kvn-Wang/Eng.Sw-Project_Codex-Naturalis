package it.polimi.codexnaturalis.GUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
public class menu extends Application{
    Button RMI;
    Button Socket;
    Stage window;
    Scene connectionScene;
    Scene lobbyScene;
    public void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage selectionMenu) throws IOException{
        window = selectionMenu;
        Label connectionLabel = new Label("Select type of connection");
        Label typeOfLobbyLabel = new Label("choose to create or join a lobby");
        RMI = new Button("RMI");
        RMI.setOnAction(e -> System.out.println("RMI"));
        RMI.setOnAction(e ->window.setScene(lobbyScene));
        Socket = new Button("Socket");
        Socket.setOnAction(e -> System.out.println("Socket"));
        Socket.setOnAction(e ->window.setScene(lobbyScene));
        VBox layout = new VBox(20);
        layout.getChildren().addAll(connectionLabel, RMI);
        layout.getChildren().addAll(typeOfLobbyLabel,Socket);
        connectionScene = new Scene(layout, 300,250);
        selectionMenu.setScene(connectionScene);
        selectionMenu.show();
    }
}
