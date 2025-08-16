package it.unipv.poisw.f25.gympal.GUI.Receptionist;
import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.RegistrationServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.IRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.GestioneAbbCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.IGestioneAbbCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.GestioneCalendarioCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;

public class ReceptionistController implements IReceptionistController {

    private String schermataPreLogout = "SCHERMATA0";
	
	/*Viste*/
    private final IDashboard recDashView;
    private LogoutConfirmationView logoutView;


    /*Servizi*/
    private RegistrationServicesBundle serviziReg;
    private CommonServicesBundle serviziComuni;
    private ICalendarioFacadeService calendarioFacade;
    
    /*Coordinatori GUI*/
    private IRegistrationCoordinator customerRegistrationCoordinator;
    private IGestioneAbbCoordinator gestoreAbb;
    private ICoordinatoreCalendario calendarioCoordinator;

    //----------------------------------------------------------------

    public ReceptionistController(IDashboard view,
            					  RegistrationServicesBundle serviziReg,
            					  CommonServicesBundle serviziComuni,
            					  ICalendarioFacadeService calendarioFacade) {
    	
        recDashView = view;

        registraAzioniPulsanti();
        inizializzaSchermateStatiche();

        /*Servizi di dominio passati ai coordinatori per realizzare comunicazione
         *fra GUI e strato di dominio*/
        
        /*Siccome "ReceptionistController" incarna il confine fra GUI e Dominio, 
         *è lecito che esso istanzi oggetti concreti.*/

        /*Al fine di alleggerire il codice, i serviziReg sono raggruppati in 
         *appositi bundles.*/
        this.serviziReg = serviziReg;
        this.serviziComuni = serviziComuni;
        this.calendarioFacade = calendarioFacade;
 
        
    }

    //----------------------------------------------------------------

    private void inizializzaSchermateStatiche() {
    	
        // 1. Schermata di conferma logout
        logoutView = new LogoutConfirmationView();
        recDashView.registraSchermata("LOGOUT_VIEW", logoutView);
        
    }

    //----------------------------------------------------------------

    private void registraAzioniPulsanti() {

        recDashView.aggiungiComando("REGISTER", () -> {
        	
        	mostraRegistrazioneClienteSeNonInizializzato();
        	mostraSchermata("SUB_VIEW");
        	});

        recDashView.aggiungiComando("MODIFY", () -> {
        	
        	mostraGestioneAbbonamentoSeNonInizializzato();
        	mostraSchermata("LOAD_CLIENT");});
        
        recDashView.aggiungiComando("CALENDAR", () -> {
        	
            mostraCalendarioSeNonInizializzato(recDashView.getMainFrame(), 
            () -> mostraSchermata("CALENDARIO_VIEW"));
            
        });

        recDashView.aggiungiComando("LOGOUT", () -> {
        	
            System.out.println("schermataPreLogout = " + schermataPreLogout);
            new LogoutConfirmationController(logoutView, (IDashboard)recDashView, schermataPreLogout);
            recDashView.mostraSchermata("LOGOUT_VIEW"); 
            
        });
        
    }

    //----------------------------------------------------------------

    // Implementazione dell’interfaccia IReceptionistController

    @Override
    public void registraSchermata(String nomeSchermata, JPanel view) {
    	
        recDashView.registraSchermata(nomeSchermata, view);
        
    }

    @Override
    public void mostraSchermata(String nomeSchermata) {
    	
        schermataPreLogout = nomeSchermata;
        recDashView.mostraSchermata(nomeSchermata);
        
    }

    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraCalendarioSeNonInizializzato(JFrame frame, Runnable callback) {
    	
        if (calendarioCoordinator == null) {
        	
            calendarioCoordinator = new GestioneCalendarioCoordinator(this,
                                    calendarioFacade);

            calendarioCoordinator.inizializzaConBarra(frame, callback);
            
        } else {callback.run();}
        
    }

    
    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraRegistrazioneClienteSeNonInizializzato() {
    	
        if (customerRegistrationCoordinator == null) {
        	
        	customerRegistrationCoordinator = new CustomerRegistrationCoordinator(
                this,
                this.serviziReg.getCalcoloEtaService(),
                this.serviziComuni.getCampoValidabileFactory(),
                this.serviziReg.getValidatoreCampi(),
                this.serviziReg.getControlloRequisiti(),
                this.serviziComuni.getPrezzoFactory(),
                this.serviziComuni.getImmettiDati(),
                this.serviziComuni.getDiscounts()
            );
        	
        }
        
    }
    
    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraGestioneAbbonamentoSeNonInizializzato() {
    	
    	if(gestoreAbb == null) {
    		
    		gestoreAbb = new GestioneAbbCoordinator(this,
					   this.serviziComuni.getCampoValidabileFactory(),
					   this.serviziComuni.getValidatoreCampi(),
					   this.serviziComuni.getRecuperaDati(),
					   this.serviziComuni.getHeadHunter(),
					   this.serviziComuni.getPrezzoFactory(),
					   this.serviziComuni.getUpdater(),
					   this.serviziComuni.getDiscounts());
    		
    	}
    	
    }
    
    //----------------------------------------------------------------



}
