package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.ControllerSupport;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneSessioniCorsi.ICRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.IEventiECorsiCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.PannelloCorsi;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.CustomTableModelsForPanels.SessioneCorsoTableModel;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class SessioniCorsiManager {

	/*Servizi*/
	private final ICorsiCRUDFacadeService gestioneSessioniService;
    private final ICalendarioFacadeService calendarioService;
    private final PannelloCorsi corsiPanel;
    private final ICRUDCorsiSupportServices supportoCostruzioneCorsi;

    /*Coordinatore*/
    private final IEventiECorsiCoordinator coordinator;

	//----------------------------------------------------------------

    public SessioniCorsiManager(ICorsiCRUDFacadeService gestioneSessioniService,
                                ICalendarioFacadeService calendarioService,
                                IEventiECorsiCoordinator coordinator,
                                ICRUDCorsiSupportServices supportoCostruzioneCorsi,
                                PannelloCorsi corsiPanel) {

        this.gestioneSessioniService = gestioneSessioniService;
        this.calendarioService = calendarioService;
        this.coordinator = coordinator;
        this.supportoCostruzioneCorsi = supportoCostruzioneCorsi;
        this.corsiPanel = corsiPanel;

    }

	//----------------------------------------------------------------

    public void onCreaSessione() {

        String id = corsiPanel.getIdSessioneFieldContent();
        String staffId = corsiPanel.getStaffIdField();
        String dataStr = corsiPanel.getDataField();
        String fascia = corsiPanel.getFasciaOrariaField();

        if (!supportoCostruzioneCorsi.getSessioneValidator()
                                     .campiObbligatoriCompilati(id, staffId, 
                                    		                    dataStr, fascia)) {
            supportoCostruzioneCorsi.getDialogUtils()
                                    .mostraInfo("Tutti i campi devono essere "
                                    		  + "compilati.");
            return;
            
        }

        LocalDate data = supportoCostruzioneCorsi.getDateUtils()
        									     .parseData(dataStr);
        
        if (data == null) {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraErrore("Formato data non valido. "
            								    + "Usa yyyy-MM-dd.");
            return;
            
        }

        if (!supportoCostruzioneCorsi.getFasciaValidator().isValid(fascia)) {
        	
            String errore = supportoCostruzioneCorsi.getFasciaValidator()
            									    .getErrore(fascia);
            supportoCostruzioneCorsi.getDialogUtils().mostraErrore(errore);
            return;
            
        }

        boolean success = gestioneSessioniService.creaSessione(id, staffId, 
        													   data, fascia);

        if (success) {
        	
            supportoCostruzioneCorsi.getDialogUtils().mostraInfo("Sessione "
            												   + "creata con successo.");
            refreshTabellaSessioni();
            
        } else {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraErrore("Impossibile creare la sessione."
            								    + " Verifica che l'ID non esista già.");
            
        }

    }

	//----------------------------------------------------------------

    public void onModificaSessione() {

        String id = corsiPanel.getIdSessioneFieldContent();
        String nuovoStaffId = corsiPanel.getStaffIdField();
        String nuovaDataStr = corsiPanel.getDataField();
        String nuovaFascia = corsiPanel.getFasciaOrariaField();

        if (!supportoCostruzioneCorsi.getSessioneValidator()
                                     .campiObbligatoriCompilati(id, nuovoStaffId, 
                                    		            nuovaDataStr, nuovaFascia)) {
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraInfo("Tutti i campi devono essere "
            								  + "compilati.");
            
            return;
            
        }

        LocalDate nuovaData = supportoCostruzioneCorsi.getDateUtils()
        										      .parseData(nuovaDataStr);
        if (nuovaData == null) {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraErrore("Formato data non valido. "
            								    + "Usa yyyy-MM-dd.");
            return;
            
        }

        if (!supportoCostruzioneCorsi.getFasciaValidator().isValid(nuovaFascia)) {
        	
            String errore = supportoCostruzioneCorsi.getFasciaValidator()
            		                                .getErrore(nuovaFascia);
            supportoCostruzioneCorsi.getDialogUtils().mostraErrore(errore);
            return;
            
        }

        boolean success = gestioneSessioniService.modificaSessione(id, nuovoStaffId, 
        													 nuovaData, nuovaFascia);

        if (success) {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraInfo("Sessione modificata con successo.");
            refreshTabellaSessioni();
            
        } else {
        	
            supportoCostruzioneCorsi.getDialogUtils()
                                    .mostraErrore("Impossibile modificare la sessione."
                                    		    + " Verifica che l'ID esista.");
            
        }

    }

	//----------------------------------------------------------------

    public void onCancellaSessione() {

        JTable tabella = corsiPanel.getSessioniTable();
        int rigaSelezionata = tabella.getSelectedRow();

        if (rigaSelezionata == -1) {
            supportoCostruzioneCorsi.getDialogUtils()
            					    .mostraInfo("Seleziona una sessione dalla"
            					    		  + " tabella da cancellare.");
            return;
            
        }

        int modelRow = tabella.convertRowIndexToModel(rigaSelezionata);
        SessioneCorsoTableModel model = (SessioneCorsoTableModel) tabella.getModel();
        SessioneCorso sessione = model.getSessioneAt(modelRow);
        String id = sessione.getSessioneId();

        int conferma = supportoCostruzioneCorsi.getDialogUtils()
                        					   .conferma("Vuoi davvero cancellare la"
                        					   		   + " sessione con ID: " + id + "?",
                                               "Conferma cancellazione");

        if (conferma != JOptionPane.YES_OPTION) return;

        boolean success = gestioneSessioniService.cancellaSessione(id);

        if (success) {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraInfo("Sessione cancellata con successo.");
            refreshTabellaSessioni();
            
        } else {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            					    .mostraErrore("Cancellazione fallita. "
            					    		    + "La sessione potrebbe non esistere.");
            
        }

    }

	//----------------------------------------------------------------

    public void onPulisciSessioni() {

        int conferma = supportoCostruzioneCorsi.getDialogUtils()
        									   .conferma("Questa operazione eliminerà"
        									   		   + " tutte le sessioni antecedenti "
        									   		   + "alla data odierna.\nSei sicuro "
        									   		   + "di procedere?",
                                                "Conferma pulizia sessioni vecchie");

        if (conferma != JOptionPane.YES_OPTION) return;

        boolean success = gestioneSessioniService.pulisciSessioniVecchie();

        if (success) {
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraInfo("Sessioni vecchie eliminate"
            								  + " con successo.");
            refreshTabellaSessioni();
            corsiPanel.pulisciCampi();
            
        } else {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraErrore("Errore durante la pulizia delle"
            								    + " sessioni vecchie.");
            
        }

    }

	//----------------------------------------------------------------

    public void onGeneraId() {

        List<SessioneCorso> sessioni = calendarioService
                                       .getSessioniDisponibili(corsiPanel.getTipoCorsoPerIdField(), null, null);

        coordinator.esponiGeneratoreID().initialize(sessioni);

        String tipo = corsiPanel.getTipoCorsoPerIdField().toUpperCase();

        if (!tipo.isEmpty()) {
        	
            String generatedId = coordinator.esponiGeneratoreID().generateId(tipo);
            corsiPanel.getIdSessioneField().setText(generatedId);
            
        } else {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraErrore("Inserisci il tipo corso prima"
            								    + " di generare l'ID.");
            
        }
        
    }

	//----------------------------------------------------------------

    public void popolaStaffIdComboBox() {

        try {
            List<Dipendente> dipendenti = calendarioService
                                           .getRetrieveDipendentiService()
                                           .retrieve();

            List<String> staffIds = dipendenti.stream()
                                              .map(Dipendente::getStaffId)
                                              .filter(id -> id != null && id.contains("DIP"))
                                              .collect(Collectors.toList());

            corsiPanel.setStaffIdOptions(staffIds);

        } catch (Exception e) {
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraErrore("Errore nel caricamento degli"
            								    + " staff ID:\n" + e.getMessage());
            
        }

    }

	//----------------------------------------------------------------

    public void refreshTabellaSessioni() {

        String tipo = corsiPanel.getTipoCorsoFiltro();
        String inizioStr = corsiPanel.getDataInizioFiltro();
        String fineStr = corsiPanel.getDataFineFiltro();

        if ((inizioStr.isEmpty() && !fineStr.isEmpty())
                || (!inizioStr.isEmpty() && fineStr.isEmpty())) {

            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraInfo("Inserisci entrambe le date o "
            								  + "nessuna.");
            
            return;
            
        }

        try {

            LocalDate inizio = inizioStr.isEmpty() ? null : LocalDate.parse(inizioStr);
            LocalDate fine = fineStr.isEmpty() ? null : LocalDate.parse(fineStr);

            if (!supportoCostruzioneCorsi.getDateUtils().isRangeValido(inizio, fine)) {
                supportoCostruzioneCorsi.getDialogUtils()
                						.mostraInfo("La data fine non può essere"
                								  + " precedente alla data inizio.");
                return;
                
            }

            List<SessioneCorso> sessioni = calendarioService
            		                      .getSessioniDisponibili(tipo, inizio, fine);
            corsiPanel.getSessioniTable()
                      .setModel(new SessioneCorsoTableModel(sessioni));

        } catch (DateTimeParseException ex) {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraErrore("Formato data non valido. "
            								    + "Usa yyyy-MM-dd.");
            
        } catch (Exception ex) {
        	
            supportoCostruzioneCorsi.getDialogUtils()
            						.mostraErrore("Errore durante "
            									 + "il recupero delle sessioni:\n" 
            								     + ex.getMessage());
            
            ex.printStackTrace();
        }

    }

	//----------------------------------------------------------------    

    public void onSelezioneRiga() {

        JTable tabella = corsiPanel.getSessioniTable();
        int rigaSelezionata = tabella.getSelectedRow();

        if (rigaSelezionata == -1) {
        	
            corsiPanel.pulisciCampi();
            return;
            
        }

        int modelRow = tabella.convertRowIndexToModel(rigaSelezionata);
        SessioneCorsoTableModel model = (SessioneCorsoTableModel) tabella.getModel();
        SessioneCorso sessione = model.getSessioneAt(modelRow);

        corsiPanel.getIdSessioneField().setText(sessione.getSessioneId());
        corsiPanel.setSelectedStaffId(sessione.getStaffId());
        corsiPanel.setDataField(sessione.getData().toString());
        corsiPanel.setFasciaOrariaField(sessione.getFasciaOraria());

    }

	//----------------------------------------------------------------

    public void onPulisciSessioniVecchie() {
    	
        onPulisciSessioni();
        
    }

	//----------------------------------------------------------------

}

