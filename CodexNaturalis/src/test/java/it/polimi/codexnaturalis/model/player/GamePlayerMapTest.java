package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

class GamePlayerMapTest {

    private GamePlayerMap gamePlayerMap;
    StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
    ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    ResourceCard secondTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    ObjectiveCard thirdTestCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
    StarterCard fourthTestCard = starterCard;
    ObjectiveCard testPlacedCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
    @BeforeEach
    void setUp() {
        PlayerScoreResource playerScoreResource = new PlayerScoreResource();
        gamePlayerMap = new GamePlayerMap(playerScoreResource);
        ResourceCard northTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
        ResourceCard southTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
        ObjectiveCard eastTestCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        StarterCard westTestCard = starterCard;
        ObjectiveCard testPlacedCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
    }

    /*public void test(){
        Scanner scanner = new Scanner(System.in);
        String inputString;
        new StarterCard(81,ResourceType.NONE,ResourceType.NONE,ResourceType.PLANT, ResourceType.INSECT,new ResourceType[]{ResourceType.INSECT},ResourceType.FUNGI,ResourceType.ANIMAL,ResourceType.PLANT,ResourceType.INSECT);
        new ResourceCard(1,ResourceType.FUNGI,null,ResourceType.NONE,ResourceType.FUNGI,ResourceType.FUNGI,0);
        new ObjectiveCard(41,null,ResourceType.QUILL,ResourceType.NONE,ResourceType.NONE,ResourceType.FUNGI, ConditionResourceType.QUILL,1,new ResourceType[]{ResourceType.FUNGI,ResourceType.FUNGI,ResourceType.ANIMAL});

    }*/
    @Test
    public void testMapArray() {
        Card[][] resultArray;
        int size = 0;
        int middle = UtilCostantValue.lunghezzaMaxMappa / 2;
        boolean Validity;
        resultArray = gamePlayerMap.getMapArray();
        size = resultArray.length * resultArray[0].length;
        System.out.println(size);
        if (size == UtilCostantValue.lunghezzaMaxMappa * UtilCostantValue.lunghezzaMaxMappa) {
            System.out.println("success");
        } else if (size != UtilCostantValue.lunghezzaMaxMappa * UtilCostantValue.lunghezzaMaxMappa) {
            System.out.println("mapArray of Wrong size");
            fail();
        } else {
            System.out.println("unknown error");
            fail();
        }
    }

    @Test
    public void testPlaceCard() throws PersonalizedException.InvalidPlacementException, PersonalizedException.InvalidPlaceCardRequirementException {
        int middle = UtilCostantValue.lunghezzaMaxMappa / 2;
        ArrayList<ResourceType> tempListOfResources;
        PlayerScoreResource testScoreCard = gamePlayerMap.getPlayerScoreCard();
        Card[][] mapArray = gamePlayerMap.getMapArray();
        int point;

        mapArray[middle][middle+1] = firstTestCard;
        mapArray[middle][middle-1] = secondTestCard;
        tempListOfResources = firstTestCard.getCardResources();
        for(ResourceType element : tempListOfResources) {
            testScoreCard.addScore(element);
        }
        tempListOfResources = secondTestCard.getCardResources();
        for(ResourceType element : tempListOfResources) {
            testScoreCard.addScore(element);
        }
        PlayerScoreResource checkScoreCard = testScoreCard;
        assertThrows(PersonalizedException.InvalidPlaceCardRequirementException.class, ()-> gamePlayerMap.placeCard(middle-1,middle,thirdTestCard,false));
        assertThrows(PersonalizedException.InvalidPlacementException.class, ()-> gamePlayerMap.placeCard(1,1,thirdTestCard,true));

    }
}