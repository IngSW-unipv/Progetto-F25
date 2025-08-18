package it.unipv.poisw.f25.gympal.GUI.LoginScreen.LoginUtilities;

import it.unipv.poisw.f25.gympal.staff.Staff;

public class LoginResult {

	private final boolean successo;
    private final String messaggio;
    private final Staff staff;
    
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
