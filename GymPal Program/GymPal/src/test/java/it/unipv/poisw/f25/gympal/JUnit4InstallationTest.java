package it.unipv.poisw.f25.gympal;

//Importa le classi necessarie da JUnit 4.
//Se Eclipse non dà errori su queste righe, significa che ha trovato le librerie!
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
* Questa classe serve solo a verificare che l'ambiente di test con JUnit 4
* sia configurato correttamente nel progetto Eclipse.
*/
public class JUnit4InstallationTest {

 // L'annotazione @Test dice a JUnit che questo è un metodo di test da eseguire.
 @Test
 public void checkJUnitIsWorking() {
     
     // 1. Prepariamo un dato di test semplice
     String expectedValue = "JUnit è pronto!";
     String actualValue = "JUnit è pronto!";
     
     // 2. Usiamo un'asserzione di JUnit per verificare che i due valori siano uguali.
     // Se lo sono, il test passa. Altrimenti, fallisce.
     assertEquals("Il test verifica che i due testi siano identici.", expectedValue, actualValue);
     
     System.out.println("Test di configurazione JUnit 4 eseguito con successo!");
 }
}
