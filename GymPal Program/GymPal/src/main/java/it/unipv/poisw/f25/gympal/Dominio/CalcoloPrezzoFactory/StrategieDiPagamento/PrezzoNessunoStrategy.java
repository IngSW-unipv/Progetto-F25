package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento;


import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IRiepilogoDTO;

public class PrezzoNessunoStrategy implements IStrategieCalcoloPrezzo{
	
	private final IStrategieCalcoloPrezzo base;
	
	//----------------------------------------------------------------
	
	public PrezzoNessunoStrategy(IStrategieCalcoloPrezzo strategiaBase) {
		
		base = strategiaBase;
		
	}
	
	//----------------------------------------------------------------
	
	@Override
	public double calcolaPrezzo(IRiepilogoDTO abbonamentoDTO) {
		
		//Applica nessuno sconto
		return base.calcolaPrezzo(abbonamentoDTO);
		
	}
	
	//----------------------------------------------------------------

}
