package it.polimi.codexnaturalis.network.socket;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.util.NetworkMessage;

import java.rmi.RemoteException;

public class SocketClient implements VirtualView {

    @Override
    public void showMessage(NetworkMessage message) throws RemoteException {
        
    }

    @Override
    public void connectToGame(GameController gameController) throws RemoteException {

    }
}
