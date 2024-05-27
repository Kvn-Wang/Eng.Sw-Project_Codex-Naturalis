package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalMissionTest {
    DiagonalMission testDiagonalMission = new DiagonalMission(87, 2 , false, ResourceType.FUNGI);
    DiagonalMission testDiagonalMission1 = new DiagonalMission(87, 2 , false, ResourceType.FUNGI);
    DiagonalMission testDiagonalMission2 = new DiagonalMission(87, 2 , true, ResourceType.FUNGI);
    DiagonalMission testDiagonalMission3 = new DiagonalMission(87, 2 , true, ResourceType.FUNGI);

    StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
    BendMission testBendMission = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT, CardCorner.SOUTH);
    ResourceMission testResourceMission = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.FUNGI});
    Player testPlayer1 = new Player("nick", ColorType.RED);
    Player testPlayer2 = new Player("nack", ColorType.BLUE);
    Player testPlayer3 = new Player("nock", ColorType.YELLOW);
    Player testPlayer4 = new Player("nuck", ColorType.GREEN);


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
        int x = testPlayer1.executePersonalMission();
        assertEquals(4, x);
        testPlayer2.setPersonalMissions(testDiagonalMission1,testResourceMission);
        testPlayer2.setPersonalMissionFinal(1);
        GamePlayerMap testMap2 = testPlayer2.getGameMap();
        testMap2.placeCard(middle,middle,starterCard,true);
        testMap2.placeCard(middle,middle-1,firstTestCard,true);
        testMap2.placeCard(middle,middle-2,firstTestCard,true);
        testMap2.placeCard(middle,middle-3,firstTestCard,true);
        testMap2.placeCard(middle,middle-4,firstTestCard,true);
        assertEquals(2,testPlayer2.executePersonalMission());
        testPlayer3.setPersonalMissions(testDiagonalMission2,testResourceMission);
        testPlayer3.setPersonalMissionFinal(1);
        GamePlayerMap testMap3 = testPlayer3.getGameMap();
        testMap3.placeCard(middle,middle,starterCard,true);
        testMap3.placeCard(middle,middle+1,firstTestCard,true);
        testMap3.placeCard(middle,middle+2,firstTestCard,true);
        testMap3.placeCard(middle,middle+3,firstTestCard,true);
        testMap3.placeCard(middle,middle+4,firstTestCard,true);
        testMap3.placeCard(middle,middle+5,firstTestCard,true);
        assertEquals(4,testPlayer3.executePersonalMission());
        testPlayer4.setPersonalMissions(testDiagonalMission3,testResourceMission);
        testPlayer4.setPersonalMissionFinal(1);
        GamePlayerMap testMap4 = testPlayer4.getGameMap();
        testMap4.placeCard(middle,middle,starterCard,true);
        testMap4.placeCard(middle,middle+1,firstTestCard,true);
        testMap4.placeCard(middle,middle+2,firstTestCard,true);
        testMap4.placeCard(middle,middle+3,firstTestCard,true);
        testMap4.placeCard(middle,middle+4,firstTestCard,true);
        assertEquals(2,testPlayer4.executePersonalMission());
    }
}