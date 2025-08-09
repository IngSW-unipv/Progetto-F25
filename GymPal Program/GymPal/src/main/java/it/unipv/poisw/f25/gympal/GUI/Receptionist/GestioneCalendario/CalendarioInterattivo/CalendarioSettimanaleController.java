package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo;

import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.ICoordinatoreCalendario;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities.CalendarioCellPanel;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities.DettaglioCellaFrame;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;

public class CalendarioSettimanaleController {

	/*Vista*/
    private final ICalendarioSettimanaleView view;
    
    /*Coordinatore*/
    private final ICoordinatoreCalendario coordinator;

	//----------------------------------------------------------------


    public CalendarioSettimanaleController(ICalendarioSettimanaleView view,
    									   ICoordinatoreCalendario coordinator) {
    	
        this.view = view;
        this.coordinator = coordinator;

        ActionListener cellClickListener = e -> {
        	
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

        inizializzaTabella(cellClickListener);
        
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
    
    private void aggiornaColoriCelle() {
    	
        for (CalendarioCellPanel cella : view.getTutteLeCelle()) {
        	
            IDatiCellaCalendarioDTO dati = coordinator
            							   .getContenutoCella(cella.getData(), 
            									   			  cella.getOra(),
            									   			  cella.getMinuti());
            
            cella.aggiornaColoreSfondo(dati);
            
        }
        
    }
    
	//----------------------------------------------------------------
    
}


