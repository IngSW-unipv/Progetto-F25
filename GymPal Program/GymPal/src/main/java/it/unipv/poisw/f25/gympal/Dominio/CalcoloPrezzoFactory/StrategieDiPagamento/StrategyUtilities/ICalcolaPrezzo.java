package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities;

import java.time.LocalDate;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public interface ICalcolaPrezzo {
	
	public boolean getScontoEta();
    
	//----------------------------------------------------------------
    
    public boolean getScontoOccasioni();
    
	//----------------------------------------------------------------
    
    public DurataAbbonamento getDurataAbbonamento();
    
	//----------------------------------------------------------------
    
    public LocalDate getDataNascita();
    
	//----------------------------------------------------------------
    
    public List<String> getSezioniAbbonamento();
    
	//----------------------------------------------------------------
    
    public List<String> getCorsiSelezionati();
    
	//----------------------------------------------------------------
    
    public List<Sconto> getScontiOccasioneSelezionati();
    
	//----------------------------------------------------------------
    
    //public void setScontiOccasioneSelezionati(List<Sconto> scontiOccasioneSelezionati);
    
	//----------------------------------------------------------------

}
