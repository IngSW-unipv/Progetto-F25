package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.FacadePerCalendario.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidatoreFasciaOraria.IFasciaOrariaValidator;
import it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData.IDateUtils;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities.CalendarioCellPanel;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities.DettaglioCellaFrame;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.IManipolazioneFrame;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ManipolazioneController;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ManipolazioneFrame;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.DynamicButtonSizeSetter;

public class CalendarioSettimanaleController implements ICalendarioChangeListener {

	/*Vista*/
    private final ICalendarioSettimanaleView view;
    
    /*Coordinatore*/
    private ICoordinatoreCalendario coordinator;
    
    /*Servizi*/
    private ICalendarioFacadeService calendarioFacade;
    private IDateUtils dateUtils;

	//----------------------------------------------------------------


    public CalendarioSettimanaleController(ICalendarioSettimanaleView view,
    									   ICalendarioFacadeService calendarioFacade,
    									   IDateUtils dateUtils,
    									   IFontChangeRegister changeRegister,
    									   IFasciaOrariaValidator fasciaValidator,
    									   ICoordinatoreCalendario coordinator) {
    	/*Vista*/
        this.view = view;
        
        /*Coordinatore*/
        this.coordinator = coordinator;
        
        /*Servizi*/
        this.calendarioFacade = calendarioFacade;
        this.dateUtils = dateUtils;
        
        /*Inizializzazioni*/
        impostaMeccanismoObservable();
        inizializzaTabella(cellListenerProvider());
        impostaEventoBtnGestioneAvanzata(changeRegister, fasciaValidator);
        impostaEventoBtnLegenda();
        
    }
    
	//----------------------------------------------------------------
    
    private void impostaMeccanismoObservable() {
    	
    	coordinator.addCalendarioChangeListener(this);
    	
    }
    
    //----------------------------------------------------------------
    
    private void inizializzaTabella(ActionListener cellClickListener) {
    	
        view.popolaGriglia(LocalDate.now(), cellClickListener);
        aggiungiScrollSettimanaHandler(cellClickListener);
        aggiornaColoriCelle();
        
    }
    
	//----------------------------------------------------------------

    private void aggiungiScrollSettimanaHandler(ActionListener cellClickListener) {
    	
        view.addBtnPrecedenteListener(e -> {
        	
            LocalDate nuovaSettimana = view.getLunediCorrente().minusWeeks(1);
            view.popolaGriglia(nuovaSettimana, cellClickListener);
            aggiornaColoriCelle();
            
        });

        view.addBtnSuccessivaListener(e -> {
        	
            LocalDate nuovaSettimana = view.getLunediCorrente().plusWeeks(1);
            view.popolaGriglia(nuovaSettimana, cellClickListener);
            aggiornaColoriCelle();
            
        });
        
    }
    
	//----------------------------------------------------------------
    
    private ActionListener cellListenerProvider() {
    	
    	return e -> {
        	
            JButton button = (JButton) e.getSource();
            CalendarioCellPanel cella = (CalendarioCellPanel) button.getParent();

            LocalDate data = cella.getData();
            int ora = cella.getOra();
            int minuti = cella.getMinuti();

            System.out.println("Cella cliccata: " + data + " " + ora + ":" + minuti);

            IDatiCellaCalendarioDTO dati = coordinator
            							   .getContenutoCella(data, ora, minuti);
            
            new DettaglioCellaFrame(dati, data, ora, minuti).setVisible(true);
            
        };

    	
    }
    
    
	//----------------------------------------------------------------
    
    private void aggiornaCelle(List<CalendarioCellPanel> celle) {
    	
        for (CalendarioCellPanel cella : celle) {
        	
            IDatiCellaCalendarioDTO dati = coordinator.getContenutoCella(
            							   cella.getData(), 
            							   cella.getOra(), 
            							   cella.getMinuti());
            
            cella.aggiornaColoreSfondo(dati);
            
        }
        
    }
    
	//----------------------------------------------------------------
    
    private void aggiornaColoriCelle() {
    	
    	aggiornaCelle(view.getTutteLeCelle());
        
    }
    
	//----------------------------------------------------------------
    
    private void aggiornaCellePerDate(List<LocalDate> dateDaAggiornare) {
    	
        List<CalendarioCellPanel> celleFiltrate = view.getTutteLeCelle().stream()
            .filter(c -> dateDaAggiornare.contains(c.getData()))
            .collect(Collectors.toList());
        
        aggiornaCelle(celleFiltrate);
        
    }

    
	//----------------------------------------------------------------
    
    private void impostaEventoBtnGestioneAvanzata(IFontChangeRegister changeRegister,
    											  IFasciaOrariaValidator fasciaValidator) {
    	
    	view.addBtnGestioneAvanzataListener(e -> {
    		
    		IManipolazioneFrame frame = new ManipolazioneFrame(new DynamicButtonSizeSetter(), changeRegister);
    		new ManipolazioneController(frame,calendarioFacade,
    									fasciaValidator, 
    									dateUtils, 
    									coordinator);
    		
    	});
    	
    }
    
	//----------------------------------------------------------------
    
    private void impostaEventoBtnLegenda() {
      	
        view.addBtnLegendaListener(e -> {
            JPanel legendaPanel = new JPanel();
            legendaPanel.setLayout(new BoxLayout(legendaPanel, BoxLayout.Y_AXIS));

            legendaPanel.add(creaVoceLegenda(new Color(100, 200, 255), "Corsi"));
            legendaPanel.add(creaVoceLegenda(new Color(255, 170, 80), "Appuntamenti PT"));
            legendaPanel.add(creaVoceLegenda(new Color(200, 100, 255), "Eventi generici"));
            legendaPanel.add(creaVoceLegenda(new Color(255, 240, 80), "Turni staff"));

            JOptionPane.showMessageDialog(null, legendaPanel,
                    "Legenda colori", JOptionPane.INFORMATION_MESSAGE);
        });
    	
    }
    
    
    private JPanel creaVoceLegenda(Color colore, String testo) {
    	
        JPanel voce = new JPanel(new BorderLayout());
        voce.setOpaque(false);

        JPanel quadrato = new JPanel();
        quadrato.setBackground(colore);
        quadrato.setPreferredSize(new Dimension(20, 20));
        quadrato.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));

        JLabel label = new JLabel(" " + testo);
        voce.add(quadrato, BorderLayout.WEST);
        voce.add(label, BorderLayout.CENTER);
        return voce;
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public void onCambioCalendario(List <LocalDate> dataCambiata) {
    	
    	aggiornaCellePerDate(dataCambiata);
    	
    }
    
    //----------------------------------------------------------------
    
}


