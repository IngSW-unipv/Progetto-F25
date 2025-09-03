package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.CustomTableModelsForPanels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class SessioneCorsoTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;

    private final List<SessioneCorso> sessioni;
    
    private final String[] columnNames = {
    		
        "ID Sessione", "Staff ID", "Data", "Fascia Oraria", "Iscritti"
        
    };
    
    //----------------------------------------------------------------

    public SessioneCorsoTableModel(List<SessioneCorso> sessioni) {
    	
        this.sessioni = sessioni;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public int getRowCount() {
    	
        return sessioni.size();
        
    }
    
    //----------------------------------------------------------------

    @Override
    public int getColumnCount() {
    	
        return columnNames.length;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public String getColumnName(int col) {
    	
        return columnNames[col];
        
    }
    
    //----------------------------------------------------------------

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	
        SessioneCorso s = sessioni.get(rowIndex);

        switch (columnIndex) {
            case 0: return s.getSessioneId();
            case 1: return s.getStaffId();
            case 2: return s.getData();
            case 3: return s.getFasciaOraria();
            case 4: return s.getNumIscritti();
            default: return null;
        }
        
    }
    
    //----------------------------------------------------------------

    public SessioneCorso getSessioneAt(int row) {
    	
        return sessioni.get(row);
        
    }
    
    //----------------------------------------------------------------

}
