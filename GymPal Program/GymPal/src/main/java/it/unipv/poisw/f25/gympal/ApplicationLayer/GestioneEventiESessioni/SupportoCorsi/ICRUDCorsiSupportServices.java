package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoCorsi;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.IDialogUtils;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneSessioneCorso.ISessioneCorsoValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;

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
