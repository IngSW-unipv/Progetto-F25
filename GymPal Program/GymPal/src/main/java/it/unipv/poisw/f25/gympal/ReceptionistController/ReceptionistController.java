package it.unipv.poisw.f25.gympal.ReceptionistController;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.ClientInfosView;
import it.unipv.poisw.f25.gympal.GUI.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.GUI.RiepilogoEPagamentoView;
import it.unipv.poisw.f25.gympal.GUI.SubscriptionCustomizationView;
import it.unipv.poisw.f25.gympal.staff.Receptionist;
import it.unipv.poisw.f25.gympal.utility.LogoutConfirmationController;

public class ReceptionistController {

	private ReceptionistDashboardView recDashView;
    private LogoutConfirmationView logoutView;
    private SubscriptionCustomizationView subView;
    private ClientInfosView clientInfosView;
    private RiepilogoEPagamentoView riepilogoEPagamento = null;
    
	private Receptionist receptionist;
    private String schermataPreLogout;
    
    private AbbonamentoDTO abbonamentoDTO;

  //----------------------------------------------------------------
    
	public ReceptionistController(ReceptionistDashboardView view, Receptionist receptionist) {
		
        recDashView = view;
        
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
        recDashView.getPannelloDestro().add(subView, "SUB_VIEW");
        
        /*Posto in questo punto, il "SubscriptionCustomizationController" è creato una sola
         *volta, il ché assicura che esso non venga ri-creato più volte (no duplicazione
         *listeners).*/
        
        new SubscriptionCustomizationController(subView, () -> {mostraSchermata("INFOS_VIEW");},
        										() -> {inizializzaCicloRegistrazioneCliente();
        										mostraSchermata("SCHERMATA0");},
        									    abbonamentoDTO);
        
        // 2. Raccolta dati cliente
        
        clientInfosView = new ClientInfosView();
        recDashView.getPannelloDestro().add(clientInfosView, "INFOS_VIEW");
        
        new ClientInfosController(
        	    
        	    clientInfosView,

        	    // Callback onAvanti
        	    () -> {
        	    	
        	        // Rimuove la vecchia view, se esiste
        	        if (riepilogoEPagamento != null) {
        	        	
        	            recDashView.getPannelloDestro().remove(riepilogoEPagamento);
        	            
        	        }

        	        // Creati view e controller associato
        	        riepilogoEPagamento = new RiepilogoEPagamentoView();
        	        
                    new RiepilogoEPagamentoController(riepilogoEPagamento, abbonamentoDTO,
                    		
                            () -> mostraSchermata("INFOS_VIEW"), // Callback "onIndietro" da riepilogo a clientInfos
                            
                            () -> {
                            	inizializzaCicloRegistrazioneCliente(); // Reset del ciclo
                                mostraSchermata("SCHERMATA0"); // Ritorna alla schermata di benvenuto della dashboard
                            });

                        // Aggiunge la nuova view al pannello e la mostra
                        recDashView.getPannelloDestro().add(riepilogoEPagamento, "RECAP_PAYMENT");
                        mostraSchermata("RECAP_PAYMENT");
                        
                    },

        	    // Callback "onIndietro" da ClienInfosView a subView
        	    () -> mostraSchermata("SUB_VIEW"), 
        	    
        	    // Callback "onAnnulla" 
        	    () -> {inizializzaCicloRegistrazioneCliente();
				mostraSchermata("SCHERMATA0");},
        	    
        	    //Questo DTO finisce in ClientInfosController
        	    abbonamentoDTO);

    }
	
    //----------------------------------------------------------------
    
    private void inizializzaSchermateStatiche() {
    	
    	
    	// Inizializza e registra le schermate che non fanno parte di un ciclo

        // 1. Schermata di conferma logout
        logoutView = new LogoutConfirmationView();
        recDashView.getPannelloDestro().add(logoutView, "LOGOUT_VIEW");
    	
    }
    
    
    //----------------------------------------------------------------
	
    /* Crea una mappa dei bottoni con i relativi comandi associati, 
     * poi collega un unico listener*/
    
    private void registraAzioniPulsanti() {
    	
        Map<JButton, String> mappaComandi = costruisciMappaComandi();

        ActionListener gestoreEventi = creaGestoreEventi();

        /*"mappaComandi.entrySet()" restituisce un insieme (Set) di oggetti Map.Entry<K, V>
         * 
         * Ogni Map.Entry<K, V> rappresenta una coppia (chiave, valore) della mappa*/
        
        /*"entry" è il nome della variabile temporanea usata per accedere alla coppia chiave-val*/
        
        /*I ":" stanno a significare "per ogni entry nella mappa"*/
        
        for (Map.Entry<JButton, String> entry : mappaComandi.entrySet()) {
        	
            JButton bottone = entry.getKey();
            
            String comando = entry.getValue();

            bottone.setActionCommand(comando);
            
            bottone.addActionListener(gestoreEventi);
        }
    }

    //----------------------------------------------------------------
    
    /* Associa ogni pulsante della vista a un comando testuale */
    
    private Map<JButton, String> costruisciMappaComandi() {
    	
        Map<JButton, String> mappa = new HashMap<>();
        
        List<JButton> bottoni = recDashView.getBottoni();

        mappa.put(bottoni.get(0), "REGISTER");
        mappa.put(bottoni.get(1), "MODIFY");
        mappa.put(bottoni.get(2), "LOGOUT");

        return mappa;
        
    }

    //----------------------------------------------------------------
    
    /*Crea un unico gestore di eventi per tutti i pulsanti*/
    private ActionListener creaGestoreEventi() {
    	
        return e -> {
        	
            String comando = e.getActionCommand();

            switch (comando) {
            
                case "REGISTER": 
                	
                	mostraSchermata("SUB_VIEW");
                	break;
                
                case "MODIFY": 
                	
                	mostraSchermata("SCHERMATA2"); 
                	break;
                
                case "LOGOUT": {
                	
                    new LogoutConfirmationController(logoutView, recDashView, schermataPreLogout);
                    mostraSchermata("LOGOUT_VIEW");
                    break;
                    
                }
            }
        };
    }

    //----------------------------------------------------------------
    
    /*Cambia la schermata mostrata e aggiorna il riferimento alla schermata attiva*/
    private void mostraSchermata(String nomeSchermata) {
    	
    	schermataPreLogout = nomeSchermata;
    	
        recDashView.getCardLayout().show(recDashView.getPannelloDestro(), nomeSchermata);
        
    }
	
    //----------------------------------------------------------------
    
}
