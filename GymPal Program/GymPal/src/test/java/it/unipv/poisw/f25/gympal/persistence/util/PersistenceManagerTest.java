package it.unipv.poisw.f25.gympal.persistence.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;

//Test per  PersistenceManager
public class PersistenceManagerTest {

	//Resetta lo stato di PersistenceManager prima di ogni test.
    //L'uso della Reflection è necessario per modificare il campo statico privato e garantire l'isolamento tra i test
    @Before
    public void resetPersistenceManagerSingleton() throws Exception {
        // Ottiengo il campo privato 'connectionFactory'
        Field instance = PersistenceManager.class.getDeclaredField("connectionFactory");
        // Rendo il campo accessibile per la modifica
        instance.setAccessible(true);
        // Imposto il campo a null per resettare lo stato
        instance.set(null, null);
    }

    //Testa che getFactory() fallisca se chiamato prima di initialize
    @Test(expected = IllegalStateException.class)
    public void testGetConnectionFactory_BeforeInitialization_ShouldThrowException() {
        System.out.println("INIZIO TEST -> PersistenceManagerTest");
        
        // Questa chiamata deve lanciare l'eccezione attesa (IllegalStateException)
        PersistenceManager.getConnectionFactory();
        
        // Se si arriva qui, il test fallisce perché l'eccezione non è stata lanciata
        fail("Avrebbe dovuto lanciare una IllegalStateException.");
    }

    //Testa che il processo di inizializzazione completo vada a buon fine
    @Test
    public void testInitialize_WhenDbIsAvailable_ShouldSucceed() {
        System.out.println("Esecuzione test per PersistenceManager (scenario: inizializzazione completa)...");
        try {
            // Esegui l'inizializzazione
            PersistenceManager.initialize();
            
            // Verifica che la factory ottenuta non sia null
            IConnectionFactory factory = PersistenceManager.getConnectionFactory();
            assertNotNull("La factory non dovrebbe essere null", factory);
            
        } catch (Exception e) {
            // Se l'inizializzazione fallisce, il test non passa
            e.printStackTrace();
            fail("L'inizializzazione non dovrebbe fallire: " + e.getMessage());
        }
    }
}
