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

class BendMissionTest {
    StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
    BendMission testBendMission = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT, CardCorner.SOUTH);
    BendMission testBendMission1 = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT, CardCorner.NORTH);
    BendMission testBendMission2 = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT, CardCorner.EAST);
    BendMission testBendMission3 = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT, CardCorner.WEST);
    Player testPlayer1 = new Player("nick", ColorType.RED);
    Player testPlayer2 = new Player("nack", ColorType.BLUE);
    Player testPlayer3 = new Player("nock", ColorType.YELLOW);
    Player testPlayer4 = new Player("nuck", ColorType.GREEN);
    GamePlayerMap testMap = testPlayer1.getGameMap();
    ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    ResourceCard secondTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.PLANT, 0);

    int middle = UtilCostantValue.lunghezzaMaxMappa/2;

    @Test
    public void testAlgorithmCheck() throws PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException {
        GamePlayerMap testMap1 = testPlayer1.getGameMap();
        starterCard.setIsBack(true);
        firstTestCard.setIsBack(true);
        secondTestCard.setIsBack(true);
        testMap1.placeCard(middle,middle,starterCard);
        testMap1.placeCard(middle-1,middle,secondTestCard);
        testMap1.placeCard(middle-2,middle,firstTestCard);
        testMap1.placeCard(middle-3,middle,firstTestCard);
        assertEquals(0,testBendMission.ruleAlgorithmCheck(testPlayer1));
        testMap1.placeCard(middle-3,middle+1,firstTestCard);
        assertEquals(3,testBendMission.ruleAlgorithmCheck(testPlayer1));
        GamePlayerMap testMap2 = testPlayer2.getGameMap();
        testMap2.placeCard(middle,middle,starterCard);
        testMap2.placeCard(middle+1,middle,secondTestCard);
        testMap2.placeCard(middle+2,middle,firstTestCard);
        testMap2.placeCard(middle+3,middle,firstTestCard);
        assertEquals(0,testBendMission1.ruleAlgorithmCheck(testPlayer2));
        testMap2.placeCard(middle+3,middle-1,firstTestCard);
        assertEquals(3,testBendMission1.ruleAlgorithmCheck(testPlayer2));
        GamePlayerMap testMap3 = testPlayer3.getGameMap();
        testMap3.placeCard(middle,middle,starterCard);
        testMap3.placeCard(middle,middle-1,secondTestCard);
        testMap3.placeCard(middle,middle-2,firstTestCard);
        testMap3.placeCard(middle,middle-3,firstTestCard);
        assertEquals(0,testBendMission2.ruleAlgorithmCheck(testPlayer3));
        testMap3.placeCard(middle+1,middle-3,firstTestCard);
        assertEquals(3,testBendMission2.ruleAlgorithmCheck(testPlayer3));
        GamePlayerMap testMap4 = testPlayer4.getGameMap();
        testMap4.placeCard(middle,middle,starterCard);
        testMap4.placeCard(middle,middle+1,secondTestCard);
        testMap4.placeCard(middle,middle+2,firstTestCard);
        testMap4.placeCard(middle,middle+3,firstTestCard);
        assertEquals(0,testBendMission3.ruleAlgorithmCheck(testPlayer4));
        testMap4.placeCard(middle-1,middle+3,firstTestCard);
        assertEquals(3,testBendMission3.ruleAlgorithmCheck(testPlayer4));

    }
    }