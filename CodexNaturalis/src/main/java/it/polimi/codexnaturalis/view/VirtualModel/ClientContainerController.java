package it.polimi.codexnaturalis.view.VirtualModel;

import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.player.Hand;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.PlayerInfo;

import java.util.ArrayList;

public interface ClientContainerController {
    void setNickname(String nickname);
    String getNickname();
    void setLobbyName(String lobbyName);
    String getLobbyName();
    void setPlayerMap(int x, int y, Card card);
    Card[][] getPlayerMap();
    void setHand(Hand hand);
    Hand getHand();
    ArrayList<PlayerInfo> getOtherPlayerInfo();
    void setCommonMission(Mission mission1, Mission mission2);
    Mission [] getCommonMission();
    Mission getPersonalMission();
    void setPersonalMission(Mission personalMission);
    void setOtherPlayer(ArrayList<PlayerInfo> listOtherPlayer);
}
