package it.unipv.poisw.f25.gympal.ReceptionistController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;

import it.unipv.poisw.f25.gympal.GUI.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.staff.Receptionist;
import it.unipv.poisw.f25.gympal.utility.LogoutConfirmationController;

public class ReceptionistController {

	private ReceptionistDashboardView recDashView;
	private Receptionist receptionist;
	
    private LogoutConfirmationView logoutView;
    
    private String schermataPreLogout;

  //----------------------------------------------------------------
    
	public ReceptionistController(ReceptionistDashboardView view, Receptionist receptionist) {
		
        recDashView = view;
        
        this.receptionist = receptionist;
        
        registraAzioniPulsanti();
        inizializzaSchermate();
        
    }

    //----------------------------------------------------------------
    
    private void inizializzaSchermate() {
        
    	// Inizializza e registra le schermate con logica propria

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

        /*"entrySet()" restituisce una coppia chiave valore "(bottone1, comando1)"*/
        
        for (Map.Entry<JButton, String> entry : mappaComandi.entrySet()) {
        	
            JButton bottone = entry.getKey();
            
            String comando = entry.getValue();
//
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
            
                case "REGISTER": mostraSchermata("SCHERMATA1"); break;
                
                case "MODIFY": mostraSchermata("SCHERMATA2"); break;
                
                case "LOGOUT": {
                	
                    new LogoutConfirmationController(logoutView, recDashView, schermataPreLogout);
                    recDashView.getCardLayout().show(recDashView.getPannelloDestro(), "LOGOUT_VIEW");
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
