package it.unipv.poisw.f25.gympal.persistence.connection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class MySQLConnectionFactoryTest {
	
    @Test
    public void testCreateConnection_ShouldReturnOpenConnection() {
        System.out.println("INIZIO TEST -> MySQLConnectionFactoryTest");
        
        IConnectionFactory factory = MySQLConnectionFactory.getInstance();
        Connection connection = null;
        try {
        
            assertNotNull("La factory non dovrebbe essere null", factory);
            
            //Test per la creazione di una connessione
            connection = factory.createConnection();
            
            // Verifico che la connessione non sia null e sia aperta
            assertNotNull("La connessione non dovrebbe essere null", connection);
            assertFalse("La connessione dovrebbe essere aperta", connection.isClosed());
            
            System.out.println("Connessione al database stabilita\nFINE TEST -> MySQLConnectionFactoryTest");
            
        } catch (SQLException e) {
            // Se viene lanciata un'eccezione, il test fallisce.
            // Questo accade se il DB non è raggiungibile o le credenziali sono errate.
            e.printStackTrace();
            fail("La creazione della connessione non dovrebbe lanciare una SQLException: " + e.getMessage()+ "\nFINE TEST -> MySQLConnectionFactoryTest");
        } finally {
            //Chiusura connesssione
            factory.closeConnection(connection);
        }
    }
}