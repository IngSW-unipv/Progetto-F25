package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneAbbonamento.RecuperoDati;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.IEtichettaPiuCampoFactory;

public class RecuperoDatiView extends JPanel implements IRecuperoDatiView{

	private static final long serialVersionUID = 1L;
	
	/*Input*/
	private JTextField codiceFiscale;
	
	/*Navigazione*/
	private JButton annulla;
	private JButton rinnova;
	private JButton modifica;
	private JButton elimina;
	
	/*Operzioni*/
	private JButton estrai;
	
	/*Visualizzazione dati estratti*/
	private JLabel cf;
	private JLabel nome;
	private JLabel cognome;
	private JLabel contatto;
	private JLabel sesso;
	private JLabel durataAbbonamento;
	private JLabel inizioAbb;
	private JLabel fineAbb;
	
	/*Pannelli & SplitPane*/
	private JSplitPane mainSplitPanel;
	private JSplitPane userInfoSplitPanel;
	
	private JPanel navigationPanel;
	
	/*Servizi*/
	private IDynamicButtonSizeSetter buttonSizeSetter;
	private IEtichettaPiuCampoFactory campoEtichettato;
	
	//----------------------------------------------------------------
	
	public RecuperoDatiView (IDynamicButtonSizeSetter setter,
							 IEtichettaPiuCampoFactory campoEtichettato,
							 IFontChangeRegister fontChangeRegister) {
		
		/*Servizi*/
		this.buttonSizeSetter = setter;
        this.campoEtichettato = campoEtichettato;

        /*Layout Pannello principale*/
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 5, 10, 5));

        /*Costruzione contenuto pannello*/
        initFields();
        JPanel upperPanel = buildUpperPanel();
        JScrollPane bottomScrollPane = buildBottomPanel();
        this.userInfoSplitPanel = buildUserInfoSplitPanel(upperPanel, bottomScrollPane);
        this.navigationPanel = buildNavigationPanel();
        this.mainSplitPanel = buildMainSplitPanel(userInfoSplitPanel, navigationPanel);

        add(mainSplitPanel);

        /*Sottoscrizione a meccanismo cambio-font*/
        fontChangeRegister.register(this, buttonSizeSetter);
    }

    //--------------------------------------------------------------
	
    // Inizializzazione dei campi e componenti base
    private void initFields() {
    	
        codiceFiscale = new JTextField(80);

        estrai = new JButton("Estrai Dati");

        cf = new JLabel("Codice fiscale cliente: ");
        nome = new JLabel("Nome cliente: ");
        cognome = new JLabel("Cognome cliente: ");
        contatto = new JLabel("Contatto cliente: ");
        sesso = new JLabel("Sesso cliente: ");
        durataAbbonamento = new JLabel("Durata abbonamento: ");
        inizioAbb = new JLabel("Data inizio abbonamento: ");
        fineAbb = new JLabel("Data fine abbonamento:");

        JLabel[] labels = {cf, nome, cognome, contatto, sesso, 
        				   durataAbbonamento, inizioAbb, fineAbb};
        
        for (JLabel label : labels) {
        	
            label.setAlignmentX(CENTER_ALIGNMENT);
            
        }

        codiceFiscale.setAlignmentX(CENTER_ALIGNMENT);
        estrai.setAlignmentX(CENTER_ALIGNMENT);
        
    }

    //--------------------------------------------------------------
    
    // Pannello superiore: input + estrazione
    private JPanel buildUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
        upperPanel.setBorder(BorderFactory.createTitledBorder("Estrazione dati:"));

        JLabel titleLabel = new JLabel("-=Inserisci codice fiscale del cliente=-");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        buttonSizeSetter.uniformButtonSize(estrai);

        upperPanel.add(Box.createVerticalGlue());
        upperPanel.add(titleLabel);
        upperPanel.add(Box.createVerticalStrut(30));
        upperPanel.add(campoEtichettato.creaCampoEtichettato("CF: ", codiceFiscale));
        upperPanel.add(Box.createVerticalStrut(10));
        upperPanel.add(estrai);
        upperPanel.add(Box.createVerticalStrut(30));
        upperPanel.add(Box.createVerticalGlue());

        return upperPanel;
        
    }

    //--------------------------------------------------------------
    
    // Pannello inferiore: visualizzazione dati estratti
    private JScrollPane buildBottomPanel() {
    	
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Dati estratti:"));

        bottomPanel.add(Box.createVerticalGlue());

        JLabel[] labels = {cf, nome, cognome, contatto, sesso, 
        				   durataAbbonamento, inizioAbb, fineAbb};
        
        for (JLabel label : labels) {
        	
            bottomPanel.add(label);
            bottomPanel.add(Box.createVerticalStrut(10));
            
        }

        bottomPanel.add(Box.createVerticalGlue());
        
        JScrollPane scrollableBottomPanel = new JScrollPane(bottomPanel);
        scrollableBottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollableBottomPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollableBottomPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableBottomPanel.setBorder(null);
        scrollableBottomPanel.getVerticalScrollBar().setUnitIncrement(20);

        return scrollableBottomPanel;
        
    }

    //--------------------------------------------------------------
    
    // Split con pannello superiore e inferiore
    private JSplitPane buildUserInfoSplitPanel(JPanel upper, JScrollPane bottom) {
    	
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setTopComponent(upper);
        split.setBottomComponent(bottom);
        SwingUtilities.invokeLater(() -> split.setDividerLocation(0.80));
        split.setEnabled(false);
        return split;
        
    }

    //--------------------------------------------------------------
    
    // Pannello pulsanti di navigazione (rinnova, modifica, elimina, annulla)
    private JPanel buildNavigationPanel() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(""));

        rinnova = new JButton("Rinnova");
        modifica = new JButton("Modifica");
        elimina = new JButton("Elimina");
        annulla = new JButton("Annulla");

        buttonSizeSetter.uniformButtonSize(rinnova, modifica, elimina, annulla);

        panel.add(Box.createHorizontalGlue());
        panel.add(annulla);
        panel.add(Box.createHorizontalStrut(100));
        panel.add(rinnova);
        panel.add(Box.createHorizontalStrut(100));
        panel.add(modifica);
        panel.add(Box.createHorizontalStrut(100));
        panel.add(elimina);
        panel.add(Box.createHorizontalGlue());

        return panel;
        
    }

    //--------------------------------------------------------------
    
    // Split principale con info e navigazione
    private JSplitPane buildMainSplitPanel(JSplitPane userInfo, JPanel navigation) {
    	
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setTopComponent(userInfo);
        split.setBottomComponent(navigation);
        SwingUtilities.invokeLater(() -> split.setDividerLocation(0.80));
        split.setEnabled(false);
        return split;
        
    }

    //--------------------------------------------------------------
    
    /*Input*/
    @Override public JTextField getCodiceFiscale() { return codiceFiscale; }
    
    //--------------------------------------------------------------
    
    /*Output a label*/
    @Override public JLabel getCfLabel() { return cf; }
    
    //--------------------------------------------------------------
    
    @Override public JLabel getNomeLabel() { return nome; }
    
    //--------------------------------------------------------------
    
    @Override public JLabel getCognomeLabel() { return cognome; }
    
    //--------------------------------------------------------------
    
    @Override public JLabel getContattoLabel() { return contatto; }
    
    //--------------------------------------------------------------
    
    @Override public JLabel getSessoLabel() { return sesso; }
    
    //--------------------------------------------------------------
    
    @Override public JLabel getDurataAbbonamentoLabel() { return durataAbbonamento; }
    
    //--------------------------------------------------------------
    
    @Override public JLabel getInizioAbbLabel() { return inizioAbb; }
    
    //--------------------------------------------------------------
    
    @Override public JLabel getFineAbbLabel() { return fineAbb; }
    
    //--------------------------------------------------------------

    @Override public void addEstraiListenr(ActionListener listener) { 
    	
    	estrai.addActionListener(listener); 
    	
    }
    
    //--------------------------------------------------------------
    
    @Override public void addAnnullaListener(ActionListener listener) {
    	
    	annulla.addActionListener(listener); 
    	
    }
    
    //--------------------------------------------------------------
    
    @Override public void addRinnovaListenr(ActionListener listener) { 
    	
    	rinnova.addActionListener(listener); 
    	
    }
    
	//----------------------------------------------------------------
    
    @Override public void addModificaListenr(ActionListener listener) { 
    	
    	modifica.addActionListener(listener); 
    	
    }
    
	//----------------------------------------------------------------
    
    @Override public void addEliminaListener(ActionListener listener) { 
    	
    	elimina.addActionListener(listener); 
    	
    }
    
	//----------------------------------------------------------------

    @Override public JPanel getMainPanel() { return this; }
    
	//----------------------------------------------------------------

    @Override public void setRinnovaEnabled(boolean enabled) { 
    	
    	rinnova.setEnabled(enabled); 
    	
    }
    
	//----------------------------------------------------------------
    
    @Override public void setModificaEnabled(boolean enabled) { 
    	
    	modifica.setEnabled(enabled); 
    	
    }
    
	//----------------------------------------------------------------
    
    @Override public void setEliminaEnabled(boolean enabled) { 
    	
    	elimina.setEnabled(enabled); 
    	
    }
    
	//----------------------------------------------------------------
    
}
