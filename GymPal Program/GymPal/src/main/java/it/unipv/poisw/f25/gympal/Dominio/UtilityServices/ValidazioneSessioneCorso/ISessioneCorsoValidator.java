package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneSessioneCorso;

public interface ISessioneCorsoValidator {

	public boolean campiObbligatoriCompilati(String id, 
											 String staffId, 
											 String data, 
											 String fascia);
	
}
