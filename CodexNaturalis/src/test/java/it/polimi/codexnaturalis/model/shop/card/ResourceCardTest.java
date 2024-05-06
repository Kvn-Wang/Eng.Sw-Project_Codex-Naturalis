package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResourceCardTest {
    @Test
    public void testResourceCard(){
        ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
        assertEquals(CardType.RESOURCE, firstTestCard.getCardType());
    }

    @Test
    public void testGetCardResource(){
        ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
        ArrayList<ResourceType> temp = new ArrayList<ResourceType>();
        temp.add(ResourceType.FUNGI);
        temp.add(ResourceType.FUNGI);
        assertEquals(temp, firstTestCard.getCardResources());
        temp.clear();
        temp.add(ResourceType.FUNGI);
        firstTestCard.setIsBack(true);
        assertEquals(temp,firstTestCard.getCardResources());
    }

    @Test
    public void testGetCardPoints(){
        PlayerScoreResource testScore = null;
        ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 1);
        assertEquals(1, firstTestCard.getCardPoints(testScore, 0));
        firstTestCard.setIsBack(true);
        assertEquals(0, firstTestCard.getCardPoints(testScore, 0));
    }
}