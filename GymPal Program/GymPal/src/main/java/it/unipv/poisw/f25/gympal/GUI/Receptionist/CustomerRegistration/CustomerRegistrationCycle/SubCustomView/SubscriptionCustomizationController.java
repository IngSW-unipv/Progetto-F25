package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.IRegistrationCoordinator;


public class SubscriptionCustomizationController {
	
	private ISubscriptionCustomizationView view;
	private IRegistrationCoordinator coordinator;
	

	
	/*Questo Runnable serve ad istituire un metodo di "CallBack" che avvisi il
	 *"ReceptionistController" dell'avvenuta pressione del tasto "avanti", di modo che
	 *detto controller possa cambiare schermata*/
	
	private Runnable onAvanti;
	
	/*La callback è una funzione che viene passata come parametro al controller. 
	 *Una volta che l'utente completa la personalizzazione dell'abbonamento (premendo il 
	 *pulsante "Avanti" -in questo caso- nella SubscriptionCustomizationView), la
	 *callback viene invocata, permettendo di passare alla schermata successiva.*/
	
	private Runnable onAnulla;
	
	//----------------------------------------------------------------
	
	public SubscriptionCustomizationController(ISubscriptionCustomizationView scv,
											   Runnable onAvantiCallback,
											   Runnable onAnnullaCallback,
											   IRegistrationCoordinator coordinator) {
		
		view = scv;
		this.coordinator = coordinator;
		
		onAvanti = onAvantiCallback;
		onAnulla = onAnnullaCallback;
		
		impostaEventiToggleBtns();
		impostaEventiCheckBox();
		impostaEventoAvanti();
		impostaEventoAnulla();
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventiToggleBtns() {
		
		List<JToggleButton> toggleBtnsList = view.getBottoniToggle();

		for (JToggleButton btn : toggleBtnsList) {

			// Ascoltatore per il bottone "Sala corsi"
			
						
			if (btn == view.getBottoniToggle().get(3)) {
				
				/*Al contrario di "ActionListener", "ItemListener" tiene traccia del fatto
				 *che un elemento sia selezionato oppure no.*/
				
				btn.addItemListener(e -> {
					
					if (btn.isSelected()) {
						
						btn.setBackground(Color.GREEN);
						
						view.getCorsiPanel().setVisible(true);

						// Forzatura aggiornamento divider
						SwingUtilities.invokeLater(() -> {
							
							view.getSplitPaneChild().setDividerLocation(0.5);
							
						});

						//Aggiungi logica che collega bottone a dominio
						
					} else {
						
						btn.setBackground(Color.decode("#ffcccc"));
						
						view.getCorsiPanel().setVisible(false);

						SwingUtilities.invokeLater(() -> {
							
							view.getSplitPaneChild().setDividerLocation(1.0); // Divider tutto in alto
							
						});
						
						//Aggiungi logica che collega bottone a dominio
						
					}

					/*Questi metodi forzano l'aggiornamento del pannello 'view' ogni volta che
					 *il bottone "Sala Corsi" è premuto*/
					view.getMainPanel().revalidate();
					view.getMainPanel().repaint();
					
				});
				
			} else {
				
				btn.addItemListener(e -> {
					
					if (btn.isSelected()) {
						
						btn.setBackground(Color.decode("#b2fab4")); // Modifica colore quando selezionato
						
						//Aggiungi logica che collega bottone a dominio
						
					} else {
						
						btn.setBackground(Color.decode("#ffcccc")); // Modifica colore quando non selezionato
						
						//Aggiungi logica che collega bottone a dominio
					}
					
				});
			}
		}
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventiCheckBox() {
        
		List<JCheckBox> checkBoxList = view.getCheckBoxes();
		
		for (JCheckBox btn : checkBoxList) {
			
			btn.addItemListener(e -> {
	           
	            if (btn.isSelected()) {
	            	
	                btn.setBackground(Color.decode("#b2fab4"));
	                
	              //Aggiungi logica che collega bottone a dominio
	                
	                
	            } else {
	            	
	                btn.setBackground(Color.decode("#ffcccc"));
	                
	              //Aggiungi logica che collega bottone a dominio
	                
	            }
	            
	        });
  
		}
        
	}
	
	//----------------------------------------------------------------

	private void impostaEventoAvanti() {
		
		//Alla pressione di 'Avanti' devono essere salvate le selezioni, da qualche parte nel dominio
				
				
		view.getAvantiButton().addActionListener(e -> {
			
		    if (!verificaSelezione(view.getBottoniToggle())) {
		    	
		    	/*Controlla che almeno un abbonamento sia selezionato*/
		    	
		        JOptionPane.showMessageDialog((java.awt.Component) view, "Seleziona almeno un'abbonamento prima "
		        								  + "di continuare.", "Errore", JOptionPane.ERROR_MESSAGE);
		        
		    } else if (view.getBottoniToggle().get(3).isSelected() && 
		    		   !verificaSelezione(view.getCheckBoxes())){
		    	
		    	/*Se "Sala Corsi" è selezionato, controlla anche che sia selezionato almeno un
		    	 *corso.*/
		    	
	            JOptionPane.showMessageDialog((java.awt.Component) view, "Seleziona almeno un corso prima"
						  	 + "di continuare", "Errore", JOptionPane.ERROR_MESSAGE);
		    	
		    			        
		    } else {

		    	aggiornaDTO();
		    	
		    	// Debug: stampa il contenuto delle liste nel DTO
		    	/*System.out.println("DEBUG - Sezioni abbonamento selezionate:");
		    	if (abbonamentoDTO.getSezioniAbbonamento() != null) {
		    	    for (String s : abbonamentoDTO.getSezioniAbbonamento()) {
		    	        System.out.println(" - " + s);
		    	    }
		    	} else {
		    	    System.out.println("Lista sezioni abbonamento è null");
		    	}

		    	System.out.println("DEBUG - Corsi selezionati:");
		    	if (abbonamentoDTO.getCorsiSelezionati() != null) {
		    	    for (String c : abbonamentoDTO.getCorsiSelezionati()) {
		    	        System.out.println(" - " + c);
		    	    }
		    	} else {
		    	    System.out.println("Lista corsi selezionati è null");
		    	}*/
		    	/////////////////////////////////////////////////////////////////
		    	
		    	
		    	if (onAvanti != null) {
		    		
                    onAvanti.run(); // E' qui richiamata la callback per cambiare schermata
                    
                }	    
		    
		    }
		    
		});
		
	}

	//----------------------------------------------------------------
	
	private void impostaEventoAnulla() {
		
		view.getAnnullaButton().addActionListener(e -> {
			
			onAnulla.run();
			
		});
		
	}
	
	//----------------------------------------------------------------
		
	private boolean verificaSelezione(List<? extends AbstractButton> listaBottoni) {
		
		for (AbstractButton btn : listaBottoni) {
			
			if(btn.isSelected()) {
				
				return true;
				
			}
		
		}
		
		return false;
			
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
	    
	    for (int i = 0; i < view.getBottoniToggle().size(); i++) {
	        if (view.getBottoniToggle().get(i).isSelected()) {
	            sezioniSelezionate.add(nomiSezioni.get(i));
	        }
	        
	    }


	    // Lista corsi selezionati
	    List<String> corsiSelezionati = new ArrayList<>();
	    
	    for (int i = 0; i < view.getCheckBoxes().size(); i++) {
	        if (view.getCheckBoxes().get(i).isSelected()) {
	            corsiSelezionati.add(nomiCorsi.get(i));
	        }
	        
	    }
	    
	    coordinator.acquisisciComponentiAbbonamento(sezioniSelezionate, corsiSelezionati);
	    
	}
	
	//----------------------------------------------------------------
	
}
