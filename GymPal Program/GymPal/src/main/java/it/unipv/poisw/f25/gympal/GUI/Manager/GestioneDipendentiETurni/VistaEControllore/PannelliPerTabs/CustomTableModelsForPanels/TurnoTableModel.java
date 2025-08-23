package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneDipendentiETurni.VistaEControllore.PannelliPerTabs.CustomTableModelsForPanels;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import it.unipv.poisw.f25.gympal.persistence.beans.TurnoBean.Turno;

public class TurnoTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;

    private final List<Turno> turni;
    
    private final String[] columnNames = {
        "Data", "Rec. Mattina", "Rec. Pomeriggio", "PT Mattina", "PT Pomeriggio"
    };

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //------------------------------------------------------------
    
    public TurnoTableModel() {
    	
        this.turni = new ArrayList<>();
        
    }
    
    //------------------------------------------------------------

    public TurnoTableModel(List<Turno> turni) {
    	
        this.turni = new ArrayList<>(turni);
        
    }
    
    //------------------------------------------------------------

    @Override
    public int getRowCount() {
    	
        return turni.size();
        
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
    	
        Turno turno = turni.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return turno.getData() != null ? turno.getData().format(formatter) : "";
            case 1: return turno.getRecMat();
            case 2: return turno.getRecPom();
            case 3: return turno.getPtMat();
            case 4: return turno.getPtPom();
            default: return null;
            
        }
        
    }
    
    //------------------------------------------------------------

    public Turno getTurnoAt(int rowIndex) {
    	
        if (rowIndex >= 0 && rowIndex < turni.size()) {
        	
            return turni.get(rowIndex);
            
        }
        
        return null;
        
    }
    
    //------------------------------------------------------------

    public void setTurni(List<Turno> nuoviTurni) {
    	
        this.turni.clear();
        
        if (nuoviTurni != null) {
        	
            this.turni.addAll(nuoviTurni);
            
        }
        
        fireTableDataChanged();
        
    }
    
    //------------------------------------------------------------

    public void clear() {
    	
        this.turni.clear();
        fireTableDataChanged();
        
    }
    
    //------------------------------------------------------------

    public void removeTurnoAt(int rowIndex) {
    	
        if (rowIndex >= 0 && rowIndex < turni.size()) {
        	
            turni.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
            
        }
        
    }
    
    //------------------------------------------------------------

    public void addTurno(Turno turno) {
    	
        this.turni.add(turno);
        fireTableRowsInserted(turni.size() - 1, turni.size() - 1);
        
    }
    
    //------------------------------------------------------------

    public void updateTurnoAt(int rowIndex, Turno turno) {
    	
        if (rowIndex >= 0 && rowIndex < turni.size()) {
        	
            turni.set(rowIndex, turno);
            fireTableRowsUpdated(rowIndex, rowIndex);
            
        }
        
    }
    
    //------------------------------------------------------------

}
