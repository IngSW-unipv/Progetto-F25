package it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory;

import it.unipv.poisw.f25.gympal.Dominio.CalcoloPrezzoFactory.StrategieDiPagamento.IStrategieCalcoloPrezzo;
import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistration.DTO.IAbbonamentoDTO;

public interface IStrategieCalcoloPrezzoFactory {
	
	public IStrategieCalcoloPrezzo getStrategy(IAbbonamentoDTO dto);

}
