package it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.ValidatoreCampi;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabile.ICampoValidabile;

public interface IValidatoreCampi {
	
	public void registra(ICampoValidabile campo);
	
	//----------------------------------------------------------------
	
	public boolean tuttiValidi();
	
	//----------------------------------------------------------------

}
