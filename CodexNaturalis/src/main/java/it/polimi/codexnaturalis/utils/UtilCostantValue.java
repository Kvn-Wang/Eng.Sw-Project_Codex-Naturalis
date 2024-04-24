package it.polimi.codexnaturalis.utils;

import it.polimi.codexnaturalis.model.shop.card.ResourceCard;

public final class UtilCostantValue {
    public static int numeroMaxPlayer = 4;
    private static int numeroMaxCarteRisorsa = 40;
    private static int numeroMaxCarteObbiettivo = 40;
    public static int lunghezzaMaxMappa = (numeroMaxCarteRisorsa + numeroMaxCarteObbiettivo) * 2;
    public static String pathToResourceJson = "src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/resourceCardsFile.json";
    public static String pathToObjectiveJson = "src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/objectiveCardsFile.json";
    public static String pathToStarterJson = "src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/starterCardsFile.json";
    public static String pathToMissionJson = "src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/missionFile.json";
    public static String pathToScoreCardImg = "src/main/resources/it/polimi/codexnaturalis/graphics/PLATEAU-SCORE-IMP/Scoreboard.png";
    public static String pathToRedPawnImg = "src/main/resources/it/polimi/codexnaturalis/graphics/Pawn_Red.png";
    public static String pathToYellowPawnImg = "src/main/resources/it/polimi/codexnaturalis/graphics/Pawn_Yellow.png";
    public static String pathToBluePawnImg = "src/main/resources/it/polimi/codexnaturalis/graphics/Pawn_Blue.png";
    public static String pathToGreenPawnImg = "src/main/resources/it/polimi/codexnaturalis/graphics/Pawn_Green.png";
    public static int maxPlayerPerLobby = 4;
    public static int minPlayerPerLobby = 2;
    public static int timeoutSecGameStart = 30;
}
