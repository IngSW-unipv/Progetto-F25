package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ValidazioneSessioneCorso;

public class SessioneCorsoValidator implements ISessioneCorsoValidator{
	
	@Override
    public boolean campiObbligatoriCompilati(String id, 
    										 String staffId, 
    										 String data, 
    										 String fascia) {
    	
        return !(id == null || id.isEmpty() ||
                 staffId == null ||
                 data == null || data.isEmpty() ||
                 fascia == null || fascia.isEmpty());
    }

}
