package it.unipv.poisw.f25.gympal.MySQLUtil;


import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;


public class MySQLConnector {
	
	// Metodo che carica le proprietà di configurazione dal file db.properties
	
	private static Properties loadProperties() 
	{
        Properties props = new Properties();
        
        // Apro il file db.properties come InputStream dalle risorse del progetto
        
        try (InputStream input = MySQLConnector.class.getClassLoader().getResourceAsStream("db.properties")) 
        {
            if (input == null) {
                System.err.println("[ERRORE] File di configurazione 'db.properties' non trovato.");
                return null;
            }
            
            props.load(input);
            
        } 
        catch (IOException e) 
        {
        	System.err.println("[ERRORE] Impossibile caricare il file 'db.properties'.");
            e.printStackTrace();
            return null;
        }
        return props;
    }
	
	
	public static Connection startConnection(Connection conn, String schema) 
	{
	    
		
		Properties props = loadProperties();
		if (props == null) 
		{
            System.err.println("[ERRORE] Caricamento delle proprietà fallito.");
            return null;
        }
        
        // Estraggo i dati dal file di configurazione nelle opportune variabili
        
        String DbURL = props.getProperty("db.url")+schema;
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");
        String DbDriver =props.getProperty("db.driver");

		// Se la connessione conn è già aperta la chiude per evitare conflitti
		
	    if (isOpen(conn)) 
	    {
	        conn = closeConnection(conn);
	    }
	    
	    try 
	    {
	        Class.forName(DbDriver);
	        conn = DriverManager.getConnection(DbURL, username, password);
	    } 
	    catch (ClassNotFoundException e) 
	    {
	        System.err.println("[ERRORE] Driver JDBC non trovato: " + DbDriver+ "\nControllare il file di configurazione 'db.properties'");
	        e.printStackTrace();
	        return null;
	    } 
	    catch (SQLException e)
	    {
	        System.err.println("[ERRORE] Errore SQL durante la connessione al database: " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	    
	    return conn;

	}

	// Metodo che verifica se una connessione è aperta e valida
	public static boolean isOpen(Connection conn) {
	    try 
	    {
	        return conn != null && !conn.isClosed(); // Ritorna true se la connessione non è nulla e non è chiusa
	    } 
	    catch (SQLException e) 
	    {
	    	System.err.println("[ERRORE] Impossibile verificare lo stato della connessione: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

	// Metodo che chiude una connessione se è aperta
	public static Connection closeConnection(Connection conn)
	{
		if ( !isOpen(conn) ) 
		{
			System.err.println("[INFO] Connessione già chiusa o nulla.");
			return null;
		}
		try
		{
			conn.close();
			conn = null;
		}
		catch (SQLException e)
		{
			System.err.println("[ERRORE] Impossibile chiudere la connessione: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return conn;
	}
}
