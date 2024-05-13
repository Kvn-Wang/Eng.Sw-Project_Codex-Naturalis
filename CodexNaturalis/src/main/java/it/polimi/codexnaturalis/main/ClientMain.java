package it.polimi.codexnaturalis.main;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.rmi.RmiClient;
import it.polimi.codexnaturalis.network.socket.SocketClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientMain {
    /*VirtualServer server;
    GameController gameController;
    public void ClientMain(RmiClient client) {
        System.out.println("Connessso al server RMI");
        server = client.getServer();
        runCli();
    }

    public void ClientMain(SocketClient client) {
        System.out.println("Connessso al server Socket");
    }

    public void runCli() {
        Scanner scan = new Scanner(System.in);

        while(true) {

        }
    }*/

    //TODO: scelta se socket o RMI da linea di comando
    public static void main(String[] args) throws NotBoundException, RemoteException, InterruptedException {
        new RmiClient();
        //GameController gameController = virtualServer.
        //new SocketClient();
    }
}
