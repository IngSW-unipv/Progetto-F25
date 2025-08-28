package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck;

public class RegexCheck implements IRegexCheck{
	
	public RegexCheck() {}
	
	//----------------------------------------------------------------
	
	/*Ricevute in input una stringa ed una regex, il metodo le mette a confronto, 
	 * restituendo 'true' oppure 'false' a seconda che la stringa rispetti la regex, 
	 * oppure no.
	 * 
	 * Aggiornato il metodo 'check' a seguito di test, affinché sia flessibile rispetto
	 * al tipo di regex usata, ed il controllo eseguito con essa.*/
	
	@Override
	public boolean check(String campo, String regex) {
		
	    if (campo == null) return false;

	    int maxLength;
	    
	    if (regex.equals(IRegexExpression.EMAIL)) {
	    	
	        maxLength = 254;  // standard massimo per email
	        
	    } else if (regex.equals(IRegexExpression.CODICE_FISCALE)) {
	    	
	        maxLength = 16;  // lunghezza fissa CF
	        
	    } else {
	    	
	        maxLength = 50;  // default
	        
	    }

	    if (campo.length() > maxLength) return false;

	    return campo.matches(regex);
	    
	}
	
	//----------------------------------------------------------------
	
	@Override
	public String retrieveSubString(String staffID) {
		
	    if (staffID == null || staffID.length() < 3) {
	    	
	        return null;
	        
	    }

	    String upperStaffID = staffID.toUpperCase();

	    String[] codes = {"DIP", "REC", "MAN"};

	    for (String code : codes) {
	    	
	        int index = upperStaffID.indexOf(code);
	        
	        if (index != -1) {
	        	
	            if (index + 3 <= staffID.length()) {
	            	
	                // Forza maiuscole nella risposta
	                return staffID.substring(index, index + 3).toUpperCase();
	                
	            }
	            
	            return null;
	        }
	        
	        
	    }
	    
	    return null;
	}

	
	//----------------------------------------------------------------

}
