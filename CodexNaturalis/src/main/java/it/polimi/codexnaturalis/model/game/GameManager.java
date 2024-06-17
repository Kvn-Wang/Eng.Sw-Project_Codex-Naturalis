package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.chat.ChatManager;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
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
    private String pathToFile;
    private Map<String, ColorType> playerInfo;
    private Player[] players;
    private Player playerTurn;
    private ChatManager chatManager;
    private boolean isFinalTurn=false;
    private List <Player> winners;
    private String scoreCardImg;
    private int playerThatHasPlayedStarterCard;
    private Observer vobs;
    private MissionSelector missionSelector;

    public GameManager(Map<String, ColorType> playerInfo, Observer observer) {
        this.playerInfo = playerInfo;
        players = new Player[playerInfo.size()];

        missionSelector = new MissionSelector();

        playerThatHasPlayedStarterCard = 0;

        vobs = observer;
        addObserver(observer);
    }

    @Override
    public void initializeGame() {
        gamePhase1();
    }

    @Override
    public void playStarterCard(String playerNick, StarterCard starterCard) {
        nickToPlayer(playerNick).placeCard(UtilCostantValue.lunghezzaMaxMappa/2,
                UtilCostantValue.lunghezzaMaxMappa/2, starterCard);

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

        initializePlayerHand();
        initializeCommonMission();
        setupPlayerContents();

        initializePlayerPersonalMission();
    }
    private void gamePhase3(){
        initializeStartingPlayer();
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
        for (Map.Entry<String, ColorType> entry : playerInfo.entrySet()) {
            String nickname = entry.getKey();
            ColorType colorPlayer = entry.getValue();
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
                        argsGenerator(starterShop.drawTopDeckCard(true))));
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initializePlayerHand(){
        for(Player p: players){
            p.addHandCard(resourceShop.drawTopDeckCard(true));
            p.addHandCard(resourceShop.drawTopDeckCard(true));
            p.addHandCard(objectiveShop.drawTopDeckCard(true));
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
        for(Player p: players){
            try {
                notifyObserverSingle(new NetworkMessage(p.getNickname(), MessageType.GAME_SETUP_INIT_HAND_COMMON_MISSION_SHOP,
                        argsGenerator(p.getHand()), argsGenerator(sharedMission1),
                        argsGenerator(sharedMission2), argsGenerator(resourceShop.getVisibleShopCard()),
                        argsGenerator(objectiveShop.getVisibleShopCard())));
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void initializePlayerPersonalMission() {
        for(Player p: players){
            try {
                notifyObserverSingle(new NetworkMessage(MessageType.GAME_SETUP_SEND_PERSONAL_MISSION,
                        p.getNickname(), argsGenerator(missionSelector.drawFromFile()), argsGenerator(missionSelector.drawFromFile())));
            } catch (PersonalizedException.InvalidRequestTypeOfNetworkMessage e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print("\nMissions being selected\n");
    }

    private void initializeStartingPlayer(){
        //Shuffle di players e poi assegno uno a playerturn
        //Fisher–Yates shuffle
        Random rnd = new Random();
        for (int i = players.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            Player a = players[j];
            players[j] = players[i];
            players[i] = a;
        }
        playerTurn = players[0];
        System.out.printf("inizia %s\n\n", players[0].getNickname());
    }

    private Player nickToPlayer(String nickname)/* throws PersonalizedException.InvalidNickname*/ {//TODO:throw exception da aggiungere
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
    public void playerDraw(String nickname, int numShopCard, String type) throws PersonalizedException.InvalidRequestTypeOfNetworkMessage {
        Player p = nickToPlayer(nickname);
        if(type.equals("RESOURCE")) {
            p.addHandCard(resourceShop.drawFromShopPlayer(numShopCard));
            System.out.println(nickname+" drew from the Resource Shop\n");
            endGameCheckFinishedShop();
            nextTurn();
        } else if(type.equals("OBJECTIVE")) {
            p.addHandCard(objectiveShop.drawFromShopPlayer(numShopCard));
            System.out.printf(nickname+" drew from the Objective Shop\n");
            endGameCheckFinishedShop();
            nextTurn();
        }
        else{
            System.out.println("wrong type shop");
            notifyObserverSingle(new NetworkMessage(nickname, MessageType.WRONG_TYPE_SHOP));
        }
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, Mission mission) {
        Player p = nickToPlayer(nickname);
        p.setPersonalMissionFinal(mission);
        System.out.printf("%s Selected his mission\n", nickname);
        int numPlayerReady = 0;
        for(Player player: players){
            if(player.getPersonalMission() != null)
                numPlayerReady++;
        }
        //controllo se tutti i player hanno selezionato personal mission
        if(numPlayerReady == players.length)
            gamePhase3();
    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, Card playedCard) {
        Player p = nickToPlayer(nickname);
        System.out.println(nickname + " ha piazzato una carta in posizione: ("+x+","+y+")");
        p.placeCard(x, y, playedCard);

        endGameCheckScoreBoard();
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
}
