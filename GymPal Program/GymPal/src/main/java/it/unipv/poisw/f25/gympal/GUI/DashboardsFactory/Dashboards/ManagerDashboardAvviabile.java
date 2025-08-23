package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.CalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneDipendenti.DipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneEventiGenerici.EventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneSessioniCorsi.CorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneTurni.TurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti.RetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneDipendenti.CRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneEventi.CRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneSessioniCorsi.CRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneSessioniCorsi.ICRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.CRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.FinestreDiDialogo.DialogUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso.DefaultSessionIdGenerator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso.ISessionIdGenerator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate.DateRangeUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.DateUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidatoreFasciaOraria.FasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneEvento.EventoValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneOra.OraUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneSessioneCorso.SessioneCorsoValidator;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.Manager.ManagerController;
import it.unipv.poisw.f25.gympal.GUI.Manager.ManagerDashboardView;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class ManagerDashboardAvviabile implements IDashboardAvviabile{
	
	public ManagerDashboardAvviabile() {}
	
    //----------------------------------------------------------------
	
	@Override
	public void avvia() {
		
		/*Facciata di connessione a servizi DB*/
		IPersistenceFacade persistence = PersistenceFacade.getInstance();
		
		/*Servizi*/
		ICommonServicesBundle serviziComuni = new CommonServicesBundle();
		
        IRetrieveDipendentiFromDB listaDip = new RetrieveDipendentiFromDB(persistence);
		ISessionIdGenerator generatoreIDs = new DefaultSessionIdGenerator();
		
		/*Servizi per calendario*/
        ICalendarioService calendarioService = new CalendarioService();
        ICalendarioFacadeService calendarioFacade = new CalendarioFacadeService(
        											   calendarioService,
        											   listaDip);
        
        /*Servizi Create-Read-Update-Delete per sessioni corsi*/
        ICorsiCRUDFacadeService corsiCRUDService = new CorsiCRUDFacadeService(
        											   calendarioService);        
        ICRUDCorsiSupportServices supportBundle = new CRUDCorsiSupportServices(
									                  new FasciaOrariaValidator(),
									                  new SessioneCorsoValidator(),
									                  new DateUtils(),
									                  new DialogUtils());
        
        /*Servizi Create-Read-Update-Delete per eventi generici*/
        IEventiCRUDFacadeService eventiCRUDService = new EventiCRUDFacadeService(
        												 calendarioService);
        
        ICRUDEventiSupportServices supportCRUDEventi = new CRUDEventiSupportServices(
        											   new EventoValidator(),
        											   new DialogUtils(),
        											   new DateUtils(),
        											   new FasciaOrariaValidator(),
        											   new OraUtils(),
        											   new DateRangeUtils());
        
        /*Servizi CRUD per turni*/
        ITurniCRUDFacadeService turniCRUDService = new TurniCRUDFacadeService(calendarioService);
        ICRUDTurniSupportServices supportoCRUDTurni = new CRUDTurniSupportServices(
        											  new DateUtils(),
        											  new DialogUtils(),
        											  new DateRangeUtils(),
        											  listaDip);
        
        /*Servizi CRUD per dipendenti*/
        IDipendentiCRUDFacadeService dipCRUDService = new DipendentiCRUDFacadeService(persistence);
        ICRUDDipendentiSupportServices supportpCRUDdip = new CRUDDipendentiSupportServices(new DialogUtils());
		
        /*Istanziazione controllore e relativa vista*/
		ManagerDashboardView manDashView = new ManagerDashboardView();
		new ManagerController(manDashView, serviziComuni, 
							  generatoreIDs, calendarioFacade, 
							  corsiCRUDService,
							  supportBundle,
							  eventiCRUDService,
							  supportCRUDEventi,
							  turniCRUDService,
							  supportoCRUDTurni,
							  dipCRUDService,
							  supportpCRUDdip);
		manDashView.setVisible(true);
		
		
	}
	
    //----------------------------------------------------------------

}
