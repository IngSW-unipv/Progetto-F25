package it.unipv.poisw.f25.gympal.GUI.Receptionist;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.CalcoloEtaService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.staff.Receptionist;

public class ReceptionistController implements ICustomerRegistrationViewHandler {

    private final IReceptionistDashboardView recDashView;
    private Receptionist receptionist;

    private LogoutConfirmationView logoutView;
    private String schermataPreLogout;

    private CustomerRegistrationCoordinator customerRegistrationCoordinator;

    //----------------------------------------------------------------

    public ReceptionistController(IReceptionistDashboardView view, Receptionist receptionist) {
    	
        recDashView = view;
        this.receptionist = receptionist;

        registraAzioniPulsanti();
        inizializzaSchermateStatiche();

        /*Servizi di dominio da passare al coordinatore per realizzare comunicazione
         *fra GUI e strato di dominio*/
        
        /*Siccome "ReceptionistController" incarna il confine fra GUI e Dominio, è lecito
         *che esso istanzi oggetti concreti.*/
        ICalcoloEtaService etaService = new CalcoloEtaService();
        
        // Inizializza il coordinator passando this come handler
        customerRegistrationCoordinator = new CustomerRegistrationCoordinator(this, etaService);
        
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

        recDashView.aggiungiComando("MODIFY", () -> mostraSchermata("SCHERMATA2"));

        recDashView.aggiungiComando("LOGOUT", () -> {
            System.out.println("schermataPreLogout = " + schermataPreLogout);
            new LogoutConfirmationController(logoutView, (ReceptionistDashboardView) recDashView, schermataPreLogout);
            recDashView.mostraSchermata("LOGOUT_VIEW"); // non aggiorna schermataPreLogout
        });
        
    }

    //----------------------------------------------------------------

    // Implementazione dell’interfaccia ICustomerRegistrationViewHandler

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
