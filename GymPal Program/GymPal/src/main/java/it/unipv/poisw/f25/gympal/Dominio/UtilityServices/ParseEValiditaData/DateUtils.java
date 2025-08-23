package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils implements IDateUtils{

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	//----------------------------------------------------------------
	
	@Override
    public LocalDate parseData(String dataStr) {
    	
        try {
        	
            return LocalDate.parse(dataStr, FORMATTER);
            
        } catch (DateTimeParseException e) {
        	
            return null;
            
        }
        
    }
    
	//----------------------------------------------------------------

	@Override
    public boolean isRangeValido(LocalDate inizio, LocalDate fine) {
    	
        return fine == null || inizio == null || !fine.isBefore(inizio);
        
    }
    
	//----------------------------------------------------------------
    
}

