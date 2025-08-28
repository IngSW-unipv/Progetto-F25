package it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici.EventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneSessioniCorsi.CorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoCorsi.CRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoCorsi.ICRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoEventi.CRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.FinestreDiDialogo.DialogUtils;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.FasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneEvento.EventoValidator;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneOra.OraUtils;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneSessioneCorso.SessioneCorsoValidator;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneRangeDate.DateRangeUtils;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.DateUtils;

public class EventiESessioniServicesBundle implements IEventiESessioniServicesBundle{

	private final IEventiCRUDFacadeService eventiCRUD;
    private final ICRUDEventiSupportServices eventiSupport;
    private final ICorsiCRUDFacadeService corsiCRUD;
    private final ICRUDCorsiSupportServices corsiSupport;

    //------------------------------------------------------------

    public EventiESessioniServicesBundle(ICalendarioService calendarioService) {
    	
        this.eventiCRUD = new EventiCRUDFacadeService(calendarioService);
        this.eventiSupport = new CRUDEventiSupportServices(new EventoValidator(),
                										   new DialogUtils(),
                										   new DateUtils(),
                										   new FasciaOrariaValidator(),
                										   new OraUtils(),
                										   new DateRangeUtils()
        );

        this.corsiCRUD = new CorsiCRUDFacadeService(calendarioService);
        this.corsiSupport = new CRUDCorsiSupportServices(new FasciaOrariaValidator(),
                										 new SessioneCorsoValidator(),
                										 new DateUtils(),
                										 new DialogUtils()
                										 
        );
        
    }

    //------------------------------------------------------------

    @Override
    public IEventiCRUDFacadeService getEventiCRUD() {
    	
        return eventiCRUD;
        
    }
    
    //------------------------------------------------------------

    @Override
    public ICRUDEventiSupportServices getEventiSupport() {
    	
        return eventiSupport;
        
    }
    
    //------------------------------------------------------------

    @Override
    public ICorsiCRUDFacadeService getCorsiCRUD() {
    	
        return corsiCRUD;
        
    }
    
    //------------------------------------------------------------

    @Override
    public ICRUDCorsiSupportServices getCorsiSupport() {
    	
        return corsiSupport;
        
    }

    //------------------------------------------------------------
	
}
