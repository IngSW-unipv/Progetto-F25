package it.unipv.poisw.f25.gympal.GUI.Receptionist;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.IGestoreTurniPersonali;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.Bundle.ICommonServicesBundle;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.IRegistrationServicesBundle;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.Dominio.staff.Receptionist;
import it.unipv.poisw.f25.gympal.GUI.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.ICustomerRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.GestioneAbbCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.IGestioneAbbCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.GestioneCalendarioCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.GUI.Utilities.ControllersCommonInterface.IRegistraEMostraSchermate;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.Supporto.TurnoIndividuale;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.IVisualizzaTurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VisualizzaTurniCoordinator;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class ReceptionistController implements IRegistraEMostraSchermate {

    private String schermataPreLogout = "SCHERMATA0";
	
	/*Viste*/
    private final IDashboard recDashView;
    private LogoutConfirmationView logoutView;


    /*Servizi*/
    
    private ICalendarioFacadeService calendarioFacade;
    private IFasciaOrariaValidator fasciaValidator;
    private IRegistrationServicesBundle serviziReg;
    private IGestoreTurniPersonali gestoreTurni;
    private ICommonServicesBundle serviziComuni;
    private IDialogUtils dialogUtils;
    private IDateUtils dateUtils;

    
    /*Coordinatori GUI*/
    private ICustomerRegistrationCoordinator customerRegistrationCoordinator;
    private IGestioneAbbCoordinator gestoreAbb;
    private ICoordinatoreCalendario calendarioCoordinator;
    private IVisualizzaTurniCoordinator turniCoordinator;
    
    /*Per visualizzazione turni Receptionist*/
    private Receptionist receptionist;

    //----------------------------------------------------------------

    public ReceptionistController(IDashboard view,
    							  Receptionist receptionist,
            					  IRegistrationServicesBundle serviziReg,
            					  ICommonServicesBundle serviziComuni,
            					  ICalendarioFacadeService calendarioFacade,
            					  IGestoreTurniPersonali gestoreTurni,
            					  IFasciaOrariaValidator fasciaValidator,
            					  IDateUtils dateUtils,
            					  IDialogUtils dialogUtils) {
    	
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
        this.gestoreTurni = gestoreTurni;
        this.dateUtils = dateUtils;
        this.dialogUtils = dialogUtils;
        this.fasciaValidator = fasciaValidator;
 
        
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
            								 serviziComuni);
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
                                    calendarioFacade, 
                                    fasciaValidator, 
                                    dateUtils, 
                                    serviziComuni);

            calendarioCoordinator.inizializzaConBarra(frame, callback);
            
        } else {callback.run();}
        
    }

    
    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraRegistrazioneClienteSeNonInizializzato() {
    	
        if (customerRegistrationCoordinator == null) {
        	
        	customerRegistrationCoordinator = new CustomerRegistrationCoordinator(
        									  this, 
        									  serviziComuni, 
        									  serviziReg,
        									  dateUtils);
        	
        }
        
    }
    
    //----------------------------------------------------------------
    /*PER "LAZY INITIALIZATION"*/
    private void mostraGestioneAbbonamentoSeNonInizializzato() {
    	
    	if(gestoreAbb == null) {
    		
    		gestoreAbb = new GestioneAbbCoordinator(this, 
    												serviziComuni,
    												dialogUtils);
    		
    	}
    	
    }
    
    //----------------------------------------------------------------

    /*PER "LAZY INITIALIZATION"*/
    private void mostraVisualizzaTurniSeNonInizializzato() {
        if (turniCoordinator == null) {
            String staffID = receptionist.getStaffID();

            List<Turno> turni = gestoreTurni.caricaTurni(staffID);
            List<TurnoIndividuale> personali = gestoreTurni.estraiTurniPersonali(staffID, turni);

            turniCoordinator = new VisualizzaTurniCoordinator(this, 
            												  personali, 
            												  serviziComuni.getFontChangeRegister());
        }
    }
   
    //----------------------------------------------------------------



}
