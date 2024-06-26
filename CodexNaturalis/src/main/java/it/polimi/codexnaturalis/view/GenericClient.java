package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public abstract class GenericClient extends UnicastRemoteObject implements GameController, VirtualView {
    protected TypeOfUI typeOfUI;
    protected ClientContainer clientContainer;

    public GenericClient(TypeOfUI typeOfUI) throws RemoteException  {
        super();

        this.typeOfUI = typeOfUI;
        clientContainer = new ClientContainer();
    }

    protected void joinPlayerToGame(GameController virtualGameController, ArrayList<PlayerInfo> listOtherPlayer) {
        System.out.println("Game Has Started!");

        clientContainer.setOtherPlayerList(listOtherPlayer);

        typeOfUI.connectGameController(virtualGameController);
    }

    public ClientContainer getClientContainerController() {
        return clientContainer;
    }

    @Override
    public void initializeGame() {}
}
