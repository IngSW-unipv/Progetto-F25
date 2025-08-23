package it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneTurni;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public interface ITurniCRUDFacadeService {
	
    public boolean creaTurno(LocalDate data, String recMat, String recPom, 
    				         String ptMat, String ptPom);
    
    //------------------------------------------------------------
    
    public boolean modificaTurno(LocalDate data, String recMat, 
    					         String recPom, String ptMat, String ptPom);
    
    //------------------------------------------------------------
    
    public boolean cancellaTurno(LocalDate data);
    
    //------------------------------------------------------------
    
    public List<Turno> findTurniByRange(LocalDate dataInizio, LocalDate dataFine);
    
    //------------------------------------------------------------
    
    public boolean pulisciTurniVecchi();
        
    //------------------------------------------------------------

}
