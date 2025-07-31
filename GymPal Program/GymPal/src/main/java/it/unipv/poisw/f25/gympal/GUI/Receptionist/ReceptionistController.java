package it.unipv.poisw.f25.gympal.GUI.Receptionist;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.RegistrationServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneAbbonamento.GestioneServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.CommonServicesBundle;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.IRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.GestioneAbbCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.IGestioneAbbCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;

public class ReceptionistController implements IReceptionistController {

    private String schermataPreLogout = "SCHERMATA0";
	
	/*Viste*/
    private final IReceptionistDashboardView recDashView;
    private LogoutConfirmationView logoutView;


    /*Servizi*/
    private RegistrationServicesBundle serviziReg;
    private CommonServicesBundle serviziComuni;
    
    /*Coordinatori GUI*/
    private IRegistrationCoordinator customerRegistrationCoordinator;
    private IGestioneAbbCoordinator gestoreAbb;

    //----------------------------------------------------------------

    public ReceptionistController(IReceptionistDashboardView view,
            					  RegistrationServicesBundle serviziReg,
            					  GestioneServicesBundle serviziGes,
            					  CommonServicesBundle serviziComuni) {
    	
        recDashView = view;

        registraAzioniPulsanti();
        inizializzaSchermateStatiche();

        /*Servizi di dominio da passare al coordinatore per realizzare comunicazione
         *fra GUI e strato di dominio*/
        
        /*Siccome "ReceptionistController" incarna il confine fra GUI e Dominio, è lecito
         *che esso istanzi oggetti concreti.*/

        /*Al fine di alleggerire il codice, i serviziReg sono raggruppati in un apposito 
         *bundle.*/
        this.serviziReg = serviziReg;
        this.serviziComuni = serviziComuni;
        
        
        // Inizializza il coordinator passando 'this' come handler.
        /*In quanto oggetto concreto, è meglio che il bundle rimanga nel controllore,
         *che fa da punto dicontatto fra GUI e dominio, per scongiurare un forte accop-
         *piamento fra il coordinatore GUI ed una struttura concreta (il bundle).*/
        customerRegistrationCoordinator = new CustomerRegistrationCoordinator(this, 
        																	  this.serviziReg.getCalcoloEtaService(),
        																	  this.serviziComuni.getCampoValidabileFactory(),
        																	  this.serviziReg.getValidatoreCampi(),
        																	  this.serviziReg.getControlloRequisiti(),
        																	  this.serviziComuni.getPrezzoFactory(),
        																	  this.serviziComuni.getImmettiDati());
        
        
        gestoreAbb = new GestioneAbbCoordinator(this,
								        		this.serviziComuni.getCampoValidabileFactory(),
								        		this.serviziComuni.getValidatoreCampi(),
								        		this.serviziComuni.getRecuperaDati(),
								        		this.serviziComuni.getHeadHunter(),
								        		this.serviziComuni.getPrezzoFactory(),
								        		this.serviziComuni.getUpdater());
        
    }

    //----------------------------------------------------------------

    private void inizializzaSchermateStatiche() {
    	
        // 1. Schermata di conferma logout
        logoutView = new LogoutConfirmationView();
        recDashView.registraSchermata("LOGOUT_VIEW", logoutView);
        
    }

    //----------------------------------------------------------------

    private void registraAzioniPulsanti() {

        recDashView.aggiungiComando("REGISTER", () -> mostraSchermata("SUB_VIEW"));

        recDashView.aggiungiComando("MODIFY", () -> mostraSchermata("LOAD_CLIENT"));

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

}
