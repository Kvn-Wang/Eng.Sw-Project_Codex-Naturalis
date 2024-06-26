package it.polimi.codexnaturalis.network.socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.mission.MissionAdapter;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.CardTypeAdapter;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.GenericClient;
import it.polimi.codexnaturalis.view.TypeOfUI;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.Hand;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.HandGsonAdapter;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SocketClient extends GenericClient implements VirtualServer {
    public Socket serverSocket;
    private BufferedReader socketRx;
    private PrintWriter socketTx;
    private String setupNickname;
    private String setupLobbyName;
    private boolean outcomeReceived;
    private Gson gsonTranslator = new GsonBuilder()
            .registerTypeAdapter(Card.class, new CardTypeAdapter())
            .registerTypeAdapter(Hand.class, new HandGsonAdapter())
            .registerTypeAdapter(Mission.class, new MissionAdapter())
            .create();

    public SocketClient(TypeOfUI typeOfUI, String address) throws IOException {
        super(typeOfUI);

        int port = UtilCostantValue.portSocketServer;

        serverSocket = new Socket(address, port);

        socketRx = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        socketTx = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()), true);

        new Thread(() -> {
            try {
                runRxClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void runRxClient() throws IOException {
        NetworkMessage message;
        String jsonRX;
        ArrayList<String> argsRX;

        // Read message type
        while ((jsonRX = socketRx.readLine()) != null) {
            message = deSerializeMesssage(jsonRX);

            argsRX = message.getArgs();

            // Read message and perform action
            switch (message.getMessageType()) {
                // ---------  Lobby Phase ----------- //
                case COM_SET_NICKNAME_RESPONSE_TCP:
                    //getArgs = (String) boolean
                    outcomeReceived = Boolean.parseBoolean(argsRX.get(0));

                    // fa partire una specie di ricorsione finchè il nick non è valido
                    if(outcomeReceived == false) {
                        typeOfUI.printSelectionNicknameRequestOutcome(false, setupNickname);
                    } else {
                        clientContainer.setNickname(setupNickname);
                        typeOfUI.printSelectionNicknameRequestOutcome(true, setupNickname);
                    }
                    break;

                case COM_GET_LOBBIES_RESPONSE_TCP:
                    // getArgs = json of lobbiesInfo
                    ArrayList<LobbyInfo> lobbies = gsonTranslator.fromJson(argsRX.get(0), new TypeToken<ArrayList<LobbyInfo>>() {}.getType());

                    typeOfUI.giveLobbies(lobbies);
                    break;

                case COM_CONNECT_GAME_TCP:
                    ArrayList<PlayerInfo> arg0 = gsonTranslator.fromJson(argsRX.get(0), new TypeToken<ArrayList<PlayerInfo>>() {}.getType());

                    connectToGame(this, arg0);
                    break;

                case COM_JOIN_LOBBY_TCP_OUTCOME:
                    //getArgs = (String) boolean
                    outcomeReceived = Boolean.parseBoolean(argsRX.get(0));

                    // fa partire una specie di ricorsione finchè il nick non è valido
                    if(outcomeReceived == false) {
                        typeOfUI.printJoinLobbyOutcome(false, setupLobbyName);
                    } else {
                        clientContainer.setLobbyNickname(setupLobbyName);
                        typeOfUI.printJoinLobbyOutcome(true, setupLobbyName);
                    }
                    break;

                case COM_SET_READY_LOBBY_RESPONSE_TCP:
                    typeOfUI.lobbyActionOutcome(true);
                    break;

                case COM_LEAVE_LOBBY_RESPONSE_TCP:
                    typeOfUI.lobbyActionOutcome(false);
                    break;

                case COM_CREATE_LOBBY_OUTCOME_TCP:
                    //getArgs = (String) boolean
                    outcomeReceived = Boolean.parseBoolean(argsRX.get(0));

                    if(outcomeReceived == false) {
                        typeOfUI.printCreationLobbyRequestOutcome(false, setupLobbyName);
                    } else {
                        clientContainer.setLobbyNickname(setupLobbyName);
                        typeOfUI.printCreationLobbyRequestOutcome(true, setupLobbyName);
                    }
                    break;

                case COM_SET_PLAYER_COLOR_OUTCOME:
                    boolean actionSuccessful = Boolean.parseBoolean(message.getArgs().get(0));

                    if(actionSuccessful) {
                        String nickname = message.getArgs().get(1);
                        ColorType color = ColorType.valueOf(message.getArgs().get(2));

                        if(nickname.equals(clientContainer.getNickname())) {
                            clientContainer.setPersonalColor(color);
                            typeOfUI.printChooseColorOutcome(true);
                        } else {
                            typeOfUI.notifyLobbyStatusColor(nickname, color);
                        }
                    } else {
                        typeOfUI.printChooseColorOutcome(false);
                    }
                    break;

                case COM_LOBBY_STATUS_NOTIFY:
                    typeOfUI.notifyLobbyStatus(message.getNickname(), argsRX.get(0));
                    break;

                // ---------  SETUP ----------- //
                case GAME_SETUP_GIVE_STARTER_CARD:
                    Card starterCard = gsonTranslator.fromJson(message.getArgs().get(0), Card.class);

                    typeOfUI.giveStarterCard((StarterCard) starterCard);
                    break;

                case GAME_SETUP_INIT_HAND_COMMON_MISSION_SHOP:
                    //receive setup hand
                    Card resourceCard1 = gsonTranslator.fromJson(message.getArgs().get(0), Card.class);
                    Card resourceCard2 = gsonTranslator.fromJson(message.getArgs().get(1), Card.class);
                    Card ObjCard1 = gsonTranslator.fromJson(message.getArgs().get(2), Card.class);
                    Hand hand = new Hand();
                    hand.addCard(resourceCard1);
                    hand.addCard(resourceCard2);
                    hand.addCard(ObjCard1);

                    // 2 common mission
                    Mission commonMission1 = gsonTranslator.fromJson(message.getArgs().get(3), Mission.class);
                    Mission commonMission2 = gsonTranslator.fromJson(message.getArgs().get(4), Mission.class);
                    // topDeckCard + 2 visible resource card from shop
                    Card topDeckCardResource = gsonTranslator.fromJson(message.getArgs().get(5), Card.class);
                    Card visible1CardResource = gsonTranslator.fromJson(message.getArgs().get(6), Card.class);
                    Card visible2CardResource = gsonTranslator.fromJson(message.getArgs().get(7), Card.class);

                    // topDeckCard + 2 visible objective card from shop
                    Card topDeckCardObj = gsonTranslator.fromJson(message.getArgs().get(8), Card.class);
                    Card visible1CardObj = gsonTranslator.fromJson(message.getArgs().get(9), Card.class);
                    Card visible2CardObj = gsonTranslator.fromJson(message.getArgs().get(10), Card.class);

                    clientContainer.initialSetupOfResources(hand, commonMission1, commonMission2,
                            topDeckCardResource, visible1CardResource, visible2CardResource,
                            topDeckCardObj, visible1CardObj, visible2CardObj);
                    break;

                case GAME_SETUP_SEND_PERSONAL_MISSION:
                    //receive personal mission
                    Mission personalMission1 = gsonTranslator.fromJson(message.getArgs().get(0), Mission.class);
                    Mission personalMission2 = gsonTranslator.fromJson(message.getArgs().get(1), Mission.class);

                    typeOfUI.givePersonalMission(personalMission1, personalMission2);
                    break;

                case GAME_SETUP_NOTIFY_TURN:
                    typeOfUI.notifyIsYourTurnInitPhase(Boolean.parseBoolean(message.getArgs().get(0)));
                    break;

                // ---------  GAME Phase ----------- //
                case DRAW_CARD_UPDATE_SHOP_CARD_POOL:
                    Card cardShop = gsonTranslator.fromJson(message.getArgs().get(0), Card.class);
                    ShopType shopType = ShopType.valueOf(message.getArgs().get(1));
                    int numShopCardToBeUpdated = Integer.parseInt(message.getArgs().get(2));

                    clientContainer.updateShopCard(cardShop, shopType, numShopCardToBeUpdated);
                    break;

                case DRAWN_CARD_DECK:
                    Card cardDrawn = gsonTranslator.fromJson(message.getArgs().get(0), Card.class);

                    clientContainer.addCardToHand(cardDrawn);
                    break;

                case PLACEMENT_CARD_OUTCOME:
                    boolean isValidPlacement = Boolean.parseBoolean(message.getArgs().get(0));

                    if(isValidPlacement) {
                        Card playedCard = gsonTranslator.fromJson(message.getArgs().get(1), Card.class);
                        int x = Integer.parseInt(message.getArgs().get(2));
                        int y = Integer.parseInt(message.getArgs().get(3));
                        PlayerScoreResource playerScoreResource = gsonTranslator.fromJson(message.getArgs().get(4), PlayerScoreResource.class);
                        int updatedScoreBoardValue = Integer.parseInt(message.getArgs().get(5));

                        System.out.println("Piazzamento valido");
                        clientContainer.playedCard(x, y, playedCard);
                        clientContainer.updatePersonalScore(updatedScoreBoardValue, playerScoreResource);
                        typeOfUI.outcomePlayCard(true);
                        typeOfUI.updatePlayerScoreBoard();
                    } else {
                        typeOfUI.outcomePlayCard(false);
                    }

                    break;

                case UPDATE_OTHER_PLAYER_GAME_MAP:
                    String nickName = message.getArgs().get(0);
                    Card playedCard = gsonTranslator.fromJson(message.getArgs().get(1), Card.class);
                    int x_pos = Integer.parseInt(message.getArgs().get(2));
                    int y_pos = Integer.parseInt(message.getArgs().get(3));
                    int hisNewPlayerScore = Integer.parseInt(message.getArgs().get(4));

                    System.out.println("the player: "+nickName+" has played a card in ("+x_pos+","+y_pos+")");

                    clientContainer.updateOtherPlayerMap(nickName, x_pos, y_pos, playedCard, hisNewPlayerScore);
                    break;

                case ERR_GAME_STATE_COMMAND:
                    GameState currGameState = GameState.valueOf(message.getArgs().get(0));
                    typeOfUI.printErrorCommandSentGameState(currGameState);
                    break;

                case YOUR_TURN:
                    typeOfUI.printIsYourTurn();
                    break;

                case NOT_YOUR_TURN:
                    typeOfUI.printIsNotYourTurn();
                    break;

                case NOTIFY_FINAL_TURN:
                    typeOfUI.printIsYourFinalTurn();
                    break;

                case GAME_ENDED:
                    ArrayList<String> winnerNicknames = gsonTranslator.fromJson(message.getArgs().get(0),
                            new TypeToken<ArrayList<String>>() {}.getType());

                    typeOfUI.printWinners(winnerNicknames);

                    break;

                default:
                    //se non è nessuno dei messaggi precedenti, vuol dire che devo mostrare il messaggio
                    System.out.println(message.getArgs().get(0));
                    break;
            }
        }
    }

    @Override
    public void showMessage(NetworkMessage message) throws RemoteException {}

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

    @Override
    public boolean setNickname(String UUID, String nickname) throws RemoteException {
        setupNickname = nickname;
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_SET_NICKNAME_TCP, nickname)));

        //return inutile
        return false;
    }

    @Override
    public void connectRMI(VirtualView client, String UUID) throws RemoteException {}

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_GET_LOBBIES_TCP)));

        //return inutile
        return null;
    }

    @Override
    public ArrayList<PlayerInfo> joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        setupLobbyName = lobbyName;
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_JOIN_LOBBY_TCP, playerNickname, lobbyName)));

        //return inutile
        return null;
    }

    @Override
    public void leaveLobby(String playerNickname) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_LEAVE_LOBBY_TCP, playerNickname)));
    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        setupLobbyName = lobbyName;

        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_CREATE_LOBBY_TCP, playerNickname, lobbyName)));

        //return inutile
        return false;
    }

    @Override
    public void setPlayerColor(String nickname, ColorType colorChosen) {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_SET_PLAYER_COLOR,
                nickname, String.valueOf(colorChosen))));
    }

    @Override
    public void setPlayerReady(String playerNickname) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.COM_SET_READY_LOBBY_TCP, playerNickname)));
    }

    @Override
    public void playStarterCard(String playerNick, StarterCard starterCard) {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.GAME_SETUP_STARTER_CARD_PLAY,
                playerNick, argsGenerator(starterCard))));

        clientContainer.playedStarterCard(starterCard);
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, Mission mission) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.GAME_SETUP_CHOOSE_PERSONAL_MISSION,
                nickname, argsGenerator(mission))));

        clientContainer.setPersonalMission(mission);
    }

    @Override
    public void playerDraw(String nickname, int numcard, ShopType type) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.DRAW_CARD,
                nickname, String.valueOf(type), String.valueOf(numcard))));
    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, Card playedCard) throws RemoteException {
        socketTx.println(serializeMesssage(new NetworkMessage(MessageType.PLAY_CARD,
                nickname, argsGenerator(playedCard), String.valueOf(x), String.valueOf(y))));
    }

    @Override
    public void typeMessage(String receiver, String sender, String msg) throws RemoteException {

    }

    // not implemented
    @Override
    public void gameEnd() throws RemoteException {}

    private String argsGenerator(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
