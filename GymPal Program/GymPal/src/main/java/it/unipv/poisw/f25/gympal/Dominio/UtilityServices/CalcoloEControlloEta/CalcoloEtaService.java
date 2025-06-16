package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta;

import java.time.LocalDate;
import java.time.Period;

public class CalcoloEtaService implements ICalcoloEtaService {

	@Override
	public int calcolaEta(LocalDate dataNascita) {
		
		/*Calcola l'età del cliente in base alla data di nascita dello stesso.*/
		return Period.between(dataNascita, LocalDate.now()).getYears();
	    
	}
	
    //----------------------------------------------------------------
	
	@Override
    public boolean isMinorenne(LocalDate dataNascita) {
    	
		/*Restituisce 'true' o 'false' a seconda che l'età calcolata sia minore o
		 *maggiore di "18".*/
    	return Period.between(dataNascita, LocalDate.now()).getYears() < 18;
    	
    }
    
    //----------------------------------------------------------------
	
	
}
