package it.unipv.poisw.f25.gympal;

import java.sql.Connection;
import it.unipv.poisw.f25.gympal.MySQLUtil.MySQLConnector;

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
        
        //prova di connessione
        
        Connection conn = null;
		
		conn= MySQLConnector.startConnection(conn,"prova");
		
		System.out.println("first check "+MySQLConnector.isOpen(conn)+"\n");
		
		MySQLConnector.closeConnection(conn);
		
		System.out.println("second check "+MySQLConnector.isOpen(conn)+"\n");
		
        
    }

}
