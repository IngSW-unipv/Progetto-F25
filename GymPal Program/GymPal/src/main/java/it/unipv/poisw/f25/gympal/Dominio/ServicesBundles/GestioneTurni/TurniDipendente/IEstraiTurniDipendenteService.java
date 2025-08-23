package it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.TurniDipendente;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public interface IEstraiTurniDipendenteService {
	
	public List<Turno> turniPerSingoloDipendente(Turno turno);

}
