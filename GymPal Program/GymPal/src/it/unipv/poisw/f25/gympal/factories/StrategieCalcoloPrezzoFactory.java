package it.unipv.poisw.f25.gympal.factories;



import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.StrategieDiPagamento.IStrategieCalcoloPrezzo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.Properties;

public class StrategieCalcoloPrezzoFactory {
	
    private static Properties strategie;
    private static Properties prezzi;

    
    static {
    	
        try {
        	
            strategie = new Properties();
            
            
            try (InputStream input = new FileInputStream("properties/strategySelection")) {
            	
                strategie.load(input);
                
            }

            prezzi = new Properties();
            
            try (InputStream prezziInput = new FileInputStream("properties/prezziComponentiCorso")) {
            	
                prezzi.load(prezziInput);
                
                //////////////
                prezzi.forEach((key, value) -> System.out.println("KEY: " + key + " = " + value));
                /////////////
            }

        } catch (Exception e) {
        	
            throw new RuntimeException("Errore nel caricamento dei file .properties", e);
            
        }
        
    }

    public static IStrategieCalcoloPrezzo getStrategy(AbbonamentoDTO abbonamento) {
    	
        String chiave;

        int eta = -1;

        if (abbonamento.getDataNascita() != null) {
        	
            eta = Period.between(abbonamento.getDataNascita(), LocalDate.now()).getYears();
            
        }

        if (eta >= 0 && eta < 18) {
        	
            chiave = "strategia.under18";
            
        } else if (eta >= 60) {
        	
            chiave = "strategia.over60";
            
        } else {
        	
            chiave = "strategia.standard";
            
        }

        try {
        	
            String className = strategie.getProperty(chiave);
            
            Class<?> classe = Class.forName(className);

            return (IStrategieCalcoloPrezzo) classe.getConstructor(Properties.class)
                    							   .newInstance(prezzi);

        } catch (Exception e) {
        	
            throw new RuntimeException("Errore durante l'istanziazione della strategia: " + chiave, e);
            
        }
        
    }

}
