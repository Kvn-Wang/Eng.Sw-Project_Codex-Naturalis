package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
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
    ResourceCard secondTestCard = new ResourceCard(1, ResourceType.FUNGI, ResourceType.UNASSIGNABLE, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    ObjectiveCard thirdTestCard = new ObjectiveCard(41, ResourceType.UNASSIGNABLE, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
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
        firstTestCard.setIsBack(true);
        assertThrows(PersonalizedException.InvalidPlacementException.class, ()-> gamePlayerMap.placeCard(middle,middle,firstTestCard));
        starterCard.setIsBack(true);
        gamePlayerMap.placeCard(middle,middle,starterCard);
        PlayerScoreResource test = gamePlayerMap.getPlayerScoreCard();
        starterCard.setIsBack(true);
        gamePlayerMap.placeCard(middle,middle+1,starterCard);

        secondTestCard.setIsBack(false);
        gamePlayerMap.placeCard(middle,middle-1,secondTestCard);
        thirdTestCard.setIsBack(false);
        gamePlayerMap.placeCard(middle+1,middle,thirdTestCard);
        PlayerScoreResource testscorecard = gamePlayerMap.getPlayerScoreCard();
        System.out.println(testscorecard.getScore(ResourceType.ANIMAL));
        assertThrows(PersonalizedException.InvalidPlaceCardRequirementException.class, ()-> gamePlayerMap.placeCard(middle+2,middle,thirdTestCard));
        thirdTestCard.setIsBack(true);
        assertThrows(PersonalizedException.InvalidPlacementException.class, ()-> gamePlayerMap.placeCard(1,1,thirdTestCard));

        firstTestCard.setIsBack(true);
        for(int i = middle+2; i < UtilCostantValue.lunghezzaMaxMappa  ;i++){
            gamePlayerMap.placeCard(middle, i, firstTestCard);
        }
        assertThrows(PersonalizedException.InvalidPlacementException.class, ()-> gamePlayerMap.placeCard(middle, UtilCostantValue.lunghezzaMaxMappa, firstTestCard));
        for(int i = middle-2; i >= 0 ; i--){
            gamePlayerMap.placeCard(middle, i, firstTestCard);
        }
        assertThrows(PersonalizedException.InvalidPlacementException.class, ()-> gamePlayerMap.placeCard(middle, -1 , firstTestCard));
        for(int i = middle-1; i >= 0 ; i--){
            gamePlayerMap.placeCard(i , middle, firstTestCard);
        }
        assertThrows(PersonalizedException.InvalidPlacementException.class, ()-> gamePlayerMap.placeCard( -1 ,middle, firstTestCard));
        for(int i = middle+2; i < UtilCostantValue.lunghezzaMaxMappa  ;i++){
            gamePlayerMap.placeCard( i, middle, firstTestCard);
        }
        assertThrows(PersonalizedException.InvalidPlacementException.class, ()-> gamePlayerMap.placeCard( UtilCostantValue.lunghezzaMaxMappa, middle, firstTestCard));
    }
}