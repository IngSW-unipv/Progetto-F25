package it.unipv.poisw.f25.gympal.GUI.Receptionist.LogoutView;

import java.awt.event.ActionListener;

import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginController;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;

public class LogoutConfirmationController {
    
    private ILogoutConfirmationView view;
    private IDashboard mainView;
    private String schermataPrecedente;

    //----------------------------------------------------------------

    public LogoutConfirmationController(ILogoutConfirmationView logoutView, IDashboard mainView,
            String schermataPrecedente) {
        
        this.view = logoutView;
        this.mainView = mainView;
        this.schermataPrecedente = schermataPrecedente;
        
        /* Rimozione vecchi listener dai pulsanti per evitare duplicazioni
         * (senza questa operazione la pressione del tasto
         * "Sì" potrebbe creare più schermate di login, e la pressione del tasto
         * "No" funzionerebbe solo sulla prima schermata) */
        
        for (ActionListener al : view.getConfirmButton().getActionListeners()) {
            view.getConfirmButton().removeActionListener(al);
        }

        for (ActionListener al : view.getCancelButton().getActionListeners()) {
            view.getCancelButton().removeActionListener(al);
        }
        
        view.getConfirmButton().addActionListener(e -> {
            // Logica di logout: mostra login, chiude dashboard
            LoginView loginView = new LoginView();
            new LoginController(loginView);
            loginView.setVisible(true);
            
            this.mainView.dispose(); // chiude la finestra dashboard
        });
        
        view.getCancelButton().addActionListener(e -> {
            // Torna alla schermata precedente nella dashboard
            this.mainView.mostraSchermata(this.schermataPrecedente);
        });
        
    }
    
    //----------------------------------------------------------------
}
