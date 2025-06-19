package it.unipv.poisw.f25.gympal.utility;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import org.junit.Test;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.RegexCheck;
import it.unipv.poisw.f25.gympal.GUI.Utilities.IRegexExpression;


public class RegexCheckTest {
	

	IRegexCheck reg =  new RegexCheck();
	
    @Test
    public void testCheck_validStaffID() {
    	
        String validStaffID = "Rossi_Mario_DIP_1234IT";
        
        assertTrue(reg.check(validStaffID, IRegexExpression.STAFF_ID_REGEXEXPRESSION));
    }
    
	//----------------------------------------------------------------

    @Test
    public void testCheck_invalidStaffID() {
    	
    	// minuscole e underscore sbagliati
    	
        String invalidStaffID = "rossi_mario_dip_1234it"; 
        
        assertFalse(reg.check(invalidStaffID, IRegexExpression.STAFF_ID_REGEXEXPRESSION));
    }
    
	//----------------------------------------------------------------

    @Test
    public void testCheck_validName() {
    	
        String name = "Jean-Pierre";
        
        assertTrue(reg.check(name, IRegexExpression.NAME_REGEXEXPRESSION));
        
    }
    
	//----------------------------------------------------------------

    @Test
    public void testCheck_invalidName_specialChars() {
    	
        String name = "Jean<Marie";
        
        assertFalse(reg.check(name, IRegexExpression.NAME_REGEXEXPRESSION));
        
    }
    
	//----------------------------------------------------------------

    @Test
    public void testRetrieveSubString_DIP() {
    	
        RegexCheck rc = new RegexCheck();
        
        String id = "Rossi_Mario_DIP_1234IT";
        
        assertEquals("DIP", rc.retrieveSubString(id));
        
    }
    
	//----------------------------------------------------------------

    @Test
    public void testRetrieveSubString_REC() {
    	
        RegexCheck rc = new RegexCheck();
        
        String id = "Bianchi_Giulia_REC_5678FR";
        
        assertEquals("REC", rc.retrieveSubString(id));
        
    }
    
	//----------------------------------------------------------------

    @Test
    public void testRetrieveSubString_MAN() {
    	
        RegexCheck rc = new RegexCheck();
        
        String id = "Verdi_Luca_MAN_9012DE";
        
        assertEquals("MAN", rc.retrieveSubString(id));
        
    }
    
	//----------------------------------------------------------------

    @Test
    public void testRetrieveSubString_invalid() {
    	
        RegexCheck rc = new RegexCheck();
        
        String id = "Ferrari_Anna_ENG_1111UK";
        
        assertNull(rc.retrieveSubString(id));
        
    }
    
	//----------------------------------------------------------------

    @Test
    public void testCheck_nullInput() {
    	
        assertFalse(reg.check(null, IRegexExpression.NAME_REGEXEXPRESSION));
        
    }
    
	//----------------------------------------------------------------

    @Test
    public void testCheck_tooLongInput() {
    	
        String longInput = new String(new char[51]).replace('\0', 'A');
        
        assertFalse(reg.check(longInput, IRegexExpression.NAME_REGEXEXPRESSION));
        
    }
    
	//----------------------------------------------------------------
    
    @Test
    public void testCheck_multipleValidNames() {
    	
        String[] validNames = {"Luca", "Jean-Pierre", "De La Cruz", "O'Connor", "Anna Maria"};

        for (String name : validNames) {
            assertTrue("Expected valid name to pass: " + name,
                       reg.check(name, IRegexExpression.NAME_REGEXEXPRESSION));
            
        }
        
    }
    
	//----------------------------------------------------------------

    @Test
    public void testCheck_multipleInvalidNames() {
    	
        String[] invalidNames = {"luca", "Jean<Marie", "De%LaCruz", "Anna=Maria", "''"};

        for (String name : invalidNames) {
            assertFalse("Expected invalid name to fail: " + name,
                        reg.check(name, IRegexExpression.NAME_REGEXEXPRESSION));
            
        }
        
    }
    
	//----------------------------------------------------------------
    
    @Test
    public void testCheck_validCodiceFiscale() {
        String[] validCf = {
            "RSSMRA85M01H501Z", // Esempio valido
            "VRDGPP13R10B293P"  // Altro esempio valido
        };

        for (String cf : validCf) {
            assertTrue("Codice Fiscale valido non riconosciuto: " + cf,
                       reg.check(cf, IRegexExpression.CODICE_FISCALE));
        }
    }

	//----------------------------------------------------------------
    
    @Test
    public void testCheck_invalidCodiceFiscale() {
        String[] invalidCf = {
            "rssmra85m01h501z", // Lettere minuscole
            "VRDGPP13R10B293",  // Lunghezza errata
            "12345678901",      // Solo numeri
            ""				    // Campo vuoto
        };

        for (String cf : invalidCf) {
            assertFalse("Codice Fiscale non valido riconosciuto: " + cf,
                        reg.check(cf, IRegexExpression.CODICE_FISCALE));
        }
    }
    
