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
    public static int maxPlayerPerLobby = 4;
    public static int minPlayerPerLobby = 2;
}
