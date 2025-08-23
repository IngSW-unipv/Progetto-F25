package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate;

import java.time.LocalDate;

public class DateRangeUtils implements IDateRangeUtils{
	
	@Override
    public LocalDate getInizioMeseCorrente() {
		
        return LocalDate.now().withDayOfMonth(1);
        
    }

    @Override
    public LocalDate getFineMeseCorrente() {
    	
        LocalDate start = getInizioMeseCorrente();
        return start.withDayOfMonth(start.lengthOfMonth());
        
    }

    //----------------------------------------------------------------
    
    @Override
    public LocalDate getInizioSettimanaCorrente() {
    	
        return LocalDate.now().with(java.time.DayOfWeek.MONDAY);
        
    }

    @Override
    public LocalDate getFineSettimanaCorrente() {
    	
        return LocalDate.now().with(java.time.DayOfWeek.SUNDAY);
        
    }
    
    //----------------------------------------------------------------

    @Override
    public LocalDate getOggi() {
    	
        return LocalDate.now();
        
    }

    @Override
    public LocalDate getDomani() {
    	
        return LocalDate.now().plusDays(1);
        
    }
    
    //----------------------------------------------------------------

}
