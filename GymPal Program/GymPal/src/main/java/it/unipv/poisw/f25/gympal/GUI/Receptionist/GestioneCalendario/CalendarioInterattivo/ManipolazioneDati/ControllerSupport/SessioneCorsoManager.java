package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ControllerSupport;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.SessionePienaException;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class SessioneCorsoManager {
	
	private final ICalendarioFacadeService calendarioFacade;
    private final ICoordinatoreCalendario coordinator;
    private final IDateUtils dateUtils;
    
	//----------------------------------------------------------------

    public SessioneCorsoManager(ICalendarioFacadeService calendarioFacade,
    							IDateUtils dateUtils,
                                ICoordinatoreCalendario coordinator) {
    	/*Servizi*/
        this.calendarioFacade = calendarioFacade;
        this.dateUtils = dateUtils;
        
        /*Coordinatore*/
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
    
    public List<SessioneCorso> recuperaSessioniDaStringhe(String tipo,
            											  String inizioStr,
            											  String fineStr) throws IllegalArgumentException {
    	
		LocalDate inizio = inizioStr.isEmpty() ? null : dateUtils.parseData(inizioStr);
		LocalDate fine = fineStr.isEmpty() ? null : dateUtils.parseData(fineStr);
		
		if ((inizioStr.isEmpty() && !fineStr.isEmpty()) || 
			(!inizioStr.isEmpty() && fineStr.isEmpty())) {
		    throw new IllegalArgumentException("Inserisci entrambe le date o nessuna.");
		}
		
		if (inizio == null && !inizioStr.isEmpty()) {
		throw new IllegalArgumentException("Formato data inizio non valido. "
										  + "Usa yyyy-MM-dd.");}
		
		if (fine == null && !fineStr.isEmpty()) {
		throw new IllegalArgumentException("Formato data fine non valido. "
										 + "Usa yyyy-MM-dd.");}
		
		if (!dateUtils.isRangeValido(inizio, fine)) {
		throw new IllegalArgumentException("La data fine non può essere precedente "
										 + "alla data inizio.");}
		
		return recuperaSessioniDisponibili(tipo, inizio, fine);
		
    }
    
	//----------------------------------------------------------------


}
