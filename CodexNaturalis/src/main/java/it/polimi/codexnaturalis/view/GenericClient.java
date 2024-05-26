package it.polimi.codexnaturalis.view;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.util.PlayerInfo;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public abstract class GenericClient extends UnicastRemoteObject implements GameController, VirtualServer, VirtualView {
    protected String playerNickname;
    protected String lobbyNickname;
    protected TypeOfUI typeOfUI;
    ArrayList<PlayerInfo> listOtherPlayer;

    public GenericClient(TypeOfUI typeOfUI) throws RemoteException  {
        this.typeOfUI = typeOfUI;
    }

    protected void initializeClient(VirtualServer virtualServer) throws RemoteException {
        initializationPhase1(virtualServer);
        initializationPhase2();
    }

    //chiamata che garantisce il setup del nickname univoco
    protected void initializationPhase1(VirtualServer virtualServer) throws RemoteException {
        // aggiungo alla UI il potere di comunicare con l'esterno
        typeOfUI.connectVirtualNetwork(virtualServer);

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

    public void joinPlayerToGame(GameController virtualGameController, ArrayList<PlayerInfo> listOtherPlayer) {
        System.out.print("Game Has Started!");
        typeOfUI.connectGameController(virtualGameController);
        this.listOtherPlayer = listOtherPlayer;
    }
}
