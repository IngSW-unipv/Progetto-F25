package it.unipv.poisw.f25.gympal.StrategieDiPagamento.StrategyUtilities;

public class KeysConversionUtility {
	
    public static String toPropertyKey(String nomeProprieta) {
    	
    	//Sostituisce ogni spazio con un underscore
    	
        return nomeProprieta.trim().replaceAll("\\s+", "_");
    }

}
