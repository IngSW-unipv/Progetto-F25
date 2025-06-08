package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.SubCustomView.ClientInfosView.ClientInfosViewHelpers.EtichettaPiuCampoFactory;

public class ClientInfosView extends JPanel implements IClientInfosView{

	private static final long serialVersionUID = 1L;
	
	//----------------------------------------------------------------
	
	private JTextField nome;
	private JTextField cognome;
	private JTextField codiceFiscale;
	private JTextField contatto;
	
	private JSpinner dateSpinner;
	
	private JRadioButton maschio;
	private JRadioButton femmina;
	
	private JRadioButton certIdoneitàSi;
	private JRadioButton certIdoneitàNo;
	
	private JRadioButton permessoGenitoriSi;
	private JRadioButton permessoGenitoriNo;
	
	private JButton avanti;
	private JButton indietro;
	private JButton annulla;
	private JButton acquisisciPermesso;
	
	private ButtonGroup sesso;
	private ButtonGroup idoneità;
	private ButtonGroup permessoGenitori;
	
	private JSplitPane mainSplitPanel;
	private JSplitPane informazioniUtenteSplitPanel;
	private JPanel avantiIndietroPanel;
	
	/*Questa etichetta non è locale, come le altre, perché deve sparire/apparire a seconda
	 *dell'età del cliente*/
	private JLabel permesso;
	
        
   //----------------------------------------------------------------
		
	public ClientInfosView () {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(10, 5, 10, 5));
	
		nome = new JTextField(80);
				
		cognome = new JTextField(80);
				
		codiceFiscale = new JTextField(80);
		
		contatto = new JTextField(80);
		
		
		/*############################################################*/
		
		//Questa istruzione crea un modello epr la data
		SpinnerDateModel model = new SpinnerDateModel();
		
		//Lo "Spinner" è creato usando il modello dell'istruzione precedente
	    dateSpinner = new JSpinner(model);
	    
	    //Tramite editor è impostato il formato di data che ci si aspetta sia usato
	    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
	    
	    	    
	    dateSpinner.setEditor(editor);
	    
	    /*############################################################*/	    
	    
	    /*Per avere una scelta esclusiva fra due(o più) "JRadioButton", essi devono essere
	     *posti in un gruppo*/
	    sesso = new ButtonGroup();
	    maschio = new JRadioButton ("Maschio");
	    femmina = new JRadioButton ("Femmina");
		sesso.add(maschio);
		sesso.add(femmina);
		maschio.setSelected(true); //Questa istruzione imposta 'maschio' come scelta pre-selezionata, di default
		
		idoneità = new ButtonGroup();
		certIdoneitàSi = new JRadioButton ("Si");
		certIdoneitàNo = new JRadioButton ("No");
		idoneità.add(certIdoneitàSi);
		idoneità.add(certIdoneitàNo);
		certIdoneitàSi.setSelected(true);
		
		permessoGenitori = new ButtonGroup();
		permessoGenitoriSi = new JRadioButton("Si");
		permessoGenitoriNo = new JRadioButton("No");
		permessoGenitori.add(permessoGenitoriSi);
		permessoGenitori.add(permessoGenitoriNo);
		permessoGenitoriSi.setSelected(true);
		
		acquisisciPermesso = new JButton("Avvia acquisizione documento");
		
		/*############################################################*/
		
		nome.setAlignmentX(CENTER_ALIGNMENT);
		cognome.setAlignmentX(CENTER_ALIGNMENT);
		codiceFiscale.setAlignmentX(CENTER_ALIGNMENT);
		dateSpinner.setAlignmentX(CENTER_ALIGNMENT);
		contatto.setAlignmentX(CENTER_ALIGNMENT);
		
		maschio.setAlignmentX(CENTER_ALIGNMENT);
		femmina.setAlignmentX(CENTER_ALIGNMENT);
		
		certIdoneitàSi.setAlignmentX(CENTER_ALIGNMENT);
		certIdoneitàNo.setAlignmentX(CENTER_ALIGNMENT);
		
		permessoGenitoriSi.setAlignmentX(CENTER_ALIGNMENT);
		permessoGenitoriNo.setAlignmentX(CENTER_ALIGNMENT);
		acquisisciPermesso.setAlignmentX(CENTER_ALIGNMENT);
		
		/*############################################################*/
		
