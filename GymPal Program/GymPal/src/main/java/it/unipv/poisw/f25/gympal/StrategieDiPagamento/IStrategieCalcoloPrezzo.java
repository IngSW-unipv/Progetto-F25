package it.unipv.poisw.f25.gympal.StrategieDiPagamento;

import it.unipv.poisw.f25.gympal.GUI.Receptionist.CustomerRegistrationCycle.DTO.AbbonamentoDTO;

public interface IStrategieCalcoloPrezzo {
	
	double calcolaPrezzo(AbbonamentoDTO abbonamento);

}
