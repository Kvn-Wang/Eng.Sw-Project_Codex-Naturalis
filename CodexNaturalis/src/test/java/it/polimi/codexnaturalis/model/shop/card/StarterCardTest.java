package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class StarterCardTest {

    public CardCorner[] cornerList = new CardCorner[] {CardCorner.NORTH, CardCorner.SOUTH,CardCorner.EAST,CardCorner.WEST};
    @Test
    public void testStarterCard(){
        StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
        assertEquals(CardType.STARTER, starterCard.getCardType());
    }

    @Test
    public void testGetCardResources(){
        int i = 0;
        StarterCard starterCard = new StarterCard(81, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
        ArrayList<ResourceType> temp = new ArrayList<ResourceType>();
        temp.add(ResourceType.FUNGI);
        temp.add(ResourceType.ANIMAL);
        temp.add(ResourceType.PLANT);
        temp.add(ResourceType.INSECT);
        temp.add(ResourceType.INSECT);
        assertEquals(temp, starterCard.getCardResources());
        temp.clear();
        starterCard.setIsBack(true);
        temp.add(ResourceType.FUNGI);
        temp.add(ResourceType.ANIMAL);
        temp.add(ResourceType.INSECT);
        temp.add(ResourceType.PLANT);
        assertEquals(temp, starterCard.getCardResources());
    }

    @Test
    public void testGetBackCentralResources(){
        StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
        assertEquals(ResourceType.INSECT, starterCard.getBackCentralResources()[0]);
    }

    @Test
    public void testGetCondition(){
        StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
        assertEquals(ConditionResourceType.NONE, starterCard.getCondition());
    }

    @Test
    public void testGetPlaceableCardResources(){
        StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
        assertEquals(null, starterCard.getPlaceableCardResources());
    }

    @Test
    public void testGetFrontalNumber(){
        StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
        assertEquals(0, starterCard.getFrontalNumber());
    }
}