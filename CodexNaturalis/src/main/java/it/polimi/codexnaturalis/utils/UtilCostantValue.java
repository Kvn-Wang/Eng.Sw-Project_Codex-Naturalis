package it.polimi.codexnaturalis.utils;

public final class UtilCostantValue {
    public static int numeroMaxPlayer = 4;
    private static int numeroMaxCarteRisorsa = 40;
    private static int numeroMaxCarteObbiettivo = 40;
    public static int lunghezzaMaxMappa;

    private UtilCostantValue() {
        lunghezzaMaxMappa = (numeroMaxCarteRisorsa + numeroMaxCarteObbiettivo) * 2;
    }
}
