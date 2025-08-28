package it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.Codecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListsToStringCodec implements IListsToStringCodec{
	
	/*E' scelto di usare una mappa (al posto di uno switch-case) perché più facile da
	 *mantenere ed estendere*/
	
	private static final Map<String, String> MAPPATURACOMPONENTI = new HashMap<>();
	private static final Map<String, String> DECODIFICA = new HashMap<>();
	
    //----------------------------------------------------------------
	
	static {
		
		MAPPATURACOMPONENTI.put("Sala pesi", "S.PESI");
		MAPPATURACOMPONENTI.put("Sala allenamento a corpo libero", "S.CORPO");
		MAPPATURACOMPONENTI.put("Allenamento mirato con personal trainer", "A.PTRAINER");
		
		/*Il presente ciclo costruisce la mappa inversa, usata nella conversione da
		 *singola String a liste di String.*/
		for(Map.Entry<String, String> entry: MAPPATURACOMPONENTI.entrySet()) {
			
			DECODIFICA.put(entry.getValue(), entry.getKey());
			
		}
		
	}
	
    //----------------------------------------------------------------
	
	@Override
	public String condensaAbbonamento(List<String> componentiAbbonamento,
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
	
    //----------------------------------------------------------------
	
	@Override
	public void espandiAbbonamento(String composizioneAbbonamento,
								   List<String> componentiAbbonamento,
								   List<String> corsiSelezionati) {
		
		if(componentiAbbonamento == null) {
			
			throw new IllegalArgumentException("ATTENZIONE! La lista 'componentiAbbonamento'"
											   + " non è stata inizializzata!!");
			
		}
		
		if (corsiSelezionati == null) {
			
			throw new IllegalArgumentException("ATTENZIONE! La lista 'corsiSelezionati'"
					   + " non è stata inizializzata!!");
			
		}
		
		/*Pulizia preventiva delle liste.*/
		componentiAbbonamento.clear();
		corsiSelezionati.clear();
		
		
		if(composizioneAbbonamento == "n/a") {
			
			componentiAbbonamento.add("none");
			corsiSelezionati.add("none");
			return;
			
		}
		
		String[] token = composizioneAbbonamento.split("_");
		
		for(String t: token) {
			
			if(DECODIFICA.containsKey(t)) {
				
				componentiAbbonamento.add(DECODIFICA.get(t));
				
			}
			
			else {
				
				corsiSelezionati.add(t);
				
			}
			
		}
		
	}
	
    //----------------------------------------------------------------

}
