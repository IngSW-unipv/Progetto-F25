package it.unipv.poisw.f25.gympal.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
 
	private static final long serialVersionUID = 1L;

	//----------------------------------------------------------------
	
	private JPanel mainPanel;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField idField;
    private JButton loginButton;
    
	//----------------------------------------------------------------

    public LoginView() {
    	
        setTitle("Login Staff");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null); // Centra la finestra

        // Inizializzazione componenti
        nomeField = new JTextField(20);
        cognomeField = new JTextField(20);
        idField = new JTextField(15);
        loginButton = new JButton("Accedi");

        // Creazione pannello principale con layout
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Riga 1 - Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(nomeField, gbc);

        // Riga 2 - Cognome
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Cognome:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(cognomeField, gbc);

        // Riga 3 - ID
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("ID Staff:"), gbc);

        gbc.gridx = 1;
        mainPanel.add(idField, gbc);

        // Riga 4 - Bottone
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        // Aggiunta del pannello principale al frame
        add(mainPanel);
    }
    
	//----------------------------------------------------------------

    public void aggiungiLoginListener(ActionListener l) {
    	
        loginButton.addActionListener(l);
        
    }

	//----------------------------------------------------------------
    
    public String getNome() {
    	
        return nomeField.getText().trim();
        
    }

	//----------------------------------------------------------------
    
    public String getCognome() {
    	
        return cognomeField.getText().trim();
        
    }
    
	//----------------------------------------------------------------

    public String getID() {
    	
        return idField.getText().trim();
        
    }

	//----------------------------------------------------------------
    
    public void mostraMessaggio(String msg) {
    	
        JOptionPane.showMessageDialog(this, msg);
        
    }
    
}
