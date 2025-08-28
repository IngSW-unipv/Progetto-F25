package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.ControllerSupport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.PannelloEventi;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.CustomTableModelsForPanels.EventiTableModel;
import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;

public class EventiGenericiManager {
	
	/* Servizi */
    private final IEventiCRUDFacadeService eventiService;

    /* Supporti */
    private final ICRUDEventiSupportServices supportUtils;

    /* GUI */
    private final PannelloEventi eventiPanel;

    // ----------------------------------------------------------------

    public EventiGenericiManager(IEventiCRUDFacadeService eventiService,
    							 ICRUDEventiSupportServices supportUtils,
                                 PannelloEventi eventiPanel) {
    	
        this.eventiService = eventiService;
        this.supportUtils = supportUtils;
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

        if (!supportUtils.getEventoValidator()
        				 .campiObbligatoriCompilati(nomeEvento, 
        						 					dataStr, 
        						 					orainizioStr, orafineStr)) {
            supportUtils.getDialogUtils().mostraErrore("Tutti i campi "
            										 + "obbligatori devono essere"
            										 + " compilati.");
            return;
            
        }

        LocalDate data = supportUtils.getDateUtils().parseData(dataStr);
        LocalTime inizio = supportUtils.getOraUtils().parseOra(orainizioStr);
        LocalTime fine = supportUtils.getOraUtils().parseOra(orafineStr);

        if (data == null || inizio == null || fine == null) {
        	
            supportUtils.getDialogUtils().mostraErrore("Formato data o ora "
            										 + "non valido.");
            return;
            
        }

        if (!supportUtils.getOraUtils().isRangeValido(inizio, fine)) {
        	
            supportUtils.getDialogUtils().mostraErrore("L'orario di fine deve essere"
            										 + " successivo a quello di "
            										 + "inizio.");
            return;
            
        }

        boolean success = eventiService.creaEvento(nomeEvento, data, 
        										   inizio, fine, msg, destinatario);

        if (success) {
        	
            supportUtils.getDialogUtils().mostraInfo("Evento creato con successo.");
            refreshTabellaEventi();
            
        } else {
        	
            supportUtils.getDialogUtils()
            			.mostraErrore("Errore nella creazione dell'evento.");
            
        }
        
    }

    //----------------------------------------------------------------

    public void onModificaEvento() {
    	
        Calendario selezionato = getEventoSelezionato();
        
        if (selezionato == null) {
        	
            supportUtils.getDialogUtils()
            			.mostraInfo("Seleziona un evento da modificare.");
            return;
            
        }

        String nuovoNome = eventiPanel.getNomeEvento();
        String nuovaDataStr = eventiPanel.getDataEvento();
        String nuovaOraInizioStr = eventiPanel.getOraInizio();
        String nuovaOraFineStr = eventiPanel.getOraFine();
        String nuovoMsg = eventiPanel.getMessaggio();
        String nuovoDestinatario = eventiPanel.getDestinatario();

        if (!supportUtils.getEventoValidator()
        				 .campiObbligatoriCompilati(nuovoNome, nuovaDataStr, 
        						 					nuovaOraInizioStr, 
        						 					nuovaOraFineStr)) {
        	
            supportUtils.getDialogUtils()
            			.mostraErrore("Tutti i campi obbligatori devono essere "
            					    + "compilati.");
            return;
            
        }

        LocalDate nuovaData = supportUtils.getDateUtils().parseData(nuovaDataStr);
        LocalTime nuovaOraInizio = supportUtils.getOraUtils().parseOra(nuovaOraInizioStr);
        LocalTime nuovaOraFine = supportUtils.getOraUtils().parseOra(nuovaOraFineStr);

        if (nuovaData == null || nuovaOraInizio == null || nuovaOraFine == null) {
        	
            supportUtils.getDialogUtils()
            			.mostraErrore("Formato data o ora non valido.");
            return;
            
        }

        if (!supportUtils.getOraUtils()
        				 .isRangeValido(nuovaOraInizio, nuovaOraFine)) {
        	
            supportUtils.getDialogUtils().mostraErrore("L'orario di fine deve essere"
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
            supportUtils.getDialogUtils()
            			.mostraInfo("Evento modificato con successo.");
            refreshTabellaEventi();
            
        } else {
        	
            supportUtils.getDialogUtils()
            			.mostraErrore("Errore nella modifica dell'evento.");
            
        }
        
    }

    //----------------------------------------------------------------

    public void onCancellaEvento() {
    	
        Calendario selezionato = getEventoSelezionato();
        
        if (selezionato == null) {
        	
            supportUtils.getDialogUtils()
            			.mostraInfo("Seleziona un evento da cancellare.");
            return;
            
        }

        int conferma = supportUtils.getDialogUtils()
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
        	
            supportUtils.getDialogUtils()
            		    .mostraInfo("Evento cancellato con successo.");
            refreshTabellaEventi();
            eventiPanel.pulisciCampi();
            
        } else {
        	
            supportUtils.getDialogUtils()
            			.mostraErrore("Errore durante la cancellazione.");
            
        }
        
    }

