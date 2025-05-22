package it.unipv.poisw.f25.gympal.utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class EtaCliente {

	public static int calcolaEta(Date dataNascita) {
		
	    LocalDate birthDate = dataNascita.toInstant().atZone(ZoneId.systemDefault())
	    								 			 .toLocalDate();
	    
	    return Period.between(birthDate, LocalDate.now()).getYears();
	    
	}
	
	
}
