package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager;

import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.IEventiESessioniServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.ITurniEDipendentiServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso.ISessionIdGenerator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.DipendentiETurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.IDipendentiETurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.EventiECorsiCoordinator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.IEventiECorsiCoordinator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.RettificaInfoCliente.IRettificaCoordinator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.RettificaInfoCliente.RettificaCoordinator;
import it.unipv.poisw.f25.gympal.GUI.LoginELogout.LogoutView.ILogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.LoginELogout.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.LoginELogout.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.CommonInterfaces.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.CommonInterfaces.DashboardsCommonInterface.IDashboard;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;

public class ManagerController implements IRegistraEMostraSchermate{
	
	private String schermataPreLogout = "SCHERMATA0";
	
	/*Viste*/
    private IDashboard manDashView;
    private ILogoutConfirmationView logoutView;
    
    /*Servizi*/
    private ICommonServicesBundle serviziComuni;
    private ISessionIdGenerator generatoreIDs;
    private ICalendarioFacadeService calendarioService;
    private ITurniEDipendentiServicesBundle turniDipServices;
    private IEventiESessioniServicesBundle eventiESessioniServices;
    
    /*Coordinatori GUI*/
    private IRettificaCoordinator rettCoord;
    private IEventiECorsiCoordinator eventiECorsiCoord;
    private IDipendentiETurniCoordinator dipETurniCoord;
    
    //----------------------------------------------------------------
    
    public ManagerController(IDashboard view,
            				 ICommonServicesBundle serviziComuni,
            				 ISessionIdGenerator generatoreIDs,
            				 ICalendarioFacadeService calendarioFacade,
            				 ITurniEDipendentiServicesBundle turniDipServices,
            				 IEventiESessioniServicesBundle eventiESessioniServices) {

   	
    	/*Dashboard*/
		this.manDashView = view;
		
		/*Servizi*/
		this.serviziComuni = serviziComuni;
		this.generatoreIDs = generatoreIDs;
		this.calendarioService = calendarioFacade;
		this.eventiESessioniServices = eventiESessioniServices;
    	this.turniDipServices = turniDipServices; 
		
    	/*Inizializzazioni*/
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

        logoutView = new LogoutConfirmationView(serviziComuni.getFontChangeRegister(),
        										new DynamicButtonSizeSetter());
        manDashView.registraSchermata("LOGOUT_VIEW", logoutView.getMainPanel());
        
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
											 serviziComuni);
            manDashView.mostraSchermata("LOGOUT_VIEW"); 
            
        });
        
    }

    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraRettificaAbbonamentoSeNonInizializzato() {
    	
    	if(rettCoord == null) {
    		
    		rettCoord = new RettificaCoordinator(this,
												 serviziComuni);
    		
    	}
    	
    }
    
    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraEventiECorsiSeNonInizializzato() {
    	
    	if(eventiECorsiCoord == null) {
    		
    		eventiECorsiCoord = new EventiECorsiCoordinator(this, 
    														generatoreIDs,
    														calendarioService,
    														serviziComuni.getFontChangeRegister(),
    														eventiESessioniServices);
    		
    	}
    	
    }
    
    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraDipendentiETurniSeNonInizializzato() {
    	
    	if(dipETurniCoord == null) {
    		
    		dipETurniCoord = new DipendentiETurniCoordinator(this,
    													     turniDipServices,
    													     serviziComuni);
    		
    	}
    	
    }
    
    
    //----------------------------------------------------------------
    
}
