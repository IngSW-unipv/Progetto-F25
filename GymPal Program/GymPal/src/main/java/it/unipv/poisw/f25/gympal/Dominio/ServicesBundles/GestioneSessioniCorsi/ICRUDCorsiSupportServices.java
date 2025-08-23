package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneSessioniCorsi;

import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneSessioneCorso.ISessioneCorsoValidator;

public interface ICRUDCorsiSupportServices {

	public IFasciaOrariaValidator getFasciaValidator();
	
	//----------------------------------------------------------------
	
	public ISessioneCorsoValidator getSessioneValidator();
	
	//----------------------------------------------------------------
	
	public IDateUtils getDateUtils();
	
	//----------------------------------------------------------------
	
	public IDialogUtils getDialogUtils();
	
	//----------------------------------------------------------------
	
}
