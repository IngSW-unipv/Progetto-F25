package it.unipv.poisw.f25.gympal.persistence.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Properties;
import org.h2.jdbcx.JdbcDataSource;

// Classe di utilità che la configura e l'inizializza l'intera infrastruttura di persistenza.
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
        
            //4. Creo il Proxy
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
            System.err.println("Proprietà di configurazione H2 non trovate in db.properties.");
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

    //Metodo che legge il timestamp dell'ultima sincronizzazione da H2
    public static void reportLastSyncStatus(JdbcDataSource localH2DataSource) {
        if (localH2DataSource == null) {
            System.err.println("ATTENZIONE: DataSource H2 non disponibile, impossibile verificare lo stato della sincronizzazione.");
            return;
        }
        System.out.println("INFO: Controllo dello stato del database di backup...");
        String sql = "SELECT VALORE FROM SINCRO_INFO WHERE CHIAVE = 'LAST_SYNC_TIMESTAMP'";
        
        try (Connection localConn = localH2DataSource.getConnection();
             PreparedStatement stmt = localConn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                String timestampString = rs.getString("VALORE");
                LocalDateTime timestamp = LocalDateTime.parse(timestampString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                DateTimeFormatter userFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
                System.out.println("ATTENZIONE: I dati di backup sono aggiornati al: " + timestamp.format(userFormatter));
            } else {
                System.out.println("ATTENZIONE: Nessuna sincronizzazione precedente trovata. Il database di backup è vuoto o contiene dati molto vecchi.");
            }
        } catch (SQLException e) {
            System.err.println("ATTENZIONE: Impossibile leggere lo stato dell'ultima sincronizzazione. I dati potrebbero essere non aggiornati.");
            e.printStackTrace();
        }
    }
}