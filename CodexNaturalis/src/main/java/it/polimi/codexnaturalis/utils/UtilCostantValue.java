package it.polimi.codexnaturalis.utils;

public final class UtilCostantValue {
    public static String RMIServerName = "RMIServer_Codex";
    public static String ipAddressSocketServer = "127.0.0.1";
    public static int portRmiServer = 49500;
    public static int portSocketServer = 49501;
    public static int numeroMaxPlayer = 4;
    private static int numeroMaxCarteRisorsa = 40;
    private static int numeroMaxCarteObbiettivo = 40;
    public static int lunghezzaMaxMappa = (numeroMaxCarteRisorsa + numeroMaxCarteObbiettivo) * 2;
    public static String pathToResourceJson = "src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/resourceCardsFile.json";
    public static String pathToObjectiveJson = "src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/objectiveCardsFile.json";
    public static String pathToStarterJson = "src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/starterCardsFile.json";
    public static String pathToMissionJson = "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/missionFile.json";
    public static String pathToScoreCardImg = "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/graphics/PLATEAU-SCORE-IMP/Scoreboard.png";
    public static String pathToRedPawnImg = "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/graphics/Pawn_Red.png";
    public static String pathToYellowPawnImg = "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/graphics/Pawn_Yellow.png";
    public static String pathToBluePawnImg = "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/graphics/Pawn_Blue.png";
    public static String pathToGreenPawnImg = "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/graphics/Pawn_Green.png";
    public static int maxPlayerPerLobby = 4;
    public static int minPlayerPerLobby = 2;
    public static int timeoutSecGameStart = 30;
}
