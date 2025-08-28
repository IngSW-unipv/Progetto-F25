package it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.ICalendarioService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.SessionePienaException;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;
import it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean.PartecipazioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class CalendarioFacadeService implements ICalendarioFacadeService{

    private ICalendarioService calendarioService;
    private IRetrieveDipendentiFromDB retrieveListaDip;
    
	//----------------------------------------------------------------
    
    public CalendarioFacadeService(ICalendarioService calendarioService) {
    	
    	this.calendarioService = calendarioService;
    	
    }
    
	//----------------------------------------------------------------

    public CalendarioFacadeService(ICalendarioService calendarioService,
                                   IRetrieveDipendentiFromDB retrieveListaDip) {
    	
        this.calendarioService = calendarioService;
        this.retrieveListaDip = retrieveListaDip;
        
    }

	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
    
    // CORSI /////////////////////////////////////////////////////////
    
    @Override
    public List<SessioneCorso> getSessioniDisponibili(String tipo, 
    												  LocalDate inizio, 
    												  LocalDate fine) {
    	
        if (!tipo.isEmpty() && inizio != null && fine != null) {
        	
            return calendarioService.findSessioniDisponibili(tipo, inizio, fine);}
        
        if (!tipo.isEmpty()) {
        	
            return calendarioService.findSessioniDisponibili(tipo);}
        
        if (inizio != null && fine != null) {
        	
            return calendarioService.findSessioniDisponibili(inizio, fine);}

        return calendarioService.findSessioniDisponibili();
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public List<PartecipazioneCorso> getPartecipazioniCliente(String cfCliente){
    	
    	return calendarioService.findPartecipazioniByCliente(cfCliente);
    	
    }
    
	//----------------------------------------------------------------

    @Override
    public void prenotaSessione(String cf, String sessioneId) 
    throws ClienteNonAbbonatoException, ConflittoOrarioException,
           DatiNonTrovatiException, SessionePienaException {
        
        calendarioService.prenotaSessioneCorso(cf, sessioneId);
    }
    
	//----------------------------------------------------------------

    @Override
    public boolean annullaSessione(String cf, String sessioneId) {
    	
        return calendarioService.annullaPrenotazioneCorso(cf, sessioneId);
        
    }
    
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------

    // PT ////////////////////////////////////////////////////////////
    @Override
    public void prenotaLezionePT(String cf, 
            String staffId, 
            LocalDate data, 
            String fascia) throws ConflittoOrarioException, 
    							  ClienteNonAbbonatoException, 
    							  DatiNonTrovatiException {
	
    	calendarioService.prenotaLezionePT(cf, staffId, data, fascia);
	}
    
	//----------------------------------------------------------------

    @Override
    public boolean annullaLezionePT(String cf, 
    								String staffId, 
    								LocalDate data, 
    								String fascia) {
    	
        return calendarioService.annullaLezionePT(cf, staffId, data, fascia);
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public List<AppuntamentoPT> getAppuntamentiPT(String cf){
    	
    	return calendarioService.findLezioniPTByCliente(cf);
    	
    }

    @Override
    public List<AppuntamentoPT> getAppuntamentiPT(String cf, String staffId) {
    	
        List<AppuntamentoPT> result = new ArrayList<>();

        if (!cf.isEmpty() && !staffId.isEmpty()) {
        	
            for (AppuntamentoPT a : calendarioService.findLezioniPTByCliente(cf)) {
            	
                if (a.getStaffId().equalsIgnoreCase(staffId)) {
                	
                    result.add(a);
                    
                }
                
            }
            
        } else if (!cf.isEmpty()) {
        	
            result = calendarioService.findLezioniPTByCliente(cf);
            
        } else if (!staffId.isEmpty()) {
        	
            result = calendarioService.findLezioniPTByTrainer(staffId);
            
        } else {
        	
            for (Dipendente d : retrieveListaDip.retrieve()) {
            	
                String id = d.getStaffId();
                if (id != null && id.contains("DIP")) {
                	
                    result.addAll(calendarioService.findLezioniPTByTrainer(id));
                    
                }
                
            }
            
        }
        return result;
    }
    
	//----------------------------------------------------------------
	//----------------------------------------------------------------
	//----------------------------------------------------------------
    
    // CACHING ///////////////////////////////////////////////////////
    
    @Override
    public List<Turno> getTurniByRange(LocalDate inizio, LocalDate fine) {
    	
        return calendarioService.findTurniByRange(inizio, fine);
        
    }
    
	//----------------------------------------------------------------

    /*@Override
    public List<Calendario> getEventiByDate(LocalDate data) {
    	
        return calendarioService.findEventiByDate(data);
        
    }*/
    
	//----------------------------------------------------------------

    @Override
    public IRetrieveDipendentiFromDB getRetrieveDipendentiService() {
    	
        return retrieveListaDip;
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public List<SessioneCorso> findSessioniDisponibili(LocalDate inizio, LocalDate fine) {
        if (inizio != null && fine != null) {
            return calendarioService.findSessioniDisponibili(inizio, fine);
        }
        return new ArrayList<>();
    }
    
	//----------------------------------------------------------------
    
    @Override
    public List<Calendario> findEventiByDate(LocalDate data) {
    	
        if (data != null) {
        	
            return calendarioService.findEventiByDate(data);
            
        }
        
        return new ArrayList<>();
    }
    
	//----------------------------------------------------------------
    
}

