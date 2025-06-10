package it.unipv.poisw.f25.gympal.persistence.connection;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcDataSource;

import it.unipv.poisw.f25.gympal.persistence.setup.DatabaseInfrastructureSetup;

//Classe resposabile del passaggio a database locale H2 in caso di failover del database primario
public class FailoverConnectionFactoryProxy implements IConnectionFactory {

    private final IConnectionFactory primaryFactory;
    private final JdbcDataSource localDataSource;
    //Flag che indica l'indisponibilità del database
    private boolean primaryDbUnavailable = false;
    
    //Costruttore
    //Riceve una factory per la connessione al database primario
    //Riceve una JdbcDataSource pper la connessione ad H2
    public FailoverConnectionFactoryProxy(IConnectionFactory primaryFactory, JdbcDataSource localDataSource) {
        if (primaryFactory == null || localDataSource == null) {
            throw new IllegalArgumentException("Factory e DataSource non possono essere null");
        }
        this.primaryFactory = primaryFactory;
        this.localDataSource = localDataSource;
    }

    //Metodo principale del proxy, ovveride del metodo createConnection
    //Prova a stabilire una connessione a MySQL e in caso di fallimento passa alla local datasource H2
    @Override
    public synchronized Connection createConnection() throws SQLException {
        try {
            // Tenta la connessione al DB primario
            Connection conn = primaryFactory.createConnection();

            // Se la connessione ha successo e prima il DB era offline, notifica il ripristino
            if (primaryDbUnavailable) {
                System.out.println("ATTENZIONE:Connessione al database primario ripristinata");
            }
            primaryDbUnavailable = false; // Imposta lo stato su "disponibile"
            return conn;

        } catch (SQLException e) {
            // Connessione al primario fallita
            // Se è il primo fallimento, mostra avviso e attiva il failover
            if (!primaryDbUnavailable) {
                primaryDbUnavailable = true; 
                System.err.println("ATTENZIONE: Database primario non raggiungibile. Passaggio alla modalità offline.");
                System.err.println("Dettaglio errore: " + e.getMessage());
                DatabaseInfrastructureSetup.reportLastSyncStatus(this.localDataSource);
            }

            // Tenta la connessione al DB di failover (H2)
            try {
                return localDataSource.getConnection();
            } catch (SQLException localDbException) {
                // Errore critico, anche il DB locale è irraggiungibile
                System.err.println("ERRORE CRITICO: Impossibile connettersi anche al database locale H2.");
                throw localDbException;
            }
        }
    }
   
    //Controlla se una connessione è valida e aperta.
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

    // Chiude una connessione data.
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
    
    // La modalità di sola lettura è attiva se e solo se il DB primario non è disponibile
    @Override
    public boolean isReadOnlyMode() {
        return primaryDbUnavailable;
    }
}
