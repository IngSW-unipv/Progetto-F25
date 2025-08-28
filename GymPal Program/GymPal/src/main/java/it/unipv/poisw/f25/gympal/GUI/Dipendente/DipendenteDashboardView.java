package it.unipv.poisw.f25.gympal.GUI.Dipendente;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.plaf.FontUIResource;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DashboardsCommonInterface.IDashboard;
import it.unipv.poisw.f25.gympal.GUI.Utilities.GestioneFont.FontManager.IFontManager;

public class DipendenteDashboardView extends JFrame implements IDashboard {


	private static final long serialVersionUID = 1L;
	
    //----------------------------------------------------------------
	
    private CardLayout cardLayout;
    
    private JPanel pannelloDestro;
    private JPanel pannelloSinistro;
    
    private JButton mostraTurni;
    private JButton logOutButton;
    
    private JSplitPane operazioniECards;
    
    private Map<String, Runnable> azioniComandi = new HashMap<>();
    
    /*Servizio per alterazioni dinamiche(run-time) del font*/
    private IFontManager fontManager;
    
    /*ComboBox per selezione dimensioni font*/
    private JComboBox<Integer> fontSizeSelector;
    
    //----------------------------------------------------------------
    
    public DipendenteDashboardView(IFontManager fontManager) {
    	
    	this.fontManager = fontManager;
    	
    	setTitle("Dipendente Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /*PannelloSinistro############################################*/
        
        pannelloSinistro = new JPanel();
        
        pannelloSinistro.setLayout(new GridBagLayout());
        pannelloSinistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mostraTurni = new JButton("<html><center>Visualizza<br>"
        						   + "Turni</center></html>");
       
        logOutButton = new JButton("Log Out");
        
        /*Selezione dimensioni font*/////////////////////////////
        fontSizeSelector = new JComboBox<>(new Integer[]{12, 14, 15, 16, 18, 20, 22, 24});
        fontSizeSelector.setSelectedItem(15); // default
        /////////////////////////////////////////////////////////
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 0, 10, 0); // Spazio verticale tra i bottoni
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; 
        
        gbc.gridy = 0;
        pannelloSinistro.add(mostraTurni, gbc);

        gbc.gridy = 1;
        pannelloSinistro.add(logOutButton, gbc);
        
        gbc.gridy = 2;
        pannelloSinistro.add(new JLabel("Dimensione Font:"), gbc);

        gbc.gridy = 3;
        pannelloSinistro.add(fontSizeSelector, gbc);
        
        pannelloSinistro.setPreferredSize(new Dimension(200, 1300));
        
        /*PannelloDestro##############################################*/
        
        cardLayout = new CardLayout();
        
        pannelloDestro = new JPanel(cardLayout);
        
        JPanel schermata0 = creaSchermata("Benvenuto, Dipendente! Seleziona un'operazione~",
        								  Color.WHITE);
        
        pannelloDestro.add(schermata0, "SCHERMATA0");
        
        //////////////////////////////////////////////////////////////
        /*Listener*/
        fontSizeSelector.addActionListener(e -> {
        	
            int size = (int) fontSizeSelector.getSelectedItem();
            Font newFont = new FontUIResource("Segoe UI", Font.PLAIN, size);
            this.fontManager.updateFont(newFont);
            refreshCurrentCard();
            aggiornaFontPannelloSinistro(newFont);
            
        });
        /////////////////////////////////////////////////////////
        
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
    public void dispose() {
    	
        super.dispose();
        
    }
	
    //----------------------------------------------------------------

	@Override
    public void mostraSchermata(String nome) {
    	
        cardLayout.show(pannelloDestro, nome);
        
    }
	
    //----------------------------------------------------------------

	@Override
	public void aggiungiComando(String nomeComando, Runnable azione) {
    	
        azioniComandi.put(nomeComando, azione);
        
        JButton bottone = null;
        
        switch (nomeComando) {
        
            case "MOSTRA_TURNI":
                bottone = mostraTurni;
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

	@Override
    public void registraSchermata(String nome, JPanel schermata) {

        pannelloDestro.add(schermata, nome);
        
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
    
    private void refreshCurrentCard() {
        for (Component comp : pannelloDestro.getComponents()) {
        	
            if (comp.isVisible()) {
            	
                comp.revalidate();
                comp.repaint();
                break;
                
            }
            
        }
        
    }
    
    //----------------------------------------------------------------  
    
    private void aggiornaFontPannelloSinistro(Font font) {
    	
        for (Component comp : pannelloSinistro.getComponents()) {
            Class<?> classe = comp.getClass();
            if (JButton.class.isAssignableFrom(classe) || 
            	JLabel.class.isAssignableFrom(classe)) {
            	
                comp.setFont(font);
                
            }
            
        }
        
    }

    //----------------------------------------------------------------  

}
