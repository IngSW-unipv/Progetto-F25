package it.unipv.poisw.f25.gympal.GUI.VistaTurniPerSingoloDipendente.VisualizzazioneTurni.VistaEControllore.CustomTableModelPerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import it.unipv.poisw.f25.gympal.ApplicationLayer.GestioneTurniEDipendenti.SupportoTurni.TurniDipendente.ClasseDiSupporto.TurnoIndividuale;

public class TurniPersonaliTableModel extends AbstractTableModel {
    
	private static final long serialVersionUID = 1L;
	
	private final List<TurnoIndividuale> turni;
    private final String[] columnNames = {"Data", "Ruolo", "ID Dipendente"};
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //------------------------------------------------------------
    
    public TurniPersonaliTableModel(List<TurnoIndividuale> turni) {
    	
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
    public String getColumnName(int column) {
    	
        return columnNames[column];
        
    }
    
    //------------------------------------------------------------

    @Override
    
    public Object getValueAt(int rowIndex, int columnIndex) {
    	
        TurnoIndividuale turno = turni.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return turno.getData().format(formatter);
            case 1: return turno.getRuolo();
            case 2: return turno.getStaffId(); 
            default: return null;
            
        }
        
    }
    
    //------------------------------------------------------------

    public TurnoIndividuale getTurnoAt(int row) {
    	
        if (row >= 0 && row < turni.size()) return turni.get(row);
        
        return null;
        
    }
    
    //------------------------------------------------------------
}
