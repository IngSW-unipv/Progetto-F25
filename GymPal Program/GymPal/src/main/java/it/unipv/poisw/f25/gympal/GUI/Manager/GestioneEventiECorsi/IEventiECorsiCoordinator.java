package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneSessioniCorsi.ICRUDCorsiSupportServices;
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
