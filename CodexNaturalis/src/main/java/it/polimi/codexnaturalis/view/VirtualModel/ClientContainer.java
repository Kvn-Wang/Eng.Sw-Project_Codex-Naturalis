package it.polimi.codexnaturalis.view.VirtualModel;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.view.TUI.PrintCardClass;
import it.polimi.codexnaturalis.view.VirtualModel.Hand.Hand;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.network.util.PlayerInfo;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ClientContainer {
    private String nickname;
    private String lobbyNickname;
    private ColorType personalColor;
    private HashMap<String, PlayerData> players;
    private PlayerScoreResource personalPlayerScoreResource;
    private Hand personalHand;
    private Mission commonMission1;
    private Mission commonMission2;
    private Mission personalMission;
    private Card topDeckResourceCardShop;
    private Card [] visibleResourceCardShop;
    private Card topDeckObjCardShop;
    private Card [] visibleObjectiveCardShop;

    public ClientContainer() {
        players = new HashMap<>();

        visibleResourceCardShop = new Card[2];
        visibleObjectiveCardShop = new Card[2];
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

    public void setOtherPlayerList(ArrayList<PlayerInfo> otherPlayerList) {
        players.put(getNickname(), new PlayerData());
        players.get(getNickname()).setPlayerColor(personalColor);

        for(PlayerInfo playerInfo : otherPlayerList) {
            players.put(playerInfo.getNickname(), new PlayerData());
            players.get(playerInfo.getNickname()).setPlayerColor(playerInfo.getColorChosen());
        }
    }

    public Card[][] getPersonalGameMap() {
        return players.get(getNickname()).getMap();
    }

    public Hand getPersonalHand() {
        return personalHand;
    }

    public Card[][] getOthersGameMap(String playerNickname) {return players.get(playerNickname).getMap();}

    public Mission getCommonMission1() {
        return commonMission1;
    }


    public Mission getCommonMission2() {
        return commonMission2;
    }

    public void initialSetupOfResources(Hand initialHand, Mission firstCommonMission, Mission secondCommonMission,
                                        Card topDeckResourceCardShop,
                                        Card visibleResourceCardShop1, Card visibleResourceCardShop2,
                                        Card topDeckObjCardShop,
                                        Card visibleObjCardShop1, Card visibleObjCardShop2) {
        personalHand = initialHand;
        commonMission1 = firstCommonMission;
        commonMission2 = secondCommonMission;

        this.topDeckResourceCardShop = topDeckResourceCardShop;
        visibleResourceCardShop[0] = visibleResourceCardShop1;
        visibleResourceCardShop[1] = visibleResourceCardShop2;

        this.topDeckObjCardShop = topDeckObjCardShop;
        visibleObjectiveCardShop[0] = visibleObjCardShop1;
        visibleObjectiveCardShop[1] = visibleObjCardShop2;
    }

    public void updateShopCard(Card card, ShopType type, int numCard) {
        if(type == ShopType.RESOURCE) {
            if(numCard == 0) {
                PrintCardClass.printCardHorizzontal(topDeckResourceCardShop);
                topDeckResourceCardShop = card;
                PrintCardClass.printCardHorizzontal(topDeckResourceCardShop);
            } else if(numCard == 1) {
                PrintCardClass.printCardHorizzontal(visibleResourceCardShop[0]);
                visibleResourceCardShop[0] = card;
                PrintCardClass.printCardHorizzontal(visibleResourceCardShop[0]);
            } else if(numCard == 2) {
                visibleResourceCardShop[1] = card;
            }
        } else if(type == ShopType.OBJECTIVE) {
            if(numCard == 0) {
                topDeckObjCardShop = card;
            } else if(numCard == 1) {
                visibleObjectiveCardShop[0] = card;
            } else if(numCard == 2) {
                visibleObjectiveCardShop[1] = card;
            }
        }
    }

    public void updatePersonalScore(int personalScoreBoardValue, PlayerScoreResource personalPlayerScoreResource) {
        players.get(getNickname()).setIntScoreBoardScore(personalScoreBoardValue);
        this.personalPlayerScoreResource = personalPlayerScoreResource;
    }

    /**
     * pop card
     */
    public void playedCard(int x, int y, Card card) {
        players.get(getNickname()).playCard(x, y, card);
        personalHand.popCard(card);
    }

    public void playedStarterCard(Card starterCard) {
        players.get(getNickname()).playCard(UtilCostantValue.lunghezzaMaxMappa/2, UtilCostantValue.lunghezzaMaxMappa/2, starterCard);
    }

    public void updateOtherPlayerMap(String nickname, int x, int y, Card card, int hisNewPlayerScore) {
        players.get(nickname).playCard(x, y, card);
        players.get(nickname).setIntScoreBoardScore(hisNewPlayerScore);
    }

    public void addCardToHand(Card card) {
        personalHand.addCard(card);
    }

    public Card getTopDeckResourceCardShop() {
        return topDeckResourceCardShop;
    }

    public Card getTopDeckObjCardShop() {
        return topDeckObjCardShop;
    }

    public Card[] getVisibleResourceCardShop() {
        return visibleResourceCardShop;
    }

    public Card[] getVisibleObjectiveCardShop() {
        return visibleObjectiveCardShop;
    }

    public HashMap<String, PlayerData> getPlayers() {
        return players;
    }

    public Set<String> getOtherPlayerNames() {return players.keySet();}

    public void setPersonalColor(ColorType color) {
        personalColor = color;
        //players.get(getNickname()).setPlayerColor(color);
    }

    public void initPlayerLobby(ArrayList<PlayerInfo> playerThatAreAlreadyInTheLobby) {
        for(PlayerInfo player : playerThatAreAlreadyInTheLobby) {
            players.put(player.getNickname(), new PlayerData());
        }
    }

    public void playerJoinedTheLobby(String nickname) {
        players.put(nickname, new PlayerData());
    }

    public void playerLeftTheLobby(String nickname) {
        players.remove(nickname);
    }

    public void playerSelectedColor(String nickname, ColorType color) {
        players.get(nickname).setPlayerColor(color);
    }
}
