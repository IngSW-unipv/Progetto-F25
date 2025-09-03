package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneSessioniCorsi;

import java.time.LocalDate;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;

public class CorsiCRUDFacadeService implements ICorsiCRUDFacadeService {
	
    private ICalendarioService calendarioService;
    
	//----------------------------------------------------------------
    
    public CorsiCRUDFacadeService(ICalendarioService calendarioService) {
    	
    	this.calendarioService = calendarioService;
    	
    }
    
	//----------------------------------------------------------------
    
    @Override
    public boolean creaSessione(String id, String staffId, LocalDate data, 
    							String fascia) {
    	
        return calendarioService.creaSessioneCorso(id, staffId, data, fascia);
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public boolean modificaSessione(String id, String nuovoStaffId, 
    							    LocalDate nuovaData, String nuovaFascia) {
    	
        return calendarioService.modificaSessioneCorso(id, nuovoStaffId, 
        											   nuovaData, nuovaFascia);
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public boolean cancellaSessione(String id) {
    	
        return calendarioService.cancellaSessioneCorso(id);
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public boolean pulisciSessioniVecchie() {

    	/* Sono prima annullate tutte le partecipazini ai corsi interessati, poi
    	 * sono eliminati i corsi stessi*/
    	
    	calendarioService.pulisciPrenotazioniVecchie();    	
        return calendarioService.pulisciSessioniVecchie();
        
    }
    
	//----------------------------------------------------------------

}
