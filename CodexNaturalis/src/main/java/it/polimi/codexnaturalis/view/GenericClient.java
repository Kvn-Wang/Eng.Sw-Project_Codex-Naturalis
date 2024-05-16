package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class GenericClient extends UnicastRemoteObject implements VirtualNetworkCommand, VirtualView {
    protected String playerNickname;
    protected String lobbyNickname;
    protected TypeOfUI typeOfUI;

    public GenericClient(TypeOfUI typeOfUI) throws RemoteException  {
        this.typeOfUI = typeOfUI;
    }
}
