package it.unipv.poisw.f25.gympal.Dominio.CalendarioService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.persistence.IPersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.PersistenceFacade;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.ClienteBean.Cliente;
import it.unipv.poisw.f25.gympal.persistence.beans.PartecipazioneCorsoBean.PartecipazioneCorso;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class CalendarioService implements ICalendarioService {
	
	//PersistenceFacade per interagire col db
    private IPersistenceFacade persistence;

    public CalendarioService() {
        this.persistence = PersistenceFacade.getInstance();
    }

    //Gestione del calendario - corsi di gruppo
    
    @Override
    //Iscrive un cliente a una specifica sessione di un corso
    public void prenotaSessioneCorso(String cfCliente, String idSessione)
            throws ClienteNonAbbonatoException, ConflittoOrarioException, DatiNonTrovatiException {
        
        //Recupera i dati del cliente 
        Cliente clienteFiltro = new Cliente();
        clienteFiltro.setCf(cfCliente);
        Cliente cliente = persistence.selectCliente(clienteFiltro);
        
        //Recupera i dati della sessione
        SessioneCorso sessioneFiltro = new SessioneCorso();
        sessioneFiltro.setSessioneId(idSessione);
        SessioneCorso sessione = persistence.selectSessioneCorso(sessioneFiltro);

        if (cliente == null || sessione == null) {
            throw new DatiNonTrovatiException("Cliente con CF " + cfCliente + " o sessione con ID " + idSessione + " non trovati.");
        }

        //Controlla la validità dell'abbonamento del cliente
        controllaValiditaAbbonamento(cliente, sessione.getData());

        //Controlla se l'abbonamento del cliente include il tipo di corso
        String tipoCorso = estraiTipoCorsoDaIdSessione(idSessione);
        if (!cliente.getComposizioneAbbonamento().contains(tipoCorso)) {
            throw new ClienteNonAbbonatoException("L'abbonamento del cliente non include il corso di " + tipoCorso);
        }

        //Controlla conflitti di orario
        if (hasTimeConflict(cfCliente, sessione.getData(), sessione.getFasciaOraria())) {
            throw new ConflittoOrarioException("Il cliente ha già un altro impegno in questa fascia oraria.");
        }

        //Se tutti i controlli sono superati, inserisci la partecipazione
        PartecipazioneCorso nuovaPartecipazione = new PartecipazioneCorso(cfCliente, idSessione);
        persistence.insertPartecipazioneCorso(nuovaPartecipazione);
    }
    
    
    @Override
    //Annulla la prenotazione di un cliente a una sessione di un corso
    public boolean annullaPrenotazioneCorso(String cfCliente, String idSessione) {
        return persistence.deletePartecipazioneCorso(new PartecipazioneCorso(cfCliente, idSessione));
    }

    @Override
    //Trova tutte le sessioni di corso disponibili (con posti liberi)
    public List<SessioneCorso> findSessioniDisponibili() {
        return persistence.selectAllSessioniCorsi();
    }

    @Override
    //Trova tutte le sessioni di corso disponibili per un certo tipo di corso
    public List<SessioneCorso> findSessioniDisponibili(String tipoCorso) {
        SessioneCorso tipo = new SessioneCorso();
        tipo.setSessioneId(tipoCorso); 
        return persistence.selectAllAvailableSessioniByType(tipo);
    }

    @Override
    //Trova tutte le sessioni di corso disponibili in un range di date
    public List<SessioneCorso> findSessioniDisponibili(LocalDate dataInizio, LocalDate dataFine) {
        SessioneCorso inizio = new SessioneCorso();
        inizio.setData(dataInizio);
        SessioneCorso fine = new SessioneCorso();
        fine.setData(dataFine);
        return persistence.selectAllSessioniByRange(inizio, fine);
    }

    @Override
    //Trova tutte le sessioni di corso disponibili di un certo tipo e in un range di date
    public List<SessioneCorso> findSessioniDisponibili(String tipoCorso, LocalDate dataInizio, LocalDate dataFine) {
        SessioneCorso tipo = new SessioneCorso();
        tipo.setSessioneId(tipoCorso);
        SessioneCorso inizio = new SessioneCorso();
        inizio.setData(dataInizio);
        SessioneCorso fine = new SessioneCorso();
        fine.setData(dataFine);
        return persistence.selectAllAvailableSessioniByTypeAndRange(tipo, inizio, fine);
    }

    @Override
    //Trova tutti i clienti iscritti a una specifica sessione
    public List<Cliente> findClientiBySessione(String idSessione) {
        PartecipazioneCorso filtro = new PartecipazioneCorso();
        filtro.setSessioneId(idSessione);
        List<PartecipazioneCorso> partecipazioni = persistence.selectAllPartecipazioniBySessione(filtro);
        
        List<Cliente> clienti = new ArrayList<>();
        for (PartecipazioneCorso p : partecipazioni) {
            Cliente clienteFiltro = new Cliente();
            clienteFiltro.setCf(p.getCf());
            clienti.add(persistence.selectCliente(clienteFiltro));
        }
        return clienti;
    }

    @Override
    //Trova tutte le partecipazioni di un cliente
    public List<PartecipazioneCorso> findPartecipazioniByCliente(String cfCliente) {
        PartecipazioneCorso filtro = new PartecipazioneCorso();
        filtro.setCf(cfCliente);
        return persistence.selectAllPartecipazioniByCf(filtro);
    }

    @Override
    //Trova tutte le partecipazioni di un cliente in un range di date
    public List<PartecipazioneCorso> findPartecipazioniByClienteAndRange(String cfCliente, LocalDate dataInizio, LocalDate dataFine) {
        //Recupera tutte le iscrizioni ai corsi del cliente senza considerare la data
    	List<PartecipazioneCorso> tutteLePartecipazioni = findPartecipazioniByCliente(cfCliente);
        
    	//Converte questa lista in uno stream
        return tutteLePartecipazioni.stream()
        		//scorre lo stream e applica una condizione per decidere se tenere o scartare la partecipazione
                .filter(p -> {
                	//Recupera dal database la sessione di corso corrispondente alla partecipazione
                    SessioneCorso sessioneFiltro = new SessioneCorso();
                    sessioneFiltro.setSessioneId(p.getSessioneId());
                    SessioneCorso sessione = persistence.selectSessioneCorso(sessioneFiltro);
                    
                    if (sessione == null) {
                        return false;
                    }
                    
                    //Ottiene la data della sessione
                    LocalDate dataSessione = sessione.getData();
                    //Confronta la data con il range di date
                    boolean isAfterOrEqualsInizio = !dataSessione.isBefore(dataInizio);
                    boolean isBeforeOrEqualsFine = !dataSessione.isAfter(dataFine);
                    
                    return isAfterOrEqualsInizio && isBeforeOrEqualsFine;
                })
                //Restituisce una lista coi risultati
                .collect(Collectors.toList());
    }


    //Gestione Appuntamenti Personal Trainer (PT)

    @Override
    //Prenota una lezione individuale con un Personal Trainer
    public void prenotaLezionePT(String cf, String staffId, LocalDate data, String fasciaOraria)
            throws ConflittoOrarioException, ClienteNonAbbonatoException, DatiNonTrovatiException {
        
    	//Recupera i dati del cliente 
        Cliente clienteFiltro = new Cliente();
        clienteFiltro.setCf(cf);
        Cliente cliente = persistence.selectCliente(clienteFiltro);
        
        if (cliente == null) {
            throw new DatiNonTrovatiException("Cliente con CF " + cf + " non trovato.");
        }
        
        //Controlla la validità dell'abbonamento del cliente
        controllaValiditaAbbonamento(cliente, data);

        //Controlla se l'abbonamento del cliente include lezioni con PT
        if (!cliente.getComposizioneAbbonamento().startsWith("S.PESI")) {
            throw new ClienteNonAbbonatoException("L'abbonamento del cliente non include lezioni con Personal Trainer.");
        }
        
        //Controlla conflitti di orario
        if (hasTimeConflict(cf, data, fasciaOraria)) {
            throw new ConflittoOrarioException("Il cliente ha già un altro impegno in questa fascia oraria.");
        }
        
        //Se tutti i controlli sono superati, inserisci l'appuntamento
        AppuntamentoPT nuovoAppuntamento = new AppuntamentoPT(cf, staffId, data, fasciaOraria);
        persistence.insertAppuntamento(nuovoAppuntamento);
    }

    @Override
    //Annulla una lezione individuale con un Personal Trainer
    public boolean annullaLezionePT(String cf, String staffId, LocalDate data, String fasciaOraria) {
        return persistence.deleteAppuntamento(new AppuntamentoPT(cf, staffId, data, fasciaOraria));
    }

    @Override
    //Trova tutte le lezioni PT prenotate da un cliente
    public List<AppuntamentoPT> findLezioniPTByCliente(String cf) {
        AppuntamentoPT filtro = new AppuntamentoPT();
        filtro.setCf(cf);
        return persistence.selectAllAppuntamentiByCf(filtro);
    }

    @Override
    //Trova tutte le lezioni PT in agenda per un personal trainer
    public List<AppuntamentoPT> findLezioniPTByTrainer(String staffId) {
        AppuntamentoPT filtro = new AppuntamentoPT();
        filtro.setStaffId(staffId);
        return persistence.selectAllAppuntamentiByStaffId(filtro);
    }


    //Metodi helper
    
    //Controlla che la data fornita sia compresa nel periodo di validità dell'abbonamento del cliente
    private void controllaValiditaAbbonamento(Cliente cliente, LocalDate dataVerifica) throws ClienteNonAbbonatoException {
        if (dataVerifica.isBefore(cliente.getInizioAbbonamento()) || dataVerifica.isAfter(cliente.getFineAbbonamento())) {
            throw new ClienteNonAbbonatoException("L'abbonamento del cliente non è attivo in data " + dataVerifica);
        }
    }
    
    //Controlla che il cliente non abbia corsi o appuntamenti con PT nella data e ffascia oraria forniita
    
    private boolean hasTimeConflict(String cfCliente, LocalDate data, String fasciaOraria) {
        LocalTime[] nuovaFascia = parseFasciaOraria(fasciaOraria);
        if (nuovaFascia == null) return false;
        
        //Prende tutte le partecipazioni associate ad un cliente
        List<PartecipazioneCorso> partecipazioni = findPartecipazioniByCliente(cfCliente);
        //Per ciascuna partecipazione prende l'Id e lo usa per trovare la sessione associata
        for (PartecipazioneCorso p : partecipazioni) {
            SessioneCorso sessioneFiltro = new SessioneCorso();
            sessioneFiltro.setSessioneId(p.getSessioneId());
            SessioneCorso s = persistence.selectSessioneCorso(sessioneFiltro);
            
            //Controlla se la sessione esiste e la sua data corrisponde alla data in cui si vuole prenotare la sessione
            //Se le date corrispondono controlla se le fasce orarie si sovrappongono 
            if (s != null && s.getData().equals(data)) {
                LocalTime[] fasciaEsistente = parseFasciaOraria(s.getFasciaOraria());
                if (fasciaEsistente != null && isOverlapping(nuovaFascia, fasciaEsistente)) {
                    return true;
                }
            }
        }
        //Se non ha trovato conflitti con i corsi di gruppo, ripete il processo controllando la lista di appuntamenti con PT 
        List<AppuntamentoPT> appuntamenti = findLezioniPTByCliente(cfCliente);
        for (AppuntamentoPT a : appuntamenti) {
            if (a.getData().equals(data)) {
                LocalTime[] fasciaEsistente = parseFasciaOraria(a.getFasciaOraria());
                if (fasciaEsistente != null && isOverlapping(nuovaFascia, fasciaEsistente)) {
                    return true;
                }
            }
        }

        return false;
    }
    
    //Separa i due orari che compongono il dato fascia oraria (es 09.00-10.00) e li restituisce come LocalTime   
    private LocalTime[] parseFasciaOraria(String fasciaOraria) {
        try {
            String[] parts = fasciaOraria.split("-");
            LocalTime inizio = LocalTime.parse(parts[0]);
            LocalTime fine = LocalTime.parse(parts[1]);
            return new LocalTime[]{inizio, fine};
        } catch (Exception e) {
            System.err.println("Formato fascia oraria non valido: " + fasciaOraria);
            return null;
        }
    }
    
    //Controlla se le due fasce orarie si sovrappongono 
    private boolean isOverlapping(LocalTime[] fasciaA, LocalTime[] fasciaB) {
        return fasciaA[0].isBefore(fasciaB[1]) && fasciaB[0].isBefore(fasciaA[1]);
    }
    
    //Elimina dall'Id sessione (ggmmyyNOME_CORSO) la parte relativa alla data del corso (ggmmyy) la sciando solo il nome
    private String estraiTipoCorsoDaIdSessione(String idSessione) {
        return idSessione.replaceAll("^\\d+", "");
    }
}
