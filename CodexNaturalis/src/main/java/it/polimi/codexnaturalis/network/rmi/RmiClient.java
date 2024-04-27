package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final String serverName = "VirtualServer";
    final VirtualServer server;

    protected RmiClient() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(UtilCostantValue.ipAddress, UtilCostantValue.portNumber);
        this.server = (VirtualServer) registry.lookup(serverName);
        System.out.println("Connessso al server RMI");
    }

    @Override
    public void showValue(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void reportError(String details) throws RemoteException {

    }

    @Override
    public void askNickname() throws RemoteException {
        Scanner scan = new Scanner(System.in);
        String command;

        System.out.println("Inserisci il tuo nickname:");
        while(true) {
            command = scan.nextLine();
            if(!server.setNickname(command)) {
                System.out.println("Nickname già preso, si prega si selezionare un altro: ");
            } else {
                System.out.println("Benvenuto: "+command);
                break;
            }
        }
    }

    @Override
    public void refreshLobbies() throws RemoteException {

    }
}
