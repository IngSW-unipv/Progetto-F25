package it.unipv.poisw.f25.gympal.LoginController;

import it.unipv.poisw.f25.gympal.GUI.LoginView;
import it.unipv.poisw.f25.gympal.factories.DashboardDispatcherFactory;
import it.unipv.poisw.f25.gympal.staff.Staff;
import it.unipv.poisw.f25.gympal.utility.LoginManager;

public class LoginController {
	
    private LoginView view;
    private LoginManager loginManager;
    
	//----------------------------------------------------------------

    public LoginController(LoginView view) {
    	
        this.view = view;
        this.loginManager = LoginManager.getLoginInstance();
        
        /*Il metodo "aggiungiLoginListener" richiama il medoto "addActionListener()", che in
         *input si aspetta di ricevere un'interfaccia di tipo "ActionListener", dotata,
         *per costruzione, di un unico metodo astratto, "actionPerformed()", il cui input è
         *un oggetto di tipo "ActionEvent".
         *
         *Il compilatore è a conoscenza di tutto ciò, del contesto, ed in base a questo 
         *inferisce che la 'e' nell'espressione Lambda non può essere altro se non un oggetto di
         *tipo "ActionEvent"
         *
         *Questo meccanismo funziona soltanto per quelle interfacce che possiedono un unico
         *metodo astratto - non c'è ambiguità*/

        this.view.aggiungiLoginListener(e -> {
        	
        	/*Contenuto del metodo "ActionPerformed", a cui è passato "ActionEvent e"*/
        	
            String nome = view.getNome();
            String cognome = view.getCognome();
            String id = view.getID();

            Staff staff = loginManager.login(nome, cognome, id);

            if (staff != null) {
            	
                view.mostraMessaggio("Login riuscito! Benvenuto, " 
                					  + staff.getClass().getSimpleName());
                
                
                // Procedi con cambio schermata                
                DashboardDispatcherFactory.getDashboardPer(staff).avvia();
                
                view.dispose(); /*Elimina la view corrente*/
                
                                
            } else {
            	
                view.mostraMessaggio("Credenziali non valide.");
            }
            
        });
        
    }
    
}
