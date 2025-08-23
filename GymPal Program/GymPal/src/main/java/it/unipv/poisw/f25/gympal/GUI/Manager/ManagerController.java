package it.unipv.poisw.f25.gympal.GUI.Manager;

import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneSessioniCorsi.ICRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso.ISessionIdGenerator;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.DipendentiETurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.IDipendentiETurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.EventiECorsiCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.IEventiECorsiCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.IRettificaCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.RettificaCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;

public class ManagerController implements IRegistraEMostraSchermate{
	
	private String schermataPreLogout = "SCHERMATA0";
	
	/*Viste*/
    private final IDashboard manDashView;
    private LogoutConfirmationView logoutView;
    
    /*Servizi*/
    private ICommonServicesBundle serviziComuni;
    private ISessionIdGenerator generatoreIDs;
    private ICalendarioFacadeService calendarioService;
    private ICorsiCRUDFacadeService corsiCRUDService;
    private ICRUDCorsiSupportServices supportoCRUDCorsi;
    private IEventiCRUDFacadeService eventiCRUDService;
    private ICRUDEventiSupportServices supportoCRUDeventi;
    private ITurniCRUDFacadeService turniCRUDService;
    private ICRUDTurniSupportServices supportoCRUDTurni;
	private IDipendentiCRUDFacadeService dipCRUDService;
    private ICRUDDipendentiSupportServices supportpCRUDdip;
    
    /*Coordinatori GUI*/
    private IRettificaCoordinator rettCoord;
    private IEventiECorsiCoordinator eventiECorsiCoord;
    private IDipendentiETurniCoordinator dipETurniCoord;
    
    //----------------------------------------------------------------
    
    public ManagerController(IDashboard view,
    						 ICommonServicesBundle serviziComuni,
    						 ISessionIdGenerator generatoreIDs,
    						 ICalendarioFacadeService calendarioService,
    						 ICorsiCRUDFacadeService corsiCRUDService,
    						 ICRUDCorsiSupportServices supportoCRUDCorsi,
    						 IEventiCRUDFacadeService eventiCRUDService,
    						 ICRUDEventiSupportServices supportoCRUDeventi,
    						 ITurniCRUDFacadeService turniCRUDService,
    						 ICRUDTurniSupportServices supportoCRUDTurni,
    						 IDipendentiCRUDFacadeService dipCRUDService,
    						 ICRUDDipendentiSupportServices supportpCRUDdip) {
    	
    	/*Dashboard*/
    	manDashView = view;
    	
    	/*Servizi*/
    	this.serviziComuni = serviziComuni;
    	this.generatoreIDs = generatoreIDs;
    	this.calendarioService = calendarioService;
    	this.corsiCRUDService = corsiCRUDService;
    	this.supportoCRUDCorsi = supportoCRUDCorsi;
    	this.eventiCRUDService = eventiCRUDService;
    	this.supportoCRUDeventi = supportoCRUDeventi;
    	this.turniCRUDService = turniCRUDService;
    	this.supportoCRUDTurni = supportoCRUDTurni;
    	this.dipCRUDService = dipCRUDService;
    	this.supportpCRUDdip = supportpCRUDdip;
    	
    	/**/
        registraAzioniPulsanti();
        inizializzaSchermateStatiche();
    	
    }
    
    //----------------------------------------------------------------
    
    @Override
    public void registraSchermata(String nomeSchermata, JPanel view) {
    	
    	manDashView.registraSchermata(nomeSchermata, view);
        
    }

    @Override
    public void mostraSchermata(String nomeSchermata) {
    	
        schermataPreLogout = nomeSchermata;
        manDashView.mostraSchermata(nomeSchermata);
        
    }
    
    //----------------------------------------------------------------
    
    private void inizializzaSchermateStatiche() {

        logoutView = new LogoutConfirmationView();
        manDashView.registraSchermata("LOGOUT_VIEW", logoutView);
        
    }

    //----------------------------------------------------------------

    private void registraAzioniPulsanti() {
    	
    	manDashView.aggiungiComando("RETTIFICA", () -> {
    		
    		mostraRettificaAbbonamentoSeNonInizializzato();
    		mostraSchermata("RETT_INFO");}
    	
    	);
    	
    	manDashView.aggiungiComando("ADD_REM_MOD_EVENTS_COURSES", () -> {
    		
    		mostraEventiECorsiSeNonInizializzato();
    		mostraSchermata("EVENTI_E_CORSI");
    		
    	});
    	
    	manDashView.aggiungiComando("PLAN_SHIFTS_HANDLE_EMPLOYEES", () -> {
    		
    		mostraDipendentiETurniSeNonInizializzato();
    		mostraSchermata("DIPENDENTI_E_TURNI");
    		
    	});    	
    	

    	manDashView.aggiungiComando("LOGOUT", () -> {
        	
            System.out.println("schermataPreLogout = " + schermataPreLogout);
            
            new LogoutConfirmationController(logoutView, 
						            		(IDashboard)manDashView, 
											 schermataPreLogout,
											 serviziComuni.getRegexChecker(),
											 serviziComuni.getAutDipendente(),
											 serviziComuni.getStaffFactory());
            manDashView.mostraSchermata("LOGOUT_VIEW"); 
            
        });
        
    }

    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraRettificaAbbonamentoSeNonInizializzato() {
    	
    	if(rettCoord == null) {
    		
    		rettCoord = new RettificaCoordinator(this,
												 this.serviziComuni.getCampoValidabileFactory(),
												 this.serviziComuni.getValidatoreCampi(),
												 this.serviziComuni.getRecuperaDati(),
												 this.serviziComuni.getHeadHunter(),
												 this.serviziComuni.getUpdater(),
												 this.serviziComuni.getImmettiDati());
    		
    	}
    	
    }
    
    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraEventiECorsiSeNonInizializzato() {
    	
    	if(eventiECorsiCoord == null) {
    		
    		eventiECorsiCoord = new EventiECorsiCoordinator(this, generatoreIDs,
    														calendarioService,
    														corsiCRUDService,
    														supportoCRUDCorsi,
    														eventiCRUDService,
    														supportoCRUDeventi);
    		
    	}
    	
    }
    
    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraDipendentiETurniSeNonInizializzato() {
    	
    	if(dipETurniCoord == null) {
    		
    		dipETurniCoord = new DipendentiETurniCoordinator(this,
    													     turniCRUDService,
    													     supportoCRUDTurni,
    													     dipCRUDService,
    													     supportpCRUDdip);
    		
    	}
    	
    }
    
    
    //----------------------------------------------------------------
    
}
