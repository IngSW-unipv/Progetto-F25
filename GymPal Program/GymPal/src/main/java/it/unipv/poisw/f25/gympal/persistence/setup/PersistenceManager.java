package it.unipv.poisw.f25.gympal.persistence.setup;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;

// Classe che gestisce l'accesso all'infrastruttura di persistenza per l'intera applicazione.
public final class PersistenceManager {

    private static IConnectionFactory connectionFactory;

    //Costruttore privato per impedire l'istanziazione
    private PersistenceManager() {}

    //Inizializza l'intera infrastruttura di persistenza (factory, proxy, H2, sincronizzatore)
    public static synchronized void initialize() {
        // Se è già stato inizializzato, non fare nulla per evitare di ripetere il setup.
        if (connectionFactory != null) {
            System.out.println("L'infrastruttura di persistenza è già stata inizializzata");
            return;
        }
        
        IConnectionFactory factory = DatabaseInfrastructureSetup.configureAndInitialize(); //configureAndInitialize restituisce un proxy
        
        if (factory == null) {
            // Se il setup fallisce, lancia un'eccezione per bloccare l'avvio dell'app in modo controllato.
            throw new RuntimeException("Inizializzazione del database fallita! L'applicazione non può partire.");
        }
        
        connectionFactory = factory;
    }

    //Restituisce la connection factory configurata, pronta per essere usata dai DAO e altri componenti
    public static IConnectionFactory getConnectionFactory() {
        if (connectionFactory == null) {
            throw new IllegalStateException("PersistenceManager non è stato inizializzato! Chiamare il metodo initialize() all'avvio dell'applicazione.");
        }
        return connectionFactory;
    }
}