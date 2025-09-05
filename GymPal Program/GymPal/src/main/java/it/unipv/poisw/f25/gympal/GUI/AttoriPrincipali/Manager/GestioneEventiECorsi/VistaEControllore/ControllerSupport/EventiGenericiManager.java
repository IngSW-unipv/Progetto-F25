package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.VistaEControllore.ControllerSupport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.PannelloEventi;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.CustomTableModelsForPanels.EventiTableModel;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;

public class EventiGenericiManager {
	
    /*Pannello*/
    private PannelloEventi eventiPanel;
    
    /*Modello Tabella*/
    private EventiTableModel tableModel;
    
	/*Servizi*/
    private IEventiCRUDFacadeService eventiService;

    /*Supporto*/
    private ICRUDEventiSupportServices supportoGestionEventi;

    // ----------------------------------------------------------------

    public EventiGenericiManager(IEventiCRUDFacadeService eventiService,
    							 ICRUDEventiSupportServices supportoGestionEventi,
                                 PannelloEventi eventiPanel) {
    	
        this.eventiService = eventiService;
        this.supportoGestionEventi = supportoGestionEventi;
        this.eventiPanel = eventiPanel;
    }

    //----------------------------------------------------------------

    public void onCreaEvento() {
    	
        String nomeEvento = eventiPanel.getNomeEvento();
        String dataStr = eventiPanel.getDataEvento();
        String orainizioStr = eventiPanel.getOraInizio();
        String orafineStr = eventiPanel.getOraFine();
        String msg = eventiPanel.getMessaggio();
        String destinatario = eventiPanel.getDestinatario();

        if (!supportoGestionEventi.getEventoValidator()
        				 .campiObbligatoriCompilati(nomeEvento, 
        						 					dataStr, 
        						 					orainizioStr, orafineStr)) {
            supportoGestionEventi.getDialogUtils().mostraErrore("Tutti i campi "
            										 + "obbligatori devono essere"
            										 + " compilati.");
            return;
            
        }

        LocalDate data = supportoGestionEventi.getDateUtils().parseData(dataStr);
        LocalTime inizio = supportoGestionEventi.getOraUtils().parseOra(orainizioStr);
        LocalTime fine = supportoGestionEventi.getOraUtils().parseOra(orafineStr);

        if (data == null || inizio == null || fine == null) {
        	
            supportoGestionEventi.getDialogUtils().mostraErrore("Formato data o ora "
            										 + "non valido.");
            return;
            
        }

        if (!supportoGestionEventi.getOraUtils().isRangeValido(inizio, fine)) {
        	
            supportoGestionEventi.getDialogUtils().mostraErrore("L'orario di fine deve essere"
            										 + " successivo a quello di "
            										 + "inizio.");
            return;
            
        }

        boolean success = eventiService.creaEvento(nomeEvento, data, 
        										   inizio, fine, msg, destinatario);

        if (success) {
        	
            supportoGestionEventi.getDialogUtils().mostraInfo("Evento creato con successo.");
            refreshTabellaEventi();
            
        } else {
        	
            supportoGestionEventi.getDialogUtils()
            			.mostraErrore("Errore nella creazione dell'evento.");
            
        }
        
    }

    //----------------------------------------------------------------

    public void onModificaEvento() {
    	
        Calendario selezionato = getEventoSelezionato();
        
        if (selezionato == null) {
        	
            supportoGestionEventi.getDialogUtils()
            			.mostraInfo("Seleziona un evento da modificare.");
            return;
            
        }

        String nuovoNome = eventiPanel.getNomeEvento();
        String nuovaDataStr = eventiPanel.getDataEvento();
        String nuovaOraInizioStr = eventiPanel.getOraInizio();
        String nuovaOraFineStr = eventiPanel.getOraFine();
        String nuovoMsg = eventiPanel.getMessaggio();
        String nuovoDestinatario = eventiPanel.getDestinatario();

        if (!supportoGestionEventi.getEventoValidator()
        				 .campiObbligatoriCompilati(nuovoNome, nuovaDataStr, 
        						 					nuovaOraInizioStr, 
        						 					nuovaOraFineStr)) {
        	
            supportoGestionEventi.getDialogUtils()
            			.mostraErrore("Tutti i campi obbligatori devono essere "
            					    + "compilati.");
            return;
            
        }

        LocalDate nuovaData = supportoGestionEventi.getDateUtils().parseData(nuovaDataStr);
        LocalTime nuovaOraInizio = supportoGestionEventi.getOraUtils().parseOra(nuovaOraInizioStr);
        LocalTime nuovaOraFine = supportoGestionEventi.getOraUtils().parseOra(nuovaOraFineStr);

        if (nuovaData == null || nuovaOraInizio == null || nuovaOraFine == null) {
        	
            supportoGestionEventi.getDialogUtils()
            			.mostraErrore("Formato data o ora non valido.");
            return;
            
        }

        if (!supportoGestionEventi.getOraUtils()
        				 .isRangeValido(nuovaOraInizio, nuovaOraFine)) {
        	
            supportoGestionEventi.getDialogUtils().mostraErrore("L'orario di fine deve essere"
            		                                 + " successivo a quello di "
            		                                 + "inizio.");
            return;
            
        }

        boolean success = eventiService.modificaEvento(
        		
                selezionato.getNomeEvento(), selezionato.getDataEvento(),
                selezionato.getOraInizio(), selezionato.getOraFine(),
                nuovoNome, nuovaData, nuovaOraInizio, nuovaOraFine,
                nuovoMsg, nuovoDestinatario);

        if (success) {
            supportoGestionEventi.getDialogUtils()
            			.mostraInfo("Evento modificato con successo.");
            refreshTabellaEventi();
            
        } else {
        	
            supportoGestionEventi.getDialogUtils()
            			.mostraErrore("Errore nella modifica dell'evento.");
            
        }
        
    }

