package it.unipv.poisw.f25.gympal.ApplicationLayer.Validatori.ValidazioneOra;

import java.time.LocalTime;

public interface IOraUtils {

	public LocalTime parseOra(String oraStr);
	
    //---------------------------------------------------------------
	
	public boolean isRangeValido(LocalTime inizio, LocalTime fine);
	
    //---------------------------------------------------------------
	
}
