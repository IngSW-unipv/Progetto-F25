package it.unipv.poisw.f25.gympal.persistence.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//Singleton che fornisce connessioni MySQL.
public class MySQLConnectionFactory implements IConnectionFactory {
    private static MySQLConnectionFactory instance;
    private final Properties props;
    
    //Ritorna l'istanza unica di MySQLConnector, creandola se necessario.
    public static synchronized MySQLConnectionFactory getInstance() {
        if (instance == null) {
            instance = new MySQLConnectionFactory();
        }
        return instance;
    }

    //Costruttore privato: carica db.properties in un campo di istanza.
    private MySQLConnectionFactory() {
        this.props = new Properties();
        try (InputStream input = MySQLConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IllegalStateException("File 'db.properties' non trovato nelle risorse.");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare db.properties", e);
        }
    }

    @Override
    public Connection createConnection() throws SQLException {
        // Recupero valori dal Properties caricato in costruttore
        String url = props.getProperty("db.url") + props.getProperty("db.schema", "gympal");
        String user = props.getProperty("db.username");
        String pass = props.getProperty("db.password");
        String driver = props.getProperty("db.driver");

        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC non trovato: " + driver, e);
        }
    }

    //Metodo che controlla che la connessione al database sia aperta
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
    
    //Metodo che chiude la connessione al database
    @Override
    public void closeConnection(Connection conn) {
        if (conn == null) {
            return;
        }
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