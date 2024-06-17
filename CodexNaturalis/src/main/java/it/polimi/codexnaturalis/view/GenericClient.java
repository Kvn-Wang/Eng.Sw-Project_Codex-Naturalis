package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainerController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public abstract class GenericClient extends UnicastRemoteObject implements GameController, VirtualView {
    protected TypeOfUI typeOfUI;
    protected ClientContainerController clientContainerController;

    public GenericClient(TypeOfUI typeOfUI) throws RemoteException  {
        this.typeOfUI = typeOfUI;
        clientContainerController = new ClientContainer();
    }

    protected void joinPlayerToGame(GameController virtualGameController, ArrayList<PlayerInfo> listOtherPlayer) {
        System.out.println("Game Has Started!");

        clientContainerController.setOtherPlayer(listOtherPlayer);

        typeOfUI.connectGameController(virtualGameController, clientContainerController);
    }

    //ricevo una mano di 2 carte risorsa e 1 carta oro
    protected void initializeInitialHand(Hand hand) {
        clientContainerController.setHand(hand);
        typeOfUI.printHand(hand);
    }

    protected void initializeCommonMission(Mission mission1, Mission mission2) {
        clientContainerController.setCommonMission(mission1, mission2);
        typeOfUI.giveCommonMission(mission1, mission2);
    }

    protected void initializePersonalMission(Mission personalMission1, Mission personalMission2) {
        typeOfUI.givePersonalMission(personalMission1, personalMission2);
    }

    public ClientContainerController getClientContainerController() {
        return clientContainerController;
    }

    @Override
    public void initializeGame() {}
}
