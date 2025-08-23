package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate;

import java.time.LocalDate;

public interface IDateRangeUtils {

    LocalDate getInizioMeseCorrente();
    LocalDate getFineMeseCorrente();
    
    //----------------------------------------------------------------

    LocalDate getInizioSettimanaCorrente();
    LocalDate getFineSettimanaCorrente();
    
    //----------------------------------------------------------------

    LocalDate getOggi();
    LocalDate getDomani();
    
    //----------------------------------------------------------------
	
}
