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
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth/2,screenHeight/2);
        setLocation(screenWidth/4,screenHeight/4);

        // Inizializzazione componenti
        nomeField = new JTextField(20); /*Tra parentesi tonde, la larghezza del campo, in colonne*/
        cognomeField = new JTextField(20);
        idField = new JTextField(20);
        loginButton = new JButton("Accedi");

        // Creazione pannello principale con layout
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        /*Dapprima sono impostate le coordinate a cui sarà collocato il componente, agendo su
         *"gbc", dopodiché si procede ad aggiungere il componente al pannello*/
        
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
        
        
        
        /*Questa istruzione fa si che "loginButton" corrisponda al bottone di 'default' della
         *view, di modo che esso reagisca alla pressione del tasto "Invio"*/
        
        /*Il metodo "setDefaultButton" funziona perché invocato in una classe che estende
         *"JFrame"*/
        
        getRootPane().setDefaultButton(loginButton);
        
    }
    
	//----------------------------------------------------------------

    public void aggiungiLoginListener(ActionListener al) {
    	
        loginButton.addActionListener(al);
        
    }

	//----------------------------------------------------------------
    
    public String getNome() {
    	
        /*Il metodo "getText()" restituisce sotto forma di "String"
         *il contenuto del campo di testo su cui è invocato*/
    	
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
    
   //----------------------------------------------------------------
    
}
