package it.unipv.poisw.f25.gympal.utility;

import java.awt.event.ActionListener;

import it.unipv.poisw.f25.gympal.GUI.LoginView;
import it.unipv.poisw.f25.gympal.GUI.LogoutConfirmationView;
import it.unipv.poisw.f25.gympal.GUI.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.LoginController.LoginController;

public class LogoutConfirmationController {
	
    private LogoutConfirmationView view;
    private ReceptionistDashboardView mainView;



    public LogoutConfirmationController(LogoutConfirmationView logoutView, ReceptionistDashboardView recDashView,
    									String schermata) {
    	
        this.view = logoutView;
        this.mainView = recDashView;
        
        
        /* Rimozione vecchi listener dai pulsanti: senza questa operazione la pressione del tasto
         * "Si" darebbe luogo alla creazione di 4 schermate di login, inoltre la pressione del tasto
         * "No" risulterebbe solo, e soltanto, nella visualizzazione della prima schermata selezionata
         * prima della chiamata alla schermata di logout*/
          
        
        for (ActionListener al : view.getConfirmButton().getActionListeners()) {
        	
            view.getConfirmButton().removeActionListener(al);
            
        }

        for (ActionListener al : view.getCancelButton().getActionListeners()) {
        	
            view.getCancelButton().removeActionListener(al);
            
        }


        view.getConfirmButton().addActionListener(e -> {
            // Logica di logout (chiudi finestra, torna al login, ecc.)
        	
        	LoginView loginView = new LoginView();
            
            new LoginController(loginView);
            
            loginView.setVisible(true);
            
            mainView.dispose(); //Elimina la ReceptionistDashboardView, lasciando solo la nuova loginView
            
        });

        view.getCancelButton().addActionListener(e -> {
            // Torna alla schermata precedente
            mainView.getCardLayout().show(mainView.getPannelloDestro(), schermata);
            
        });
    }
}
