package it.polimi.codexnaturalis.utils;

public final class UtilCostantValue {
    public static int numeroMaxPlayer = 4;
    private static int numeroMaxCarteRisorsa = 40;
    private static int numeroMaxCarteObbiettivo = 40;
    public static int lunghezzaMaxMappa = (numeroMaxCarteRisorsa + numeroMaxCarteObbiettivo) * 2;
    public static String pathToResourceJson = "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/resourceCardsFile.json";
    public static String pathToObjectiveJson = "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/objectiveCardsFile.json";
}
