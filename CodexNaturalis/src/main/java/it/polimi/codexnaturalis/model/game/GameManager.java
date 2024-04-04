package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.model.chat.ChatManager;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.scoreboard.ScoreBoard;
import it.polimi.codexnaturalis.model.shop.GeneralShop;
import it.polimi.codexnaturalis.model.shop.Shop;
import it.polimi.codexnaturalis.model.shop.card.Card;

import java.util.ArrayList;
import java.util.List;



public class GameManager implements GameInterface {
    public Mission sharedMission1;
    public Mission sharedMission2;
    private ScoreBoard scoreBoard;
    private GeneralShop resourceShop;
    private GeneralShop objectiveShop;
    private String pathToFile;
    private Player[] players;
    private Player playerTurn;
    private ChatManager chatManager;
    private boolean isFinalTurn;
    private List <Player> winners;
    private Player startingPlayer;

    @Override
    public void initializeGame() {
        initializeScoreboard();
        resourceShop = initializeShop(ShopType.Resource, "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/resourceCardsFile.json");
        objectiveShop = initializeShop(ShopType.Objective, "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/objectiveCardsFile.json");
        initializePlayer();
        //TODO, dovrei notificare i player di scegliere il colore (forse listener), attendere che chiamino setPlayerColor
        // verificare che tutti abbiano scelto e poi continuare con le inizializzazioni
        //initializePlayerColor();
        initializePlayerHand();
        initializeStarterCard();
        initializeMission();
        initializeStartingPlayer();
    }

    private void initializeScoreboard(){
        scoreBoard = new ScoreBoard(players);
    }
    private GeneralShop initializeShop(ShopType typeOfShop, String pathToFile){
        return new GeneralShop(typeOfShop, pathToFile);
    }

    private void initializePlayer(){

    }
    public boolean setPlayerColor(String nickname, String color) {
        return false;
    }
    private void initializeStarterCard(){
        Shop starterShop = new Shop(ShopType.Starter, "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/starterCardsFile.json");
    }
    private void initializePlayerHand(){

    }
    private void initializeMission(){

    }
    private void initializeStartingPlayer(){

    }

    private Player nickToPlayer(String nickname){//throw da aggiungere
        for(Player p: players)
            if(p.getNickname().equals(nickname))
                return p;
        return null;//caso player non trovato
    }

    @Override
    public void disconnectPlayer(String nickname) {

    }

    @Override
    public void reconnectPlayer(String nickname) {

    }

    @Override
    public void playerDraw(String nickname, int numShopCard, String type) {
        Player p = nickToPlayer(nickname);
        switch (type) {
            case "resourceShop":
                p.drawCard(resourceShop.drawFromShopPlayer(numShopCard));
                break;
            case "objectiveShop":
                p.drawCard(objectiveShop.drawFromShopPlayer(numShopCard));
                break;
        }
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, int numMission) {
        Player p = nickToPlayer(nickname);
        p.setPersonalMissionChoice(p.getPersonalMission(numMission));
    }

    @Override
    public void playerPlayCard(String nickname, int x, int y, int numCard) {
        Player p = nickToPlayer(nickname);
        p.placeCard(x, y, numCard);
    }

    @Override
    public void typeMessage(String receiver, String sender, String msg) {
        Player rec = nickToPlayer(receiver);
        Player send = nickToPlayer(sender);
        chatManager.writeComment(rec, send, msg);
    }

    @Override
    public void switchPlayer(String nickname) {

    }
    private boolean endGameCheckFinishedShop(){
        return false;
    }
    private boolean endGameCheckScoreBoard(){
            return scoreBoard.checkEnd20(playerTurn);

    }
    @Override
    public void endGame() {

    }

    private int executeSharedMission(Card[][] mapArray){
        return 0;
    }

    private void executePlayerMission(){

    }

    private void nextTurn(){
        for(int i=0; i<players.length; i++){
            if (playerTurn.equals(players[i])) {
                if (i != players.length-1)
                    playerTurn = players[i+1];
                else
                    playerTurn = players[0];
            }
        }
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
