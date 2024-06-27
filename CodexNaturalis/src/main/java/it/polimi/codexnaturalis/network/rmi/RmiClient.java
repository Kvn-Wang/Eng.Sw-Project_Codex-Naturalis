package it.polimi.codexnaturalis.network.rmi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.GameState;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.mission.MissionAdapter;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.Hand;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.HandGsonAdapter;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.CardTypeAdapter;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualServer;
import it.polimi.codexnaturalis.network.communicationInterfaces.VirtualView;
import it.polimi.codexnaturalis.network.lobby.LobbyInfo;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.view.GenericClient;
import it.polimi.codexnaturalis.view.TypeOfUI;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RmiClient extends GenericClient implements VirtualServer {
    private final String serverName = UtilCostantValue.RMIServerName;
    private final VirtualServer server;
    private GameController personalGameController;
    private Registry registry;
    // variabile di identificativo temporanea -> inutile dopo il setting del nickname
    private String ID;
    /**
     * thread used when after ready the real main thread dies, so this executorService replace it
     */
    private ExecutorService serviceThread;
    /**
     * when the game starts, this thread will be used to send and receive updates
     */
    Gson gsonTranslator = new GsonBuilder()
            .registerTypeAdapter(Card.class, new CardTypeAdapter())
            .registerTypeAdapter(Hand.class, new HandGsonAdapter())
            .registerTypeAdapter(Mission.class, new MissionAdapter())
            .create();

    public RmiClient(TypeOfUI typeOfUI, String address) throws RemoteException, NotBoundException, InterruptedException {
        //setup communicazione bidirezionale tra rete e oggetto grafico
        super(typeOfUI);

        serviceThread = Executors.newSingleThreadExecutor();
        serviceThread = Executors.newSingleThreadExecutor();

        registry = LocateRegistry.getRegistry(address, UtilCostantValue.portRmiServer);
        this.server = (VirtualServer) registry.lookup(serverName);

        //System.out.println(getClientIpAddress(address));

        // scambio dell'oggetto per comunicare col server
        ID = UUID.randomUUID().toString();
        server.connectRMI(this, ID);

        System.out.println("Collegato al server: " + serverName);
    }

    @Override
    public void showMessage(NetworkMessage message) throws RemoteException {
        switch(message.getMessageType()) {
            // ---------  Lobby phase ----------- //
            case COM_LOBBY_STATUS_NOTIFY:
                typeOfUI.notifyLobbyStatus(message.getNickname(), message.getArgs().get(0));
                break;

            case COM_SET_PLAYER_COLOR_OUTCOME:
                boolean actionSuccessful = Boolean.parseBoolean(message.getArgs().get(0));

                if(actionSuccessful) {
                    String nickname = message.getArgs().get(1);
                    ColorType color = ColorType.valueOf(message.getArgs().get(2));

                    if(nickname.equals(clientContainer.getNickname())) {
                        clientContainer.setPersonalColor(color);
                        typeOfUI.printChooseColorOutcome(true);
                    } else {
                        typeOfUI.notifyLobbyStatusColor(nickname, color);
                    }
                } else {
                    typeOfUI.printChooseColorOutcome(false);
                }
                break;

            // ---------  SETUP ----------- //
            case GAME_SETUP_GIVE_STARTER_CARD:
                //crea un thread che fa l'operazione così che non sia il thread server a gestire la gestione della starter card
                serviceThread.submit(() -> {
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
                serviceThread.submit(() -> {
                    try {
                        //receive setup hand
                        Card resourceCard1 = gsonTranslator.fromJson(message.getArgs().get(0), Card.class);
                        Card resourceCard2 = gsonTranslator.fromJson(message.getArgs().get(1), Card.class);
                        Card ObjCard1 = gsonTranslator.fromJson(message.getArgs().get(2), Card.class);
                        Hand hand = new Hand();
                        hand.addCard(resourceCard1);
                        hand.addCard(resourceCard2);
                        hand.addCard(ObjCard1);

                        // 2 common mission
                        Mission commonMission1 = gsonTranslator.fromJson(message.getArgs().get(3), Mission.class);
                        Mission commonMission2 = gsonTranslator.fromJson(message.getArgs().get(4), Mission.class);
                        // topDeckCard + 2 visible resource card from shop
                        Card topDeckCardResource = gsonTranslator.fromJson(message.getArgs().get(5), Card.class);
                        Card visible1CardResource = gsonTranslator.fromJson(message.getArgs().get(6), Card.class);
                        Card visible2CardResource = gsonTranslator.fromJson(message.getArgs().get(7), Card.class);

                        // topDeckCard + 2 visible objective card from shop
                        Card topDeckCardObj = gsonTranslator.fromJson(message.getArgs().get(8), Card.class);
                        Card visible1CardObj = gsonTranslator.fromJson(message.getArgs().get(9), Card.class);
                        Card visible2CardObj = gsonTranslator.fromJson(message.getArgs().get(10), Card.class);

                        clientContainer.initialSetupOfResources(hand, commonMission1, commonMission2,
                                topDeckCardResource, visible1CardResource, visible2CardResource,
                                topDeckCardObj, visible1CardObj, visible2CardObj);
                    } catch (Exception e) {
                        System.err.println("Err Init resources: "+e.getMessage());
                    }
                });
                break;

            case GAME_SETUP_SEND_PERSONAL_MISSION:
                //crea un thread che fa l'operazione così che non sia il thread server a gestire il client
                serviceThread.submit(() -> {
                    try {
                        //receive personal mission
                        Mission personalMission1 = gsonTranslator.fromJson(message.getArgs().get(0), Mission.class);
                        Mission personalMission2 = gsonTranslator.fromJson(message.getArgs().get(1), Mission.class);

                        typeOfUI.givePersonalMission(personalMission1, personalMission2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;

            case GAME_SETUP_NOTIFY_TURN:
                //il thread sostituisce "ufficialmente" il thread main e sarà lui a portare avanti il gioco
                serviceThread.submit(() -> {
                    typeOfUI.notifyIsYourTurnInitPhase(Boolean.parseBoolean(message.getArgs().get(0)));
                });
                break;

            // ---------  GAME Phase ----------- //
            case DRAW_CARD_UPDATE_SHOP_CARD_POOL:
                serviceThread.submit(() -> {
                    Card cardShop = gsonTranslator.fromJson(message.getArgs().get(0), Card.class);
                    ShopType shopType = ShopType.valueOf(message.getArgs().get(1));
                    int numShopCardToBeUpdated = Integer.parseInt(message.getArgs().get(2));

                    clientContainer.updateShopCard(cardShop, shopType, numShopCardToBeUpdated);
                });
                break;

            case DRAWN_CARD_DECK:
                Card cardDrawn = gsonTranslator.fromJson(message.getArgs().get(0), Card.class);

                clientContainer.addCardToHand(cardDrawn);
                typeOfUI.updateHand();
                break;

            case PLACEMENT_CARD_OUTCOME:
                boolean isValidPlacement = Boolean.parseBoolean(message.getArgs().get(0));

                if(isValidPlacement) {
                    Card playedCard = gsonTranslator.fromJson(message.getArgs().get(1), Card.class);
                    int x = Integer.parseInt(message.getArgs().get(2));
                    int y = Integer.parseInt(message.getArgs().get(3));
                    PlayerScoreResource playerScoreResource = gsonTranslator.fromJson(message.getArgs().get(4), PlayerScoreResource.class);
                    int updatedScoreBoardValue = Integer.parseInt(message.getArgs().get(5));

                    System.out.println("Piazzamento valido");
                    clientContainer.playedCard(x, y, playedCard);
                    clientContainer.updatePersonalScore(updatedScoreBoardValue, playerScoreResource);
                    typeOfUI.outcomePlayCard(true);
                    typeOfUI.updatePlayerScoreBoard();
                } else {
                    System.out.println("Piazzamento non valido");
                    typeOfUI.outcomePlayCard(false);
                }

                break;

            case UPDATE_OTHER_PLAYER_GAME_MAP:
                String nickName = message.getArgs().get(0);
                Card playedCard = gsonTranslator.fromJson(message.getArgs().get(1), Card.class);
                int x_pos = Integer.parseInt(message.getArgs().get(2));
                int y_pos = Integer.parseInt(message.getArgs().get(3));
                int hisNewPlayerScore = Integer.parseInt(message.getArgs().get(4));

                System.out.println("the player: "+nickName+" has played a card in ("+x_pos+","+y_pos+")");

                clientContainer.updateOtherPlayerMap(nickName, x_pos, y_pos, playedCard, hisNewPlayerScore);
                typeOfUI.updatePlayerScoreBoard();
                break;

            case ERR_GAME_STATE_COMMAND:
                GameState currGameState = GameState.valueOf(message.getArgs().get(0));
                typeOfUI.printErrorCommandSentGameState(currGameState);
                break;

            case YOUR_TURN:
                typeOfUI.printIsYourTurn();
                break;

            case NOT_YOUR_TURN:
                typeOfUI.printIsNotYourTurn();
                break;

            case INCOMING_MESSAGE:
                String sender = message.getArgs().get(0);
                String msg = message.getArgs().get(1);

                typeOfUI.printPlayerMsg(sender, msg);
                break;

            case NOTIFY_FINAL_TURN:
                typeOfUI.printIsYourFinalTurn();
                break;

            case GAME_ENDED:
                ArrayList<String> winnerNicknames = gsonTranslator.fromJson(message.getArgs().get(0),
                        new TypeToken<ArrayList<String>>() {}.getType());

                typeOfUI.printWinners(winnerNicknames);

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
    public boolean setNickname(String UUID, String nickname) {
        serviceThread.submit(() -> {
            try {
                if(server.setNickname(this.ID, nickname)) {
                    clientContainer.setNickname(nickname);
                    typeOfUI.printSelectionNicknameRequestOutcome(true, nickname);
                } else {
                    typeOfUI.printSelectionNicknameRequestOutcome(false, nickname);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //è un ritorno fittizzio, non è utilizzato da nessuno
        return false;
    }

    @Override
    public ArrayList<LobbyInfo> getAvailableLobby() {
        serviceThread.submit(() -> {
            try {
                typeOfUI.giveLobbies(server.getAvailableLobby());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //return inutile
        return null;
    }

    @Override
    public ArrayList<PlayerInfo> joinLobby(String playerNickname, String lobbyName) {
        serviceThread.submit(() -> {
            try  {
                if(server.joinLobby(playerNickname, lobbyName) != null) {
                    clientContainer.setLobbyNickname(lobbyName);
                    typeOfUI.printJoinLobbyOutcome(true, lobbyName);
                } else {
                    typeOfUI.printJoinLobbyOutcome(false, lobbyName);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        return null;
    }

    @Override
    public void leaveLobby(String playerNickname) {
        serviceThread.submit(() -> {
            try {
                server.leaveLobby(playerNickname);
                typeOfUI.lobbyActionOutcome(false);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public boolean createLobby(String playerNickname, String lobbyName) {
        serviceThread.submit(() -> {
            try {
                if(server.createLobby(playerNickname, lobbyName)) {
                    clientContainer.setLobbyNickname(lobbyName);
                    typeOfUI.printCreationLobbyRequestOutcome(true, lobbyName);
                } else {
                    typeOfUI.printCreationLobbyRequestOutcome(false, lobbyName);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        //ritorno fittizzio
        return false;
    }

    @Override
    public void setPlayerColor(String nickname, ColorType colorChosen) {
        serviceThread.submit(() -> {
            try {
                server.setPlayerColor(nickname, colorChosen);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void setPlayerReady(String playerNickname) {
        serviceThread.submit(() -> {
            try {
                server.setPlayerReady(playerNickname);
                typeOfUI.lobbyActionOutcome(true);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void playStarterCard(String playerNick, StarterCard starterCard)  {
        serviceThread.submit(() -> {
            try {
                clientContainer.playedStarterCard(starterCard);
                personalGameController.playStarterCard(playerNick, starterCard);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, Mission mission) {
        serviceThread.submit(() -> {
            try {
                personalGameController.playerPersonalMissionSelect(nickname, mission);
                clientContainer.setPersonalMission(mission);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void playerDraw(String nickname, int numcard, ShopType type) throws RemoteException {
        serviceThread.submit(() -> {
            try {
                personalGameController.playerDraw(nickname, numcard, type);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, Card playedCard) throws RemoteException {
        serviceThread.submit(() -> {
            try {
                personalGameController.playerPlayCard(nickname, x, y, playedCard);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void typeMessage(String sender, String receiver, String msg) {
        serviceThread.submit(() -> {
            try {
                personalGameController.typeMessage(sender, receiver, msg);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    // not implemented
    @Override
    public void gameEnd() throws RemoteException {}
}
