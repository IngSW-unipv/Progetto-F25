package it.unipv.poisw.f25.gympal.Dominio.UtilityServices.GenerazioneIDSessioneCorso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import it.unipv.poisw.f25.gympal.persistence.beans.SessioneCorsoBean.SessioneCorso;

public class SessionIdGenerator implements ISessionIdGenerator{
	
    private Map<String, AtomicInteger> maxCountersByTipo;
    
	//----------------------------------------------------------------
    
    @Override
    public void initialize(List<SessioneCorso> sessioni) {
    	
    	maxCountersByTipo = new HashMap<>();
        maxCountersByTipo.clear();
        
        for (SessioneCorso s : sessioni) {
        	
            String tipo = extractTipoDaId(s.getSessioneId());
            int numero = extractNumeroDaId(s.getSessioneId());
            maxCountersByTipo
                .computeIfAbsent(tipo, k -> new AtomicInteger(0))
                .updateAndGet(current -> Math.max(current, numero));
            
        }
        
    }
    
	//----------------------------------------------------------------

    @Override
    public String generateId(String tipoCorso) {
    	
        AtomicInteger counter = maxCountersByTipo
        						.computeIfAbsent(tipoCorso.toUpperCase(), 
        						k -> new AtomicInteger(0));
        
        int nuovoNumero = counter.incrementAndGet();
        
        return String.format("%06d", nuovoNumero) + tipoCorso.toUpperCase();
        
    }

    
	//----------------------------------------------------------------
    
    private String extractTipoDaId(String id) {
    	
        // es. da "010825CROSSFIT" estrai "CROSSFIT"
        return id.substring(6);
        
    }
    
	//----------------------------------------------------------------
    
    private int extractNumeroDaId(String id) {
    	
        // es. da "010825CROSSFIT" estrai 10825
        return Integer.parseInt(id.substring(0, 6));
        
    }
    
	//----------------------------------------------------------------

}
