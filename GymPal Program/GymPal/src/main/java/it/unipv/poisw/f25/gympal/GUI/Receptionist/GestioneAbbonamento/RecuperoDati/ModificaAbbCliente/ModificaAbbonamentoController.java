package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.ModAbbContract.IModAbbContract;

public class ModificaAbbonamentoController {
	
	/*Vista*/
	private IModificaAbbonamentoView modAbbView;
	
	/*Coordinatore*/
	IModAbbContract coordinator;
	
	/*CallBacks*/
	private Runnable onAnnulla;
	private Runnable onIndietro;
	private Runnable onAvanti;
	
	//----------------------------------------------------------------
	
	public ModificaAbbonamentoController(IModificaAbbonamentoView modAbbView,
										 Runnable onAnnulla,
										 Runnable onIndietro,
										 Runnable onAvanti,
										 IModAbbContract coordinator) {
		
		this.modAbbView = modAbbView;
		this.coordinator = coordinator;
						
		this.onAnnulla = onAnnulla;
		this.onIndietro = onIndietro;
		this.onAvanti = onAvanti;
		
		viewInit();
		
		/*Inizializzazione listeners*/
		impostaEventoAnnulla();
		impostaEventoIndietro();
		impostaEventoAvanti();
		impostaEventoConferma();
		impostaEventoResetta();
		impostaEventiToggleBtns();
		impostaEventiCheckBox();
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventiToggleBtns() {
		
		List<JToggleButton> toggleBtnsList = modAbbView.getBottoniToggle();

		for (JToggleButton btn : toggleBtnsList) {
						
			if (btn == modAbbView.getBottoniToggle().get(3)) {
				
				btn.addItemListener(e -> {
					
					if (btn.isSelected()) {
						
						btn.setBackground(Color.decode("#b2fab4"));
						
						modAbbView.getCorsiPanel().setVisible(true);
						SwingUtilities.invokeLater(() -> {
							
							modAbbView.getSubEditorSplitPane().setDividerLocation(0.5);
							
						});

					} else {
						
						btn.setBackground(Color.decode("#ffcccc"));
						
						modAbbView.getCorsiPanel().setVisible(false);

						SwingUtilities.invokeLater(() -> {
							
							modAbbView.getSubEditorSplitPane().setDividerLocation(1.0); 
							
						});
						
					}
					
					modAbbView.getMainPanel().revalidate();
					modAbbView.getMainPanel().repaint();
					
				});
				
			} else {
				
				btn.addItemListener(e -> {
					
					if (btn.isSelected()) {
						
						btn.setBackground(Color.decode("#b2fab4")); 

						
					} else {
						
						btn.setBackground(Color.decode("#ffcccc")); 

					}
					
				});
				
			}
			
		}
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventiCheckBox() {
        
		List<JCheckBox> checkBoxList = modAbbView.getCheckBoxes();
		
		for (JCheckBox btn : checkBoxList) {
			
			btn.addItemListener(e -> {
	           
	            if (btn.isSelected()) {
	            	
	                btn.setBackground(Color.decode("#b2fab4"));
     	                
	            } else {
	            	
	                btn.setBackground(Color.decode("#ffcccc"));
	                
	            }
	            
	        });
  
		}
        
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventoIndietro() {
		
		modAbbView.addIndietroListener(e -> {onIndietro.run();});
		
	}

	//----------------------------------------------------------------
	
	private void impostaEventoAvanti() {
		
		modAbbView.addAvantiListener(e -> {onAvanti.run();});
		
	}	
	
	//----------------------------------------------------------------
	
	private void impostaEventoAnnulla() {
		
		modAbbView.addAnnullaListener(e -> {onAnnulla.run();});
		
	}	
	
	//----------------------------------------------------------------
	
	private void impostaEventoConferma() {
		
		modAbbView.addConfermaListener(e -> {
            
			aggiornaDTO();
			modAbbView.setLists(coordinator.retrieveSezioniAbbonamento(),
								coordinator.retrieveCorsiSelezionati());
            
        });
		
	}	
	
	//----------------------------------------------------------------
	
	private void impostaEventoResetta() {
		
		modAbbView.addResettaListener(e -> {
            
			coordinator.annullaModsCompAbbonamento();
			modAbbView.setLists(coordinator.retrieveSezioniAbbonamento(),
								coordinator.retrieveCorsiSelezionati());
			
        });
		
	}
	
	//----------------------------------------------------------------
	
	private void aggiornaDTO() {
		
	    // Nomi delle sezioni abbonamento corrispondenti ai toggle
	    List<String> nomiSezioni = Arrays.asList(
	        "Allenamento mirato con personal trainer",
	        "Sala allenamento a corpo libero",
	        "Sala pesi",
	        "Sala corsi"
	    );

	    // Nomi corsi corrispondenti alle checkbox
	    List<String> nomiCorsi = Arrays.asList(
	        "Crossfit",
	        "Yoga",
	        "Pilates",
	        "Zumba",
	        "Fullbody"
	    );

	    // Lista sezioni selezionate
	    List<String> sezioniSelezionate = new ArrayList<>();
	    
	    for (int i = 0; i < modAbbView.getBottoniToggle().size(); i++) {
	        if (modAbbView.getBottoniToggle().get(i).isSelected()) {
	            sezioniSelezionate.add(nomiSezioni.get(i));
	        }
	        
	    }


	    // Lista corsi selezionati
	    List<String> corsiSelezionati = new ArrayList<>();
	    
	    for (int i = 0; i < modAbbView.getCheckBoxes().size(); i++) {
	        if (modAbbView.getCheckBoxes().get(i).isSelected()) {
	            corsiSelezionati.add(nomiCorsi.get(i));
	        }
	        
	    }
	    
	    coordinator.acquisisciComponentiAbbonamento(sezioniSelezionate, corsiSelezionati);
	    
	}
	
	//----------------------------------------------------------------
	
	private void viewInit() {modAbbView.setLists(coordinator.retrieveSezioniAbbonamento(),
												 coordinator.retrieveCorsiSelezionati());}
	
	//----------------------------------------------------------------
	
}
