package it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneSessioneCorso;

public interface ISessioneCorsoValidator {

	public boolean campiObbligatoriCompilati(String id, 
											 String staffId, 
											 String data, 
											 String fascia);
	
}
