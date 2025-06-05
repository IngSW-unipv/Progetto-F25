package it.unipv.poisw.f25.gympal.persistence.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.h2.jdbcx.JdbcDataSource;

// Classe di utilità che configura e inizializza l'intera infrastruttura di persistenza.
public final class DatabaseInfrastructureSetup {
    // Costruttore privato 
    private DatabaseInfrastructureSetup() {}

    //Metodo statico principale che sincronizza MySQL e H2 e restituisce il proxy della connection factory
    public static IConnectionFactory configureAndInitialize() {
        System.out.println("[SETUP] Inizio configurazione dell'infrastruttura di persistenza...");
        try {
            //1. Carico le proprietà dal file db.properties
            Properties props = loadProperties();

            //2. Creo la factory primaria (MySQL)
            IConnectionFactory mysqlFactory = MySQLConnectionFactory.getInstance();
            System.out.println("[SETUP] Istanza di MySQLConnectionFactory ottenuta.");

            //3. Creo e configuro il DataSource H2 usando le proprietà
            JdbcDataSource h2DataSource = createH2DataSource(props);
            if (h2DataSource == null) {
                throw new IllegalStateException("Creazione del DataSource H2 fallita.");
            }
        
            //4. Creo il Proxy, iniettando le sue dipendenze
            IConnectionFactory proxyFactory = new FailoverConnectionFactoryProxy(mysqlFactory, h2DataSource);
            System.out.println("[SETUP] Istanza di FailoverConnectionFactoryProxy creata.");

            //5. Esegui la sincronizzazione iniziale
            System.out.println("[SETUP] Tentativo di sincronizzazione dati iniziale...");
            DataSynchronizer synchronizer = new DataSynchronizer(mysqlFactory, h2DataSource);
            synchronizer.synchronizeMySQLToH2OnStartup();
            System.out.println("[SETUP] Fase di sincronizzazione terminata.");

            //6. Restituisci il proxy
            return proxyFactory;

        } catch (Exception e) {
            System.err.println("[SETUP] ERRORE CRITICO durante l'inizializzazione del database:");
            e.printStackTrace();
            return null; 
        }
    }

    private static Properties loadProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream input = DatabaseInfrastructureSetup.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IOException("File 'db.properties' non trovato nel classpath.");
            }
            props.load(input);
            return props;
        }
    }

    private static JdbcDataSource createH2DataSource(Properties props) {
        String h2Url = props.getProperty("h2.db.url");
        String h2User = props.getProperty("h2.db.username");
        String h2Password = props.getProperty("h2.db.password");

        if (h2Url == null || h2User == null || h2Password == null) {
            System.err.println("Proprietà di configurazione H2 (h2.db.url, h2.db.username, h2.db.password) non trovate in db.properties.");
            return null;
        }

        try {
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(h2Url);
            ds.setUser(h2User);
            ds.setPassword(h2Password);
            System.out.println("[SETUP] DataSource locale H2 configurato con URL: " + h2Url);
            return ds;
        } catch (Exception e) {
            System.err.println("[SETUP] Errore durante la creazione del DataSource H2: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}