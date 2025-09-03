package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneTurni;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class TurniCRUDFacadeService implements ITurniCRUDFacadeService{
	
	private ICalendarioService calendarioService;

    //------------------------------------------------------------
	
    public TurniCRUDFacadeService(ICalendarioService calendarioService) {
    	
        this.calendarioService = calendarioService;
        
    }
    
    //------------------------------------------------------------

    @Override
    public boolean creaTurno(LocalDate data, String recMat, 
    						 String recPom, String ptMat, String ptPom) {
    	
        return calendarioService.creaTurno(data, recMat, recPom, ptMat, ptPom);
        
    }
    
    //------------------------------------------------------------

    @Override
    public boolean modificaTurno(LocalDate data, String recMat, 
    							 String recPom, String ptMat, String ptPom) {
    	
        return calendarioService.modificaTurno(data, recMat, recPom, ptMat, ptPom);
        
    }
    
    //------------------------------------------------------------

    @Override
    public boolean cancellaTurno(LocalDate data) {
    	
        return calendarioService.cancellaTurno(data);
        
    }
    
    //------------------------------------------------------------

    @Override
    public List<Turno> findTurniByRange(LocalDate dataInizio, LocalDate dataFine) {
    	
        return calendarioService.findTurniByRange(dataInizio, dataFine);
        
    }
    
    //------------------------------------------------------------

    @Override
    public boolean pulisciTurniVecchi() {
    	
        return calendarioService.pulisciTurniVecchi();
        
    }
    
    //------------------------------------------------------------

}
