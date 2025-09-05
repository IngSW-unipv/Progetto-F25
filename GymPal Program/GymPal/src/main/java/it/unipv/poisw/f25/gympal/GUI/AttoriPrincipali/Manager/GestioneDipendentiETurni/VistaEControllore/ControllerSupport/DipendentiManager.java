package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.ControllerSupport;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.ControllerSupport.ObsAddDelDipendenti.IDipendentiChangeListener;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.PannelloDipendenti;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.CustomTableModelsForPanels.DipendentiTableModel;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class DipendentiManager {
	
	/*Servizi*/
	private IDipendentiCRUDFacadeService dipendentiService;
    private ICRUDDipendentiSupportServices support;
    
    /*Pannello*/
    private PannelloDipendenti dipendentiPanel;
    
    /**/
    private String staffIdSelezionato;

    /*Modello tabella*/
    private DipendentiTableModel tableModel;
    
    /*Per meccanismo observable*/
    private List<IDipendentiChangeListener> listeners;
    
    //--------------------------------------------------------------------

    public DipendentiManager(IDipendentiCRUDFacadeService dipendentiService,
                             ICRUDDipendentiSupportServices support,
                             PannelloDipendenti dipendentiPanel) {

    	/*Servizi*/
        this.dipendentiService = dipendentiService;
        this.support = support;
        
        /*Pannello*/
        this.dipendentiPanel = dipendentiPanel;
        
        /*Lista di ascoltatori*/
        listeners = new ArrayList<>();
    }

    //--------------------------------------------------------------------

    public void refreshTabellaDipendenti() {
    	
        try {
        	
            List<Dipendente> lista = dipendentiService.getAllDipendenti();
            tableModel = new DipendentiTableModel(lista);
            dipendentiPanel.getTabella().setModel(tableModel);
            dipendentiPanel.getTabella().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
        } catch (Exception e) {
        	
            support.getDialogUtils()
            	   .mostraErrore("Errore durante il caricamento dei dipendenti:\n" 
            			       + e.getMessage());
        }
        
    }

    //--------------------------------------------------------------------

    public void onSelezioneRiga() {
    	
        JTable table = dipendentiPanel.getTabella();
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
                
                notificaAggiornamentoDipendenti();
                
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
                
                notificaAggiornamentoDipendenti();
                
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
        dipendentiPanel.getTabella().clearSelection();
        staffIdSelezionato = null;
        
    }

    //--------------------------------------------------------------------
    /* Usato all'esterno di DipendentiManager dagli ascoltatori che si vogliono
     * registrare per ricevere aggiornamenti su eventuali cambiamenti riguardo
     * aggiunta/rimozione di profili dipendente*/
    public void addDipendentiChangeListener(IDipendentiChangeListener listener) {
    	
        listeners.add(listener);
        
    }
    
    //--------------------------------------------------------------------
    /* Usato internamente da DipendentiManager per notificare cambiamenti a tutti
     * gli ascoltatori registrati alla lista "listeners", tramite innesco del me-
     * todo 'onListaDipendentiAggiornata()' - ogni ascoltatore fa un proprio over-
     * ride di questo metodo, nel cui corpo sono inserite le azioni che l'ascolta-
     * tore deve compiere nel momento in cui gli è notificato un cambiamento*/
    private void notificaAggiornamentoDipendenti() {
    	
        for (IDipendentiChangeListener l : listeners) {
        	
            l.onListaDipendentiAggiornata();
            
        }
        
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
