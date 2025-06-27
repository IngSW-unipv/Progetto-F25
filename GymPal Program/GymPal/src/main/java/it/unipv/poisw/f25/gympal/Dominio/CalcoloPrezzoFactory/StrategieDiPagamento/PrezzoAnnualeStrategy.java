package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento;


import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IAbbonamentoDTO;

public class PrezzoAnnualeStrategy implements IStrategieCalcoloPrezzo {
	
	private final IStrategieCalcoloPrezzo base;
	
	//----------------------------------------------------------------
	
	public PrezzoAnnualeStrategy(IStrategieCalcoloPrezzo strategiaBase) {
		
		base = strategiaBase;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public double calcolaPrezzo(IAbbonamentoDTO abbonamentoDTO) {
		
		//Applica 15% di sconto
		return base.calcolaPrezzo(abbonamentoDTO) * 0.85;
		
	}
	
	//----------------------------------------------------------------

}
