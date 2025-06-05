package it.unipv.poisw.f25.gympal.persistence.util;

import java.sql.Connection;
import java.sql.SQLException;
import org.h2.jdbcx.JdbcDataSource;

public class FailoverConnectionFactoryProxy implements IConnectionFactory {

    private final IConnectionFactory primaryFactory;
    private final JdbcDataSource localDataSource;
    private boolean primaryDbUnavailable = false;
    //Flag per mostrare il messaggio di failover una sola volta
    private boolean failoverMessageShown = false; 
    
    //Costruttore
    public FailoverConnectionFactoryProxy(IConnectionFactory primaryFactory, JdbcDataSource localH2DataSource) {
        if (primaryFactory == null || localH2DataSource == null) {
            throw new IllegalArgumentException("La factory primaria e il datasource locale non possono essere null.");
        }
        this.primaryFactory = primaryFactory;
        this.localDataSource = localH2DataSource;
    }

    //Metodo principale del proxy, prova a stabilire una connessione a MySQL e in caso di fallimento passa alla local datasource H2
    @Override
    public synchronized Connection createConnection() throws SQLException {
        //Se MySQL risulta disponibile prova a stabilire una connessione
    	if (!primaryDbUnavailable) {
            try {
                Connection conn = primaryFactory.createConnection();
                //Se la connessione ha successo e prima il DB era offline, logga il ripristino e modifica il flag di indisponibilità
                if (primaryDbUnavailable) { 
                    System.out.println("Database primario tornato online.");
                    // Reset del flag di failover quando il DB torna online
                    failoverMessageShown = false;
                }
                primaryDbUnavailable = false; 
                return conn;
            } catch (SQLException e) {
                primaryDbUnavailable = true;
                //Mostro il messaggio di failover una sola volta
                if (!failoverMessageShown) {
                    System.err.println("Errore connessione al DB primario: " + e.getMessage() + ".");
                    //Chiamo il metodo centralizzato per riportare lo stato del backup
                    DatabaseInfrastructureSetup.reportLastSyncStatus(this.localDataSource);
                    failoverMessageShown = true;
                }
            }
        }
    	//Se MySQL risulta indisponibile passa a H2
        if (primaryDbUnavailable) {
            try {
                //Connessione dal datasource H2 locale.
                return localDataSource.getConnection();
            } catch (SQLException localDbException) {
                System.err.println("Errore critico: Impossibile connettersi anche al DB locale H2: " + localDbException.getMessage());
                localDbException.printStackTrace(); 
                throw localDbException; 
            }
        }
        
        throw new SQLException("Errore critico in FailoverConnectionFactoryProxy");
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
}