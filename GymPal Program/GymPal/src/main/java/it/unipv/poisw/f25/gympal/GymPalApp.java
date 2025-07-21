package it.unipv.poisw.f25.gympal;


import java.awt.Font;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginController;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginView;
import it.unipv.poisw.f25.gympal.GUI.Utilities.FontSetter.*;
import it.unipv.poisw.f25.gympal.persistence.setup.PersistenceManager;

public class GymPalApp {

    public static void main(String[] args) {
    	
    	IFontSetter setter = new FontSetter();
    	
        try {
        	
            UIManager.setLookAndFeel(new FlatLightLaf()); 
            
        } catch (UnsupportedLookAndFeelException e) {
        	
            e.printStackTrace();
            
        }
        
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
        	
            
        	Font font = new Font("Segoe UI", Font.PLAIN, 15);
        	setter.setDefaultFont(font);
        	
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
