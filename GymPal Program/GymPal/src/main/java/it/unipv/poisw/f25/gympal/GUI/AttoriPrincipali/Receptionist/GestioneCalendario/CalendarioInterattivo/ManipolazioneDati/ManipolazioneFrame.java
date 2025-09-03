package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import it.unipv.poisw.f25.gympal.ApplicationLayer.UtilityServices.GestioneFont.IFontChangeRegister;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.AppuntamentiPTPanel;
import it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.IscrizioneCorsiPanel;
import it.unipv.poisw.f25.gympal.GUI.Utilities.DynamicButtons.IDynamicButtonSizeSetter;

public class ManipolazioneFrame extends JFrame implements IManipolazioneFrame{

	private static final long serialVersionUID = 1L;
	
	/*Pannelli*/
	private JTabbedPane tabbedPane;
	
	private IscrizioneCorsiPanel corsiPanel;
	private AppuntamentiPTPanel appuntamentiPanel;
	
	//----------------------------------------------------------------
	
	public ManipolazioneFrame(IDynamicButtonSizeSetter setter,
							  IFontChangeRegister changeRegister) {
		
		super("Gestione Modifiche Calendario");
		
		tabbedPane = new JTabbedPane();
		
		corsiPanel = new IscrizioneCorsiPanel(setter, changeRegister);
		appuntamentiPanel = new AppuntamentiPTPanel(setter, changeRegister);
		
	    tabbedPane.addTab("Corsi", corsiPanel);
	    tabbedPane.addTab("Appuntamenti", appuntamentiPanel);
		
		getContentPane().add(tabbedPane);
		setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

	}
	
	//----------------------------------------------------------------
	
	@Override
	public JPanel getCorsiPanel() {return corsiPanel;}
	
	//----------------------------------------------------------------
	
	@Override
	public JPanel getAppuntamentiPTPanel() {return appuntamentiPanel;}
	
	//----------------------------------------------------------------

}
