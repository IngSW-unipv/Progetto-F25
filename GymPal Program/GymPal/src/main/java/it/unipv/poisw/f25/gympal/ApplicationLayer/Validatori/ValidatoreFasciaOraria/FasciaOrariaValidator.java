package it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class FasciaOrariaValidator implements IFasciaOrariaValidator{

    private static final LocalTime ORA_MIN = LocalTime.of(8, 0);
    private static final LocalTime ORA_MAX = LocalTime.of(20, 30);
    
	//----------------------------------------------------------------

    @Override
    public boolean isValid(String fascia) {
    	
        if (!fascia.matches("\\d{2}:\\d{2}-\\d{2}:\\d{2}")) {return false;}

        try {
        	
            String[] orari = fascia.split("-");
            LocalTime inizio = LocalTime.parse(orari[0]);
            LocalTime fine = LocalTime.parse(orari[1]);

            return !inizio.isBefore(ORA_MIN) &&
                   !fine.isAfter(ORA_MAX) &&
                    fine.isAfter(inizio);
            
        } catch (DateTimeParseException e) {
        	
            return false;
        }
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public String getErrore(String fascia) {
    	
        if (!fascia.matches("\\d{2}:\\d{2}-\\d{2}:\\d{2}")) {
        	
            return "Formato fascia oraria non valido. Usa HH:mm-HH:mm, "
            	 + "es. 18:00-19:00.";
            
        }

        try {
        	
            String[] orari = fascia.split("-");
            LocalTime inizio = LocalTime.parse(orari[0]);
            LocalTime fine = LocalTime.parse(orari[1]);

            if (inizio.isBefore(ORA_MIN) || fine.isAfter(ORA_MAX)) {
            	
                return "Orari fuori dal range consentito (08:00 - 20:30).";
                
            }

            if (!fine.isAfter(inizio)) {
            	
                return "L'orario di fine deve essere successivo a quello di inizio.";
                
            }

        } catch (DateTimeParseException e) {
        	
            return "Orari non validi. Usa il formato HH:mm.";
            
        }

        return null;
        
    }
    
	//----------------------------------------------------------------
	
}
