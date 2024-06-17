package it.polimi.codexnaturalis.view.VirtualModel;

import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.ArrayList;

public class ClientContainer {
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Mission getPersonalMission() {
        return personalMission;
    }

    public void setPersonalMission(Mission personalMission) {
        this.personalMission = personalMission;
    }

    public String getLobbyNickname() {
        return lobbyNickname;
    }

    public void setLobbyNickname(String lobbyNickname) {
        this.lobbyNickname = lobbyNickname;
    }

    public ArrayList<PlayerInfo> getOtherPlayerList() {
        return otherPlayerList;
    }

    public void setOtherPlayerList(ArrayList<PlayerInfo> otherPlayerList) {
        this.otherPlayerList = otherPlayerList;
    }

    public Card[][] getPersonalGameMap() {
        return personalGameMap;
    }

    public void setPersonalGameMap(Card[][] personalGameMap) {
        this.personalGameMap = personalGameMap;
    }

    public Hand getPersonalHand() {
        return personalHand;
    }

    public void setPersonalHand(Hand personalHand) {
        this.personalHand = personalHand;
    }

    public Mission getCommonMission1() {
        return commonMission1;
    }

    public void setCommonMission1(Mission commonMission1) {
        this.commonMission1 = commonMission1;
    }

    public Mission getCommonMission2() {
        return commonMission2;
    }

    public void setCommonMission2(Mission commonMission2) {
        this.commonMission2 = commonMission2;
    }
}
