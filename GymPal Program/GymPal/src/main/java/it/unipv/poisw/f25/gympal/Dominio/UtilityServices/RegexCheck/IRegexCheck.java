package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck;

public interface IRegexCheck {
	
	public boolean check(String campo, String regex);
	
	//----------------------------------------------------------------
	
	public String retrieveSubString(String staffID);
	
	//----------------------------------------------------------------

}
