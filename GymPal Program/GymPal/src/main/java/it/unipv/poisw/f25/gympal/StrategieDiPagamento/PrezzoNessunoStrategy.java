package it.unipv.poisw.f25.gympal.StrategieDiPagamento;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.DTO.AbbonamentoDTO;

public class PrezzoNessunoStrategy implements IStrategieCalcoloPrezzo{
	
	private final IStrategieCalcoloPrezzo base;
	
	//----------------------------------------------------------------
	
	public PrezzoNessunoStrategy(IStrategieCalcoloPrezzo strategiaBase) {
		
		base = strategiaBase;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public double calcolaPrezzo(AbbonamentoDTO abbonamentoDTO) {
		
		//Applica nessuno sconto
		return base.calcolaPrezzo(abbonamentoDTO);
		
	}
	
	//----------------------------------------------------------------

}
