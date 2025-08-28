package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
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
	
	private final ITurniCRUDFacadeService turniCRUD;
    private final ICRUDTurniSupportServices turniSupport;
    private final IDipendentiCRUDFacadeService dipCRUD;
    private final ICRUDDipendentiSupportServices dipSupport;
    private final IStaffIdGeneratorService staffIdGen;
    
	//----------------------------------------------------------------

    public TurniEDipendentiServicesBundle(ICalendarioService calendarioService,
                                          IDipendentiCRUDFacadeService dipService,
                                          IPersistenceFacade persistence,
                                          IRetrieveDipendentiFromDB listaDip) {
    	
        this.turniCRUD = new TurniCRUDFacadeService(calendarioService);
        this.turniSupport = new CRUDTurniSupportServices(new DateUtils(), 
        												 new DialogUtils(), 
        												 new DateRangeUtils(), 
        												 listaDip);
        this.dipCRUD = dipService;
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
