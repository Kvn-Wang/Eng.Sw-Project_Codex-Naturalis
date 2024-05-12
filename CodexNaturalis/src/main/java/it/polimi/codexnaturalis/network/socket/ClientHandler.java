package it.polimi.codexnaturalis.network.socket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.MessageType;
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

public class ClientHandler extends Thread implements VirtualView {
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
    }

    public void runSocketListener() throws IOException {
        String jsonRX;
        String argsTX;
        NetworkMessage messageRX;
        NetworkMessage messageTX;
        Gson gson;

        gson = new Gson();
        while ((jsonRX = input.readLine()) != null) {
            messageRX = deSerializeMesssage(jsonRX);

            switch (messageRX.getMessageType()) {
                case COM_SET_NICKNAME_TCP:
                    //get nickname
                    argsTX = messageRX.getArgs();

                    if(serverContainer.playerCreation(this, argsTX)) {
                        playerNickname = argsTX;
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(false));
                    }
                    break;

                case COM_GET_LOBBIES_TCP:
                    //translation of the lobbies in json
                    argsTX = gson.toJson(serverContainer.getActiveLobby(), new TypeToken<ArrayList<LobbyInfo>>(){}.getType());

                    messageTX = new NetworkMessage(MessageType.COM_GET_LOBBIES_TCP, argsTX);
                    break;

                case COM_JOIN_LOBBY_TCP:
                    //get lobby name
                    argsTX = messageRX.getArgs();

                    if(serverContainer.joinPlayerToLobby(playerNickname, argsTX)) {
                        lobbyNickname = argsTX;
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_ACK_TCP, String.valueOf(false));
                    }
                    break;

                case COM_SET_READY_LOBBY_TCP:
                    serverContainer.setPlayerReady(playerNickname, lobbyNickname);
                    messageTX = new NetworkMessage(MessageType.COM_ACK_TCP);
                    break;

                case COM_LEAVE_LOBBY_TCP:
                    serverContainer.leaveLobby(playerNickname, lobbyNickname);
                    lobbyNickname = null;

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

    public void run() {
        try {
            runSocketListener();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
