package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Card testCard;
    @BeforeEach
    void setUp() {
        int png = 1;
        ResourceType north = ResourceType.NONE;
        ResourceType south = ResourceType.ANIMAL;
        ResourceType east = ResourceType.FUNGI;
        ResourceType west = ResourceType.UNASSIGNABLE;
        testCard = new Card(png, north,south,east,west) {
            @Override
            public ResourceType getCardColor() {
                return null;
            }

            @Override
            public boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard) {
                return false;
            }

            @Override
            public ArrayList<ResourceType> getCardResources() {
                return null;
            }

            @Override
            public int getCardPoints(PlayerScoreResource scoreCard, int neightbouringCard) {
                return 0;
            }

            @Override
            public ResourceType getBackNorthResource() {
                return null;
            }

            @Override
            public ResourceType getBackSouthResource() {
                return null;
            }

            @Override
            public ResourceType getBackEastResource() {return null;}

            @Override
            public ResourceType getBackWestResource() {
                return null;
            }

            @Override
            public ResourceType[] getBackCentralResources() {
                return new ResourceType[0];
            }

            @Override
            public ConditionResourceType getCondition() {
                return null;
            }

            @Override
            public ResourceType[] getPlaceableCardResources() {
                return new ResourceType[0];
            }

            @Override
            public int getFrontalNumber() {
                return 0;
            }

            @Override
            public CardType getCardType() {
                return null;
            }
        };

    }
    @Test
    void testGetFrontNorthResource() {
        assertEquals(ResourceType.NONE, testCard.getFrontNorthResource());
    }

    @Test
    void testGetFrontSouthResource() {
        assertEquals(ResourceType.ANIMAL, testCard.getFrontSouthResource());
    }

    @Test
    void testGetFrontEastResource() {
        assertEquals(ResourceType.FUNGI, testCard.getFrontEastResource());
    }

    @Test
    void testGetFrontWestResource() {
        assertEquals(ResourceType.UNASSIGNABLE, testCard.getFrontWestResource());
    }

    @Test
    void testEquals() {
        assertEquals(true, testCard.equals(testCard));
        assertEquals(false, testCard.equals(null));
        Card testCard2 = testCard;
        assertEquals(true, testCard.equals(testCard2));
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.UNASSIGNABLE, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        ObjectiveCard fourthTestCard = new ObjectiveCard(41, ResourceType.NONE, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        assertEquals(false, fourthTestCard.equals(thirdTestCard));
    }
}