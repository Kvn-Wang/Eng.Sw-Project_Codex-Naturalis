package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.MissionType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MissionTest {
    BendMission testBendMission = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT, CardCorner.SOUTH);
    @BeforeEach
    void setUp() {
        Mission mission = new Mission() {
            @Override
            public int ruleAlgorithmCheck(Player player) {
                return 0;
            }

            @Override
            public MissionType getMissionType() {
                return null;
            }
        };
    }
    @Test
    public void testMission(){
        DiagonalMission testDiagonalMission = new DiagonalMission(87, 2 , false, ResourceType.FUNGI);
        assertEquals(87,testDiagonalMission.getPngNumber());
        assertEquals(2,testDiagonalMission.getPointPerCondition());
        assertEquals(false,testDiagonalMission.getIsLeftToRight());
        assertEquals(ResourceType.FUNGI,testDiagonalMission.getResourceType());
        ResourceMission testResourceMission = new ResourceMission(95,2,3,new ResourceType[] {ResourceType.FUNGI});
        assertEquals(95,testResourceMission.getPngNumber());
        assertEquals(2,testResourceMission.getPointPerCondition());
        assertEquals(3,testResourceMission.getNumberOfSymbols());
        ResourceType[] check =new ResourceType[] {ResourceType.FUNGI};
        assertEquals(true, Arrays.equals(check,testResourceMission.getTypeOfResource()));
        BendMission testBendMission = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT,CardCorner.SOUTH);
        assertEquals(91,testBendMission.getPngNumber());
        assertEquals(3,testBendMission.getPointPerCondition());
        assertEquals(ResourceType.FUNGI, testBendMission.getPillarResource());
        assertEquals(ResourceType.PLANT, testBendMission.getDecorationResource());
        assertEquals(CardCorner.SOUTH,testBendMission.getDecorationPosition());
    }

    @Test
    public void testMisssion(){
    }
}