package it.unipv.poisw.f25.gympal.persistence.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
import org.h2.jdbcx.JdbcDataSource;

public class FailoverConnectionFactoryProxy implements IConnectionFactory {

    private final IConnectionFactory primaryFactory;
    private JdbcDataSource localDataSource;
    private boolean primaryDbUnavailable = false;

    // Campi per la configurazione H2 (caricati da db.properties)
    private String h2DbUrl;
    private String h2DbUser;
    private String h2DbPassword;

    private final Properties props;
    
    //Costruttore, carica il file di configurazione per il DataSource Locale H2 e chiama il metodo che lo inizializza
    public FailoverConnectionFactoryProxy(IConnectionFactory primaryFactory) {
        this.primaryFactory = primaryFactory;
        this.props = new Properties();

        // Caricamento del file db.properties
        try (InputStream input = FailoverConnectionFactoryProxy.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IllegalStateException("File 'db.properties' non trovato nelle risorse. Impossibile configurare H2.");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare db.properties per la configurazione H2.", e);
        }

        // Lettura di url, username e password per H2
        this.h2DbUrl = props.getProperty("h2.db.url");
        this.h2DbUser = props.getProperty("h2.db.username");
        this.h2DbPassword = props.getProperty("h2.db.password");

        // Verifica che le proprietà H2 siano state caricate
        if (this.h2DbUrl == null || this.h2DbUser == null || this.h2DbPassword == null) {
            throw new IllegalStateException("Proprietà di configurazione H2 non trovate in db.properties.");
        }
     // Chiama il metodo per inizializzare il DataSource Locale H2
        initializeLocalDataSource();
    }
    
    //Metodo per inizializzare il DataSource Locale H2
    private void initializeLocalDataSource() {
    	try {
            localDataSource = new JdbcDataSource();
            //Usa le proprietà caricate
            localDataSource.setURL(this.h2DbUrl);
            localDataSource.setUser(this.h2DbUser);
            localDataSource.setPassword(this.h2DbPassword);
        } 
    	catch (Exception e) { 
            System.err.println("Errore durante l'inizializzazione del DataSource locale H2: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    @Override
    public synchronized Connection createConnection(String schema) throws SQLException {
        //Tentativo di connessione al DB primario, se il flag primaryDbUnavailable risulta false (DB tecnicamente raggiungibile)
    	//primaryDbUnavailable è false anche al primo avvio perchè a prescindere non si può sapere se il DB è online o no
    	if (!primaryDbUnavailable) {
            try {
                Connection conn = primaryFactory.createConnection(schema);
                //Se il flag di indisponibilità del DB era true (DB offline) , lo imposto a false perchè ora il DB è tornato online
                if (primaryDbUnavailable) { 
                    System.out.println("Database primario tornato online.");
                }
                primaryDbUnavailable = false;
                return conn;
                //Catch in caso ill DB non torni online
            } catch (SQLException e) {
                System.err.println("Errore connessione al DB primario: " + e.getMessage() + ". Tentativo di failover su DB locale.");
                primaryDbUnavailable = true;
            }
        }
    	//Se il flag di indisponibilità del DB è true o il tentativo di connettersi al DB è fallito passo a quello locale
        if (primaryDbUnavailable) {
            System.out.println("Utilizzo del database locale H2.");
            //Controllo che H2 sia inizzializzato, dovrebbe perchè il costruttore invoca il metodo per farlo
            if (localDataSource != null) {
                try {
                	//Tento la connessione al DB locale
                    return localDataSource.getConnection();
                } catch (SQLException e) {
                    System.err.println("Errore critico: Impossibile connettersi anche al DB locale H2: " + e.getMessage());
                    e.printStackTrace(); 
                    throw e;
                }
            } else {
                throw new SQLException("DB primario non disponibile e DB locale non inizializzato o inizializzazione fallita.");
            }
        }
        
        throw new SQLException("Stato inconsistente in FailoverConnectionFactoryProxy dopo tentativi di connessione.");
    }
    
    //metodo per ottenere localDataSource, usato per sincronizzare h2 con MySQL (vedi classe DataSynchronizer)
    public JdbcDataSource getLocalH2DataSource() {
        return this.localDataSource;
    }
    
    //Ovveride dei metodi isOpen e closeConnection stesso funzionamento di MySQLConnectionFactory
    @Override
    public boolean isOpen(Connection conn) {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Impossibile verificare lo stato della connessione: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Impossibile chiudere la connessione: " + e.getMessage());
                e.printStackTrace(); 
            }
        }
    }
}