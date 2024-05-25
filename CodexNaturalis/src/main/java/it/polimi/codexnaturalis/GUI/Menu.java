package it.polimi.codexnaturalis.GUI;

import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Menu extends Application {

    private static VirtualServer vnc;

     static Stage gameWindow;
     private static Scene startScene;
     private static Scene nickScene;
     private static Scene lobbyListScene;

    public static void main(String[] args) {
        launch(args);
    }
    public static void setupMenu(VirtualServer virtualNetworkCommand){
        vnc = virtualNetworkCommand;
    }

    public static void setNickname(boolean outcome, String nickname){
        if(outcome){
            gameWindow.setScene(lobbyListScene);
        } else{
            Popup popup = new Popup();
            Button closePop = new Button("close");
            Label nick = new Label(nickname+" é già stato selezionato");
            closePop.setOnAction(event -> {
                if (popup.isShowing()) {
                    popup.hide();
                }
            });
            VBox vBox = new VBox(
                    nick,
                    closePop
            );

            popup.getContent().add(vBox);
            closePop.setTranslateY(20);
            popup.show(gameWindow);
        }
    }

    private static void updateLobbyList(TableView lobbyList){
        ArrayList<LobbyInfo> lobbyInfo;

        try {
            lobbyInfo = vnc.getAvailableLobby();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        for(LobbyInfo lobby : lobbyInfo){
            lobbyList.getItems().add(lobby);
        }

    }

    private static void createLobby(TableView lobbyList){

    }

    @Override
    public void start(Stage gameStage) throws Exception {
        Menu.gameWindow = gameStage;
        startScene = startScene();
        nickScene = nickScene();
        lobbyListScene = lobbyListScene();
        gameWindow.setScene(startScene);
        gameWindow.show();
    }

    private static Scene startScene() throws Exception {
        gameWindow.setTitle("CodexNaturalis");
        Button play = new Button("PLAY");
        Label title = new Label("Codex Naturalis");

        title.setFont(new Font("Arial", 30));
        title.setTranslateY(-100);


        play.setTranslateY(20);
        play.setPrefSize(100, 50);
        play.setOnAction(actionEvent -> gameWindow.setScene(nickScene));

        StackPane menuPane = new StackPane(
                title,
                play
        );
        return new Scene(menuPane, 500, 300);
    }

    private static Scene nickScene() throws Exception {

        Button back = new Button("<-");
        TextField nickname = new TextField();
        Label confirm = new Label("press Enter to confirm");

        confirm.setVisible(false);
        confirm.setFont(new Font("Arial", 20));

        nickname.setFont(new Font("Arial", 20));
        nickname.setTranslateY(-40);
        nickname.setMaxWidth(300);
        nickname.setText("inserisci il nickname");
        nickname.setOnMouseClicked(event -> {
            nickname.clear();
            confirm.setVisible(true);
        });
        nickname.setOnAction(event -> {
            try {
                vnc.setNickname(null, nickname.getText());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        back.setTranslateX(-200);
        back.setTranslateY(-100);
        back.setOnAction(event -> gameWindow.setScene(startScene));

        StackPane menuPane = new StackPane(
                nickname,
                back,
                confirm
        );
        return new Scene(menuPane, 500, 300);
    }

    private static Scene lobbyListScene() throws Exception {

        Button back = new Button("<-");
        Button refresh = new Button("refresh");
        Button create = new Button("create lobby");
        TableView lobbyTable = new TableView();

        TableColumn<String, String> column1 =
                new TableColumn<>("Lobby Name");
        column1.setCellValueFactory(
                new PropertyValueFactory<>("LobbyName"));

        TableColumn<String, String> column2 =
                new TableColumn<>("Started");
        column2.setCellValueFactory(
                new PropertyValueFactory<>("IsLobbyStarted"));

        TableColumn<String, String> column3 =
                new TableColumn<>("Players");
        column3.setCellValueFactory(
                new PropertyValueFactory<>("MaxPlayer"));

        lobbyTable.getColumns().add(column1);
        lobbyTable.setMaxWidth(300);
        lobbyTable.getColumns().add(column2);
        lobbyTable.getColumns().add(column3);

        back.setTranslateX(-200);
        back.setTranslateY(-100);
        back.setOnAction(actionEvent -> gameWindow.setScene(nickScene));

        refresh.setTranslateX(-200);
        refresh.setTranslateY(100);
        refresh.setOnAction(actionEvent -> {
            lobbyTable.getItems().clear();
            updateLobbyList(lobbyTable);
        });

        create.setTranslateX(-200);
        create.setTranslateY(0);
        create.setOnAction(actionEvent -> {

        });

        StackPane menuPane = new StackPane(
                lobbyTable,
                refresh,
                back
        );
        return new Scene(menuPane, 500, 300);
    }

    private void lobbyScene(Stage gameStage) throws Exception {

        Button back = new Button("<-");
        VBox playerBox = new VBox();


        TableView lobby = new TableView();

        TableColumn<String, String> column1 =
                new TableColumn<>("Lobby Name");
        column1.setCellValueFactory(
                new PropertyValueFactory<>("LobbyName"));

        TableColumn<String, String> column2 =
                new TableColumn<>("Started");
        column2.setCellValueFactory(
                new PropertyValueFactory<>("IsLobbyStarted"));

        TableColumn<String, String> column3 =
                new TableColumn<>("Players");
        column3.setCellValueFactory(
                new PropertyValueFactory<>("MaxPlayer"));


        lobby.getColumns().add(column1);
        lobby.setMaxWidth(300);
        lobby.getColumns().add(column2);
        lobby.getColumns().add(column3);
        lobby.getItems().add(
                new LobbyInfo("Lobby1", false, 3));
        lobby.getItems().add(
                new LobbyInfo("Lobby2", false, 4));
        lobby.getItems().add(
                new LobbyInfo("Kevin stellina", true, 1));


        back.setTranslateX(-200);
        back.setTranslateY(-100);
        back.setOnAction(actionEvent -> gameWindow.setScene(lobbyListScene));

        StackPane menuPane = new StackPane(
                lobby,
                back
        );
        gameStage.setScene(new Scene(menuPane, 500, 300));
    }

    private String playerJoin(String nickname){
        Label player = new Label();
        return nickname;
    }
}
