package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.chat.ChatManager;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.GameState;
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

import java.util.*;


public class GameManager extends Observable implements GameController {
    private Mission sharedMission1;
    private Mission sharedMission2;
    private GeneralShop resourceShop;
    private GeneralShop objectiveShop;
    private ArrayList<PlayerInfo> networkPlayer;
    private Player[] players;
    private Player playerTurn;
    private ChatManager chatManager;
    private boolean isFinalTurn = false;
    private List <Player> winners;
    private String scoreCardImg;
    private int playerThatHasPlayedStarterCard;
    private int playerThatHasPlayedPersonalMission;
    private Observer vobs;
    private MissionSelector missionSelector;
    GameState gameState;

    public GameManager(ArrayList<PlayerInfo> playerInfo, Observer observer, GameState gameState) {
        networkPlayer = playerInfo;
        players = new Player[playerInfo.size()];

        this.gameState = gameState;

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

        playerThatHasPlayedStarterCard++;
        if(playerThatHasPlayedStarterCard == players.length) {
            gamePhase2();
        }
    }

    private void gamePhase1(){
        initializeScoreboard();
        initializePlayer();
        initializeStarterCard();
    }

    private void gamePhase2(){
        resourceShop = initializeShop(ShopType.RESOURCE);
        objectiveShop = initializeShop(ShopType.OBJECTIVE);

        //initializePlayerHand();
        initializeCommonMission();
        setupPlayerContents();

        initializePlayerPersonalMission();
    }