    //----------------------------------------------------------------

    public void onSelezioneRiga() {
    	
        JTable tabella = eventiPanel.getEventiTable();
        int viewRow = tabella.getSelectedRow();

        if (viewRow == -1) {
        	
            eventiPanel.pulisciCampi();
            return;
            
        }

        int modelRow = tabella.convertRowIndexToModel(viewRow);
        EventiTableModel model = (EventiTableModel) tabella.getModel();
        Calendario evento = model.getEventoAt(modelRow);

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
    	
        int conferma = supportUtils.getDialogUtils()
        						   .conferma(
                  "Questa operazione eliminerà tutti gli eventi precedenti "
                + "alla data odierna.\nProcedere?",
                  "Conferma pulizia eventi vecchi");

        if (conferma != JOptionPane.YES_OPTION) return;

        boolean success = eventiService.pulisciEventiVecchi();

        if (success) {
        	
            supportUtils.getDialogUtils()
            			.mostraInfo("Eventi vecchi eliminati con successo.");
            refreshTabellaEventi();
            eventiPanel.pulisciCampi();
            
        } else {
        	
            supportUtils.getDialogUtils()
            			.mostraErrore("Errore durante la pulizia.");
            
        }
        
    }

    //----------------------------------------------------------------

    public void refreshTabellaEventi() {
    	
    	String inizioStr = eventiPanel.getDataInizioFiltro();
        String fineStr = eventiPanel.getDataFineFiltro();

        try {
        	
            LocalDate inizio = inizioStr.isEmpty() ? null : supportUtils.getDateUtils()
            															.parseData(inizioStr);
            LocalDate fine = fineStr.isEmpty() ? null : supportUtils.getDateUtils()
            														.parseData(fineStr);

            if ((inizio != null && fine == null) || 
            	(inizio == null && fine != null)) {
            	
                supportUtils.getDialogUtils()
                            .mostraInfo("Inserisci entrambe le date o nessuna.");
                return;
                
            }

            if (!supportUtils.getDateUtils().isRangeValido(inizio, fine)) {
            	
                 supportUtils.getDialogUtils()
                             .mostraInfo("La data fine non può precedere quella"
                             		   + " d'inizio.");
                return;
            }

            List<Calendario> eventi;

            if (inizio == null && fine == null) {
            	
                // Nessun filtro: mostra eventi del mese corrente
                LocalDate start = supportUtils.getDateRangeUtils()
                							  .getInizioMeseCorrente();
                LocalDate end = supportUtils.getDateRangeUtils()
                							.getFineMeseCorrente();
                eventi = eventiService.findEventiByRange(start, end);
                
            } else {
            	
                // Filtro personalizzato
                eventi = eventiService.findEventiByRange(inizio, fine);
                
            }

            eventiPanel.getEventiTable()
                       .setModel(new EventiTableModel(eventi));

        } catch (Exception e) {
            supportUtils.getDialogUtils()
                        .mostraErrore("Errore durante il caricamento eventi:\n" 
                        			 + e.getMessage());
        }
        
    }

    //----------------------------------------------------------------

    private Calendario getEventoSelezionato() {
    	
        JTable tabella = eventiPanel.getEventiTable();
        int riga = tabella.getSelectedRow();
        if (riga == -1) return null;

        int modelRow = tabella.convertRowIndexToModel(riga);
        EventiTableModel model = (EventiTableModel) tabella.getModel();
        return model.getEventoAt(modelRow);
        
    }

    //----------------------------------------------------------------

}
