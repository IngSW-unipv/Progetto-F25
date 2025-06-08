package it.unipv.poisw.f25.gympal.GUI.LoginScreen;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.CredentialsPOJO;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.StaffFactory;
import it.unipv.poisw.f25.gympal.GUI.Utilities.IRegexExpression;
import it.unipv.poisw.f25.gympal.GUI.Utilities.RegexCheck;
import it.unipv.poisw.f25.gympal.staff.Staff;

public class LoginManager implements IRegexExpression {

    private static LoginManager loginInstance;

	//----------------------------------------------------------------
    
    private LoginManager() {}

    public static synchronized LoginManager getLoginInstance() {
    	
        if (loginInstance == null) {
        	
            loginInstance = new LoginManager();
        }
        
        return loginInstance;
    }
    
	//----------------------------------------------------------------

    public Staff login(String nome, String cognome, String staffID) {
    	
        RegexCheck reg = new RegexCheck();
        StaffFactory factory = new StaffFactory();

        if (RegexCheck.check(nome, NAME_REGEXEXPRESSION)
            && RegexCheck.check(cognome, SURNAME_REGEXEXPRESSION)
            && RegexCheck.check(staffID, STAFF_ID_REGEXEXPRESSION)) {

            CredentialsPOJO credentials = new CredentialsPOJO();
            credentials.setAllProperties(nome, cognome, staffID);

			//Va qui inserita la logica che mette a confronto le stringhe inserite da GUI con
			//le credenziali presenti in archivio.

            if (archiveCheck(credentials)) {
            	
            	
            	//Factory che istanzia Dipendente, Manager oppure Receptionist in base a cosa
            	//sia presente entro staffID
            	
                return factory.generateStaffMember(reg.retrieveSubString(staffID));
                
            } else {
            	
                return null; // Credenziali non trovate in archivio
            }
            
            
        } else {
        	
            	return null; // Formato errato
        }
   
    //----------------------------------------------------------------
        
    }

    private boolean archiveCheck(CredentialsPOJO credentials) {
        // Logica di verifica in archivio
        return true;
    }
    
}
