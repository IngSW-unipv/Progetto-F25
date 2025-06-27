package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento;

import java.time.LocalDate;
import java.time.Period;
import java.util.Properties;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IAbbonamentoDTO;

public class PrezzoAnzianiStrategy extends PrezzoStandardStrategy {
	
    public PrezzoAnzianiStrategy(Properties prezzi) {
    	
        super(prezzi);
        
    }

	//----------------------------------------------------------------
    
    @Override
    public double calcolaPrezzo(IAbbonamentoDTO abbonamento) {
    	
        double prezzoBase = super.calcolaPrezzo(abbonamento);

        if (abbonamento.getDataNascita() != null) {
        	
            int eta = Period.between(abbonamento.getDataNascita(), LocalDate.now()).getYears();
            
            if (eta >= 60) {
            	
                // Sconto del 30% per over 60
                return prezzoBase * 0.7;
                
            }
            
        }
        
        return prezzoBase;
    }

	//----------------------------------------------------------------
    
}
