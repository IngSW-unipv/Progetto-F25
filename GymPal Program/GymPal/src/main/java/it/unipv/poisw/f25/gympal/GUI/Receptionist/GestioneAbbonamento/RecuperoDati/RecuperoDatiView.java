package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampoFactory;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class RecuperoDatiView extends JPanel implements IRecuperoDatiView{

	private static final long serialVersionUID = 1L;
	
	private JTextField codiceFiscale;
	
	private JButton annulla;
	private JButton rinnova;
	private JButton modifica;
	private JButton elimina;
	
	private JButton estrai;
	
	private JLabel cf;
	private JLabel nome;
	private JLabel cognome;
	private JLabel contatto;
	private JLabel sesso;
	private JLabel durataAbbonamento;
	private JLabel inizioAbb;
	private JLabel fineAbb;
	
	private JSplitPane mainSplitPanel;
	private JSplitPane userInfoSplitPanel;
	
	private JPanel navigationPanel;
	
	private final IDynamicButtonSizeSetter buttonSizeSetter;
	
	//----------------------------------------------------------------
	
	public RecuperoDatiView (IDynamicButtonSizeSetter setter) {
		
		buttonSizeSetter = setter;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(10, 5, 10, 5));
		
		cf = new JLabel("Codice fiscale cliente: ");
		
		nome = new JLabel("Nome cliente: ");
		
		cognome = new JLabel("Cognome cliente: ");
		
		contatto = new JLabel("Contatto cliente: ");
		
		sesso = new JLabel("Sesso cliente: ");
		
		durataAbbonamento = new JLabel("Durata abbonamento: ");
		
		inizioAbb = new JLabel("Data inizio abbonamento: ");
		
		fineAbb = new JLabel("Data fine abbonamento:");
				
		codiceFiscale = new JTextField(80);
		
		/*############################################################*/
		
		cf.setAlignmentX(CENTER_ALIGNMENT);
		
		nome.setAlignmentX(CENTER_ALIGNMENT);
		
		cognome.setAlignmentX(CENTER_ALIGNMENT);
		
		codiceFiscale.setAlignmentX(CENTER_ALIGNMENT);
		
		contatto.setAlignmentX(CENTER_ALIGNMENT);
		
		sesso.setAlignmentX(CENTER_ALIGNMENT);
		
		durataAbbonamento.setAlignmentX(CENTER_ALIGNMENT);
		
		inizioAbb.setAlignmentX(CENTER_ALIGNMENT);
		
		fineAbb.setAlignmentX(CENTER_ALIGNMENT);
		
		/*############################################################*/
		
		JPanel upperPanel = new JPanel();
		
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
		
		estrai = new JButton ("Estrai Dati");
		buttonSizeSetter.uniformButtonSize(estrai);

		upperPanel.add(Box.createVerticalGlue());
		
		JLabel titleLabel = new JLabel("-=Inserisci codice fiscale del cliente=-");
		
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		estrai.setAlignmentX(CENTER_ALIGNMENT);
		
		upperPanel.add(titleLabel);
		upperPanel.add(Box.createVerticalStrut(30));
		
		upperPanel.add(EtichettaPiuCampoFactory.creaCampoEtichettato("CF: ", codiceFiscale));
		upperPanel.add(Box.createVerticalStrut(10));
		
		upperPanel.add(estrai);
		upperPanel.add(Box.createVerticalStrut(30));
		
		upperPanel.add(Box.createVerticalGlue());
		
		/*############################################################*/
		
		JPanel bottomPanel = new JPanel();
		
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

		bottomPanel.add(Box.createVerticalGlue());
		
		bottomPanel.add(cf);
		bottomPanel.add(Box.createVerticalStrut(10));
		
		bottomPanel.add(nome);
		bottomPanel.add(Box.createVerticalStrut(10));
		
		bottomPanel.add(cognome);
		bottomPanel.add(Box.createVerticalStrut(10));
		
		bottomPanel.add(contatto);
		bottomPanel.add(Box.createVerticalStrut(10));
		
		bottomPanel.add(sesso);
		bottomPanel.add(Box.createVerticalStrut(10));
		
		bottomPanel.add(durataAbbonamento);
		bottomPanel.add(Box.createVerticalStrut(10));
		
		bottomPanel.add(inizioAbb);
		bottomPanel.add(Box.createVerticalStrut(10));
		
		bottomPanel.add(fineAbb);
		bottomPanel.add(Box.createVerticalStrut(10));
		
		bottomPanel.add(Box.createVerticalGlue());
		
		/*############################################################*/
		
		userInfoSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		userInfoSplitPanel.setTopComponent(upperPanel);
		userInfoSplitPanel.setBottomComponent(bottomPanel);
		
		SwingUtilities.invokeLater(() -> {
			
			
			userInfoSplitPanel.setDividerLocation(0.80); 
		    
		});
		
		userInfoSplitPanel.setEnabled(false);
		
		/*############################################################*/
		
		navigationPanel = new JPanel();
		
		navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
		
		rinnova = new JButton ("Rinnova");
		modifica = new JButton ("Modifica");
		elimina = new JButton ("Elimina");
		annulla = new JButton ("Annulla");
		
		buttonSizeSetter.uniformButtonSize(rinnova, modifica, elimina, annulla);
		
		navigationPanel.add(Box.createHorizontalGlue());
		navigationPanel.add(annulla);
		navigationPanel.add(Box.createHorizontalStrut(100));
		navigationPanel.add(rinnova);
		navigationPanel.add(Box.createHorizontalStrut(100));
		navigationPanel.add(modifica);
		navigationPanel.add(Box.createHorizontalStrut(100));
		navigationPanel.add(elimina);
		navigationPanel.add(Box.createHorizontalGlue());
		
		/*############################################################*/
		
		mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		mainSplitPanel.setTopComponent(userInfoSplitPanel);
		mainSplitPanel.setBottomComponent(navigationPanel);
		
		SwingUtilities.invokeLater(() -> {
			
			/*Alloca 80% dello splitPaneFather a splitPaneChild*/
			mainSplitPanel.setDividerLocation(0.80); 
		    
		});
		
		mainSplitPanel.setEnabled(false);
		
		/*############################################################*/
		
		add(mainSplitPanel);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JTextField getCodiceFiscale() {
		
		return codiceFiscale;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JLabel getCfLabel() {
		
		return cf;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JLabel getNomeLabel() {
		
		return nome;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JLabel getCognomeLabel() {
		
		return cognome;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JLabel getContattoLabel() {
		
		return contatto;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JLabel getSessoLabel() {
		
		return sesso;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JLabel getDurataAbbonamentoLabel() {
		
		return durataAbbonamento;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JLabel getInizioAbbLabel() {
		
		return inizioAbb;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JLabel getFineAbbLabel() {
		
		return fineAbb;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JButton getEstraiButton() {
		
		return estrai;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JButton getAnnullaButton() {
		
		return annulla;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JButton getRinnovaButton() {
		
		return rinnova;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JButton getModificaButton() {
		
		return modifica;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JButton getEliminaButton() {
		
		return elimina;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JPanel getMainPanel() {
		
		return this;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void setRinnovaEnabled(boolean enabled) {
		
		rinnova.setEnabled(enabled);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void setModificaEnabled(boolean enabled) {
		
		modifica.setEnabled(enabled);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void setEliminaEnabled(boolean enabled) {
		
		elimina.setEnabled(enabled);
		
	}
	
	//----------------------------------------------------------------

}
