package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneSessioniCorsi.ICRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso.ISessionIdGenerator;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.EventiECorsiController;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.EventiECorsiView;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.IEventiECorsiView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;

public class EventiECorsiCoordinator implements IEventiECorsiCoordinator{
	
	/*Controllore*/
	private IRegistraEMostraSchermate viewHandler;
	
	/*Viste*/
	private IEventiECorsiView eventiECorsi;
	
	/*Servizi*/
	private ISessionIdGenerator generatoreIDs;
	private ICalendarioFacadeService calendarioService;
	private ICorsiCRUDFacadeService corsiCRUDService;
	private ICRUDCorsiSupportServices supportoCRUDCorsi;
	private IEventiCRUDFacadeService eventiCRUDService;
	private ICRUDEventiSupportServices supportoCRUDeventi;
	
    //----------------------------------------------------------------
	
	public EventiECorsiCoordinator(IRegistraEMostraSchermate viewHandler,
								   ISessionIdGenerator generatoreIDs,
								   ICalendarioFacadeService calendarioService,
								   ICorsiCRUDFacadeService corsiCRUDService,
								   ICRUDCorsiSupportServices supportoCRUDCorsi,
								   IEventiCRUDFacadeService eventiCRUDService,
								   ICRUDEventiSupportServices supportoCRUDeventi) {
		
		/*Controllore*/
		this.viewHandler = viewHandler;
		
		/*Servizi*/
		this.generatoreIDs = generatoreIDs;
		this.calendarioService = calendarioService;
		this.corsiCRUDService = corsiCRUDService;
		this.supportoCRUDCorsi = supportoCRUDCorsi;
		this.eventiCRUDService = eventiCRUDService;
		this.supportoCRUDeventi = supportoCRUDeventi;
		
		/*Inizializzazione schermate GUI*/
		setupSchermataEventiECorsi();
		
	}
	
    //----------------------------------------------------------------
	
	private void setupSchermataEventiECorsi() {
		
		eventiECorsi = new EventiECorsiView(new DynamicButtonSizeSetter());
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
		
		return supportoCRUDeventi;
		
	}
	
    //----------------------------------------------------------------

}
