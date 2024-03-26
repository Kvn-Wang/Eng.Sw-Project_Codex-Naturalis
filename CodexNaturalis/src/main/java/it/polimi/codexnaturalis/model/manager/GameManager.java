package it.polimi.codexnaturalis.model.manager;

import it.polimi.codexnaturalis.model.GameInterface;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.GeneralShop;

public class GameManager implements GameInterface {
    public Mission sharedMission1;
    public Mission sharedMission2;
    private GeneralShop resourceShop;
    private GeneralShop objectiveShop;
    private String pathToFile;
    private Player[] player;
    private Player playerTurn;
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
    @Override
    public void disconnectPlayer(String nickname) {

    }

    @Override
    public void reconnectPlayer(String nickname) {

    }

    @Override
    public void playerDraw(String nickname, int Numcard, String type) {

    }

    @Override
    public void playerPersonalMissionSelect(String nickname, int numMission) {
        for(Player p: player){
            if(p.getNickname().equals(nickname))
                p.setSelectedPersonalMission(p.getPersonalMission(numMission));
        }
    }

    @Override
    public void playerPlayCard(String nickname, int numCard) {

    }

    @Override
    public void typeMessage(Player reciver, Player sender, String msg) {

    }

    @Override
    public void switchPlayer(String nickname) {

    }
    private boolean endGameCheckFinishedShop(){

    }
    private boolean endGameCheckScoreCard(){

    }
    @Override
    public void endGame() {

    }
}
