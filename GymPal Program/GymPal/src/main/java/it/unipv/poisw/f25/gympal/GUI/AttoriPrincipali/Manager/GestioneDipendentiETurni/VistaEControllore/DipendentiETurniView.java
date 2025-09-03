package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.PannelloDipendenti;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.PannelloTurni;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class DipendentiETurniView extends JPanel implements IDipendentiETurniView{

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	
	private PannelloTurni turniPanel;
	private PannelloDipendenti dipPanel;

	
    //----------------------------------------------------------------
	
	public DipendentiETurniView(IDynamicButtonSizeSetter buttonSizeSetter,
								IFontChangeRegister changeRegister) {
		
		setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane();
		
		turniPanel = new PannelloTurni(buttonSizeSetter, changeRegister);
		dipPanel = new PannelloDipendenti(buttonSizeSetter, changeRegister);
		
		tabbedPane.add("Pianificazione Turni", turniPanel);
		tabbedPane.add("Gestione Dipendenti", dipPanel);
		
		add(tabbedPane, BorderLayout.CENTER);
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public JPanel getMainPanel() {return this;}
	
    //----------------------------------------------------------------
	
	@Override
	public JPanel getTurniPanel() {return turniPanel;}
	
    //----------------------------------------------------------------
	
	@Override
	public JPanel getDipendentiPanel() {return dipPanel;}
	
    //----------------------------------------------------------------
	
}
