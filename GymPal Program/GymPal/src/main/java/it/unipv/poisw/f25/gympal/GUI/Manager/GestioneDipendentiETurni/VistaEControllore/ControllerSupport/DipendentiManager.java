package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.ControllerSupport;

import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.PannelloDipendenti;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.CustomTableModelsForPanels.DipendentiTableModel;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class DipendentiManager {

	private final IDipendentiCRUDFacadeService dipendentiService;
    private final ICRUDDipendentiSupportServices support;
    private final PannelloDipendenti dipendentiPanel;
    private String staffIdSelezionato;

    private DipendentiTableModel tableModel;
    
    //--------------------------------------------------------------------

    public DipendentiManager(IDipendentiCRUDFacadeService dipendentiService,
                             ICRUDDipendentiSupportServices support,
                             PannelloDipendenti dipendentiPanel) {

        this.dipendentiService = dipendentiService;
        this.support = support;
        this.dipendentiPanel = dipendentiPanel;
    }

    //--------------------------------------------------------------------

    public void refreshTabellaDipendenti() {
    	
        try {
        	
            List<Dipendente> lista = dipendentiService.getAllDipendenti();
            tableModel = new DipendentiTableModel(lista);
            dipendentiPanel.getDipendentiTable().setModel(tableModel);
            dipendentiPanel.getDipendentiTable().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
        } catch (Exception e) {
        	
            support.getDialogUtils()
            	   .mostraErrore("Errore durante il caricamento dei dipendenti:\n" 
            			       + e.getMessage());
        }
        
    }

    //--------------------------------------------------------------------

    public void onSelezioneRiga() {
    	
        JTable table = dipendentiPanel.getDipendentiTable();
        int selectedRow = table.getSelectedRow();

        if (selectedRow >= 0) {
        	
            int modelRow = table.convertRowIndexToModel(selectedRow);
            Dipendente d = tableModel.getDipendenteAt(modelRow);

            dipendentiPanel.setStaffId(d.getStaffId());
            dipendentiPanel.setNome(d.getNome());
            dipendentiPanel.setCognome(d.getCognome());
            dipendentiPanel.setContatto(d.getContatto());

            staffIdSelezionato = d.getStaffId();  // salva ID originale
            
        }
        
    }

    //--------------------------------------------------------------------

    public void onCreaDipendente() {
    	
        try {
        	
            Dipendente d = leggiInput();

            if (d == null) return;

            boolean success = dipendentiService.creaDipendente(d);

            if (success) {
            	
                refreshTabellaDipendenti();
                dipendentiPanel.pulisciCampi();
                support.getDialogUtils()
                	   .mostraInfo("Dipendente creato con successo.");
                
            } else {
            	
                support.getDialogUtils()
                       .mostraErrore("Creazione dipendente fallita.");
                
            }
            
        } catch (Exception e) {
        	
            support.getDialogUtils()
                   .mostraErrore("Errore durante la creazione:\n" + e.getMessage());
            
        }
        
    }

    //--------------------------------------------------------------------

    public void onModificaDipendente() {
    	
    	 try {
    	        Dipendente d = leggiInput();
    	        if (d == null) return;

    	        // Controllo chiave primaria modificata
    	        if (staffIdSelezionato != null && !staffIdSelezionato.equals(d.getStaffId())) {
    	        	
    	            support.getDialogUtils()
    	            	   .mostraErrore("Modifica dello Staff ID non consentita.\n"
    	                               + "Cancellare profilo attuale, ri-creare con nuovo"
    	                               + "Staff ID.");
    	            return;
    	            
    	        }

    	        boolean success = dipendentiService.aggiornaDipendente(d);

    	        if (success) {
    	        	
    	            refreshTabellaDipendenti();
    	            dipendentiPanel.pulisciCampi();
    	            support.getDialogUtils()
    	            	   .mostraInfo("Dipendente modificato con successo.");
    	            
    	        } else {
    	        	
    	            support.getDialogUtils()
    	            	   .mostraErrore("Modifica fallita.");
    	            
    	        }
    	        
    	    } catch (Exception e) {
    	    	
    	        support.getDialogUtils()
    	        	   .mostraErrore("Errore durante la modifica:\n" + e.getMessage());
    	        
    	    }
        
    }

    //--------------------------------------------------------------------

    public void onCancellaDipendente() {
    	
        try {
        	
            String staffId = dipendentiPanel.getStaffId();
            
            if (staffId == null || staffId.isEmpty()) {
            	
                support.getDialogUtils()
                	   .mostraInfo("Seleziona un dipendente da eliminare.");
                return;
                
            }

            Dipendente d = new Dipendente();
            d.setStaffId(staffId);

            boolean success = dipendentiService.cancellaDipendente(d);

            if (success) {
            	
                refreshTabellaDipendenti();
                dipendentiPanel.pulisciCampi();
                support.getDialogUtils()
                	   .mostraInfo("Dipendente eliminato con successo.");
                
            } else {
            	
                support.getDialogUtils().mostraErrore("Eliminazione fallita.");
                
            }
            
        } catch (Exception e) {
        	
            support.getDialogUtils()
            	   .mostraErrore("Errore durante l'eliminazione:\n" + e.getMessage());
            
        }
        
    }

    //--------------------------------------------------------------------

    public void onPulisciCampi() {
    	
        dipendentiPanel.pulisciCampi();
        dipendentiPanel.getDipendentiTable().clearSelection();
        staffIdSelezionato = null;
        
    }

    //--------------------------------------------------------------------

    private Dipendente leggiInput() {

        String staffId = dipendentiPanel.getStaffId().trim();
        String nome = dipendentiPanel.getNome().trim();
        String cognome = dipendentiPanel.getCognome().trim();
        String contatto = dipendentiPanel.getContatto().trim();

        if (staffId.isEmpty() || nome.isEmpty() || cognome.isEmpty()) {
            support.getDialogUtils().mostraInfo("Staff ID, nome e cognome sono "
            								  + "obbligatori.");
            return null;
        }

        return new Dipendente(staffId, nome, cognome, contatto);
    }
    
    //--------------------------------------------------------------------
	
}
