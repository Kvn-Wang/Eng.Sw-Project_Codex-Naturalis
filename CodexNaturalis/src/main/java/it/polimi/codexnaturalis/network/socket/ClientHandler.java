package it.polimi.codexnaturalis.network.socket;

import com.google.gson.Gson;
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
    final PrintWriter output;
    final BufferedReader input;
    final ServerContainer serverContainer;

    public ClientHandler(ServerContainer serverContainer, BufferedReader input, BufferedWriter output) {
        this.serverContainer = serverContainer;
        this.input = input;
        this.output = new PrintWriter(output);
    }

    public void runSocketListener() throws IOException {
        String jsonRX;
        NetworkMessage messageRX;
        NetworkMessage messageTX;
        String args;

        while ((jsonRX = input.readLine()) != null) {
            messageRX = deSerializeMesssage(jsonRX);

            switch (messageRX.getMessageType()) {
                case COM_SET_NICKNAME_TCP:
                    //get nickname
                    args = messageRX.getArgs();
                    if(serverContainer.playerCreation(this, args)) {
                        messageTX = new NetworkMessage(MessageType.COM_SET_NICKNAME_OUTCOME_TCP, String.valueOf(true));
                    } else {
                        messageTX = new NetworkMessage(MessageType.COM_SET_NICKNAME_OUTCOME_TCP, String.valueOf(false));
                    }

                    showMessage(messageTX);
                    break;
                default:
                    //TODO comunicazione comando errato al client
                    System.err.println("[INVALID MESSAGE]");
                    break;
            }
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
    public void reportError(String details) throws RemoteException {

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
