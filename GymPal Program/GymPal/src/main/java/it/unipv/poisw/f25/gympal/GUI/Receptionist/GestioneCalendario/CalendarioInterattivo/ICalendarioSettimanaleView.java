package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JPanel;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.CalendarioUtilities.CalendarioCellPanel;

public interface ICalendarioSettimanaleView {
	
	public void popolaGriglia(LocalDate lunedi, ActionListener cellClickListener);
	
    //----------------------------------------------------------------
	
	public LocalDate[] getDateSettimana();
	
    //----------------------------------------------------------------
	
	public List<CalendarioCellPanel> getTutteLeCelle();
	
    //----------------------------------------------------------------
	
	public void addBtnPrecedenteListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public void addBtnSuccessivaListener(ActionListener listener);
	
	//----------------------------------------------------------------
	
	public LocalDate getLunediCorrente();
	
	//----------------------------------------------------------------
	
	public JPanel getMainPanel();
	
    //----------------------------------------------------------------

}
