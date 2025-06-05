package it.unipv.poisw.f25.gympal;


import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.GUI.LoginView;
import it.unipv.poisw.f25.gympal.LoginController.LoginController;

import it.unipv.poisw.f25.gympal.persistence.util.DataSynchronizer;
import it.unipv.poisw.f25.gympal.persistence.util.FailoverConnectionFactoryProxy;
import it.unipv.poisw.f25.gympal.persistence.util.IConnectionFactory;
import it.unipv.poisw.f25.gympal.persistence.util.MySQLConnectionFactory;


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
        
        // ### SINCRONIZZAZIONE MYSQL TO H2
        IConnectionFactory mysqlFactory = null;
        // Dichiarariamo il proxy con il suo tipo concreto per poter usare il metodo getLocalH2DataSource()
        FailoverConnectionFactoryProxy proxyFactory = null; 

        try {
            // 1. Ottieni la factory primaria (MySQL)
            mysqlFactory = MySQLConnectionFactory.getInstance();
            System.out.println("[SETUP] Istanza di MySQLConnectionFactory ottenuta.");

            // 2. Crea il Proxy. Il suo costruttore inizializzerà anche il DataSource H2 interno.
            proxyFactory = new FailoverConnectionFactoryProxy(mysqlFactory);
            System.out.println("[SETUP] Istanza di FailoverConnectionFactoryProxy creata.");

            System.out.println("\n--- Avvio sincronizzazione dati iniziale ---");
            
            DataSynchronizer synchronizer = new DataSynchronizer(mysqlFactory, proxyFactory); 
            synchronizer.synchronizeMySQLToH2OnStartup();
            
            System.out.println("--- Sincronizzazione dati iniziale terminata ---\n");

        } catch (Exception e) {
            System.err.println("[SETUP] ERRORE CRITICO durante l'inizializzazione o la sincronizzazione:");
            e.printStackTrace();
            System.err.println("L'applicazione verrà terminata.");
        } 
        
    }

}
