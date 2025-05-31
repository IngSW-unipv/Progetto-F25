package it.unipv.poisw.f25.gympal.persistence.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;

//Singleton che fornisce connessioni MySQL.

public class MySQLConnectionFactory implements IConnectionFactory 
{

    private static MySQLConnectionFactory instance;

    
    //Ritorna l'istanza unica di MySQLConnector, creandola se necessario.
     
    public static synchronized MySQLConnectionFactory getInstance() 
    {
        if (instance == null) 
        {
            instance = new MySQLConnectionFactory();
        }
        return instance;
    }

    private final Properties props;

    
    //Costruttore privato: carica db.properties in un campo di istanza.
     
    private MySQLConnectionFactory() 
    {
        this.props = new Properties();
        try (InputStream input = MySQLConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) 
        {
            if (input == null) 
            {
                throw new IllegalStateException("File 'db.properties' non trovato nelle risorse.");
            }
            props.load(input);
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Impossibile caricare db.properties", e);
        }
    }


    @Override
    public Connection createConnection(String schema) throws SQLException 
    {
        // Recupero valori dal Properties caricato in costruttore
        String url = props.getProperty("db.url") + schema;
        String user = props.getProperty("db.username");
        String pass = props.getProperty("db.password");
        String driver = props.getProperty("db.driver");

        // Carico il driver
        try 
        {
            Class.forName(driver);  // Caricamento dinamico del driver
            return DriverManager.getConnection(url, user, pass); // Apertura connessione
        } 
        catch (ClassNotFoundException e) 
        {
            throw new SQLException("Driver JDBC non trovato: " + driver, e);
        }
        catch (SQLException e)
        {
            throw new SQLException("Errore SQL durante la connessione a " + url, e);
        }
        
    }

    @Override
    public boolean isOpen(Connection conn) 
    {
    	try 
	    {
	        return conn != null && !conn.isClosed(); // Ritorna true se la connessione non è nulla e non è chiusa
	    } 
	    catch (SQLException e) 
	    {
	    	System.err.println("Impossibile verificare lo stato della connessione: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
    }

    @Override
    public void closeConnection(Connection conn) 
    {
        if (conn == null)
        {
            return;
        }
        try 
        {
            if (!conn.isClosed()) 
            {
                conn.close();
            }
        } 
        catch (SQLException e) 
        {
        	System.err.println("Impossibile chiudere la connessione: " + e.getMessage());
			e.printStackTrace();
        }
    }

}