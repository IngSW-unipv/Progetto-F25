package it.unipv.poisw.f25.gympal.persistence;

import java.util.List;

import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;

import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.IClienteDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.IDipendenteDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.ICalendarioDAO;

import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.ClienteDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.DipendenteDAO;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.CalendarioDAO;

import it.unipv.poisw.f25.gympal.persistence.connection.IConnectionFactory;


public class PersistenceFacade implements IPersistenceFacade {

    private final IClienteDAO clienteDAO;
    private final IDipendenteDAO dipendenteDAO;
    private final ICalendarioDAO calendarioDAO;

    //Costruttore
    //Riceve una factory per le connessioni e la utilizza per inizializzare tutti i DAO
    public PersistenceFacade(IConnectionFactory connectionFactory) {
        this.clienteDAO = new ClienteDAO(connectionFactory);
        this.dipendenteDAO = new DipendenteDAO(connectionFactory);
        this.calendarioDAO = new CalendarioDAO(connectionFactory);
    }

    //Implementazione metodi Cliente
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
    

    //Implementazione metodi Dipendente
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

    
    //Implementazione metodi Calendario
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
}
