package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento;


import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;

public interface IStrategieCalcoloPrezzo {
	
	double calcolaPrezzo(ICalcolaPrezzo abbonamentoDTO);

}
