package it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneEvento;

public interface IEventoValidator {
	
	public boolean campiObbligatoriCompilati(String nome, 
											 String data, 
											 String orarioInizio, 
											 String orarioFine);

}
