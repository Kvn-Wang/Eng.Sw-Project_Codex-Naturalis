package it.polimi.codexnaturalis.view.GUI;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class GuiGame extends Application {

    private static VirtualServer vnc;
    private static GameController vgc;
    private static ClientContainerController containerController;
    private static Circle[][]  anchorPointsMatrix;
    private static ArrayList<GuiCard> handCards;
    private static Pane vHand;
    private static Pane map;
    private static String playerNickname;
    private static boolean starterBeingPlaced = false;

     static Stage gameWindow;
     private static Scene startScene;
     private static Scene nickScene;
     private static Scene lobbyListScene;
     private static Scene lobbyScene;
     private static Scene gameScene;

    public static Pane getvHand() {
        return vHand;
    }

    /*
             private static final BackgroundImage bgi = new BackgroundImage(
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

//    private static void playerListJoin(ObservableList<String> playerList){
//
//        try {
//            players = vnc.;
//        } catch (RemoteException e) {
//            throw new RuntimeException(e);
//        }
//
//        playerList.addAll(players);
//    }

    public static void addStarter(Card starter){
        Platform.runLater(() -> {
            handCards.add(new GuiCard(starter, anchorPointsMatrix));
            vHand.getChildren().add(handCards.getLast().getRectangle());
            handCards.getLast().setNum(0);
            handCards.getLast().getRectangle().setTranslateX(170);
            starterBeingPlaced = true;
        });
    }

    public static void UpdateShop(){
        Platform.runLater(() -> {

        });
    }

    public static void UpdateHand(Hand hand){
        Platform.runLater(() -> {
            System.out.println("update");
            boolean alreadyInHand;
            for (int i = 0; i < hand.getCards().size(); i++) {
                alreadyInHand = false;
                for (GuiCard handCard : handCards) {
                    if (handCard.getCard().equals(hand.getCards().get(i))) {
                        alreadyInHand = true;
                        break;
                    }
                }
                if (!alreadyInHand) {
                    handCards.add(new GuiCard(hand.getCards().get(i), anchorPointsMatrix));
                    vHand.getChildren().add(handCards.getLast().getRectangle());
                    handCards.getLast().setNum(i);
                    handCards.getLast().getRectangle().setTranslateX(170 * i);
                }
            }
        });
    }

    public static void turnNotify(){
        Platform.runLater(() -> {
            Popup popup = new Popup();
            Button closePop = new Button("ok");
            Label nick = new Label("é il tuo turno");
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
            popup.show(gameWindow);
        });
    }

    public static void commonMissionSetup(Mission mission1, Mission mission2){
        Platform.runLater(() -> {
            CommonMissionBox missionAlert = new CommonMissionBox();
            missionAlert.setMission(mission1, mission2);
            missionAlert.display("Shared Missions", 800, 700);
        });
    }
    public static void missionSelection(Mission mission1, Mission mission2){
        Platform.runLater(() -> {
            MissionSelectBox missionAlert = new MissionSelectBox();
            missionAlert.setMission(mission1, mission2);
            /*try {
                vgc.playerPersonalMissionSelect(playerNickname, Integer.parseInt(missionAlert.display("Mission selection", 800, 700)));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }*/
        });
    }

    private static void createLobby(){
        LobbyCreationBox lobbyAlert = new LobbyCreationBox();
        try {
            vnc.createLobby(playerNickname, lobbyAlert.display("Create lobby", 300,200));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        gameWindow.setScene(lobbyScene);
    }

    public static void startGame(GameController gameController, ClientContainerController clientContainerController){
        Platform.runLater(() -> {
            vgc=gameController;
            containerController = clientContainerController;

            try {
                gameWindow.setScene(gameScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void start(Stage gameStage) throws Exception {
        GuiGame.gameWindow = gameStage;
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
                playerNickname = nickname.getText();
                vnc.setNickname(null, playerNickname);
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
                        vnc.joinLobby(playerNickname, clickedRow.getLobbyName());
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
                vnc.leaveLobby(playerNickname);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            gameWindow.setScene(lobbyListScene);
        });
        ready.setOnAction(actionEvent -> {
            try {
                vnc.setPlayerReady(playerNickname);
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

        Pane game = new StackPane();
        map = new Pane();
        anchorPointsMatrix = getAnchorPoints(game);
        vHand = handLayer();
        vHand.setTranslateX(200);

        map.setOnDragOver(e -> {
            if (e.getGestureSource() != map && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            e.consume();
        });
        map.setOnDragEntered(e -> {
            if (e.getGestureSource() != map && e.getDragboard().hasString()) {
                map.setStyle("-fx-background-color: #ecdfb3;");
            }
            e.consume();
        });
        map.setOnDragExited(e -> {
            map.setStyle("-fx-background-color: #ffffff;");
            e.consume();
        });
        map.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                String[] droppedStrings = db.getString().split("\\|\\|");
                String num = db.getString();
//                System.out.println(droppedStrings[0]);
//                System.out.println(droppedStrings[1]);
//                System.out.println(num);
                Rectangle sourceRect =  (Rectangle) e.getGestureSource();
//                System.out.println(sourceRect);
                Rectangle newRect = new Rectangle(sourceRect.getWidth(), sourceRect.getHeight(), sourceRect.getFill());
                if(starterBeingPlaced){
                    starterBeingPlaced=false;
                    newRect.setX(-newRect.getBoundsInLocal().getCenterX());
                    newRect.setY(-newRect.getBoundsInLocal().getCenterY());
                    /*try {
                        vgc.playerPlayCard(playerNickname, 80, 80, Integer.parseInt(droppedStrings[1]), Boolean.parseBoolean(droppedStrings[0]));
                    } catch (PersonalizedException.InvalidPlacementException |
                             PersonalizedException.InvalidPlaceCardRequirementException | RemoteException ex) {
                        throw new RuntimeException(ex);
                    }*/
                }
                else {
                    double closestDistance = Double.MAX_VALUE;
                    int x = 0;
                    int y = 0;
                    Circle closestAnchor = null;
                    newRect.setX(e.getX() - newRect.getBoundsInLocal().getCenterX());
                    newRect.setY(e.getY() - newRect.getBoundsInLocal().getCenterY());
                    System.out.println(e.getX());
                    System.out.println(e.getY());
                    for (int i = 0; i < (UtilCostantValue.lunghezzaMaxMappa); i++) {
                        for (int j = 0; j < (UtilCostantValue.lunghezzaMaxMappa); j++) {
                            double distance = Math.hypot(
                                    anchorPointsMatrix[i][j].getCenterX() - (e.getX()),
                                    anchorPointsMatrix[i][j].getCenterY() - (e.getY())
                            );

                            if (distance < closestDistance) {
                                closestDistance = distance;
                                closestAnchor = anchorPointsMatrix[i][j];
                                y = i;
                                x = j;
                            }
                        }
                    }

                    double snapThreshold = 200;
                    if (closestAnchor != null && closestDistance < snapThreshold) {
                        newRect.setTranslateX(closestAnchor.getCenterX() - newRect.getBoundsInLocal().getCenterX());
                        newRect.setTranslateY(closestAnchor.getCenterY() - newRect.getBoundsInLocal().getCenterY());
                        /*try {
                            vgc.playerPlayCard(playerNickname, x, y, Integer.parseInt(droppedStrings[1]), Boolean.parseBoolean(droppedStrings[0]));
                        } catch (PersonalizedException.InvalidPlacementException |
                                 PersonalizedException.InvalidPlaceCardRequirementException | RemoteException ex) {
                            throw new RuntimeException(ex);
                        }*/
                    }
                }

                map.getChildren().add(newRect);
                success = true;
            }
            /* let the source know whether the string was successfully transferred and used */
            e.setDropCompleted(success);

            e.consume();
        });

        game.getChildren().add(map);
        game.getChildren().add(vHand);
        vHand.setTranslateY(500);

//        Rectangle draggableCard1 = vCard1.getRectangle();
//        Rectangle draggableCard2 = vCard2.getRectangle();


        for(Circle[] rows : anchorPointsMatrix) {
            for (Circle anchor : rows) {
                map.getChildren().add(anchor);
            }
        }
//        map.getChildren().add(draggableCard1);
//        map.getChildren().add(draggableCard2);


        map.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            double scaleFactor = (deltaY > 0) ? 1.1 : 0.9;
            map.setScaleX(map.getScaleX() * scaleFactor);
            map.setScaleY(map.getScaleY() * scaleFactor);
        });

        return new Scene(game, 1000, 600);
    }

//    private Circle createDraggableNode(double x, double y) {
//        Circle circle = new Circle(x, y, 20, Color.BLUE);
//
//        circle.setOnMousePressed((MouseEvent event) -> {
//            orgSceneX = event.getSceneX();
//            orgSceneY = event.getSceneY();
//            orgTranslateX = circle.getTranslateX();
//            orgTranslateY = circle.getTranslateY();
//        });
//
//        circle.setOnMouseDragged((MouseEvent event) -> {
//            double offsetX = event.getSceneX() - orgSceneX;
//            double offsetY = event.getSceneY() - orgSceneY;
//            circle.setTranslateX(orgTranslateX + offsetX);
//            circle.setTranslateY(orgTranslateY + offsetY);
//        });
//
//        circle.setOnMouseReleased((MouseEvent event) -> {
//            // Snap to the nearest anchor point if close enough
//            double closestDistance = Double.MAX_VALUE;
//            Circle closestAnchor = null;
//
//            for(Circle[] rows : getAnchorPoints()) {
//                for (Circle anchor : rows) {
//                    double distance = Math.hypot(
//                            anchor.getCenterX() - (circle.getTranslateX() + circle.getCenterX()),
//                            anchor.getCenterY() - (circle.getTranslateY() + circle.getCenterY())
//                    );
//
//                    if (distance < closestDistance) {
//                        closestDistance = distance;
//                        closestAnchor = anchor;
//                    }
//                }
//            }
//
//            double snapThreshold = 50;
//            if (closestAnchor != null && closestDistance < snapThreshold) {
//                circle.setTranslateX(closestAnchor.getCenterX() - circle.getCenterX());
//                circle.setTranslateY(closestAnchor.getCenterY() - circle.getCenterY());
//            }
//        });
//
//        return circle;
//    }

    private Circle createAnchorPoint(double x, double y) {
        Circle anchor = new Circle(x, y, 10, Color.RED);
        anchor.setOpacity(0.5);  // Make it semi-transparent
        return anchor;
    }

    private Circle[][] getAnchorPoints(Pane game) {
        Circle[][] anchorMap = new Circle[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
        for(int y = -(UtilCostantValue.lunghezzaMaxMappa/2); y< (UtilCostantValue.lunghezzaMaxMappa/2); y++){
            for(int x = -(UtilCostantValue.lunghezzaMaxMappa/2); x< (UtilCostantValue.lunghezzaMaxMappa/2); x++){
                anchorMap[y+UtilCostantValue.lunghezzaMaxMappa/2][x+UtilCostantValue.lunghezzaMaxMappa/2] = createAnchorPoint((x*100 * Math.cos(Math.toRadians(45)) - y*100 * Math.sin(Math.toRadians(45)))*2, x*100 * Math.sin(Math.toRadians(45)) + y*100 * Math.cos(Math.toRadians(45)));
            }
        }
        return anchorMap;
    }

    private Pane handLayer(){
        handCards = new ArrayList<GuiCard>();
        Pane vHand= new Pane();

//        StarterCard card1 = new StarterCard(    81,
//                ResourceType.NONE,
//                ResourceType.NONE,
//                ResourceType.PLANT,
//                ResourceType.INSECT,
//                new ResourceType[]{ResourceType.INSECT},
//                ResourceType.FUNGI,
//                ResourceType.ANIMAL,
//                ResourceType.PLANT,
//                ResourceType.INSECT);
//
//        ResourceCard card2 = new ResourceCard(    4,
//                ResourceType.NONE,
//                ResourceType.NONE,
//                ResourceType.PLANT,
//                ResourceType.INSECT,
//                ResourceType.PLANT,
//                0);
//        card1.setIsBack(false);
//        card2.setIsBack(false);
//
//        handCards.add(new GuiCard(card1, anchorPointsMatrix));
//        handCards.add(new GuiCard(card2, anchorPointsMatrix));
//
//        for(int i=0; i<handCards.size(); i++) {
//            vHand.getChildren().add(handCards.get(i).getRectangle());
//            handCards.get(i).getRectangle().setTranslateX(170*i);
//        }
        return vHand;
    }
}
