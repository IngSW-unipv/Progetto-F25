package it.unipv.poisw.f25.gympal.ApplicationLayer.DataTransferServices.TowardsDB.ExchangeUtilities;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;

public class CalcolaFineAbbonamento {
	
	/*Ho scelto di usare una mappa (al posto di uno switch-case) perché più facile da
	 *mantenere ed estendere*/
	
	private static final Map<DurataAbbonamento, 
							 Function<LocalDate, LocalDate>> CALCOLOFINEABBONAMENTO =
							 
							 					new EnumMap<>(DurataAbbonamento.class);
	
    //----------------------------------------------------------------
	
    static {
    	
        CALCOLOFINEABBONAMENTO.put(DurataAbbonamento.MENSILE, 
										d -> d.plusMonths(1));
    	
        CALCOLOFINEABBONAMENTO.put(DurataAbbonamento.TRIMESTRALE, 
        								d -> d.plusMonths(3));
        
        CALCOLOFINEABBONAMENTO.put(DurataAbbonamento.SEMESTRALE, 
        								d -> d.plusMonths(6));
        
        CALCOLOFINEABBONAMENTO.put(DurataAbbonamento.ANNUALE,     
        								d -> d.plusYears(1));
        
        CALCOLOFINEABBONAMENTO.put(DurataAbbonamento.NESSUNO,     
        								d -> d); 
    }
    
    //----------------------------------------------------------------

    public static LocalDate calcolaFineAbbonamento(LocalDate inizio, 
    											   DurataAbbonamento durata) {
    	
        if (inizio == null || durata == null) {
        	
            return inizio;
            
        }

        /*Il metodo "getOrDefault" restituisce il valore mappato alla specifica chiave.
         *Se alla chiave non corrisponde alcun valore, è allora restituito un valore di
         *default.*/
        return CALCOLOFINEABBONAMENTO.getOrDefault(durata, d -> d).apply(inizio);
        
    }
    
    //----------------------------------------------------------------

}
