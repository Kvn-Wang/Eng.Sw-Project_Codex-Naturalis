package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceMissionTest {
    ResourceMission testResourceMission = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.FUNGI});
    ResourceMission testResourceMission1 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.PLANT});
    ResourceMission testResourceMission2 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.ANIMAL});
    ResourceMission testResourceMission3 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.INSECT});
    ResourceMission testResourceMission4 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.MANUSCRIPT, ResourceType.QUILL, ResourceType.MANUSCRIPT});
    ResourceMission testResourceMission5 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.QUILL});
    ResourceMission testResourceMission6 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.INKWELL});
    ResourceMission testResourceMission7= new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.MANUSCRIPT});

    StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);

    Player testPlayer1 = new Player("nick", ColorType.RED);
    Player testPlayer2 = new Player("nack", ColorType.BLUE);
    Player testPlayer3 = new Player("nock", ColorType.YELLOW);
    Player testPlayer4 = new Player("nuck", ColorType.GREEN);
    ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    ResourceCard secondTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.PLANT, 0);

    int middle = UtilCostantValue.lunghezzaMaxMappa/2;
    @Test
    public void testResourceMission(){
        PlayerScoreResource testscore1 = testPlayer1.getScoreResource();
        for(int i=0; i<6; i++){
            testscore1.addScore(ResourceType.FUNGI);
            if(i==2){
                assertEquals(2,testResourceMission.ruleAlgorithmCheck(testPlayer1));
            }
            if(i==5){
                assertEquals(4,testResourceMission.ruleAlgorithmCheck(testPlayer1));
            }
        }
        for(int i=0; i<6; i++){
            testscore1.addScore(ResourceType.PLANT);
            if(i==2){
                assertEquals(2,testResourceMission1.ruleAlgorithmCheck(testPlayer1));
            }
            if(i==5){
                assertEquals(4,testResourceMission1.ruleAlgorithmCheck(testPlayer1));
            }
        }
        for(int i=0; i<6; i++){
            testscore1.addScore(ResourceType.ANIMAL);
            if(i==2){
                assertEquals(2,testResourceMission2.ruleAlgorithmCheck(testPlayer1));
            }
            if(i==5){
                assertEquals(4,testResourceMission2.ruleAlgorithmCheck(testPlayer1));
            }
        }
        for(int i=0; i<6; i++){
            testscore1.addScore(ResourceType.INSECT);
            if(i==2){
                assertEquals(2,testResourceMission3.ruleAlgorithmCheck(testPlayer1));
            }
            if(i==5){
                assertEquals(4,testResourceMission3.ruleAlgorithmCheck(testPlayer1));
            }
        }
        for(int i=0; i<6; i++){
            testscore1.addScore(ResourceType.QUILL);
            if(i==2){
                assertEquals(2,testResourceMission5.ruleAlgorithmCheck(testPlayer1));
            }
            if(i==5){
                assertEquals(4,testResourceMission5.ruleAlgorithmCheck(testPlayer1));
            }
        }
        for(int i=0; i<6; i++){
            testscore1.addScore(ResourceType.INKWELL);
            if(i==2){
                assertEquals(2,testResourceMission6.ruleAlgorithmCheck(testPlayer1));
            }
            if(i==5){
                assertEquals(4,testResourceMission6.ruleAlgorithmCheck(testPlayer1));
            }
        }
        for(int i=0; i<6; i++){
            testscore1.addScore(ResourceType.MANUSCRIPT);
            if(i==2){
                assertEquals(2,testResourceMission7.ruleAlgorithmCheck(testPlayer1));
                assertEquals(2,testResourceMission4.ruleAlgorithmCheck(testPlayer1));
            }
            if(i==5){
                assertEquals(4,testResourceMission7.ruleAlgorithmCheck(testPlayer1));
            }
        }
        assertEquals(4,testResourceMission4.ruleAlgorithmCheck(testPlayer1));
    }
}