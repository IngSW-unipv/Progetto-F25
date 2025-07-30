package it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc;

public class ClienteNonAbbonatoException extends Exception {
	
	//Identificatore di versione per la serializzazione
	private static final long serialVersionUID = 1L;
	
    public ClienteNonAbbonatoException(String message) {
        super(message);
    }
}