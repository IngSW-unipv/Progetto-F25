package it.unipv.poisw.f25.gympal.Dominio.DataTransferHelpers.TowardsDB.ExchangeUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListsContentToString {
	
	/*E' scelto di usare una mappa (al posto di uno switch-case) perché più facile da
	 *mantenere ed estendere*/
	
	private static final Map<String, String> MAPPATURACOMPONENTI = new HashMap<>();
	
    //----------------------------------------------------------------
	
	static {
		
		MAPPATURACOMPONENTI.put("Sala Pesi", "S.PESI");
		MAPPATURACOMPONENTI.put("Sala allenamento a corpo libero", "S.CORPO");
		MAPPATURACOMPONENTI.put("Allenamento mirato con personal trainer", "A.PTRAINER");
		
	}
	
    //----------------------------------------------------------------
	
	public static String condensaAbbonamento(List<String> componentiAbbonamento,
									  		 List<String> corsiSelezionati) {
		
		List<String> risultato = new ArrayList<>();
		
		for(String componente: componentiAbbonamento) {
			
			if(MAPPATURACOMPONENTI.containsKey(componente)) {
				
				risultato.add(MAPPATURACOMPONENTI.get(componente));
				
			}
			
		}
		
		for(String corso: corsiSelezionati) {
			
			risultato.add(corso.toUpperCase());
			
		}
		
		
		return String.join("_", risultato);
		
	}

}
