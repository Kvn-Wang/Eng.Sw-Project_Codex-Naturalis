package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.chat.ChatManager;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.mission.MissionSelector;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.GeneralShop;
import it.polimi.codexnaturalis.model.shop.Shop;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.*;


public class GameManager implements GameController {
    private Mission sharedMission1;
    private Mission sharedMission2;
    private GeneralShop resourceShop;
    private GeneralShop objectiveShop;
    private MissionSelector missionSelector;
    private String pathToFile;
    private String[] nicknamelist;//TODO: pensare a come fare per eliminare nicknameList e nicknameNumber per ridondaza con players
    private int nicknameNumber;
    private Player[] players;
    private Player playerTurn;
    private ChatManager chatManager;
    private boolean isFinalTurn;
    private List <Player> winners;
    private Player startingPlayer;
    private String scoreCardImg;

    @Override
    public void initializeGame() {
        initializeScoreboard();
        resourceShop = initializeShop(ShopType.RESOURCE);
        objectiveShop = initializeShop(ShopType.OBJECTIVE);
        initializePlayer(nicknamelist, nicknameNumber);
        initializeStarterCard();
        //TODO, dovrei notificare i player di scegliere il colore (forse listener), attendere che chiamino setPlayerColor
        // verificare che tutti abbiano scelto e poi continuare con le inizializzazioni
        initializePlayerColor();
        initializePlayerHand();
        initializeMission();
        initializeStartingPlayer();

        //TODO manca logica per selezionare lato starter card
    }

    private void initializeScoreboard(){
        scoreCardImg = UtilCostantValue.pathToScoreCardImg;
    }

    private GeneralShop initializeShop(ShopType typeOfShop){
        return new GeneralShop(typeOfShop);
    }

    private void initializePlayer(String[] playerList, int playerNumber){
        for(int i=0; i<playerNumber; i++){
            players[i]=new Player(playerList[i]);
        }
    }

    public void initializePlayerColor() throws InterruptedException {
        //mancano observer/listener
        for(Player p: players){
            while(p.getPawnColor()==null){
            }
        }
        //TODO, come implemento la logica di scelta del colore? idea: con questa funzione
        // mando ai player la domanda di inserire un colore, la lobby man mano che riceve le
        // rispose chiama setPlayerColor, e appena arriva a count 4 risposte positive (max)
        // chiama la prossima inizialize -> problema: esponiamo un inizialize all'esterno
    }

    @Override
    public boolean setPlayerColor(String nickname, String color) {
        boolean colorAlreadyChosen=false;

        for(Player p: players){
            if(p.getPawnColor().equals(color))
                colorAlreadyChosen=true;
        }
        if(colorAlreadyChosen)
            return false;
        else{
            if(color.equals(ColorType.RED)) {
                nickToPlayer(nickname).setPawnImg(UtilCostantValue.pathToRedPawnImg);
                nickToPlayer(nickname).setPawnColor(color);
            } else if(color.equals(ColorType.YELLOW)) {
                nickToPlayer(nickname).setPawnImg(UtilCostantValue.pathToYellowPawnImg);
                nickToPlayer(nickname).setPawnColor(color);
            } else if(color.equals(ColorType.GREEN)) {
                nickToPlayer(nickname).setPawnImg(UtilCostantValue.pathToGreenPawnImg);
                nickToPlayer(nickname).setPawnColor(color);
            } else if(color.equals(ColorType.BLUE)) {
                nickToPlayer(nickname).setPawnImg(UtilCostantValue.pathToBluePawnImg);
                nickToPlayer(nickname).setPawnColor(color);
            } else {
                System.err.println("Errore: colore richiesto inesistente!");
            }

            return true;
        }
    }

    private void initializeStarterCard(){
        Shop starterShop = new Shop(ShopType.STARTER);
        for(Player p: players){
            p.addHandCard(starterShop.drawTopDeckCard());
            p.setStarterCard();
        }
    }

    private void initializePlayerHand(){
        for(Player p: players){
            p.addHandCard(resourceShop.drawTopDeckCard());
            p.addHandCard(resourceShop.drawTopDeckCard());
            p.addHandCard(objectiveShop.drawTopDeckCard());
        }
    }

    private void initializeMission(){
        missionSelector = new MissionSelector();
        sharedMission1 = missionSelector.drawFromFile();
        sharedMission2 = missionSelector.drawFromFile();
        for(Player p: players){
            p.setPersonalMissions(missionSelector.drawFromFile(), missionSelector.drawFromFile());
        }
    }

    private void initializeStartingPlayer(){
        //TODO: shuffle di players e poi assegno uno a playerturn
    }

    private Player nickToPlayer(String nickname){//TODO:throw exception da aggiungere
        for(Player p: players)
            if(p.getNickname().equals(nickname))
                return p;
        return null;//caso player non trovato
    }

    @Override
    public void disconnectPlayer(String nickname) {
        nickToPlayer(nickname).setStatus(true);
    }

    @Override
    public void reconnectPlayer(String nickname) {
        nickToPlayer(nickname).setStatus(false);
    }

    @Override
    public void playerDraw(String nickname, int numShopCard, String type) {
        Player p = nickToPlayer(nickname);
        if(type.equals(ShopType.RESOURCE)) {
            p.addHandCard(resourceShop.drawFromShopPlayer(numShopCard));
        } else if(type.equals(ShopType.OBJECTIVE)) {
            p.addHandCard(objectiveShop.drawFromShopPlayer(numShopCard));
        }
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, int numMission) {
        Player p = nickToPlayer(nickname);
        p.setPersonalMissionChoice(p.getPersonalMission(numMission));
    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException {
        Player p = nickToPlayer(nickname);
        try {
            p.placeCard(x, y, numCard, isCardBack);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw e; // Propagate the caught exception directly
        }
    }

    @Override
    public void typeMessage(String receiver, String sender, String msg) {
        Player rec = nickToPlayer(receiver);
        Player send = nickToPlayer(sender);
        chatManager.writeComment(rec, send, msg);
    }

    @Override
    public void switchPlayer(String reqPlayer, String target) {
        nickToPlayer(reqPlayer).switchPlayerView(nickToPlayer(target));
    }

    private boolean endGameCheckFinishedShop(){
        return false; //TODO: mancano funzioni per controllare presenza di topdeckcard e le carte nello shop senza pescarle
    }

    private boolean endGameCheckScoreBoard(){
        return playerTurn.getPersonalScore() >= 20;
    }
    @Override
    public void endGame() {

    }

    private void executeSharedMission(){
        int points=0;
        for(Player p: players){
            points=0;
            points = sharedMission1.ruleAlgorithmCheck(p) + sharedMission2.ruleAlgorithmCheck(p);
            p.addMissionScore(points);
        }
    }

    private void executePlayerMission(){
        for(Player p: players){
            p.executePersonalMission();
        }
    }

    private void nextTurn(){
        do{
            for(int i = 0; i < players.length; i++) {
                if (playerTurn.equals(players[i])) {
                    if (i != players.length - 1)
                        playerTurn = players[i + 1];
                    else
                        playerTurn = players[0];
                }
            }
        }while(!playerTurn.isPlayerAlive());
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
}
