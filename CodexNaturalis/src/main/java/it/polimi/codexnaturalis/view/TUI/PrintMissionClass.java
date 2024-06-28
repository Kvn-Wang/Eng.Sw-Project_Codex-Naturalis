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
    private static final String ANSI_RESET = "\033[0m";
    private static final String ANSI_BLUE = "\033[34m";

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
                BendMission bendMission = (BendMission) mission;
                printBendMission(bendMission);
                break;
            case MissionType.DIAGONAL:
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
            for(int i=0; i < mission.getNumberOfSymbols(); i++){
                symbol = symbol + " " + resources[0];
            }
            System.out.println(symbol);
        }else {
            for(int i=0; i<mission.getNumberOfSymbols(); i++){
                symbol = symbol + " " + resources[i];
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
                printSmallCard(mission.getPillarResource(), 0);
                printSmallCard(mission.getPillarResource(), 0);
                printSmallCard(mission.getDecorationResource(), 3);
            }
        if(mission.getDecorationPosition() == CardCorner.WEST){
            printSmallCard(mission.getPillarResource(), 3);
            printSmallCard(mission.getPillarResource(), 3);
            printSmallCard(mission.getDecorationResource(), 0);
        }
        if(mission.getDecorationPosition() == CardCorner.NORTH){
            printSmallCard(mission.getDecorationResource(), 0);
            printSmallCard(mission.getPillarResource(), 3);
            printSmallCard(mission.getPillarResource(), 3);
        }
        if(mission.getDecorationPosition() == CardCorner.EAST){
            printSmallCard(mission.getDecorationResource(), 3);
            printSmallCard(mission.getPillarResource(), 0);
            printSmallCard(mission.getPillarResource(), 0);
        }
        System.out.println("point per bend: " + mission.getPointPerCondition());
    }

    private static void printDiagonalMission(DiagonalMission mission) {
        System.out.println("Requirements for the diagonal mission:");
        String Requirements = PrintSymbols.convertResourceType(mission.getResourceType(), false);
        if(mission.getIsLeftToRight()){
            printSmallCard(mission.getResourceType(), 0);
            printSmallCard(mission.getResourceType(), 3);
            printSmallCard(mission.getResourceType(), 6);
        }else {
            printSmallCard(mission.getResourceType(), 6);
            printSmallCard(mission.getResourceType(), 3);
            printSmallCard(mission.getResourceType(), 0);

        }
        System.out.println("point per diagonal: " + mission.getPointPerCondition());
    }

    private static void printSmallCard(ResourceType resourceType, int space){
        String ANSI_COLOR = PrintSymbols.convertColor(resourceType);
        System.out.println( " ".repeat(space) + ANSI_COLOR + "╔════╗"+ANSI_RESET);
        System.out.println( " ".repeat(space) + ANSI_COLOR+  "║    ║"+ ANSI_RESET);
        System.out.println( " ".repeat(space) + ANSI_COLOR+  "║    ║"+ ANSI_RESET);
        System.out.println( " ".repeat(space) + ANSI_COLOR+  "║    ║"+ ANSI_RESET);
        System.out.println(" ".repeat(space) + ANSI_COLOR+   "╚════╝"+ANSI_RESET);
    }
}
