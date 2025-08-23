package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData;

import java.time.LocalDate;

public interface IDateUtils {
	
	public LocalDate parseData(String dataStr);
	
	//----------------------------------------------------------------
	
	public boolean isRangeValido(LocalDate inizio, LocalDate fine);
	
	//----------------------------------------------------------------

}
