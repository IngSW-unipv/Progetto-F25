package it.unipv.poisw.f25.gympal.GUI.LoginScreen;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField idField;
    private JButton loginButton;

    // Componenti per bypass
    private JCheckBox bypassCheckBox;
    private JComboBox<String> tipoComboBox;
    
	//----------------------------------------------------------------

    public LoginView() {
    	
        setTitle("Login Staff");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);

        nomeField = new JTextField(20);
        cognomeField = new JTextField(20);
        idField = new JTextField(20);
        loginButton = new JButton("Accedi");

        // Bypass components
        bypassCheckBox = new JCheckBox("Bypass Login (dev)");
        tipoComboBox = new JComboBox<>(new String[] { "DIP", "REC", "MAN" });
        tipoComboBox.setEnabled(false);

        bypassCheckBox.addActionListener(e -> tipoComboBox.setEnabled(bypassCheckBox.isSelected()));

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(nomeField, gbc);

        // Cognome
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Cognome:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(cognomeField, gbc);

        // ID Staff
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("ID Staff:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(idField, gbc);

        // Checkbox bypass
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        mainPanel.add(bypassCheckBox, gbc);

        // ComboBox tipo
        gbc.gridy = 4;
        mainPanel.add(tipoComboBox, gbc);

        // Bottone login
        gbc.gridy = 5;
        mainPanel.add(loginButton, gbc);

        add(mainPanel);
        getRootPane().setDefaultButton(loginButton);
        
    }
    
	//----------------------------------------------------------------

    // Listener per il bottone login
    public void addLoginListener(ActionListener listener) {
    	
        loginButton.addActionListener(listener);
        
    }
    
	//----------------------------------------------------------------

    // Getters dei campi
    public String getNome() {
        return nomeField.getText().trim();
    }

    public String getCognome() {
        return cognomeField.getText().trim();
    }

    public String getID() {
        return idField.getText().trim();
    }
    
	//----------------------------------------------------------------

    public void mostraMessaggio(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    
	//----------------------------------------------------------------

    // -------------------------
    // Bypass methods
    // -------------------------
    public boolean isBypassEnabled() {
    	
        return bypassCheckBox.isSelected();
        
    }

    public String getTipoBypassato() {
    	
        return (String) tipoComboBox.getSelectedItem();
        
    }
    
	//----------------------------------------------------------------
    
}
