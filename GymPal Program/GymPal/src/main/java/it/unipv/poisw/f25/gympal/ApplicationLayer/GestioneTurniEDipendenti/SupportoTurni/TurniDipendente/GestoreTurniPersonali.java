package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.ClasseDiSupporto.TurnoIndividuale;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class GestoreTurniPersonali implements IGestoreTurniPersonali{
	
	private IEstraiTurniDipendenteService estrattore;
	
    //------------------------------------------------------------

    public GestoreTurniPersonali(IEstraiTurniDipendenteService estrattore) {
    	
        this.estrattore = estrattore;
        
    }
    
    //------------------------------------------------------------

    @Override
    public List<Turno> caricaTurni(String staffId) {
    	
        Turno t = new Turno();
        t.setRecMat(staffId);
        return estrattore.turniPerSingoloDipendente(t);
        
    }
    
    //------------------------------------------------------------
    
    @Override
    public List<TurnoIndividuale> estraiTurniPersonali(String staffId, 
    												   List<Turno> turni) {
    	
        List<TurnoIndividuale> risultati = new ArrayList<>();

        for (Turno t : turni) {
        	
            LocalDate data = t.getData();
            if (staffId.equals(t.getRecMat())) {
            	
                risultati.add(new TurnoIndividuale(data, "Rec. Mattina", staffId));
                
            }
            
            if (staffId.equals(t.getRecPom())) {
            	
                risultati.add(new TurnoIndividuale(data, "Rec. Pomeriggio", staffId));
                
            }
            
            if (staffId.equals(t.getPtMat())) {
            	
                risultati.add(new TurnoIndividuale(data, "PT Mattina", staffId));
                
            }
            
            if (staffId.equals(t.getPtPom())) {
            	
                risultati.add(new TurnoIndividuale(data, "PT Pomeriggio", staffId));
                
            }
            
        }

        risultati.sort(Comparator.comparing(TurnoIndividuale::getData));
        return risultati;
        
    }
    
    //------------------------------------------------------------

}
