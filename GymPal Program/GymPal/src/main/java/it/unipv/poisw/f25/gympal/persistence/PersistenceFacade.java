package it.unipv.poisw.f25.gympal.persistence;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPTDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.IAppuntamentoPTDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.CalendarioDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.ICalendarioDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.ClienteDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.IClienteDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.DipendenteDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.IDipendenteDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean.IPartecipazioneCorsoDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean.PartecipazioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean.PartecipazioneCorsoDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.IScontoDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.ScontoDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.ISessioneCorsoDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorsoDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.ITurnoDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.TurnoDAO;
import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;
import it.unipv.poisw.f25.gympal.persistence.setup.PersistenceManager;

//Implementazione del pattern Facade e Singleton per l'accesso allo strato di persistenza
public class PersistenceFacade implements IPersistenceFacade {

    
    private static IPersistenceFacade instance;

    private final IClienteDAO clienteDAO;
    private final IDipendenteDAO dipendenteDAO;
    private final ICalendarioDAO calendarioDAO;
    private final IPartecipazioneCorsoDAO partecipazioneCorsoDAO;
    private final IScontoDAO scontoDAO;
    private final ISessioneCorsoDAO sessioneCorsoDAO;
    private final ITurnoDAO turnoDAO;
    private final IAppuntamentoPTDAO appuntamentoPTDAO;

    
    private PersistenceFacade() {
        IConnectionFactory connectionFactory = PersistenceManager.getConnectionFactory();
        
        this.clienteDAO = new ClienteDAO(connectionFactory);
        this.dipendenteDAO = new DipendenteDAO(connectionFactory);
        this.calendarioDAO = new CalendarioDAO(connectionFactory);
        this.partecipazioneCorsoDAO = new PartecipazioneCorsoDAO(connectionFactory);
        this.scontoDAO = new ScontoDAO(connectionFactory);
        this.sessioneCorsoDAO = new SessioneCorsoDAO(connectionFactory);
        this.turnoDAO = new TurnoDAO(connectionFactory);
        this.appuntamentoPTDAO = new AppuntamentoPTDAO(connectionFactory);
    }

    //Metodo statico per ottenere l'unica istanza della classe
    public static IPersistenceFacade getInstance() {
        if (instance == null) {
            instance = new PersistenceFacade();
        }
        return instance;
    }

    //Metodi Cliente
    @Override
    public List<Cliente> selectAllClienti() {
        return clienteDAO.selectAll();
    }
    @Override
    public Cliente selectCliente(Cliente cliente) {
        return clienteDAO.selectCliente(cliente);
    }
    @Override
    public boolean insertCliente(Cliente cliente) {
        return clienteDAO.insertCliente(cliente);
    }
    @Override
    public boolean updateCliente(Cliente cliente) {
        return clienteDAO.updateCliente(cliente);
    }
    @Override
    public boolean deleteCliente(Cliente cliente) {
        return clienteDAO.deleteCliente(cliente);
    }
   

    //Metodi Dipendente
    @Override
    public List<Dipendente> selectAllDipendenti() {
        return dipendenteDAO.selectAll();
    }
    @Override
    public Dipendente selectDipendente(Dipendente dipendente) {
        return dipendenteDAO.selectDipendente(dipendente);
    }
    @Override
    public boolean insertDipendente(Dipendente dipendente) {
        return dipendenteDAO.insertDipendente(dipendente);
    }
    @Override
    public boolean updateDipendente(Dipendente dipendente) {
        return dipendenteDAO.updateDipendente(dipendente);
    }
    @Override
    public boolean deleteDipendente(Dipendente dipendente) {
        return dipendenteDAO.deleteDipendente(dipendente);
    }
    

