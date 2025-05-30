package it.unipv.poisw.f25.gympal.StrategieDiPagamento;

import it.unipv.poisw.f25.gympal.DTOs.AbbonamentoDTO;

public interface IStrategieCalcoloPrezzo {
	
	double calcolaPrezzo(AbbonamentoDTO abbonamento);

}
