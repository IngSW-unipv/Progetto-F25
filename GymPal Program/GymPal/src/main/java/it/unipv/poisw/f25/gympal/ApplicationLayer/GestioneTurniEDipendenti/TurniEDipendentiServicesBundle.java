package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.RetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneDipendenti.DipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneTurni.TurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoDipendenti.CRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.CRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.DialogUtils;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GeneratoreStaffID.IStaffIdGeneratorService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GeneratoreStaffID.StaffIdGeneratorService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate.DateRangeUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.DateUtils;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;

public class TurniEDipendentiServicesBundle implements ITurniEDipendentiServicesBundle{
	
	private ITurniCRUDFacadeService turniCRUD;
    private ICRUDTurniSupportServices turniSupport;
    private IDipendentiCRUDFacadeService dipCRUD;
    private ICRUDDipendentiSupportServices dipSupport;
    private IStaffIdGeneratorService staffIdGen;
    
	//----------------------------------------------------------------

    public TurniEDipendentiServicesBundle(ICalendarioService calendarioService,
                                          IPersistenceFacade persistence) {
    	
        this.turniCRUD = new TurniCRUDFacadeService(calendarioService);
        this.turniSupport = new CRUDTurniSupportServices(new DateUtils(), 
        												 new DialogUtils(), 
        												 new DateRangeUtils(), 
        												 new RetrieveDipendentiFromDB(persistence));
        this.dipCRUD = new DipendentiCRUDFacadeService(persistence);
        this.dipSupport = new CRUDDipendentiSupportServices(new DialogUtils());
        this.staffIdGen = new StaffIdGeneratorService(persistence);
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public ITurniCRUDFacadeService getTurniCRUD() { return turniCRUD; }
    
	//----------------------------------------------------------------
    
    @Override
    public ICRUDTurniSupportServices getTurniSupport() { return turniSupport; }
    
	//----------------------------------------------------------------
    
    @Override
    public IDipendentiCRUDFacadeService getDipCRUD() { return dipCRUD; }
    
	//----------------------------------------------------------------
    
    @Override
    public ICRUDDipendentiSupportServices getDipSupport() { return dipSupport; }
    
	//----------------------------------------------------------------
    
    @Override
    public IStaffIdGeneratorService getStaffIdGenerator() { return staffIdGen; }
    
	//----------------------------------------------------------------

}