    private void initializeScoreboard(){
        scoreCardImg = UtilCostantValue.pathToScoreCardImg;
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
            try {
                notifyObserverSingle(new NetworkMessage(p.getNickname(), MessageType.GAME_SETUP_GIVE_STARTER_CARD,
                        argsGenerator(starterShop.drawTopDeckCard())));
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*private void initializePlayerHand(){
        for(Player p: players){
            p.addHandCard(resourceShop.drawTopDeckCard());
            p.addHandCard(resourceShop.drawTopDeckCard());
            p.addHandCard(objectiveShop.drawTopDeckCard());
        }
    }*/

    private void initializeCommonMission() {
        sharedMission1 = missionSelector.drawFromFile();
        sharedMission2 = missionSelector.drawFromFile();
    }

    /**
     * sends to all player, all their necessary resources for their setup, hand, common missions and all visible shop cards
     */
    private void setupPlayerContents(){
        System.out.println("Init player resources.");
        for(Player p: players){
            try {
                notifyObserverSingle(new NetworkMessage(p.getNickname(), MessageType.GAME_SETUP_INIT_HAND_COMMON_MISSION_SHOP,
                        argsGenerator(resourceShop.drawTopDeckCard()), argsGenerator(resourceShop.drawTopDeckCard()),
                        argsGenerator(objectiveShop.drawTopDeckCard()), argsGenerator(sharedMission1), argsGenerator(sharedMission2),
                        argsGenerator(resourceShop.getTopDeckCard()),
                        argsGenerator(resourceShop.getVisibleCard1()), argsGenerator(resourceShop.getVisibleCard2()),
                        argsGenerator(objectiveShop.getTopDeckCard()),
                        argsGenerator(objectiveShop.getVisibleCard1()), argsGenerator(objectiveShop.getVisibleCard2())));
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
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

    protected Player nickToPlayer(String nickname)/* throws PersonalizedException.InvalidNickname*/ {//TODO:throw exception da aggiungere
            for (Player p : players) {
                if (p.getNickname().equals(nickname))
                    return p;
            }
        //throw new PersonalizedException.InvalidNickname;
        return null;
    }

    @Override
    public void disconnectPlayer(String nickname) {
        Player dcPlayer=nickToPlayer(nickname);
        dcPlayer.setStatus(false);
        if (dcPlayer.equals(playerTurn))
            nextTurn();
    }

    @Override
    public void reconnectPlayer(String nickname) {
        nickToPlayer(nickname).setStatus(true);
    }

    @Override
    public void playerDraw(String nickname, int numcard, ShopType type) {
        Player p = nickToPlayer(nickname);
        Card drawnCard = null;

        if(type == ShopType.RESOURCE) {
            drawnCard = resourceShop.drawFromShopPlayer(numcard);
            //p.addHandCard(resourceShop.drawFromShopPlayer(numShopCard));
            System.out.println(nickname+" drew from the Resource Shop\n");
            endGameCheckFinishedShop();
        } else if(type == ShopType.OBJECTIVE) {
            drawnCard = objectiveShop.drawFromShopPlayer(numcard);
            //p.addHandCard(objectiveShop.drawFromShopPlayer(numShopCard));
            System.out.printf(nickname+" drew from the Objective Shop\n");
            endGameCheckFinishedShop();
        }

        try {
            notifyObserverSingle(new NetworkMessage(nickname, MessageType.DRAWN_CARD_DECK, argsGenerator(drawnCard)));
        } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
            throw new RuntimeException(e);
        }
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
            try {
                notifyObserverSingle(new NetworkMessage(players[0].getNickname(),  MessageType.GAME_SETUP_NOTIFY_TURN,
                        String.valueOf(true)));
                for(int i = 1; i < players.length; i++) {
                    notifyObserverSingle(new NetworkMessage(players[i].getNickname(),  MessageType.GAME_SETUP_NOTIFY_TURN,
                            String.valueOf(false)));
                }
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, Card playedCard) {
        Player p = nickToPlayer(nickname);
        System.out.println(nickname + " ha piazzato una carta in posizione: ("+x+","+y+")");

        try {
            p.placeCard(x, y, playedCard);
            /**
             * notify the player that he succesfully placed the card
             */
            notifyObserverSingle(new NetworkMessage(nickname, MessageType.PLACEMENT_CARD_OUTCOME,
                    String.valueOf(true), argsGenerator(playedCard), argsGenerator(p.getScoreResource()),
                    String.valueOf(p.getPersonalScoreBoardScore())));

            /**
             * notify the other players that the map of the player that has played card has changed
             */
            for (Player otherPlayer : players) {
                if (!otherPlayer.getNickname().equals(nickname))
                    notifyObserverSingle(new NetworkMessage(otherPlayer.getNickname(), MessageType.UPDATE_OTHER_PLAYER_GAME_MAP,
                            nickname, argsGenerator(playedCard), String.valueOf(x), String.valueOf(y)));
            }

            gameState = GameState.DRAW_PHASE;

            endGameCheckScoreBoard();
        } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
            throw new RuntimeException(e);
        } catch(PersonalizedException.InvalidPlaceCardRequirementException |
                 PersonalizedException.InvalidPlacementException e) {
            try {
                notifyObserverSingle(new NetworkMessage(nickname, MessageType.PLACEMENT_CARD_OUTCOME,
                        String.valueOf(false)));
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void typeMessage(String recipient, String sender, String msg) {
        if(recipient.equals("everyone"))
            chatManager.writeComment(sender, msg);
        else
            chatManager.writeComment(recipient, sender, msg);
    }

    @Override
    public void switchPlayer(String reqPlayer, String target) {
        nickToPlayer(reqPlayer).switchPlayerView(nickToPlayer(target));
    }

    @Override
    public void endGame() {
        isFinalTurn = true;
    }
    
    private void endGameCheckFinishedShop(){
        if(objectiveShop.checkEmptyShop() && resourceShop.checkEmptyShop())
            isFinalTurn = true;
    }

    private void endGameCheckScoreBoard(){
        if(playerTurn.getPersonalScore() >= 20)
            isFinalTurn = true;
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

    private void nextTurn(){
        /*do{
            boolean playerfound=false;
            int i=0;
            while(!playerfound && i<players.length) {
                if (playerTurn.equals(players[i])) {
                    if (i != players.length - 1) {
                        playerTurn = players[i + 1];
                        playerfound=true;
                        System.out.printf("per ora %s\n", playerTurn.getNickname());
                    }
                    else {
                        playerTurn = players[0];
                        playerfound=true;
                        System.out.printf("per ora %s\n", playerTurn.getNickname());
                        if(isFinalTurn){
                            if(remainingRounds==0)
                                setWinner();
                            else
                                remainingRounds--;
                        }
                    }
                }
                i++;
            }
        }while(!playerTurn.isPlayerAlive());
        System.out.printf("é il turno di %s\n", playerTurn.getNickname());*/
    }

    private void setWinner(){
        int max = 0;
        for(Player p: players){
            if(p.getPersonalScore()>max){
                max=p.getPersonalScore();
            }
        }
        for(Player p: players){
            if(p.getPersonalScore()==max){
                winners.add(p);
            }
        }
        max=0;
        if(winners.size()==1) {}
        else{
            for(Player p: winners){
                if(p.getPersonalMissionTotalScore()>max){
                    max=p.getPersonalMissionTotalScore();
                }
            }
            for(Player p: winners) {
                if (p.getPersonalMissionTotalScore() < max) {
                    winners.remove(p);
                }
            }
            if(winners.size()==1) {}
            else
                tieHandler(winners);
        }
    }
    private void tieHandler(List<Player> winningPlayers){
        System.out.printf("__Winners__%n%n");
        for(Player p: winningPlayers){
            System.out.printf("-%s%n", p.getNickname());
        }
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public boolean getIsFinalTurn() {
        return isFinalTurn;
    }
}
