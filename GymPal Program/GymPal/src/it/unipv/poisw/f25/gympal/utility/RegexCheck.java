package it.unipv.poisw.f25.gympal.utility;

public class RegexCheck {
	
	public RegexCheck() {}
	
	
	//Ricevute in input una stringa ed una regex, il metodo le mette a confronto, restituendo
	//'true' oppure 'false' a seconda che la stringa rispetti la regex, oppure no
	
	public boolean check(String campo, String regex) {
        return campo != null && campo.matches(regex);
    }
	
	public String retrieveSubString(String staffID) {
		
		if (staffID.contains("DIP")) {
		    int startIndex = staffID.indexOf("DIP");
		    String sottostringa = staffID.substring(startIndex, startIndex + 3);
		    
		    return sottostringa;
		    
		} else if (staffID.contains("REC")) {
		    int startIndex = staffID.indexOf("REC");
		    String sottostringa = staffID.substring(startIndex, startIndex + 3);
		    
		    return sottostringa;
		    
		} else if (staffID.contains("MAN")) {
		    int startIndex = staffID.indexOf("MAN");
		    String sottostringa = staffID.substring(startIndex, startIndex + 3);
		    
		    return sottostringa;
		}
		
		return null;
		
	}

}
