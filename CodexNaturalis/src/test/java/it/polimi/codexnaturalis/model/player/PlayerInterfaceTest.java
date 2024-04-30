package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.mission.BendMission;
import it.polimi.codexnaturalis.model.mission.DiagonalMission;
import it.polimi.codexnaturalis.model.mission.ResourceMission;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInterfaceTest {
    DiagonalMission testDiagonalMission = new DiagonalMission(87, 2 , false, ResourceType.FUNGI);
    BendMission testBendMission = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT,CardCorner.SOUTH);
    ResourceMission testResourceMission = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.FUNGI});
    Player testPlayer = new Player("nick", ColorType.RED);
    GamePlayerMap testMap = testPlayer.getGameMap();
    ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);

    @Test
    public void testExecutePersonalMission() throws PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException {
        testPlayer.setPersonalMissions(testDiagonalMission,testResourceMission);
        testPlayer.setPersonalMissionFinal(1);
        GamePlayerMap testMap = testPlayer.getGameMap();
        testMap.placeCard(10,10,firstTestCard,true);
        testMap.placeCard(10,9,firstTestCard,true);
        testMap.placeCard(10,8,firstTestCard,true);
        testMap.placeCard(10,7,firstTestCard,true);
        testMap.placeCard(10,6,firstTestCard,true);
        testMap.placeCard(10,5,firstTestCard,true);
        int score;
        score = testPlayer.executePersonalMission();
        System.out.println(score);
    }
    @Test
    void roba(){

    }
}