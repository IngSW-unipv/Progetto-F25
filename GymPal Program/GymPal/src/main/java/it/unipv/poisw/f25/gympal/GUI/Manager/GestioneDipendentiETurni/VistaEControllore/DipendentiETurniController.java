package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.Dominio.ServicesBundles.GestioneTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.IDipendentiETurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.ControllerSupport.DipendentiManager;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.ControllerSupport.TurniManager;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.PannelloDipendenti;
import it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.PannelloTurni;

public class DipendentiETurniController {
	
	/* Vista */
    private IDipendentiETurniView view;

    /* Coordinatore */
    private IDipendentiETurniCoordinator coordinator;

    /* Servizi */
    private final ITurniCRUDFacadeService turniCRUDService;
    private final ICRUDTurniSupportServices supportoCRUDTurni;
	private final IDipendentiCRUDFacadeService dipCRUDService;
    private final ICRUDDipendentiSupportServices supportpCRUDdip;

    /* Manager */
    private TurniManager turniManager;
    private DipendentiManager dipManager;

    //----------------------------------------------------------------

    public DipendentiETurniController(IDipendentiETurniView view,
                                      IDipendentiETurniCoordinator coordinator) {
        this.view = view;
        this.coordinator = coordinator;

        this.turniCRUDService = this.coordinator.getTurniCRUDService();
        this.supportoCRUDTurni = this.coordinator.getTurniCRUDSupportServices();
        this.dipCRUDService = this.coordinator.getDipendentiCRUDService();
        this.supportpCRUDdip = this.coordinator.getDipendentiCRUDSupportServices();

        inizializzaPannelloTurni();
        inizializzaPannelloDipendenti(); 
    }

    //----------------------------------------------------------------

    private void inizializzaPannelloTurni() {

        PannelloTurni turniPanel = (PannelloTurni) view.getTurniPanel();

        // Istanziazione Manager con dipendenze necessarie
        turniManager = new TurniManager(turniCRUDService,
                                        supportoCRUDTurni,
                                        turniPanel);

        // Popolazione tabella con turni del mese corrente
        turniManager.refreshTabellaTurni();

        // Popolazione di combo-boxes dei dipendenti
        turniManager.popolaComboDipendenti();

        // Listeners pulsanti
        turniPanel.addFiltroBtnListener(e -> turniManager.refreshTabellaTurni());
        turniPanel.addCreaBtnListener(e -> turniManager.onCreaTurno());
        turniPanel.addModificaBtnListener(e -> turniManager.onModificaTurno());
        turniPanel.addCancellaBtnListener(e -> turniManager.onCancellaTurno());
        turniPanel.addPulisciBtnListener(e -> turniManager.onPulisciTurniVecchi());

        // Listener selezione riga tabella
        turniPanel.addTabellaTurniSelectionListener(e -> {
        	
            if (!e.getValueIsAdjusting()) {
            	
                turniManager.onSelezioneRiga();
                
            }
            
        });

        // Deseleziona riga al click fuori tabella
        impostaDeselezioneClickFuoriTabella(turniPanel);
        
    }

    //----------------------------------------------------------------
    
    private void inizializzaPannelloDipendenti() {

        PannelloDipendenti dipPanel = (PannelloDipendenti) view.getDipendentiPanel();

        // Manager
        dipManager = new DipendentiManager(dipCRUDService, supportpCRUDdip, dipPanel);

        // Carica i dati iniziali
        dipManager.refreshTabellaDipendenti();

        // Listener pulsanti
        dipPanel.addCreaBtnListener(e -> dipManager.onCreaDipendente());
        dipPanel.addModificaBtnListener(e -> dipManager.onModificaDipendente());
        dipPanel.addCancellaBtnListener(e -> dipManager.onCancellaDipendente());
        dipPanel.addPulisciBtnListener(e -> dipManager.onPulisciCampi());

        // Selezione riga tabella
        dipPanel.addDipendentiTableSelectionListener(e -> {
        	
            if (!e.getValueIsAdjusting()) {
            	
                dipManager.onSelezioneRiga();
                
            }
            
        });

        // Deseleziona riga se clic fuori
        impostaDeselezioneClickFuoriTabella(dipPanel);
        
    }

    
    //----------------------------------------------------------------

    private void impostaDeselezioneClickFuoriTabella(JPanel panel) {
    	
        panel.addMouseListener(new MouseAdapter() {
        	
            @Override
            public void mouseClicked(MouseEvent e) {
            	
                if (panel instanceof PannelloTurni) {
                	
                    JTable table = ((PannelloTurni) panel).getTurniTable();
                    clearIfClickedOutside(table, e);
                    
                } else if (panel instanceof PannelloDipendenti) {
                	
                    JTable table = ((PannelloDipendenti) panel).getDipendentiTable();
                    clearIfClickedOutside(table, e);
                    
                }
                
            }
            
        });
        
    }


    private void clearIfClickedOutside(JTable table, MouseEvent e) {
    	
        Point point = SwingUtilities.convertPoint((Component) e.getSource(), 
        													  e.getPoint(), table);
        if (point.x < 0 || point.y < 0 
        				|| point.x >= table.getWidth() 
        				|| point.y >= table.getHeight()) {
            table.clearSelection();
            
        }
        
    }

    //----------------------------------------------------------------

    /*private void mostraErrore(String msg) {
    	
        JOptionPane.showMessageDialog(null, msg, "Errore", JOptionPane.ERROR_MESSAGE);
        
    }*/

    //----------------------------------------------------------------

}
