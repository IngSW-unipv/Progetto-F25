package it.unipv.poisw.f25.gympal.GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;


public class ReceptionistDashboardView extends JFrame{

	private static final long serialVersionUID = 1L;
	
	//----------------------------------------------------------------
	
    private CardLayout cardLayout;
    private JPanel pannelloDestro;
	
	private JPanel  pannelloSinistro;
    private JButton logOutButton;
    private JButton registerNewClientButton;
    private JButton modifySubscriptionButton;
    
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
        
        modifySubscriptionButton = new JButton ("Modifica Abbonamento");
        
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
        
        JPanel schermata1 = creaSchermata("Sei nella schermata 1", Color.CYAN);
        
        JPanel schermata2 = creaSchermata("Sei nella schermata 2", Color.PINK);
  
        pannelloDestro.add(schermata0, "SCHERMATA0");
        
        pannelloDestro.add(schermata1, "SCHERMATA1");
        
        pannelloDestro.add(schermata2, "SCHERMATA2");
        
        //----------------------------------------------------------------
        
        /*I bottoni sono raccolti in una lista, su cui il controllore itera per aggiungervi i
         *listener*/
        
        List<JButton> bottoni = new ArrayList<>();
        
        bottoni.add(registerNewClientButton);
        
        bottoni.add(modifySubscriptionButton);
        
        bottoni.add(logOutButton);
        
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
    
    public List<JButton> getBottoni() {
    	
        List<JButton> bottoni = new ArrayList<>();
        
        bottoni.add(registerNewClientButton);
        
        bottoni.add(modifySubscriptionButton);
        
        bottoni.add(logOutButton);
        
        return bottoni;
    }
    
    //----------------------------------------------------------------
    
    /*la View si occupa solo dell'aspetto grafico, mentre il Controller 
     *gestisce la logica e le interazioni.
     *
     *Il CardLayout è parte del sistema che gestisce quale pannello 
     *mostrare nella parte destra dell’interfaccia - risultato ottenuto tramite il metodo
     *
     *cardLayout.show(pannelloDestro, "NOME_CARTA");
     *
     *cardLayout è privato nella View, quindi per farlo usare al Controller,
     *va esposto tramite un getter pubblico*/
    
    public CardLayout getCardLayout() {
    	
        return cardLayout;
        
    }

    
    /*Restituisce il "pannelloDestro", come impostato in questa classe*/
    public JPanel getPannelloDestro() {
    	
        return pannelloDestro;
        
    }
    
    //----------------------------------------------------------------
    

}
