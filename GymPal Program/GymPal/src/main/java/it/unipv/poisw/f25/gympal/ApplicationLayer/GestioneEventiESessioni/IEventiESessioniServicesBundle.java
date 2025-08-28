package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoCorsi.ICRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoEventi.ICRUDEventiSupportServices;

public interface IEventiESessioniServicesBundle {
	
    public IEventiCRUDFacadeService getEventiCRUD();
    
	//----------------------------------------------------------------
    
    public ICRUDEventiSupportServices getEventiSupport();
    
	//----------------------------------------------------------------
    
    public ICorsiCRUDFacadeService getCorsiCRUD();
    
	//----------------------------------------------------------------
    
    public ICRUDCorsiSupportServices getCorsiSupport();
    
	//----------------------------------------------------------------

}
