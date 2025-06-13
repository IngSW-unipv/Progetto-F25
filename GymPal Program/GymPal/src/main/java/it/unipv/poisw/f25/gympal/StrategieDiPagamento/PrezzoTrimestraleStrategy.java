package it.unipv.poisw.f25.gympal.StrategieDiPagamento;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IRiepilogoDTO;

public class PrezzoTrimestraleStrategy implements IStrategieCalcoloPrezzo{

	private final IStrategieCalcoloPrezzo base;
	
	//----------------------------------------------------------------
	
	/*Il costruttore riceve la strategia da avvolgere ("wrappare")*/
	public PrezzoTrimestraleStrategy(IStrategieCalcoloPrezzo strategiaBase) {
		
		base = strategiaBase;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public double calcolaPrezzo(IRiepilogoDTO abbonamentoDTO) {
		
		/*La chiamata "base.calcolaPrezzo()" restituisce il prezzo di base (che può essere
		 *quello standard, oppure un prezzo mediato dalle strategie basate su età), cui è
		 *applicato un 5% di sconto*/
		return base.calcolaPrezzo(abbonamentoDTO) * 0.95;
		
	}
	
	//----------------------------------------------------------------
	

}
