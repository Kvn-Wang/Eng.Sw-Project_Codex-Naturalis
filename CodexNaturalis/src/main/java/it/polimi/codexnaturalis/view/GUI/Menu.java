package it.polimi.codexnaturalis.view.GUI;

import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Menu extends Application {

    private static VirtualServer vnc;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

     static Stage gameWindow;
     private static Scene startScene;
     private static Scene nickScene;
     private static Scene lobbyListScene;
     private static Scene lobbyScene;
     private static Scene gameScene;
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
            players = vnc.;
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

    public static void startGame(){
        Platform.runLater(() -> {
            try {
                gameWindow.setScene(gameScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void start(Stage gameStage) throws Exception {
        Menu.gameWindow = gameStage;
        startScene = startScene();
        nickScene = nickScene();
        lobbyListScene = lobbyListScene();
        lobbyScene = lobbyScene();
        gameScene = gameScene();
        gameWindow.setScene(startScene);
        gameWindow.show();
    }

    private static Scene startScene() throws Exception {
        gameWindow.setTitle("CodexNaturalis");
        Button play = new Button("PLAY");
        Button skip = new Button("SKIP");
        Label title = new Label("Codex Naturalis");

        title.setFont(new Font("Arial", 30));
        title.setTranslateY(-100);


        play.setTranslateY(20);
        play.setPrefSize(100, 50);
        play.setOnAction(actionEvent -> gameWindow.setScene(nickScene));

        skip.setTranslateY(-40);
        skip.setPrefSize(100, 50);
        skip.setOnAction(actionEvent -> gameWindow.setScene(gameScene));

        StackPane menuPane = new StackPane(
                title,
                play,
                skip
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

        TableColumn<LobbyInfo, String> column1 = new TableColumn<>("Lobby Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("lobbyName"));

        TableColumn<LobbyInfo, Boolean> column2 = new TableColumn<>("Started");
        column2.setCellValueFactory(new PropertyValueFactory<>("isLobbyStarted"));

        TableColumn<LobbyInfo, Integer> column3 = new TableColumn<>("Players");
        column3.setCellValueFactory(new PropertyValueFactory<>("maxPlayer"));

        lobbyTable.getColumns().addAll(column1, column2, column3);
        lobbyTable.setItems(lobbyList);
        lobbyTable.setMaxWidth(300);
        updateLobbyList(lobbyList);
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
                        gameWindow.setScene(lobbyScene);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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

        Button leave = new Button("<-");
        Button ready = new Button("Ready");
        VBox playerBox = new VBox();

        ListView<String> lobby = new ListView<>();
        ObservableList<String> players = FXCollections.observableArrayList();

        lobby.setMaxWidth(300);

        leave.setOnAction(actionEvent -> {
            try {
                vnc.leaveLobby("piggo");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            gameWindow.setScene(lobbyListScene);
        });

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
            lobbyLayout.getChildren().add(leave);
        lobbyLayout.getChildren().add(ready);
        return new Scene(lobbyLayout);
    }

    public Scene gameScene() {

        Pane map = new Pane();
        Scene scene = new Scene(map, 800, 600);

        // Create a draggable node
        Circle draggableNode = createDraggableNode(100, 100);

        map.getChildren().add(draggableNode);
        for(Circle[] rows : getAnchorPoints()) {
            for (Circle anchor : rows) {
                map.getChildren().add(anchor);
            }
        }
        map.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            double scaleFactor = (deltaY > 0) ? 1.1 : 0.9;
            map.setScaleX(map.getScaleX() * scaleFactor);
            map.setScaleY(map.getScaleY() * scaleFactor);
        });

        return scene;
    }

    private Circle createDraggableNode(double x, double y) {
        Circle circle = new Circle(x, y, 20, Color.BLUE);

        circle.setOnMousePressed((MouseEvent event) -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            orgTranslateX = circle.getTranslateX();
            orgTranslateY = circle.getTranslateY();
        });

        circle.setOnMouseDragged((MouseEvent event) -> {
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            circle.setTranslateX(orgTranslateX + offsetX);
            circle.setTranslateY(orgTranslateY + offsetY);
        });

        circle.setOnMouseReleased((MouseEvent event) -> {
            // Snap to the nearest anchor point if close enough
            double closestDistance = Double.MAX_VALUE;
            Circle closestAnchor = null;

            for(Circle[] rows : getAnchorPoints()) {
                for (Circle anchor : rows) {
                    double distance = Math.hypot(
                            anchor.getCenterX() - (circle.getTranslateX() + circle.getCenterX()),
                            anchor.getCenterY() - (circle.getTranslateY() + circle.getCenterY())
                    );

                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestAnchor = anchor;
                    }
                }
            }

            double snapThreshold = 50;
            if (closestAnchor != null && closestDistance < snapThreshold) {
                circle.setTranslateX(closestAnchor.getCenterX() - circle.getCenterX());
                circle.setTranslateY(closestAnchor.getCenterY() - circle.getCenterY());
            }
        });

        return circle;
    }

    private Circle createAnchorPoint(double x, double y) {
        Circle anchor = new Circle(x, y, 10, Color.RED);
        anchor.setOpacity(0.5);  // Make it semi-transparent
        return anchor;
    }

    private Circle[][] getAnchorPoints() {
        Circle[][] map = new Circle[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
        for(int i = -(UtilCostantValue.lunghezzaMaxMappa/2); i< (UtilCostantValue.lunghezzaMaxMappa/2); i++){
            for(int j = -(UtilCostantValue.lunghezzaMaxMappa/2); j< (UtilCostantValue.lunghezzaMaxMappa/2); j++){
                map[i+UtilCostantValue.lunghezzaMaxMappa/2][j+UtilCostantValue.lunghezzaMaxMappa/2] = createAnchorPoint(i*100, j*100);
            }
        }
        return map;
    }
}
