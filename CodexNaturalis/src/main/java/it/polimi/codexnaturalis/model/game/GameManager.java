package it.polimi.codexnaturalis.model.game;

import it.polimi.codexnaturalis.controller.GameController;
import it.polimi.codexnaturalis.model.chat.ChatManager;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.MessageType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.mission.MissionSelector;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.GeneralShop;
import it.polimi.codexnaturalis.model.shop.Shop;
import it.polimi.codexnaturalis.network.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.utils.observer.Observable;

import java.util.*;


public class GameManager extends Observable implements GameController {
    private Mission sharedMission1;
    private Mission sharedMission2;
    private GeneralShop resourceShop;
    private GeneralShop objectiveShop;
    private String pathToFile;
    private Map<String, ColorType> playerInfo = new HashMap<>();
    private Player[] players;
    private Player playerTurn;
    private ChatManager chatManager;
    private boolean isFinalTurn=false;
    private List <Player> winners;
    private String scoreCardImg;
    private int starterCards;
    private int remainingRounds=1;

    public GameManager(Map<String, ColorType> playerInfo) {
        this.playerInfo = playerInfo;
        players = new Player[playerInfo.size()];
    }

    @Override
    public void initializeGame() {
        //initializeGame ora é diviso in 3 phases
        gamePhase1();
    }
    private void gamePhase1(){
        initializeScoreboard();
        resourceShop = initializeShop(ShopType.RESOURCE);
        objectiveShop = initializeShop(ShopType.OBJECTIVE);
        initializePlayer();
        initializeStarterCard();
    }

    private void gamePhase2(){
        initializePlayerHand();
        initializeMission();
    }
    private void gamePhase3(){
        initializeStartingPlayer();
    }

    private void initializeScoreboard(){
        scoreCardImg = UtilCostantValue.pathToScoreCardImg;
    }

    private GeneralShop initializeShop(ShopType typeOfShop){
        return new GeneralShop(typeOfShop);
    }

    //inizialize nickname and color
    private void initializePlayer(){
        int i = 0;
        for (Map.Entry<String, ColorType> entry : playerInfo.entrySet()) {
            String nickname = entry.getKey();
            ColorType colorPlayer = entry.getValue();
            players[i] = new Player(nickname, colorPlayer);
            i++;
        }
    }

