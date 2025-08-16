package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.AppuntamentiPTPanel;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.IscrizioneCorsiPanel;

public class ManipolazioneFrame extends JFrame implements IManipolazioneFrame{

	private static final long serialVersionUID = 1L;
	
	private JTabbedPane tabbedPane;
	
	private IscrizioneCorsiPanel corsiPanel;
	private AppuntamentiPTPanel appuntamentiPanel;
	
	//----------------------------------------------------------------
	
	public ManipolazioneFrame() {
		
		super("Gestione Modifiche Calendario");
		
		tabbedPane = new JTabbedPane();
		
		corsiPanel = new IscrizioneCorsiPanel();
		appuntamentiPanel = new AppuntamentiPTPanel();
		
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
