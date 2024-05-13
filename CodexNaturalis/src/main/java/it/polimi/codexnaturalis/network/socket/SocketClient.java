package it.polimi.codexnaturalis.network.socket;

import com.google.gson.Gson;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketClient implements VirtualView {
    Socket serverSocket;
    BufferedReader socketRx;
    PrintWriter socketTx;

    public void SocketCLient() throws IOException {
        //TODO bisognerà poi settarlo dinamicamente da linea di comando
        String host = "127.0.0.1";
        int port = UtilCostantValue.portSocketServer;

        serverSocket = new Socket(host, port);

        socketRx = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        socketTx = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));

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

        // Read message type
        while ((jsonRX = socketRx.readLine()) != null) {
            messageRX = deSerializeMesssage(jsonRX);

            // Read message and perform action
            switch (messageRX.getMessageType()) {
                case COM_ACK_TCP:
                    break;
                case COM_ERROR_TCP:
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
}
