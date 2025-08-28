package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.ValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica.CtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.CalcoloEtaService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;

public class RegistrationServicesBundle implements IRegistrationServicesBundle{
	

    private ICalcoloEtaService calcoloEtaService;
    private IValidatoreCampi validatoreCampi;
    private ICtrlReqAnagraficiService controlloRequisiti;
    
	//----------------------------------------------------------------

    public RegistrationServicesBundle() {
    	
    	/*Servizi Dominio*/
        this.calcoloEtaService = new CalcoloEtaService();
        this.validatoreCampi = new ValidatoreCampi();
        this.controlloRequisiti = new CtrlReqAnagraficiService(calcoloEtaService);

    }
    
	//----------------------------------------------------------------

    @Override
    public ICalcoloEtaService getCalcoloEtaService() {
    	
        return calcoloEtaService;
        
    }

	//----------------------------------------------------------------

    @Override
    public IValidatoreCampi getValidatoreCampi() {
    	
        return validatoreCampi;
        
    }
    
	//----------------------------------------------------------------

    @Override
    public ICtrlReqAnagraficiService getControlloRequisiti() {
    	
        return controlloRequisiti;
        
    }
    
	//----------------------------------------------------------------

}
