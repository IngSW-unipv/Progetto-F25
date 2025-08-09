package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneCalendario;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;

public class CalendarServicesBundle {
	
	private ICalendarioService calendarizzazione;
	
	//----------------------------------------------------------------
	
	public CalendarServicesBundle() {

		calendarizzazione = new CalendarioService();
		
	}
	
	//----------------------------------------------------------------
	
	public ICalendarioService getCalendarioService() {
		
		return calendarizzazione;
		
	}
	
	//----------------------------------------------------------------	

}
