package it.unipv.poisw.f25.gympal.GUI.Manager.GestioneEventiECorsi.VistaEControllore.PannelliPerTabs.CustomTableModelsForPanels;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import it.unipv.poisw.f25.gympal.persistence.beans.CalendarioBean.Calendario;

public class EventiTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;

	private final List<Calendario> eventi;

	private final String[] columnNames = {
		"Nome Evento", "Data", "Ora Inizio", "Ora Fine", "Destinatario", "Messaggio"
	};

	// Formatters per LocalDate e LocalTime
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

	//----------------------------------------------------------------

	public EventiTableModel(List<Calendario> eventi) {
		
		this.eventi = eventi;
		
	}

	//----------------------------------------------------------------

	@Override
	public int getRowCount() {
		
		return eventi.size();
		
	}
	
	//----------------------------------------------------------------

	@Override
	public int getColumnCount() {
		
		return columnNames.length;
		
	}

	//----------------------------------------------------------------
	
	@Override
	public String getColumnName(int column) {
		
		return columnNames[column];
		
	}
	
	//----------------------------------------------------------------

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Calendario e = eventi.get(rowIndex);

		switch (columnIndex) {
		
			case 0: return e.getNomeEvento();
			
			case 1: return e.getDataEvento() != null ? e.getDataEvento()
														.format(dateFormatter) : "";
			
			case 2: return e.getOraInizio() != null ? e.getOraInizio()
													   .format(timeFormatter) : "";
			
			case 3: return e.getOraFine() != null ? e.getOraFine()
													 .format(timeFormatter) : "";
			
			case 4: return e.getDestinatarioMessaggio();
			
			case 5: return e.getMessaggio();
			
			default: return null;
			
		}
		
	}

	//----------------------------------------------------------------

	public Calendario getEventoAt(int row) {
		if (row >= 0 && row < eventi.size()) {
			return eventi.get(row);
		}
		return null;
	}
	
	//----------------------------------------------------------------

	public void updateData(List<Calendario> nuoviEventi) {
		eventi.clear();
		eventi.addAll(nuoviEventi);
		fireTableDataChanged();
	}
	
	//----------------------------------------------------------------

}
