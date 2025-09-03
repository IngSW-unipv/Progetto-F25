package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneDipendenti.IDipendentiCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.FacadePerGestioneTurni.ITurniCRUDFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoDipendenti.ICRUDDipendentiSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.ICRUDTurniSupportServices;
import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabile.ICampoValidabile;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GeneratoreStaffID.IStaffIdGeneratorService;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.RegexCheck.IRegexExpression;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.IDipendentiETurniCoordinator;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.ControllerSupport.DipendentiManager;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.ControllerSupport.TurniManager;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.ControllerSupport.ObsAddDelDipendenti.IDipendentiChangeListener;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.PannelloDipendenti;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.PannelloTurni;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosViewHelpers.ValidazioneCampo;

public class DipendentiETurniController implements IDipendentiChangeListener{
	
	/* Vista */
    private IDipendentiETurniView view;

    /* Coordinatore */
    private IDipendentiETurniCoordinator coordinator;

    /* Servizi */
    private ITurniCRUDFacadeService turniCRUDService;
    private ICRUDTurniSupportServices supportoCRUDTurni;
	private IDipendentiCRUDFacadeService dipCRUDService;
    private ICRUDDipendentiSupportServices supportpCRUDdip;
    private IStaffIdGeneratorService generatoreStaffIDs;

    /* Manager */
    private TurniManager turniManager;
    private DipendentiManager dipManager;

    //----------------------------------------------------------------

    public DipendentiETurniController(IDipendentiETurniView view,
                                      IDipendentiETurniCoordinator coordinator) {
    	
    	/*Vista*/
        this.view = view;
        
        /*Coordinatore*/
        this.coordinator = coordinator;

        /*Servizi*/
        this.turniCRUDService = this.coordinator.getTurniCRUDService();
        this.supportoCRUDTurni = this.coordinator.getTurniCRUDSupportServices();
        this.dipCRUDService = this.coordinator.getDipendentiCRUDService();
        this.supportpCRUDdip = this.coordinator.getDipendentiCRUDSupportServices();
        this.generatoreStaffIDs = this.coordinator.getGeneratoreStaffIDs();

        /*Inizializzazioni*/
        inizializzaPannelloTurni();
        inizializzaPannelloDipendenti(); 
        
        /*Registrazione ascoltatore*/
        this.dipManager.addDipendentiChangeListener(this);
    }

    //----------------------------------------------------------------
    /*Observable per ComboBox*/
    @Override
    public void onListaDipendentiAggiornata() {
        
        turniManager.popolaComboDipendenti();
        
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
        
        // Listener per il bottone "Genera ID"
        /* Posto in questo punto del codice siccome non riguarda direttamente 
         * l'accesso al database, né tantomento la tabella in cui sono visualizzati
         * i dati dei dipendenti ('dipManager' gestisce CRUD e selezione dei dati)*/
        dipPanel.addGeneraIdBtnListener(e -> {
            String nome = dipPanel.getNome().trim();
            String cognome = dipPanel.getCognome().trim();
            String ruolo = dipPanel.getRuolo();
            String citta = dipPanel.getCitta().trim();

            if (nome.isEmpty() || cognome.isEmpty() 
            				   || ruolo == null 
            				   || citta.isEmpty()) {
                supportpCRUDdip.getDialogUtils()
                			   .mostraInfo("Inserisci nome, cognome, ruolo e città "
                			   			 + "prima "
                			   			 + "di generare lo Staff ID.");
                
                return;
                
            }

            String idGenerato = generatoreStaffIDs.generaStaffId(nome, cognome, 
            													 ruolo, citta);
            dipPanel.setStaffId(idGenerato);
        });
        
        impostaValidazioneCampiDipendente();

        
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
    
    private ICampoValidabile registraCampo(JTextField field, String regex) {
    	
        ICampoValidabile campo = coordinator.getCampoValidabileFactory()
        									.creaCampoValidabile(field, regex);

        ValidazioneCampo.applicaFeedbackSwing(campo);
        
        return campo;
    }
    
    //----------------------------------------------------------------
    
    private void impostaValidazioneCampiDipendente() {

        ICampoValidabile campoNome = registraCampo(
            ((PannelloDipendenti) view.getDipendentiPanel()).getNomeField(),
            IRegexExpression.NAME_REGEXEXPRESSION);

        ICampoValidabile campoCognome = registraCampo(
            ((PannelloDipendenti) view.getDipendentiPanel()).getCognomeField(),
            IRegexExpression.SURNAME_REGEXEXPRESSION);

        ICampoValidabile campoId = registraCampo(
            ((PannelloDipendenti) view.getDipendentiPanel()).getStaffIdField(),
            IRegexExpression.STAFF_ID_REGEXEXPRESSION);

        coordinator.getValidatoreCampi().registra(campoNome);
        coordinator.getValidatoreCampi().registra(campoCognome);
        coordinator.getValidatoreCampi().registra(campoId);
    }
    
    //----------------------------------------------------------------


}
