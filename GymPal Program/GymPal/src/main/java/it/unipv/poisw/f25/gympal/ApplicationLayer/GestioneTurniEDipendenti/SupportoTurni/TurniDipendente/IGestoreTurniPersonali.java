package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente;

import java.util.List;

import it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.Supporto.TurnoIndividuale;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public interface IGestoreTurniPersonali {
	
    List<Turno> caricaTurni(String staffID);
    
    //------------------------------------------------------------
    List<TurnoIndividuale> estraiTurniPersonali(String staffID, List<Turno> turni);
    
    //------------------------------------------------------------

}
