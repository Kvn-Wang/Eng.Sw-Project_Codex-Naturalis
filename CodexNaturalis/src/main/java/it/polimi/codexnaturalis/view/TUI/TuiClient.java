package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.view.TypeOfUI;
import it.polimi.codexnaturalis.view.VirtualModel.ClientContainer;
import it.polimi.codexnaturalis.view.VirtualModel.OtherPlayerData;

import java.rmi.RemoteException;
import java.util.*;

public class TuiClient implements TypeOfUI {
    protected VirtualServer networkCommand;
    protected GameController virtualGame;
    protected ClientContainer clientContainer;
    boolean outcomeSetNick = false;
    boolean outcomeJoinLobby = false;
    boolean outcomeChooseColor = false;
    boolean outcomeJoinGame = false;

    private Scanner scan;
    private final Object lock = new Object();
    private StarterCard tempStarterCard;
    private Mission tempMission1;
    private Mission tempMission2;
    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_BLUE = "\033[34m";
    private static final String ANSI_RED = "\u001B[31m";

    public TuiClient() {
        scan = new Scanner(System.in);
    }

    public void initializeClient(VirtualServer virtualServer, ClientContainer clientContainer) throws RemoteException {
        // aggiungo alla UI il potere di comunicare con l'esterno
        this.networkCommand = virtualServer;
        this.clientContainer = clientContainer;

        connectToGameProcedure();
    }

    private void connectToGameProcedure() throws RemoteException {
        while(!outcomeJoinGame) {
            while(!outcomeSetNick) {
                printSelectionNicknameRequest();
                doWait();
            }

            while(!outcomeJoinLobby) {
                networkCommand.getAvailableLobby();
                doWait(); // aspetti che stampi le lobby

                String command = printSelectionCreateOrJoinLobbyRequest();
                if(command.equals("CREATE")) {
                    doWait();
                } else if(command.equals("LEAVE")) {
                } else if(command.equals("JOIN")) {
                    doWait();
                }
            }

            while(!outcomeChooseColor) {
                printChooseColorReq();
                doWait();
            }

            lobbyActionReq();
            doWait();
        }

        gameSetupProcedure();
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
            System.out.println("Welcome: "+ nickname);
            outcomeSetNick = true;
        } else {
            System.out.println("Nickname already taken, please take an another one.");
            outcomeSetNick = false;
        }

