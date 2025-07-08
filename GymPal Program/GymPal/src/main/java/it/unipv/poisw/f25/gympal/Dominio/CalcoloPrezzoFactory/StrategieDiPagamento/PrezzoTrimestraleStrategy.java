package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.StrategyUtilities.ICalcolaPrezzo;

public class PrezzoTrimestraleStrategy implements IStrategieCalcoloPrezzo{

	private final IStrategieCalcoloPrezzo base;
	
	//----------------------------------------------------------------
	
	/*Il costruttore riceve la strategia da avvolgere ("wrappare")*/
	public PrezzoTrimestraleStrategy(IStrategieCalcoloPrezzo strategiaBase) {
		
		base = strategiaBase;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public double calcolaPrezzo(ICalcolaPrezzo abbonamentoDTO) {
		
		/*La chiamata "base.calcolaPrezzo()" restituisce il prezzo di base (che può essere
		 *quello standard, oppure un prezzo mediato dalle strategie basate su età), cui è
		 *applicato un 5% di sconto*/
		return base.calcolaPrezzo(abbonamentoDTO) * 0.95;
		
	}
	
	//----------------------------------------------------------------
	

}
