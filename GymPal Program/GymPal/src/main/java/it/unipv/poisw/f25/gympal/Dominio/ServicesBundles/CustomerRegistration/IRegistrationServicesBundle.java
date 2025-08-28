package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi.IValidatoreCampi;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.CustomerRegistration.ControlloRequisitiAnagrafica.ICtrlReqAnagraficiService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta.ICalcoloEtaService;

public interface IRegistrationServicesBundle {
	
	public ICalcoloEtaService getCalcoloEtaService();
	
	//----------------------------------------------------------------
	
	public IValidatoreCampi getValidatoreCampi();
	
	//----------------------------------------------------------------
	
	public ICtrlReqAnagraficiService getControlloRequisiti();
	
	//----------------------------------------------------------------

}
