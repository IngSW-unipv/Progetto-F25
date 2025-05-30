package it.unipv.poisw.f25.gympal.utility;

public class KeysConversionUtility {
	
    public static String toPropertyKey(String nome) {
    	
    	//Sostituisce ogni spazio con un underscore
    	
        return nome.trim().replaceAll("\\s+", "_");
    }

}
