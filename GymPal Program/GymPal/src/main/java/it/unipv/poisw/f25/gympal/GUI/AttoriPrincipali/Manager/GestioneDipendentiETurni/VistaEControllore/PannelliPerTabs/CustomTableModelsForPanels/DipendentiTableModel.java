package it.unipv.poisw.f25.gympal.GUI.AttoriPrincipali.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.CustomTableModelsForPanels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import it.unipv.poisw.f25.gympal.persistence.beans.DipendenteBean.Dipendente;

public class DipendentiTableModel extends AbstractTableModel{

	private static final long serialVersionUID = 1L;

    private final List<Dipendente> dipendenti;
    private final String[] columnNames = {
        "Staff ID", "Nome", "Cognome", "Contatto"
    };
    
    //------------------------------------------------------------

    public DipendentiTableModel() {
    	
        this.dipendenti = new ArrayList<>();
        
    }
    
    //------------------------------------------------------------

    public DipendentiTableModel(List<Dipendente> dipendenti) {
    	
        this.dipendenti = new ArrayList<>(dipendenti);
        
    }
    
    //------------------------------------------------------------

    @Override
    public int getRowCount() {
    	
        return dipendenti.size();
        
    }
    
    //------------------------------------------------------------

    @Override
    public int getColumnCount() {
    	
        return columnNames.length;
        
    }
    
    //------------------------------------------------------------

    @Override
    public String getColumnName(int columnIndex) {
    	
        return columnNames[columnIndex];
        
    }
    
    //------------------------------------------------------------

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	
        Dipendente d = dipendenti.get(rowIndex);
        
        switch (columnIndex) {
        
            case 0: return d.getStaffId();
            case 1: return d.getNome();
            case 2: return d.getCognome();
            case 3: return d.getContatto();
            default: return null;
            
        }
        
    }
    
    //------------------------------------------------------------

    public Dipendente getDipendenteAt(int rowIndex) {
    	
        if (rowIndex >= 0 && rowIndex < dipendenti.size()) {
        	
            return dipendenti.get(rowIndex);
            
        }
        
        return null;
        
    }
    
    //------------------------------------------------------------

    public void setDipendenti(List<Dipendente> nuoviDipendenti) {
    	
        dipendenti.clear();
        
        if (nuoviDipendenti != null) {
        	
            dipendenti.addAll(nuoviDipendenti);
            
        }
        
        fireTableDataChanged();
        
    }
    
    //------------------------------------------------------------

    public void addDipendente(Dipendente d) {
    	
        dipendenti.add(d);
        int row = dipendenti.size() - 1;
        fireTableRowsInserted(row, row);
        
    }

    //------------------------------------------------------------
    
    public void updateDipendenteAt(int rowIndex, Dipendente d) {
    	
        if (rowIndex >= 0 && rowIndex < dipendenti.size()) {
        	
            dipendenti.set(rowIndex, d);
            fireTableRowsUpdated(rowIndex, rowIndex);
            
        }
        
    }
    
    //------------------------------------------------------------

    public void removeDipendenteAt(int rowIndex) {
    	
        if (rowIndex >= 0 && rowIndex < dipendenti.size()) {
        	
            dipendenti.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
            
        }
        
    }
    
    //------------------------------------------------------------

    public void clear() {
    	
        dipendenti.clear();
        fireTableDataChanged();
        
    }
    
    //------------------------------------------------------------
	
}
