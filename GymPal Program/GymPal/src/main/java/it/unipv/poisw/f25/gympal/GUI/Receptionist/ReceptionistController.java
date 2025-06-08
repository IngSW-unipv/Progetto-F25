package it.unipv.poisw.f25.gympal.GUI.Receptionist;

import java.util.Objects;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.DTO.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.SubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment.RiepilogoEPagamentoController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView.RecapAndPayment.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.staff.Receptionist;

public class ReceptionistController {

    private final IReceptionistDashboardView recDashView;
    private LogoutConfirmationView logoutView;
    private SubscriptionCustomizationView subView;
    private ClientInfosView clientInfosView;
    private RiepilogoEPagamentoView riepilogoEPagamento = null;
    
    private final Receptionist receptionist;
    private String schermataPreLogout;
    
    private final AbbonamentoDTO abbonamentoDTO;

    //----------------------------------------------------------------
    
    public ReceptionistController(IReceptionistDashboardView view, Receptionist receptionist) {
        this.recDashView = Objects.requireNonNull(view);
        this.receptionist = receptionist;
        abbonamentoDTO = new AbbonamentoDTO();
        
        registraAzioniPulsanti();
        inizializzaCicloRegistrazioneCliente();
        inizializzaSchermateStatiche();
        
    }

    //----------------------------------------------------------------
    
    private void inizializzaCicloRegistrazioneCliente() {
        
        // 1. Schermata composizione abbonamento
        subView = new SubscriptionCustomizationView();
        recDashView.registraSchermata("SUB_VIEW", subView);
        
        /*Posto in questo punto, il "SubscriptionCustomizationController" è creato una sola
         *volta, il ché assicura che esso non venga ri-creato più volte (no duplicazione
         *listeners).*/
        
        new SubscriptionCustomizationController(subView,
            () -> mostraSchermata("INFOS_VIEW"),
            () -> {
                inizializzaCicloRegistrazioneCliente();
                mostraSchermata("SCHERMATA0");
            },
            abbonamentoDTO);
        
        // 2. Raccolta dati cliente
        
        clientInfosView = new ClientInfosView();
        recDashView.registraSchermata("INFOS_VIEW", clientInfosView);
        
        new ClientInfosController(
            clientInfosView,

            // Callback onAvanti
            () -> {
                // Rimuove la vecchia view, se esiste
                if (riepilogoEPagamento != null) {
                    recDashView.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento); // opzionale rimozione
                }

                // Sono creati view e controller associato
                riepilogoEPagamento = new RiepilogoEPagamentoView();
                
                new RiepilogoEPagamentoController(riepilogoEPagamento, abbonamentoDTO,
                    () -> mostraSchermata("INFOS_VIEW"), // Callback "onIndietro" da riepilogo a clientInfos
                    () -> {
                        inizializzaCicloRegistrazioneCliente(); // Reset del ciclo
                        mostraSchermata("SCHERMATA0"); // Ritorna alla schermata di benvenuto della dashboard
                    });
                
                // Aggiunge la nuova view al pannello e la mostra
                recDashView.registraSchermata("RECAP_PAYMENT", riepilogoEPagamento);
                mostraSchermata("RECAP_PAYMENT");
            },

            // Callback "onIndietro" da ClientInfosView a subView
            () -> mostraSchermata("SUB_VIEW"),

            // Callback "onAnnulla"
            () -> {
                inizializzaCicloRegistrazioneCliente();
                mostraSchermata("SCHERMATA0");
            },

            //Questo DTO finisce in ClientInfosController
            abbonamentoDTO);
    }

    //----------------------------------------------------------------
    
    private void inizializzaSchermateStatiche() {
        // Inizializza e registra le schermate che non fanno parte di un ciclo

        // 1. Schermata di conferma logout
        logoutView = new LogoutConfirmationView();
        
        recDashView.registraSchermata("LOGOUT_VIEW", logoutView);
        
    }
    
    //----------------------------------------------------------------
    
    /* Registra le azioni associate ai comandi della view */
    
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
    
    /* Cambia la schermata mostrata e aggiorna il riferimento alla schermata attiva */
    private void mostraSchermata(String nomeSchermata) {
        schermataPreLogout = nomeSchermata;
        recDashView.mostraSchermata(nomeSchermata);
    }
	
    //----------------------------------------------------------------

    
}
