package it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.CampoValidabileFactory;

import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.CampoValidabile.CampoValidabile;
import it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.CampoValidabile.ICampoValidabile;

public class CampoValidabileFactory implements ICampoValidabileFactory {
	
    private IRegexCheck regexChecker;
    
	//----------------------------------------------------------------

    public CampoValidabileFactory(IRegexCheck regexChecker) {
    	
        this.regexChecker = regexChecker;
        
    }
	
	//----------------------------------------------------------------
	
	@Override
	public ICampoValidabile creaCampoValidabile(JTextField field, String regex) {
		
		return new CampoValidabile(field, regex, regexChecker);
		
	}
	
	//----------------------------------------------------------------

}
