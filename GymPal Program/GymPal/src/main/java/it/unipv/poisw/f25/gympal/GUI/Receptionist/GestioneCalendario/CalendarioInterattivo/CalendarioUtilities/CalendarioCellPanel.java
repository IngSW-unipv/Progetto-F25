package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;

public class CalendarioCellPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private LocalDate data;
	private int ora;
	private int minuti;

	private JButton button;
	
	//----------------------------------------------------------------

	public CalendarioCellPanel(LocalDate data, int ora, int minuti) {
		
	    this.data = data;
	    this.ora = ora;
	    this.minuti = minuti;

	    setBackground(Color.LIGHT_GRAY);
	    setPreferredSize(new Dimension(240, 120));
	    setBorder(BorderFactory.createLineBorder(Color.BLACK));

	    setLayout(new GridBagLayout());
	    
	    /*############################################################*/

	    button = new JButton(String.format("%02d:%02d", ora, minuti));
	    button.setOpaque(true);
	    button.setFocusable(false);
	    button.setFont(new Font("SansSerif", Font.BOLD, 18));
	    button.setPreferredSize(new Dimension(180, 80));  
	    
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.anchor = GridBagConstraints.CENTER;

	    add(button, gbc);
	    
	    /*############################################################*/
	    
	    setOpaque(true);
	    
	}

	
	//----------------------------------------------------------------


	public LocalDate getData() {
		
	    return data;
	    
	}
	
	//----------------------------------------------------------------

	public int getOra() {
		
	    return ora;
	    
	}
	    
    //----------------------------------------------------------------

	public int getMinuti() {
		
	    return minuti;
	    
	}
	    
    //----------------------------------------------------------------
	    
	public JButton getButton() {
	    	
	    return button;
	        
	}
	    
    //----------------------------------------------------------------
	    
	public void addButtonListener(ActionListener listener) {
	    	
		button.addActionListener(listener);
	    	
	}
	    
    //----------------------------------------------------------------
	    
	public void aggiornaColoreSfondo(IDatiCellaCalendarioDTO dati) {
		
	    boolean haContenuti = !(dati.getCorsi().isEmpty()
	                          && dati.getAppuntamentiPT().isEmpty()
	                          && dati.getEventiGenerici().isEmpty());

	    if (haContenuti) {
	        	
	        setBackground(new Color(200, 255, 200)); // Verde chiaro
	            
	    } else {
	        	
	        setBackground(new Color(255, 200, 200)); // Rosso chiaro
	            
	    }
	    
	        repaint(); 
	        
	    }
	    
    //----------------------------------------------------------------
	    
}
