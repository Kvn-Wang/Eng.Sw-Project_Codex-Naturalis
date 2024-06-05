package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.PersonalizedException;
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
    }

    protected void initializeClient(VirtualServer virtualServer) throws RemoteException {
        clientContainerController = new ClientContainer();

        initializationPhase1(virtualServer);
        initializationPhase2();
    }

    //chiamata che garantisce il setup del nickname univoco
    protected void initializationPhase1(VirtualServer virtualServer) throws RemoteException {
        // aggiungo alla UI il potere di comunicare con l'esterno
        typeOfUI.connectVirtualNetwork(virtualServer, clientContainerController);

        // per com'è stato scritto il codice, dopo questa riga avremo un nickname sicuramente settato correttamente
        // stessa cosa vale per le righe successive
        typeOfUI.printSelectionNicknameRequest();
    }

    //chiamata che garantisce alla fine l'avvio del gioco o ritorno alla fase di selezione lobby
    protected void initializationPhase2() throws RemoteException {
        //setup lobbyName unico
        typeOfUI.printSelectionCreateOrJoinLobbyRequest();

        typeOfUI.printReadyOrLeaveSelection();
    }

    protected void joinPlayerToGame(GameController virtualGameController, ArrayList<PlayerInfo> listOtherPlayer) {
        System.out.println("Game Has Started!");

        clientContainerController.setOtherPlayer(listOtherPlayer);

        typeOfUI.connectGameController(virtualGameController, clientContainerController);
    }

    //la starterCard deve essere nella prima carta della mano (non la memorizziamo nel container)
    protected void playStarterCard(Card starterCard) {
        typeOfUI.printStarterCardReq(starterCard);
    }

    //ricevo una mano di 2 carte risorsa e 1 carta oro
    protected void initializeInitialHand(Hand hand) {
        clientContainerController.setHand(hand);
        typeOfUI.printHand(hand);
    }

    protected void initializeCommonMission(Mission mission1, Mission mission2) {
        clientContainerController.setCommonMission(mission1, mission2);
        typeOfUI.printCommonMission(mission1, mission2);
    }

    protected void initializePersonalMission(Mission personalMission1, Mission personalMission2) {
        typeOfUI.printPersonalMissionReq(personalMission1, personalMission2);
    }

    @Override
    public void initializeGame() {}
}
