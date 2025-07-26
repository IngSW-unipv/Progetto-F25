package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneAbbonamento.RecuperoDati.ModificaAbbCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.RiepilogoEPagamento.AuxiliaryInterfaces.IDatiCliente;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class ModificaAbbonamentoView extends JPanel implements IModificaAbbonamentoView {

	private static final long serialVersionUID = 1L;
	
	private JButton annulla;
	private JButton avanti;
	private JButton indietro;
	
	private JButton conferma;
	private JButton resetta;
	
	private JToggleButton toggleBtn1;
	private JToggleButton toggleBtn2;
	private JToggleButton toggleBtn3;
	private JToggleButton toggleBtn4;

	private JCheckBox cB1;
	private JCheckBox cB2;
	private JCheckBox cB3;
	private JCheckBox cB4;
	private JCheckBox cB5;
	
	private JSplitPane mainSplitPanel;
	private JSplitPane displayAndModifyAbb;
	private JSplitPane subscriptionEditor;
	private JSplitPane navAndAppRevChanges;
	
	private JPanel componentiPanel;
	private JPanel corsiPanel;
	private JPanel listsPanel;
	private JPanel applyReverseChanges;
	private JPanel navigationPanel;
	
	private final IDynamicButtonSizeSetter buttonSizeSetter;
	
	//----------------------------------------------------------------
	
	public ModificaAbbonamentoView(IDynamicButtonSizeSetter setter) {
		
		buttonSizeSetter = setter;
		setLayout(new BorderLayout());
		
		/*############################################################*/
		
		componentiPanel = new JPanel();
		componentiPanel.setLayout(new BoxLayout(componentiPanel, BoxLayout.Y_AXIS));
		
		JLabel messageLabel = new JLabel("-=Componi l'abbonamento=-");
		messageLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		toggleBtn1 = new JToggleButton("Allenamento mirato con personal trainer");
		toggleBtn2 = new JToggleButton("Sala allenamento a corpo libero");
		toggleBtn3 = new JToggleButton("Sala pesi");
		toggleBtn4 = new JToggleButton("Sala corsi");
		
		componentiPanel.add(messageLabel);
		componentiPanel.add(Box.createVerticalStrut(30));
		componentiPanel.add(toggleBtn1);
		componentiPanel.add(Box.createVerticalStrut(10));
		componentiPanel.add(toggleBtn2);
		componentiPanel.add(Box.createVerticalStrut(10));
		componentiPanel.add(toggleBtn3);
		componentiPanel.add(Box.createVerticalStrut(10));
		componentiPanel.add(toggleBtn4);
		
		/*############################################################*/
		
		corsiPanel = new JPanel();
		corsiPanel.setLayout(new BoxLayout(corsiPanel, BoxLayout.Y_AXIS));

		cB1 = new JCheckBox("Crossfit");		
		cB2 = new JCheckBox("Yoga");
		cB3 = new JCheckBox("Pilates");
		cB4 = new JCheckBox("Zumba");
		cB5 = new JCheckBox("Fullbody");
		
		corsiPanel.add(cB1);
		corsiPanel.add(Box.createVerticalStrut(10));
		corsiPanel.add(cB2);
		corsiPanel.add(Box.createVerticalStrut(10));
		corsiPanel.add(cB3);
		corsiPanel.add(Box.createVerticalStrut(10));
		corsiPanel.add(cB4);
		corsiPanel.add(Box.createVerticalStrut(10));
		corsiPanel.add(cB5);
		
		corsiPanel.setVisible(false);
		
		/*############################################################*/
		
		List<AbstractButton> allButtons = new ArrayList<>();
		allButtons.addAll(getBottoniToggle());
		allButtons.addAll(getCheckBoxes());

		for (AbstractButton btn : allButtons) {
			btn.setBackground(Color.decode("#ffcccc"));
			btn.setOpaque(true);
			btn.setContentAreaFilled(true);
			btn.setBorderPainted(true);
			btn.setFocusPainted(true);
			btn.setAlignmentX(CENTER_ALIGNMENT);
			btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
			
		}
		
		/*############################################################*/
		
		subscriptionEditor = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		subscriptionEditor.setTopComponent(componentiPanel);
		subscriptionEditor.setBottomComponent(corsiPanel);
		
		subscriptionEditor.setEnabled(false);		
		
		/*############################################################*/

		listsPanel = new JPanel();
		listsPanel.setLayout(new BoxLayout(listsPanel, BoxLayout.Y_AXIS));
		
		/*############################################################*/
		
		displayAndModifyAbb = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		displayAndModifyAbb.setLeftComponent(listsPanel);
		displayAndModifyAbb.setRightComponent(subscriptionEditor);
		
		displayAndModifyAbb.setEnabled(false);
		
		/*############################################################*/
		
		navigationPanel = new JPanel();
		
		navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
		
		annulla = new JButton("Annulla");
		indietro = new JButton("Indietro");
		avanti = new JButton("Avanti");
		
		buttonSizeSetter.uniformButtonSize(annulla, indietro, avanti);
		
		navigationPanel.add(Box.createHorizontalGlue());
		navigationPanel.add(annulla);
		navigationPanel.add(Box.createHorizontalStrut(100));
		navigationPanel.add(indietro);
		navigationPanel.add(Box.createHorizontalStrut(100));
		navigationPanel.add(avanti);
		navigationPanel.add(Box.createHorizontalGlue());
		
		/*############################################################*/
		
		applyReverseChanges = new JPanel();
		
		applyReverseChanges.setLayout(new BoxLayout(applyReverseChanges, BoxLayout.X_AXIS));
		
		resetta = new JButton("Resetta composizione");
		conferma = new JButton("Conferma composzione");
		
		buttonSizeSetter.uniformButtonSize(resetta, conferma);
		
		applyReverseChanges.add(Box.createHorizontalGlue());
		applyReverseChanges.add(resetta);
		applyReverseChanges.add(Box.createHorizontalStrut(100));
		applyReverseChanges.add(conferma);
		applyReverseChanges.add(Box.createHorizontalGlue());
		
		/*############################################################*/
		
		navAndAppRevChanges = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		navAndAppRevChanges.setTopComponent(applyReverseChanges);
		navAndAppRevChanges.setBottomComponent(navigationPanel);
		
		navAndAppRevChanges.setEnabled(false);
		
		/*############################################################*/
		
		mainSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		mainSplitPanel.setTopComponent(displayAndModifyAbb);
		mainSplitPanel.setBottomComponent(navAndAppRevChanges);
		
		mainSplitPanel.setEnabled(false);
		
		/*############################################################*/
		
		add(mainSplitPanel);
		
		SwingUtilities.invokeLater(() -> {
			
			
			mainSplitPanel.setDividerLocation(0.80); 
		    
		});
		
		mainSplitPanel.setEnabled(false);
		
	}
	
	//----------------------------------------------------------------
	
	private JScrollPane listaAScorrimento(List<String> selezione) {

		
		JList<String> lista = new JList<>(selezione != null ? selezione.toArray(new String[0]) : new String[0]);
		lista.setEnabled(false); 
			
		return new JScrollPane(lista);
			
	}
	
	//----------------------------------------------------------------
	
	private void labeledList (JPanel panel, String label, List<String> campoDTO ) {
		
		panel.add(new JLabel (label));
		panel.add(Box.createVerticalStrut(30));
		panel.add(listaAScorrimento(campoDTO));
		panel.add(Box.createVerticalStrut(60));
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public List<JToggleButton> getBottoniToggle(){
		
		List<JToggleButton> toggleBtnsList = new ArrayList<>();
		
		toggleBtnsList.add(toggleBtn1);
		toggleBtnsList.add(toggleBtn2);
		toggleBtnsList.add(toggleBtn3);
		toggleBtnsList.add(toggleBtn4);
		
		return toggleBtnsList;
	}
	
	//----------------------------------------------------------------

	@Override
	public List<JCheckBox> getCheckBoxes(){
		
		List<JCheckBox> checkBoxList = new ArrayList<>();
		
		checkBoxList.add(cB1);
		checkBoxList.add(cB2);
		checkBoxList.add(cB3);
		checkBoxList.add(cB4);
		checkBoxList.add(cB5);
		return checkBoxList;
		
	}

	//----------------------------------------------------------------
	
	@Override
	public void setLists(IDatiCliente abbDTO) {
		
		listsPanel.removeAll();
		
		labeledList(listsPanel, "Componenti abbonamento selezionate: ", 
				    abbDTO.getSezioniAbbonamento());
		
		labeledList(listsPanel, "Corsi selezionati: ", 
			    	abbDTO.getCorsiSelezionati());
		
		revalidate();
        repaint();
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JPanel getCorsiPanel() {
		
		return corsiPanel;
		
	}

	//----------------------------------------------------------------
	
	@Override
	public JPanel getMainPanel() {
		
	    return this;
	    
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JSplitPane getSubEditorSplitPane() {
		
		return subscriptionEditor;
		
	}
	
	//----------------------------------------------------------------
	
    @Override
    public void addAnnullaListener(ActionListener listener) {
    	
        annulla.addActionListener(listener);
        
    }
	
	//----------------------------------------------------------------
	
    @Override
    public void addIndietroListener(ActionListener listener) {
    	
        indietro.addActionListener(listener);
        
    }
	
	//----------------------------------------------------------------
	
	@Override
	public void addAvantiListener(ActionListener listener) {
		
		avanti.addActionListener(listener);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public void addResettaListener(ActionListener listener) {
		
		resetta.addActionListener(listener);
		
	}
	
	//----------------------------------------------------------------
	
    @Override
    public void addConfermaListener(ActionListener listener) {
    	
        conferma.addActionListener(listener);
        
    }
	
	//----------------------------------------------------------------	


}
