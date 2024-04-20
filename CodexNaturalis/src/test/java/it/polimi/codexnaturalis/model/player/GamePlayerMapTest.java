package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

class GamePlayerMapTest {

    private GamePlayerMap gamePlayerMap;
    StarterCard starteCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
    ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    ResourceCard secondTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    ObjectiveCard thirdTestCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
    StarterCard fourthTestCard = starteCard;
    ObjectiveCard testPlacedCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
    @BeforeEach
    void setUp() {
        PlayerScoreResource playerScoreResource = new PlayerScoreResource();
        gamePlayerMap = new GamePlayerMap(playerScoreResource, starteCard);
        ResourceCard northTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
        ResourceCard southTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
        ObjectiveCard eastTestCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
        StarterCard westTestCard = starteCard;
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
    public void testGetMapArray() {
        Card[][] resultArray;
        int size = 0;
        int middle = UtilCostantValue.lunghezzaMaxMappa / 2;
        boolean Validity;
        resultArray = gamePlayerMap.getMapArray();
        size = resultArray.length * resultArray[0].length;
        System.out.println(size);
        /*for(int i=0; i<resultArray.length; i++){
            for(int j=0;j<resultArray[i].length;j++){
                if(resultArray[i][j]==null ||){

                }
            }
        }*/
        if (resultArray[middle][middle] == starteCard && size == UtilCostantValue.lunghezzaMaxMappa * UtilCostantValue.lunghezzaMaxMappa) {
            System.out.println("success");
        } else if (resultArray[middle][middle] != starteCard) {
            System.out.println("absent starter or in worng position");
        } else if (size != UtilCostantValue.lunghezzaMaxMappa * UtilCostantValue.lunghezzaMaxMappa) {
            System.out.println("mapArray of Wrong size");
        } else {
            System.out.println("unknown error");
        }
    }

    @Test
    public void testCheckResourceCovered() {
        Card[][] resultArray;
        ArrayList<ResourceType> tempListOfResources;
        PlayerScoreResource playerScoreCard = gamePlayerMap.getPlayerScoreCard();
        PlayerScoreResource checkPlayerScoreCard = null;
        int X = 40, Y = 40;
        resultArray = gamePlayerMap.getMapArray();
        resultArray[X + 1][Y] = firstTestCard;
        resultArray[X - 1][Y] = secondTestCard;
        resultArray[X][Y + 1] = thirdTestCard;
        resultArray[X][Y - 1] = fourthTestCard;
        playerScoreCard.addScore(ResourceType.FUNGI);
        playerScoreCard.addScore(ResourceType.INSECT);
        try {
            Method method = GamePlayerMap.class.getDeclaredMethod("checkResourceCovered", int.class, int.class);
            method.setAccessible(true);
            ResourceType result = (ResourceType) method.invoke(gamePlayerMap, X, Y);
            assert result == null : "failed";
            System.out.println("success");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
    }
    }

    @Test
    public void testPlaceCard(){
        Card[][] mapArray = gamePlayerMap.getMapArray();
        mapArray[]
    }
}