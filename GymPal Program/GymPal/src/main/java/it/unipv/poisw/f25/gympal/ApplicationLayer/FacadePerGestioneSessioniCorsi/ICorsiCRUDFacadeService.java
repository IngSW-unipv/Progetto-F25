package it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneSessioniCorsi;

import java.time.LocalDate;

public interface ICorsiCRUDFacadeService {

    boolean creaSessione(String id, String staffId, LocalDate data, String fascia);
    
	//----------------------------------------------------------------
    
    boolean modificaSessione(String id, String nuovoStaffId, 
    						 LocalDate nuovaData, String nuovaFascia);
    
	//----------------------------------------------------------------
    
    boolean cancellaSessione(String id);
    
	//----------------------------------------------------------------
    
    boolean pulisciSessioniVecchie();
    
	//----------------------------------------------------------------
	
}
