package it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc;

public class DatiNonTrovatiException extends Exception {

	//Identificatore di versione per la serializzazione
	private static final long serialVersionUID = 1L;

    public DatiNonTrovatiException(String message) {
        super(message);
    }
}
