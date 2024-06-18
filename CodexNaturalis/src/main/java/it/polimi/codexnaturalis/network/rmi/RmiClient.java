package it.polimi.codexnaturalis.network.rmi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.mission.MissionAdapter;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.player.HandGsonAdapter;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.CardTypeAdapter;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.GenericClient;
import it.polimi.codexnaturalis.view.TypeOfUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RmiClient extends GenericClient implements VirtualServer {
    private final String serverName = UtilCostantValue.RMIServerName;
    private final VirtualServer server;
    private GameController personalGameController;
    private Registry registry;
    // variabile di identificativo temporanea -> inutile dopo il setting del nickname
    private String ID;
    private ExecutorService executorService;
    Gson gsonTranslator = new GsonBuilder()
            .registerTypeAdapter(Card.class, new CardTypeAdapter())
            .registerTypeAdapter(Hand.class, new HandGsonAdapter())
            .registerTypeAdapter(Mission.class, new MissionAdapter())
            .create();

    public RmiClient(TypeOfUI typeOfUI) throws RemoteException, NotBoundException, InterruptedException {
        //setup communicazione bidirezionale tra rete e oggetto grafico
        super(typeOfUI);

        executorService = Executors.newSingleThreadExecutor();

        registry = LocateRegistry.getRegistry(UtilCostantValue.ipAddressSocketServer, UtilCostantValue.portRmiServer);
        this.server = (VirtualServer) registry.lookup(serverName);

        System.out.println("Collegato al server: " + serverName);

        // scambio dell'oggetto per comunicare col server
        ID = UUID.randomUUID().toString();
        server.connectRMI(this, ID);
    }

    @Override
    public void showMessage(NetworkMessage message) throws RemoteException {
        switch(message.getMessageType()) {
            case STATUS_PLAYER_CHANGE:
                break;

            case NOT_YOUR_TURN:
                break;

            case SCORE_UPDATE:
                break;

            case GAME_SETUP_GIVE_STARTER_CARD:
                //crea un thread che fa l'operazione così che non sia il thread server a gestire la gestione della starter card
                executorService.submit(() -> {
                    try {
                        Card supp = gsonTranslator.fromJson(message.getArgs().get(0), Card.class);

                        typeOfUI.giveStarterCard((StarterCard) supp);
                    } catch (Exception e) {
                        System.err.println("Err Starter card play");
                    }
                });
                break;

            case GAME_SETUP_INIT_HAND_COMMON_MISSION_SHOP:
                //crea un thread che fa l'operazione così che non sia il thread server a gestire il client
                executorService.submit(() -> {
                    try {
                        //receive setup hand
                        Hand hand = gsonTranslator.fromJson(message.getArgs().get(0), Hand.class);
                        // 2 common mission
                        Mission commonMission1 = gsonTranslator.fromJson(message.getArgs().get(1), Mission.class);
                        Mission commonMission2 = gsonTranslator.fromJson(message.getArgs().get(2), Mission.class);
                        // topDeckCard + 2 visible resource card from shop
                        Card topDeckCardResource = gsonTranslator.fromJson(message.getArgs().get(3), Card.class);
                        Card visible1CardResource = gsonTranslator.fromJson(message.getArgs().get(4), Card.class);
                        Card visible2CardResource = gsonTranslator.fromJson(message.getArgs().get(5), Card.class);

                        // topDeckCard + 2 visible objective card from shop
                        Card topDeckCardObj = gsonTranslator.fromJson(message.getArgs().get(6), Card.class);
                        Card visible1CardObj = gsonTranslator.fromJson(message.getArgs().get(7), Card.class);
                        Card visible2CardObj = gsonTranslator.fromJson(message.getArgs().get(8), Card.class);

                        clientContainer.initialSetupOfResources(hand, commonMission1, commonMission2,
                                topDeckCardResource, visible1CardResource, visible2CardResource,
                                topDeckCardObj, visible1CardObj, visible2CardObj);

                        System.out.println("finished Client setup initial resources");
                    } catch (Exception e) {
                        System.err.println("Err Init resources: "+e.getMessage());
                    }
                });
                break;

            case GAME_SETUP_SEND_PERSONAL_MISSION:
                //crea un thread che fa l'operazione così che non sia il thread server a gestire il client
                executorService.submit(() -> {
                    try {
                        //receive personal mission
                        Mission personalMission1 = gsonTranslator.fromJson(message.getArgs().get(0), Mission.class);
                        Mission personalMission2 = gsonTranslator.fromJson(message.getArgs().get(1), Mission.class);

                        typeOfUI.givePersonalMission(personalMission1, personalMission2);

                        System.out.println("finished Client setup Personal");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;

            case GAME_SETUP_NOTIFY_TURN:
                //crea un thread che fa l'operazione così che non sia il thread server a gestire il client
                executorService.submit(() -> {
                    typeOfUI.notifyIsYourTurn(Boolean.parseBoolean(message.getArgs().get(0)));
                    typeOfUI.startGamePhase();
                });
                break;

            case COM_LOBBY_STATUS_NOTIFY:
                typeOfUI.notifyLobbyStatus(message.getNickname(), message.getArgs().get(0));
                break;

            default:
                //se non è nessuno dei messaggi precedenti, vuol dire che devo mostrare il messaggio
                System.out.println(message.getArgs().get(0));
                break;
        }
    }

    @Override
    public void connectToGame(GameController gameController, ArrayList<PlayerInfo> listOtherPlayer) {
        this.personalGameController = gameController;
        joinPlayerToGame(this, listOtherPlayer);
    }

    @Override
    public void connectRMI(VirtualView client, String UUID) throws RemoteException {}

    @Override
    public boolean setNickname(String UUID, String nickname) throws RemoteException {
        if(server.setNickname(this.ID, nickname)) {
            clientContainer.setNickname(nickname);
            typeOfUI.printSelectionNicknameRequestOutcome(true, nickname);
        } else {
            typeOfUI.printSelectionNicknameRequestOutcome(false, nickname);
        }

        //è un ritorno fittizzio, non è utilizzato da nessuno
        return false;
    }

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby() throws RemoteException {
        return server.getAvailableLobby();
    }

    @Override
    public ArrayList<PlayerInfo> joinLobby(String playerNickname, String lobbyName) throws RemoteException {
        if(server.joinLobby(playerNickname, lobbyName) != null) {
            clientContainer.setLobbyNickname(lobbyName);
            typeOfUI.printJoinLobbyOutcome(true, lobbyName);
        } else {
            typeOfUI.printJoinLobbyOutcome(false, lobbyName);
        }

        return null;
    }

    @Override
    public void leaveLobby(String playerNickname) throws RemoteException {
        server.leaveLobby(playerNickname);
        typeOfUI.lobbyActionOutcome(false);
    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) throws RemoteException {
        if(server.createLobby(playerNickname, lobbyName)) {
            clientContainer.setLobbyNickname(lobbyName);
            typeOfUI.printCreationLobbyRequestOutcome(true, lobbyName);
        } else {
            typeOfUI.printCreationLobbyRequestOutcome(false, lobbyName);
        }

        //ritorno fittizzio
        return false;
    }

    @Override
    public boolean setPlayerColor(String nickname, ColorType colorChosen) throws RemoteException {
        return server.setPlayerColor(nickname, colorChosen);
    }

    @Override
    public void setPlayerReady(String playerNickname) throws RemoteException {
        server.setPlayerReady(playerNickname);
        typeOfUI.lobbyActionOutcome(true);
    }

    @Override
    public void playStarterCard(String playerNick, StarterCard starterCard) throws RemoteException {
        personalGameController.playStarterCard(playerNick, starterCard);
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, Mission mission) throws RemoteException {
        personalGameController.playerPersonalMissionSelect(nickname, mission);
    }

    @Override
    public void disconnectPlayer(String nickname) throws RemoteException {
        personalGameController.disconnectPlayer(nickname);
    }

    @Override
    public void reconnectPlayer(String nickname) throws RemoteException {
        personalGameController.reconnectPlayer(nickname);
    }

    @Override
    public void playerDraw(String nickname, int numcard, ShopType type) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage, RemoteException {
        personalGameController.playerDraw(nickname, numcard, type);
    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, Card playedCard) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException, RemoteException {
        personalGameController.playerPlayCard(nickname, x, y, playedCard);
    }

    @Override
    public void typeMessage(String receiver, String sender, String msg) throws RemoteException {
        personalGameController.typeMessage(receiver, sender, msg);
    }

    @Override
    public void switchPlayer(String reqPlayer, String target) throws RemoteException {
        personalGameController.switchPlayer(reqPlayer, target);
    }

    @Override
    public void endGame() throws RemoteException {
        personalGameController.endGame();
    }

    private void runAsync(Runnable run) {
        Future<?> future = executorService.submit(() -> {
            try {
                // Simula un'operazione lunga
                Thread.sleep(2000);
                String result = "Result from RMI";
                System.out.println("ciao");
            } catch (Exception e) {
                System.err.println("err");
            }
        });

        while (!future.isDone()) {
            try {
                System.out.println("Loading...");
                Thread.sleep(1000); // Simula l'input dell'utente ogni secondo
            } catch (Exception e) {
                System.err.println("err");
            }
        }
    }
}
