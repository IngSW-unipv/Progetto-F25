package it.unipv.poisw.f25.gympal.StrategieDiPagamento;

import java.util.Properties;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;
import it.unipv.poisw.f25.gympal.utility.KeysConversionUtility;


public class PrezzoStandardStrategy implements IStrategieCalcoloPrezzo{
	
    private Properties prezzi;

    public PrezzoStandardStrategy(Properties prezzi) {
    	
        this.prezzi = prezzi;
        
    }


    @Override
    public double calcolaPrezzo(AbbonamentoDTO abbonamento) {
    	
        System.out.println("DEBUG nella strategia - componentiAbbonamento:");
        for (String s : abbonamento.getSezioniAbbonamento()) {
            System.out.println(" - " + s);
        }
        System.out.println("DEBUG nella strategia - corsiSelezionati:");
        for (String s : abbonamento.getCorsiSelezionati()) {
            System.out.println(" - " + s);
        }
    	
        double totale = 0.0;
        
        System.out.println("Componenti abbonamento:");
        for (String componente : abbonamento.getSezioniAbbonamento()) {
        	
        	/*Questa istruzione richiama un metodo di utilità che rimuove spazi dalle chiavi
        	 *e li sostituisce con underscores, per avere la corrispondenza con il contenuto
        	 *del file "prezziComponentiCorso"*/
        	
        	String componenteKey = KeysConversionUtility.toPropertyKey(componente);
        	
        	/*Questa istruzione crea in modo dinamico una chiave da ricercare nel file
        	 *".properties".
        	 *Ad esempio, se componente = "Sala pesi", allora la chiave sarà
        	 *"componenti.Sala_pesi"
        	 *
        	 *Poi cerca la chiave nel file ".properties".
        	 *
        	 *Ed infine, se la chiave è individuata allora restituisce il valore ad essa asso-
        	 *ciato, sotto forma di stringa. Qualora la chiave non esista, viene passato uno "0"
        	 *di default, per evitare un "null" e conseguente "NumberFormatException"*/
        	
            
            String prezzo = prezzi.getProperty("componenti." + componenteKey, "0");
            
            System.out.println(" - " + componente + " (chiave: 'componenti." + componenteKey + "'): " + prezzo);
            
            /*Questa istruzione converte il valore ottenuto pocanzi da "String" a "Double", ed 
             *esegue la somma*/
            totale += Double.parseDouble(prezzo);
        }

        System.out.println("Corsi selezionati:");
        
        for (String corso : abbonamento.getCorsiSelezionati()) {
        	
            String prezzo = prezzi.getProperty("corsi." + corso, "0");
            
            System.out.println(" - " + corso + ": " + prezzo);
            
            totale += Double.parseDouble(prezzo);
        }

        System.out.println("Totale calcolato: " + totale);
        return totale;
    }


}
