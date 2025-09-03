package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosViewHelpers;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import it.unipv.poisw.f25.gympal.ApplicationLayer.ServiziGenerali.ValidazioneCampi.CampoValidabile.ICampoValidabile;

public class ValidazioneCampo {

    public static void applicaFeedbackSwing(ICampoValidabile campo) {
    	
        JTextField field = campo.getField();

        field.getDocument().addDocumentListener(new DocumentListener() {

            private void validazione() {
            	
                if (campo.isValido()) {
                	
                    field.setBackground(Color.decode("#b2fab4")); // verde chiaro
                    
                } else {
                	
                    field.setBackground(Color.decode("#ffcccc")); // rosso chiaro
                    
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
                // non usato con JTextField
            }
            
        });
        
    }
    
}

