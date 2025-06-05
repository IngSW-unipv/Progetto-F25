package it.unipv.poisw.f25.gympal.persistence.util;

/**
 * Gestisce l'accesso all'infrastruttura di persistenza per l'intera applicazione.
 * Agisce come Service Locator per la IConnectionFactory, fornendo un punto di accesso globale
 * e centralizzato.
 */
public final class PersistenceManager {

    private static IConnectionFactory connectionFactory;

    // Costruttore privato per impedire l'istanziazione, è una classe puramente statica.
    private PersistenceManager() {}

    /**
     * Inizializza l'intera infrastruttura di persistenza (factory, proxy, H2, sincronizzatore).
     * Questo metodo DEVE essere chiamato una sola volta all'avvio del programma (es. nel main).
     * È 'synchronized' per garantire che non possa essere chiamato da più thread contemporaneamente
     * durante la fase critica di inizializzazione.
     */
    public static synchronized void initialize() {
        // Se è già stato inizializzato, non fare nulla per evitare di ripetere il setup.
        if (connectionFactory != null) {
            System.out.println("[PersistenceManager] L'infrastruttura di persistenza è già stata inizializzata.");
            return;
        }

        System.out.println("[PersistenceManager] Inizializzazione dell'infrastruttura di persistenza...");
        IConnectionFactory factory = DatabaseInfrastructureSetup.configureAndInitialize();
        
        if (factory == null) {
            // Se il setup fallisce, lancia un'eccezione per bloccare l'avvio dell'app in modo controllato.
            throw new RuntimeException("Inizializzazione del database fallita! L'applicazione non può partire.");
        }
        
        connectionFactory = factory;
        System.out.println("[PersistenceManager] Infrastruttura di persistenza inizializzata con successo.");
    }

    /**
     * Restituisce la connection factory configurata, pronta per essere usata dai DAO e altri componenti.
     * @return L'IConnectionFactory (che in realtà è il proxy) pronta all'uso.
     * @throws IllegalStateException se il PersistenceManager non è stato ancora inizializzato tramite initialize().
     */
    public static IConnectionFactory getConnectionFactory() {
        if (connectionFactory == null) {
            throw new IllegalStateException("PersistenceManager non è stato inizializzato! Chiamare il metodo initialize() all'avvio dell'applicazione.");
        }
        return connectionFactory;
    }
}