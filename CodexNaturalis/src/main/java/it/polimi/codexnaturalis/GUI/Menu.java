package it.polimi.codexnaturalis.GUI;

import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Menu extends Gui {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage menuStage) throws Exception{
        startScene(menuStage);
        menuStage.show();
    }

    private void startScene(Stage menuStage) throws Exception{
        Button rmi = new  Button("RMI");
        Button socket = new Button("Socket");
        Label title = new Label("Codex Naturalis");
        Label request = new Label("selezionare il tipo di connessione");

        title.setFont(new Font("Arial", 30));
        title.setTranslateY(-100);

        request.setFont(new Font("Arial",15));
        request.setTranslateY(-40);

        rmi.setTranslateX(-100);
        rmi.setTranslateY(20);
        rmi.setPrefSize(100,50);
        rmi.setOnAction(actionEvent -> {
            try {
                rmiScene(menuStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        socket.setTranslateX(100);
        socket.setTranslateY(20);
        socket.setPrefSize(100,50);

        StackPane menuPane = new StackPane(
                title,
                request,
                rmi,
                socket
        );
        menuStage.setScene(new Scene(menuPane,500,300));
    }

    private void rmiScene(Stage menuStage) throws Exception{

        Button back = new  Button("<-");
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
            String inputText = nickname.getText();
            System.out.println("Input received: " + inputText);
            try {
                lobbyListScene(menuStage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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
        menuStage.setScene(new Scene(menuPane,500,300));
    }

    private void lobbyListScene(Stage menuStage) throws Exception{

        Button back = new  Button("<-");
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
        menuStage.setScene(new Scene(menuPane,500,300));
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
