package it.unipv.poisw.f25.gympal.GUI.Receptionist;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;

public class ReceptionistDashboardView extends JFrame implements IDashboard {

    private static final long serialVersionUID = 1L;
	
    //----------------------------------------------------------------
	
    private CardLayout cardLayout;
    
    private JPanel pannelloDestro;
	private JPanel pannelloSinistro;
	
    private JButton logOutButton;
    private JButton registerNewClientButton;
    private JButton modifySubscriptionButton;
    private JButton corsiEdAppuntamentiButton;
    
    //////////////////////////
    private JButton turniPersonaliButton;
    /////////////////////////
    
    private JSplitPane splitPane;

    // Mappa interna per associare i comandi alle azioni
    private Map<String, Runnable> azioniComandi = new HashMap<>();

    //----------------------------------------------------------------
    
    public ReceptionistDashboardView () {
    	
    	    	
        setTitle("Receptionist Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /*PannelloSinistro############################################*/
        
        /* Costruzione pannello sinistro */

        logOutButton = new JButton("Log Out");

        /* Impiegato codice html per spezzare le parole componenti i nomi dei bottoni,
         * ed incolonnarle con allineamento centrale.
         * La particella "<br>" spezza le parole, "<center>" definisce il loro allineamento. */
        registerNewClientButton = new JButton("<html><center>Registra<br>Nuovo Cliente</center></html>");

        modifySubscriptionButton = new JButton("<html><center>Modifica<br>Rinnovo<br>Cancellazione<br>Abbonamento</center></html>");
        
        corsiEdAppuntamentiButton = new JButton("<html><center>Gestione calendario<br>"
							        		  + "partecipazione corsi<br>"
							        		  + "ed<br>appuntamenti PT</html>");
        
        ///////////////////////////////////
        turniPersonaliButton = new JButton("<html><center>Visualizza<br>Turni Personali</center></html>");
        ///////////////////////////////////
        pannelloSinistro = new JPanel();

        /*Scelto "GridBagLayout" anziché "GridLayout" siccome quest'ultimo soffre di
         *problemi di scaling su schermi ad alti DPI, come quelli dei portatili*/
        pannelloSinistro.setLayout(new GridBagLayout());

        pannelloSinistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 10, 0); // Spazio verticale tra i bottoni
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; 

        gbc.gridy = 0;
        pannelloSinistro.add(registerNewClientButton, gbc);

        gbc.gridy = 1;
        pannelloSinistro.add(modifySubscriptionButton, gbc);
        
        gbc.gridy = 2;
        pannelloSinistro.add(corsiEdAppuntamentiButton, gbc);
        
        /////////////////////////////////////////////////
        gbc.gridy = 3;
        pannelloSinistro.add(turniPersonaliButton, gbc);
        /////////////////////////////////////////////////

        gbc.gridy = 4;
        pannelloSinistro.add(logOutButton, gbc);
        
        
        pannelloSinistro.setPreferredSize(new Dimension(200, 1300));
        
        /*PannelloDestro##############################################*/
    	
        /*Costruzione pannello destro*/
        
        /*Il "CardLayout" è applicato soltanto al pannello che contiene tutte le 'carte'.
         *Questo è un layout manager che gestite più componenti all'interno di un singolo
         *contenitore, mostrando uno solo di questi per volta. 
         *Le schermate sono figlie di "pannelloDestro" */
        
        cardLayout = new CardLayout();
        
        pannelloDestro = new JPanel(cardLayout);
        
        JPanel schermata0 = creaSchermata("Benvenuto, Receptionist! Seleziona un'operazione~", Color.WHITE);
        
  
        pannelloDestro.add(schermata0, "SCHERMATA0"); //Schermata di benvenuto
        
        
        pannelloDestro.setPreferredSize(new Dimension(2000, 1300));
        
        /*SplitPane####################################################*/
        
        /*Sono costruiti il "pannelloSinistro" ed il "pannelloDestro", poi inseriti nello
         *"splitPane", che è dunque assegnato al Frame*/
        
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pannelloSinistro, pannelloDestro);
        
        splitPane.setDividerLocation(200); 
        
        splitPane.setDividerSize(5);
        
        splitPane.setEnabled(false); /*Impedisce alterazioni al rapporto fra le dim. dei pannelli, da parte dell'utente*/
        
        /*############################################################*/
        
        getContentPane().add(splitPane);
        
        pack();
        setLocationRelativeTo(null);
        
    }
    
    //----------------------------------------------------------------

    /* Implementazione metodo dell’interfaccia: associa una Runnable ad
     * un comando ed al bottone relativo */
    @Override
    public void aggiungiComando(String nomeComando, Runnable azione) {
    	
        azioniComandi.put(nomeComando, azione);
        
        JButton bottone = null;
        
        switch (nomeComando) {
        
            case "REGISTER":
                bottone = registerNewClientButton;
                break;
                
            case "MODIFY":
                bottone = modifySubscriptionButton;
                break;
                
            case "CALENDAR":
                bottone = corsiEdAppuntamentiButton;
                break;
             
                //////////////////////////////////
            case "TURNI_RECEPTIONIST":
                bottone = turniPersonaliButton;
                break;
                //////////////////////////////////
                
            case "LOGOUT":
                bottone = logOutButton;
                break;
                
            default:
                throw new IllegalArgumentException("Comando non riconosciuto: " +
                									nomeComando);
        }
        
        // Rimuovo tutti i listener esistenti per evitare duplicazioni
        for (ActionListener al : bottone.getActionListeners()) {
            bottone.removeActionListener(al);
            
        }
        // Aggiungo listener che esegue la Runnable passata
        bottone.addActionListener(e -> azione.run());
        
    }

    //----------------------------------------------------------------      
    
    /* Implementazione metodo dell’interfaccia: mostra la schermata specificata */
    @Override
    public void mostraSchermata(String nome) {
        cardLayout.show(pannelloDestro, nome);
    }
    
    //----------------------------------------------------------------  

    /* Implementazione metodo dell’interfaccia: aggiunge una nuova schermata */
    @Override
    public void registraSchermata(String nome, JPanel schermata) {

        pannelloDestro.add(schermata, nome);
    }
    
    //----------------------------------------------------------------  
    
    @Override
    public void dispose() {
        super.dispose();
    }
    
    //---------------------------------------------------------------- 
    
    @Override
    public JFrame getMainFrame() {
    	
    	return this;
    	
    }
    
    //---------------------------------------------------------------- 
    
    private JPanel creaSchermata(String testo, Color coloreSfondo) {
    	
        JPanel panel = new JPanel();
        
        panel.setBackground(coloreSfondo);
        
        panel.add(new JLabel(testo));
        
        return panel;
    }
    
    //----------------------------------------------------------------  
    
}
