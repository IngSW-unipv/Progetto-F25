package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario;

import java.time.LocalDate;

import javax.swing.JFrame;

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

}
