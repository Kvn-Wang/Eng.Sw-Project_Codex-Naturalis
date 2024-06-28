package it.polimi.codexnaturalis.view.GUI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.utils.jsonAdapter.MissionAdapter;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.Hand;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.HandGsonAdapter;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.jsonAdapter.CardTypeAdapter;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;
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
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class GuiGame extends Application {

    private static VirtualServer vnc;
    private static GameController vgc;
    private static ClientContainer clientContainer;
    private static ObservableList<LobbyInfo> lobbyList;
    private static ObservableList<String> lobbyPlayers;
    private static VBox lobbyLayout;
    private static ListView<String> playerBox;
    private static HBox colorChoice;
    private static Circle[][] anchorPointsMatrix;
    private static ArrayList<GuiCard> handCards;
    private static Rectangle[] missions;
    private static Pane vHand;
    private static VBox missionList;
    private static VBox actionMenu;
    private static Pane map;
    private static Pane cameraView;
    private static HashMap<String, Circle> pawns;
    private static int test=0; //TODO da togliere
    private static Rectangle cardBeingPlaced;
    private static double cameraX=500;
    private static double cameraY=300;
    private static double maxPlusX=100;
    private static double maxPlusY=100;
    private static double maxMinusX=-100;
    private static double maxMinusY=-100;
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
        launch(args);
    } //lancia il codice UOMO!

    public static void setupMenu(VirtualServer virtualNetworkCommand, ClientContainer clientContainer){
        vnc = virtualNetworkCommand;
        GuiGame.clientContainer = clientContainer;
    }

    public static void setNickname(boolean outcome, String nickname){
        Platform.runLater(() -> {
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
        });
    }

    public static void joinLobby(){
        Platform.runLater(() -> {
            lobbyLayout.getChildren().get(0).setVisible(true);
            lobbyLayout.getChildren().get(2).setVisible(true);
            updatePlayerList();
            gameWindow.setScene(lobbyScene);
        });
    }

    private static void pickColor(ColorType color){
        lobbyLayout.getChildren().get(0).setVisible(false);
        lobbyLayout.getChildren().get(2).setVisible(false);
        try {
            vnc.setPlayerColor(playerNickname, color);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void colorPicked(String nick){
        Platform.runLater(() -> {
            Popup popup = new Popup();
            Button closePop = new Button("close");
            Label nickname = new Label(nick + " ha selezionato un colore");
            closePop.setOnAction(event -> {
                if (popup.isShowing()) {
                    popup.hide();
                }
            });
            VBox vBox = new VBox(
                    nickname,
                    closePop
            );

            popup.getContent().add(vBox);
            closePop.setTranslateY(20);
            popup.show(gameWindow);

            updatePlayerList();
            playerBox.refresh();
        });
    }

    private static void updatePlayerList(){
        lobbyPlayers.clear();
        colorChoice.getChildren().forEach(rec-> rec.setVisible(true));
        clientContainer.getPlayers().forEach((nick, playerData)->{
            lobbyPlayers.add(nick);
            if (playerData.getPlayerColor() != null) {
                switch (playerData.getPlayerColor()) {
                    case RED -> colorChoice.getChildren().get(0).setVisible(false);//Red
                    case YELLOW -> colorChoice.getChildren().get(1).setVisible(false);//Yellow
                    case GREEN -> colorChoice.getChildren().get(2).setVisible(false);//Green
                    case BLUE -> colorChoice.getChildren().get(3).setVisible(false);//Blue
                }
            }
        });
    }

    public static void playerLeftGame(String playerNickname){
        Platform.runLater(() -> {
            lobbyPlayers.remove(playerNickname);
        });
    }

    public static void lobbyListRefresh(ArrayList<LobbyInfo> lobbies){
        Platform.runLater(() -> {
            lobbyList.addAll(lobbies);
        });
    }

    private static void updateLobbyList(ObservableList<LobbyInfo> lobbyList){
        try {
            vnc.getAvailableLobby();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addStarter(Card starter){
        Platform.runLater(() -> {
            handCards.add(new GuiCard(starter, anchorPointsMatrix));
            vHand.getChildren().add(handCards.getLast().getRectangle());
            handCards.getLast().setNum(0);
            starterBeingPlaced = true;
        });
    }

    public static void turnNotify(Boolean isYourTurn, Boolean isLastRound){
        Platform.runLater(() -> {
            updateHand(clientContainer.getPersonalHand());
            Popup popup = new Popup();
            Button closePop = new Button("ok");
            Label turn = new Label();
            if(isYourTurn){
                if(!isLastRound)
                    turn.setText("é il tuo turno");
                else
                    turn.setText("é il tuo ULTIMO turno!");
            }
            else{
                turn.setText("é il turno di un altro giocatore");
            }
            closePop.setOnAction(event -> {
                if (popup.isShowing()) {
                    popup.hide();
                }
            });
            VBox vBox = new VBox(
                    turn,
                    closePop
            );
            popup.getContent().add(vBox);
            popup.show(gameWindow);
        });
    }

    public static void missionSelection(Mission mission1, Mission mission2){
        Platform.runLater(() -> {
            updateHand(clientContainer.getPersonalHand());
            Label request = new Label("Missioni Comuni");
            Label request2 = new Label("Missione personale");
            Gson gsonTranslator = new GsonBuilder()
                    .registerTypeAdapter(Card.class, new CardTypeAdapter())
                    .registerTypeAdapter(Hand.class, new HandGsonAdapter())
                    .registerTypeAdapter(Mission.class, new MissionAdapter())
                    .create();
            String mission1Path = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+clientContainer.getCommonMission1().getPngNumber()+".png";
            String mission2Path = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+clientContainer.getCommonMission2().getPngNumber()+".png";
            missions[0]= new Rectangle(170, 100, new ImagePattern(new Image(GuiGame.class.getResourceAsStream(mission1Path))));
            missions[0].setStroke(null);
            missions[1]= new Rectangle(170, 100, new ImagePattern(new Image(GuiGame.class.getResourceAsStream(mission2Path))));
            missions[1].setStroke(null);

            missionList.getChildren().add(request);
            missionList.getChildren().add(missions[0]);
            missionList.getChildren().add(missions[1]);

            missionList.getChildren().add(request2);

            MissionSelectBox missionAlert = new MissionSelectBox();
            missionAlert.setMission(mission1, mission2);
            try {
                Mission selectedMission = gsonTranslator.fromJson(missionAlert.display("Mission selection", 800, 700), Mission.class);
                vgc.playerPersonalMissionSelect(playerNickname, selectedMission);
                String mission3Path = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+selectedMission.getPngNumber()+".png";
                missions[2]= new Rectangle(170, 100, new ImagePattern(new Image(GuiGame.class.getResourceAsStream(mission3Path))));
                missions[2].setStroke(null);
                missionList.getChildren().add(missions[2]);
                clientContainer.setPersonalMission(selectedMission);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            Button shopButton = new Button("Shop");
            shopButton.setMinSize(200,50);
            shopButton.setOnAction(event -> {
                openShop();
            });


            actionMenu.getChildren().add(shopButton);

//            TextField testField= new TextField();
//            testField.prefWidth(30);
//            testField.prefHeight(30);
//            testField.setOnAction(event -> {
//
//                String num = testField.getText();
//
//                movePawn(pawns.get(playerNickname), Integer.parseInt(num));
//            });
            actionMenu.getChildren().add(scoreBoard());
//            actionMenu.getChildren().add(testField);
        });
    }

    public static void validPlacement(boolean isValidPlacement) {
        Platform.runLater(() -> {
            if (isValidPlacement) {
                System.out.println("successful placement");
                updateHand(clientContainer.getPersonalHand());
                map.getChildren().add(cardBeingPlaced);
                moveCamera(cardBeingPlaced.getBoundsInLocal().getCenterX(), cardBeingPlaced.getBoundsInLocal().getCenterY());
            } else {
                System.out.println("invalid placement");
                updateHand(clientContainer.getPersonalHand());
            }
        });
    }

    public static void drawDuringPlayPhase() {
        Platform.runLater(() -> {
                System.out.println("draw during PlayPhase");
                updateHand(clientContainer.getPersonalHand());
        });
    }

    public static void updateScore(){
        clientContainer.getPlayers().forEach((nick,playerData) ->{
            movePawn(pawns.get(nick),playerData.getIntScoreBoardScore());
        });
    }

    private static void openShop(){
        ShopBox shopAlert = new ShopBox();
        shopAlert.setCC(clientContainer);
        String drawnCard = shopAlert.display("Shop", 400,300);
        if(drawnCard!=null) {
            switch (drawnCard) {
                case "r0":
                    try {
                        vgc.playerDraw(playerNickname, 0, ShopType.RESOURCE);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "r1":
                    try {
                        vgc.playerDraw(playerNickname, 1, ShopType.RESOURCE);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "r2":
                    try {
                        vgc.playerDraw(playerNickname, 2, ShopType.RESOURCE);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "o0":
                    try {
                        vgc.playerDraw(playerNickname, 0, ShopType.OBJECTIVE);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "01":
                    try {
                        vgc.playerDraw(playerNickname, 1, ShopType.OBJECTIVE);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "o2":
                    try {
                        vgc.playerDraw(playerNickname, 2, ShopType.OBJECTIVE);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    break;
            }
            updateHand(clientContainer.getPersonalHand());
        }
    }

    private static void updateHand(Hand personalHand) {
        Platform.runLater(() -> {
            System.out.println("Hand Update");
            vHand.getChildren().clear();
            for(int i=0; i<personalHand.getCards().size(); i++) {
                handCards.add(new GuiCard(personalHand.getCards().get(i), anchorPointsMatrix));
                vHand.getChildren().add(handCards.getLast().getRectangle());
                handCards.getLast().setNum(i);
            }
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

    public static void endGame(ArrayList<String> winners){
        Platform.runLater(() -> {
            TreeMap<Integer, String> list = null;
            for (String winner : winners) {
                list.put(clientContainer.getPlayers().get(winner).getIntScoreBoardScore(), winner);
            }

            WinnerBox winnerBox = new WinnerBox();
            winnerBox.setLeaderboard(list);
            String end = winnerBox.display("The game is over", 600, 600);
            if(end.equals("end"))
                Platform.exit();
        });
    }

    public static void startGame(GameController gameController){
        Platform.runLater(() -> {
            vgc=gameController;

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
//        Button skip = new Button("SKIP");
        Label title = new Label("Codex Naturalis");

        title.setFont(new Font("Arial", 30));
        title.setTranslateY(-100);


        play.setTranslateY(20);
        play.setPrefSize(100, 50);
        play.setOnAction(actionEvent -> gameWindow.setScene(nickScene));

//        skip.setTranslateY(-40);
//        skip.setPrefSize(100, 50);
//        skip.setOnAction(actionEvent -> gameWindow.setScene(gameScene));

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
        lobbyList = FXCollections.observableArrayList();

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
        lobbyLayout = new VBox();
        Button leave = new Button("<-");
        Button ready = new Button("Ready");

        playerBox = new ListView<>();
        lobbyPlayers = FXCollections.observableArrayList();

        colorChoice = new HBox();
        Rectangle red = new Rectangle();
        Rectangle yellow = new Rectangle();
        Rectangle green = new Rectangle();
        Rectangle blue = new Rectangle();

        red.setFill(Color.RED);
        yellow.setFill(Color.YELLOW);
        green.setFill(Color.GREEN);
        blue.setFill(Color.BLUE);

        red.setWidth(30);
        red.setHeight(30);
        yellow.setWidth(30);
        yellow.setHeight(30);
        green.setWidth(30);
        green.setHeight(30);
        blue.setWidth(30);
        blue.setHeight(30);

        colorChoice.getChildren().addAll(red, yellow, green, blue);

        playerBox.setItems(lobbyPlayers);

        red.setOnMouseClicked(actionEvent -> {
            pickColor(ColorType.RED);
        });
        yellow.setOnMouseClicked(actionEvent -> {
            pickColor(ColorType.YELLOW);
        });
        green.setOnMouseClicked(actionEvent -> {
            pickColor(ColorType.GREEN);
        });
        blue.setOnMouseClicked(actionEvent -> {
            pickColor(ColorType.BLUE);
        });
        playerBox.setCellFactory(lv -> new ListCell<String>() {
            private Text text = new Text();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    text.setText(item);
                    if(clientContainer.getPlayers().get(item).getPlayerColor()!=null) {
                        switch (clientContainer.getPlayers().get(item).getPlayerColor()) {
                            case ColorType.RED:
                                text.setFill(Color.RED);
                                break;
                            case ColorType.YELLOW:
                                text.setFill(Color.YELLOW);
                                break;
                            case ColorType.GREEN:
                                text.setFill(Color.GREEN);
                                break;
                            case ColorType.BLUE:
                                text.setFill(Color.BLUE);
                                break;
                        }
                    }
                    else{
                        text.setFill(Color.BLACK);
                    }
                    setGraphic(text);
                }
            }
        });
        playerBox.setMaxWidth(300);

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

        lobbyLayout.getChildren().add(leave);
        lobbyLayout.getChildren().add(playerBox);
        lobbyLayout.getChildren().add(colorChoice);
        lobbyLayout.getChildren().add(ready);
        return new Scene(lobbyLayout);
    }

    public Scene gameScene() {
        BorderPane game = new BorderPane();
        map = new Pane();
        anchorPointsMatrix = getAnchorPoints(game);
        vHand = handLayer();
        missionList = missionLayer();
        actionMenu = actionMenuLayer();
        cameraView= new Pane(map);

//        map.setMinSize(4000, 4000);

        map.setOnDragOver(e -> {
            if (e.getGestureSource() != map && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            e.consume();
        });
//        map.setOnDragEntered(e -> {
//            if (e.getGestureSource() != map && e.getDragboard().hasString()) {
//                map.setStyle("-fx-background-color: #ecdfb3;");
//            }
//            e.consume();
//        });
//        map.setOnDragExited(e -> {
//            map.setStyle("-fx-background-color: #ffffff;");
//            e.consume();
//        });
        map.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            Gson gsonTranslator = new GsonBuilder()
                    .registerTypeAdapter(Card.class, new CardTypeAdapter())
                    .registerTypeAdapter(Hand.class, new HandGsonAdapter())
                    .registerTypeAdapter(Mission.class, new MissionAdapter())
                    .create();
            if (db.hasString()) {
                String cardString = db.getString();
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
                    map.setTranslateX(game.getWidth()/2);
                    map.setTranslateY(game.getHeight()/2);
                    try {
                        vgc.playStarterCard(playerNickname, gsonTranslator.fromJson(cardString, StarterCard.class));
                        map.getChildren().add(newRect);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
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
                        try {
                            vgc.playerPlayCard(playerNickname, x, y, gsonTranslator.fromJson(cardString, Card.class));
                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }
                        cardBeingPlaced = newRect;
                    }
                }
                success = true;
            }

            e.setDropCompleted(success);

            e.consume();
        });

        cameraView.addEventFilter(ScrollEvent.SCROLL, event -> {
            double scale = cameraView.getScaleX();
            if (event.getDeltaY() > 0) {
                scale *= 1.1; // Zoom in
            } else {
                scale /= 1.1; // Zoom out
            }
            cameraView.setScaleX(scale);
            cameraView.setScaleY(scale);
        });

        game.setCenter(cameraView);
        game.setBottom(vHand);
        game.setLeft(missionList);
        game.setRight(actionMenu);

        for(Circle[] rows : anchorPointsMatrix) {
            for (Circle anchor : rows) {
                map.getChildren().add(anchor);
            }
        }

        return new Scene(game, 1500, 900);
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
        Circle anchor = new Circle(x, y, 10, Color.BLACK);
        anchor.setOpacity(0.1);  // Make it semi-transparent
        return anchor;
    }

    private Circle[][] getAnchorPoints(Pane game) {
        Circle[][] anchorMap = new Circle[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
        for(int y = -(UtilCostantValue.lunghezzaMaxMappa/2); y< (UtilCostantValue.lunghezzaMaxMappa/2); y++){
            for(int x = -(UtilCostantValue.lunghezzaMaxMappa/2); x< (UtilCostantValue.lunghezzaMaxMappa/2); x++){
                anchorMap[y+UtilCostantValue.lunghezzaMaxMappa/2][x+UtilCostantValue.lunghezzaMaxMappa/2] = createAnchorPoint(
                        (x*100 * Math.cos(Math.toRadians(45)) - y*100 * Math.sin(Math.toRadians(45)))*2,
                        x*100 * Math.sin(Math.toRadians(45)) + y*100 * Math.cos(Math.toRadians(45)));
            }
        }
        return anchorMap;
    }

    private static void moveCamera(double x, double y){
        maxPlusX = Math.max(x, maxPlusX);
        maxMinusX = Math.min(x, maxMinusX);
        maxPlusY = Math.max(y, maxPlusY);
        maxMinusY = Math.min(y, maxMinusY);

        cameraX = (maxPlusX + maxMinusX) / 2;
        cameraY = (maxPlusY + maxMinusY) / 2;

        cameraView.setTranslateX(-cameraX);
        cameraView.setTranslateY(-cameraY);

        double scaleFactor = 2.0 / (Math.max(maxPlusX - maxMinusX, maxPlusY - maxMinusY) / 100);
        cameraView.setScaleX(scaleFactor);
        cameraView.setScaleY(scaleFactor);
    }

    private Pane handLayer(){
        handCards = new ArrayList<GuiCard>();
        return new HBox();
    }

    private VBox missionLayer(){
        missions = new Rectangle[3];
        return new VBox();
    }
    private VBox actionMenuLayer(){
        VBox actions = new VBox();
        return actions;
    }

    private static Pane scoreBoard(){
        Pane scoreBoard = new Pane();
        pawns = new HashMap<>();
        String boardPath = "/it/polimi/codexnaturalis/graphics/PLATEAU-SCORE-IMP/Scoreboard.png";
        Image boardImage = new Image(GuiGame.class.getResourceAsStream(boardPath));

        BackgroundSize backgroundSize = new BackgroundSize(scoreBoard.getWidth(), scoreBoard.getHeight(), false, false, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(boardImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, backgroundSize);

        Background background = new Background(backgroundImage);
        scoreBoard.setBackground(background);

        scoreBoard.setPrefSize(200,400);
        scoreBoard.setMaxSize(200,400);

        clientContainer.getPlayers().forEach((nick,playerData) ->{
            Circle pawn = new Circle(52,372,10);
            switch(playerData.getPlayerColor()){
                case ColorType.RED:
                    pawn.setFill(Color.RED);
                    break;
                case ColorType.YELLOW:
                    pawn.setFill(Color.YELLOW);
                    break;
                case ColorType.GREEN:
                    pawn.setFill(Color.GREEN);
                    break;
                case ColorType.BLUE:
                    pawn.setFill(Color.BLUE);
                    break;
            }
            scoreBoard.getChildren().add(pawn);
            pawns.put(nick, pawn);
        });
        return scoreBoard;
    }

    private static void movePawn(Circle pawn, int i){
        switch(i){
            case 1:
                pawn.setTranslateX(100-pawn.getCenterX());
                pawn.setTranslateY(372-pawn.getCenterY());
                break;
            case 2:
                pawn.setTranslateX(148-pawn.getCenterX());
                pawn.setTranslateY(372-pawn.getCenterY());
                break;
            case 3:
                pawn.setTranslateX(171-pawn.getCenterX());
                pawn.setTranslateY(329-pawn.getCenterY());
                break;
            case 4:
                pawn.setTranslateX(124-pawn.getCenterX());
                pawn.setTranslateY(329-pawn.getCenterY());
                break;
            case 5:
                pawn.setTranslateX(76-pawn.getCenterX());
                pawn.setTranslateY(329-pawn.getCenterY());
                break;
            case 6:
                pawn.setTranslateX(29-pawn.getCenterX());
                pawn.setTranslateY(329-pawn.getCenterY());
                break;
            case 7:
                pawn.setTranslateX(29-pawn.getCenterX());
                pawn.setTranslateY(286-pawn.getCenterY());
                break;
            case 8:
                pawn.setTranslateX(76-pawn.getCenterX());
                pawn.setTranslateY(286-pawn.getCenterY());
                break;
            case 9:
                pawn.setTranslateX(124-pawn.getCenterX());
                pawn.setTranslateY(286-pawn.getCenterY());
                break;
            case 10:
                pawn.setTranslateX(171-pawn.getCenterX());
                pawn.setTranslateY(286-pawn.getCenterY());
                break;
            case 11:
                pawn.setTranslateX(171-pawn.getCenterX());
                pawn.setTranslateY(243-pawn.getCenterY());
                break;
            case 12:
                pawn.setTranslateX(124-pawn.getCenterX());
                pawn.setTranslateY(243-pawn.getCenterY());
                break;
            case 13:
                pawn.setTranslateX(76-pawn.getCenterX());
                pawn.setTranslateY(243-pawn.getCenterY());
                break;
            case 14:
                pawn.setTranslateX(29-pawn.getCenterX());
                pawn.setTranslateY(243-pawn.getCenterY());
                break;
            case 15:
                pawn.setTranslateX(29-pawn.getCenterX());
                pawn.setTranslateY(200-pawn.getCenterY());
                break;
            case 16:
                pawn.setTranslateX(76-pawn.getCenterX());
                pawn.setTranslateY(200-pawn.getCenterY());
                break;
            case 17:
                pawn.setTranslateX(124-pawn.getCenterX());
                pawn.setTranslateY(200-pawn.getCenterY());
                break;
            case 18:
                pawn.setTranslateX(171-pawn.getCenterX());
                pawn.setTranslateY(200-pawn.getCenterY());
                break;
            case 19:
                pawn.setTranslateX(171-pawn.getCenterX());
                pawn.setTranslateY(157-pawn.getCenterY());
                break;
            case 20:
                pawn.setTranslateX(100-pawn.getCenterX());
                pawn.setTranslateY(135-pawn.getCenterY());
                break;
            case 21:
                pawn.setTranslateX(29-pawn.getCenterX());
                pawn.setTranslateY(157-pawn.getCenterY());
                break;
            case 22:
                pawn.setTranslateX(29-pawn.getCenterX());
                pawn.setTranslateY(114-pawn.getCenterY());
                break;
            case 23:
                pawn.setTranslateX(29-pawn.getCenterX());
                pawn.setTranslateY(71-pawn.getCenterY());
                break;
            case 24:
                pawn.setTranslateX(57-pawn.getCenterX());
                pawn.setTranslateY(34-pawn.getCenterY());
                break;
            case 25:
                pawn.setTranslateX(100-pawn.getCenterX());
                pawn.setTranslateY(27-pawn.getCenterY());
                break;
            case 26:
                pawn.setTranslateX(143-pawn.getCenterX());
                pawn.setTranslateY(35-pawn.getCenterY());
                break;
            case 27:
                pawn.setTranslateX(171-pawn.getCenterX());
                pawn.setTranslateY(71-pawn.getCenterY());
                break;
            case 28:
                pawn.setTranslateX(171-pawn.getCenterX());
                pawn.setTranslateY(114-pawn.getCenterY());
                break;
            case 29:
                pawn.setTranslateX(100-pawn.getCenterX());
                pawn.setTranslateY(79-pawn.getCenterY());
                break;
            default:
                break;
        }
    }
}
