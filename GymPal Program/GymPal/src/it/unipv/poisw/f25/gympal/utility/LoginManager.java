package it.unipv.poisw.f25.gympal.utility;
import it.unipv.poisw.f25.gympal.factories.StaffFactory;
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

        if (reg.check(nome, NAME_REGEXEXPRESSION)
            && reg.check(cognome, SURNAME_REGEXEXPRESSION)
            && reg.check(staffID, STAFF_ID_REGEXEXPRESSION)) {

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
