package it.polimi.codexnaturalis.view.GUI;

import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Menu extends Application {

    private static VirtualServer vnc;

     static Stage gameWindow;
     private static Scene startScene;
     private static Scene nickScene;
     private static Scene lobbyListScene;
     private static Scene lobbyScene;
/*     private static final BackgroundImage bgi = new BackgroundImage(
             new Image(UtilCostantValue.pathToBackGroundImg),
             BackgroundRepeat.NO_REPEAT,
             BackgroundRepeat.NO_REPEAT,
             BackgroundPosition.CENTER,
             new BackgroundSize(
                     100,
                     100,
                     true,
                     true,
                     true,
                     true
             )
             );

 */
    public static void main(String[] args) {
        launch(args); //
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

    private static void updateLobbyList(ObservableList<LobbyInfo> lobbyList){
        ArrayList<LobbyInfo> lobbyInfoList;

        try {
            lobbyInfoList = vnc.getAvailableLobby();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        lobbyList.addAll(lobbyInfoList);
    }

    private static void updatePlayerList(ObservableList<String> playerList){
        String[] players = new String[0];

        /*try {
            players = vnc.funzione mancante;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }*/

        playerList.addAll(players);
    }

    private static void createLobby(){
        LobbyCreationBox lobbyAlert = new LobbyCreationBox();
        try {
            vnc.createLobby("piggo", lobbyAlert.display("Create lobby", 100,100));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        gameWindow.setScene(lobbyScene);
    }


    @Override
    public void start(Stage gameStage) throws Exception {
        Menu.gameWindow = gameStage;
        startScene = startScene();
        nickScene = nickScene();
        lobbyListScene = lobbyListScene();
        lobbyScene = lobbyScene();
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

        //Background bg = new Background(bgi);
        //menuPane.setBackground(bg);
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
        TableView<LobbyInfo> lobbyTable = new TableView<>();
        ObservableList<LobbyInfo> lobbyList = FXCollections.observableArrayList();

        TableColumn<String, String> column1 =
                new TableColumn<>("Lobby Name");
        column1.setCellValueFactory(
                new PropertyValueFactory<>("lobbyName"));

        TableColumn<String, String> column2 =
                new TableColumn<>("Started");
        column2.setCellValueFactory(
                new PropertyValueFactory<>("isLobbyStarted"));

        TableColumn<String, String> column3 =
                new TableColumn<>("Players");
        column3.setCellValueFactory(
                new PropertyValueFactory<>("maxPlayer"));

        updateLobbyList(lobbyList);
        lobbyTable.setMaxWidth(300);
        lobbyTable.setItems(lobbyList);

        back.setTranslateX(-200);
        back.setTranslateY(-100);
        back.setOnAction(actionEvent -> gameWindow.setScene(nickScene));

        refresh.setTranslateX(-200);
        refresh.setTranslateY(100);
        refresh.setOnAction(actionEvent -> {
            lobbyTable.getItems().clear();
            updateLobbyList(lobbyList);
        });
        lobbyTable.setRowFactory(tv -> {
                    TableRow<LobbyInfo> row = new TableRow<>();
                    row.setOnMouseClicked(event -> {
                        if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                            LobbyInfo clickedRow = row.getItem();
                            try {
                                vnc.joinLobby("piggo", clickedRow.getLobbyName());
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            gameWindow.setScene(lobbyScene);
                        }
                    });
                    return row;
                });

        create.setTranslateX(-200);
        create.setTranslateY(0);
        create.setOnAction(actionEvent -> createLobby());

        StackPane menuPane = new StackPane(
                lobbyTable,
                refresh,
                create,
                back
        );
        return new Scene(menuPane, 500, 300);
    }

    private Scene lobbyScene(){

        Button back = new Button("<-");
        Button ready = new Button("Ready");
        VBox playerBox = new VBox();

        ListView<String> lobby = new ListView<>();
        ObservableList<String> players = FXCollections.observableArrayList();

        lobby.setMaxWidth(300);

        back.setTranslateX(-200);
        back.setTranslateY(-100);
        back.setOnAction(actionEvent -> {
            try {
                vnc.leaveLobby("piggo");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            gameWindow.setScene(lobbyListScene);
        });

        ready.setTranslateX(-200);
        ready.setTranslateY(-200);
        ready.setOnAction(actionEvent -> {
            try {
                vnc.setPlayerReady("piggo");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            ready.setVisible(false);
        });

        VBox lobbyLayout = new VBox();
            lobbyLayout.getChildren().add(lobby);
            lobbyLayout.getChildren().add(back);
        lobbyLayout.getChildren().add(ready);
        return new Scene(lobbyLayout);
    }
}
