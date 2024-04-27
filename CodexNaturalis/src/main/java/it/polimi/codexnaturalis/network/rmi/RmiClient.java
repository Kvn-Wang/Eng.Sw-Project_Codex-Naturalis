package it.polimi.codexnaturalis.network.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;

    protected RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }

    @Override
    public void showValue(Integer number) throws RemoteException {

    }

    @Override
    public void reportError(String details) throws RemoteException {

    }

    @Override
    public boolean askNickname() throws RemoteException {
        return false;
    }
}
