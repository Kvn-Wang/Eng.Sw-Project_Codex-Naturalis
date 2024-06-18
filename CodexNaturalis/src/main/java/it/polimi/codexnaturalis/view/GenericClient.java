package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
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
        this.typeOfUI = typeOfUI;
        clientContainer = new ClientContainer();
    }

    protected void joinPlayerToGame(GameController virtualGameController, ArrayList<PlayerInfo> listOtherPlayer) {
        System.out.println("Game Has Started!");

        clientContainer.setOtherPlayerList(listOtherPlayer);

        typeOfUI.connectGameController(virtualGameController);
    }

    //ricevo una mano di 2 carte risorsa e 1 carta oro
    protected void initializeInitialHand(Hand hand) {
        clientContainer.setPersonalHand(hand);
        typeOfUI.printHand(hand);
    }

    protected void initializeCommonMission(Mission mission1, Mission mission2) {
        clientContainer.setCommonMission1(mission1);
        clientContainer.setCommonMission2(mission2);
        typeOfUI.giveCommonMission(mission1, mission2);
    }

    protected void initializePersonalMission(Mission personalMission1, Mission personalMission2) {
        typeOfUI.givePersonalMission(personalMission1, personalMission2);
    }

    public ClientContainer getClientContainerController() {
        return clientContainer;
    }

    @Override
    public void initializeGame() {}
}
