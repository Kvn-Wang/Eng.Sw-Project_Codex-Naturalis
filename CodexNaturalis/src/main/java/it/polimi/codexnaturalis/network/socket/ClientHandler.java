package it.polimi.codexnaturalis.network.socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.mission.MissionAdapter;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.CardTypeAdapter;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.Lobby;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.network.util.ServerContainer;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.Hand;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.HandGsonAdapter;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientHandler implements VirtualView, VirtualServer {
    final PrintWriter output;
    final BufferedReader input;
    private ServerContainer serverContainer;
    private GameController virtualGame;
    private Gson gsonTranslator = new GsonBuilder()
            .registerTypeAdapter(Card.class, new CardTypeAdapter())
            .registerTypeAdapter(Hand.class, new HandGsonAdapter())
            .registerTypeAdapter(Mission.class, new MissionAdapter())
            .create();

    public ClientHandler(ServerContainer serverContainer, Socket clientSocket) throws IOException {
        this.serverContainer = serverContainer;
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));;
        this.output = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

        new Thread(() -> {
            try {
                runSocketListener();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void runSocketListener() throws IOException {
        String jsonRX;
        String argsRX0, argsRX1;
        NetworkMessage messageRX;
        NetworkMessage messageTX = null;
        String nickname;

        while ((jsonRX = input.readLine()) != null) {
            messageRX = deSerializeMesssage(jsonRX);

            switch (messageRX.getMessageType()) {
                // ---------  Lobby Phase ----------- //
                case COM_SET_NICKNAME_TCP:
                    //get nickname
                    argsRX0 = messageRX.getArgs().get(0);

                    if(setNickname(null, argsRX0)) {
                        messageTX = new NetworkMessage(MessageType.COM_SET_NICKNAME_RESPONSE_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_SET_NICKNAME_RESPONSE_TCP, String.valueOf(false));
                    }

                    showMessage(messageTX);
                    break;

                case COM_GET_LOBBIES_TCP:
                    //translation of the lobbies in json
                    argsRX0 = gsonTranslator.toJson(getAvailableLobby(), new TypeToken<ArrayList<LobbyInfo>>(){}.getType());

                    messageTX = new NetworkMessage(MessageType.COM_GET_LOBBIES_RESPONSE_TCP, argsRX0);

                    showMessage(messageTX);
                    break;

                case COM_CREATE_LOBBY_TCP:
                    //get player nickname
                    argsRX0 = messageRX.getArgs().get(0);
                    //get lobby nickname
                    argsRX1 = messageRX.getArgs().get(1);

                    if(createLobby(argsRX0, argsRX1)) {
                        messageTX = new NetworkMessage(MessageType.COM_CREATE_LOBBY_OUTCOME_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_CREATE_LOBBY_OUTCOME_TCP, String.valueOf(false));
                    }

                    showMessage(messageTX);
                    break;

                case COM_JOIN_LOBBY_TCP:
                    //get player name
                    argsRX0 = messageRX.getArgs().get(0);
                    argsRX1 = messageRX.getArgs().get(1);

                    if(joinLobby(argsRX0, argsRX1) != null) {
                        messageTX = new NetworkMessage(MessageType.COM_JOIN_LOBBY_TCP_OUTCOME, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_JOIN_LOBBY_TCP_OUTCOME, String.valueOf(false));
                    }

                    showMessage(messageTX);
                    break;

                case COM_SET_PLAYER_COLOR:
                    nickname = messageRX.getArgs().get(0);
                    ColorType colorChosen = ColorType.valueOf(messageRX.getArgs().get(1));

                    setPlayerColor(nickname, colorChosen);
                    break;

                case COM_SET_READY_LOBBY_TCP:
                    argsRX0 = messageRX.getArgs().get(0);

                    /**
                     * it is important to first notify that we received the ready command and then change it in the model
                     * because the next function could start the game and lead to some problem in the client UI later on
                     */
                    messageTX = new NetworkMessage(MessageType.COM_SET_READY_LOBBY_RESPONSE_TCP);
                    showMessage(messageTX);

                    setPlayerReady(argsRX0);
                    break;

                case COM_LEAVE_LOBBY_TCP:
                    argsRX0 = messageRX.getArgs().get(0);
                    leaveLobby(argsRX0);
                    messageTX = new NetworkMessage(MessageType.COM_LEAVE_LOBBY_RESPONSE_TCP);

                    showMessage(messageTX);
                    break;

                // ---------  Game Setup Phase ----------- //
                case GAME_SETUP_STARTER_CARD_PLAY:
                    nickname = messageRX.getArgs().get(0);
                    Card starterCard = gsonTranslator.fromJson(messageRX.getArgs().get(1), Card.class);

                    virtualGame.playStarterCard(nickname, (StarterCard) starterCard);
                    break;

                case GAME_SETUP_CHOOSE_PERSONAL_MISSION:
                    nickname = messageRX.getArgs().get(0);
                    Mission chosenMission = gsonTranslator.fromJson(messageRX.getArgs().get(1), Mission.class);

                    virtualGame.playerPersonalMissionSelect(nickname, chosenMission);
                    break;

                // ---------  GAME Phase ----------- //
                case PLAY_CARD:
                    nickname = messageRX.getArgs().get(0);
                    Card playedCard = gsonTranslator.fromJson(messageRX.getArgs().get(1), Card.class);
                    int x = Integer.parseInt(messageRX.getArgs().get(2));
                    int y = Integer.parseInt(messageRX.getArgs().get(3));;

                    virtualGame.playerPlayCard(nickname, x, y, playedCard);
                    break;

                case DRAW_CARD:
                    nickname = messageRX.getArgs().get(0);
                    ShopType shopTypeFromDraw = ShopType.valueOf(messageRX.getArgs().get(1));
                    int numCard = Integer.parseInt(messageRX.getArgs().get(2));

                    virtualGame.playerDraw(nickname, numCard, shopTypeFromDraw);
                    break;

                case WRITE_MESSAGE:
                    String sender = messageRX.getArgs().get(0);
                    String receiver = messageRX.getArgs().get(1);
                    String msg = messageRX.getArgs().get(2);

                    virtualGame.typeMessage(sender, receiver, msg);
                    break;

                default:
                    break;
            }
        }
    }

    private String serializeMesssage(NetworkMessage message) {
        String json;

        json = gsonTranslator.toJson(message);

        return json;
    }

    private NetworkMessage deSerializeMesssage(String json) {
        NetworkMessage networkMessage;

        networkMessage = gsonTranslator.fromJson(json, NetworkMessage.class);

        return networkMessage;
    }

    @Override
    public void showMessage(NetworkMessage message) throws RemoteException {
        String jsonTX;

        jsonTX = serializeMesssage(message);
        output.println(jsonTX);
    }

    @Override
    public void connectToGame(GameController gameController, ArrayList<PlayerInfo> listOtherPlayer) throws RemoteException {
        String jsonTX;
        String arg;

        virtualGame = gameController;

        arg = gsonTranslator.toJson(listOtherPlayer);

        //non passa il gameController via rete perchè con TCP non si può, sarà poi il client che emulerà il suo comportamento
        jsonTX = serializeMesssage(new NetworkMessage(MessageType.COM_CONNECT_GAME_TCP, arg));
        output.println(jsonTX);
    }

    @Override
    public void connectRMI(VirtualView client, String UUID) throws RemoteException {}

    @Override
    public boolean setNickname(String userID, String nickname) throws RemoteException {
        if(serverContainer.playerCreation(this, nickname)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException {
        ArrayList<LobbyInfo> lobbiesInfo = new ArrayList<>();

        for(Lobby elem : serverContainer.getActiveLobby()) {
            lobbiesInfo.add(elem.getLobbyInfo());
        }

        return lobbiesInfo;
    }

    @Override
    public ArrayList<PlayerInfo> joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        return serverContainer.joinPlayerToLobby(playerNickname, lobbyName);
    }

    @Override
    public void leaveLobby(String playerNickname) throws RemoteException {
        serverContainer.leaveLobby(playerNickname);
    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        if(serverContainer.lobbyCreation(lobbyName)) {
            System.out.println(lobbyName + " lobby has been created");

            serverContainer.joinPlayerToLobby(playerNickname, lobbyName);
            System.out.println(playerNickname + " has joined " + lobbyName + " lobby");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setPlayerColor(String nickname, ColorType colorChosen) {
        serverContainer.setPlayerColor(nickname, colorChosen);
    }

    @Override
    public void setPlayerReady(String playerNickname) throws RemoteException {
        serverContainer.setPlayerReady(playerNickname);
    }
}
