package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;

public interface ICalcolaPrezzo {
	
    boolean getScontoEta();
    
	//----------------------------------------------------------------
    
    boolean getScontoOccasioni();
    
	//----------------------------------------------------------------
    
    DurataAbbonamento getDurataAbbonamento();
    
	//----------------------------------------------------------------
    
    LocalDate getDataNascita();
    
	//----------------------------------------------------------------
    
    List<String> getSezioniAbbonamento();
    
	//----------------------------------------------------------------
    
    List<String> getCorsiSelezionati();
    
	//----------------------------------------------------------------

}
