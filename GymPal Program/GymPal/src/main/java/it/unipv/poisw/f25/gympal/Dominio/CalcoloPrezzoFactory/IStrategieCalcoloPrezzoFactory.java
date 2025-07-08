package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;

public interface IStrategieCalcoloPrezzoFactory {
	
	public IStrategieCalcoloPrezzo getStrategy(ICalcolaPrezzo dto);

}
