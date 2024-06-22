package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.utils.observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);

    Observer observer;
    Player testPlayer1 = new Player("roberto", ColorType.RED, observer);
    int middle = UtilCostantValue.lunghezzaMaxMappa/2;

    @Test
    public void testAddHandCard() throws PersonalizedException.InvalidPopCardException, PersonalizedException.InvalidNumPopCardException {
        testPlayer1.addHandCard(firstTestCard);
        Hand testHand = testPlayer1.getHand();
        assertEquals(firstTestCard, testHand.popCard(0));
    }

    @Test
    public void testPlaceCard() throws PersonalizedException.InvalidPopCardException, PersonalizedException.InvalidNumPopCardException, PersonalizedException.InvalidPlaceCardRequirementException, PersonalizedException.InvalidPlacementException {
        testPlayer1.addHandCard(starterCard);
        Hand testHand = testPlayer1.getHand();

        starterCard.setIsBack(false);
        testPlayer1.placeCard(middle,middle,starterCard);
        GamePlayerMap testMap = testPlayer1.getGameMap();
        assertThrows(PersonalizedException.InvalidPopCardException.class, ()->testHand.popCard(0));
        Card testArray[][] = testMap.getMapArray();
        assertEquals(starterCard, testArray[middle][middle]);
        testPlayer1.addHandCard(firstTestCard);

        firstTestCard.setIsBack(false);
        assertThrows(PersonalizedException.InvalidPlacementException.class, ()->testPlayer1.placeCard(middle,middle,firstTestCard));
        assertEquals(firstTestCard, testHand.popCard(0));
    }

    @Test
    public void testAddMissionScore(){
        testPlayer1.addScore(3);
        assertEquals(3,testPlayer1.getPersonalScore());
        assertEquals(3,testPlayer1.getPersonalScoreBoardScore());
    }
}