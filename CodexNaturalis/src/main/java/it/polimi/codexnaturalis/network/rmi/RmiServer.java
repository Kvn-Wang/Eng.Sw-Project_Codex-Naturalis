package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.network.ServerMain;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RmiServer extends Thread implements VirtualServer {
    @Override
    public void connect(VirtualView client) throws RemoteException {
        //lobbyLessClients.add(client);
    }

    public void run() {
        // Code that will run in a separate thread
        for (int i = 0; i < 5; i++) {
            System.out.println("Thread is running: " + i);
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }
    }
}
