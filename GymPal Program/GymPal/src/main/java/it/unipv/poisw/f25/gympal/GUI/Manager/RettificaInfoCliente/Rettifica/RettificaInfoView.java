package it.unipv.poisw.f25.gympal.GUI.Manager.RettificaInfoCliente.Rettifica;

import java.awt.event.ActionListener;

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
import javax.swing.event.ChangeListener;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;
import it.unipv.poisw.f25.gympal.GUI.Utilities.EtichettaPiuCampo.IEtichettaPiuCampoFactory;

public class RettificaInfoView extends JPanel implements IRettificaInfoView{

	private static final long serialVersionUID = 1L;

	private JTextField codiceFiscale;
	
	private JTextField nome;
	private JTextField cognome;
	private JTextField cfAnagrafico;
	private JTextField contatto;
	
	private JSpinner dateSpinner;
	
	private JRadioButton maschio;
	private JRadioButton femmina;
	private ButtonGroup sesso;
	
	
	private JButton estrai;
	private JButton elimina;
	private JButton saveMods;
	private JButton insData;
	private JButton annulla;
	private JButton avanti;
	
	private JLabel cfErrato;
	private JLabel postElimina;
	
	private JPanel navigationPanel;
	
	private JSplitPane mainSplitPanel;
	private JSplitPane estraiAndEdit;
	private JSplitPane eliminaAndInfosView;
	private JSplitPane editAndInsertBtns;
	
	private final IDynamicButtonSizeSetter buttonSizeSetter;
	private final IEtichettaPiuCampoFactory campoEtichettato;
	
	//----------------------------------------------------------------
	
	public RettificaInfoView(IDynamicButtonSizeSetter setter,
							 IEtichettaPiuCampoFactory campoEtichettato) {
		
		buttonSizeSetter = setter;
		this.campoEtichettato = campoEtichettato;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(10, 5, 10, 5));
		
		/*PannelloDxUP(Anagrafica)####################################*/
		
		JPanel pannelloDxUP = new JPanel();
		
		pannelloDxUP.setLayout(new BoxLayout(pannelloDxUP, BoxLayout.Y_AXIS));

		pannelloDxUP.add(Box.createVerticalGlue());
		
		nome = new JTextField(80);
		nome.setAlignmentX(CENTER_ALIGNMENT);
		
		cognome = new JTextField(80);
		cognome.setAlignmentX(CENTER_ALIGNMENT);
		
		contatto = new JTextField(80);
		contatto.setAlignmentX(CENTER_ALIGNMENT);
		
		cfAnagrafico = new JTextField(80);
		cfAnagrafico.setAlignmentX(CENTER_ALIGNMENT);
		cfAnagrafico.setEditable(false);

		/*Data di nascita*/
		SpinnerDateModel model = new SpinnerDateModel();
	    dateSpinner = new JSpinner(model);
	    JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
	    dateSpinner.setEditor(editor);
	    dateSpinner.setAlignmentX(CENTER_ALIGNMENT);
	    
	    /*M / F*/
	    maschio = new JRadioButton ("Maschio");
	    femmina = new JRadioButton ("Femmina");
		maschio.setAlignmentX(CENTER_ALIGNMENT);
		femmina.setAlignmentX(CENTER_ALIGNMENT);
	    
	    sesso = new ButtonGroup();
		sesso.add(maschio);
		sesso.add(femmina);
		maschio.setSelected(true);

		
		JLabel panelTitle = new JLabel("-=Anagrafica Cliente=-");
		panelTitle.setAlignmentX(CENTER_ALIGNMENT);
		JLabel nota = new JLabel("Modificare 'cf' -SE, E SOLO SE- dati utente sono stati eliminati");
		nota.setAlignmentX(CENTER_ALIGNMENT);
		
		pannelloDxUP.add(Box.createVerticalGlue());
		pannelloDxUP.add(panelTitle);
		pannelloDxUP.add(Box.createVerticalStrut(30));
		
		pannelloDxUP.add(this.campoEtichettato.creaCampoEtichettato("Nome: ", nome));
		pannelloDxUP.add(Box.createVerticalStrut(10));
		
		pannelloDxUP.add(this.campoEtichettato.creaCampoEtichettato("Cognome: ", cognome));
		pannelloDxUP.add(Box.createVerticalStrut(20));
		
		pannelloDxUP.add(this.campoEtichettato.creaCampoEtichettato("Data di nascita (dd/MM/aa): ", dateSpinner));
		pannelloDxUP.add(Box.createVerticalStrut(20));
		
