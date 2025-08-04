package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento;

import java.math.BigDecimal;
import java.util.List;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;
import it.unipv.poisw.f25.gympal.persistence.beans.Sconto.Sconto;

public class ScontiOccasioneStrategy implements IStrategieCalcoloPrezzo{

	private final IStrategieCalcoloPrezzo base;

    // -------------------------------------------------------

    public ScontiOccasioneStrategy(IStrategieCalcoloPrezzo base) {
        this.base = base;
    }

    // -------------------------------------------------------

    @Override
    public double calcolaPrezzo(ICalcolaPrezzo abbDTO) {
    	
        double prezzoBase = base.calcolaPrezzo(abbDTO);
        
        List<Sconto> sconti = abbDTO.getScontiOccasioneSelezionati();

        if (sconti == null || sconti.isEmpty()) {
        	
            return prezzoBase;
            
        }

        BigDecimal prezzo = BigDecimal.valueOf(prezzoBase);

        for (Sconto s : sconti) {
            BigDecimal scontoPercentuale = s.getAmountSconto().
            								 divide(BigDecimal.valueOf(100));
            BigDecimal moltiplicatore = BigDecimal.ONE.subtract(scontoPercentuale);
            prezzo = prezzo.multiply(moltiplicatore);
        }

        return prezzo.doubleValue();
    }

    // -------------------------------------------------------
    
}
