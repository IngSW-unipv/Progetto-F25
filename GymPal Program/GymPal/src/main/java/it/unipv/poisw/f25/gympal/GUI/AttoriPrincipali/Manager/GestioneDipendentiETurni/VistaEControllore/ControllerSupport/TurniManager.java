package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.ControllerSupport;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.PannelloTurni;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.CustomTableModelsForPanels.TurnoTableModel;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;
import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class TurniManager {

    /* Servizi */
    private ITurniCRUDFacadeService turniService;
    private ICRUDTurniSupportServices support;
    
    /*Pannello*/
    private PannelloTurni turniPanel;
    
    /*Modello Tabella*/
    private TurnoTableModel tableModel;
    
    //------------------------------------------------------------

    public TurniManager(ITurniCRUDFacadeService turniService,
    					ICRUDTurniSupportServices support,
                        PannelloTurni turniPanel) {
    	
        this.turniService = turniService;
        this.support = support;
        this.turniPanel = turniPanel;
        
    }

    //------------------------------------------------------------
    
    public void refreshTabellaTurni() {
    	
        String inizioStr = turniPanel.getDataInizioFiltro();
        String fineStr = turniPanel.getDataFineFiltro();

        try {
        	
            LocalDate inizio = inizioStr.isEmpty() ? null : support.getDateUtils()
            													   .parseData(inizioStr);
            LocalDate fine = fineStr.isEmpty() ? null : support.getDateUtils()
            												   .parseData(fineStr);

            if ((inizio != null && fine == null) || 
            	(inizio == null && fine != null)) {
            	
                support.getDialogUtils()
                	   .mostraInfo("Inserisci entrambe le date o nessuna.");
                return;
                
            }

            if (inizio != null && 
            	fine != null && !support.getDateUtils()
            							.isRangeValido(inizio, fine)) {
            	
                support.getDialogUtils()
                	   .mostraInfo("La data fine non può precedere la data inizio.");
                return;
                
            }
            

            List<Turno> turni;

            if (inizio == null && fine == null) {
            	
                // Nessun filtro: mostra i turni del mese corrente
                LocalDate start = support.getDateRangeUtils()
                						 .getInizioMeseCorrente();
                LocalDate end = support.getDateRangeUtils()
                					   .getFineMeseCorrente();
                turni = turniService.findTurniByRange(start, end);
                tableModel = new TurnoTableModel(turni);
                turniPanel.getTabella().setModel(tableModel);
                
            } else {
            	
                // Filtro personalizzato
                turni = turniService.findTurniByRange(inizio, fine);
                
            }

            turniPanel.getTabella().setModel(new TurnoTableModel(turni));

        } catch (DateTimeParseException ex) {
        	
            support.getDialogUtils()
            	   .mostraErrore("Formato data non valido. Usa yyyy-MM-dd.");
            
        } catch (Exception ex) {
        	
            support.getDialogUtils()
            	   .mostraErrore("Errore nel recupero dei turni:\n" + ex.getMessage());
            ex.printStackTrace();
            
        }
        
    }

    //------------------------------------------------------------
    
    public void onCreaTurno() {
    	
        String dataStr = turniPanel.getDataTurno();
        String recMat = turniPanel.getRecMat();
        String recPom = turniPanel.getRecPom();
        String ptMat = turniPanel.getPtMat();
        String ptPom = turniPanel.getPtPom();

        if (dataStr.isEmpty() || recMat == null 
        					  || recPom == null 
        					  || ptMat == null 
        					  || ptPom == null) {
        	
            support.getDialogUtils()
            	   .mostraInfo("Tutti i campi devono essere compilati.");
            return;
            
        }

        LocalDate data = support.getDateUtils().parseData(dataStr);
        
        if (data == null) {
        	
            support.getDialogUtils()
            	   .mostraErrore("Formato data non valido. Usa yyyy-MM-dd.");
            return;
            
        }

        boolean success = turniService.creaTurno(data, recMat, recPom, ptMat, ptPom);

        if (success) {
        	
            support.getDialogUtils().mostraInfo("Turno creato con successo.");
            refreshTabellaTurni();
            
        } else {
        	
            support.getDialogUtils()
            	   .mostraErrore("Creazione turno fallita. Verifica che non sia"
            	   		       + " duplicato.");
        }
        
    }

    //------------------------------------------------------------
    
    public void onModificaTurno() {
    	
        JTable tabella = turniPanel.getTabella();
        int riga = tabella.getSelectedRow();

        if (riga == -1) {
        	
            support.getDialogUtils().mostraInfo("Seleziona un turno da modificare.");
            return;
            
        }

        int modelRow = tabella.convertRowIndexToModel(riga);
        Turno turnoEsistente = tableModel.getTurnoAt(modelRow);

        String dataStr = turniPanel.getDataTurno();
        String recMat = turniPanel.getRecMat();
        String recPom = turniPanel.getRecPom();
        String ptMat = turniPanel.getPtMat();
        String ptPom = turniPanel.getPtPom();

        if (dataStr.isEmpty() || recMat == null 
        					  || recPom == null 
        					  || ptMat == null 
        					  || ptPom == null) {
        	
            support.getDialogUtils().mostraInfo("Tutti i campi devono essere "
            								  + "compilati.");
            return;
            
        }

        LocalDate nuovaData = support.getDateUtils().parseData(dataStr);
        
        if (nuovaData == null) {
        	
            support.getDialogUtils()
            	   .mostraErrore("Formato data non valido. Usa yyyy-MM-dd.");
            return;
            
        }

        boolean success = turniService.modificaTurno(
        	    turnoEsistente.getData(), // ← data del turno da modificare
        	    recMat,
        	    recPom,
        	    ptMat,
        	    ptPom
        	);

        if (success) {
        	
            support.getDialogUtils().mostraInfo("Turno modificato con successo.");
            refreshTabellaTurni();
            
        } else {
        	
            support.getDialogUtils()
            	   .mostraErrore("Modifica fallita. Verifica i dati.");
            
        }
        
    }

    //------------------------------------------------------------
    
    public void onCancellaTurno() {
    	
        JTable tabella = turniPanel.getTabella();
        int riga = tabella.getSelectedRow();

        if (riga == -1) {
        	
            support.getDialogUtils().mostraInfo("Seleziona un turno da cancellare.");
            return;
            
        }

        int modelRow = tabella.convertRowIndexToModel(riga);
        Turno turno = tableModel.getTurnoAt(modelRow);

        int conferma = support.getDialogUtils().conferma(
                       "Vuoi davvero cancellare il turno del " + turno.getData() + "?",
                       "Conferma cancellazione");

        if (conferma != JOptionPane.YES_OPTION) return;

        boolean success = turniService.cancellaTurno(turno.getData());

        if (success) {
        	
            support.getDialogUtils().mostraInfo("Turno cancellato con successo.");
            refreshTabellaTurni();
            
        } else {
        	
            support.getDialogUtils().mostraErrore("Cancellazione fallita.");
            
        }
        
    }

    //------------------------------------------------------------
    
    public void onSelezioneRiga() {
    	
        JTable tabella = turniPanel.getTabella();
        int riga = tabella.getSelectedRow();

        if (riga == -1) {
        	
            turniPanel.pulisciCampi();
            return;
            
        }

        int modelRow = tabella.convertRowIndexToModel(riga);
        Turno turno = tableModel.getTurnoAt(modelRow);

        turniPanel.setDataTurno(turno.getData().toString());
        turniPanel.setRecMat(turno.getRecMat());
        turniPanel.setRecPom(turno.getRecPom());
        turniPanel.setPtMat(turno.getPtMat());
        turniPanel.setPtPom(turno.getPtPom());
        
    }

    //------------------------------------------------------------
    
    public void onPulisciTurniVecchi() {
    	
        int conferma = support.getDialogUtils().conferma(
                	   "Eliminare tutti i turni precedenti alla data odierna?",
                       "Conferma pulizia");

        if (conferma != JOptionPane.YES_OPTION) return;

        boolean success = turniService.pulisciTurniVecchi();

        if (success) {
        	
            support.getDialogUtils().mostraInfo("Turni vecchi eliminati.");
            refreshTabellaTurni();
            turniPanel.pulisciCampi();
            
        } else {
        	
            support.getDialogUtils().mostraErrore("Errore durante la pulizia.");
            
        }
        
    }

    //------------------------------------------------------------
    
    public void popolaComboDipendenti() {
        try {
            List<Dipendente> dipendenti = support.getRetrieveDipendentiService()
            							         .retrieve();

            List<String> recIds = dipendenti.stream()
                    			 .map(Dipendente::getStaffId)
                    			 .filter(id -> id != null && id.contains("REC"))
                    			 .collect(Collectors.toList());

            List<String> ptIds = dipendenti.stream()
                    		    .map(Dipendente::getStaffId)
                    		    .filter(id -> id != null && id.contains("DIP"))
                    		    .collect(Collectors.toList());

            turniPanel.setReceptionistsOptions(recIds);
            turniPanel.setPtOptions(ptIds);

        } catch (Exception e) {
        	
            support.getDialogUtils()
            	   .mostraErrore("Errore nel caricamento dei dipendenti:\n" 
            	  + e.getMessage());
            
        }
        
    }
    
    //------------------------------------------------------------
    
}

