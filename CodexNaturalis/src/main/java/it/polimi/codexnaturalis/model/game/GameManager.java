package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.mission.MissionSelector;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.GeneralShop;
import it.polimi.codexnaturalis.model.shop.Shop;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.utils.observer.Observable;
import it.polimi.codexnaturalis.utils.observer.Observer;

import java.rmi.RemoteException;
import java.util.*;


/**
 * The type Game manager.
 */
public class GameManager extends Observable implements GameController {
    private Mission sharedMission1;
    private Mission sharedMission2;
    private GeneralShop resourceShop;
    private GeneralShop objectiveShop;
    private ArrayList<PlayerInfo> networkPlayer;
    private Player[] players;
    private ArrayList<Player> winners;
    private int playerThatHasPlayedStarterCard;
    private int playerThatHasPlayedPersonalMission;
    private Observer vobs;
    private MissionSelector missionSelector;
    /**
     * The Error during playing phase.
     * Variable that I need to contact virtual game that reached the catch inside playCard
     */
    public boolean errorDuringPlayingPhase;

    /**
     * Instantiates a new Game manager.
     *
     * @param playerInfo the player info
     * @param observer   the observer
     */
    public GameManager(ArrayList<PlayerInfo> playerInfo, Observer observer) {
        networkPlayer = playerInfo;
        players = new Player[playerInfo.size()];

        missionSelector = new MissionSelector();

        playerThatHasPlayedStarterCard = 0;
        playerThatHasPlayedPersonalMission = 0;

        vobs = observer;
        addObserver(observer);
    }

    @Override
    public void initializeGame() {
        gamePhase1();
    }

