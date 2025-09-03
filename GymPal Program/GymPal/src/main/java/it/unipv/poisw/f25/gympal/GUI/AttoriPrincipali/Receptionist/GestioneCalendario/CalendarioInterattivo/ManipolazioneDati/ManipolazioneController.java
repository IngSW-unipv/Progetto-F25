package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ClienteNonAbbonatoException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.ConflittoOrarioException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.DatiNonTrovatiException;
import it.unipv.poisw.f25.gympal.Dominio.CalendarioService.CalendarioExc.SessionePienaException;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ControllerSupport.AppuntamentoPTManager;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ControllerSupport.SessioneCorsoManager;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.JDialogFissaAppuntamento.FissaAppuntamentoDialog;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.AppuntamentiPTPanel;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.IscrizioneCorsiPanel;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.CustomTableModelsForPanels.AppuntamentoPTTableModel;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.CustomTableModelsForPanels.SessioneCorsoTableModel;
import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;
import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class ManipolazioneController {
	
	/*Vista*/
	private IManipolazioneFrame frame;
	
    /*Coordinatore*/
    private ICoordinatoreCalendario coordinator;
    
    /*Servizi*/
    private ICalendarioFacadeService calendarioFacade;
    private IFasciaOrariaValidator fasciaValidator;
    private IDateUtils dateUtils;

    /*Managers*/
    private final SessioneCorsoManager corsoManager;
    private final AppuntamentoPTManager ptManager;
    
	//----------------------------------------------------------------
	
	public ManipolazioneController(IManipolazioneFrame frame,
								   ICalendarioFacadeService calendarioFacade,
								   IFasciaOrariaValidator fasciaValidator,
								   IDateUtils dateUtils,
								   ICoordinatoreCalendario coordinator) {
		/*Vista*/
		this.frame = frame;
		
		/*Coordinatore*/
		this.coordinator = coordinator;
		
		/*Servizi*/
		this.calendarioFacade = calendarioFacade;
		this.fasciaValidator = fasciaValidator;
		this.dateUtils = dateUtils;
		
		/*Managers*/
	    this.corsoManager = new SessioneCorsoManager(calendarioFacade,
	    											 this.dateUtils,
	    											 this.coordinator);
	    
	    this.ptManager = new AppuntamentoPTManager(calendarioFacade, 
	    										   this.coordinator);
		
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
	    
	    popolaStaffIdComboBox(apptPanel::setStaffIdOptions);
	    
	}
	
	//----------------------------------------------------------------
	
	private void popolaStaffIdComboBox(Consumer<List<String>> staffIdConsumer) {
		
	    try {
	    	
	        List<String> soloDIP = ptManager.getSoloStaffIdDIP();	
	        /* 'Consumer' è un'intefaccia funzionala dotata di un unico metodo
	         * astratto ('accept').
	         * Quando è invocato 'popolaStaffIdComboBox(apptPanel::setStaffIdOptions)'
	         * Java sa che 'popolaStaffIdComboBox' accetta una 'Consumer<List<String>>'
	         * e che il metodo 'setStaffIdOptions' accetta una 'List<String>'.
	         * Quindi, Java può creare un oggetto 'Consumer<List<String>>' che
	         * chiama 'setStaffIdOptions' quando è invocata 'accept()'.*/
	        staffIdConsumer.accept(soloDIP);
	        
	    } catch (Exception e) {
	    	
	        mostraErrore("Errore durante il caricamento degli staffId: " 
	        			 + e.getMessage());
	        e.printStackTrace();
	        
	    }
	    
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
	    	
	        mostraErrore("Nell'abbonamento del cliente non è incluso questo corso.");
	        
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
		
	    try {
	    	
	        // Recupera lista di dipendenti con ID contenente "DIP"
	    	List<String> staffIdDIP = new ArrayList<>();
	    	popolaStaffIdComboBox(staffIdDIP::addAll);

	        // Inizializza e mostra la dialog
	        
	        FissaAppuntamentoDialog dialog = new FissaAppuntamentoDialog(
	            staffIdDIP,
	            dateUtils,
	            fasciaValidator
	        );
	        dialog.setVisible(true);

	        // Acquisizione informazioni appuntamento da finestra di dialogo
	        AppuntamentoPT nuovoAppuntamento = dialog.getAppuntamentoPT();

	        // Chiusura dialog senza inserire dati
	        if (nuovoAppuntamento == null) {
	        	
	            return;
	            
	        }

	        // Prenotazione
	        ptManager.prenota(nuovoAppuntamento.getCf(),
	        				  nuovoAppuntamento);

	        refreshTables();
	        mostraMessaggio("Lezione prenotata.");

	    } catch (ClienteNonAbbonatoException e) {
	    	
	        mostraErrore("L'abbonamento del cliente non include 'Sala Pesi'.");
	        
	    } catch (ConflittoOrarioException e) {
	    	
	        mostraErrore("Il cliente ha già un’attività nello stesso orario.");
	        
	    } catch (DatiNonTrovatiException e) {
	    	
	        mostraErrore("Dati non trovati. Controlla le informazioni inserite.");
	        
	    } catch (Exception ex) {
	    	
	        mostraErrore("Errore inaspettato: " + ex.getMessage());
	        
	        ex.printStackTrace();
	        
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
	    
	    try {
	    	
	        List<SessioneCorso> sessioni = corsoManager.recuperaSessioniDaStringhe(tipo, inizioStr, fineStr);
	        corsiPanel.getSessioniTable().setModel(new SessioneCorsoTableModel(sessioni));
	        
	    } catch (IllegalArgumentException ex) {
	    	
	        mostraMessaggio(ex.getMessage());
	        
	    } catch (Exception ex) {
	    	
	        mostraErrore("Errore durante il recupero delle sessioni:\n" 
	        			+ ex.getMessage());
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