        doNotify();
    }

    @Override
    public void giveLobbies(ArrayList<LobbyInfo> lobbies) {
        System.out.println("Lobby list:");
        if(lobbies != null) {
            for (LobbyInfo elem : lobbies) {
                System.out.println("  - " + elem.getLobbyName() + " | Player: " + elem.getCurrentPlayer() + "/" + elem.getMaxPlayer() + " | started:" + elem.getIsLobbyStarted());
            }
        } else {
            System.out.println("  - No lobby Available");
        }

        doNotify();
    }

    private String printSelectionCreateOrJoinLobbyRequest() throws RemoteException {
        String command;
        System.out.println("Write the name of an existing lobby to join it or write 'CREATE' to create a new lobby");
        command = scan.nextLine();

        switch(command) {
            case "CREATE":
                System.out.println("Inserisci il nuovo nome della lobby, LEAVE per uscire dalla modalità CREATE");
                command = scan.nextLine();

                if(command.equals("LEAVE")) {
                    return "LEAVE";
                } else {
                    networkCommand.createLobby(clientContainer.getNickname(), command);
                    return "CREATE";
                }

            /**
             * lobby joining
             */
            default:
                networkCommand.joinLobby(clientContainer.getNickname(), command);
                return "JOIN";
        }
    }

    private void printChooseColorReq() {
        String command;

        do {
            System.out.println("Choose a color between: ");
            System.out.println("  - RED");
            System.out.println("  - BLUE");
            System.out.println("  - GREEN");
            System.out.println("  - YELLOW");
            command = scan.nextLine();
        } while(!(command.equals("RED") || command.equals("BLUE") || command.equals("GREEN") || command.equals("YELLOW")));

        try {
            networkCommand.setPlayerColor(clientContainer.getNickname(), ColorType.valueOf(command));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printJoinLobbyOutcome(boolean positiveOutcome, String lobbyName) {
        if(positiveOutcome) {
            System.out.println("You've joined the lobby: "+lobbyName);
            outcomeJoinLobby = true;
        } else {
            System.out.println("You failed to join the lobby");
            outcomeJoinLobby = false;
        }

        doNotify();
    }

    @Override
    public void printCreationLobbyRequestOutcome(boolean outcomePositive, String lobbyName) {
        if(outcomePositive) {
            System.out.println("You successfully created the lobby: "+ lobbyName);
            outcomeJoinLobby = true;
        } else {
            System.out.println("Creation lobby failed, "+lobbyName+" name has already been taken!");
            outcomeJoinLobby = false;
        }

        doNotify();
    }

    // una volta joinata la lobby, puoi entrare o uscire
    private void lobbyActionReq() throws RemoteException {
        String command;
        boolean flag = true;

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

    private void gameSetupProcedure() {
        doWait();
        printStarterCardReq(tempStarterCard);

        doWait();
        printSelectPersonalMission(tempMission1, tempMission2);

        doWait();
        startGamePhase();
    }

    @Override
    public void lobbyActionOutcome(boolean isReady) {
        if(isReady) {
            System.out.println("You set yourself ready!");
            outcomeJoinGame = true;
        } else {
            System.out.println("You've left the lobby!");
            outcomeJoinLobby = false;
            outcomeChooseColor = false;
            outcomeJoinGame = false;
        }

        doNotify();
    }

    @Override
    public void printChooseColorOutcome(boolean isSuccessful) {
        if(isSuccessful) {
            System.out.println("You've confirmed your color");
            outcomeChooseColor = true;
        } else {
            System.out.println("ERR. someone else has already chosen this color!!!");
            outcomeChooseColor = false;
        }

        doNotify();
    }

    @Override
    public void notifyLobbyStatusColor(String otherPlayerNickname, ColorType color) {
        System.out.println("The player: "+ otherPlayerNickname +", has chosen the color: "+ color.name());
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
        System.out.flush();
        System.out.println("Here's your starter card:");
        PrintCardClass TUIstarterCard = new PrintCardClass(starterCard);
        TUIstarterCard.printCardHorizzontal(starterCard);

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
        System.out.flush();
    }

    @Override
    public void giveStarterCard(StarterCard starterCard) {
        tempStarterCard = starterCard;
        doNotify();
    }

    @Override
    public void givePersonalMission(Mission choice1, Mission choice2) {
        tempMission1 = choice1;
        tempMission2 = choice2;
        doNotify();
    }

    private void printSelectPersonalMission(Mission choice1, Mission choice2) {
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
    public void notifyIsYourTurnInitPhase(boolean isYourTurn) {
        System.out.println(ANSI_BLUE + "Is my turn: "+ isYourTurn + "" + ANSI_RESET);
        doNotify();
    }

    private void startGamePhase() {
        String command;

        while(true) {
            System.out.println("1) if you want to see your map");
            System.out.println("2) if you want to play a card");
            System.out.println("3) if you want to see your Hand");
            System.out.println("4) if you want to see other player's map");
            System.out.println("5) if you want to see the Shop");
            System.out.println("6) if you want to see your resources");
            System.out.println("7) if you want to see your missions");
            System.out.println("8) if you want to see the scoreboard");

            command = scan.nextLine();
            if(command.equals("1")) {
                PrintMapClass.printYourMap(clientContainer.getPersonalGameMap());
            }else if(command.equals("2")) {
                PrintMapClass.printYourMap(clientContainer.getPersonalGameMap());
                PrintHandClass.printHand(clientContainer.getPersonalHand());

                System.out.println("Give me which num card in hand to play");
                int numCard = scan.nextInt();
                Card card = clientContainer.getPersonalHand().getCard((numCard-1));

                System.out.println("Type 0 if you want to play the card front face, 1 back face");
                int isReversed = scan.nextInt();
                if(isReversed == 1) {
                    card.setIsBack(true);
                } else {
                    card.setIsBack(false);
                }
                int key;
                do{
                    System.out.println("Enter the number of the space you want to put your card in");
                    key = scan.nextInt();
                }while(key<1 || key > PrintMapClass.getPublicCounter());
                int x = PrintMapClass.getFreePos().get(key)[0];
                int y = PrintMapClass.getFreePos().get(key)[1];
                try {
                    virtualGame.playerPlayCard(clientContainer.getNickname(), x, y,
                            clientContainer.getPersonalHand().getCard(numCard));
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

                /**
                 * wait the server response
                 */
                doWait();
                ShopType shopType;

                do {
                    System.out.println("Tell me from which shop you want to draw: RESOURCE or OBJECTIVE");
                    shopType = ShopType.valueOf(scan.nextLine());
                } while(!(shopType.equals(ShopType.RESOURCE) || shopType.equals(ShopType.OBJECTIVE)));

                do {
                    System.out.println("Give me which card you want to draw: 0 top deck, 1 first card, 2 second card");
                    numCard = scan.nextInt();
                } while(!(numCard == 0 || numCard == 1 || numCard == 2));

                try {
                    virtualGame.playerDraw(clientContainer.getNickname(), numCard, shopType);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }else if(command.equals("3")) {
                PrintHandClass.printHand(clientContainer.getPersonalHand());
            }else if(command.equals("4")) {
                Set<String> players = clientContainer.getOtherPlayerNames();
                System.out.println("list of other players" + players);
                String choice = "";
                do{
                    System.out.println("Enter the name of the player: ");
                    choice = scan.nextLine();
                }while(!players.contains(choice));
                PrintMapClass.printMap(clientContainer.getOthersGameMap(choice));
            }else if(command.equals("5")) {
                PrintShop.printShop(clientContainer);
            }else if(command.equals("6")) {
                printPlayerScore();
            }else if(command.equals("7")) {
                System.out.println(ANSI_BLUE + "1) Common Mission:" + ANSI_RESET);
                PrintMissionClass.printMission(clientContainer.getCommonMission1());

                System.out.println(ANSI_BLUE + "2) Common Mission:" + ANSI_RESET);
                PrintMissionClass.printMission(clientContainer.getCommonMission2());

                System.out.println(ANSI_BLUE + "Personal Mission:" + ANSI_RESET);
                PrintMissionClass.printMission(clientContainer.getPersonalMission());
            } else if(command.equals("8")) {

            }
        }
    }

    @Override
    public void outcomePlayCard(boolean isValidPlacement) {
        System.out.println(ANSI_BLUE + "The played card isValid: "+ isValidPlacement + ""+ ANSI_RESET);
        doNotify();
    }

    @Override
    public void printErrorCommandSentGameState(GameState currentGameState) {
        System.err.println("Action not valid, current phase of game: "+currentGameState.name());
    }

    @Override
    public void printIsYourTurn() {
        System.out.println(ANSI_BLUE + "Is your turn now!!!" + ANSI_RESET);
    }

    @Override
    public void printIsNotYourTurn() {
        System.out.println(ANSI_BLUE + "It's NOT your turn!!!" + ANSI_RESET);
    }

    @Override
    public void printIsYourFinalTurn() {
        System.out.println(ANSI_RED + "Is your turn now!!!" + ANSI_RESET);
    }

    @Override
    public void printWinners(ArrayList<String> winnersNickname) {
        System.out.println(ANSI_RED + "The game has ended!!! list of winners: " + ANSI_RESET);

        for(String winner : winnersNickname) {
            System.out.println(ANSI_BLUE + "  -  " + winner + "" + ANSI_RESET);
        }
    }

    @Override
    public void updateHand() {
        //do not implement!
    }

    @Override
    public void updatePlayerScoreBoard() {
        //do not implement!
    }

    private void doWait() {
        synchronized (lock) {
            try {
                lock.wait(); // Puts the current thread in wait state
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doNotify() {
        synchronized (lock) {
            lock.notify(); // Wakes up one waiting thread
        }
    }

    private void printPlayerScore() {
        HashMap<String, OtherPlayerData>  players = clientContainer.getPlayers();

        for (Map.Entry<String, OtherPlayerData> entry : players.entrySet()) {
            String nickname = entry.getKey();
            OtherPlayerData playerData = entry.getValue();

            System.out.println("  - " + nickname + ", score: " + "Scoreboard: " + playerData.getIntScoreBoardScore() + ", COLOR: " + playerData.getColor());
        }
    }
}
