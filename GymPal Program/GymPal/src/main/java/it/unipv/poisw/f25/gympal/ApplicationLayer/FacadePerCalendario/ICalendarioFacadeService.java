package it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.SessionePienaException;
import it.unipv.poisw.f25.gympal.Dominio.DataTransferServices.FromDB.RetrieveDipendenti.IRetrieveDipendentiFromDB;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public interface ICalendarioFacadeService {
	
	//CORSI///////////////////////////////////////////////////////////
	
	public List<SessioneCorso> getSessioniDisponibili(String tipo, 
													  LocalDate inizio, 
													  LocalDate fine);
	
	//----------------------------------------------------------------
	
	public void prenotaSessione(String cf, String sessioneId) 
		   throws ClienteNonAbbonatoException, ConflittoOrarioException,
		          DatiNonTrovatiException, SessionePienaException;
	
	//----------------------------------------------------------------
	
	public boolean annullaSessione(String cf, String sessioneId);
	
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	
    // PT ////////////////////////////////////////////////////////////
	
    public void prenotaLezionePT(String cf, 
					             String staffId, 
					             LocalDate data, 
					             String fascia) throws ConflittoOrarioException, 
					    							   ClienteNonAbbonatoException, 
					    							   DatiNonTrovatiException;
	
	//----------------------------------------------------------------
    
    public boolean annullaLezionePT(String cf, 
									String staffId, 
									LocalDate data, 
									String fascia);
    
	//----------------------------------------------------------------
    
    public List<AppuntamentoPT> getAppuntamentiPT(String cf, String staffId);
    
	//----------------------------------------------------------------
    
	//----------------------------------------------------------------
    
    // CACHES BUILDING ///////////////////////////////////////////////
    
    public List<Turno> getTurniByRange(LocalDate inizio, LocalDate fine);
    
	//----------------------------------------------------------------
    
    //public List<Calendario> getEventiByDate(LocalDate data);
    
	//----------------------------------------------------------------
    
    public IRetrieveDipendentiFromDB getRetrieveDipendentiService();
    
	//----------------------------------------------------------------
    
    public List<SessioneCorso> findSessioniDisponibili(LocalDate inizio, 
    												   LocalDate fine);
    
	//----------------------------------------------------------------
    
    public List<Calendario> findEventiByDate(LocalDate data);
    
	//----------------------------------------------------------------

}