	//----------------------------------------------------------------
    
    @Test
    public void testCheck_validEmails() {
        String[] validEmails = {
            "mario.rossi@example.com",
            "luca_bianchi123@sub.domain.it",
            "giulia-verdi+test@gmail.com",
            "user.name+filter@sub.domain.co",
            "a_b-c.d+e@domain.it"
        };

        for (String email : validEmails) {
            assertTrue("Email valida non riconosciuta: " + email,
                    reg.check(email, IRegexExpression.EMAIL));
        }
    }
    
	//----------------------------------------------------------------
    
    @Test
    public void testCheck_invalidEmails() {
        String[] invalidEmails = {
            "mario.rossi@example",           // dominio incompleto
            "luca@.com",                     // dominio mancante
            "giulia@domain_with_underscore.com", // underscore nel dominio
            "rossi@@example.com",           // doppia @
            "a@b",                          // troppo corta
            "user@domain.c",                // TLD troppo corto
            "user@domain.toolongtldddd",    // TLD troppo lungo
            "utente@<dominio>.it",          // caratteri vietati
            "user@exam%ple.com"             // carattere vietato
        };

        for (String email : invalidEmails) {
            assertFalse("Email non valida riconosciuta: " + email,
                    reg.check(email, IRegexExpression.EMAIL));
        }
    }
    
	//----------------------------------------------------------------
    
    @Test
    public void testCheck_emailMaxLength() {
        String localPart = new String(new char[64]).replace('\0', 'a');
        String domain = new String(new char[185]).replace('\0', 'b') + ".com"; // 185 + 4 = 189
        String email = localPart + "@" + domain;

        assertTrue("Email con lunghezza massima non riconosciuta: " + email,
                reg.check(email, IRegexExpression.EMAIL));
    }
    
	//----------------------------------------------------------------
    
    @Test
    public void testCheck_emailTooLongLocalPart() {
        String localPart = new String(new char[65]).replace('\0', 'a'); // >64
        String domain = "example.com";
        String email = localPart + "@" + domain;

        assertFalse("Email con parte locale troppo lunga accettata: " + email,
                reg.check(email, IRegexExpression.EMAIL));
    }

	//----------------------------------------------------------------
    
    @Test
    public void testRetrieveSubString_nullInput() {
        RegexCheck rc = new RegexCheck();
        assertNull(rc.retrieveSubString(null));
    }

	//----------------------------------------------------------------
    
    @Test
    public void testRetrieveSubString_emptyString() {
        RegexCheck rc = new RegexCheck();
        assertNull(rc.retrieveSubString(""));
    }
    
	//----------------------------------------------------------------

    @Test
    public void testRetrieveSubString_caseInsensitive() {
        RegexCheck rc = new RegexCheck();
        assertEquals("DIP", rc.retrieveSubString("rossi_mario_dip_1234IT")); // minuscolo -> trova "dip"
    }
    
	//----------------------------------------------------------------

    @Test
    public void testRetrieveSubString_partialCode() {
        RegexCheck rc = new RegexCheck();
        assertNull(rc.retrieveSubString("rossi_mario_DI_1234IT")); // codice incompleto
    }
    
	//----------------------------------------------------------------

    @Test
    public void testRetrieveSubString_codeAtEnd() {
        RegexCheck rc = new RegexCheck();
        assertEquals("MAN", rc.retrieveSubString("Verdi_Luca_MAN")); // codice esatto alla fine
    }

    
	//----------------------------------------------------------------
    
    @Test
    public void testCheck_inputWithSpaces() {
    	
        // Spazi all'inizio e alla fine - dovrebbe fallire se la regex non lo consente
        String emailWithLeadingTrailingSpaces = "  mario.rossi@example.com  ";
        assertFalse("Email con spazi iniziali/finali dovrebbe fallire",
                    reg.check(emailWithLeadingTrailingSpaces, IRegexExpression.EMAIL));
        
        // Spazi all'interno del campo (non ammessi in email)
        String emailWithInternalSpaces = "mario. rossi@example.com";
        assertFalse("Email con spazi interni dovrebbe fallire",
                    reg.check(emailWithInternalSpaces, IRegexExpression.EMAIL));

        // Spazi nei nomi (se la regex NAME li consente, test che passano)
        String nameWithSpaces = "Anna Maria";
        assertTrue("Nome con spazi dovrebbe passare",
                   reg.check(nameWithSpaces, IRegexExpression.NAME_REGEXEXPRESSION));

        // Spazi nei nomi con underscore o caratteri non ammessi - fallisce
        String invalidNameWithSpaces = "Anna Maria_";
        assertFalse("Nome con caratteri non ammessi dovrebbe fallire",
                    reg.check(invalidNameWithSpaces, IRegexExpression.NAME_REGEXEXPRESSION));
    }

	//----------------------------------------------------------------
    
}
