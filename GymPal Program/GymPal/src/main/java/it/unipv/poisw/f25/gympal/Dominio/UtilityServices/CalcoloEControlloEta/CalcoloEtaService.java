package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta;

import java.time.LocalDate;
import java.time.Period;

public class CalcoloEtaService implements ICalcoloEtaService {

	@Override
	public int calcolaEta(LocalDate dataNascita) {
		
		return Period.between(dataNascita, LocalDate.now()).getYears();
	    
	}
	
    //----------------------------------------------------------------
	
	@Override
    public boolean isMinorenne(LocalDate dataNascita) {
    	
    	return Period.between(dataNascita, LocalDate.now()).getYears() < 18;
    	
    }
    
    //----------------------------------------------------------------
	
	
}