    /*public void initializePlayerColor(){
        for(Player elem : players) {
            elem.setPawnColor();
        }
    }*/

/*    @Override
    public void setPlayerColor(String nickname, String color) {
        boolean colorAlreadyChosen=false;
        int chosenColorNum = 0;

        for(Player p: players){
            if(p.getPawnColor().equals(color))
                colorAlreadyChosen=true;
            if(p.getPawnColor()!=null)
                chosenColorNum++;
        }
        if(colorAlreadyChosen)
            //TODO: specifica observer
            notifyObserver(new NetworkMessage(nickname, MessageType.COLOR_ALREADY_CHOSEN));
        else{
            if(color.equals(ColorType.RED)) {
                nickToPlayer(nickname).setPawnImg(UtilCostantValue.pathToRedPawnImg);
                nickToPlayer(nickname).setPawnColor(ColorType.RED);
            } else if(color.equals(ColorType.YELLOW)) {
                nickToPlayer(nickname).setPawnImg(UtilCostantValue.pathToYellowPawnImg);
                nickToPlayer(nickname).setPawnColor(ColorType.YELLOW);
            } else if(color.equals(ColorType.GREEN)) {
                nickToPlayer(nickname).setPawnImg(UtilCostantValue.pathToGreenPawnImg);
                nickToPlayer(nickname).setPawnColor(ColorType.GREEN);
            } else if(color.equals(ColorType.BLUE)) {
                nickToPlayer(nickname).setPawnImg(UtilCostantValue.pathToBluePawnImg);
                nickToPlayer(nickname).setPawnColor(ColorType.BLUE);
            } else {
                System.err.println("Errore: colore richiesto inesistente!");
            }
            //TODO: specifica observer
            notifyObserver(new NetworkMessage(nickname, MessageType.CORRECT_CHOSEN_COLOR));
            //serve a capire se tutti i player hanno scelto un colore
            if(chosenColorNum == players.length)
                gamePhase3();
        }
    }
*/
    private void initializeStarterCard(){
        Shop starterShop = new Shop(ShopType.STARTER);
        for(Player p: players){
            p.addHandCard(starterShop.drawTopDeckCard());
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
        MissionSelector missionSelector = new MissionSelector();
        sharedMission1 = missionSelector.drawFromFile();
        sharedMission2 = missionSelector.drawFromFile();
        for(Player p: players){
            p.setPersonalMissions(missionSelector.drawFromFile(), missionSelector.drawFromFile());
        }
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
    }

    private Player nickToPlayer(String nickname){//TODO:throw exception da aggiungere
        for(Player p: players) {
            if (p.getNickname().equals(nickname))
                return p;
        }
        return null;//caso player non trovato
    }

    @Override
    public void disconnectPlayer(String nickname) {
        nickToPlayer(nickname).setStatus(false);
        nextTurn();
    }

    @Override
    public void reconnectPlayer(String nickname) {
        nickToPlayer(nickname).setStatus(true);
    }

    @Override
    public void playerDraw(String nickname, int numShopCard, String type) {
        Player p = nickToPlayer(nickname);
        if(type.equals(ShopType.RESOURCE)) {
            p.addHandCard(resourceShop.drawFromShopPlayer(numShopCard));
            nextTurn();
            endGameCheckFinishedShop();
        } else if(type.equals(ShopType.OBJECTIVE)) {
            p.addHandCard(objectiveShop.drawFromShopPlayer(numShopCard));
            nextTurn();
            endGameCheckFinishedShop();
        }
        else{
            notifyObserver(new NetworkMessage(nickname, MessageType.WRONG_TYPE_SHOP));
        }
    }

    @Override
    public void playerPersonalMissionSelect(String nickname, int numMission) {
        Player p = nickToPlayer(nickname);
        p.setPersonalMissionFinal(numMission);
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
    public void playerPlayCard(String nickname, int x, int y, int numCard, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException {
        Player p = nickToPlayer(nickname);
        try {
            p.placeCard(x, y, numCard, isCardBack);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw e; // Propagate the caught exception directly
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw e;
        }
        endGameCheckScoreBoard();
    }

    @Override
    public void playerPlayStarterCard(String nickname, boolean isCardBack) throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException {
        Player p = nickToPlayer(nickname);
        try {
            p.placeCard(p.getGameMap().getMapArray().length, (p.getGameMap().getMapArray())[0].length, 0, isCardBack);
        } catch (PersonalizedException.InvalidPlacementException e) {
            throw e; // Propagate the caught exception directly
        } catch (PersonalizedException.InvalidPlaceCardRequirementException e) {
            throw e;
        }
        starterCards++;
        if(starterCards==players.length)
            gamePhase2();
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

    private void endGameCheckFinishedShop(){
        if(objectiveShop.checkEmptyShop() && resourceShop.checkEmptyShop())
            endGame();
    }

    private void endGameCheckScoreBoard(){
        if(playerTurn.getPersonalScore() >= 20)
            endGame();
    }

    @Override
    public void endGame() {
        isFinalTurn=true;
    }

    private void executeSharedMission(){
        int points = 0;
        for(Player p: players){
            points = sharedMission1.ruleAlgorithmCheck(p) + sharedMission2.ruleAlgorithmCheck(p);
            p.addMissionScore(points);
            points = 0;
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
                    else {
                        playerTurn = players[0];
                        if(isFinalTurn){
                            if(remainingRounds==0)
                                setWinner();
                            else
                                remainingRounds--;
                        }
                    }
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
