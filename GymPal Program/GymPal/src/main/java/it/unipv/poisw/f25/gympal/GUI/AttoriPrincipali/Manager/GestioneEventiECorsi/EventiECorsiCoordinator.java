package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.IEventiESessioniServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoCorsi.ICRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso.ISessionIdGenerator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.VistaEControllore.EventiECorsiController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.VistaEControllore.EventiECorsiView;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.VistaEControllore.IEventiECorsiView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.CommonInterfaces.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;

public class EventiECorsiCoordinator implements IEventiECorsiCoordinator{
	
	/*Controllore*/
	private IRegistraEMostraSchermate viewHandler;
	
	/*Servizi*/
	private ISessionIdGenerator generatoreIDs;
	private ICalendarioFacadeService calendarioService;
	private ICorsiCRUDFacadeService corsiCRUDService;
	private ICRUDCorsiSupportServices supportoCRUDCorsi;
	private IEventiCRUDFacadeService eventiCRUDService;
	private ICRUDEventiSupportServices supportoCRUDEventi;
	private IFontChangeRegister changeRegister;
	
    //----------------------------------------------------------------
	
	public EventiECorsiCoordinator(IRegistraEMostraSchermate viewHandler,
								   ISessionIdGenerator generatoreIDs,
								   ICalendarioFacadeService calendarioService,
								   IFontChangeRegister changeRegister,
								   IEventiESessioniServicesBundle eventiESessioniServices) {
		
		/*Controllore*/
		this.viewHandler = viewHandler;
		
		/*Servizi*/
		this.generatoreIDs = generatoreIDs;
		this.calendarioService = calendarioService;
		this.corsiCRUDService = eventiESessioniServices.getCorsiCRUD();
		this.supportoCRUDCorsi = eventiESessioniServices.getCorsiSupport();
		this.eventiCRUDService = eventiESessioniServices.getEventiCRUD();
		this.supportoCRUDEventi = eventiESessioniServices.getEventiSupport();
		this.changeRegister = changeRegister;
		
		/*Inizializzazione schermate GUI*/
		setupSchermataEventiECorsi();
		
	}
	
    //----------------------------------------------------------------
	
	private void setupSchermataEventiECorsi() {
		
		IEventiECorsiView eventiECorsi = new EventiECorsiView(new DynamicButtonSizeSetter(), changeRegister);
		viewHandler.registraSchermata("EVENTI_E_CORSI", eventiECorsi.getMainPanel());
		
		new EventiECorsiController(eventiECorsi, this);
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public ISessionIdGenerator esponiGeneratoreID() {
		
		return generatoreIDs;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public ICalendarioFacadeService getCalendarioService() {
		
	    return calendarioService;
	    
	}
	
    //----------------------------------------------------------------
	
	@Override
	public ICorsiCRUDFacadeService getCorsiCRUDService() {
		
		return corsiCRUDService;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public ICRUDCorsiSupportServices getCorsiCRUDSupportServices() {
		
		return supportoCRUDCorsi;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public IEventiCRUDFacadeService getEventiCRUDService() {
		
		return eventiCRUDService;
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public ICRUDEventiSupportServices getEventiCRUDSupportServices() {
		
		return supportoCRUDEventi;
		
	}
	
    //----------------------------------------------------------------

}
