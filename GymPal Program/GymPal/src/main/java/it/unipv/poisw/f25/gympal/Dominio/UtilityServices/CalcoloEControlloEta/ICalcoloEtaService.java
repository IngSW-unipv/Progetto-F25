package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.CalcoloEControlloEta;

import java.time.LocalDate;

public interface ICalcoloEtaService {
	
	public int calcolaEta(LocalDate dataNascita);
	
    //----------------------------------------------------------------
	
    public boolean isMinorenne(LocalDate dataNascita);
    
    //----------------------------------------------------------------

}
