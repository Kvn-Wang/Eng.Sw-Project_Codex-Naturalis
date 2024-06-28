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
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.UNASSIGNABLE, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        assertEquals(CardType.OBJECTIVE, thirdTestCard.getCardType());
    }

    @Test
    public void testGetCardColor(){
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.UNASSIGNABLE, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        assertEquals(ResourceType.FUNGI, thirdTestCard.getCardColor());
    }

    @Test
    public void testCheckPlaceableCardCondition(){
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.UNASSIGNABLE, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
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
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.ANIMAL, ResourceType.QUILL, ResourceType.PLANT, ResourceType.INSECT, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        ArrayList<ResourceType> check = new ArrayList<ResourceType>();
        check.add(ResourceType.ANIMAL);
        check.add(ResourceType.QUILL);
        check.add(ResourceType.PLANT);
        check.add(ResourceType.INSECT);
        assertEquals(check, thirdTestCard.getCardResources());
        check.clear();
        check.add(ResourceType.FUNGI);
        thirdTestCard.setIsBack(true);
        assertEquals(check, thirdTestCard.getCardResources());
    }

    @Test
    public void testGetCardPoints(){
        ConditionResourceType[] allConditions = new ConditionResourceType[] {ConditionResourceType.NONE, ConditionResourceType.INKWELL, ConditionResourceType.MANUSCRIPT, ConditionResourceType.OCCUPIEDSPACE, ConditionResourceType.QUILL};
        int testNeighboringCard = 4;
        int check = 1;
        PlayerScoreResource testScoreCard = new PlayerScoreResource();
        for(int i=0; i<5;i++){
            if(i<2){
                testScoreCard.addScore(ResourceType.INKWELL);
            }
            if(i<3){
                testScoreCard.addScore(ResourceType.MANUSCRIPT);
            }
            testScoreCard.addScore(ResourceType.QUILL);
        }
        for(ConditionResourceType testCondition : allConditions){
            ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.UNASSIGNABLE, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, testCondition, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
            assertEquals(check, thirdTestCard.getCardPoints(testScoreCard, testNeighboringCard));
            check++;
        }
        ObjectiveCard fourthTestCard = new ObjectiveCard(41, ResourceType.ANIMAL, ResourceType.QUILL, ResourceType.PLANT, ResourceType.INSECT, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        fourthTestCard.setIsBack(true);
        assertEquals(0, fourthTestCard.getCardPoints(testScoreCard, testNeighboringCard));
    }

    @Test
    public void testGetBackResources(){
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.ANIMAL, ResourceType.QUILL, ResourceType.PLANT, ResourceType.INSECT, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        assertEquals(ResourceType.NONE,thirdTestCard.getBackNorthResource());
        assertEquals(ResourceType.NONE,thirdTestCard.getBackSouthResource());
        assertEquals(ResourceType.NONE,thirdTestCard.getBackEastResource());
        assertEquals(ResourceType.NONE,thirdTestCard.getBackWestResource());
        assertEquals(ResourceType.FUNGI,thirdTestCard.getBackCentralResources()[0]);
        assertEquals(1,thirdTestCard.getBackCentralResources().length);
    }

    @Test
    public void testGetCondition(){
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.ANIMAL, ResourceType.QUILL, ResourceType.PLANT, ResourceType.INSECT, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        assertEquals(ConditionResourceType.QUILL, thirdTestCard.getCondition());
    }

    @Test
    public void testGetPlaceableCardCondition(){
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.ANIMAL, ResourceType.QUILL, ResourceType.PLANT, ResourceType.INSECT, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        ResourceType[] check = new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL};
        for(int i=0; i<check.length;i++) {
            assertEquals(check[0], thirdTestCard.getPlaceableCardResources()[0]);
        }
    }

    @Test
    public void testGetFrontalNumber(){
        ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.ANIMAL, ResourceType.QUILL, ResourceType.PLANT, ResourceType.INSECT, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        assertEquals(1,thirdTestCard.getFrontalNumber());
    }
}