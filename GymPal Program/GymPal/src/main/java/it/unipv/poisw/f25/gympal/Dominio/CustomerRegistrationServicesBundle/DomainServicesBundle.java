package it.unipv.poisw.f25.gympal.Dominio.CustomerRegistrationServicesBundle;

import it.unipv.poisw.f25.gympal.Dominio.ControlloRequisitiAnagrafica.CtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.CalcoloEtaService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexCheck;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.RegexCheck;
import it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.CampoValidabileFactory.CampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.CampoValidabileFactory.ICampoValidabileFactory;
import it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.ValidazioneCampi.ValidatoreCampi.ValidatoreCampi;

public class DomainServicesBundle {
	
	private IRegexCheck regexChecker;
    private ICalcoloEtaService calcoloEtaService;
    private ICampoValidabileFactory campoValidabileFactory;
    private IValidatoreCampi validatoreCampi;
    private ICtrlReqAnagraficiService controlloRequisiti;
    
	//----------------------------------------------------------------

    public DomainServicesBundle() {
    	
        this.regexChecker = new RegexCheck();
        this.calcoloEtaService = new CalcoloEtaService();
        this.campoValidabileFactory = new CampoValidabileFactory(regexChecker);
        this.validatoreCampi = new ValidatoreCampi();
        this.controlloRequisiti = new CtrlReqAnagraficiService(calcoloEtaService);
        
    }
    
	//----------------------------------------------------------------

    public IRegexCheck getRegexChecker() {
    	
        return regexChecker;
        
    }
    
	//----------------------------------------------------------------

    public ICalcoloEtaService getCalcoloEtaService() {
    	
        return calcoloEtaService;
        
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

    public ICtrlReqAnagraficiService getControlloRequisiti() {
    	
        return controlloRequisiti;
        
    }
    
	//----------------------------------------------------------------

}