		pannelloDxUP.add(nota);
		pannelloDxUP.add(Box.createVerticalStrut(5));
		pannelloDxUP.add(this.campoEtichettato.creaCampoEtichettato("Codice Fiscale: ", cfAnagrafico));
		pannelloDxUP.add(Box.createVerticalStrut(10));
		
		pannelloDxUP.add(this.campoEtichettato.creaCampoEtichettato("Contatto cliente (email): ", contatto));
		pannelloDxUP.add(Box.createVerticalStrut(30));
		
		
		JLabel sessoLabel = new JLabel("Seleziona il sesso del cliente:");
		sessoLabel.setAlignmentX(CENTER_ALIGNMENT);
		pannelloDxUP.add(sessoLabel);
		pannelloDxUP.add(Box.createVerticalStrut(10));
		
		pannelloDxUP.add(maschio);
		pannelloDxUP.add(Box.createVerticalStrut(5));
		
		pannelloDxUP.add(femmina);
		pannelloDxUP.add(Box.createVerticalStrut(10));
		pannelloDxUP.add(Box.createVerticalGlue());
		
		/*pannelloDxDown(Salva Modifiche / Re-Insert)#################*/
		
		JPanel pannelloDxDown = new JPanel();
		JPanel pannelloDxDownLeft = new JPanel();
		JPanel pannelloDxDownRight = new JPanel();
		
		saveMods = new JButton("Salva Modifiche");
		saveMods.setAlignmentX(CENTER_ALIGNMENT);
		saveMods.setEnabled(false);
		
		insData = new JButton("Re-Ins Dati");
		insData.setAlignmentX(CENTER_ALIGNMENT);
		insData.setEnabled(false);
		
		buttonSizeSetter.uniformButtonSize(saveMods, insData);
		
		postElimina = new JLabel("Usare -DOPO- Elimina");
		postElimina.setAlignmentX(CENTER_ALIGNMENT);
		
		pannelloDxDownLeft.setLayout(new BoxLayout(pannelloDxDownLeft, BoxLayout.Y_AXIS));
		pannelloDxDownLeft.add(Box.createHorizontalGlue());
		pannelloDxDownLeft.add(Box.createVerticalStrut(35));
		pannelloDxDownLeft.add(saveMods);
		pannelloDxDownLeft.add(Box.createHorizontalGlue());
		
		pannelloDxDownRight.setLayout(new BoxLayout(pannelloDxDownRight, BoxLayout.Y_AXIS));
		pannelloDxDownRight.add(Box.createHorizontalGlue());
		pannelloDxDownRight.add(postElimina);
		pannelloDxDownRight.add(Box.createVerticalStrut(10));
		pannelloDxDownRight.add(insData);
		pannelloDxDownRight.add(Box.createHorizontalGlue());
		
		pannelloDxDown.setLayout(new BoxLayout(pannelloDxDown, BoxLayout.X_AXIS));
				
		pannelloDxDown.add(Box.createHorizontalGlue());
		pannelloDxDown.add(pannelloDxDownLeft);
		pannelloDxDown.add(Box.createHorizontalStrut(40));
		pannelloDxDown.add(pannelloDxDownRight);
		pannelloDxDown.add(Box.createHorizontalGlue());
		
		/*editAndInsertBtns(JSplitPane)###############################*/
		
		editAndInsertBtns = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		editAndInsertBtns.setTopComponent(pannelloDxUP);
		editAndInsertBtns.setBottomComponent(pannelloDxDown);
		
		SwingUtilities.invokeLater(() -> {

			editAndInsertBtns.setDividerLocation(0.9); 
		    
		});
		
		editAndInsertBtns.setEnabled(false);
		
		/*eliminaPanel################################################*/
		
		JPanel eliminaPanel = new JPanel();
		eliminaPanel.setLayout(new BoxLayout(eliminaPanel, BoxLayout.Y_AXIS));
		
		cfErrato = new JLabel("CF errato? Allora: ");
		cfErrato.setAlignmentX(CENTER_ALIGNMENT);

		elimina = new JButton("Elimina");
		elimina.setEnabled(false);
		elimina.setAlignmentX(CENTER_ALIGNMENT);
		buttonSizeSetter.uniformButtonSize(elimina);
		
		eliminaPanel.add(Box.createVerticalGlue());
		eliminaPanel.add(Box.createVerticalStrut(10));
		eliminaPanel.add(cfErrato);
		eliminaPanel.add(Box.createVerticalStrut(10));
		eliminaPanel.add(elimina);
		eliminaPanel.add(Box.createVerticalGlue());		
		
		/*eliminaAndInfosView(JSplitPane)#############################*/
		
		eliminaAndInfosView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		eliminaAndInfosView.setLeftComponent(eliminaPanel);
		eliminaAndInfosView.setRightComponent(editAndInsertBtns);
		
