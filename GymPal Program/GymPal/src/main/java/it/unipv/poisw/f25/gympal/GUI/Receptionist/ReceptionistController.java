package it.unipv.poisw.f25.gympal.GUI.Receptionist;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.RegistrationServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente.IGestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.IRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.GestioneAbbCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.IGestioneAbbCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.GestioneCalendarioCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.Supporto.TurnoIndividuale;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.IVisualizzaTurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VisualizzaTurniCoordinator;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;
import it.unipv.poisw.f25.gympal.staff.Receptionist;

public class ReceptionistController implements IRegistraEMostraSchermate {

    private String schermataPreLogout = "SCHERMATA0";
	
	/*Viste*/
    private final IDashboard recDashView;
    private LogoutConfirmationView logoutView;


    /*Servizi*/
    private RegistrationServicesBundle serviziReg;
    private ICommonServicesBundle serviziComuni;
    private ICalendarioFacadeService calendarioFacade;
    private IDateUtils dateUtils;
    private IGestoreTurniPersonali gestoreTurni;
    
    /*Coordinatori GUI*/
    private IRegistrationCoordinator customerRegistrationCoordinator;
    private IGestioneAbbCoordinator gestoreAbb;
    private ICoordinatoreCalendario calendarioCoordinator;
    private IVisualizzaTurniCoordinator turniCoordinator;
    
    /*Per visualizzazione turni Receptionist*/
    private Receptionist receptionist;

    //----------------------------------------------------------------

    public ReceptionistController(IDashboard view,
    							  Receptionist receptionist,
            					  RegistrationServicesBundle serviziReg,
            					  ICommonServicesBundle serviziComuni,
            					  ICalendarioFacadeService calendarioFacade,
            					  IDateUtils dateUtils,
            					  IGestoreTurniPersonali gestoreTurni) {
    	
        recDashView = view;
        
        this.receptionist = receptionist;

        /*Inizializzazioni*/
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
        this.dateUtils = dateUtils;
        this.gestoreTurni = gestoreTurni;
 
        
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
        
        recDashView.aggiungiComando("TURNI_RECEPTIONIST", () -> {
            mostraVisualizzaTurniSeNonInizializzato();
            mostraSchermata("TURNI");
        });

        recDashView.aggiungiComando("LOGOUT", () -> {
        	
            System.out.println("schermataPreLogout = " + schermataPreLogout);
            new LogoutConfirmationController(logoutView, 
            								(IDashboard)recDashView, 
            								 schermataPreLogout,
            								 serviziComuni.getRegexChecker(),
            								 serviziComuni.getAutDipendente(),
            								 serviziComuni.getStaffFactory());
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
                                    calendarioFacade, dateUtils);

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

    /*PER "LAZY INITIALIZATION"*/
    private void mostraVisualizzaTurniSeNonInizializzato() {
        if (turniCoordinator == null) {
            String staffID = receptionist.getStaffID();

            List<Turno> turni = gestoreTurni.caricaTurni(staffID);
            List<TurnoIndividuale> personali = gestoreTurni.estraiTurniPersonali(staffID, turni);

            turniCoordinator = new VisualizzaTurniCoordinator(this, personali);
        }
    }
   
    //----------------------------------------------------------------



}
