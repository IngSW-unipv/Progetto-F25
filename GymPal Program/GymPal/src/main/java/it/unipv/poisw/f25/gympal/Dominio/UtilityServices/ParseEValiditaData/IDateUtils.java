package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.ParseEValiditaData;

import java.time.LocalDate;
import java.util.Date;

public interface IDateUtils {
	
	public LocalDate parseData(String dataStr);
	
	//----------------------------------------------------------------
	
	public boolean isRangeValido(LocalDate inizio, LocalDate fine);
	
	//----------------------------------------------------------------
	
	public LocalDate convertiDaUtilDate(Date data);
	
	//----------------------------------------------------------------

}
