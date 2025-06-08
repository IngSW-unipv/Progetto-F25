package it.unipv.poisw.f25.gympal.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginManager;
import it.unipv.poisw.f25.gympal.staff.*;

public class LoginManagerTest {
	
	private final LoginManager loginManager = LoginManager.getLoginInstance();

    // --------------------------------------------------------------------
    // CASI VALIDI
    // --------------------------------------------------------------------

    @Test
    public void testLogin_validDipendente() {
        Staff result = loginManager.login("Mario", "Rossi", "Rossi_Mario_DIP_1234IT");
        assertNotNull(result);
        assertTrue(result instanceof Dipendente);
    }
    
    // --------------------------------------------------------------------

    @Test
    public void testLogin_validReceptionist() {
        Staff result = loginManager.login("Giulia", "Bianchi", "Bianchi_Giulia_REC_5678FR");
        assertNotNull(result);
        assertTrue(result instanceof Receptionist);
    }
    
    // --------------------------------------------------------------------

    @Test
    public void testLogin_validManager() {
        Staff result = loginManager.login("Luca", "Verdi", "Verdi_Luca_MAN_9012DE");
        assertNotNull(result);
        assertTrue(result instanceof Manager);
    }

    // --------------------------------------------------------------------
    // CASI NON VALIDI - regex fallisce
    // --------------------------------------------------------------------

    @Test
    public void testLogin_invalidName() {
        Staff result = loginManager.login("mario", "Rossi", "Rossi_Mario_DIP_1234IT"); // nome in minuscolo
        assertNull(result);
    }
    
    // --------------------------------------------------------------------

    @Test
    public void testLogin_invalidSurname() {
        Staff result = loginManager.login("Mario", "rossi", "Rossi_Mario_DIP_1234IT"); // cognome in minuscolo
        assertNull(result);
    }
    
    // --------------------------------------------------------------------

    @Test
    public void testLogin_invalidStaffID_format() {
        Staff result = loginManager.login("Mario", "Rossi", "rossi_mario_dip_1234it"); // tutto minuscolo, formato errato
        assertNull(result);
    }

    // --------------------------------------------------------------------
    
    @Test
    public void testLogin_invalidStaffID_codeUnknown() {
        Staff result = loginManager.login("Anna", "Ferrari", "Ferrari_Anna_ENG_1111UK"); // "ENG" non riconosciuto
        assertNull(result); // perché retrieveSubString restituirà null e la factory non sa che tipo creare
    }
    
    // --------------------------------------------------------------------

    @Test
    public void testLogin_nullInputs() {
        Staff result = loginManager.login(null, null, null);
        assertNull(result);
    }
    
    // --------------------------------------------------------------------

    @Test
    public void testLogin_emptyInputs() {
        Staff result = loginManager.login("", "", "");
        assertNull(result);
    }

    // --------------------------------------------------------------------

}
