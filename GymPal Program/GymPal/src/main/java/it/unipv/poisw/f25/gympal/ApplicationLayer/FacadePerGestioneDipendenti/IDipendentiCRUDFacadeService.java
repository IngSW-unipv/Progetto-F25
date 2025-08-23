package it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneDipendenti;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public interface IDipendentiCRUDFacadeService {

    public List<Dipendente> getAllDipendenti();
    
    //------------------------------------------------------------
    
    public Dipendente getDipendenteByCF(Dipendente dipendente);
    
    //------------------------------------------------------------
    
    public boolean creaDipendente(Dipendente dipendente);
    
    //------------------------------------------------------------
    
    public boolean aggiornaDipendente(Dipendente dipendente);
    
    //------------------------------------------------------------
    
    public boolean cancellaDipendente(Dipendente dipendente);
    
    //------------------------------------------------------------
	
}
