package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ManipolazioneDati.PannelliPerTabs.CustomTableModelsForPanels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import it.unipv.poisw.f25.gympal.persistence.beans.AppuntamentoPTBean.AppuntamentoPT;

public class AppuntamentoPTTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;
	
	private final List<AppuntamentoPT> appuntamenti;
	
    private final String[] columns = {"CF Cliente", "Staff ID", 
    								  "Data", "Fascia Oraria"};
    
    //----------------------------------------------------------------

    public AppuntamentoPTTableModel(List<AppuntamentoPT> appuntamenti) {
    	
        this.appuntamenti = (appuntamenti != null) ? appuntamenti : new ArrayList<>();
        
    }
    
    //----------------------------------------------------------------

    @Override
    public int getRowCount() {
    	
        return appuntamenti.size();
        
    }
    
    //----------------------------------------------------------------

    @Override
    public int getColumnCount() {
    	
        return columns.length;
        
    }
    
    //----------------------------------------------------------------

    @Override
    public String getColumnName(int col) {
    	
        return columns[col];
        
    }
    
    //----------------------------------------------------------------

    @Override
    public Object getValueAt(int row, int col) {
    	
        AppuntamentoPT app = appuntamenti.get(row);
        switch (col) {
            case 0: return app.getCf();
            case 1: return app.getStaffId();
            case 2: return app.getData();
            case 3: return app.getFasciaOraria();
            default: return null;
            
        }
        
    }
    
    //----------------------------------------------------------------

    public AppuntamentoPT getAppuntamentoAt(int row) {
    	
        return appuntamenti.get(row);
        
    }
    
    //----------------------------------------------------------------

}