		JPanel upperPanel = new JPanel();
		
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));

		upperPanel.add(Box.createVerticalGlue());
		
		JLabel titleLabel = new JLabel("-=Compila il Form con i dati del cliente=-");
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		upperPanel.add(titleLabel);
		upperPanel.add(Box.createVerticalStrut(30));
		
		upperPanel.add(EtichettaPiuCampoFactory.creaCampoEtichettato("Nome: ", nome));
		upperPanel.add(Box.createVerticalStrut(10));
		
		upperPanel.add(EtichettaPiuCampoFactory.creaCampoEtichettato("Cognome: ", cognome));
		upperPanel.add(Box.createVerticalStrut(10));
		
		upperPanel.add(EtichettaPiuCampoFactory.creaCampoEtichettato("Data di nascita (dd/MM/aa): ", dateSpinner));
		upperPanel.add(Box.createVerticalStrut(10));
		
		upperPanel.add(EtichettaPiuCampoFactory.creaCampoEtichettato("Codice Fiscale: ", codiceFiscale));
		upperPanel.add(Box.createVerticalStrut(10));
		
		upperPanel.add(EtichettaPiuCampoFactory.creaCampoEtichettato("Contatto cliente (email): ", contatto));
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
		
		/*############################################################*/
		
		JPanel bottomPanel = new JPanel();
		
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

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
		
		//bottomPanel.setVisible(true);
		
		/*############################################################*/
		
		informazioniUtenteSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		informazioniUtenteSplitPanel.setTopComponent(upperPanel);
		informazioniUtenteSplitPanel.setBottomComponent(bottomPanel);
		
		SwingUtilities.invokeLater(() -> {
			
			
			informazioniUtenteSplitPanel.setDividerLocation(0.75); 
		    
		});
		
		informazioniUtenteSplitPanel.setEnabled(false);
		
		/*############################################################*/
		
		avantiIndietroPanel = new JPanel();
		
		avantiIndietroPanel.setLayout(new BoxLayout(avantiIndietroPanel, BoxLayout.X_AXIS));

		avanti = new JButton ("Avanti");
		avanti.setMaximumSize(new Dimension(80, 40)); 
		
		indietro = new JButton ("Indietro");
		indietro.setMaximumSize(new Dimension(80, 40)); 
		
		annulla = new JButton ("Annulla");
		annulla.setMaximumSize(new Dimension(80, 40));
		
		avantiIndietroPanel.add(Box.createHorizontalGlue());
		avantiIndietroPanel.add(annulla);
		avantiIndietroPanel.add(Box.createHorizontalStrut(100));
		avantiIndietroPanel.add(indietro);
		avantiIndietroPanel.add(Box.createHorizontalStrut(100));
		avantiIndietroPanel.add(avanti);
		avantiIndietroPanel.add(Box.createHorizontalGlue());
		
		/*############################################################*/
		
		mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		mainSplitPanel.setTopComponent(informazioniUtenteSplitPanel);
		mainSplitPanel.setBottomComponent(avantiIndietroPanel);
		
		SwingUtilities.invokeLater(() -> {
			
			/*Alloca 80% dello splitPaneFather a splitPaneChild*/
			mainSplitPanel.setDividerLocation(0.8); 
		    
		});
		
		mainSplitPanel.setEnabled(false);
		
		/*############################################################*/
		
		add(mainSplitPanel);
		
	}
	
	//----------------------------------------------------------------
	
	public JTextField getNome() {
		return nome;
	}

	//----------------------------------------------------------------

	public JTextField getCognome() {
		return cognome;
	}


	//----------------------------------------------------------------

	public JTextField getCodiceFiscale() {
		return codiceFiscale;
	}

	//----------------------------------------------------------------
	
	public JTextField getContatto() {
		return contatto;
	}
	
	//----------------------------------------------------------------

	public JSpinner getDateSpinner() {
		return dateSpinner;
	}

	//----------------------------------------------------------------

	public JRadioButton getMaschio() {
		return maschio;
	}

	//----------------------------------------------------------------

	public JRadioButton getFemmina() {
		return femmina;
	}

	//----------------------------------------------------------------

	public JRadioButton getCertIdoneitàSi() {
		return certIdoneitàSi;
	}
	
	//----------------------------------------------------------------

	public JRadioButton getCertIdoneitàNo() {
		return certIdoneitàNo;
	}
	
	//----------------------------------------------------------------

	public JRadioButton getPermessoGenitoriSi() {
		return permessoGenitoriSi;
	}

	//----------------------------------------------------------------

	public JRadioButton getPermessoGenitoriNo() {
		return permessoGenitoriNo;
	}

	//----------------------------------------------------------------

	public JButton getAvantiButton() {
		return avanti;
	}

	//----------------------------------------------------------------

	public JButton getIndietroButton() {
		return indietro;
	}

	//----------------------------------------------------------------
	
	public JButton getAnnullaButton() {
		
		return annulla;
		
	}
	
	//----------------------------------------------------------------

	public JSplitPane getMainSplitPanel() {
		return mainSplitPanel;
	}
	
	//----------------------------------------------------------------

	public JSplitPane getInformazioniUtenteSplitPanel() {
		return informazioniUtenteSplitPanel;
	}

	//----------------------------------------------------------------

	public JPanel getAvantiIndietroPanel() {
		return avantiIndietroPanel;
	}

	//---------------------------------------------------------------- 
	
	public JLabel getPermessoGenitoriLabel () {
		return permesso;
	}
	
	//----------------------------------------------------------------
	
	public JButton getAcquisisciPermesso() {
		
		return acquisisciPermesso;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public Component getRootComponent() {
	    return this; // se la classe estende JPanel o JFrame
	}
	
	//----------------------------------------------------------------

}
