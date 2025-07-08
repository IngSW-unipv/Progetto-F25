package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento;

import java.time.LocalDate;
import java.time.Period;
import java.util.Properties;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;

public class PrezzoAnzianiStrategy extends PrezzoStandardStrategy {
	
    public PrezzoAnzianiStrategy(Properties prezzi) {
    	
        super(prezzi);
        
    }

	//----------------------------------------------------------------
    
    @Override
    public double calcolaPrezzo(ICalcolaPrezzo abbonamentoDTO) {
    	
        double prezzoBase = super.calcolaPrezzo(abbonamentoDTO);

        if (abbonamentoDTO.getDataNascita() != null) {
        	
            int eta = Period.between(abbonamentoDTO.getDataNascita(), LocalDate.now()).getYears();
            
            if (eta >= 60) {
            	
                // Sconto del 30% per over 60
                return prezzoBase * 0.7;
                
            }
            
        }
        
        return prezzoBase;
    }

	//----------------------------------------------------------------
    
}
