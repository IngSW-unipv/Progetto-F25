package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneEventiGenerici.IEventiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.FacadePerGestioneSessioniCorsi.ICorsiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoCorsi.ICRUDCorsiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneEventiESessioni.SupportoEventi.ICRUDEventiSupportServices;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.IEventiECorsiCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.ControllerSupport.EventiGenericiManager;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.ControllerSupport.SessioniCorsiManager;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.PannelloCorsi;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.PannelloEventi;
import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class EventiECorsiController {
	
	/*Vista*/
	private IEventiECorsiView view;
	
	/*Coordinatore*/
	private IEventiECorsiCoordinator coordinator;
	
	/*Servizi*/
	private ICalendarioFacadeService calendarioService;
	private ICorsiCRUDFacadeService corsiCRUDService;
	private ICRUDCorsiSupportServices supportoCRUDCorsi;
	private IEventiCRUDFacadeService eventiCRUDService;
	private ICRUDEventiSupportServices supportoCRUDeventi;
	
	/*Managers*/
	private SessioniCorsiManager corsiManager;
	private EventiGenericiManager eventiManager;
	
	//----------------------------------------------------------------
	
	public EventiECorsiController(IEventiECorsiView view,
								  IEventiECorsiCoordinator coordinator) {
		/*Vista*/
		this.view = view;
		
		/*Coordinatore*/
		this.coordinator = coordinator;
		
		/*Servizi*/
		this.calendarioService = coordinator.getCalendarioService();
		this.corsiCRUDService = coordinator.getCorsiCRUDService();
		this.supportoCRUDCorsi = coordinator.getCorsiCRUDSupportServices();
		this.eventiCRUDService = coordinator.getEventiCRUDService();
		this.supportoCRUDeventi = coordinator.getEventiCRUDSupportServices();
		
		/*Inizializzazioni*/
		inizializzaPannelloGestioneSessioniCorsi();
		inizializzaPannelloEventiGenerici();
		
	}
	
	//----------------------------------------------------------------
	
	private void inizializzaPannelloGestioneSessioniCorsi() {
		

	    PannelloCorsi corsiPanel = (PannelloCorsi) view.getCorsiPanel();

	    // Crea helper 'Manager' con dipendenze
	    corsiManager = new SessioniCorsiManager(corsiCRUDService, 
	    										calendarioService, 
	    										coordinator,
	    										supportoCRUDCorsi,
	    										corsiPanel);

	    // Inizializza dati e UI tramite helper
	    corsiManager.refreshTabellaSessioni();

	    popolaStaffIdComboBox(corsiPanel); 

	    impostaDeselezioneClickFuoriTabella(corsiPanel); 

	    // Listeners forniti da helper
	    corsiPanel.addGeneraIdBtnListener(e -> corsiManager.onGeneraId());

	    corsiPanel.addFiltroBtnListener(e -> corsiManager.refreshTabellaSessioni());

	    corsiPanel.addCreaBtnListener(e -> corsiManager.onCreaSessione());

	    corsiPanel.addTabellaSessioniSelectionListener(e -> {
	    	
	        if (!e.getValueIsAdjusting()) {
	        	
	            corsiManager.onSelezioneRiga();
	            
	        }});

	    corsiPanel.addCancellaBtnListener(e -> corsiManager.onCancellaSessione());

	    corsiPanel.addModificaBtnListener(e -> corsiManager.onModificaSessione());

	    corsiPanel.addPulisciBtnListener(e -> corsiManager.onPulisciSessioniVecchie());
		
	}
	
	//----------------------------------------------------------------
	
	private void inizializzaPannelloEventiGenerici() {

	    PannelloEventi eventiPanel = (PannelloEventi) view.getEventiPanel();

	    // Inizializza il manager con i servizi necessari
	    eventiManager = new EventiGenericiManager(eventiCRUDService,
	    										  supportoCRUDeventi,
	    										  eventiPanel
	    );

	    // Popola la tabella con eventi del mese corrente
	    eventiManager.refreshTabellaEventi();

	    // Listeners per i bottoni
	    eventiPanel.addFiltroBtnListener(e -> eventiManager.refreshTabellaEventi());
	    eventiPanel.addCreaBtnListener(e -> eventiManager.onCreaEvento());
	    eventiPanel.addModificaBtnListener(e -> eventiManager.onModificaEvento());
	    eventiPanel.addCancellaBtnListener(e -> eventiManager.onCancellaEvento());
	    eventiPanel.addPulisciBtnListener(e -> eventiManager.onPulisciEventiVecchi());

	    // Listener sulla selezione della riga della tabella
	    eventiPanel.addTabellaEventiSelectionListener(e -> {
	    	
	        if (!e.getValueIsAdjusting()) {
	        	
	            eventiManager.onSelezioneRiga();
	            
	        }
	        
	    });

	    // Deseleziona riga se clic fuori tabella
	    impostaDeselezioneClickFuoriTabella(eventiPanel);
	    
	}

	
	
	//----------------------------------------------------------------
	
	private void popolaStaffIdComboBox(PannelloCorsi corsiPanel) {
		
	    try {
	    	
	        List<Dipendente> dipendenti = calendarioService
							             .getRetrieveDipendentiService()
							             .retrieve();

	        List<String> staffIds = dipendenti.stream()
	        								  .map(Dipendente::getStaffId)
	        								  .filter(id -> id != null && id
	        								  .contains("DIP"))
	        								  .collect(Collectors.toList());

	        corsiPanel.setStaffIdOptions(staffIds);
	        
	    } catch (Exception e) {
	    	
	        mostraErrore("Errore nel caricamento degli staff ID:\n" + e.getMessage());
	    }
	    
	}
		
	//----------------------------------------------------------------
	
	private void impostaDeselezioneClickFuoriTabella(JPanel panel) {
	    panel.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            if (panel instanceof PannelloCorsi) {
	                JTable table = ((PannelloCorsi) panel).getSessioniTable();
	                clearIfClickedOutside(table, e);
	            } else if (panel instanceof PannelloEventi) {
	                JTable table = ((PannelloEventi) panel).getEventiTable();
	                clearIfClickedOutside(table, e);
	            }
	        }
	    });
	}

	private void clearIfClickedOutside(JTable table, MouseEvent e) {
	    Point point = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), table);
	    if (point.x < 0 || point.y < 0 || point.x >= table.getWidth() || point.y >= table.getHeight()) {
	        table.clearSelection();
	    }
	}

			
	//----------------------------------------------------------------

	private void mostraErrore(String msg) {
		
	    JOptionPane.showMessageDialog(null, msg, "Errore", 
	    							  JOptionPane.ERROR_MESSAGE);
	    
	}
	
	//----------------------------------------------------------------

}
