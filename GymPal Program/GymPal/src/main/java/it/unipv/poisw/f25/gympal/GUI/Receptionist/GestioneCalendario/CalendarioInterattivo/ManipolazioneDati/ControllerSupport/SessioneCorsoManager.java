package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ControllerSupport;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.SessionePienaException;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class SessioneCorsoManager {
	
	private final ICalendarioFacadeService calendarioFacade;
    private final ICoordinatoreCalendario coordinator;
    
	//----------------------------------------------------------------

    public SessioneCorsoManager(ICalendarioFacadeService calendarioFacade,
                                ICoordinatoreCalendario coordinator) 
    {
        this.calendarioFacade = calendarioFacade;
        this.coordinator = coordinator;
        
    }
    
	//----------------------------------------------------------------

    public List<SessioneCorso> recuperaSessioniDisponibili(String tipo, 
    													   LocalDate inizio, 
    													   LocalDate fine) {
    	
        return calendarioFacade.getSessioniDisponibili(tipo, inizio, fine);
        
    }
    
	//----------------------------------------------------------------

    public void iscrivi(String cfCliente, SessioneCorso sessione)
			            throws ClienteNonAbbonatoException,
			                   ConflittoOrarioException,
			                   DatiNonTrovatiException,
			                   SessionePienaException {
        
        calendarioFacade.prenotaSessione(cfCliente, sessione.getSessioneId());
        coordinator.notificaCambio(sessione.getData());
        
    }
    
	//----------------------------------------------------------------

    public boolean annulla(String cfCliente, SessioneCorso sessione) {
    	
        boolean success = calendarioFacade
        				 .annullaSessione(cfCliente, sessione.getSessioneId());
        
        if (success) {
        	
            coordinator.notificaCambio(sessione.getData());
            
        }
        return success;
    }
    
	//----------------------------------------------------------------

}
