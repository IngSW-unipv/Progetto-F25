package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosViewHelpers;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import it.unipv.poisw.f25.gympal.GUI.Utilities.RegexCheck;

public class ValidazioneCampo {
	
	public static void aggiungiValidatore(JTextField field, String regex) {
		
        field.getDocument().addDocumentListener(new DocumentListener() {

            private void validazione() {
            	
                String contenutoCampo = field.getText().trim();
                
                if (RegexCheck.check(contenutoCampo, regex)) {
                	
                    field.setBackground(Color.decode("#b2fab4")); // Verde chiaro
                    
                } else {
                	
                    field.setBackground(Color.decode("#ffcccc")); // Rosso chiaro
                    
                }
                
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
            	
                validazione();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	
                validazione();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	
                // Questo metodo non viene utilizzato con i JTextField
            	
            }
            
        });
        
    }

}
