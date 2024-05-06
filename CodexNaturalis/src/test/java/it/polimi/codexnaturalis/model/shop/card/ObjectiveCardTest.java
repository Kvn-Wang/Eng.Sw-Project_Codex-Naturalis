package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardTest {

    @Test
    public void testObjectiveCard(){
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        assertEquals(CardType.OBJECTIVE, thirdTestCard.getCardType());
    }

    @Test
    public void testCheckPlaceableCardCondition(){
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        PlayerScoreResource testScoreCard = new PlayerScoreResource();
        assertEquals(false, thirdTestCard.checkPlaceableCardCondition(testScoreCard));
        testScoreCard.addScore(ResourceType.FUNGI);
        assertEquals(false, thirdTestCard.checkPlaceableCardCondition(testScoreCard));
        testScoreCard.addScore(ResourceType.FUNGI);
        assertEquals(false, thirdTestCard.checkPlaceableCardCondition(testScoreCard));
        testScoreCard.addScore(ResourceType.ANIMAL);
        assertEquals(true, thirdTestCard.checkPlaceableCardCondition(testScoreCard));
        testScoreCard.addScore(ResourceType.ANIMAL);
        assertEquals(true, thirdTestCard.checkPlaceableCardCondition(testScoreCard));
        testScoreCard.substractScore(ResourceType.FUNGI);
        assertEquals(false, thirdTestCard.checkPlaceableCardCondition(testScoreCard));
    }

    @Test
    public void testGetCardResources(){
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        ArrayList<ResourceType> check = new ArrayList<ResourceType>();
        check.add(ResourceType.QUILL);
        assertEquals(check, thirdTestCard.getCardResources());
        check.clear();
        check.add(ResourceType.FUNGI);
        thirdTestCard.setIsBack(true);
        assertEquals(check, thirdTestCard.getCardResources());
    }

    @Test
    public void testGetCardPoints(){
        ConditionResourceType[] allConditions = new ConditionResourceType[] {ConditionResourceType.NONE, ConditionResourceType.INKWELL, ConditionResourceType.MANUSCRIPT, ConditionResourceType.QUILL, ConditionResourceType.OCCUPIEDSPACE};

        for(ConditionResourceType testCondition : allConditions){

        }
    }
}