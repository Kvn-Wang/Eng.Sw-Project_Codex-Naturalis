package it.polimi.codexnaturalis.GUI;

import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.view.VirtualNetworkCommand;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class Menu extends Application {

    private static VirtualNetworkCommand vnc;

    static Stage mainStage;
    public static void main(String[] args) {
        launch(args);
    }
    public static void setupMenu(VirtualNetworkCommand virtualNetworkCommand){
        vnc = virtualNetworkCommand;
    }
    public static void setupNickname(boolean outcome, String nickname){
        //if(outcome){

       // }
       // else{
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
            popup.show(mainStage);

       // }
    }
    @Override
    public void start(Stage gameStage) throws Exception {
        startScene(gameStage);
        mainStage = gameStage;
        gameStage.show();
    }

    private void startScene(Stage gameStage) throws Exception {
        gameStage.setTitle("CodexNaturalis");
        Button play = new Button("PLAY");
        Label title = new Label("Codex Naturalis");;

        title.setFont(new Font("Arial", 30));
        title.setTranslateY(-100);


        play.setTranslateY(20);
        play.setPrefSize(100, 50);
        play.setOnAction(actionEvent -> {
            try {
                rmiScene(gameStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        StackPane menuPane = new StackPane(
                title,
                play
        );
        gameStage.setScene(new Scene(menuPane, 500, 300));
    }

    private void rmiScene(Stage menuStage) throws Exception {

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
                vnc.selectNickname(nickname.getText());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        /*
            try {
                lobbyListScene(menuStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
          }
         */
        });

        back.setTranslateX(-200);
        back.setTranslateY(-100);
        back.setOnAction(actionEvent -> {
            try {
                startScene(menuStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        StackPane menuPane = new StackPane(
                nickname,
                back,
                confirm
        );
        menuStage.setScene(new Scene(menuPane, 500, 300));
    }

    private void lobbyListScene(Stage menuStage) throws Exception {

        Button back = new Button("<-");
        TableView lobbyList = new TableView();

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


        lobbyList.getColumns().add(column1);
        lobbyList.setMaxWidth(300);
        lobbyList.getColumns().add(column2);
        lobbyList.getColumns().add(column3);
        lobbyList.getItems().add(
                new LobbyInfo("Lobby1", false, 3));
        lobbyList.getItems().add(
                new LobbyInfo("Lobby2", false, 4));
        lobbyList.getItems().add(
                new LobbyInfo("Kevin stellina", true, 1));


        back.setTranslateX(-200);
        back.setTranslateY(-100);
        back.setOnAction(actionEvent -> {
            try {
                rmiScene(menuStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        StackPane menuPane = new StackPane(
                lobbyList,
                back
        );
        menuStage.setScene(new Scene(menuPane, 500, 300));
    }

    private void lobbyScene(Stage menuStage) throws Exception {

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
        back.setOnAction(actionEvent -> {
            try {
                lobbyListScene(menuStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        StackPane menuPane = new StackPane(
                lobby,
                back
        );
        menuStage.setScene(new Scene(menuPane, 500, 300));
    }

    private String playerJoin(String nickname){
        Label player = new Label();
        return nickname;
    }

    /*

    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox(new Label("A JavaFX Label"));
        Scene scene = new Scene(vBox);

        stage.setScene(scene);
        stage.show();
    }
     */
}
