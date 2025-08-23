package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ControllerSupport;

import java.util.List;
import java.util.stream.Collectors;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class AppuntamentoPTManager {
	
	private final ICalendarioFacadeService calendarioFacade;
    private final ICoordinatoreCalendario coordinator;
    
	//----------------------------------------------------------------

    public AppuntamentoPTManager(ICalendarioFacadeService calendarioFacade,
                                 ICoordinatoreCalendario coordinator) {
    	
        this.calendarioFacade = calendarioFacade;
        this.coordinator = coordinator;
        
    }
    
	//----------------------------------------------------------------

    public List<AppuntamentoPT> getAppuntamenti(String cfCliente, String staffId) {
    	
        return calendarioFacade.getAppuntamentiPT(cfCliente, staffId);
        
    }

	//----------------------------------------------------------------
    
    public void prenota(String cfCliente, AppuntamentoPT app) 
    					throws ConflittoOrarioException, 
    						   ClienteNonAbbonatoException, 
	                           DatiNonTrovatiException {
    	
        calendarioFacade.prenotaLezionePT(cfCliente, app.getStaffId(), app.getData(), app.getFasciaOraria());
        coordinator.notificaCambio(app.getData());
        
    }
    
	//----------------------------------------------------------------

    public boolean annulla(String cfCliente, AppuntamentoPT app) {
    	
        boolean success = calendarioFacade.annullaLezionePT(cfCliente, 
        												    app.getStaffId(), 
        												    app.getData(), 
        												    app.getFasciaOraria());
        
        if (success) {
        	
            coordinator.notificaCambio(app.getData());
            
        }
        
        return success;
        
    }
    
	//----------------------------------------------------------------
    
    public List<String> getSoloStaffIdDIP() {
    	
        return calendarioFacade.getRetrieveDipendentiService()
                               .retrieve()
                               .stream()
                               .map(Dipendente::getStaffId)
                               .filter(id -> id.contains("DIP"))
                               .collect(Collectors.toList());
    }
    
	//----------------------------------------------------------------

}
