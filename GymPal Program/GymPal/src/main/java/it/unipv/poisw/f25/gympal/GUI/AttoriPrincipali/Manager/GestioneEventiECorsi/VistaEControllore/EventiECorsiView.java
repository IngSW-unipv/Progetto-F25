package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.VistaEControllore;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.PannelloCorsi;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.PannelloEventi;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class EventiECorsiView extends JPanel implements IEventiECorsiView{

	private static final long serialVersionUID = 1L;
	
	/*Pannelli & TabbedPane*/
	private JTabbedPane tabbedPane;
	
	private PannelloCorsi corsiPanel;
	private PannelloEventi eventiPanel;
	
	//----------------------------------------------------------------
	
	public EventiECorsiView(IDynamicButtonSizeSetter buttonSizeSetter,
							IFontChangeRegister changeRegister) {
		
		/*Layout Pannello principale*/
		setLayout(new BorderLayout());
		
		/*Costruzione contenuto pannello*/
		tabbedPane = new JTabbedPane();
		
		corsiPanel = new PannelloCorsi(buttonSizeSetter, changeRegister);
		eventiPanel = new PannelloEventi(buttonSizeSetter, changeRegister);
	
	    tabbedPane.addTab("Gestione Corsi", corsiPanel);
	    tabbedPane.addTab("Gestione Eventi", eventiPanel);
		
		add(tabbedPane, BorderLayout.CENTER);
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public JPanel getCorsiPanel() {return corsiPanel;}
	
	//----------------------------------------------------------------
	
	@Override
	public JPanel getEventiPanel() {return eventiPanel;}
	
	//----------------------------------------------------------------
	
	@Override
	public JPanel getMainPanel() {return this;}
	
	//----------------------------------------------------------------

}
