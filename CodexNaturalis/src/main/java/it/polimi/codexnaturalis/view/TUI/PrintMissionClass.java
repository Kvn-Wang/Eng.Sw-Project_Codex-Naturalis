package it.polimi.codexnaturalis.view.TUI;

import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.MissionType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.mission.BendMission;
import it.polimi.codexnaturalis.model.mission.DiagonalMission;
import it.polimi.codexnaturalis.model.mission.Mission;
import it.polimi.codexnaturalis.model.mission.ResourceMission;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class PrintMissionClass {

    public static void main(String[] args) {
        DiagonalMission testDiagonalMission = new DiagonalMission(87, 2 , false, ResourceType.INSECT);
        DiagonalMission testDiagonalMission2 = new DiagonalMission(87, 2 , true, ResourceType.INSECT);
        BendMission testBendMission = new BendMission(91,3,ResourceType.FUNGI,ResourceType.ANIMAL, CardCorner.SOUTH);
        BendMission testBendMission1 = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT, CardCorner.NORTH);
        BendMission testBendMission2 = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT, CardCorner.EAST);
        BendMission testBendMission3 = new BendMission(91,3,ResourceType.FUNGI,ResourceType.PLANT, CardCorner.WEST);
        ResourceMission testResourceMission = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.FUNGI});
        ResourceMission testResourceMission1 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.PLANT});
        ResourceMission testResourceMission2 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.ANIMAL});
        ResourceMission testResourceMission3 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.INSECT});
        ResourceMission testResourceMission4 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.INKWELL, ResourceType.QUILL, ResourceType.MANUSCRIPT});
        ResourceMission testResourceMission5 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.QUILL});
        ResourceMission testResourceMission6 = new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.INKWELL});
        ResourceMission testResourceMission7= new ResourceMission(95, 2,3,new ResourceType[] {ResourceType.MANUSCRIPT});
        printMission(testDiagonalMission);
        printMission(testDiagonalMission2);
        printMission(testBendMission);
        printMission(testBendMission1);
        printMission(testBendMission2);
        printMission(testBendMission3);
        printMission(testResourceMission);
        printMission(testResourceMission1);
        printMission(testResourceMission2);
        printMission(testResourceMission3);
        printMission(testResourceMission4);
        printMission(testResourceMission5);
        printMission(testResourceMission6);
        printMission(testResourceMission7);
    }

    public static void printMission(Mission mission) {
        switch (mission.getMissionType()){
            case MissionType.RESOURCE:
                ResourceMission resourceMission = (ResourceMission) mission;
                printResourceMission(resourceMission);
                break;
            case MissionType.BEND:
                System.out.println("x");
                BendMission bendMission = (BendMission) mission;
                printBendMission(bendMission);
                break;
            case MissionType.DIAGONAL:
                System.out.println("y");
                DiagonalMission diagMission = (DiagonalMission) mission;
                printDiagonalMission(diagMission);
                break;
        }
    }
    private static void printResourceMission(ResourceMission mission) {
        System.out.println("Requirements for the resource mission:");
        String resources[] = PrintSymbols.convertMultipleResourceType(mission.getTypeOfResource(), true);
        String symbol = "";
        if(resources.length == 1){
            for(int i=0; i<mission.getNumberOfSymbols(); i++){
                symbol += resources[0];
            }
            System.out.println(symbol);
        }else {
            for(int i=0; i<mission.getNumberOfSymbols(); i++){
                symbol += resources[i];
            }
            System.out.println(symbol);
        }
        System.out.println("point per collection: " + mission.getPointPerCondition());

    }

    private static void printBendMission(BendMission mission) {
        System.out.println("Requirements for the bend mission:");
            String pillarRequirements = PrintSymbols.convertResourceType(mission.getPillarResource(), false);
            String decorationRequirements = PrintSymbols.convertResourceType(mission.getDecorationResource(), false);
            if(mission.getDecorationPosition() == CardCorner.SOUTH){
                System.out.println(pillarRequirements);
                System.out.println(pillarRequirements);
                System.out.println("  " + decorationRequirements);
            }
        if(mission.getDecorationPosition() == CardCorner.WEST){
            System.out.println("  " + pillarRequirements);
            System.out.println("  " + pillarRequirements);
            System.out.println(decorationRequirements);
        }
        if(mission.getDecorationPosition() == CardCorner.NORTH){
            System.out.println(decorationRequirements);
            System.out.println("  " + pillarRequirements);
            System.out.println("  " + pillarRequirements);
        }
        if(mission.getDecorationPosition() == CardCorner.EAST){
            System.out.println("  " + decorationRequirements);
            System.out.println(pillarRequirements);
            System.out.println(pillarRequirements);
        }
        System.out.println("point per bend: " + mission.getPointPerCondition());
    }

    private static void printDiagonalMission(DiagonalMission mission) {
        System.out.println("Requirements for the diagonal mission:");
        String Requirements = PrintSymbols.convertResourceType(mission.getResourceType(), false);
        if(mission.getIsLeftToRight()){
            System.out.println(Requirements);
            System.out.println("  " + Requirements);
            System.out.println("    " + Requirements);
        }else {
            System.out.println("    " + Requirements);
            System.out.println("  " + Requirements);
            System.out.println(Requirements);

        }
        System.out.println("point per diagonal: " + mission.getPointPerCondition());
    }
}
