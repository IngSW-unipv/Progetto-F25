package it.unipv.poisw.f25.gympal.GUI.Receptionist;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class ReceptionistDashboardView extends JFrame implements IReceptionistDashboardView {

    private static final long serialVersionUID = 1L;
	
    //----------------------------------------------------------------
	
    private CardLayout cardLayout;
    private JPanel pannelloDestro;
	
    private JPanel pannelloSinistro;
    private JButton logOutButton;
    private JButton registerNewClientButton;
    private JButton modifySubscriptionButton;

    // Mappa interna per associare i comandi alle azioni
    private Map<String, Runnable> azioniComandi = new HashMap<>();

    //----------------------------------------------------------------
    
    public ReceptionistDashboardView () {
    	
        setTitle("Receptionist Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth/2,screenHeight/2);
        setLocation(screenWidth/4,screenHeight/4);
        
        //----------------------------------------------------------------
        
        /*Costruzione pannello sinistro*/
        
        logOutButton = new JButton("Log Out");
        
        registerNewClientButton = new JButton("Registra Nuovo Cliente");
        
        modifySubscriptionButton = new JButton (" - Modifica - Rinnovo - Cancellazione - Abbonamento");
        
        pannelloSinistro = new JPanel();
        
        pannelloSinistro.setLayout(new GridLayout(3, 1, 10, 10));
        
        pannelloSinistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        pannelloSinistro.add(registerNewClientButton);
        
        pannelloSinistro.add(modifySubscriptionButton);
        
        pannelloSinistro.add(logOutButton);
        
        //----------------------------------------------------------------
    	
        /*Costruzione pannello destro*/
        
        /*Il "CardLayout" è applicato soltanto al pannello che contiene tutte le 'carte'.
         *Questo è un layout manager che gestite più componenti all'interno di un singolo
         *contenitore, mostrando uno solo di questi per volta. 
         *Le schermate sono figlie di "pannelloDestro" */
        
        cardLayout = new CardLayout();
        
        pannelloDestro = new JPanel(cardLayout);
        
        JPanel schermata0 = creaSchermata("Benvenuto, Receptionist! Seleziona un'operazione~", Color.WHITE);
        
        //JPanel schermata1 = creaSchermata("Sei nella schermata 1", Color.CYAN);
        
        JPanel schermata2 = creaSchermata("Sei nella schermata 2", Color.PINK);
  
        pannelloDestro.add(schermata0, "SCHERMATA0"); //Schermata di benvenuto
        
        //pannelloDestro.add(schermata1, "SCHERMATA1");
        
        pannelloDestro.add(schermata2, "SCHERMATA2");
        
        //----------------------------------------------------------------
        
        /*Sono costruiti il "pannelloSinistro" ed il "pannelloDestro", poi inseriti nello
         *"splitPane", che è dunque assegnato al Frame*/
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pannelloSinistro, pannelloDestro);
        
        splitPane.setDividerLocation(200); 
        
        splitPane.setDividerSize(5);
        
        splitPane.setEnabled(false); /*Impedisce alterazioni al rapporto fra le dim. dei pannelli, da parte dell'utente*/
        
        //----------------------------------------------------------------
        
        getContentPane().add(splitPane);
        
    }
    
    //----------------------------------------------------------------
    
    private JPanel creaSchermata(String testo, Color coloreSfondo) {
    	
        JPanel panel = new JPanel();
        
        panel.setBackground(coloreSfondo);
        
        panel.add(new JLabel(testo));
        
        return panel;
    }
    
    //----------------------------------------------------------------    

    /* Implementazione metodo dell’interfaccia: associa una Runnable a
     * un comando e al bottone relativo */
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
    
}
