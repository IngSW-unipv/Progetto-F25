package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView.ClientInfosView;


import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.IEtichettaPiuCampoFactory;

public class ClientInfosView extends JPanel implements IClientInfosView{

	private static final long serialVersionUID = 1L;
	
	//----------------------------------------------------------------
	
	/*Anagrafica*/
	private JTextField nome;
	private JTextField cognome;
	private JTextField codiceFiscale;
	private JTextField contatto;
	
	private JSpinner dateSpinner;
	
	private JRadioButton maschio;
	private JRadioButton femmina;
	
	/*Certificato di Idoneità*/
	private JRadioButton certIdoneitàSi;
	private JRadioButton certIdoneitàNo;
	
	/*Permesso Genitoriale*/
	private JRadioButton permessoGenitoriSi;
	private JRadioButton permessoGenitoriNo;
	
	/*Bottoni*/
	private JButton avanti;
	private JButton indietro;
	private JButton annulla;
	private JButton acquisisciPermesso;
	
	/*Gruppi bottoni - mutua esclusività*/
	private ButtonGroup sesso;
	private ButtonGroup idoneità;
	private ButtonGroup permessoGenitori;
	
	/*Pannelli & SplitPane*/
	private JSplitPane mainSplitPanel;
	private JSplitPane userInfoSplitPanel;
	private JPanel navigationPanel;
	
	/*Questa etichetta non è locale, come le altre, perché deve sparire/apparire a seconda
	 *dell'età del cliente*/
	private JLabel permesso;
	
	/*Servizi*/
	private IDynamicButtonSizeSetter buttonSizeSetter;
	private IEtichettaPiuCampoFactory campoEtichettato;
	
        
   //----------------------------------------------------------------
		
	public ClientInfosView (IDynamicButtonSizeSetter setter,
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
        JScrollPane upperScrollPane = buildUpperScrollPanel();
        JPanel bottomPanel = buildBottomPanel();
        this.userInfoSplitPanel = buildUserInfoSplitPane(upperScrollPane, bottomPanel);
        this.navigationPanel = buildNavigationPanel();
        this.mainSplitPanel = buildMainSplitPane(userInfoSplitPanel, navigationPanel);

        add(mainSplitPanel);
        
        /*Sottoscrizione a meccanismo cambio-font*/
        fontChangeRegister.register(this, buttonSizeSetter);
        
    }

    //--------------------------------------------------------------
	
