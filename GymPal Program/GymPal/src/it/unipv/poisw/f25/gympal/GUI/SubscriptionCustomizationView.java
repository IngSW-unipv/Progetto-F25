package it.unipv.poisw.f25.gympal.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;


public class SubscriptionCustomizationView extends JPanel {

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
	private JSplitPane splitPane; 

	//----------------------------------------------------------------

	public SubscriptionCustomizationView () {

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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
		

		corsiPanel = new JPanel();
		corsiPanel.setLayout(new BoxLayout(corsiPanel, BoxLayout.Y_AXIS));
		
		corsiPanel.add(cB1);
		corsiPanel.add(cB2);
		corsiPanel.add(cB3);
		corsiPanel.add(cB4);
		corsiPanel.add(cB5);
		
		corsiPanel.setVisible(false);
		

		List<AbstractButton> allButtons = new ArrayList<>();
		allButtons.addAll(getBottoniToggle());
		allButtons.addAll(getCheckBoxes());

		for (AbstractButton btn : allButtons) {
			btn.setBackground(Color.RED);
			btn.setOpaque(true);
			btn.setContentAreaFilled(true);
			btn.setBorderPainted(true);
			btn.setFocusPainted(true);
			btn.setAlignmentX(CENTER_ALIGNMENT);
			btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
			add(Box.createVerticalStrut(10));
		}

		// MODIFICATO: splitPane ora è un campo, non più locale
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


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

		splitPane.setTopComponent(upperPanel);
		splitPane.setBottomComponent(corsiPanel);

		add(splitPane);
		add(Box.createVerticalGlue());
	}

	//----------------------------------------------------------------

	public List<JToggleButton> getBottoniToggle(){
		
		List<JToggleButton> toggleBtnsList = new ArrayList<>();
		
		toggleBtnsList.add(toggleBtn1);
		toggleBtnsList.add(toggleBtn2);
		toggleBtnsList.add(toggleBtn3);
		toggleBtnsList.add(toggleBtn4);
		
		return toggleBtnsList;
	}
	
	//----------------------------------------------------------------

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
	
	public JPanel getCorsiPanel() {
		return corsiPanel;
	}

	//----------------------------------------------------------------
	
	public JSplitPane getSplitPane() {
		return splitPane;
	}
	
	//----------------------------------------------------------------
	
}
