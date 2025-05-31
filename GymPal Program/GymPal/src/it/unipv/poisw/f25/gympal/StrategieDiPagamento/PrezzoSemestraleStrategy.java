package it.unipv.poisw.f25.gympal.StrategieDiPagamento;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;

public class PrezzoSemestraleStrategy implements IStrategieCalcoloPrezzo{
	
	private final IStrategieCalcoloPrezzo base;
	
	//----------------------------------------------------------------
	
	public PrezzoSemestraleStrategy(IStrategieCalcoloPrezzo strategiaBase) {
		
		base = strategiaBase;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public double calcolaPrezzo(AbbonamentoDTO abbonamentoDTO) {
		
		//Applica 10% di sconto
		return base.calcolaPrezzo(abbonamentoDTO) * 0.90;
		
	}
	
	//----------------------------------------------------------------
	
	

}
