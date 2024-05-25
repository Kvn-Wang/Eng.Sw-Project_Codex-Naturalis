package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.mission.BendMission;
import it.polimi.codexnaturalis.model.mission.DiagonalMission;
import it.polimi.codexnaturalis.model.mission.ResourceMission;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInterfaceTest {
    DiagonalMission testDiagonalMission = new DiagonalMission(87, 2 , false, ResourceType.FUNGI);
    DiagonalMission testDiagonalMission1 = new DiagonalMission(87, 2 , false, ResourceType.FUNGI);
    StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
    BendMission testBendMission = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT,CardCorner.SOUTH);
    ResourceMission testResourceMission = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.FUNGI});
    Player testPlayer1 = new Player("nick", ColorType.RED);
    Player testPlayer2 = new Player("nack", ColorType.BLUE);
    GamePlayerMap testMap = testPlayer1.getGameMap();
    ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    int middle = UtilCostantValue.lunghezzaMaxMappa/2;
    @Test
    public void testExecutePersonalMission() throws PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException {
        testPlayer1.setPersonalMissions(testDiagonalMission,testResourceMission);
        testPlayer1.setPersonalMissionFinal(1);
        GamePlayerMap testMap1 = testPlayer1.getGameMap();
        testMap1.placeCard(middle,middle,starterCard,true);
        testMap1.placeCard(middle,middle-1,firstTestCard,true);
        testMap1.placeCard(middle,middle-2,firstTestCard,true);
        testMap1.placeCard(middle,middle-3,firstTestCard,true);
        testMap1.placeCard(middle,middle-4,firstTestCard,true);
        testMap1.placeCard(middle,middle-5,firstTestCard,true);
        assertEquals(4, testPlayer1.executePersonalMission());
        testDiagonalMission.getUsedArray();
        //assertEquals(0, testPlayer1.executePersonalMission());
        testPlayer2.setPersonalMissions(testDiagonalMission1,testResourceMission);
        testPlayer2.setPersonalMissionFinal(1);
        GamePlayerMap testMap2 = testPlayer2.getGameMap();
        testMap2.placeCard(middle,middle,starterCard,true);
        testMap2.placeCard(middle,middle-1,firstTestCard,true);
        testMap2.placeCard(middle,middle-2,firstTestCard,true);
        testMap2.placeCard(middle,middle-3,firstTestCard,true);
        testMap2.placeCard(middle,middle-4,firstTestCard,true);
        assertEquals(2,testPlayer2.executePersonalMission());
        //assertEquals(0,testPlayer2.executePersonalMission());
    }
    @Test
    void roba(){

    }
}