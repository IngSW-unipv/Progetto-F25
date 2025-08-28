package it.unipv.poisw.f25.gympal.GUI.LoginScreen;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Autenticazione.IAutenticaDipendente;
import it.unipv.poisw.f25.gympal.Dominio.staff.Staff;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.LoginResult;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.StaffFactory;

public class LoginManager {

    private final IAutenticaDipendente autentica;
    private final StaffFactory staffFactory;
    
	//----------------------------------------------------------------

    public LoginManager(IAutenticaDipendente autentica, StaffFactory factory) {
    	
        this.autentica = autentica;
        this.staffFactory = factory;
        
    }
    
	//----------------------------------------------------------------

    public LoginResult login(String nome, String cognome, 
    						 String staffID, String tipo) {
    	
        if (!autentica.autenticazione(nome, cognome, staffID)) {
        	
            return new LoginResult(false, "Credenziali non trovate.", null);
            
        }

        Staff staff = staffFactory.generateStaffMember(tipo);
        
        if (staff == null) {
        	
            return new LoginResult(false, "Tipo di staff non riconosciuto.", null);
            
        }
        
        // Inizializzazione oggetto Staff con dati del login
        staff.setBasicInfo(nome, cognome, staffID);

        return new LoginResult(true,"Login riuscito! Benvenuto, " 
        					 + staff.getClass().getSimpleName(), staff);
        
    }
    
	//----------------------------------------------------------------

}
