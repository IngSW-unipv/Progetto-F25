package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public interface ISessionIdGenerator {
	
	public void initialize(List<SessioneCorso> sessioni);
	
	//----------------------------------------------------------------

	public String generateId(String tipoCorso);
	
	//----------------------------------------------------------------	
	
}
