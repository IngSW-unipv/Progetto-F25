package it.unipv.poisw.f25.gympal;

//i prossimi 4 package servono per il test di connessione al DB
import java.sql.Connection;
import java.sql.SQLException;
import it.unipv.poisw.f25.gympal.persistence.util.IConnectionFactory;
import it.unipv.poisw.f25.gympal.persistence.util.MySQLConnectionFactory;



import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.GUI.LoginView;
import it.unipv.poisw.f25.gympal.LoginController.LoginController;


public class GymPalApp {

    public static void main(String[] args) {
    	
    	/*Il metodo "invokeLater()" di Swing garantisce che il codice della GUI sia eseguito in
    	 *modo sicuro dal thread "Event Dispatch" ("Event Dispatch Thread" o "EDT") - di fatto,
    	 *Swing non è thread-safe. 
    	 *
    	 *Ovvero: il metodo protegge da errori di concorrenza*/
    	
    	/*Il metodo "invokeLater()" accetta un "Runnable" oppure una funzione Lambda 
    	 * "(parametri) -> {corpo}"
    	 * 
    	 *In Java, una funzione Lambda è un modo conciso per rappresentare un blocco di
    	 *codice che può essere passato come parametro a un metodo o assegnato a una variabile*/
    	
        SwingUtilities.invokeLater(() -> {
        	
            LoginView view = new LoginView();
            
            new LoginController(view);
            
            view.setVisible(true);
            
        });
        
   //NUOVO TEST CONNESSIONE DB INIZIA QUI ####################################       
        
        IConnectionFactory factory = MySQLConnectionFactory.getInstance();
        Connection conn = null;

        try {
            conn = factory.createConnection("prova");

            if (factory.isOpen(conn)) {
                System.out.println("✅ Connessione APERTA con successo.");
            } else {
                System.err.println("❌ Connessione NON valida.");
            }

            factory.closeConnection(conn);

            if (!factory.isOpen(conn)) {
                System.out.println("✅ Connessione CHIUSA correttamente.");
            } else {
                System.err.println("❌ Connessione NON chiusa.");
            }

        } catch (SQLException e) {
            System.err.println("[ERRORE TEST] Errore durante la gestione della connessione:");
            e.printStackTrace();
        }
   
      //FINE TEST #########################################
        
        
        
    }

}