    @Override
    public void playStarterCard(String playerNick, StarterCard starterCard) {
        try {
            nickToPlayer(playerNick).placeCard(UtilCostantValue.lunghezzaMaxMappa/2,
                    UtilCostantValue.lunghezzaMaxMappa/2, starterCard);
        } catch (PersonalizedException.InvalidPlaceCardRequirementException |
                 PersonalizedException.InvalidPlacementException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Player: "+playerNick+" ha giocato la sua starter card con isBack: "+starterCard.getIsBack());
        /**
         * notify the other players that the map of the player that has played card has changed
         */
        for (Player otherPlayer : players) {
            if (!otherPlayer.getNickname().equals(playerNick))
                notifyObserverSingle(new NetworkMessage(otherPlayer.getNickname(), MessageType.UPDATE_OTHER_PLAYER_GAME_MAP,
                        playerNick, argsGenerator(starterCard), String.valueOf(UtilCostantValue.lunghezzaMaxMappa/2),
                        String.valueOf(UtilCostantValue.lunghezzaMaxMappa/2),
                        String.valueOf(0)));
        }

        playerThatHasPlayedStarterCard++;
        if(playerThatHasPlayedStarterCard == players.length) {
            gamePhase2();
        }
    }

    private void gamePhase1(){
        initializePlayer();
        initializeStarterCard();
    }

    private void gamePhase2(){
        resourceShop = initializeShop(ShopType.RESOURCE);
        objectiveShop = initializeShop(ShopType.OBJECTIVE);

        initializeCommonMission();
        setupPlayerContents();

        initializePlayerPersonalMission();
    }

    private GeneralShop initializeShop(ShopType typeOfShop){
        return new GeneralShop(typeOfShop, vobs);
    }

    //inizialize nickname and color
    private void initializePlayer(){
        int i = 0;
        for (PlayerInfo playerInfo : networkPlayer) {
            String nickname = playerInfo.getNickname();
            ColorType colorPlayer = playerInfo.getColorChosen();
            players[i] = new Player(nickname, colorPlayer, vobs);
            i++;
        }
    }

    private void initializeStarterCard(){
        Shop starterShop = new Shop(ShopType.STARTER, vobs);

        System.out.println("Starter cards being placed for " + players.length + " players");
        for(Player p: players) {
            //manda la starterCard al playerSpecifico
            notifyObserverSingle(new NetworkMessage(p.getNickname(), MessageType.GAME_SETUP_GIVE_STARTER_CARD,
                    argsGenerator(starterShop.drawTopDeckCard())));
        }
    }

    private void initializeCommonMission() {
        sharedMission1 = missionSelector.drawFromFile();
        sharedMission2 = missionSelector.drawFromFile();
    }

    /**
     * sends to all player, all their necessary resources for their setup, hand, common missions and all visible shop cards
     */
    private void setupPlayerContents(){
        System.out.println("Init player resources.");

        setupPlayerHand();

        for(Player p: players){
            try {
                notifyObserverSingle(new NetworkMessage(p.getNickname(), MessageType.GAME_SETUP_INIT_HAND_COMMON_MISSION_SHOP,
                        argsGenerator(p.getStartingHand()[0]), argsGenerator(p.getStartingHand()[1]),
                        argsGenerator(p.getStartingHand()[2]), argsGenerator(sharedMission1), argsGenerator(sharedMission2),
                        argsGenerator(resourceShop.getTopDeckCard()),
                        argsGenerator(resourceShop.getVisibleCard1()), argsGenerator(resourceShop.getVisibleCard2()),
                        argsGenerator(objectiveShop.getTopDeckCard()),
                        argsGenerator(objectiveShop.getVisibleCard1()), argsGenerator(objectiveShop.getVisibleCard2())));
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void setupPlayerHand() {
        for(Player p: players) {
            Card[] startingHand = new Card[3];

            startingHand[0] = resourceShop.drawTopDeckCard();
            startingHand[1] = resourceShop.drawTopDeckCard();
            startingHand[2] = objectiveShop.drawTopDeckCard();

            p.setStartingHand(startingHand);
        }
    }

    private void initializePlayerPersonalMission() {
        for(Player p: players){
            try {
                notifyObserverSingle(new NetworkMessage(p.getNickname(), MessageType.GAME_SETUP_SEND_PERSONAL_MISSION,
                        argsGenerator(missionSelector.drawFromFile()), argsGenerator(missionSelector.drawFromFile())));
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        System.out.print("\nMissions being selected\n");
    }

    private Player nickToPlayer(String nickname) {
        for (Player p : players) {
            if (p.getNickname().equals(nickname))
                return p;
        }

        return null;
    }

    @Override
    public void playerDraw(String nickname, int numcard, ShopType type) {
        Player p = nickToPlayer(nickname);
        Card drawnCard = null;

        if(type == ShopType.RESOURCE) {
            drawnCard = resourceShop.drawFromShopPlayer(numcard);
            //p.addHandCard(resourceShop.drawFromShopPlayer(numShopCard));
            System.out.println(nickname+" drew from the Resource Shop");
            endGameCheckFinishedShop();
        } else if(type == ShopType.OBJECTIVE) {
            drawnCard = objectiveShop.drawFromShopPlayer(numcard);
            //p.addHandCard(objectiveShop.drawFromShopPlayer(numShopCard));
            System.out.println(nickname+" drew from the Objective Shop");
            endGameCheckFinishedShop();
        }

        notifyObserverSingle(new NetworkMessage(nickname, MessageType.DRAWN_CARD_DECK, argsGenerator(drawnCard)));
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, Mission mission) {
        Player p = nickToPlayer(nickname);
        p.setPersonalMissionFinal(mission);

        System.out.println(nickname+ " Selected his mission");

        playerThatHasPlayedPersonalMission++;
        //controllo se tutti i player hanno selezionato personal mission
        if(playerThatHasPlayedPersonalMission == players.length){
            System.out.println("Init phase finished, init game phase");

            /**
             * since the starting player is the first one in the list, notify him that it's his turn
             */
            notifyObserverSingle(new NetworkMessage(players[0].getNickname(),  MessageType.GAME_SETUP_NOTIFY_TURN,
                    String.valueOf(true)));
            for(int i = 1; i < players.length; i++) {
                notifyObserverSingle(new NetworkMessage(players[i].getNickname(),  MessageType.GAME_SETUP_NOTIFY_TURN,
                        String.valueOf(false)));
            }
        }

    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, Card playedCard) {
        Player p = nickToPlayer(nickname);
        errorDuringPlayingPhase = false;

        try {
            p.placeCard(x, y, playedCard);
            /**
             * notify the player that he succesfully placed the card
             */
            notifyObserverSingle(new NetworkMessage(nickname, MessageType.PLACEMENT_CARD_OUTCOME,
                    String.valueOf(true), argsGenerator(playedCard), String.valueOf(x), String.valueOf(y),
                    argsGenerator(p.getScoreResource()), String.valueOf(p.getPersonalScoreBoardScore())));

            /**
             * notify the other players that the map of the player that has played card has changed
             */
            for (Player otherPlayer : players) {
                if (!otherPlayer.getNickname().equals(nickname))
                    notifyObserverSingle(new NetworkMessage(otherPlayer.getNickname(), MessageType.UPDATE_OTHER_PLAYER_GAME_MAP,
                            nickname, argsGenerator(playedCard), String.valueOf(x), String.valueOf(y),
                            String.valueOf(p.getPersonalScoreBoardScore())));
            }

            System.out.println("The card has been placed");

            endGameCheckScoreBoard(nickToPlayer(nickname));
        } catch(PersonalizedException.InvalidPlaceCardRequirementException |
                 PersonalizedException.InvalidPlacementException e) {
            errorDuringPlayingPhase = true;
            System.out.println("Error placement Card");

            notifyObserverSingle(new NetworkMessage(nickname, MessageType.PLACEMENT_CARD_OUTCOME,
                    String.valueOf(false)));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void typeMessage(String sender, String receiver, String msg) {
        System.out.println(sender);
        System.out.println(receiver);
        System.out.println(msg);

        if(receiver.equals("EVERYONE")) {
            /**
             * notify the other player
             */
            for (Player otherPlayer : players) {
                if (!otherPlayer.getNickname().equals(sender))
                    notifyObserverSingle(new NetworkMessage(otherPlayer.getNickname(), MessageType.INCOMING_MESSAGE,
                            sender, msg));
            }
        } else if(!sender.equals(receiver)){
            for(Player player : players) {
                if(receiver.equals(player.getNickname())) {
                    notifyObserverSingle(new NetworkMessage(player.getNickname(), MessageType.INCOMING_MESSAGE,
                            sender, msg));
                }
            }
        }
    }

    @Override
    public void gameEnd() throws RemoteException {
        setWinner();
    }

    private void startFinalTurn() {
        notifyObserverSingle(new NetworkMessage(MessageType.NOTIFY_FINAL_TURN));
    }
    
    private void endGameCheckFinishedShop(){
        if(objectiveShop.checkEmptyShop() && resourceShop.checkEmptyShop())
            startFinalTurn();
    }

    private void endGameCheckScoreBoard(Player playingPlayer){
        if(playingPlayer.getPersonalScore() >= 20)
            startFinalTurn();
    }

    private void executeSharedMission(){
        for(Player p: players){
            p.addMissionScore(sharedMission1.ruleAlgorithmCheck(p) + sharedMission2.ruleAlgorithmCheck(p));
        }
    }

    private void executePlayerMission(){
        for(Player p: players){
            p.executePersonalMission();
        }
    }

    private void setWinner(){
        int maxPersonalScore = 0;
        winners = new ArrayList<>();

        /**
         * search for the highest score achieved by anyone
         */
        for(Player p: players){
            if(p.getPersonalScore()>maxPersonalScore){
                maxPersonalScore=p.getPersonalScore();
            }
        }
        /**
         * search who got it and add it to the list of winners
         */
        for(Player p: players){
            if(p.getPersonalScore()==maxPersonalScore){
                winners.add(p);
            }
        }

        /**
         * in case of multiple winners -> apply the special rules
         */
        int maxPersonalObjectiveMissionScore = 0;
        if(winners.size() != 1){
            for(Player p: winners){
                if(p.getPersonalMissionTotalScore() > maxPersonalObjectiveMissionScore){
                    maxPersonalObjectiveMissionScore = p.getPersonalMissionTotalScore();
                }
            }
            for(Player p: winners) {
                if (p.getPersonalMissionTotalScore() < maxPersonalScore) {
                    winners.remove(p);
                }
            }
        }

        /**
         * send to everyone the list of the winners
         */
        ArrayList<String> winnerNicknames = new ArrayList<>();
        for(Player player : winners) {
            winnerNicknames.add(player.getNickname());

            System.out.println("Ha vinto: " + player.getNickname());
        }

        notifyObserverSingle(new NetworkMessage(MessageType.GAME_ENDED, argsGenerator(winnerNicknames)));
    }
}
