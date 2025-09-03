package it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni;

import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.ClasseDiSupporto.TurnoIndividuale;

public interface IVisualizzaTurniCoordinator {
	
	public List<TurnoIndividuale> passListaTurni();

}