		SwingUtilities.invokeLater(() -> {

			eliminaAndInfosView.setDividerLocation(0.2); 
		    
		});
		
		eliminaAndInfosView.setEnabled(false);
		
		/*estraiPanel#################################################*/
		
		JPanel estraiPanel = new JPanel();
		
		estraiPanel.setLayout(new BoxLayout(estraiPanel, BoxLayout.Y_AXIS));
		
		codiceFiscale = new JTextField(80);
		estrai = new JButton ("Estrai Dati");
		buttonSizeSetter.uniformButtonSize(estrai);
		
		JLabel titleLabel = new JLabel("-=Inserisci codice fiscale del cliente=-");
		
		estraiPanel.add(Box.createVerticalGlue());
		
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		estrai.setAlignmentX(CENTER_ALIGNMENT);
		
		estraiPanel.add(titleLabel);
		estraiPanel.add(Box.createVerticalStrut(30));
		estraiPanel.add(this.campoEtichettato.creaCampoEtichettato("CF: ", codiceFiscale));
		estraiPanel.add(Box.createVerticalStrut(10));
		estraiPanel.add(estrai);
		estraiPanel.add(Box.createVerticalStrut(30));
		estraiPanel.add(Box.createVerticalGlue());
		
		/*estraiAndEdit(JSplitPane)###################################*/
		
		estraiAndEdit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		estraiAndEdit.setTopComponent(estraiPanel);
		estraiAndEdit.setBottomComponent(eliminaAndInfosView);
		
		SwingUtilities.invokeLater(() -> {
			
			
			estraiAndEdit.setDividerLocation(0.35); 
		    
		});
		
		estraiAndEdit.setEnabled(false);
		
		/*navigationPanel##############################################*/
		
		navigationPanel = new JPanel();
		
		navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
		

		annulla = new JButton ("Annulla");
		avanti = new JButton ("Avanti");
		
		buttonSizeSetter.uniformButtonSize(annulla, avanti);
		
		navigationPanel.add(Box.createHorizontalGlue());
		navigationPanel.add(annulla);
		navigationPanel.add(Box.createHorizontalStrut(100));
		navigationPanel.add(avanti);
		navigationPanel.add(Box.createHorizontalGlue());
		
		/*mainSplitPanel(JSplitPane)##################################*/
		
		mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		mainSplitPanel.setTopComponent(estraiAndEdit);
		mainSplitPanel.setBottomComponent(navigationPanel);
		
		SwingUtilities.invokeLater(() -> {
			
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
	public JTextField getNome() {
		
		return nome;
		
	}

	//----------------------------------------------------------------
	
	@Override
	public JTextField getCognome() {
		
		return cognome;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JTextField getCfAnagrafico() {
		
		return cfAnagrafico;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JTextField getContatto() {
		
		return contatto;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void addDateSpinnerListener(ChangeListener listener) {
		
		dateSpinner.addChangeListener(listener);
		
	}
	
	@Override
	public JSpinner getDateSpinner() {
		
		return dateSpinner;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JRadioButton getMaschio() {
		
		return maschio;
		
	}

	//----------------------------------------------------------------

	@Override
	public JRadioButton getFemmina() {
		
		return femmina;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void addEstraiListenr(ActionListener listener) {
		
		estrai.addActionListener(listener);
		
	}
	
	//----------------------------------------------------------------
	
    @Override
    public void addEliminaListener(ActionListener listener) {
    	
        elimina.addActionListener(listener);
        
    }
	
	//----------------------------------------------------------------
    
    @Override
    public void addSaveModsListener(ActionListener listener) {
    	
        saveMods.addActionListener(listener);
        
    }
	
	//----------------------------------------------------------------
    
    @Override
    public void addInsDataListener(ActionListener listener) {
    	
        insData.addActionListener(listener);
        
    }
	
	//----------------------------------------------------------------
    
    
	
    @Override
    public void addAnnullaListener(ActionListener listener) {
    	
        annulla.addActionListener(listener);
        
    }
	
	//----------------------------------------------------------------
    
	@Override
	public void addAvantiListener(ActionListener listener) {
		
		avanti.addActionListener(listener);
		
	}

	//----------------------------------------------------------------
	
	@Override
	public JPanel getMainPanel() {
		
	    return this; 
	    
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void setEliminaEnabled(boolean enabled) {
		
		elimina.setEnabled(enabled);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void setSaveModsEnabled(boolean enabled) {
		
		saveMods.setEnabled(enabled);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void setInsDataEnabled(boolean enabled) {
		
		insData.setEnabled(enabled);
		
	}
	
	//----------------------------------------------------------------
	
}