    //----------------------------------------------------------------

    public void onCancellaEvento() {
    	
        Calendario selezionato = getEventoSelezionato();
        
        if (selezionato == null) {
        	
            supportoGestionEventi.getDialogUtils()
            			.mostraInfo("Seleziona un evento da cancellare.");
            return;
            
        }

        int conferma = supportoGestionEventi.getDialogUtils()
        						   .conferma("Vuoi davvero cancellare l'evento \"" 
        								   	+ selezionato.getNomeEvento() + "\"?",
        								     "Conferma cancellazione");

        if (conferma != JOptionPane.YES_OPTION) return;

        boolean success = eventiService.cancellaEvento(
        		
                selezionato.getNomeEvento(),
                selezionato.getDataEvento(),
                selezionato.getOraInizio(),
                selezionato.getOraFine()
                
        );

        if (success) {
        	
            supportoGestionEventi.getDialogUtils()
            		    .mostraInfo("Evento cancellato con successo.");
            refreshTabellaEventi();
            eventiPanel.pulisciCampi();
            
        } else {
        	
            supportoGestionEventi.getDialogUtils()
            			.mostraErrore("Errore durante la cancellazione.");
            
        }
        
    }

    //----------------------------------------------------------------

    public void onSelezioneRiga() {
    	
        JTable tabella = eventiPanel.getTabella();
        int viewRow = tabella.getSelectedRow();

        if (viewRow == -1) {
        	
            eventiPanel.pulisciCampi();
            return;
            
        }

        int modelRow = tabella.convertRowIndexToModel(viewRow);
        Calendario evento = tableModel.getEventoAt(modelRow);


        // Verifica nullità e formattazione campi in modo sicuro
        String data = evento.getDataEvento() != null ? evento.getDataEvento()
        													 .toString() : "";
        String oraInizio = evento.getOraInizio() != null ? evento.getOraInizio()
        														 .toString() : "";
        String oraFine = evento.getOraFine() != null ? evento.getOraFine()
        													 .toString() : "";

        eventiPanel.setEventoFields(
        		
            evento.getNomeEvento(),
            data,
            oraInizio,
            oraFine,
            evento.getMessaggio(),
            evento.getDestinatarioMessaggio());
        
    }

    //----------------------------------------------------------------

    public void onPulisciEventiVecchi() {
    	
        int conferma = supportoGestionEventi.getDialogUtils()
        						   .conferma(
                  "Questa operazione eliminerà tutti gli eventi precedenti "
                + "alla data odierna.\nProcedere?",
                  "Conferma pulizia eventi vecchi");

        if (conferma != JOptionPane.YES_OPTION) return;

        boolean success = eventiService.pulisciEventiVecchi();

        if (success) {
        	
            supportoGestionEventi.getDialogUtils()
            			.mostraInfo("Eventi vecchi eliminati con successo.");
            refreshTabellaEventi();
            eventiPanel.pulisciCampi();
            
        } else {
        	
            supportoGestionEventi.getDialogUtils()
            			.mostraErrore("Errore durante la pulizia.");
            
        }
        
    }

    //----------------------------------------------------------------

    public void refreshTabellaEventi() {
    	
    	String inizioStr = eventiPanel.getDataInizioFiltro();
        String fineStr = eventiPanel.getDataFineFiltro();

        try {
        	
            LocalDate inizio = inizioStr.isEmpty() ? null : supportoGestionEventi.getDateUtils()
            															.parseData(inizioStr);
            LocalDate fine = fineStr.isEmpty() ? null : supportoGestionEventi.getDateUtils()
            														.parseData(fineStr);

            if ((inizio != null && fine == null) || 
            	(inizio == null && fine != null)) {
            	
                supportoGestionEventi.getDialogUtils()
                            .mostraInfo("Inserisci entrambe le date o nessuna.");
                return;
                
            }

            if (!supportoGestionEventi.getDateUtils().isRangeValido(inizio, fine)) {
            	
                 supportoGestionEventi.getDialogUtils()
                             .mostraInfo("La data fine non può precedere quella"
                             		   + " d'inizio.");
                return;
            }

            List<Calendario> eventi;

            if (inizio == null && fine == null) {
            	
                // Nessun filtro: mostra eventi del mese corrente
                LocalDate start = supportoGestionEventi.getDateRangeUtils()
                							  .getInizioMeseCorrente();
                LocalDate end = supportoGestionEventi.getDateRangeUtils()
                							.getFineMeseCorrente();
                eventi = eventiService.findEventiByRange(start, end);
                
            } else {
            	
                // Filtro personalizzato
                eventi = eventiService.findEventiByRange(inizio, fine);
                
            }

            tableModel = new EventiTableModel(eventi);
            eventiPanel.getTabella().setModel(tableModel);

        } catch (Exception e) {
            supportoGestionEventi.getDialogUtils()
                        .mostraErrore("Errore durante il caricamento eventi:\n" 
                        			 + e.getMessage());
        }
        
    }

    //----------------------------------------------------------------

    private Calendario getEventoSelezionato() {
    	
        JTable tabella = eventiPanel.getTabella();
        int riga = tabella.getSelectedRow();
        if (riga == -1) {return null;}

        /* Il metodo "convertRowIndexToModel" assicura la corretta mappatura
         * dell'indice di una riga a video con l'indice della medesima riga nel
         * table-model, a prescindere da come le righe siano ordinate a video. */
        int modelRow = tabella.convertRowIndexToModel(riga);
        return tableModel.getEventoAt(modelRow);

        
    }

    //----------------------------------------------------------------

}
