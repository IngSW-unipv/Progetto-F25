package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneOra;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class OraUtils implements IOraUtils{

    @Override
    public LocalTime parseOra(String oraStr) {
    	
        try {
        	
            return LocalTime.parse(oraStr);
            
        } catch (DateTimeParseException e) {
        	
            return null;
            
        }
        
    }
    
    //---------------------------------------------------------------

    @Override
    public boolean isRangeValido(LocalTime inizio, LocalTime fine) {
    	
        return inizio != null && fine != null && fine.isAfter(inizio);
        
    }
    
    //---------------------------------------------------------------
	
}
