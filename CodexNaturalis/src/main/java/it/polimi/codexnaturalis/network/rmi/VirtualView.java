package it.polimi.codexnaturalis.network.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualView extends Remote {
    public void showValue(Integer number) throws RemoteException;
    public void reportError(String details) throws RemoteException;
    public boolean askNickname() throws RemoteException;
}