    //Metodi Calendario
    @Override
    public boolean insertEvento(Calendario evento) {
        return calendarioDAO.insertEvento(evento);
    }
    @Override
    public boolean updateEvento(Calendario evento) {
        return calendarioDAO.updateEvento(evento);
    }
    @Override
    public boolean deleteEvento(Calendario evento) {
        return calendarioDAO.deleteEvento(evento);
    }
    @Override
    public boolean deleteOldEventi() {
        return calendarioDAO.deleteOldEventi();
    }
    @Override
    public Calendario selectEvento(Calendario evento) {
        return calendarioDAO.selectEvento(evento);
    }
    @Override
    public List<Calendario> selectAllEventiByDate(Calendario evento) {
        return calendarioDAO.selectAllEventiByDate(evento);
    }
    @Override
    public List<Calendario> selectAllEventiByDateRange(Calendario eventoInizio, Calendario eventoFine) {
        return calendarioDAO.selectAllEventiByDateRange(eventoInizio, eventoFine);
    }
    

    //Metodi PartecipazioneCorso
    @Override
    public List<PartecipazioneCorso> selectAllPartecipazioni() {
        return partecipazioneCorsoDAO.selectAll();
    }
    @Override
    public List<PartecipazioneCorso> selectAllPartecipazioniByCf(PartecipazioneCorso partecipazione) {
        return partecipazioneCorsoDAO.selectAllPartecipazioniByCf(partecipazione);
    }
    @Override
    public List<PartecipazioneCorso> selectAllPartecipazioniBySessione(PartecipazioneCorso partecipazione) {
        return partecipazioneCorsoDAO.selectAllPartecipazioniBySessione(partecipazione);
    }
    @Override
    public List<PartecipazioneCorso> selectAllPartecipazioniByCfAndRange(PartecipazioneCorso cf, PartecipazioneCorso inizio, PartecipazioneCorso fine) {
        return partecipazioneCorsoDAO.selectAllPartecipazioniByCfAndRange(cf, inizio, fine);
    }
    @Override
    public boolean insertPartecipazioneCorso(PartecipazioneCorso partecipazione) {
        return partecipazioneCorsoDAO.insertPartecipazioneCorso(partecipazione);
    }
    @Override
    public boolean deletePartecipazioneCorso(PartecipazioneCorso partecipazione) {
        return partecipazioneCorsoDAO.deletePartecipazioneCorso(partecipazione);
    }
    @Override
    public boolean deleteOldPartecipazioni() {
        return partecipazioneCorsoDAO.deleteOldPartecipazioni();
    }
    

    //Metodi Sconto
    @Override
    public List<Sconto> selectAllSconti() {
        return scontoDAO.selectAll();
    }
    @Override
    public List<Sconto> selectAllScontiByDate(Sconto sconto) {
        return scontoDAO.selectAllScontiByDate(sconto);
    }
    @Override
    public Sconto selectSconto(Sconto sconto) {
        return scontoDAO.selectSconto(sconto);
    }
    @Override
    public boolean insertSconto(Sconto sconto) {
        return scontoDAO.insertSconto(sconto);
    }
    @Override
    public boolean updateSconto(Sconto sconto) {
        return scontoDAO.updateSconto(sconto);
    }
    @Override
    public boolean deleteSconto(Sconto sconto) {
        return scontoDAO.deleteSconto(sconto);
    }
    

