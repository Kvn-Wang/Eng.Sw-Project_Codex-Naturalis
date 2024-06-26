package it.polimi.codexnaturalis.network.socket;

import it.polimi.codexnaturalis.network.util.ServerContainer;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer extends Thread {
    ServerContainer serverContainer;
    int portServer = UtilCostantValue.portSocketServer;
    ServerSocket serverSocket;

    public SocketServer() throws IOException {
        this.serverContainer = ServerContainer.getInstance();
        serverSocket = new ServerSocket(portServer);
        //serverSocket.bind(new InetSocketAddress(networkAddress, 50505));
    }

    private void runRxServer() throws IOException {
        Socket clientSocket;
        while (true) {
            clientSocket = this.serverSocket.accept();

            System.out.println("Someone has connected using Socket");

            new ClientHandler(serverContainer, clientSocket);
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
