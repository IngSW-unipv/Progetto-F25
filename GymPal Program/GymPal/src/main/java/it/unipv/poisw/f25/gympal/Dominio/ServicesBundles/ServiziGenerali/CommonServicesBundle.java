package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.CampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.ValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.RegexCheck;

public class CommonServicesBundle {
	
	private IRegexCheck regexChecker;
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;

    
	//----------------------------------------------------------------
    
    public CommonServicesBundle(){
    	
        this.regexChecker = new RegexCheck();
        this.campoValidabileFactory = new CampoValidabileFactory(regexChecker);
        this.validatoreCampi = new ValidatoreCampi();
    	
    }
    
	//----------------------------------------------------------------

    public IRegexCheck getRegexChecker() {
    	
        return regexChecker;
        
    }
    
	//----------------------------------------------------------------

    public ICampoValidabileFactory getCampoValidabileFactory() {
    	
        return campoValidabileFactory;
        
    }
    
	//----------------------------------------------------------------

    public IValidatoreCampi getValidatoreCampi() {
    	
        return validatoreCampi;
        
    }
    
	//----------------------------------------------------------------


}
