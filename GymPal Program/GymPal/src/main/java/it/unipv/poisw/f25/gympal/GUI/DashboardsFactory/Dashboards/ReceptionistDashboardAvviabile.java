package it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.RetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.CalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.EstraiTurniDipendenteService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.GestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.IEstraiTurniDipendenteService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.IGestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.CommonServicesBundleFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ICommonServicesBundleFactory;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.DialogUtils;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.FasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.IRegistrationServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.RegistrationServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.DateUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.Dominio.staff.Receptionist;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.Dashboards.CommonInterface.IDashboardAvviabile;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;

public class ReceptionistDashboardAvviabile implements IDashboardAvviabile{
	
	
	private Receptionist receptionist;
	
    //----------------------------------------------------------------
	
    public ReceptionistDashboardAvviabile(Receptionist receptionist) {
    	
    	this.receptionist = receptionist;
    	
    }
    
    //----------------------------------------------------------------

    @Override
    public void avvia() {
        
        /*Servizi DAO*/
        IPersistenceFacade persistence = PersistenceFacade.getInstance();
        
        /*Servizi per Registrazione clienti*/
        IRegistrationServicesBundle serviziReg = new RegistrationServicesBundle();
        
        /*Servizi per Calendario*/
        ICalendarioService calendarioService = new CalendarioService();
        IRetrieveDipendentiFromDB listaDip = new RetrieveDipendentiFromDB(persistence);
        ICalendarioFacadeService calendarioFacade = new CalendarioFacadeService(
        											   calendarioService,
        											   listaDip);
        
        /*Servizi comuni a più parti della GUI*/
        ICommonServicesBundleFactory factory = new CommonServicesBundleFactory(persistence, calendarioFacade);
        ICommonServicesBundle serviziComuni = factory.create();
        
        /*Servizi Validazione Date ed Ore*/
        IDateUtils dateUtils = new DateUtils();
        IFasciaOrariaValidator fasciaValidator = new FasciaOrariaValidator();
        
        /*Servizi Visualizzazione Dialoghi*/
        IDialogUtils dialogUtils = new DialogUtils();
        
        /*Servizi per estrazione turni singolo dipendente*/
        IEstraiTurniDipendenteService estraiTurni = new EstraiTurniDipendenteService();
        IGestoreTurniPersonali gestoreTurni = new GestoreTurniPersonali(estraiTurni);
        
        /*Istanziazione controllore e relativa vista*/
        ReceptionistDashboardView recDashView = new ReceptionistDashboardView(serviziComuni.getFontManager());
        new ReceptionistController(recDashView, 
        						   receptionist,
        						   serviziReg, 
        						   serviziComuni, 
        						   calendarioFacade,
        						   gestoreTurni,
        						   fasciaValidator,
        						   dateUtils,
        						   dialogUtils);
        
        recDashView.setVisible(true);
        
    }
    
    //----------------------------------------------------------------

}
