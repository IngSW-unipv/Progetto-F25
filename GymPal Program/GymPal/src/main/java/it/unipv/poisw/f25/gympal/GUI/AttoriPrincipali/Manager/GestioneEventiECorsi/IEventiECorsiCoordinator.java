package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoCorsi.ICRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso.ISessionIdGenerator;

public interface IEventiECorsiCoordinator {
	
	public ISessionIdGenerator esponiGeneratoreID();
	
	//----------------------------------------------------------------
	
	public ICalendarioFacadeService getCalendarioService();
	
	//----------------------------------------------------------------
	
	public ICorsiCRUDFacadeService getCorsiCRUDService();
	
	//----------------------------------------------------------------
	
	public ICRUDCorsiSupportServices getCorsiCRUDSupportServices();
	
	//----------------------------------------------------------------
	
	public IEventiCRUDFacadeService getEventiCRUDService();
	
	//----------------------------------------------------------------
	
	public ICRUDEventiSupportServices getEventiCRUDSupportServices();
	
	//----------------------------------------------------------------

}