    // Inizializzazione componenti base
    private void initFields() {
        nome = new JTextField(80);
        cognome = new JTextField(80);
        codiceFiscale = new JTextField(80);
        contatto = new JTextField(80);

        SpinnerDateModel model = new SpinnerDateModel();
        dateSpinner = new JSpinner(model);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));

        maschio = new JRadioButton("Maschio");
        femmina = new JRadioButton("Femmina");
        sesso = new ButtonGroup();
        sesso.add(maschio);
        sesso.add(femmina);
        maschio.setSelected(true);

        certIdoneitàSi = new JRadioButton("Si");
        certIdoneitàNo = new JRadioButton("No");
        idoneità = new ButtonGroup();
        idoneità.add(certIdoneitàSi);
        idoneità.add(certIdoneitàNo);
        certIdoneitàSi.setSelected(true);

        permessoGenitoriSi = new JRadioButton("Si");
        permessoGenitoriNo = new JRadioButton("No");
        permessoGenitori = new ButtonGroup();
        permessoGenitori.add(permessoGenitoriSi);
        permessoGenitori.add(permessoGenitoriNo);
        permessoGenitoriSi.setSelected(true);

        acquisisciPermesso = new JButton("Avvia acquisizione documento");

        maschio.setAlignmentX(CENTER_ALIGNMENT);
        femmina.setAlignmentX(CENTER_ALIGNMENT);
        certIdoneitàSi.setAlignmentX(CENTER_ALIGNMENT);
        certIdoneitàNo.setAlignmentX(CENTER_ALIGNMENT);
        permessoGenitoriSi.setAlignmentX(CENTER_ALIGNMENT);
        permessoGenitoriNo.setAlignmentX(CENTER_ALIGNMENT);
        acquisisciPermesso.setAlignmentX(CENTER_ALIGNMENT);
        
    }

    //--------------------------------------------------------------
    
    // Parte superiore: Dati anagrafici + idoneità
    private JScrollPane buildUpperScrollPanel() {
    	
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
        upperPanel.setBorder(BorderFactory.createTitledBorder("Dati Anagrafici & idoneità:"));

        upperPanel.add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel("-=Compila il Form con i dati del cliente=-");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        upperPanel.add(titleLabel);
        upperPanel.add(Box.createVerticalStrut(30));

        upperPanel.add(campoEtichettato.creaCampoEtichettato("Nome: ", nome));
        upperPanel.add(Box.createVerticalStrut(10));
        upperPanel.add(campoEtichettato.creaCampoEtichettato("Cognome: ", cognome));
        upperPanel.add(Box.createVerticalStrut(10));
        upperPanel.add(campoEtichettato.creaCampoEtichettato("Data di nascita (dd/MM/aa): ", dateSpinner));
        upperPanel.add(Box.createVerticalStrut(10));
        upperPanel.add(campoEtichettato.creaCampoEtichettato("Codice Fiscale: ", codiceFiscale));
        upperPanel.add(Box.createVerticalStrut(10));
        upperPanel.add(campoEtichettato.creaCampoEtichettato("Contatto cliente (email): ", contatto));
        upperPanel.add(Box.createVerticalStrut(10));

        JLabel sessoLabel = new JLabel("Seleziona il sesso del cliente:");
        sessoLabel.setAlignmentX(CENTER_ALIGNMENT);
        upperPanel.add(sessoLabel);
        upperPanel.add(Box.createVerticalStrut(10));
        upperPanel.add(maschio);
        upperPanel.add(Box.createVerticalStrut(5));
        upperPanel.add(femmina);
        upperPanel.add(Box.createVerticalStrut(10));

        JLabel certLabel = new JLabel("Certificato di Idoneità ricevuto?");
        certLabel.setAlignmentX(CENTER_ALIGNMENT);
        upperPanel.add(certLabel);
        upperPanel.add(Box.createVerticalStrut(10));
        upperPanel.add(certIdoneitàSi);
        upperPanel.add(Box.createVerticalStrut(5));
        upperPanel.add(certIdoneitàNo);
        upperPanel.add(Box.createVerticalStrut(10));
        upperPanel.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(upperPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
        
    }

    //--------------------------------------------------------------
    
    // Parte inferiore: Permesso genitoriale
    private JPanel buildBottomPanel() {
    	
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Autorizzazione genitoriale:"));

        bottomPanel.add(Box.createVerticalGlue());

        permesso = new JLabel("Autorizzazione fornita da adulto responsabile?");
        permesso.setAlignmentX(CENTER_ALIGNMENT);
        bottomPanel.add(permesso);
        bottomPanel.add(Box.createVerticalStrut(10));

        bottomPanel.add(permessoGenitoriSi);
        bottomPanel.add(Box.createVerticalStrut(5));
        bottomPanel.add(permessoGenitoriNo);
        bottomPanel.add(Box.createVerticalStrut(10));

        bottomPanel.add(acquisisciPermesso);
        bottomPanel.add(Box.createVerticalStrut(10));

        bottomPanel.add(Box.createVerticalGlue());

        return bottomPanel;
        
    }

    //--------------------------------------------------------------
    
    // SplitPanel con top e bottom panel
    private JSplitPane buildUserInfoSplitPane(JScrollPane upper, JPanel bottom) {
    	
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setTopComponent(upper);
        split.setBottomComponent(bottom);
        SwingUtilities.invokeLater(() -> split.setDividerLocation(0.75));
        split.setEnabled(false);
        return split;
        
    }

    //--------------------------------------------------------------
    
    // Navigazione (avanti, indietro, annulla)
    private JPanel buildNavigationPanel() {
    	
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(""));

        avanti = new JButton("Avanti");
        indietro = new JButton("Indietro");
        annulla = new JButton("Annulla");

        buttonSizeSetter.uniformButtonSize(avanti, indietro, annulla);

        panel.add(Box.createHorizontalGlue());
        panel.add(annulla);
        panel.add(Box.createHorizontalStrut(100));
        panel.add(indietro);
        panel.add(Box.createHorizontalStrut(100));
        panel.add(avanti);
        panel.add(Box.createHorizontalGlue());

        return panel;
        
    }

    //--------------------------------------------------------------
    
    // Split principale: contiene tutto
    private JSplitPane buildMainSplitPane(JSplitPane upper, JPanel navPanel) {
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setTopComponent(upper);
        split.setBottomComponent(navPanel);
        SwingUtilities.invokeLater(() -> split.setDividerLocation(0.8));
        split.setEnabled(false);
        return split;
    }

    //--------------------------------------------------------------

    @Override public JTextField getNome() { return nome; }
    
    //----------------------------------------------------------------
    
    @Override public JTextField getCognome() { return cognome; }
    
    //----------------------------------------------------------------
    
    @Override public JTextField getCodiceFiscale() { return codiceFiscale; }
    
    //----------------------------------------------------------------
    
    @Override public JTextField getContatto() { return contatto; }
    
    //----------------------------------------------------------------
    
    @Override public JSpinner getDateSpinner() { return dateSpinner; }
    
    //----------------------------------------------------------------
    
    @Override public JRadioButton getMaschio() { return maschio; }
    
    //----------------------------------------------------------------
    
    @Override public JRadioButton getFemmina() { return femmina; }
    
    //----------------------------------------------------------------
    
    @Override public JRadioButton getCertIdoneitàSi() { return certIdoneitàSi; }
    @Override public JRadioButton getCertIdoneitàNo() { return certIdoneitàNo; }
    
    //----------------------------------------------------------------
    
    @Override public JRadioButton getPermessoGenitoriSi() { return permessoGenitoriSi; }
    @Override public JRadioButton getPermessoGenitoriNo() { return permessoGenitoriNo; }
    
    //----------------------------------------------------------------
     
    @Override public JLabel getPermessoGenitoriLabel() { return permesso; }
    
    //----------------------------------------------------------------
    
    @Override public void addDateSpinnerListener(ChangeListener listener) { 
    	
    	dateSpinner.addChangeListener(listener); 
    	
    }
    
    //----------------------------------------------------------------
    
    @Override public void addAvantiListener(ActionListener listener) {
    	
    	avanti.addActionListener(listener); 
    	
    }
    
    //----------------------------------------------------------------
    
    @Override public void addIndietroListener(ActionListener listener) { 
    	
    	indietro.addActionListener(listener); 
    	
    }
    
    //----------------------------------------------------------------
    
    @Override public void addAnnullaListener(ActionListener listener) { 
    	
    	annulla.addActionListener(listener); 
        
    }
    
    //----------------------------------------------------------------
    
    @Override public void addAcquisisciPermessoListener(ActionListener listener) {
    	
    	acquisisciPermesso.addActionListener(listener); 
    	
    }
    
    //----------------------------------------------------------------
    
    @Override public JSplitPane getMainSplitPanel() { return mainSplitPanel; }
    
    //----------------------------------------------------------------
    
    @Override public JSplitPane getInformazioniUtenteSplitPanel() { 
    	
    	return userInfoSplitPanel; 
    	
    }
    
    //----------------------------------------------------------------
    
    @Override public JPanel getNavigationPanel() { return navigationPanel; }
    
    //----------------------------------------------------------------
    
    @Override public JPanel getMainPanel() { return this; }
    
    //----------------------------------------------------------------

    @Override
    public void setBtnsVisibility(boolean flag) {
    	
        permessoGenitoriSi.setVisible(flag);
        permessoGenitoriNo.setVisible(flag);
        permesso.setVisible(flag);
        acquisisciPermesso.setVisible(flag);
        
    }
    
    //----------------------------------------------------------------
    
}
