package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneEvento;

public interface IEventoValidator {
	
	public boolean campiObbligatoriCompilati(String nome, 
											 String data, 
											 String orarioInizio, 
											 String orarioFine);

}
