package it.unipv.poisw.f25.gympal.GUI.LoginELogout.LoginScreen.LoginUtilities;

import it.unipv.poisw.f25.gympal.Dominio.staff.Staff;

public class LoginResult {

	private boolean successo;
    private String messaggio;
    private Staff staff;
    
	//----------------------------------------------------------------

    public LoginResult(boolean successo, String messaggio, Staff staff) {
    	
        this.successo = successo;
        this.messaggio = messaggio;
        this.staff = staff;
        
    }
    
	//----------------------------------------------------------------

    public boolean isSuccesso() {return successo;}
    
	//----------------------------------------------------------------

    public String getMessaggio() {return messaggio;}

	//----------------------------------------------------------------
    
    public Staff getStaff() {return staff;}
    
	//----------------------------------------------------------------
	
}
