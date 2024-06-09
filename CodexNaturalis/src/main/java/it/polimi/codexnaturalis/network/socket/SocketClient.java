package it.polimi.codexnaturalis.network.socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.player.HandGsonAdapter;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.CardTypeAdapter;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.GenericClient;
import it.polimi.codexnaturalis.view.TypeOfUI;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SocketClient extends GenericClient implements VirtualServer {
    Socket serverSocket;
    BufferedReader socketRx;
    PrintWriter socketTx;
    // variabili usata per la logica di invio e aspetta la risposta
    boolean ackArrived;
    private final Object lock = new Object();
    boolean outcomeReceived;
    ArrayList<String> argsRX; // TODO data Race
    Gson gson;

    public SocketClient(TypeOfUI typeOfUI) throws IOException {
        super(typeOfUI);

        gson = new Gson();

        String host = "127.0.0.1";
        int port = UtilCostantValue.portSocketServer;

        serverSocket = new Socket(host, port);

        socketRx = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        //socketTx = new ServerProxySocket(new PrintWriter(serverSocket.getOutputStream(), true));
        socketTx = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()), true);

        ackArrived = false;

        new Thread(() -> {
            try {
                runRxClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        initializeClient(this);
    }

    private void runRxClient() throws IOException {
        NetworkMessage message;
        String jsonRX;

        // Read message type
        while ((jsonRX = socketRx.readLine()) != null) {
            message = deSerializeMesssage(jsonRX);

            argsRX = message.getArgs();
            //risveglia il thread in wait per la risposta del server
            doNotify();

            // Read message and perform action
            switch (message.getMessageType()) {
                case COM_ACK_TCP:
                    ackArrived = true;
                    break;

                case COM_GET_LOBBIES_TCP:
                    break;

                case COM_CONNECT_GAME_TCP:
                    ArrayList<PlayerInfo> arg0 = gson.fromJson(argsRX.get(0), new TypeToken<ArrayList<PlayerInfo>>() {}.getType());

                    connectToGame(this, arg0);
                    break;

                case COM_LOBBY_STATUS_NOTIFY:
                    typeOfUI.notifyLobbyStatus(message.getNickname(), argsRX.get(0));
                    break;

                case STATUS_PLAYER_CHANGE:
                    break;

                case WRONG_TYPE_SHOP:
                    break;

                case NOT_YOUR_TURN:
                    break;

                case SCORE_UPDATE:
                    break;

                case SWITCH_PLAYER_VIEW:
                    break;

                case GAME_SETUP_STARTER_CARD:
                    Gson cardTranslator = new GsonBuilder()
                            .registerTypeAdapter(Card.class, new CardTypeAdapter())
                            .create();

                    System.out.println("received starter card: "+ message.getArgs().get(0));
                    Card supp = cardTranslator.fromJson(message.getArgs().get(0), Card.class);

                    playStarterCard(supp);
                    break;

                case SHOP_UPDATE:
                    break;

                case CORRECT_DRAW_CARD:
                    Gson handTranslator = new GsonBuilder()
                            .registerTypeAdapter(Card.class, new CardTypeAdapter())
                            .registerTypeAdapter(Hand.class, new HandGsonAdapter())
                            .create();

                    System.out.println("received card: "+ argsRX.get(0));
                    Hand hand = handTranslator.fromJson(argsRX.get(0), Hand.class);

                    //playStarterCard(hand);
                    break;

                case CORRECT_PLACEMENT:
                    break;

                case COM_ERROR_TCP:
                    //TODO
                    break;

                default:
                    // sono messaggi di notifiche
                    System.out.println(message.getArgs().get(0));
                    break;
            }
        }
    }

    @Override
    public void showMessage(NetworkMessage message) throws RemoteException {
        switch(message.getMessageType()) {
            case STATUS_PLAYER_CHANGE:
                break;

            case WRONG_TYPE_SHOP:
                break;

            case NOT_YOUR_TURN:
                break;

            case SCORE_UPDATE:
                break;

            case SWITCH_PLAYER_VIEW:
                break;

            case CORRECT_DRAW_CARD:
                break;

            case CORRECT_PLACEMENT:
                break;
        }
    }

    @Override
    public void connectToGame(GameController gameController, ArrayList<PlayerInfo> listOtherPlayer) {
        joinPlayerToGame(gameController, listOtherPlayer);
    }

    private String serializeMesssage(NetworkMessage message) {
        String json;
        Gson gson = new Gson();

        json = gson.toJson(message);

        return json;
    }

    private NetworkMessage deSerializeMesssage(String json) {
        NetworkMessage networkMessage;
        Gson gson = new Gson();

        networkMessage = gson.fromJson(json, NetworkMessage.class);

        return networkMessage;
    }

    private void doWait() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // ripristina lo stato di interruzione
            }
        }
    }

    private void doNotify() {
        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    public boolean setNickname(String UUID, String nickname) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_SET_NICKNAME_TCP, nickname)));
        doWait();

        //getArgs = (String) boolean
        outcomeReceived = Boolean.parseBoolean(argsRX.get(0));

        // fa partire una specie di ricorsione finchè il nick non è valido
        if(outcomeReceived == false) {
            typeOfUI.printSelectionNicknameRequestOutcome(false, nickname);
            typeOfUI.printSelectionNicknameRequest();
        } else {
            clientContainerController.setNickname(nickname);
            typeOfUI.printSelectionNicknameRequestOutcome(true, nickname);
        }

        //reset della variabile in attesa di altri ACK
        outcomeReceived = false;

        //return inutile
        return false;
    }

    @Override
    public void connectRMI(VirtualView client, String UUID) throws RemoteException {}

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_GET_LOBBIES_TCP)));
        doWait();

        // getArgs = json of lobbiesInfo
        ArrayList<LobbyInfo> lobbies = gson.fromJson(argsRX.get(0), new TypeToken<ArrayList<LobbyInfo>>() {}.getType());

        return lobbies;
    }

    @Override
    public boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_JOIN_LOBBY_TCP, playerNickname, lobbyName)));
        doWait();

        //getArgs = (String) boolean
        outcomeReceived = Boolean.parseBoolean(argsRX.get(0));

        // fa partire una specie di ricorsione finchè il nick non è valido
        if(outcomeReceived == false) {
            typeOfUI.printJoinLobbyOutcome(false, lobbyName);
            typeOfUI.printSelectionCreateOrJoinLobbyRequest();
        } else {
            clientContainerController.setLobbyName(lobbyName);
            typeOfUI.printJoinLobbyOutcome(true, lobbyName);
        }

        //reset della variabile in attesa di altri ACK
        outcomeReceived = false;

        //return inutile
        return false;
    }

    @Override
    public void leaveLobby(String playerNickname) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_LEAVE_LOBBY_TCP, playerNickname)));
        doWait();

        typeOfUI.printReadyOrLeaveSelectionOutcome(false);

        //reset della variabile in attesa di altri ACK
        outcomeReceived = false;

        initializationPhase2();
    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_CREATE_LOBBY_TCP, playerNickname, lobbyName)));
        doWait();

        //getArgs = (String) boolean
        outcomeReceived = Boolean.parseBoolean(argsRX.get(0));

        // fa partire una specie di ricorsione finchè il nick non è valido
        if(outcomeReceived == false) {
            typeOfUI.printCreationLobbyRequestOutcome(false, lobbyName);
            typeOfUI.printSelectionCreateOrJoinLobbyRequest();
        } else {
            clientContainerController.setLobbyName(lobbyName);
            typeOfUI.printCreationLobbyRequestOutcome(true, lobbyName);
        }

        //reset della variabile in attesa di altri ACK
        outcomeReceived = false;

        //return inutile
        return false;
    }

    @Override
    public void setPlayerReady(String playerNickname) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_SET_READY_LOBBY_TCP, playerNickname)));
        doWait();

        typeOfUI.printReadyOrLeaveSelectionOutcome(true);

        //reset della variabile in attesa di altri ACK
        outcomeReceived = false;
    }

    @Override
    public void disconnectPlayer(String nickname) throws RemoteException {

    }

    @Override
    public void reconnectPlayer(String nickname) throws RemoteException {

    }

    @Override
    public void playerDraw(String nickname, int Numcard, String type) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage, RemoteException {

    }

    @Override
    public void playerPersonalMissionSelect(String nickname, int numMission) throws RemoteException {

    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException, RemoteException {

    }

    @Override
    public void typeMessage(String receiver, String sender, String msg) throws RemoteException {

    }

    @Override
    public void switchPlayer(String reqPlayer, String target) throws RemoteException {

    }

    @Override
    public void endGame() throws RemoteException {

    }
}
