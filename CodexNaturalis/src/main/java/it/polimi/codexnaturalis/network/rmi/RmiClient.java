package it.polimi.codexnaturalis.network.rmi;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.NetworkMessage;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.GenericClient;
import it.polimi.codexnaturalis.view.TypeOfUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.UUID;

public class RmiClient extends GenericClient {
    private final String serverName = UtilCostantValue.RMIServerName;
    private final VirtualServer server;
    private GameController gameController;
    private Registry registry;
    // variabile di identificativo temporanea -> inutile dopo il setting del nickname
    private String ID;

    public RmiClient(TypeOfUI typeOfUI) throws RemoteException, NotBoundException, InterruptedException {
        //setup communicazione bidirezionale tra rete e oggetto grafico
        super(typeOfUI);

        registry = LocateRegistry.getRegistry(UtilCostantValue.ipAddressSocketServer, UtilCostantValue.portRmiServer);
        this.server = (VirtualServer) registry.lookup(serverName);

        System.out.println("Collegato al server: " + serverName);

        // scambio dell'oggetto per comunicare col server
        ID = UUID.randomUUID().toString();
        server.connectRMI(this, ID);

        initializeClient(this);
    }

    @Override
    public void showMessage(NetworkMessage message) throws RemoteException {
        switch(message.getMessageType()) {
            case STATUS_PLAYER_CHANGE:

                break;

            case WRONG_TYPE_SHOP:
                break;

            case NOT_YOUR_TURN:
                break;

            case SCORE_UPDATE:
                break;

            case SWITCH_PLAYER_VIEW:
                break;

            case CORRECT_DRAW_CARD:
                break;

            case CORRECT_PLACEMENT:
                break;
        }
        System.out.println(message.getArgs().get(0));
    }

    @Override
    public void connectToGame(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void connectRMI(VirtualView client, String UUID) throws RemoteException {}

    @Override
    public boolean setNickname(String UUID, String nickname) throws RemoteException {
        if(server.setNickname(this.ID, nickname)) {
            this.playerNickname = nickname;
            typeOfUI.printSelectionNicknameRequestOutcome(true, this.playerNickname);
        } else {
            typeOfUI.printSelectionNicknameRequestOutcome(false, nickname);

            //emula un loop infinito finchè non sceglie un nickname corretto
            typeOfUI.printSelectionNicknameRequest();
        }

        //è un ritorno fittizzio, non è utilizzato da nessuno
        return false;
    }

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException {
        return server.getAvailableLobby();
    }

    @Override
    public boolean joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        if(server.joinLobby(this.playerNickname, lobbyName)) {
            this.lobbyNickname = lobbyName;
            typeOfUI.printJoinLobbyOutcome(true, this.lobbyNickname);
        } else {
            typeOfUI.printJoinLobbyOutcome(false, lobbyName);

            //start loop
            typeOfUI.printSelectionCreateOrJoinLobbyRequest();
        }

        return false;
    }

    @Override
    public void leaveLobby(String playerNickname) throws RemoteException {
        server.leaveLobby(this.playerNickname);
        typeOfUI.printReadyOrLeaveSelectionOutcome(false);

        initializationPhase2();
    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        if(server.createLobby(this.playerNickname, lobbyName)) {
            this.lobbyNickname = lobbyName;
            typeOfUI.printCreationLobbyRequestOutcome(true, this.lobbyNickname);
        } else {
            typeOfUI.printCreationLobbyRequestOutcome(false, lobbyName);
            typeOfUI.printSelectionCreateOrJoinLobbyRequest();
        }

        //ritorno fittizzio
        return false;
    }

    @Override
    public void setPlayerReady(String playerNickname) throws RemoteException {
        server.setPlayerReady(this.playerNickname);
        typeOfUI.printReadyOrLeaveSelectionOutcome(true);
    }
}
