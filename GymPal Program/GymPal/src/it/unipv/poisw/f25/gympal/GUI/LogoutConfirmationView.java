package it.unipv.poisw.f25.gympal.GUI;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogoutConfirmationView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//----------------------------------------------------------------
	
	private JButton confirmButton;
	private JButton cancelButton;
	
	//----------------------------------------------------------------
	
	public LogoutConfirmationView() {
		
		confirmButton = new JButton("Si");
		cancelButton = new JButton("No");
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel messageLabel = new JLabel("Sei sicuro di voler uscire?");
		messageLabel.setAlignmentX(CENTER_ALIGNMENT); //Centra ogni componente in orizzontale.

		confirmButton = new JButton("Sì");
		confirmButton.setAlignmentX(CENTER_ALIGNMENT);

		cancelButton = new JButton("No");
		cancelButton.setAlignmentX(CENTER_ALIGNMENT);
		

		// Spingie i componenti verticalmente verso il centro
		
		add(Box.createVerticalGlue());
		add(messageLabel);
		
		add(Box.createVerticalStrut(20));
		add(confirmButton);
		
		add(Box.createVerticalStrut(10));
		add(cancelButton);
		
		add(Box.createVerticalGlue()); // Aggiunge spazio sotto i componenti
		
	}
	
	//----------------------------------------------------------------	

	public JButton getConfirmButton() {
		
		return confirmButton;
		
	}

	public JButton getCancelButton() {
		
		return cancelButton;
		
	}

}
