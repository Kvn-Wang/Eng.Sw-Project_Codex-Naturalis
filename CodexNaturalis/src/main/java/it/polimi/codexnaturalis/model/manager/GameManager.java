package it.polimi.codexnaturalis.model.manager;

import it.polimi.codexnaturalis.model.GameInterface;
import it.polimi.codexnaturalis.model.chat.ChatManager;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.GeneralShop;
import it.polimi.codexnaturalis.model.shop.card.Card;

public class GameManager implements GameInterface {
    public Mission sharedMission1;
    public Mission sharedMission2;
    private GeneralShop resourceShop;
    private GeneralShop objectiveShop;
    private String pathToFile;
    private Player[] player;
    private Player playerTurn;
    private ChatManager chatManager;
    private boolean isFinalTurn;
    private Player winner;
    private Player startingPlayer;

    @Override
    public void initializeGame() {

    }

    private void initializeScoreboard(){

    }
    private void initializeShop(ShopType typeOfShop, String pathToFile){

    }
    private void initializePlayer(){

    }
    private void initializePlayerColor(){

    }
    private void initializeStarterCard(){

    }
    private void initializePlayerHand(){

    }
    private void initializeMission(){

    }
    private void initializeStartingPlayer(){

    }

    private Player nickToPlayer(String nickname){//throw da aggiungere
        for(Player p: player)
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
            case "objectiveShop":
                p.drawCard(objectiveShop.drawFromShopPlayer(numShopCard));
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
    public void typeMessage(String reciver, String sender, String msg) {
        Player rec = nickToPlayer(reciver);
        Player send = nickToPlayer(sender);
        chatManager.writeComment(rec, send, msg);
    }

    @Override
    public void switchPlayer(String nickname) {

    }
    private boolean endGameCheckFinishedShop(){
        return false;
    }
    private boolean endGameCheckScoreCard(){
        return false;
    }
    @Override
    public void endGame() {

    }

    private int executeSharedMission(Card[][] mapArray){
        return 0;
    }

    private void finalUpdatePlayerScore(String nickname, int value){

    }

    private void nextTurn(String nickname, int value){

    }
    private void setWinner(String nickname){

    }
}
