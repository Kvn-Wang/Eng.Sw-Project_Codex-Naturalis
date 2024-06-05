package it.polimi.codexnaturalis.view.VirtualModel;

import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.ArrayList;

public class ClientContainer implements ClientContainerController {
    private String nickname;
    private String lobbyNickname;
    private ArrayList<PlayerInfo> otherPlayerList;
    private Card[][] personalGameMap;
    private Hand personalHand;
    private Mission commonMission1;
    private Mission commonMission2;
    private Mission personalMission;

    public ClientContainer() {
        //inizializzazione mappa vuota
        personalGameMap = new Card[UtilCostantValue.lunghezzaMaxMappa][UtilCostantValue.lunghezzaMaxMappa];
        for(int i = 0; i < UtilCostantValue.lunghezzaMaxMappa; i++) {
            for(int j = 0; j < UtilCostantValue.lunghezzaMaxMappa; j++) {
                personalGameMap[i][j] = null;
            }
        }
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname=nickname;
    }

    @Override
    public void setPlayerMap(int x, int y, Card card) {
        personalGameMap[x][y] = card;
    }

    @Override
    public Card[][] getPlayerMap() {
        return personalGameMap;
    }

    @Override
    public void setHand(Hand hand) {
        personalHand = hand;
    }

    @Override
    public Hand getHand() {
        return personalHand;
    }

    @Override
    public ArrayList<PlayerInfo> getOtherPlayerInfo() {
        return otherPlayerList;
    }

    @Override
    public void setCommonMission(Mission mission1, Mission mission2) {
        commonMission1 = mission1;
        commonMission2 = mission2;
    }

    @Override
    public Mission[] getCommonMission() {
        Mission[] commonMissionArray = new Mission[2];
        commonMissionArray[0] = commonMission1;
        commonMissionArray[1] = commonMission2;

        return commonMissionArray;
    }

    @Override
    public Mission getPersonalMission() {
        return personalMission;
    }

    @Override
    public void setPersonalMission(Mission personalMission) {
        this.personalMission = personalMission;
    }

    @Override
    public void setOtherPlayer(ArrayList<PlayerInfo> listOtherPlayer) {
        otherPlayerList = listOtherPlayer;
    }


    public String getNickname() {
        return nickname;
    }

    @Override
    public void setLobbyName(String lobbyName) {
        this.lobbyNickname = lobbyName;
    }

    @Override
    public String getLobbyName() {
        return lobbyNickname;
    }
}
