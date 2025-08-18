package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.SessionePienaException;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ControllerSupport.AppuntamentoPTManager;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ControllerSupport.SessioneCorsoManager;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.AppuntamentiPTPanel;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.IscrizioneCorsiPanel;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.CustomTableModelsForPanels.AppuntamentoPTTableModel;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.CustomTableModelsForPanels.SessioneCorsoTableModel;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class ManipolazioneController {
	
	/*Vista*/
	private IManipolazioneFrame frame;
	
    /*Coordinatore*/
    private final ICoordinatoreCalendario coordinator;
    
    /*Servizi*/
    private ICalendarioFacadeService calendarioFacade;

    /*Managers*/
    private final SessioneCorsoManager corsoManager;
    private final AppuntamentoPTManager ptManager;
    
	//----------------------------------------------------------------
	
	public ManipolazioneController(IManipolazioneFrame frame,
								   ICalendarioFacadeService calendarioFacade,
								   ICoordinatoreCalendario coordinator) {
		/*Vista*/
		this.frame = frame;
		
		/*Coordinatore*/
		this.coordinator = coordinator;
		
		/*Servizi*/
		this.calendarioFacade = calendarioFacade;
		
		/*Managers*/
	    this.corsoManager = new SessioneCorsoManager(calendarioFacade, this.coordinator);
	    this.ptManager = new AppuntamentoPTManager(calendarioFacade, this.coordinator);
		
        inizializzaPannelli();
        		
	}
	
	//----------------------------------------------------------------
	
	private void inizializzaPannelli() {
		
	    inizializzaCorsiPanel();
	    inizializzaAppuntamentiPTPanel();
	    refreshTables(); // Caricamento iniziale del contenuto tabelle
	    
	}
	
	//----------------------------------------------------------------
	
	private void inizializzaCorsiPanel() {
		
	    IscrizioneCorsiPanel corsiPanel = (IscrizioneCorsiPanel) 
	    								   frame.getCorsiPanel();

	    corsiPanel.addFiltroListener(e -> refreshTables());

	    corsiPanel.addIscriviBtnListener(e -> gestisciIscrizioneCorso(corsiPanel));

	    corsiPanel.addAnnullaIscrizioneBtnListener(e -> gestisciAnnullamentoCorso(corsiPanel));
	    
	}
	
	//----------------------------------------------------------------
	
	private void inizializzaAppuntamentiPTPanel() {
		
	    AppuntamentiPTPanel apptPanel = (AppuntamentiPTPanel) frame.getAppuntamentiPTPanel();

	    apptPanel.addFiltroListener(e -> refreshTables());

	    apptPanel.addFissaBtnListener(e -> gestisciPrenotazionePT(apptPanel));

	    apptPanel.addAnnullaBtnListener(e -> gestisciAnnullamentoPT(apptPanel));
	    
	}
	
	//----------------------------------------------------------------
	
	private void gestisciIscrizioneCorso(IscrizioneCorsiPanel corsiPanel) {
		
	    JTable table = corsiPanel.getSessioniTable();
	    int selectedRow = table.getSelectedRow();

	    if (selectedRow == -1) {
	    	
	        mostraMessaggio("Seleziona una sessione.");
	        return;
	        
	    }

	    SessioneCorsoTableModel model = (SessioneCorsoTableModel) table.getModel();
	    SessioneCorso sessione = model.getSessioneAt(selectedRow);

	    String cfCliente = richiediCfCliente();
	    if (cfCliente == null) return;

	    try {
	    	
	        corsoManager.iscrivi(cfCliente, sessione);
	        refreshTables();
	        mostraMessaggio("Iscrizione effettuata.");
	        
	    } catch (ClienteNonAbbonatoException e) {
	    	
	        mostraErrore("Il cliente non è abbonato.");
	        
	    } catch (ConflittoOrarioException e) {
	    	
	        mostraErrore("Il cliente ha un conflitto di orario.");
	        
	    } catch (DatiNonTrovatiException e) {
	    	
	        mostraErrore("Sessione non trovata.");
	        
	    } catch (SessionePienaException e) {
	    	
	        mostraErrore("La sessione è piena.");
	        
	    } catch (Exception ex) {
	    	
	        mostraErrore("Errore generico: " + ex.getMessage());
	        
	    }
	    
	}

	
	//----------------------------------------------------------------
	
	private void gestisciAnnullamentoCorso(IscrizioneCorsiPanel corsiPanel) {
		
	    JTable table = corsiPanel.getSessioniTable();
	    int selectedRow = table.getSelectedRow();

	    if (selectedRow == -1) {
	    	
	        mostraMessaggio("Seleziona una sessione.");
	        return;
	        
	    }

	    SessioneCorsoTableModel model = (SessioneCorsoTableModel) table.getModel();
	    SessioneCorso sessione = model.getSessioneAt(selectedRow);

	    String cfCliente = richiediCfCliente();
	    if (cfCliente == null) return;

	    boolean success = corsoManager.annulla(cfCliente, sessione);

	    if (success) {
	    	
	        refreshTables();
	        mostraMessaggio("Iscrizione annullata.");
	        
	    } else {
	    	
	        mostraErrore("Errore: iscrizione non trovata o già annullata.");
	        
	    }
	    
	}

	
	//----------------------------------------------------------------
	
	private void gestisciPrenotazionePT(AppuntamentiPTPanel apptPanel) {
		
	    JTable table = apptPanel.getAppuntamentiTable();
	    int selectedRow = table.getSelectedRow();

	    if (selectedRow == -1) {
	    	
	        mostraMessaggio("Seleziona un appuntamento.");
	        return;
	        
	    }

	    AppuntamentoPTTableModel model = (AppuntamentoPTTableModel) table.getModel();
	    AppuntamentoPT app = model.getAppuntamentoAt(selectedRow);

	    String cfCliente = richiediCfCliente();
	    if (cfCliente == null) return;

	    try {
	    	
	        ptManager.prenota(cfCliente, app);
	        refreshTables();
	        mostraMessaggio("Lezione prenotata.");
	        
	    } catch (ClienteNonAbbonatoException e) {
	    	
	        mostraErrore("Il cliente non è abbonato.");
	        
	    } catch (ConflittoOrarioException e) {
	    	
	        mostraErrore("Il cliente ha già un’attività nello stesso orario.");
	        
	    } catch (DatiNonTrovatiException e) {
	    	
	        mostraErrore("Dati non trovati. Controlla le informazioni inserite.");
	        
	    } catch (Exception ex) {
	    	
	        mostraErrore("Errore inaspettato: " + ex.getMessage());
	        
	    }
	    
	}

	
	//----------------------------------------------------------------
	
	private void gestisciAnnullamentoPT(AppuntamentiPTPanel apptPanel) {
		
	    JTable table = apptPanel.getAppuntamentiTable();
	    int selectedRow = table.getSelectedRow();

	    if (selectedRow == -1) {
	    	
	        mostraMessaggio("Seleziona un appuntamento.");
	        return;
	        
	    }

	    AppuntamentoPTTableModel model = (AppuntamentoPTTableModel) table.getModel();
	    AppuntamentoPT app = model.getAppuntamentoAt(selectedRow);

	    String cfCliente = richiediCfCliente();
	    if (cfCliente == null) return;

	    boolean success = ptManager.annulla(cfCliente, app);

	    if (success) {
	    	
	        refreshTables();
	        mostraMessaggio("Lezione annullata.");
	        
	    } else {
	    	
	        mostraErrore("Errore: lezione non trovata o già annullata.");
	        
	    }
	    
	}

	
	//----------------------------------------------------------------
	
	private String richiediCfCliente() {
		
	    String cf = JOptionPane.showInputDialog(null, "Inserisci CF cliente:");
	    return (cf != null && !cf.trim().isEmpty()) ? cf.trim() : null;
	    
	}
	
	//----------------------------------------------------------------
	
	private void mostraMessaggio(String msg) {
		
	    JOptionPane.showMessageDialog(null, msg);
	    
	}
	
	//----------------------------------------------------------------
	
	private void mostraErrore(String msg) {
		
	    JOptionPane.showMessageDialog(null, msg);
	    
	}
	
	//----------------------------------------------------------------
	 
    /* Popola table model con sessioni disponibili / appuntamenti esistenti
     * tramite servizio esposto da coordinatore*/

	private void refreshTables() {
		
	    refreshSessioniCorsoTable();
	    refreshAppuntamentiPTTable();
	    
	}

	
	//----------------------------------------------------------------
	
	private void refreshSessioniCorsoTable() {
		
	    IscrizioneCorsiPanel corsiPanel = (IscrizioneCorsiPanel) frame.getCorsiPanel();

	    String tipo = corsiPanel.getTipoCorsoFiltro();
	    String inizioStr = corsiPanel.getDataInizioFiltro().trim();
	    String fineStr = corsiPanel.getDataFineFiltro().trim();

	    if ((inizioStr.isEmpty() && !fineStr.isEmpty()) ||
	        (!inizioStr.isEmpty() && fineStr.isEmpty())) {
	    	
	        mostraMessaggio("Inserisci entrambe le date o nessuna.");
	        return;
	        
	    }

	    try {
	    	
	        LocalDate inizio = inizioStr.isEmpty() ? null : LocalDate.parse(inizioStr);
	        LocalDate fine = fineStr.isEmpty() ? null : LocalDate.parse(fineStr);

	        if (inizio != null && fine != null && fine.isBefore(inizio)) {
	        	
	            mostraMessaggio("La data fine non può essere precedente alla data inizio.");
	            return;
	            
	        }

	        List<SessioneCorso> sessioni = corsoManager
	        							  .recuperaSessioniDisponibili(tipo, inizio, fine);
	        
	        /*Punto applicazione di table-model custom*/
	        corsiPanel.getSessioniTable()
	        		  .setModel(new SessioneCorsoTableModel(sessioni));

	    } catch (DateTimeParseException ex) {
	    	
	        mostraErrore("Formato data non valido. Usa yyyy-MM-dd.");
	        
	    } catch (Exception ex) {
	    	
	        mostraErrore("Errore durante il recupero delle sessioni:\n" + ex.getMessage());
	        ex.printStackTrace();
	        
	    }
	    
	}
	
	//----------------------------------------------------------------
	
	private void refreshAppuntamentiPTTable() {
		
	    AppuntamentiPTPanel apptPanel = (AppuntamentiPTPanel) 
	    								 frame.getAppuntamentiPTPanel();

	    String cfCliente = apptPanel.getCfClienteFiltro().trim();
	    String staffId = apptPanel.getStaffIdFiltro().trim();

	    try {
	    	
	        List<AppuntamentoPT> appuntamenti = calendarioFacade
	        									.getAppuntamentiPT(cfCliente, staffId);
	        
	        /*Punto applicazione di table-model custom*/
	        apptPanel.getAppuntamentiTable()
	        		 .setModel(new AppuntamentoPTTableModel(appuntamenti));
	        
	    } catch (Exception ex) {
	    	
	        mostraErrore("Errore durante il recupero appuntamenti:\n" 
	        			+ ex.getMessage());
	        ex.printStackTrace();
	        
	    }
	    
	}
	
	//----------------------------------------------------------------
	
}
