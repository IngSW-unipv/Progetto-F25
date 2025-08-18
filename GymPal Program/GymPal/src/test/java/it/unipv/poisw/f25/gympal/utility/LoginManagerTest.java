package it.unipv.poisw.f25.gympal.utility;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.AutEmployee.IAutenticaDipendente;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginManager;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.LoginResult;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.StaffFactory;
import it.unipv.poisw.f25.gympal.staff.Dipendente;
import it.unipv.poisw.f25.gympal.staff.Manager;
import it.unipv.poisw.f25.gympal.staff.Receptionist;

public class LoginManagerTest {

    private LoginManager loginManager;

    // Finto autenticatore per test (simula risposte del DB)
    private class AutenticatoreFake implements IAutenticaDipendente {
        @Override
        public boolean autenticazione(String nome, String cognome, String staffId) {
            // Simulazione risposte specifiche in base all’input
            return (
                (nome.equals("Mario") && cognome.equals("Rossi") && staffId.equals("Rossi_Mario_DIP_1234IT")) ||
                (nome.equals("Giulia") && cognome.equals("Bianchi") && staffId.equals("Bianchi_Giulia_REC_5678FR")) ||
                (nome.equals("Luca") && cognome.equals("Verdi") && staffId.equals("Verdi_Luca_MAN_9012DE"))
            );
        }
    }
    
    //----------------------------------------------------------------

    @Before
    public void setup() {
        loginManager = new LoginManager(new AutenticatoreFake(), new StaffFactory());
    }

    // --------------------------------------------------------------------
    // CASI VALIDI
    // --------------------------------------------------------------------

    @Test
    public void testLogin_validDipendente() {
        LoginResult result = loginManager.login("Mario", "Rossi", "Rossi_Mario_DIP_1234IT", "DIP");
        assertTrue(result.isSuccesso());
        assertNotNull(result.getStaff());
        assertTrue(result.getStaff() instanceof Dipendente);
    }
    
    //----------------------------------------------------------------

    @Test
    public void testLogin_validReceptionist() {
        LoginResult result = loginManager.login("Giulia", "Bianchi", "Bianchi_Giulia_REC_5678FR", "REC");
        assertTrue(result.isSuccesso());
        assertNotNull(result.getStaff());
        assertTrue(result.getStaff() instanceof Receptionist);
    }
    
    //----------------------------------------------------------------

    @Test
    public void testLogin_validManager() {
        LoginResult result = loginManager.login("Luca", "Verdi", "Verdi_Luca_MAN_9012DE", "MAN");
        assertTrue(result.isSuccesso());
        assertNotNull(result.getStaff());
        assertTrue(result.getStaff() instanceof Manager);
    }

    // --------------------------------------------------------------------
    // CASI NON VALIDI
    // --------------------------------------------------------------------

    @Test
    public void testLogin_tipoStaffSconosciuto() {
        LoginResult result = loginManager.login("Anna", "Ferrari", "Ferrari_Anna_ENG_1111UK", "ENG");
        assertFalse(result.isSuccesso());
        assertNull(result.getStaff());
        assertEquals("Tipo di staff non riconosciuto.", result.getMessaggio());
    }
    
    //----------------------------------------------------------------

    @Test
    public void testLogin_archivioFallisce() {
        LoginResult result = loginManager.login("Non", "Esiste", "Qualcosa_DIP_0000XX", "DIP");
        assertFalse(result.isSuccesso());
        assertNull(result.getStaff());
        assertEquals("Credenziali non trovate.", result.getMessaggio());
    }
    
    //----------------------------------------------------------------

    @Test
    public void testLogin_conNull() {
        LoginResult result = loginManager.login(null, null, null, "DIP");
        assertFalse(result.isSuccesso());
        assertNull(result.getStaff());
        assertEquals("Credenziali non trovate.", result.getMessaggio());
    }
    
    //----------------------------------------------------------------

    @Test
    public void testLogin_conStringheVuote() {
        LoginResult result = loginManager.login("", "", "", "DIP");
        assertFalse(result.isSuccesso());
        assertNull(result.getStaff());
        assertEquals("Credenziali non trovate.", result.getMessaggio());
    }
    
    //----------------------------------------------------------------

}
