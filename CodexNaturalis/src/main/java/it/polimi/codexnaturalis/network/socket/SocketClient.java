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
    private final Object lock = new Object();
    boolean outcomeReceived;

    public SocketClient(TypeOfUI typeOfUI) throws IOException {
        super(typeOfUI);

        String host = "127.0.0.1";
        int port = UtilCostantValue.portSocketServer;

        serverSocket = new Socket(host, port);

        socketRx = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        //socketTx = new ServerProxySocket(new PrintWriter(serverSocket.getOutputStream(), true));
        socketTx = new PrintWriter(new BufferedWriter(new OutputStreamWriter(serverSocket.getOutputStream())));

        System.out.println("asd");

        //socketTx.println("ciao");


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
        NetworkMessage messageRX;
        String jsonRX;
        String argsRX;

        // Read message type
        while ((jsonRX = socketRx.readLine()) != null) {
            messageRX = deSerializeMesssage(jsonRX);

            System.out.println(messageRX);

            // Read message and perform action
            switch (messageRX.getMessageType()) {
                case COM_ACK_TCP:
                    ackArrived = true;
                    //getArgs = (String) boolean
                    argsRX = messageRX.getArgs().get(0);

                    outcomeReceived = Boolean.parseBoolean(argsRX);
                    doNotify();
                    break;

                case COM_ERROR_TCP:
                    //TODO
                    break;
                default:
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
        System.out.println(message.getArgs().get(0));
    }

    @Override
    public void connectToGame(GameController gameController) {

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
    public boolean setNickname(String nickname) throws RemoteException {
        //socketTx.setNickname(nickname);
        doWait();
        if(outcomeReceived == true) {
            System.out.println("yey");
        } else {
            System.out.println("dio");
        }

        return false;
    }

    @Override
    public void connectRMI(VirtualView client, String UUID) throws RemoteException {}

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException {
        return null;
    }

    @Override
    public boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        return false;
    }

    @Override
    public void leaveLobby(String playerNickname) throws RemoteException {

    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        return false;
    }

    @Override
    public void setPlayerReady(String playerNickname) throws RemoteException {

    }
}
