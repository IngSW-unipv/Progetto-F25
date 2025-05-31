package it.unipv.poisw.f25.gympal.StrategieDiPagamento;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;

public class PrezzoTrimestraleStrategy implements IStrategieCalcoloPrezzo{

	private final IStrategieCalcoloPrezzo base;
	
	//----------------------------------------------------------------
	
	public PrezzoTrimestraleStrategy(IStrategieCalcoloPrezzo strategiaBase) {
		
		base = strategiaBase;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public double calcolaPrezzo(AbbonamentoDTO abbonamentoDTO) {
		
		//Applica 5% di sconto
		return base.calcolaPrezzo(abbonamentoDTO) * 0.95;
		
	}
	
	//----------------------------------------------------------------
	

}
