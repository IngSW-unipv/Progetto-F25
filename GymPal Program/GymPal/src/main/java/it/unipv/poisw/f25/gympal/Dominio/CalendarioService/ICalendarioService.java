package it.unipv.poisw.f25.gympal.Dominio.CalendarioService;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;
import it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean.PartecipazioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;


/*
 * Interfaccia per i servizi legati alla gestione del calendario
 * Definisce il contratto per tutte le operazioni di business relative a 
 * prenotazioni di corsi di gruppo e lezioni con Personal Trainer
 */

public interface ICalendarioService {

    //Gestione Corsi di Gruppo 

    //Iscrive un cliente a una specifica sessione di un corso
    void prenotaSessioneCorso(String cfCliente, String idSessione) throws  ClienteNonAbbonatoException, ConflittoOrarioException, DatiNonTrovatiException;

    //Annulla la prenotazione di un cliente a una sessione di un corso 
    boolean annullaPrenotazioneCorso(String cfCliente, String idSessione);

    //Trova tutte le sessioni di corso disponibili (con posti liberi)
    List<SessioneCorso> findSessioniDisponibili();
    
    //Trova tutte le sessioni di corso disponibili per un certo tipo di corso
    List<SessioneCorso> findSessioniDisponibili(String tipoCorso);

    //Trova tutte le sessioni di corso disponibili in un range di date
    List<SessioneCorso> findSessioniDisponibili(LocalDate dataInizio, LocalDate dataFine);
    
    //Trova tutte le sessioni di corso disponibili di un certo tipo e in un range di date
    List<SessioneCorso> findSessioniDisponibili(String tipoCorso, LocalDate dataInizio, LocalDate dataFine);
    
    //Trova tutti i clienti iscritti a una specifica sessione
    List<Cliente> findClientiBySessione(String idSessione);
    
    //Trova tutte le partecipazioni di un cliente
    List<PartecipazioneCorso> findPartecipazioniByCliente(String cfCliente);
    
    //Trova tutte le partecipazioni di un cliente in un range di date
    List<PartecipazioneCorso> findPartecipazioniByClienteAndRange(String cfCliente, LocalDate dataInizio, LocalDate dataFine);
    
    
    
    //Gestione Appuntamenti Personal Trainer (PT)

    //Prenota una lezione individuale con un Personal Trainer 
    void prenotaLezionePT(String cf, String staffId, LocalDate data, String fasciaOraria) throws ConflittoOrarioException, ClienteNonAbbonatoException, DatiNonTrovatiException;

    //Annulla una lezione individuale con un Personal Trainer
    boolean annullaLezionePT(String cf, String staffId, LocalDate data, String fasciaOraria);
    
    //Trova tutte le lezioni PT prenotate da un cliente
    List<AppuntamentoPT> findLezioniPTByCliente(String cf);

    //Trova tutte le lezioni PT in agenda per un personal trainer
    List<AppuntamentoPT> findLezioniPTByTrainer(String staffId);
}

