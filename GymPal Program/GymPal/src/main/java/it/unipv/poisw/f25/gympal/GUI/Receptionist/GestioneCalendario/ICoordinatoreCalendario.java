package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JFrame;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo.ICalendarioChangeListener;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.DTOs.IDatiCellaCalendarioDTO;

public interface ICoordinatoreCalendario {
	
	boolean verificaDisponibilita(LocalDate giorno, int ora);
	 
	//----------------------------------------------------------------
	
	public IDatiCellaCalendarioDTO getContenutoCella(LocalDate data, int ora, int minuti);
	
	//----------------------------------------------------------------
	
	 public void inizializzaConBarra(JFrame framePadre, Runnable callbackFine);
	 
	//----------------------------------------------------------------
	 
	 public void aggiornaDatiCella(IDatiCellaCalendarioDTO dto);
	 
	//----------------------------------------------------------------
	 
	 public void addCalendarioChangeListener(ICalendarioChangeListener listener);
	 
	 public void removeCalendarioChangeListener(ICalendarioChangeListener listener);
	 
	//----------------------------------------------------------------
	 
	 public void notificaCambio(LocalDate data);
	 
	 public void notificaCambio(List<LocalDate> dateCambiate);
	 
	//----------------------------------------------------------------

}
