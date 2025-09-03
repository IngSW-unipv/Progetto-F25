package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.RetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.CalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.EventiESessioniServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.IEventiESessioniServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.ITurniEDipendentiServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.TurniEDipendentiServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.CommonServicesBundleFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ICommonServicesBundleFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso.ISessionIdGenerator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso.SessionIdGenerator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.ManagerController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.ManagerDashboardView;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class ManagerDashboardAvviabile implements IDashboardAvviabile{
	
	public ManagerDashboardAvviabile() {}
	
    //----------------------------------------------------------------
	
	 @Override
	    public void avvia() {
		 	
		 	/*Facciata strato persistenza*/
	        IPersistenceFacade persistence = PersistenceFacade.getInstance();
	        
	        /*Recupero contenuto tabella Dipendenti*/
	        IRetrieveDipendentiFromDB listaDip = new RetrieveDipendentiFromDB(persistence);
	        
	        /*Generatore ID per sessioni corsi*/
	        ISessionIdGenerator generatoreIDs = new SessionIdGenerator();
	        
	        /*Servizi per visualizzazione/popolazione calendario 
	         *sessioni/eventi/appuntamenti/ecc.*/
	        ICalendarioService calendarioService = new CalendarioService();
	        ICalendarioFacadeService calendarioFacade = new CalendarioFacadeService(calendarioService, listaDip);

	        /*Servizi Comuni*/
	        ICommonServicesBundleFactory factory = new CommonServicesBundleFactory(persistence, calendarioFacade);
	        ICommonServicesBundle serviziComuni = factory.buildBundle();
	        
	        /*Servizi per responasbilità manageriali*/
	        ITurniEDipendentiServicesBundle turniDipServices = new TurniEDipendentiServicesBundle(
	                calendarioService,
	                persistence
	        );
	        
	        IEventiESessioniServicesBundle eventiESessioniServices = new EventiESessioniServicesBundle(calendarioService);


	        /*Vista e controllore per Manager*/
	        ManagerDashboardView manDashView = new ManagerDashboardView(serviziComuni.getFontManager());
	        new ManagerController(manDashView,
	                			  serviziComuni,
	                			  generatoreIDs,
	                			  calendarioFacade,
	                			  turniDipServices,
	                			  eventiESessioniServices
	        );
	        
	        manDashView.setVisible(true);
	    }
	 
      //----------------------------------------------------------------
	 
}
