package it.unipv.poisw.f25.gympal.ApplicationLayer.Mappers.ExchangeUtilities;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BDayFromCf {

	private static final Map<Character, Integer> MESE_MAP;
	
    //----------------------------------------------------------------
	
	/*Inizializzazione della mappa a sola lettura.
	 *Questo è il modo in cui mese-lettera sono rapportati nel CF italiano.
	 *Dunque non ha senso che, una volta costruita, questa mappa sia modificabile.*/
	static {
		
		Map<Character, Integer> map = new HashMap<>();
        map.put('A', 1); map.put('B', 2); map.put('C', 3); map.put('D', 4);
        map.put('E', 5); map.put('H', 6); map.put('L', 7); map.put('M', 8);
        map.put('P', 9); map.put('R', 10); map.put('S', 11); map.put('T', 12);
        MESE_MAP = Collections.unmodifiableMap(map);
		
	}
	
    //----------------------------------------------------------------
	
	static public LocalDate estraiDataNascita(String cf) {
		
		if (cf == null || cf.length() < 16) {
			
			throw new IllegalArgumentException("Codice Fiscale -NON- valido");
			
		}
		
		int anno = Integer.parseInt(cf.substring(6, 8));
		char meseChar = cf.charAt(8);
		int giorno = Integer.parseInt(cf.substring(9, 11));
		
		if (giorno >= 40) { giorno -= 40;}
		
		Integer mese = MESE_MAP.get(meseChar);
		
		if(mese == null) {
			
			throw new IllegalArgumentException("Mese INVALIDO nel codice fiscale");
			
		}
		
		int annoCompleto = (anno <= 25) ? (2000 + anno) : (1900 + anno); 
		
		return LocalDate.of(annoCompleto, mese, giorno);
				
	}
	
    //----------------------------------------------------------------
	
}
