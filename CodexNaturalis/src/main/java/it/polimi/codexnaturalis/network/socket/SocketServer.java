package it.polimi.codexnaturalis.network.socket;

import it.polimi.codexnaturalis.network.util.ServerContainer;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer extends Thread {
    ServerContainer serverContainer;
    int portServer;
    ServerSocket serverSocket;

    public SocketServer() throws IOException {
        portServer = UtilCostantValue.portSocketServer;
        this.serverContainer = ServerContainer.getInstance();
        serverSocket = new ServerSocket(portServer);
    }

    private void runRxServer() throws IOException {
        Socket clientSocket;
        while ((clientSocket = this.serverSocket.accept()) != null) {
            InputStreamReader clientSocketRX = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter clientSocketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            ClientHandler handler = new ClientHandler(serverContainer, new BufferedReader(clientSocketRX), new BufferedWriter(clientSocketTx));
            handler.start();
        }
    }

    public void run() {
        System.out.println("TCP server started");

        try {
            runRxServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
