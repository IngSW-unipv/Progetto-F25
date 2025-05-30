package it.unipv.poisw.f25.gympal.StrategieDiPagamento;

import java.time.LocalDate;
import java.time.Period;
import java.util.Properties;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;

public class PrezzoUnderAgeStrategy extends PrezzoStandardStrategy {

	/*Ho preferito estendere la strategia Standard per poter riutilizzare il metodo
	 *"calcolaPrezzo" della superclasse, e sovrascriverlo con logica appropriata alla strategia
	 *corrente.
	 *
	 *Inoltre, siccome estensione della superclasse, "PrezzoUnderAgeStrategy" implementa 
	 *l'intefaccia comune "IStrategieCalcoloPrezzo"*/
	
    public PrezzoUnderAgeStrategy(Properties prezzi) {
    	
        super(prezzi);
        
    }

    @Override
    public double calcolaPrezzo(AbbonamentoDTO abbonamento) {
    	
        double prezzoBase = super.calcolaPrezzo(abbonamento);

        if (abbonamento.getDataNascita() != null) {
        	
            int eta = Period.between(abbonamento.getDataNascita(), LocalDate.now()).getYears();
            
            if (eta < 18) {
            	
                // Sconto del 20% per persona al di sotto dei 18 anni
                return prezzoBase * 0.8;
                
            }
            
        }
        return prezzoBase;
    }
	
}
