package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneDipendenti;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class DipendentiCRUDFacadeService implements IDipendentiCRUDFacadeService {
	
	private IPersistenceFacade persistence;
	
    //------------------------------------------------------------

    public DipendentiCRUDFacadeService(IPersistenceFacade persistence) {
    	
        this.persistence = persistence;
        
    }
    
    //------------------------------------------------------------

    @Override
    public List<Dipendente> getAllDipendenti() {
    	
        return persistence.selectAllDipendenti();
        
    }
    
    //------------------------------------------------------------

    @Override
    public Dipendente getDipendenteByCF(Dipendente dipendente) {
    	
        return persistence.selectDipendente(dipendente);
        
    }
    
    //------------------------------------------------------------

    @Override
    public boolean creaDipendente(Dipendente dipendente) {
    	
        return persistence.insertDipendente(dipendente);
        
    }
    
    //------------------------------------------------------------

    @Override
    public boolean aggiornaDipendente(Dipendente dipendente) {
    	
        return persistence.updateDipendente(dipendente);
        
    }
    
    //------------------------------------------------------------

    @Override
    public boolean cancellaDipendente(Dipendente dipendente) {
    	
        return persistence.deleteDipendente(dipendente);
        
    }
    
    //------------------------------------------------------------

}
