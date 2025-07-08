package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory;




import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.Properties;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.Enums.DurataAbbonamento;

public class StrategieCalcoloPrezzoFactory implements IStrategieCalcoloPrezzoFactory {
	
    private static Properties strategie;
    private static Properties prezzi;

	//----------------------------------------------------------------
    
    public StrategieCalcoloPrezzoFactory() {
    	
        try {
        	
            strategie = new Properties();
            
            
            try (InputStream input = new FileInputStream("properties/strategySelection")) {
            	
                strategie.load(input);
                
            }

            prezzi = new Properties();
            
            try (InputStream prezziInput = new FileInputStream("properties/prezziComponentiCorso")) {
            	
                prezzi.load(prezziInput);
                
                //DEBUG
                //prezzi.forEach((key, value) -> System.out.println("KEY: " + key + " = " + value));
                /////////////
            }

        } catch (Exception e) {
        	
            throw new RuntimeException("Errore nel caricamento dei file .properties", e);
            
        }
        
    }
    
	//----------------------------------------------------------------
    
    @Override
    public IStrategieCalcoloPrezzo getStrategy(ICalcolaPrezzo abbonamentoDTO) {
    	
    	    IStrategieCalcoloPrezzo strategiaBase;

    	    // Strategia base: età oppure standard
    	    
    	    boolean scontoEta = abbonamentoDTO.getScontoEta();
    	    boolean scontoOccasioni = abbonamentoDTO.getScontoOccasioni();
    	    DurataAbbonamento durata = abbonamentoDTO.getDurataAbbonamento();
    	    
    	    String chiave = "strategia.standard";
    	    
    	    int eta = abbonamentoDTO.getDataNascita() != null
    	    		
    	        ? Period.between(abbonamentoDTO.getDataNascita(), LocalDate.now()).getYears()
    	        : -1;

    	    //Se scatta la "if", la variabile "chiave" è sovrascritta
    	    
    	    if (scontoEta && eta >= 0) {
    	    	
    	        if (eta < 18) {chiave = "strategia.under18";}
    	        else if (eta >= 60) {chiave = "strategia.over60";}
    	        
    	    }

    	    try {
    	    	
    	        String nomeClasseSconto = strategie.getProperty(chiave);
    	        
    	        Class<?> classeBase = Class.forName(nomeClasseSconto);
    	        
    	        /*La variabile "strategiaBase" referenzia un oggeto pronto per
    	         *essere stratificato con altre strategie*/
    	        strategiaBase = (IStrategieCalcoloPrezzo) classeBase.getConstructor(Properties.class)
    	        												   .newInstance(prezzi);
    	        
    	    } catch (Exception e) {
    	    	
    	        throw new RuntimeException("Errore con strategia base", e);
    	        
    	    }

    	    /* Wrapper con strategia occasioni (basato su reflection, come il resto)
    	    if (scontoOccasioni) {
    	        strategiaBase = ... Lasciato in sospeso, per ora
    	    }*/

    	    
    	    // Wrapper con strategia durata
    	    if (durata != null) {
    	    	
    	        try {
    	        	
    	            String durataKey = "strategia." + durata.name().toLowerCase(); // es: strategia.annuale
    	            
    	            String durataClassName = strategie.getProperty(durataKey);
    	            
    	            Class<?> durataClass = Class.forName(durataClassName);

    	            /* Prende il costruttore della classe associata alla chiave, e gli passa 
    	             * la "strategiaBase" proveniente dal blocco di istruzioni soprastante,
    	             * si che essa venga stratificata(avvolto) da un'ulteriore strategia.*/
    	            
    	            strategiaBase = (IStrategieCalcoloPrezzo) durataClass
    	            		
    	                .getConstructor(IStrategieCalcoloPrezzo.class)
    	                
    	                .newInstance(strategiaBase);
    	            
    	        } catch (Exception e) {
    	        	
    	            throw new RuntimeException("Errore durante la composizione della strategia di durata", e);
    	            
    	        }
    	        
    	        
    	    }

    	    return strategiaBase;
    	}

    
	//----------------------------------------------------------------

}
