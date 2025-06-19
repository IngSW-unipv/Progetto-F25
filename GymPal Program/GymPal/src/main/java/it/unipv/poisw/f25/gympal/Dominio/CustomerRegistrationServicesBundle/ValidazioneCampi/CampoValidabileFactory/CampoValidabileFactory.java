package it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.CampoValidabileFactory;

import javax.swing.JTextField;

import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.CampoValidabile.CampoValidabile;
import it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle.ValidazioneCampi.CampoValidabile.ICampoValidabile;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;

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
