package it.unipv.poisw.f25.gympal.GUI.LoginScreen;

import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.AutEmployee.IAutenticaDipendente;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.GUI.DashboardsFactory.DashboardDispatcherFactory;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.LoginResult;
import it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities.StaffFactory;
import it.unipv.poisw.f25.gympal.GUI.Utilities.IRegexExpression;
import it.unipv.poisw.f25.gympal.staff.Staff;

public class LoginController implements IRegexExpression {

    private final LoginView view;
    private final IRegexCheck validator;
    private final LoginManager loginManager;
    
	//----------------------------------------------------------------

    public LoginController(LoginView view,
                           IRegexCheck validator,
                           IAutenticaDipendente autenticatore,
                           StaffFactory staffFactory) {

        this.view = view;
        this.validator = validator;
        this.loginManager = new LoginManager(autenticatore, staffFactory);

        this.view.addLoginListener(e -> gestisciLogin());
    }
    
	//----------------------------------------------------------------

    private void gestisciLogin() {
    	
    	if (view.isBypassEnabled()) {
    	    String tipo = view.getTipoBypassato();

    	    Staff staff = new StaffFactory().generateStaffMember(tipo);
    	    if (staff != null) {
    	        view.mostraMessaggio("Login bypass attivo - accesso come " + tipo);
    	        DashboardDispatcherFactory.getDashboardPer(staff).avvia();
    	        view.dispose();
    	    } else {
    	        view.mostraMessaggio("Tipo di staff non valido per il bypass.");
    	    }
    	    return;
    	}
    	
        String nome = view.getNome();
        String cognome = view.getCognome();
        String staffID = view.getID();

        // 1. Validazione sintattica
        if (!validator.check(nome, NAME_REGEXEXPRESSION) ||
            !validator.check(cognome, SURNAME_REGEXEXPRESSION) ||
            !validator.check(staffID, STAFF_ID_REGEXEXPRESSION)) {

            view.mostraMessaggio("Formato credenziali non valido. "
            					 + "Controlla nome, cognome e ID.");
            return;
        }

        // 2. Estrazione tipo staff
        String tipo = validator.retrieveSubString(staffID);
        if (tipo == null) {
        	
            view.mostraMessaggio("Impossibile determinare il tipo"
            					 + " di staff da StaffID.");
            return;
            
        }

        // 3. Login logico
        LoginResult result = loginManager.login(nome, cognome, staffID, tipo);

        // 4. Risposta
        view.mostraMessaggio(result.getMessaggio());

        if (result.isSuccesso()) {
        	
            DashboardDispatcherFactory.getDashboardPer(result.getStaff()).avvia();
            view.dispose();
            
        }
        
    }
    
	//----------------------------------------------------------------
}
