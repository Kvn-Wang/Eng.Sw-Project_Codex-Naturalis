package it.polimi.codexnaturalis.network.socket;

import com.google.gson.Gson;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.GenericClient;
import it.polimi.codexnaturalis.view.TypeOfUI;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class SocketClient extends GenericClient {
    Socket serverSocket;
    BufferedReader socketRx;
    PrintWriter socketTx;
    // variabili usata per la logica di invio e aspetta la risposta
    boolean ackArrived;
    boolean outcomeReceived;

    public SocketClient(TypeOfUI typeOfUI) throws RemoteException {
        super(typeOfUI);
    }

    public void SocketCLient() throws IOException {
        //TODO bisognerà poi settarlo dinamicamente da linea di comando
        String host = "127.0.0.1";
        int port = UtilCostantValue.portSocketServer;

        serverSocket = new Socket(host, port);

        socketRx = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        socketTx = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));

        ackArrived = false;

        new Thread(() -> {
            try {
                runRxClient();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void runRxClient() throws IOException {
        NetworkMessage messageRX;
        String jsonRX;
        String argsRX;

        // Read message type
        while ((jsonRX = socketRx.readLine()) != null) {
            messageRX = deSerializeMesssage(jsonRX);

            // Read message and perform action
            switch (messageRX.getMessageType()) {
                case COM_ACK_TCP:
                    ackArrived = true;
                    //getArgs = (String) boolean
                    argsRX = messageRX.getArgs();

                    outcomeReceived = Boolean.parseBoolean(argsRX);
                    notify();
                    break;

                case COM_ERROR_TCP:
                    //TODO
                    break;
                default:
                    break;
            }
        }
    }

    /*private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            int command = scan.nextInt();

            if (command == 0) {
                server.reset();
            } else {
                server.add(command);
            }
        }
    }*/

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

            default:
                throw new RuntimeException("Received invalid typeOfMessage: "+ message.getMessageType());
        }
        System.out.println(message.getArgs());
    }

    @Override
    public void connectToGame(GameController gameController) throws RemoteException {

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
    public void selectNickname(String nickname) throws RemoteException {

    }

    @Override
    public ArrayList<LobbyInfo> getLobbies() throws RemoteException {
        return null;
    }

    @Override
    public void joinLobby(String selection) throws RemoteException {

    }

    @Override
    public void createLobby(String lobbyName) throws RemoteException {

    }

    @Override
    public void setReady() throws RemoteException {

    }

    @Override
    public void leaveLobby() throws RemoteException {

    }
}
