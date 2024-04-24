package it.polimi.codexnaturalis.network.rmi;

import java.rmi.RemoteException;

public interface VirtualServer {
    public void connect(VirtualView client) throws RemoteException;
}
