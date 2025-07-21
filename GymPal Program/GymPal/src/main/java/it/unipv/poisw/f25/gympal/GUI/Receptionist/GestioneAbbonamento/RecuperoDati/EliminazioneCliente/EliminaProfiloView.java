package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.EliminazioneCliente;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class EliminaProfiloView extends JPanel implements IEliminaProfiloView {

	private static final long serialVersionUID = 1L;
	
	private JLabel messaggio;
	private JLabel cf;
	
	private JButton conferma;
	private JButton annulla;
	private JButton indietro;
	
	private JSplitPane mainSplitPanel;
	
	private JPanel navigationPanel;
	
	private final IDynamicButtonSizeSetter buttonSizeSetter;
	
	//----------------------------------------------------------------
	
	public EliminaProfiloView(IDynamicButtonSizeSetter setter){
		
		buttonSizeSetter = setter;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(10, 5, 10, 5));
		
		cf = new JLabel("Codice fiscale cliente: ");
		
		messaggio = new JLabel("Procedere all'eliminazione del profilo cliente"
							   + " corrispondente al CF fornito dal DataBase?");
		
		/*############################################################*/
		
		cf.setAlignmentX(CENTER_ALIGNMENT);
		
		messaggio.setAlignmentX(CENTER_ALIGNMENT);
		
		/*############################################################*/
		
		JPanel upperPanel = new JPanel();
		
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
		
		conferma = new JButton ("Conferma");
		buttonSizeSetter.uniformButtonSize(conferma);

		upperPanel.add(Box.createVerticalGlue());
		
		JLabel titleLabel = new JLabel("-=Eliminazione profilo Cliente=-");
		
		titleLabel.setAlignmentX(CENTER_ALIGNMENT);
		conferma.setAlignmentX(CENTER_ALIGNMENT);
		
		upperPanel.add(titleLabel);
		upperPanel.add(Box.createVerticalStrut(30));
		
		upperPanel.add(cf);
		upperPanel.add(Box.createVerticalStrut(30));
		
		upperPanel.add(messaggio);
		upperPanel.add(Box.createVerticalStrut(30));
		
		upperPanel.add(conferma);
		upperPanel.add(Box.createVerticalStrut(30));
		
		upperPanel.add(Box.createVerticalGlue());
		
		/*############################################################*/
		
		navigationPanel = new JPanel();
		
		navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
		
		annulla = new JButton ("Annulla");
		indietro = new JButton ("Indietro");
		
		buttonSizeSetter.uniformButtonSize(indietro, annulla);
		
		navigationPanel.add(Box.createHorizontalGlue());
		navigationPanel.add(annulla);
		navigationPanel.add(Box.createHorizontalStrut(100));
		navigationPanel.add(indietro);
		navigationPanel.add(Box.createHorizontalGlue());
		
		/*############################################################*/
		
		mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		mainSplitPanel.setTopComponent(upperPanel);
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
	public JButton getConfermaButton() {
		
		return conferma;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JButton getAnnullaButton() {
		
		return annulla;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JButton getIndietroButton() {
		
		return indietro;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JLabel getCfLabel() {
		
		return cf;
		
	}	
	
	//----------------------------------------------------------------
	
	@Override
	public JPanel getMainPanel() {
		
		return this;
		
	}
	
	//----------------------------------------------------------------

}
