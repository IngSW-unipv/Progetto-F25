package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneEvento;

public class EventoValidator implements IEventoValidator{
	
    @Override
    public boolean campiObbligatoriCompilati(String nome, 
    										 String data, 
    										 String orarioInizio, 
    										 String orarioFine) {
    	
        return nome != null && !nome.isEmpty()
            && data != null && !data.isEmpty()
            && orarioInizio != null && !orarioInizio.isEmpty()
            && orarioFine != null && !orarioFine.isEmpty();
        
    }

}
