package it.polimi.codexnaturalis.network.socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.Lobby;
import it.polimi.codexnaturalis.network.util.MessageType;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.network.util.ServerContainer;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientHandler implements VirtualView, VirtualServer {
    final PrintWriter output;
    final BufferedReader input;
    final ServerContainer serverContainer;
    private final Gson gson = new Gson();

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
        NetworkMessage messageTX;
        Gson gson = new Gson();

        while ((jsonRX = input.readLine()) != null) {
            messageRX = deSerializeMesssage(jsonRX);

            switch (messageRX.getMessageType()) {
                case COM_SET_NICKNAME_TCP:
                    //get nickname
                    argsRX0 = messageRX.getArgs().get(0);

                    if(setNickname(null, argsRX0)) {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(false));
                    }
                    break;

                case COM_GET_LOBBIES_TCP:
                    //translation of the lobbies in json
                    argsRX0 = gson.toJson(getAvailableLobby(), new TypeToken<ArrayList<LobbyInfo>>(){}.getType());

                    messageTX = new NetworkMessage(MessageType.COM_GET_LOBBIES_TCP, argsRX0);
                    break;

                case COM_CREATE_LOBBY_TCP:
                    //get player nickname
                    argsRX0 = messageRX.getArgs().get(0);
                    //get lobby nickname
                    argsRX1 = messageRX.getArgs().get(1);

                    if(createLobby(argsRX0, argsRX1)) {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(false));
                    }

                    break;

                case COM_JOIN_LOBBY_TCP:
                    //get player name
                    argsRX0 = messageRX.getArgs().get(0);
                    argsRX1 = messageRX.getArgs().get(1);

                    if(joinLobby(argsRX0, argsRX1)) {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(false));
                    }
                    break;

                case COM_SET_READY_LOBBY_TCP:
                    argsRX0 = messageRX.getArgs().get(0);
                    setPlayerReady(argsRX0);

                    messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    break;

                case COM_LEAVE_LOBBY_TCP:
                    argsRX0 = messageRX.getArgs().get(0);
                    leaveLobby(argsRX0);

                    messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    break;

                default:
                    messageTX = new NetworkMessage(MessageType.COM_ERROR_TCP);
                    break;
            }
            showMessage(messageTX);
        }
    }

    private String serializeMesssage(NetworkMessage message) {
        String json;

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
    public void showMessage(NetworkMessage message) throws RemoteException {
        String jsonTX;

        jsonTX = serializeMesssage(message);
        output.println(jsonTX);
    }

    @Override
    public void connectToGame(GameController gameController, ArrayList<PlayerInfo> listOtherPlayer) throws RemoteException {
        String jsonTX;
        String arg;

        arg = gson.toJson(listOtherPlayer);

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
    public boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        if(serverContainer.joinPlayerToLobby(playerNickname, lobbyName)) {
            return true;
        } else {
            return false;
        }
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
    public void setPlayerReady(String playerNickname) throws RemoteException {
        serverContainer.setPlayerReady(playerNickname);
    }
}
