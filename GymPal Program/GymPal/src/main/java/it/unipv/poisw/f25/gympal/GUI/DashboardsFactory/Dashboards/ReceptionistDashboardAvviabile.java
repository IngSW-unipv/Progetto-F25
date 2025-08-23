package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.CalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti.RetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.RegistrationServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente.EstraiTurniDipendenteService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente.GestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente.IEstraiTurniDipendenteService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente.IGestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.DateUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;
import it.unipv.poisw.f25.gympal.staff.Receptionist;

public class ReceptionistDashboardAvviabile implements IDashboardAvviabile{
	
	
	private Receptionist receptionist;
	
    //----------------------------------------------------------------
	
    public ReceptionistDashboardAvviabile(Receptionist receptionist) {
    	
    	this.receptionist = receptionist;
    	
    }
    
    //----------------------------------------------------------------

    @Override
    public void avvia() {
        
        /*Servizi*/
        IPersistenceFacade persistence = PersistenceFacade.getInstance();
        
        /*Servizi per Registrazione clienti*/
        RegistrationServicesBundle serviziReg = new RegistrationServicesBundle();
        
        /*Servizi comuni a più parti della GUI*/
        ICommonServicesBundle serviziComuni = new CommonServicesBundle();
        
        /*Servizi per Calendario*/
        ICalendarioService calendarioService = new CalendarioService();
        IRetrieveDipendentiFromDB listaDip = new RetrieveDipendentiFromDB(persistence);
        ICalendarioFacadeService calendarioFacade = new CalendarioFacadeService(
        											   calendarioService,
        											   listaDip);
        
        /*Servizi Validazione Date*/
        IDateUtils dateUtils = new DateUtils();
        
        /*Servizi per estrazione turni singolo dipendente*/
        IEstraiTurniDipendenteService estraiTurni = new EstraiTurniDipendenteService();
        IGestoreTurniPersonali gestoreTurni = new GestoreTurniPersonali(estraiTurni);
        
        /*Istanziazione controllore e relativa vista*/
        ReceptionistDashboardView recDashView = new ReceptionistDashboardView();
        new ReceptionistController(recDashView, 
        						   receptionist,
        						   serviziReg, 
        						   serviziComuni, 
        						   calendarioFacade,
        						   dateUtils,
        						   gestoreTurni);
        
        recDashView.setVisible(true);
        
    }
    
    //----------------------------------------------------------------

}
