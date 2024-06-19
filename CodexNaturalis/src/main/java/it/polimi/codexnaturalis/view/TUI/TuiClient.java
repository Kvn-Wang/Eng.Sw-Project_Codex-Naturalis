package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.TypeOfUI;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

public class TuiClient implements TypeOfUI {
    protected VirtualServer networkCommand;
    protected GameController virtualGame;
    protected ClientContainer clientContainer;
    Scanner scan;

    public TuiClient() {
        scan = new Scanner(System.in);
    }

    public void initializeClient(VirtualServer virtualServer, ClientContainer clientContainer) throws RemoteException {
        initializationPhase1(virtualServer, clientContainer);
        initializationPhase2();
    }

    //chiamata che garantisce il setup del nickname univoco
    protected void initializationPhase1(VirtualServer virtualServer, ClientContainer clientContainer) throws RemoteException {
        // aggiungo alla UI il potere di comunicare con l'esterno
        connectVirtualNetwork(virtualServer, clientContainer);

        // per com'è stato scritto il codice, dopo questa riga avremo un nickname sicuramente settato correttamente
        // stessa cosa vale per le righe successive
        printSelectionNicknameRequest();
    }

    //chiamata che garantisce alla fine l'avvio del gioco o ritorno alla fase di selezione lobby
    protected void initializationPhase2() {
        try {
            //setup lobbyName unico
            printSelectionCreateOrJoinLobbyRequest();

            lobbyActionReq();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void connectVirtualNetwork(VirtualServer virtualNetworkCommand, ClientContainer clientContainer) {
        this.networkCommand = virtualNetworkCommand;
        this.clientContainer = clientContainer;
    }

    @Override
    public void connectGameController(GameController virtualGame) {
        this.virtualGame = virtualGame;
    }

    public void printSelectionNicknameRequest() throws RemoteException {
        String nickname;

        System.out.println("Inserisci il tuo nickname:");

        nickname = scan.nextLine();
        networkCommand.setNickname(null, nickname);
    }

    @Override
    public void printSelectionNicknameRequestOutcome(boolean positiveOutcome, String nickname) {
        if(positiveOutcome) {
            System.out.println("Benvenuto: "+ nickname);
        } else {
            System.out.println("Nickname già preso, si prega di selezionare un altro.");
            try {
                printSelectionNicknameRequest();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void printLobby() throws RemoteException {
        ArrayList<LobbyInfo> lobbies;

        lobbies = networkCommand.getAvailableLobby();
        System.out.println("Lobby list:");
        if(lobbies != null) {
            for (LobbyInfo elem : lobbies) {
                System.out.println("  - " + elem.getLobbyName() + " | Player: " + elem.getCurrentPlayer() + "/" + elem.getMaxPlayer() + " | started:" + elem.getIsLobbyStarted());
            }
        } else {
            System.out.println("  - No lobby Available");
        }
    }

    public void printSelectionCreateOrJoinLobbyRequest() throws RemoteException {
        String command;

        printLobby();

        System.out.println("Write the name of an existing lobby to join it or write 'CREATE' to create a new lobby");
        command = scan.nextLine();

        switch(command) {
            case "CREATE":
                System.out.println("Inserisci il nuovo nome della lobby, LEAVE per uscire dalla modalità CREATE");
                command = scan.nextLine();

                if(command.equals("LEAVE")) {
                    printSelectionCreateOrJoinLobbyRequest();
                } else {
                    networkCommand.createLobby(clientContainer.getNickname(), command);
                }
                break;
            default:
                networkCommand.joinLobby(clientContainer.getNickname(), command);
                break;
        }
    }

    @Override
    public void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) throws RemoteException {
        if(positiveOutcome) {
            System.out.println("you've joined the lobby: "+lobbyName);
        } else {
            System.out.println("you failed to join the lobby");
            printSelectionCreateOrJoinLobbyRequest();
        }
    }

    @Override
    public void printLobbyStatus(ArrayList<PlayerInfo> lobbyPlayers) throws RemoteException {
        
    }

    @Override
    public void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName) {
        if(outcomePositive) {
            System.out.println("You successfully created the lobby: "+ lobbyName);
        } else {
            System.out.println("Creation lobby failed, "+lobbyName+" name has already been taken!");
            try {
                printSelectionCreateOrJoinLobbyRequest();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // una volta joinata la lobby, puoi entrare o uscire
    public void lobbyActionReq() throws RemoteException {
        String command;
        boolean flag;

        flag = true;
        while(flag) {
            System.out.println("Write READY to set your state as ready, LEAVE to leave the lobby");
            command = scan.nextLine();

            switch(command) {
                case "READY":
                    networkCommand.setPlayerReady(clientContainer.getNickname());
                    flag = false;
                    break;

                case "LEAVE":
                    networkCommand.leaveLobby(clientContainer.getNickname());
                    flag = false;
                    break;

                default:
                    System.out.println("Comando non valido");
                    break;
            }
        }
    }

    @Override
    public void lobbyActionOutcome(boolean isReady) {
        if(isReady) {
            System.out.println("You set yourself ready!");
        } else {
            System.out.println("You've left the lobby!");
            initializationPhase2();
        }
    }

    @Override
    public void notifyLobbyStatus(String otherPlayerNickname, String status) {
        if(status.equals("JOIN")) {
            System.out.println(otherPlayerNickname + " has joined the lobby!");
        } else if(status.equals("LEFT")){
            System.out.println(otherPlayerNickname + " has left the lobby");
        } else if(status.equals("READY")) {
            System.out.println(otherPlayerNickname + " is ready");
        } else if(status.equals("WAIT")) {
            System.out.println("Wait for more players");
        } else {
            System.err.println("Has been called an invalid command: "+status);
        }
    }

    public void printStarterCardReq(Card starterCard) {
        String command;

        System.out.println("Here's your starter card");
        PrintCardClass.printCard(starterCard, true);
        PrintCardClass.printCard(starterCard, false);

        do {
            System.out.println("Type FRONT or BACK to select the face of the starting card");
            command = scan.nextLine();
        } while(!(command.equals("FRONT") || command.equals("BACK")));

        boolean isBack = command.equals("BACK");

        //setta la carta per aggiornare la copia locale
        starterCard.setIsBack(isBack);
        try {
            virtualGame.playStarterCard(clientContainer.getNickname(), (StarterCard) starterCard);
        } catch (Exception e) {
            System.err.println("errore starter card: "+ e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printHand(Hand hand) {
        PrintHandClass.printHand(hand);
    }



    @Override
    public void giveStarterCard(StarterCard starterCard) {
        printStarterCardReq(starterCard);
    }

    @Override
    public void giveCommonMission(Mission mission1, Mission mission2) {
        System.out.println("Common mission 1: " + mission1.getMissionType());
        PrintMissionClass.printMission(mission1);
        System.out.println("Common mission 2: " + mission2.getMissionType());
        PrintMissionClass.printMission(mission2);
    }

    @Override
    public void givePersonalMission(Mission choice1, Mission choice2) {
        String command;
        System.out.println("Personal mission 1: " + choice1.getMissionType());
        PrintMissionClass.printMission(choice1);
        System.out.println("Personal mission 2: " + choice2.getMissionType());
        PrintMissionClass.printMission(choice2);

        do {
            System.out.println("Write 1 to choose mission 1, Write 2 for mission 2");
            command = scan.nextLine();
        }while(!(command.equals("1") || command.equals("2")));

        try {
            if(command.equals("1")) {
                // chiamata a server
                virtualGame.playerPersonalMissionSelect(clientContainer.getNickname(), choice1);

                // memorizzo nella memoria del client la missione scelta
                clientContainer.setPersonalMission(choice1);

                System.out.println("you have chosen personal mission 1: " + choice1.getMissionType());
            }else if(command.equals("2")) {
                virtualGame.playerPersonalMissionSelect(clientContainer.getNickname(), choice2);
                clientContainer.setPersonalMission(choice2);
                System.out.println("you have chosen personal mission 2: " + choice2.getMissionType());
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void notifyIsYourTurn(boolean isYourTurn) {
        System.out.println("Is my turn: "+ isYourTurn);
    }

    @Override
    public void startGamePhase() {
        //TODO
        System.out.println("Menu a tendina non ancora sviluppata..... 1)gioca carta 2)visualizza mappa ecc");
    }

    @Override
    public void outcomePlayCard(boolean isValidPlacement) {

    }

    @Override
    public void printErrorCommandSentGameState(GameState currentGameState) {

    }
}
