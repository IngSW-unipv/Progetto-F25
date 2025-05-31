package it.unipv.poisw.f25.gympal.ReceptionistController;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.GUI.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.GUI.SubscriptionCustomizationView;


public class SubscriptionCustomizationController {
	
	private SubscriptionCustomizationView view;
	private ReceptionistDashboardView mainView;
	
	private AbbonamentoDTO abbonamentoDTO;
	
	/*Questo Runnable serve ad istituire un metodo di "CallBack" che avvisi il
	 *"ReceptionistController" dell'avvenuta pressione del tasto "avanti", di modo che
	 *detto controller possa cambiare schermata*/
	
	private Runnable onAvanti;
	
	/*La callback è una funzione che viene passata come parametro al controller. 
	 *Una volta che l'utente completa la personalizzazione dell'abbonamento (premendo il 
	 *pulsante "Avanti" -in questo caso- nella SubscriptionCustomizationView), la
	 *callback viene invocata, permettendo di passare alla schermata successiva.*/
	
	//----------------------------------------------------------------
	
	public SubscriptionCustomizationController(SubscriptionCustomizationView scv,
											   ReceptionistDashboardView recDashView,
                                               Runnable onAvantiCallback,
                                               AbbonamentoDTO abbonamentoDTO) {
		
		view = scv;
		mainView = recDashView;
		
		this.abbonamentoDTO = abbonamentoDTO;
		
		onAvanti = onAvantiCallback;
		
		impostaEventiToggleBtns();
		impostaEventiCheckBox();
		impostaEventoAvanti();
		
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
						
						btn.setBackground(Color.RED);
						
						view.getCorsiPanel().setVisible(false);

						SwingUtilities.invokeLater(() -> {
							
							view.getSplitPaneChild().setDividerLocation(1.0); // Divider tutto in alto
							
						});
						
						//Aggiungi logica che collega bottone a dominio
						
					}

					/*Questi metodi forzano l'aggiornamento del pannello 'view' ogni volta che
					 *il bottone "Sala Corsi" è premuto*/
					view.revalidate();
					view.repaint();
					
				});
				
			} else {
				
				btn.addItemListener(e -> {
					
					if (btn.isSelected()) {
						
						btn.setBackground(Color.decode("#b2fab4")); // Modifica colore quando selezionato
						
						//Aggiungi logica che collega bottone a dominio
						
					} else {
						
						btn.setBackground(Color.RED); // Modifica colore quando non selezionato
						
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
	            	
	                btn.setBackground(Color.RED);
	                
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
		    	
		        JOptionPane.showMessageDialog(view, "Seleziona almeno un'abbonamento prima "
		        								  + "di continuare.", "Errore", JOptionPane.ERROR_MESSAGE);
		        
		    } else if (view.getBottoniToggle().get(3).isSelected() && 
		    		   !verificaSelezione(view.getCheckBoxes())){
		    	
		    	/*Se "Sala Corsi" è selezionato, controlla anche che sia selezionato almeno un
		    	 *corso.*/
		    	
	            JOptionPane.showMessageDialog(view, "Seleziona almeno un corso prima"
						  	 + "di continuare", "Errore", JOptionPane.ERROR_MESSAGE);
		    	
		    			        
		    } else {

		    	aggiornaDTO(abbonamentoDTO);
		    	
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
		
	private boolean verificaSelezione(List<? extends AbstractButton> listaBottoni) {
		
		for (AbstractButton btn : listaBottoni) {
			
			if(btn.isSelected()) {
				
				return true;
				
			}
		
		}
		
		return false;
			
	}
		
	//----------------------------------------------------------------
	
	private void aggiornaDTO(AbbonamentoDTO abbonamentoDTO) {
		
	    // Lista temporanea per le sezioni dell'abbonamento
	    List<String> componentiSelezionate = new ArrayList<>();
	    
	    
	    for (JToggleButton btn : view.getBottoniToggle()) {
	    	
	        if (btn.isSelected()) {
	        	
	            // Se il toggle è selezionato, allora la corrispondente label è posta nella lista
	            componentiSelezionate.add(btn.getText());
	        }
	        
	    }
	    
	    abbonamentoDTO.setSezioniAbbonamento(componentiSelezionate);
	    
	    // Se il toggle "Sala Corsi" è selezionato
	    if (view.getBottoniToggle().get(3).isSelected()) {
	    	
	        List<String> corsiSelezionati = new ArrayList<>();
	        
	        for (JCheckBox cb : view.getCheckBoxes()) {
	        	
	            if (cb.isSelected()) {
	            	
	            	//Stesso trattamento per i CheckBoxes
	                corsiSelezionati.add(cb.getText());
	                
	            }
	            
	        }
	        
	        abbonamentoDTO.setCorsiSelezionati(corsiSelezionati);
	        
	    } else {
	    	
	        // Se "Sala Corsi" non è selezionato, allora la corrispondente lista è posta vuota
	        abbonamentoDTO.setCorsiSelezionati(new ArrayList<>());
	        
	    }
	    
	}
	
	//----------------------------------------------------------------
	
}
