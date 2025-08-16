package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ICalendarioFacadeService;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities.CalendarioCellPanel;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities.DettaglioCellaFrame;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.IManipolazioneFrame;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ManipolazioneController;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.ManipolazioneFrame;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;

public class CalendarioSettimanaleController implements ICalendarioChangeListener {

	/*Vista*/
    private final ICalendarioSettimanaleView view;
    
    /*Coordinatore*/
    private final ICoordinatoreCalendario coordinator;
    private ICalendarioFacadeService calendarioFacade;

	//----------------------------------------------------------------


    public CalendarioSettimanaleController(ICalendarioSettimanaleView view,
    									   ICalendarioFacadeService calendarioFacade,
    									   ICoordinatoreCalendario coordinator) {
    	
        this.view = view;
        this.coordinator = coordinator;
        this.calendarioFacade = calendarioFacade;
        
        impostaMeccanismoObservable();
        inizializzaTabella(cellListenerProvider());
        impostaEventoBtnGestioneAvanzata();
        
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
    
    private void impostaEventoBtnGestioneAvanzata() {
    	
    	view.addBtnGestioneAvanzataListener(e -> {
    		
    		IManipolazioneFrame frame = new ManipolazioneFrame();
    		new ManipolazioneController(frame,calendarioFacade, coordinator);
    		
    	});
    	
    }
    
	//----------------------------------------------------------------
    
    private void aggiornaCelle(List<CalendarioCellPanel> celle) {
    	
        for (CalendarioCellPanel cella : celle) {
        	
            IDatiCellaCalendarioDTO dati = coordinator.getContenutoCella(
                cella.getData(), cella.getOra(), cella.getMinuti());
            cella.aggiornaColoreSfondo(dati);
            
        }
        
    }
    
	//----------------------------------------------------------------

    
    @Override
    public void onCambioCalendario(List <LocalDate> dataCambiata) {
    	
    	aggiornaCellePerDate(dataCambiata);
    	
    }
    
    //----------------------------------------------------------------
    
}


