package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class EstraiTurniDipendenteService implements IEstraiTurniDipendenteService{
	
	private IPersistenceFacade persistence;
	
	//----------------------------------------------------------------
	
	public EstraiTurniDipendenteService(IPersistenceFacade facade) {
		
		this.persistence = facade;
	}
	
	//----------------------------------------------------------------
	
	@Override
	public List<Turno> turniPerSingoloDipendente(Turno turno){
		
	    // Recupera tutti i turni in cui è presente un determinato membro dello staff 
	    // Staff id del dipendente interessato da inserire in 'recMat' del dummy 'turno'
		return persistence.selectAllTurniByPerson(turno);
		
	}
	
	//----------------------------------------------------------------

}