    //Metodi SessioneCorso
    @Override
    public List<SessioneCorso> selectAllSessioniCorsi() {
        return sessioneCorsoDAO.selectAll();
    }
    @Override
    public SessioneCorso selectSessioneCorso(SessioneCorso sessione) {
        return sessioneCorsoDAO.selectSessioneCorso(sessione);
    }
    @Override
    public List<SessioneCorso> selectAllSessioniByDate(SessioneCorso sessione) {
        return sessioneCorsoDAO.selectAllSessioniByDate(sessione);
    }
    @Override
    public List<SessioneCorso> selectAllSessioniByRange(SessioneCorso sessioneInizio, SessioneCorso sessioneFine) {
        return sessioneCorsoDAO.selectAllSessioniByRange(sessioneInizio, sessioneFine);
    }
    @Override
    public List<SessioneCorso> selectAllSessioniByType(SessioneCorso sessione) {
        return sessioneCorsoDAO.selectAllSessioniByType(sessione);
    }
    @Override
    public List<SessioneCorso> selectAllSessioniByTypeAndRange(SessioneCorso sessioneTipo, SessioneCorso sessioneInizio, SessioneCorso sessioneFine) {
        return sessioneCorsoDAO.selectAllSessioniByTypeAndRange(sessioneTipo, sessioneInizio, sessioneFine);
    }
    @Override
    public List<SessioneCorso> selectAllAvailableSessioniByType(SessioneCorso sessione) {
        return sessioneCorsoDAO.selectAllAvailableSessioniByType(sessione);
    }
    @Override
    public List<SessioneCorso> selectAllAvailableSessioniByTypeAndRange(SessioneCorso sessioneTipo, SessioneCorso sessioneInizio, SessioneCorso sessioneFine) {
        return sessioneCorsoDAO.selectAllAvailableSessioniByTypeAndRange(sessioneTipo, sessioneInizio, sessioneFine);
    }
    @Override
    public boolean insertSessioneCorso(SessioneCorso sessione) {
        return sessioneCorsoDAO.insertSessioneCorso(sessione);
    }
    @Override
    public boolean updateSessioneCorso(SessioneCorso sessione) {
        return sessioneCorsoDAO.updateSessioneCorso(sessione);
    }
    @Override
    public boolean deleteSessioneCorso(SessioneCorso sessione) {
        return sessioneCorsoDAO.deleteSessioneCorso(sessione);
    }
    @Override
    public boolean deleteOldSessioni() {
        return sessioneCorsoDAO.deleteOldSessioni();
    }
    

    //Metodi Turno
    @Override
    public List<Turno> selectAllTurni() {
        return turnoDAO.selectAll();
    }
    @Override
    public Turno selectTurno(Turno turno) {
        return turnoDAO.selectTurno(turno);
    }
    @Override
    public List<Turno> selectAllTurniByPerson(Turno turno) {
        return turnoDAO.selectAllTurniByPerson(turno);
    }
    @Override
    public List<Turno> selectAllTurniByRange(Turno turnoInizio, Turno turnoFine) {
        return turnoDAO.selectAllTurniByRange(turnoInizio, turnoFine);
    }
    @Override
    public boolean insertTurno(Turno turno) {
        return turnoDAO.insertTurno(turno);
    }
    @Override
    public boolean updateTurno(Turno turno) {
        return turnoDAO.updateTurno(turno);
    }
    @Override
    public boolean deleteTurno(Turno turno) {
        return turnoDAO.deleteTurno(turno);
    }
    @Override
    public boolean deleteOldTurni() {
        return turnoDAO.deleteOldTurni();
    }

    //Metodi AppuntamentoPT
    @Override
    public boolean insertAppuntamento(AppuntamentoPT appuntamento) {
        return appuntamentoPTDAO.insertAppuntamento(appuntamento);
    }
    @Override
    public boolean deleteAppuntamento(AppuntamentoPT appuntamento) {
        return appuntamentoPTDAO.deleteAppuntamento(appuntamento);
    }
    @Override
    public AppuntamentoPT findAppuntamento(AppuntamentoPT appuntamento) {
        return appuntamentoPTDAO.findAppuntamento(appuntamento);
    }
    @Override
    public List<AppuntamentoPT> selectAllAppuntamenti() {
        return appuntamentoPTDAO.selectAll();
    }
    @Override
    public List<AppuntamentoPT> selectAllAppuntamentiByCf(AppuntamentoPT appuntamento) {
        return appuntamentoPTDAO.selectAllAppuntamentiByCf(appuntamento);
    }
    @Override
    public List<AppuntamentoPT> selectAllAppuntamentiByStaffId(AppuntamentoPT appuntamento) {
        return appuntamentoPTDAO.selectAllAppuntamentiByStaffId(appuntamento);
    }
    @Override
    public List<AppuntamentoPT> selectAllAppuntamentiByDate(AppuntamentoPT appuntamento) {
        return appuntamentoPTDAO.selectAllAppuntamentiByDate(appuntamento);
    }
    @Override
    public boolean deleteOldAppuntamenti() {
        return appuntamentoPTDAO.deleteOldAppuntamenti();
    }
}
