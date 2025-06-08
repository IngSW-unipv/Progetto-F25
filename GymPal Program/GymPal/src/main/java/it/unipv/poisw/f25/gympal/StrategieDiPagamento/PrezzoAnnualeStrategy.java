package it.unipv.poisw.f25.gympal.StrategieDiPagamento;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.DTO.AbbonamentoDTO;

public class PrezzoAnnualeStrategy implements IStrategieCalcoloPrezzo {
	
	private final IStrategieCalcoloPrezzo base;
	
	//----------------------------------------------------------------
	
	public PrezzoAnnualeStrategy(IStrategieCalcoloPrezzo strategiaBase) {
		
		base = strategiaBase;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public double calcolaPrezzo(AbbonamentoDTO abbonamentoDTO) {
		
		//Applica 15% di sconto
		return base.calcolaPrezzo(abbonamentoDTO) * 0.85;
		
	}
	
	//----------------------------------------------------------------

}
