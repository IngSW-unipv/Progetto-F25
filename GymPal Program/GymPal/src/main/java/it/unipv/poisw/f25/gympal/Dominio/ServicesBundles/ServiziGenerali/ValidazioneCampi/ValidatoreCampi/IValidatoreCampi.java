package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.ValidatoreCampi;

import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.ServiziGenerali.ValidazioneCampi.CampoValidabile.ICampoValidabile;

public interface IValidatoreCampi {
	
	public void registra(ICampoValidabile campo);
	
	//----------------------------------------------------------------
	
	public boolean tuttiValidi();
	
	//----------------------------------------------------------------

}
