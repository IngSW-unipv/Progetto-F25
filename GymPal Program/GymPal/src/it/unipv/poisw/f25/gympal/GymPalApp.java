package it.unipv.poisw.f25.gympal;


import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.GUI.LoginView;
import it.unipv.poisw.f25.gympal.LoginController.LoginController;

import it.unipv.poisw.f25.gympal.persistence.util.PersistenceManager;

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
        
            System.out.println(">>> AVVIO APPLICAZIONE GYMPAL <<<");

            // PASSO 1: Inizializza l'intera infrastruttura di persistenza con una sola chiamata.
            // Questo metodo si occuperà di creare le factory, il proxy e lanciare la sincronizzazione.
            try {
                PersistenceManager.initialize();
            } catch (Exception e) {
                System.err.println("Impossibile avviare l'applicazione a causa di un errore critico di inizializzazione:");
                e.printStackTrace();
                return; // Interrompi l'avvio se il setup fallisce
            }
        
    }

}
