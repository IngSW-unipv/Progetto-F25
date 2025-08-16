package it.unipv.poisw.f25.gympal.GUI.Receptionist.GestioneCalendario.CalendarioInterattivo;

import java.time.LocalDate;
import java.util.List;

public interface ICalendarioChangeListener {

	void onCambioCalendario(List <LocalDate> dataCambiata);
	
}
