package it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc;

public class ConflittoOrarioException extends Exception {
	
	//Identificatore di versione per la serializzazione
	private static final long serialVersionUID = 1L;
	
    public ConflittoOrarioException(String message) {
        super(message);
    }
}