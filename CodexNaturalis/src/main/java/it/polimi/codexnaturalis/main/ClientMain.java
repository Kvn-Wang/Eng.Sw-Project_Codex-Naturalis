package it.polimi.codexnaturalis.main;

import it.polimi.codexnaturalis.network.rmi.RmiClient;
import it.polimi.codexnaturalis.network.socket.SocketClient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientMain {
    //TODO: scelta se socket o RMI da linea di comando
    public static void main(String[] args) throws NotBoundException, RemoteException, InterruptedException {
        new RmiClient();
        //new SocketClient();
    }
}
