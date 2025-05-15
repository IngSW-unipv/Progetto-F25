package it.unipv.poisw.f25.gympal.ReceptionistController;

import java.awt.Color;

import java.util.List;


import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.GUI.ReceptionistDashboardView;
import it.unipv.poisw.f25.gympal.GUI.SubscriptionCustomizationView;


public class SubscriptionCustomizationController {
	
	private SubscriptionCustomizationView view;
	private ReceptionistDashboardView mainView;
	
	//----------------------------------------------------------------
	
	public SubscriptionCustomizationController(SubscriptionCustomizationView scv,
											   ReceptionistDashboardView recDashView) {
		
		view = scv;
		mainView = recDashView;
		
		impostaEventiToggleBtns();
		impostaEventiCheckBox();
		
	}
	
	//----------------------------------------------------------------
	
	private void impostaEventiToggleBtns() {
		List<JToggleButton> toggleBtnsList = view.getBottoniToggle();

		for (JToggleButton btn : toggleBtnsList) {

			// Ascoltatore per il bottone "Sala corsi"
			
			if (btn == view.getBottoniToggle().get(3)) {
				
				btn.addItemListener(e -> {
					
					if (btn.isSelected()) {
						
						btn.setBackground(Color.GREEN);
						
						view.getCorsiPanel().setVisible(true);

						// Forzatura aggiornamento divider
						SwingUtilities.invokeLater(() -> {
							
							view.getSplitPane().setDividerLocation(0.5);
							
						});

					} else {
						
						btn.setBackground(Color.RED);
						
						view.getCorsiPanel().setVisible(false);

						SwingUtilities.invokeLater(() -> {
							
							view.getSplitPane().setDividerLocation(1.0); // Divider tutto in alto
							
						});
						
					}

					view.revalidate();
					view.repaint();
				});
				
			} else {
				
				btn.addItemListener(e -> {
					
					if (btn.isSelected()) {
						
						btn.setBackground(Color.GREEN); // Modifica colore quando selezionato
						
					} else {
						
						btn.setBackground(Color.RED); // Modifica colore quando non selezionato
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
	            	
	                btn.setBackground(Color.GREEN);
	                
	                
	            } else {
	            	
	                btn.setBackground(Color.RED);
	                
	            }
	            
	        });
  
		}
        
	}
	
	//----------------------------------------------------------------

}
