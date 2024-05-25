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
import it.polimi.codexnaturalis.network.util.ServerContainer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ClientHandler implements VirtualView, VirtualServer {
    String playerNickname;
    String lobbyNickname;
    final PrintWriter output;
    final BufferedReader input;
    final ServerContainer serverContainer;

    public ClientHandler(ServerContainer serverContainer, BufferedReader input, BufferedWriter output) {
        this.serverContainer = serverContainer;
        this.input = input;
        this.output = new PrintWriter(output);
        playerNickname = null;
        lobbyNickname = null;

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
        String argsRX;
        NetworkMessage messageRX;
        NetworkMessage messageTX;
        Gson gson = new Gson();

        System.out.println("Socket Listener Avviato");
        while ((jsonRX = input.readLine()) != null) {
            System.out.println("asd");
            messageRX = deSerializeMesssage(jsonRX);

            System.out.println(messageRX.getNickname());
            System.out.println(messageRX.getArgs());

            switch (messageRX.getMessageType()) {
                case COM_SET_NICKNAME_TCP:
                    //get nickname
                    argsRX = messageRX.getArgs().get(0);

                    if(setNickname(null, argsRX)) {
                        playerNickname = argsRX;
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(false));
                    }
                    break;

                case COM_GET_LOBBIES_TCP:
                    //translation of the lobbies in json
                    argsRX = gson.toJson(getAvailableLobby(), new TypeToken<ArrayList<LobbyInfo>>(){}.getType());

                    messageTX = new NetworkMessage(MessageType.COM_GET_LOBBIES_TCP, argsRX);
                    break;

                case COM_CREATE_LOBBY_TCP:
                    //get lobby nickname
                    argsRX = messageRX.getArgs().get(0);

                    if(createLobby(playerNickname, argsRX)) {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(false));
                    }

                    break;

                case COM_JOIN_LOBBY_TCP:
                    //get lobby name
                    argsRX = messageRX.getArgs().get(0);

                    if(joinLobby(playerNickname, argsRX)) {
                        lobbyNickname = argsRX;
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(false));
                    }
                    break;

                case COM_SET_READY_LOBBY_TCP:
                    setPlayerReady(playerNickname, lobbyNickname);

                    messageTX = new NetworkMessage(MessageType.COM_ACK_TCP);
                    break;

                case COM_LEAVE_LOBBY_TCP:
                    leaveLobby(playerNickname, lobbyNickname);

                    messageTX = new NetworkMessage(MessageType.COM_ACK_TCP);
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
    public void showMessage(NetworkMessage message) throws RemoteException {
        String jsonTX;

        jsonTX = serializeMesssage(message);
        output.println(jsonTX);
        output.flush();
    }

    @Override
    public void connectToGame(GameController gameController) throws RemoteException {

    }

    @Override
    public String connect(VirtualView client) throws RemoteException {
        return null;
    }

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
    public void leaveLobby(String playerNickname, String lobbyName) throws RemoteException {
        serverContainer.leaveLobby(playerNickname, lobbyNickname);
        lobbyNickname = null;
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
    public void setPlayerReady(String playerNickname, String lobbyName) throws RemoteException {
        serverContainer.setPlayerReady(playerNickname, lobbyNickname);
    }
}
