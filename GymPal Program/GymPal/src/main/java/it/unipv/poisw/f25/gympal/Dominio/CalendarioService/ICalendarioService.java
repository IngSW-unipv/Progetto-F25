package it.unipv.poisw.f25.gympal.Dominio.CalendarioService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.SessionePienaException;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;
import it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean.PartecipazioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;


/*
 * Interfaccia per i servizi legati alla gestione del calendario
 * Definisce il contratto per tutte le operazioni di business relative a 
 * prenotazioni di corsi di gruppo e lezioni con Personal Trainer
 */

public interface ICalendarioService {

    //Gestione Corsi di Gruppo 

    //Iscrive un cliente a una specifica sessione di un corso
    void prenotaSessioneCorso(String cfCliente, String idSessione) throws  ClienteNonAbbonatoException, ConflittoOrarioException, DatiNonTrovatiException, SessionePienaException;

    //Annulla la prenotazione di un cliente a una sessione di un corso 
    boolean annullaPrenotazioneCorso(String cfCliente, String idSessione);
    
    //Cancella le prenotazioni più vecchie della  data corrente
    boolean pulisciPrenotazioniVecchie();

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
    
	//Cancella le lezioni più vecchie della data corrente
    boolean pulisciLezioniVecchie();
    
    //Trova tutte le lezioni PT prenotate da un cliente
    List<AppuntamentoPT> findLezioniPTByCliente(String cf);

    //Trova tutte le lezioni PT in agenda per un personal trainer
    List<AppuntamentoPT> findLezioniPTByTrainer(String staffId);
    
    
    
    // Gestione Sessioni Corso - Lato Staff
    
    //Crea una sessione per un corso
    boolean creaSessioneCorso(String idSessione, String staffId, LocalDate data, String fasciaOraria);
    
    //Modifica una sessione per un corso
    boolean modificaSessioneCorso(String idSessione, String nuovoStaffId, LocalDate nuovaData, String nuovaFasciaOraria);
    
    //Elimina una sessione per un corso
    boolean cancellaSessioneCorso(String idSessione);
    
    //Elimina le sessioni più vecchie della data corrente
    boolean pulisciSessioniVecchie();
    

    // Gestione Turni Staff

    //Crea un turno
    boolean creaTurno(LocalDate data, String recMat, String recPom, String ptMat, String ptPom);
    
    //Modifica un turno
    boolean modificaTurno(LocalDate data, String recMat, String recPom, String ptMat, String ptPom);
    
    //Elimina un turno
    boolean cancellaTurno(LocalDate data);
    
    //Trova tutti i turni in un range di date
    List<Turno> findTurniByRange(LocalDate dataInizio, LocalDate dataFine);
    
    //Cancella i turni di lavoro più vecchi della  data corrente
    boolean pulisciTurniVecchi();
    
    

    // Gestione Eventi Generici del Calendario (Manutenzione, Eventi, etc.)
    
    //Crea un evento nel Calendario
    boolean creaEventoCalendario(String nomeEvento, LocalDate dataEvento, LocalTime oraInizio, LocalTime oraFine, String messaggio, String destinatario);
    
    //Modifica un evento nel Calendario
    boolean modificaEventoCalendario(String nomeEventoOriginale, LocalDate dataEventoOriginale, LocalTime oraInizioOriginale, LocalTime oraFineOriginale, String nuovoNomeEvento, LocalDate nuovaData, LocalTime nuovaOraInizio, LocalTime nuovaOraFine, String nuovoMessaggio, String nuovoDestinatario);
    
    //Trova un singolo evento
    Calendario findEvento(String nomeEvento, LocalDate dataEvento, LocalTime oraInizio, LocalTime oraFine);
    
    //Trova tutti gli eventi in una data specifica
    List<Calendario> findEventiByDate(LocalDate data);
    
    //Trova tutti gli eventi in un intervallo di date
    List<Calendario> findEventiByRange(LocalDate dataInizio, LocalDate dataFine);
    
    //Elimina un evento nel Calendario
    boolean cancellaEventoCalendario(String nomeEvento, LocalDate dataEvento, LocalTime oraInizio, LocalTime oraFine);

    //Cancella gli eventi generici del calendario più vecchi della  data corrente
    boolean pulisciEventiVecchi();
    
    
    //Metodo che raccoglie tutti i metodi che puliscono il database da dati vecchi
    boolean pulisciDatiObsoleti();
}

