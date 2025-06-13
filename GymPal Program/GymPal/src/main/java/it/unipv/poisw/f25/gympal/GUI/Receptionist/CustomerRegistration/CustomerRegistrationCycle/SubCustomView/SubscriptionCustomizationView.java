package it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.CustomerRegistrationCycle.SubCustomView;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class SubscriptionCustomizationView extends JPanel implements ISubscriptionCustomizationView {

	private static final long serialVersionUID = 1L;

	private JToggleButton toggleBtn1;
	private JToggleButton toggleBtn2;
	private JToggleButton toggleBtn3;
	private JToggleButton toggleBtn4;

	private JCheckBox cB1;
	private JCheckBox cB2;
	private JCheckBox cB3;
	private JCheckBox cB4;
	private JCheckBox cB5;

	private JPanel corsiPanel;
	private JSplitPane splitPaneChild;
	private JSplitPane splitPaneFather;
	
	private JButton avanti;
	private JButton annulla;

	//----------------------------------------------------------------

	public SubscriptionCustomizationView () {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(10, 5, 10, 5));

		JLabel messageLabel = new JLabel("-=Componi l'abbonamento=-");
		messageLabel.setAlignmentX(CENTER_ALIGNMENT);

		toggleBtn1 = new JToggleButton("Allenamento mirato con personal trainer");
		toggleBtn2 = new JToggleButton("Sala allenamento a corpo libero");
		toggleBtn3 = new JToggleButton("Sala pesi");
		toggleBtn4 = new JToggleButton("Sala corsi");

		cB1 = new JCheckBox("Crossfit");		
		cB2 = new JCheckBox("Yoga");
		cB3 = new JCheckBox("Pilates");
		cB4 = new JCheckBox("Zumba");
		cB5 = new JCheckBox("Fullbody");
		
		avanti = new JButton("Avanti");
		annulla = new JButton("Annulla");

		corsiPanel = new JPanel();
		corsiPanel.setLayout(new BoxLayout(corsiPanel, BoxLayout.Y_AXIS));
		
		corsiPanel.add(cB1);
		corsiPanel.add(cB2);
		corsiPanel.add(cB3);
		corsiPanel.add(cB4);
		corsiPanel.add(cB5);
		
		corsiPanel.setVisible(false);
		
		/*############################################################*/
		
		/*Creo una lista di bottoni basata sul super-tipo, così posso scrivere un solo ciclo
		 *for per assegnare a tutti le proprietà comuni*/
		
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
			add(Box.createVerticalStrut(10));
		}

		/*############################################################*/
		
		splitPaneChild = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
		upperPanel.add(messageLabel);
		upperPanel.add(Box.createVerticalStrut(30));
		upperPanel.add(toggleBtn1);
		upperPanel.add(Box.createVerticalStrut(10));
		upperPanel.add(toggleBtn2);
		upperPanel.add(Box.createVerticalStrut(10));
		upperPanel.add(toggleBtn3);
		upperPanel.add(Box.createVerticalStrut(10));
		upperPanel.add(toggleBtn4);
		upperPanel.add(Box.createVerticalStrut(15));

		splitPaneChild.setTopComponent(upperPanel);
		splitPaneChild.setBottomComponent(corsiPanel);
		
		/*############################################################*/

		/*Non è possibile utilizzare "GridLayout()" - questo UImanager non rispetta il metodo
		 *"setMaximumSize()" - ecco allora che la scelta ricade su "BoxLayout()"*/
		
		JPanel bottomPanel = new JPanel();
		
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

		avanti.setMaximumSize(new Dimension(80, 40)); 
		annulla.setMaximumSize(new Dimension(80, 40));

		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(annulla);
		bottomPanel.add(Box.createHorizontalStrut(200));
		bottomPanel.add(avanti);
		bottomPanel.add(Box.createHorizontalGlue());
		
		/*############################################################*/
		
		splitPaneFather = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneChild, bottomPanel);
		
		
		/*E' necessario utilizzare "invokeLater": se si imposta "setDividerLocation()" prima che
		 *il layout del componente sia visibile a schermo, il valore in percentuale non viene 
		 *rispettato correttamente siccome la dimensione del contenitore padre non è ancora 
		 *definita.*/
		
		SwingUtilities.invokeLater(() -> {
			
			/*Alloca 80% dello splitPaneFather a splitPaneChild*/
		    splitPaneFather.setDividerLocation(0.8); 
		    
		});
		
		splitPaneFather.setEnabled(false);
		
		/*############################################################*/
		
		add(splitPaneFather);
		add(Box.createVerticalGlue());
		
		
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
	public JPanel getCorsiPanel() {
		
		return corsiPanel;
		
	}

	//----------------------------------------------------------------
	
	@Override
	public JSplitPane getSplitPaneChild() {
		
		return splitPaneChild;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JButton getAvantiButton() {
		
		return avanti;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JButton getAnnullaButton() {
		
		return annulla;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JPanel getMainPanel() {
	    return this;  // dato che la view estende JPanel
	}
	
	//----------------------------------------------------------------
	
}
