package it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria;

public interface IFasciaOrariaValidator {

	public boolean isValid(String fascia);
	
	//----------------------------------------------------------------
	
	public String getErrore(String fascia);
	
	//----------------------------------------------------------------
	
}
