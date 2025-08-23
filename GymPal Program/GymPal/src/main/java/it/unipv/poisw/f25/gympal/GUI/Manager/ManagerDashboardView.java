package it.unipv.poisw.f25.gympal.GUI.Manager;

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

public class ManagerDashboardView extends JFrame implements IDashboard{

	private static final long serialVersionUID = 1L;
	
    //----------------------------------------------------------------
	
    private CardLayout cardLayout;
    
    private JPanel pannelloDestro;
    private JPanel pannelloSinistro;
    
    private JButton rectClientData;
    private JButton addRemModEvents;
    private JButton planShifts;
    private JButton logOutButton;
    
    private JSplitPane operazioniECards;
    
    private Map<String, Runnable> azioniComandi = new HashMap<>();
    
    //----------------------------------------------------------------
    
    public ManagerDashboardView() {
    	
        setTitle("Manager Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /*PannelloSinistro############################################*/
        
        pannelloSinistro = new JPanel();
        
        pannelloSinistro.setLayout(new GridBagLayout());
        pannelloSinistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        rectClientData = new JButton("<html><center>Rettifica<br>"
        						   + "Dati Cliente</center></html>");
        
        addRemModEvents = new JButton("<html><center>Aggiungi<br>Rimuovi<br>Modifica:"
        							+ "<br>Eventi<br>&<br>Corsi</center></html>");
        
        planShifts = new JButton("<html><center>Profili<br>Dipendenti<br>&<br>"
        					   + "Pianifica<br>Turni Lavoro</center></html>");
        
        logOutButton = new JButton("Log Out");
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 10, 0); // Spazio verticale tra i bottoni
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; 
        
        gbc.gridy = 0;
        pannelloSinistro.add(rectClientData, gbc);

        gbc.gridy = 1;
        pannelloSinistro.add(addRemModEvents, gbc);
        
        gbc.gridy = 2;
        pannelloSinistro.add(planShifts, gbc);

        gbc.gridy = 3;
        pannelloSinistro.add(logOutButton, gbc);
        
        pannelloSinistro.setPreferredSize(new Dimension(200, 1300));
        
        /*PannelloDestro##############################################*/
        
        cardLayout = new CardLayout();
        
        pannelloDestro = new JPanel(cardLayout);
        
        JPanel schermata0 = creaSchermata("Benvenuto, Manager! Seleziona un'operazione~",
        								  Color.WHITE);
        
        pannelloDestro.add(schermata0, "SCHERMATA0");
        
        pannelloDestro.setPreferredSize(new Dimension(2000, 1300));
        
        /*SplitPane####################################################*/
        
        operazioniECards = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pannelloSinistro, pannelloDestro);
        
        operazioniECards.setDividerLocation(200); 
        
        operazioniECards.setDividerSize(5);
        
        operazioniECards.setEnabled(false);
        
        /*############################################################*/
        
        getContentPane().add(operazioniECards);
        
        pack();
        setLocationRelativeTo(null);
    	
    }
    
    //----------------------------------------------------------------

    @Override
    public void aggiungiComando(String nomeComando, Runnable azione) {
    	
        azioniComandi.put(nomeComando, azione);
        
        JButton bottone = null;
        
        switch (nomeComando) {
        
            case "RETTIFICA":
                bottone = rectClientData;
                break;
                
            case "ADD_REM_MOD_EVENTS_COURSES":
                bottone = addRemModEvents;
                break;
                
            case "PLAN_SHIFTS_HANDLE_EMPLOYEES":
            	bottone = planShifts;
            	break;
                
            case "LOGOUT":
                bottone = logOutButton;
                break;
                
            default:
            	
                throw new IllegalArgumentException("Comando non riconosciuto: " +
                									nomeComando);
        }

        for (ActionListener al : bottone.getActionListeners()) {
            bottone.removeActionListener(al);
            
        }

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
