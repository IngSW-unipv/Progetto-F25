package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GeneratoreStaffID.IStaffIdGeneratorService;

public interface ITurniEDipendentiServicesBundle {
	
    public ITurniCRUDFacadeService getTurniCRUD();
    
	//----------------------------------------------------------------
    
    public ICRUDTurniSupportServices getTurniSupport();
    
	//----------------------------------------------------------------
    
    public IDipendentiCRUDFacadeService getDipCRUD();
    
	//----------------------------------------------------------------
    
    public ICRUDDipendentiSupportServices getDipSupport();
    
	//----------------------------------------------------------------
    
    public IStaffIdGeneratorService getStaffIdGenerator();
    
	//----------------------------------------------------------------

}
