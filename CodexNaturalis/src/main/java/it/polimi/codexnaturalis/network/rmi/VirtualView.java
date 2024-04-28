package it.polimi.codexnaturalis.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {
    public void showValue(String message) throws RemoteException;
    public void reportError(String details) throws RemoteException;
    public void initializeClient() throws RemoteException;
    public void refreshLobbies() throws RemoteException;
}

